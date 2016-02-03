/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Yohann Bacha <y.bacha@live.fr>
 */
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
			switch(this.type) {
				case NRZ :
					
					break;
			}
		}
	}
	
	public static void main(String[] args) {
		PlotTransmissions pt = new PlotTransmissions();
	}
}
