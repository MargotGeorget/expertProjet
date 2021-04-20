 package fr.um3.miashs.projet;

public class Test2 {
	public static void main (String []args) {
	Thread t = new Thread (new ClientConnexion("127.0.0.1",5000));
	//Thread t1 = new Thread (new ClientConnexion("127.0.0.1",5000));
	t.start();
	//t1.start();
	}
}
