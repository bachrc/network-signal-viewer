# Afficheur de Transmissions

Ce projet de TP a pour but d'implémenter une représentation graphique d'une transmission en bande de passe dans un applet JAVA. Ce sujet rentre dans le cadre du TP n°1 fourni par M. DUVALLET disponible [ici](http://litis.univ-lehavre.fr/~duvallet/enseignements/Cours/M1INFO/Reseau/MI-TP-Transmission.pdf).

## Sommaire

<!-- MarkdownTOC -->

- Rappel du sujet
- Types de code
    - Code NRZ
    - Code NRZi
    - Code Manchester
    - Code Manchester différentiel
    - Code de Miller
- Le programme
    - L'interface de demande du signal
    - Tracé du signal
        - Analyseur de signal
        - Affichage de l'analyse

<!-- /MarkdownTOC -->


## Rappel du sujet

Il s'agit d'implanter un programme JAVA (sous forme d'une applet) permettant de tracer le signal électrique émis lors de l'envoi d'une chaîne binaire en fonction du code choisi (NRZ, Manchester, Manchester différentiel, Miller).

1. Votre applet devra permettre de saisir une chaine binaire et de choisir le code à utiliser pour le signal électrique.
2. En fonction de la chaine binaire et du code choisi, vous devez tracer le bon signal.

## Types de code

### Code NRZ

Le code No Return to Zero (NRZ) résoud le probleme d'absence de signal sur le câble. Dans ce type de code, nous codons le bit 1 par un signal de n volts et le bit 0 par un signal opposé.

Voici ci-dessous le signal `11001010` codé avec le code NRZ :

![NRZ](http://i.imgur.com/hV9W6fN.png)

### Code NRZi

Le code No Return to Zero inverted (NRZi) est similaire au code NRZ, mais les tensions associées aux valeurs binaires sont inversées : 1 est codé par une tension négative et 0 par une tension positive.

Ci-dessous le même signal `11001010` codé avec le code NRZ inversé :

![NRZi](http://i.imgur.com/tU710N8.png)

### Code Manchester

Le code Manchester (ou le code biphase) propose une solution au problème de détection des longues chaînes de 0 ou 1.

Ici, le 1 est codé par un passage de la tension n à -n et 0 par le passage en sens inverse.

Ci-dessous le même signal `11001010` codé avec le code Manchester :

![Manchester](http://i.imgur.com/9FfEoKO.png)

### Code Manchester différentiel

Le code Manchester différentiel (ou code biphase différentiel) est similaire au code de Manchester, mais le bit 0 est codé par une transition en début d'horloge contrairement au bit 1. Et dans les deux cas, un changement de tension est réalisé en milieu de temps horloge.

Ci-dessous le même signal `11001010` codé avec le code Manchester différentiel :

![Manchester différentiel](http://i.imgur.com/bR3tVix.png)

### Code de Miller

Le code de Miller propose le codage suivant : le bit 1 est codé par une transition en milieu de temps horloge et le bit 0 ar une absence de transition.

Les longues suites de 0 posant toujours le problème de la synchronisation, si un bit 0 est suivi d'un autre 0, une transition est rajoutée à la fin du temps horloge.

Ci-dessous le même signal `11001010` codé avec le code de Miller :

![Miller](http://i.imgur.com/HS5ukDd.png)

## Le programme

### L'interface de demande du signal

Tout d'abord, il nous faut requérir les informations à l'utilisateur. C'est donc dans une interface très simple que nous demandons le signal à tracer, ainsi que le site de signal à tracer.

![Accueil](http://i.imgur.com/CbcPGmk.png)

Cette interface ne possède rien en particulier, comme le démontre le code de cette interface ci-dessous. L'interface est mise en forme à l'aide d'un GridBagLayout. Tout les boutons déclencheurs sont ensuite déclarés et instanciés à l'aide d'une AbstractAction possède en mémoire le type de code à analyser.

```java
public class PlotTransmissions extends JFrame {
    private JTextField signal;
    
    public PlotTransmissions() {
        this.setupAffichage();
    }
    
    public final void setupAffichage() {
        this.setTitle("Modélisateur de trames");
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5,3,5,3);
        this.add(new JLabel("Entrez le signal reçu :"), gbc);
        
        gbc.gridx = 2;
        gbc.gridwidth = 3;
        gbc.weightx = 1.;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.signal = new JTextField(18);
        this.add(this.signal, gbc);
        
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10,3,5,3);
        this.add(new JButton(new BoutonsPlot("NRZ", TypeCourbe.NRZ)), gbc);
        
        gbc.gridx = 1;
        this.add(new JButton(new BoutonsPlot("NRZi", TypeCourbe.NRZi)), gbc);
        
        gbc.gridx = 2;
        this.add(new JButton(new BoutonsPlot("Manchester", TypeCourbe.Manchester)), gbc);
        
        gbc.gridx = 3;
        this.add(new JButton(new BoutonsPlot("Manchester différentiel", TypeCourbe.ManDiff)), gbc);
        
        gbc.gridx = 4;
        this.add(new JButton(new BoutonsPlot("Miller", TypeCourbe.Miller)), gbc);
        pack();
        this.setLocation(420, 420);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
    
    public class BoutonsPlot extends AbstractAction {
        TypeCourbe type;
        public BoutonsPlot(String nom, TypeCourbe type) {
            super(nom);
            this.type = type;
        }
        
        public void actionPerformed(ActionEvent e) {
            try {
                new PlotSignal(this.type, signal.getText());
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public static void main(String[] args) {
        PlotTransmissions pt = new PlotTransmissions();
    }
}
```

### Tracé du signal

#### Analyseur de signal

Le constructeur de la fenêtre d'affichage du signal reçoit donc la chaine à analyser, ainsi que le type de code qu'elle emploie. Cependant, le constructeur doit pouvoir envoyer les informations de tracé de courbe à l'afficheur. Nous transmettons donc la chaîne à analyser à la classe Trame qui transformera la chaîne en informations de tracage de courbe.

```java
public static ArrayList<Couple> courbe(String trame, TypeCourbe type) throws Exception {
    switch(type) {
        case NRZ :
            return nrz(trame); 
        case NRZi :
            return nrzi(trame); 
        case Manchester :
            return manchester(trame); 
        case ManDiff :
            return mandiff(trame); 
        case Miller :
            return miller(trame); 
        default :
            throw new Exception("Erreur : Courbe non prise en charge.");
    }
}
```

Comme le montre la méthode ci-dessus, nous traitons le signal différement en fonction du type de courbe renseigné. Nous renvoyons ensuite la liste de Couples afin de procéder à l'affichage de la courbe.

```java
public class Couple {
    public int debut;
    public int milieu;

    public Couple(int debut, int milieu) {
        this.debut = debut;
        this.milieu = milieu;
    }

    public String toString() {
        return "(" + debut + ", " + milieu + ")";
    }
}
```

Le principe de l'objet Couple est simple : un bit de signal est représenté lors d'un temps d'horloge, sauf qu'une transition peut avoir lieu au milieu de ce dernier. Un couple possède donc simplement une valeur (0/1) pour le début du temps d'horloge, et une valeur (0/1) pour la fin du temps d'horloge.

Prenons l'exemple du code Manchester :

```java
private static ArrayList<Couple> manchester(String trame) throws Exception {
    ArrayList<Couple> retour = new ArrayList<>();
    for(int i = 0; i < trame.length(); i++) {
        char val = trame.charAt(i);
        if(val == '0')
            retour.add(new Couple(0,1));
        else if(val == '1')
            retour.add(new Couple(1,0));
        else
            throw new Exception("Erreur : Caractère dans la trame invalide");
    }
    
    return retour;
}
```

Le but de ce code est simple : Nous parcourrons toute la chaine à analyser. Je vous invite à vous informer plus haut sur le principe du code Manchester si ce n'est déjà fait.

Si la valeur du bit est de 0, nous devons la représenter d'un passage d'une tension négative à une tension positive. Nous le représentons donc d'un Couple possédant en valeur de début : "0", et en valeur de fin : "1".

A l'inverse si la valeur du bit est le 1, nous la représentons d'un passage d'une tension positive à une tension négative, grâce à un couple possédant en valeur de début "1", et en valeur de fin "0". 

Nous réitèrons donc la procédure pour chacun des bits du signal. Et si un des caractères ne correspond pas à nos attentes, nous renvoyons une exception, qui se solde par une boîte de dialogue d'erreur s'affichant.

Le constructeur de la fenêtre reçoit donc la liste de couples, l'affichage peut donc commencer.

#### Affichage de l'analyse

L'affichage du résultat se fait donc sur une fenêtre de largeur dynamique ainsi que dans un JScrollPane (une barre de défilement horizontale), vu que la longueur du signal n'est soumise à aucune contrainte. Elle peut donc être très courte, et donc s'étirer sur toute la largeur de la fenêtre, ou bien très longue et requérir un défilement horizontal afin de s'afficher dans son intégralité.

A chaque changement de taille de la fenêtre, la méthode paintComponent est appelée et redessine son contenu en fonction de sa largeur.

```java 
public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    // Définit la largeur occupée par une valeur du signal
    int largeur = getLargeur();
    
    for(int i = 0; i < this.signal.length(); i++) {
```

Ci-dessus, nous obtenons la largeur attribuée pour un bit de signal. Une fois qu'elle est obtenue, nous parcourons chaque bit de signal, et nous affichons donc en un seul parcours de gauche à droite :

- L'entête
- Les pointillés de délimitation
- La courbe de signal

##### Tracé de l'entête

```java
// Le tracé de l'entête
Font font = new Font("TimesRoman", Font.PLAIN, tailleEntete);
g2.setFont(font);
int x = (largeur * i) + (largeur / 2) - 
(g2.getFontMetrics().stringWidth(Character.toString(signal.charAt(i)))/2);
int y = (hauteurEntete / 2) + (tailleEntete / 2);
g2.setColor(Color.black);
g2.drawString(Character.toString(signal.charAt(i)), x, y);
```

Nous traçons ici l'entête de l'affichage du signal. Nous faisons en sorte que le bit concerné soit bien centré horizontalement et verticalement dans l'espace qui lui est réservé.

##### Tracé des pointillés

```java
// Tracé des pointillés
float[] dash = {10.0f, 7.0f};
g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
g2.drawLine(i*largeur, 0, i*largeur, getYGraphe() + Signal.hauteurGraphe);

g2.drawLine(i*largeur, Signal.hauteurEntete, (i+1)*largeur, Signal.hauteurEntete);
g2.drawLine(i*largeur, getYGraphe(), (i+1)*largeur, getYGraphe());

g2.drawLine(i*largeur, getYGraphe() + (Signal.hauteurGraphe / 2), 
(i+1)*largeur, getYGraphe() + (Signal.hauteurGraphe / 2));

g2.drawLine(i*largeur, getYGraphe() + Signal.hauteurGraphe, 
(i+1)*largeur, getYGraphe() + Signal.hauteurGraphe);
```

Nous traçons ici tous les pointillés de repère pour délimiter verticalement sur la gauche le bit, puis horizontalement pour délimiter l'entête, le début du graphe, le milieu du graphe et la fin de la section du graphe.

##### Tracé de la courbe

```java
// Le tracé de la courbe
g2.setStroke(new BasicStroke(3));
g2.setColor(Color.red);

// Traçons la transition de début
if(i != 0) {
    if(this.trame.get(i-1).milieu != this.trame.get(i).debut)
        g2.drawLine((largeur * i), getYGraphe(), (largeur * i), 
        getYGraphe() + Signal.hauteurGraphe);
}

// La première partie de la courbe
g2.drawLine((largeur*i), 
getYGraphe() + ((this.trame.get(i).debut + 1) % 2) * Signal.hauteurGraphe, 
(largeur*i) + (largeur/2), getYGraphe() + ((this.trame.get(i).debut + 1) % 2) * Signal.hauteurGraphe);

// Transition s'il y a lieu
if(this.trame.get(i).debut != this.trame.get(i).milieu)
    g2.drawLine((largeur * i) + (largeur/2), getYGraphe(), 
    (largeur * i)  + (largeur/2), getYGraphe() + Signal.hauteurGraphe);

// La deuxième partie de la courbe
g2.drawLine((largeur*i) + (largeur/2), 
getYGraphe() + ((this.trame.get(i).milieu + 1) % 2) * Signal.hauteurGraphe, 
(largeur*(i+1)), getYGraphe() + ((this.trame.get(i).milieu + 1) % 2) * Signal.hauteurGraphe);

```

Tout d'abord, si la valeur de fin du couple d'avant est différente de la valeur de début du couple actuel, nous traçons une transition en début de l'espace. De même si la valeur de début est différente de celle de fin du couple actuel, nous traçons une transition au milieu. Et nous traçons une droite en haut si la valeur du couple est 1, et en bas si elle est de 0.

Nous répétons ce procédé pour tous les bits du signal, et nous obtenons une courbe représentant le signal.