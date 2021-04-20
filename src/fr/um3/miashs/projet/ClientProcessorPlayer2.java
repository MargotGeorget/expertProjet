package fr.um3.miashs.projet;

/*
 * G�re les connexions des joueur2 (dans des Threads s�par�s pour chaque joueur)
 * Contient deux m�thodes: 
 * - la fonction isWin permet de v�rifier si le message envoyer par le joueur 2 
 * contient le mot but et donc si les joueurs ont gagn�s la partie 
 * - la fonction sendMessage qui permet d'envoyer le message � son alli� 
 * apr�s avoir v�rifi� que le mot but n'est pas pr�sent (avec isWin()). Une 
 * fois le message envoy� la fonction v�rifie que le nombre de messages �chang�s 
 * entre les joueurs n'a pas atteint 10 �changes. 
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
				getAllie().getOut().println("<html><p>Alli�:<br>" +msg +"</br></p></html>");
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
	
	//Traitement (lanc� dans un thread s�par�): 
	public synchronized void run() {
		System.err.println("Lancement du traitement de la connexion cliente");
		
		//tant que la connexion est active, on traite les demandes
		while (!getClientSocket().isClosed()) {
			try {
				while (!getCommonAttributes().isCloseConnexion())
					similarsFonctions();
			} catch (SocketException e) {
				System.err.println("La connexion a �t� interrompue !");
			} catch (IOException e) {
				e.printStackTrace();
			}catch (NullPointerException e) {
				System.out.println("Connexion perdue");
			}
			
		}
	}
	
}

