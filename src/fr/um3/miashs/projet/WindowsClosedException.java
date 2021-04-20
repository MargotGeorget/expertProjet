package fr.um3.miashs.projet;

//Exception personnalis�e qui permet d'informer l'alli� et de couper la connexion 
//lorsqu'un joueur ferme sa fen�tre de jeu  

public class WindowsClosedException extends Exception {
	
	public WindowsClosedException (ClientProcessor cp) {
		cp.getAllie().getOut().println("CLOSED WINDOWS");
		cp.getCommonAttributes().setCloseConnexion(true);
	}
	
}
