package fr.um3.miashs.projet;

/*
 * Gère les connexions des joueur2 (dans des Threads séparés pour chaque joueur)
 * Contient deux méthodes: 
 * - la fonction isWin permet de vérifier si le message envoyer par le joueur 2 
 * contient le mot but et donc si les joueurs ont gagnés la partie 
 * - la fonction sendMessage qui permet d'envoyer le message à son allié 
 * après avoir vérifié que le mot but n'est pas présent (avec isWin()). Une 
 * fois le message envoyé la fonction vérifie que le nombre de messages échangés 
 * entre les joueurs n'a pas atteint 10 échanges. 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class ClientProcessorPlayer2 extends ClientProcessor implements Runnable {
	
	//Constructors: 
	public ClientProcessorPlayer2 (Socket s) throws IOException {
		super(s);
	}
	
	public ClientProcessorPlayer2 (Socket s, ClientProcessor allie) throws IOException {
		super(s, allie);
	}
	
	public ClientProcessorPlayer2 () {}
	
	//Methods: 
	
	//Methods: 
	
	public void sendMessage(String msg) throws IOException {
		try {
			if (isWin(msg)) {
				getCommonAttributes().setWin(true);
				getOut().println("OK");
				getOut().flush();
				getAllie().getOut().println("OK");
				getAllie().getOut().flush();
				//getCommonAttributes().setCloseConnexion(true);
			}

			else {
				getAllie().getOut().println("<html><p>Allié:<br>" +msg +"</br></p></html>");
				getAllie().getOut().flush();
				getCommonAttributes().setNbMsg(getCommonAttributes().getNbMsg()+1);
				if  (getCommonAttributes().getNbMsg()>=10) {throw new LoseException();}
			}
			
		} catch (LoseException e) {
			getOut().println("NON");
			getOut().flush();
			getAllie().getOut().println("NON");
			getAllie().getOut().flush(); 
		}
	}
		
	public boolean isWin(String s) {
		return s.contains(getCommonAttributes().getWord());
	}
	
	//Traitement (lancé dans un thread séparé): 
	public synchronized void run() {
		System.err.println("Lancement du traitement de la connexion cliente");
		
		//tant que la connexion est active, on traite les demandes
		while (!getClientSocket().isClosed()) {
			try {
				while (!getCommonAttributes().isCloseConnexion())
					similarsFonctions();
			} catch (SocketException e) {
				System.err.println("La connexion a été interrompue !");
			} catch (IOException e) {
				e.printStackTrace();
			}catch (NullPointerException e) {
				System.out.println("Connexion perdue");
			}
			
		}
	}
	
}

