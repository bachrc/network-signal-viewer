package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 *
 * @author Yohann Bacha <y.bacha@live.fr>
 */
public class Signal extends JPanel {
	
	public static final int largeurMin = 100;
	public static final int hauteurEntete = 150;
	public static final int hauteurGraphe = 300;
	
	
	private final ArrayList<Couple> trame;
	private final String signal;
	
	public Signal(ArrayList<Couple> trame, String signal) {
		this.trame = trame;
		this.signal = signal;
		
		setPreferredSize(new Dimension(getLargeur(), Signal.hauteurEntete + Signal.hauteurGraphe));
	}
	
	private int getLargeur() {
		int largeur = this.getWidth() / signal.length();
		if(largeur < largeurMin) largeur = largeurMin; 
		
		return largeur;
	}
	
	@Override
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
		
		setPreferredSize(new Dimension(getLargeur(), Signal.hauteurEntete + Signal.hauteurGraphe));
		revalidate();
	}
}
