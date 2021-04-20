package fr.um3.miashs.projet;

//Exception personnalisée qui permet d'informer l'allié et de couper la connexion 
//lorsqu'un joueur ferme sa fenêtre de jeu  

public class WindowsClosedException extends Exception {
	
	public WindowsClosedException (ClientProcessor cp) {
		cp.getAllie().getOut().println("CLOSED WINDOWS");
		cp.getCommonAttributes().setCloseConnexion(true);
	}
	
}
