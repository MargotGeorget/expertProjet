package fr.um3.miashs.projet;

public class Test1 {

	public static void main(String[] args) {
		Thread t = new Thread (new ClientConnexion("127.0.0.1",5000));
		t.start();
	}

}
