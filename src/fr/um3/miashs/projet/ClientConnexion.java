package fr.um3.miashs.projet;
/*
 * Classe qui gère les connexions clientes. 
 * Permet aux clients d'entrer des chaines de caractère qui sont transmises au serveur
 * Ouvre une fenetre : interface graphique
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ClientConnexion extends Window implements Runnable {
	
	//Attributes server:
	private Socket connexion = null; //liaison avec client 
	private BufferedReader in = null; //réception de requête 
	private PrintWriter out = null; //envoi des réponses 
	private boolean done = false; 
	
	//Constructor:
	public ClientConnexion(String host, int port) {
		super();
		try {
			connexion = new Socket(host, port);
			this.setOut(new PrintWriter(new BufferedWriter(new OutputStreamWriter(getConnexion().getOutputStream())), true));
	        this.setIn(new BufferedReader(new InputStreamReader (getConnexion().getInputStream())));
	    	this.addWindowListener(new WindowAdapter(){
	            public void windowClosing(WindowEvent e){
	            	//gère la fermeture de la fenêtre, 
	            	//envoie un message qui sera detecter par le thread clientProcessor
	                  out.println("CLOSED WINDOWS");
	                  out.flush();
	                  done = true;
	            }
	    	});
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch  (IOException e) {
			e.printStackTrace();
		}

	}
	
	//Methods: 	

	public void sendMessage(String message) {
		//envoie les messages vers le thread clientProcessor
         out.println(message);
         out.flush();
	}
	
	public boolean readMessage() throws IOException {
		String response = in.readLine();
		if (response.equals("ATTENTE")) {
			//prévient le joueur qu'il n'a pas encore d'allié
			this.getPanelLabel().
			add(getWaitLabel());
			getPanel().revalidate();
			while (response.equals("ATTENTE")) {
				//attend qu'un allié soit connecté
				response = in.readLine();
			}	
		}
		//Allié trouvé 
		getWaitLabel().setText("Allié trouvé");
		getPanel().revalidate();
		
		Color colorErreur = new Color(225,50,50);
		//Controle les messages pour vérifier si la partie est gagnée, perdue ou s'il y a une erreur...
		if (response.equals("OK")) {
			JLabel label = new JLabel("Bravo! Vous avez gagné!!",JLabel.CENTER);
			label.setForeground(Color.BLUE);
			this.addLabel(label);
			done = true;
		}
		else if (response.equals("NON")) {
			JLabel label = new JLabel("Perdu! Nombre de coup épuisé!!",JLabel.CENTER);
			label.setForeground(colorErreur);
			this.addLabel(label);
			done = true;
		}
		else if (response.equals("CLOSED WINDOWS")) {
			JLabel label = new JLabel("Votre allié à fermé la connexion!",JLabel.CENTER);
			label.setForeground(colorErreur);
			this.addLabel(label);
			done = true;
		}
		else if (response.contentEquals("ERREUR")) {
			JLabel label = new JLabel("<html><p>Mot but ou mot similaire présent dans le message,</br><br> veuillez en saisir un autre!</p></html>",JLabel.CENTER);
			label.setForeground(colorErreur);
			this.addLabel(label);
		}
		else if (response.contentEquals("BAD SIZE")) {
			JLabel label = new JLabel("<html><p> Message trop long, maximum 50 caractères!! Veuillez en saisir un autre!</p></html>",JLabel.CENTER);
			label.setForeground(colorErreur);
			this.addLabel(label);		
		}
		else {
			JLabel label = new JLabel(response,JLabel.LEFT);
			label.setForeground(new Color(50,25,150));
			this.addLabel(label);
		}
		getButton().setEnabled(true);
		getPanel().revalidate();
		return !done;
	}
	
	public void actionPerformed(ActionEvent evt) {
		//gère l'action du bouton 
		String reponse = JOptionPane.showInputDialog(null, "Saisir votre message");
		if (reponse!=null) {
			//affiche le message sur la fenêtre du joueur 
			JLabel label = new JLabel("<html><p>Vous:<br>"+ reponse+"</br></p></html>",JLabel.RIGHT);
			label.setForeground(new Color(40,100,40));
			this.addLabel(label);
			//envoie le message à l'allié, message qui sera affiché sur la fenêtre de l'allié
			sendMessage(reponse);
			getButton().setEnabled(false);
			getPanel().revalidate();
		}
	}
	
	//Methods run():
	public void run() {
		try {   
			while (!done) {
				readMessage();
				getPanel().setVisible(true);
			}
			getButton().setEnabled(false);//désactive le bouton lorsque la connexion est fermée
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Exception entrée/sortie : "+e.getMessage());
		} catch (NullPointerException e) {
			System.out.println("Connexion perdue");
		}
		getOut().close();
	}
	
	//Getters and setters:
	public Socket getConnexion() {
		return connexion;
	}
	public void setConnexion(Socket connexion) {
		this.connexion = connexion;
	}

	public BufferedReader getIn() {
		return in;
	}
	public void setIn(BufferedReader in) {
		this.in = in;
	}

	public PrintWriter getOut() {
		return out;
	}
	public void setOut(PrintWriter out) {
		this.out = out;
	}
	
}

