package main;

import java.awt.BorderLayout;
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
		
		Signal signal = new Signal(Trames.courbe(trame, type), trame);
		
		JScrollPane scroll = new JScrollPane(signal, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.add(scroll, BorderLayout.CENTER);
		
		this.setVisible(true);
	}
	
}
