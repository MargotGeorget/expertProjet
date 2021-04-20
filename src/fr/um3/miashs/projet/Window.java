package fr.um3.miashs.projet;

/*
 * Unitialise la fenêtre qui sera ouverte lorsqu'il y aura une connexion Cliente
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Window extends JFrame implements ActionListener {
	
	//Attributes: 
	private JButton button = new JButton("Pour saisir votre message");
	private JLabel label = new JLabel("Bienvenue dans le jeu",JLabel.CENTER);
	private JLabel waitLabel = new JLabel("En attente d'un allié...",JLabel.CENTER);
	private JPanel panel = new JPanel();
	private JPanel panelLabel = new JPanel();
	private JPanel panelButton = new JPanel();
	private GridLayout grid = new GridLayout(3,1);
	
	//Constructor:
    public Window() {
    	//Settings windows: 
    	this.setTitle("Game");
    	this.setSize(350,450);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.setLocationRelativeTo(null);
    	this.setVisible(true);
    	    	
    	panelButton.setLayout(new BorderLayout());
    	panelButton.add(button, BorderLayout.SOUTH);
      	button.addActionListener(this);
      	button.setEnabled(false);
      	
      	label.setForeground(new Color(150,25,100));
    	panelLabel.setLayout(grid);
    	panelLabel.add(label);
    	panelLabel.setBackground(Color.WHITE);
    	
    	panel.setLayout(new BorderLayout());
    	panel.setPreferredSize(new Dimension(450,450));
    	panel.add(panelButton, BorderLayout.SOUTH);    	
    	panel.add(panelLabel, BorderLayout.CENTER);
    	panel.setVisible(true);
    	setContentPane(panel);
    }

    //Methods: 
    public void addLabel (JLabel message) {
    	//permet l'ajout d'un label (augmente le nombre de ligne du gridLayout pour chaque ajout)
    	grid.setRows(grid.getRows()+1);
    	panelLabel.add(message);
    }
    
    public void actionPerformed(ActionEvent evt) {
    	String reponse = JOptionPane.showInputDialog(null, "Saisir votre message");
    	label.setText("Vous: " + reponse);
    }

    //Getters and setters:
	public JButton getButton() {
		return button;
	}
	public void setButton(JButton button) {
		this.button = button;
	}

	public JLabel getLabel() {
		return label;
	}
	public void setLabel(JLabel label) {
		this.label = label;
	}

	public JPanel getPanel() {
		return panel;
	}
	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	public JLabel getWaitLabel() {
		return waitLabel;
	}
	public void setWaitLabel(JLabel waitLabel) {
		this.waitLabel = waitLabel;
	}
	
	public JPanel getPanelLabel() {
		return panelLabel;
	}
	public void setPanelLabel(JPanel panelLabel) {
		this.panelLabel = panelLabel;
	}
    
    
}
