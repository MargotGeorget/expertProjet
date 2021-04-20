package fr.um3.miashs.projet;

/*
 * Classe qui gère un serveur et lui permet d'attendre des connexions. 
 * L'attribut 'nbConnexion' permet de créer un clientProcessor différent en fonction du nombre de client: le jeu se joue
 * avec un joueur1 et un joueur2 qui seront connectés entre eux et qui n'effecturont pas exactement les mêmes actions
 * Lorsque qu'il y a un nombre de connexion impair, c'est un joueur1 qui est créé. Il est stocké dans une variable tampon 'tmp'
 * ce qui permettra lors de la prochaine connexion d'attribuer comme allié au client (qui sera un joueur2) le dernier client 
 * (joueur1) qui c'est connecté
 */

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class GameServer {
	
	//Attributes:
	private ServerSocket server = null;
	private boolean isRunning = true;
	private int nbConnexion = 1;
	
	
	//Constructors: 
	public GameServer (int port, String host) {
		try {
			server = new ServerSocket (port, 100, InetAddress.getByName(host));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public GameServer () {
		this(5000,"127.0.0.1");
	}
	
	
	//On lance notre serveur:
	public void open() {
		Thread t = new Thread (new Runnable() {
			public synchronized void run() {
				ClientProcessorPlayer1 tmp = null;
				while (isRunning) {
					try {
						Socket s = server.accept();
						System.out.println(nbConnexion + " connexion cliente reçue.");
						if (nbConnexion%2!=0) {
							tmp= new ClientProcessorPlayer1(s);
							Thread t = new Thread (tmp);
							t.start();
						}
						else {
							Thread t = new Thread (new ClientProcessorPlayer2(s, tmp));
							tmp = null;
							t.start();
						}
						nbConnexion++;
					} catch (IOException e ) {
						e.printStackTrace();
					}
				}
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
					server = null;;
				}
			}
		});
		
		t.start();
	}
	
	public void close() {
		isRunning = false;
	}
}