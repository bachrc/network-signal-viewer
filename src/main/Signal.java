package main;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
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
	public static final int hauteurEntete = 150;
	public static final int hauteurGraphe = 300;
	
	
	private final ArrayList<Couple> trame;
	private final String signal;
	private final JFrame parent;
	
	public Signal(ArrayList<Couple> trame, String signal, JFrame parent) {
		this.trame = trame;
		this.signal = signal;
		this.parent = parent;
		
		setPreferredSize(new Dimension(getLargeur(), Signal.hauteurEntete + Signal.hauteurGraphe));
	}
	
	private int getLargeur() {
		int largeur = parent.getWidth() / signal.length();
		if(largeur < largeurMin) largeur = largeurMin; 
		
		return largeur;
	}
	
	@Override
	public Dimension getPreferredSize() {
		int largeur = getLargeur() * signal.length();
		
		return new Dimension(largeur, Signal.hauteurEntete + Signal.hauteurGraphe);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// Définit la largeur occupée par une valeur du signal
		int largeur = getLargeur();
		
		// Définit la police des chiffres de l'entête
		g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		
		for(int i = 0; i < this.signal.length(); i++) {
			int x = (largeur * i) + (largeur / 2) - (g.getFontMetrics().stringWidth(Character.toString(signal.charAt(i)))/2);
			int y = (hauteurEntete / 2) - (g.getFontMetrics().getHeight()/2);
			g.drawString(Character.toString(signal.charAt(i)), x, y);
		}
		
		
	}
}
