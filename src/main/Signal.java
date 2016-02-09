package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Yohann Bacha <y.bacha@live.fr>
 */
public class Signal extends JPanel {
	
	public static final int largeurMin = 80;
	public static final int largeurMax = 1000;
	public static final int hauteurEntete = 100;
	public static final int ecart = 30;
	public static final int hauteurGraphe = 150;
	public static final int tailleEntete = 30;
	
	
	private final ArrayList<Couple> trame;
	private final String signal;
	private final JFrame parent;
	
	public Signal(ArrayList<Couple> trame, String signal, JFrame parent) {
		this.trame = trame;
		this.signal = signal;
		this.parent = parent;
		
		setPreferredSize(new Dimension(getLargeur(), Signal.hauteurEntete + Signal.hauteurGraphe));
		setBackground(Color.white);
	}
	
	private int getLargeur() {
		int largeur = parent.getWidth() / signal.length();
		if(largeur < largeurMin) largeur = largeurMin; 
		
		return largeur;
	}
	
	public int getYGraphe() {
		return Signal.hauteurEntete + Signal.ecart;
	}

	public int getTotalY() {
		return Signal.hauteurEntete + Signal.ecart + Signal.hauteurGraphe;
	}
	
	@Override
	public Dimension getPreferredSize() {
		int largeur = getLargeur() * signal.length();
		
		return new Dimension(largeur, getTotalY());
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		// Définit la largeur occupée par une valeur du signal
		int largeur = getLargeur();
		
		for(int i = 0; i < this.signal.length(); i++) {
			// Le tracé de l'entête
			Font font = new Font("TimesRoman", Font.PLAIN, tailleEntete);
			g2.setFont(font);
			int x = (largeur * i) + (largeur / 2) - (g2.getFontMetrics().stringWidth(Character.toString(signal.charAt(i)))/2);
			int y = (hauteurEntete / 2) + (tailleEntete / 2);
			g2.setColor(Color.black);
			g2.drawString(Character.toString(signal.charAt(i)), x, y);
			
			// Tracé des pointillés
			float[] dash = {10.0f, 7.0f};
			g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
			g2.drawLine(i*largeur, 0, i*largeur, getYGraphe() + Signal.hauteurGraphe);
			
			g2.drawLine(i*largeur, Signal.hauteurEntete, (i+1)*largeur, Signal.hauteurEntete);
			g2.drawLine(i*largeur, getYGraphe(), (i+1)*largeur, getYGraphe());
			g2.drawLine(i*largeur, getYGraphe() + (Signal.hauteurGraphe / 2), (i+1)*largeur, getYGraphe() + (Signal.hauteurGraphe / 2));
			g2.drawLine(i*largeur, getYGraphe() + Signal.hauteurGraphe, (i+1)*largeur, getYGraphe() + Signal.hauteurGraphe);
			
			// Le tracé de la courbe
			g2.setStroke(new BasicStroke(3));
			g2.setColor(Color.red);
			
			// Traçons la transition de début
			if(i != 0) {
				if(this.trame.get(i-1).milieu != this.trame.get(i).debut)
					g2.drawLine((largeur * i), getYGraphe(), (largeur * i), getYGraphe() + Signal.hauteurGraphe);
			}
			
			// La première partie de la courbe
			g2.drawLine((largeur*i), getYGraphe() + ((this.trame.get(i).debut + 1) % 2) * Signal.hauteurGraphe, (largeur*i) + (largeur/2), getYGraphe() + ((this.trame.get(i).debut + 1) % 2) * Signal.hauteurGraphe);
			
			// Transition s'il y a lieu
			if(this.trame.get(i).debut != this.trame.get(i).milieu)
				g2.drawLine((largeur * i) + (largeur/2), getYGraphe(), (largeur * i)  + (largeur/2), getYGraphe() + Signal.hauteurGraphe);
			
			// La deuxième partie de la courbe
			g2.drawLine((largeur*i) + (largeur/2), getYGraphe() + ((this.trame.get(i).milieu + 1) % 2) * Signal.hauteurGraphe, (largeur*(i+1)), getYGraphe() + ((this.trame.get(i).milieu + 1) % 2) * Signal.hauteurGraphe);
		}
		
		
	}
}
