package fr.um3.miashs.projet;

/* 
 * Cr�er le serveur et le met en attente de connexion
 */

public class ServerGameTest {
	public static void main (String[] args) {
		GameServer s = new GameServer();
		s.open();
		System.out.println("Serveur initialis�.");

	}
}
