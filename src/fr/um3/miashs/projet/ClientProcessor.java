package fr.um3.miashs.projet;

/*
* Classe m�re des classes ClientProcessorPlayer1 et Player2
* Permet d'initialiser leurs attributs et m�thodes communes 
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class ClientProcessor {

	//Attributes:
	private Socket clientSocket = null; //liaison avec le client 
	private BufferedReader in = null; //r�ception des messages du client
	private PrintWriter out = null; //envoi des r�ponses au client
	private ClientProcessor allie = null; //autre client avec qui le joueur �change ses messages et joue
	
	private CommonAttributes commonAttributes; //D�finis plusieurs attributs. L'objectif est de cr�er une seule instance de cette
	//classe qui sera partag�e par le joueur et son alli� afin que la modification d'un attribut soit applicable pour les deux joueurs
	
	//Constructor:
	public ClientProcessor (Socket s, ClientProcessor allie) throws IOException {
		clientSocket = s;
		setAllie(allie);
		out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true);
		in  = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        if (allie!=null) {
        	commonAttributes = getAllie().getCommonAttributes(); //r�cup�re les m�mes attributs communs que son alli�
        	getAllie().setAllie(this); //J'initialise l'attibut "allie" de mon allie comme �tant moi (sinon il resterait � null)
       }
        else 
        	commonAttributes = new CommonAttributes(); //n'a pas encore d'alli� alors cr�er une instance des attributs communs 
        //� partager avec son futur alli�
	}
	
	public ClientProcessor (Socket s) throws IOException {
		this(s, null);
	}

	public ClientProcessor() {
		// utilis� pour les tests unitaires
	}
	
	//Methods: 
	public abstract void sendMessage(String msg) throws IOException;

	public boolean isGoodSize (String s) {
		//permet de v�rifier la taille des messages: le nombre de caract�res doit �tre inf�rieur � 50
		return (s.length()<=50);
	}
	
	public boolean WindowsClosedDetection(String message) {
		//V�rifie que le message re�u du client n'est pas d� � la fermeture de la fen�tre de jeu 
		if (message.equals("CLOSED WINDOWS"))
			return true;
		else 
			return false;
	}
	
	public synchronized void similarsFonctions () throws IOException {
		//Utiliser dans les m�thodes run() des clientProcessor Player1 et Player2 
		while (commonAttributes.getNbMsg()<10 && !commonAttributes.isWin()) {
			String msg = getIn().readLine();
			
			try {
				if (!isGoodSize(msg)) {
					getOut().println("BAD SIZE");
					getOut().flush();
				}
				else if (WindowsClosedDetection(msg)) { throw new WindowsClosedException(this); }
				else {sendMessage(msg);} //fonction d�finie dans les classes clientProcessor Player1 et Player2
				//g�re les actions diff�rentes
				
			} catch (WindowsClosedException e) { } 	
		
			if (commonAttributes.isCloseConnexion()) {
				System.err.println("COMMANDE CLOSE DETECTEE !");
				setIn(null);
				setOut(null);
				getAllie().setIn(null);
				getAllie().setOut(null);
				getClientSocket().close();
			}
		}
	}
	
	//Getters and setters:
	public void setAllie(ClientProcessor allie) {
		this.allie = allie;
	}
	public void setClientSocket(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}
	public void setIn(BufferedReader in) {
		this.in = in;
	}
	public void setOut(PrintWriter out) {
		this.out = out;
	}
	public void setCommonAttributes(CommonAttributes commonAttributes) {
		this.commonAttributes = commonAttributes;
	}
	
	public ClientProcessor getAllie() {
		return allie;
	}
	public Socket getClientSocket() {
		return clientSocket;
	}
	public BufferedReader getIn() {
		return in;
	}
	public PrintWriter getOut() {
		return out;
	}
	public CommonAttributes getCommonAttributes() {
		return commonAttributes;
	}

}


