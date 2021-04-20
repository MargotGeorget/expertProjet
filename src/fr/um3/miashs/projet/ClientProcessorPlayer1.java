package fr.um3.miashs.projet;

/*
 * G�re les connexions des joueur1 (dans des Threads s�par�s pour chaque joueur)
 *  Permet de communiquer aux joueur1 le mot but et permet d'envoyer des messages du joueur1 vers l'alli�: message d�grad� selon les 
 *  deux m�thodes (m�thodes choisi de mani�re al�atoire) et ne permet pas au joueur1 d'envoyer un message contenant le mot 
 *  but ou mot similaire (demande au joueur de saisir un autre message)
 */

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class ClientProcessorPlayer1 extends ClientProcessor implements Runnable  {
	
	//Constructors: 
	public ClientProcessorPlayer1 (Socket s, ClientProcessor allie) throws IOException {
		super(s, allie);		 
	}
	
 	public ClientProcessorPlayer1 (Socket s) throws IOException{
		this(s,null);
	}

	public ClientProcessorPlayer1() {
		// Utiliser pour les tests unitaires
	}
	
	//Methods:
	public String messageDegradationWord (String s) {
		//Degrade le message en supprimant des mots 
		
		//met tous les mots du message dans un tableau	
		ArrayList<String> list = new ArrayList<String>();
		String tab[] = s.split("\\s");
		//met chaque �l�ments du tableau dans une ArrayList pour qu'il soit plus simple de supprimer 
		//un de ces �l�ments 
		for (int i =0; i<tab.length; i++)
			list.add(tab[i]);
		// cr�er un entier "a" correspondant � environ 30% du nombre de mots puis suprimme "a" mots 
		//de fa�on al�atoire 
		int a = (int)(list.size()*0.3);
		for (int i = 0;i<=a;i++) {
			int b = (int)(Math.random()*list.size());
			list.remove(b);
		}
		//remet les mots restant dans une chaine de caract�re qui sera envoy�e 
		String str = "";
		for (int i=0; i<list.size();i++)
			str += list.get(i)+" ";
		return str;
	}
	
	public String messageDegradationLetters (String s) {
		//Degrade le message en supprimant des lettres
		int a = (int)(s.length()*0.3); //entier "a" correspondant a environ 30% des lettres
		//supprime "a" lettres de fa�on al�atoire 
		for (int i=0;i<=a;i++) {
			int b = (int)(Math.random()*s.length());
			String str = s.substring(0, b)+s.substring(b+1, s.length());
			s = str;
		}
		return s;
	}
	
	public boolean wordPresence (String s) {
		//V�rifie si le mot but est pr�sent dans le message 
		return s.contains(getCommonAttributes().getWord());
	}
	
	public boolean similarWordPresence(String msg) { 
		//Si tout un des mots de msg est "similaire" au mot but, renvoie TRUE, FALSE sinon.
		//Faille :  Ne prend pas en compte les accents, donc si le mot = �t� et msg = ete, aucun probleme n'est detecte.

		String tab[] = msg.split("\\s");
		boolean isSimilar = false;
		//on effectue un premier test qui v�rifie si un mot commen�ant par les m�mes lettres que le mot but est pr�sent 
		int s = (int)(getCommonAttributes().getWord().length()/2) +1;
		if (msg.contains(getCommonAttributes().getWord().substring(0,s))) {
			isSimilar = true;
		}
		
		//le test pr�c�dent � peut �tre r�tourner false mais le mot peut quand m�me �tre similaire alors on fait une deuxi�me v�rification 
		//on regarde le nombre de lettres communes dans tout le mot et pas uniquement les premi�res lettres

		double b = getCommonAttributes().getWord().length()*(0.9);
		String letters[]; 
		int k =0;
		while (k<tab.length && !isSimilar) {
			int a = 0;
			letters = new String[Math.max(tab[k].length(), getCommonAttributes().getWord().length())];
			for(int i=0; i < tab[k].length(); i++) {
				for(int j=0; j< getCommonAttributes().getWord().length(); j++) {
					if(String.valueOf(tab[k].charAt(i)).equals(String.valueOf(getCommonAttributes().getWord().charAt(j))) && !verifTab(letters, String.valueOf(getCommonAttributes().getWord().charAt(j)))) {
						letters[a] = String.valueOf(getCommonAttributes().getWord().charAt(j));
						a+=1 ;
					}
				}
			}
			if(a >= b)
				isSimilar = true;
			else
				isSimilar = false;
			k++;
		}

		return isSimilar;
	}


	public static boolean verifTab(String tab[], String letter) {
		boolean flag = false;
		int i = 0;
		while(flag == false && i < tab.length) {
			if(tab[i] == letter)
				flag = true;
			i+=1;
		}
		return flag;
	}
	
	
	public void sendMessage(String msg) throws IOException {
		try {
			if (wordPresence(msg) || similarWordPresence (msg) ) {throw new WordPresenceException();}

			else {
				String tab[] = msg.split("\\s");
				//degrade le message en fonction des mots ou des lettres
				double random = Math.random();
				String degradedMsg;
				if (random<0.5 && tab.length>1) //v�rifie que le msg contient au moins 2mots pour �viter de supprimer 
					//le seul mot du message et que le joueur2 re�oive un message vide
					degradedMsg = messageDegradationWord(msg);
				else 
					degradedMsg = messageDegradationLetters(msg);
				getAllie().getOut().println("<html><p>Alli�:<br>" +degradedMsg +"</br></p></html>");
				getAllie().getOut().flush();
				getCommonAttributes().setNbMsg(getCommonAttributes().getNbMsg()+1);
		}
		}catch (WordPresenceException e) {
			getOut().println("ERREUR");
			getOut().flush();
			similarsFonctions();
		}
	}
			
	//Traitement (lanc� dans un thread s�par�): 
	public void run() {
		System.err.println("Lancement du traitement de la connexion cliente");
		
		//tant que la connexion est active, on traite les demandes
		try {
			while (getAllie()==null) {
				//Tant que le joueur 1 n'a pas d'alli�, le message "ATTENTE" est envoy� ce qui permet de bloquer 
				//l'envoie de message du joueur 1 sur sa fen�tre de jeu et donc d'�viter que le joueur envoie un 
				//message vers un pointeur null
				getOut().println("ATTENTE");
				getOut().flush();
			}
			while (!getClientSocket().isClosed()) {
				//Une fois que l'alli� est initialis�, on communique le mot but au joueur 1
				getOut().println("Le mot but est: "+getCommonAttributes().getWord());
				getOut().flush();
				
				//Puis l'�change des messages peut se faire 
				while (!getCommonAttributes().isCloseConnexion())
					similarsFonctions();
			}
		} catch (SocketException e) {
			System.err.println("La connexion a �t� interrompue !");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("Connexion perdue");
		}

	}
	
}
