package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 *
 * @author Yohann Bacha <y.bacha@live.fr>
 */
public class PlotSignal extends JFrame {
	
	public PlotSignal(TypeCourbe type, String trame) throws Exception {
		this.setTitle("Affichage du signal " + type.getNom());
		this.setLayout(new BorderLayout());
		this.setSize(1000, Signal.hauteurEntete + Signal.hauteurGraphe);
		
		Signal signal = new Signal(Trames.courbe(trame, type), trame, this);
		JScrollPane scroll = new JScrollPane(signal, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		this.setMaximumSize(new Dimension(Signal.largeurMax, Signal.hauteurEntete + Signal.hauteurGraphe));
		this.add(scroll, BorderLayout.CENTER);
		this.pack();
		this.setSize(1000,600);
		this.setVisible(true);
	}
	
}
