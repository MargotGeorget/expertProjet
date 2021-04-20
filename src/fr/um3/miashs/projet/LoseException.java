package fr.um3.miashs.projet;

// Exception personnalisée qui permet de couper la connexion lorsque la partie est perdue 

public class LoseException extends Exception{
	
	public LoseException() {
		super();
	}
}
