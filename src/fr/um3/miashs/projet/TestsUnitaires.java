package fr.um3.miashs.projet;

public class TestsUnitaires {
	
	public TestsUnitaires () {
		
		//Test des methodes de la classe ClientProcessorPlayer1:
		ClientProcessorPlayer1 cp1 = new ClientProcessorPlayer1();
		cp1.setCommonAttributes(new CommonAttributes());
		cp1.getCommonAttributes().setWord("boulot");
		
		if (cp1.wordPresence("il faut boulot")==false) {System.exit(1);}
		
		if (cp1.similarWordPresence("il faut boulit")==false) {System.exit(1);}
		if (cp1.similarWordPresence("il faut bulot")==false) {System.exit(1);}

		if (cp1.isGoodSize("Taille correcte")==false) {System.exit(1);}
		if (cp1.isGoodSize("Chaine de caract�re d�passant les 50caract�res. Taille trop longue")==true) {System.exit(1);}
		
		if (cp1.messageDegradationLetters("Chaine de caract�re � d�grader")=="Chaine de caract�re � d�grader"){System.exit(1);}
		
		if (cp1.messageDegradationWord("Chaine de caract�re � d�grader")=="Chaine de caract�re � d�grader"){System.exit(1);}
		
		//Test des methodes de la classe ClientProcessorPlayer2:
		ClientProcessorPlayer2 cp2 =new ClientProcessorPlayer2();
		cp2.setCommonAttributes(new CommonAttributes());
		cp2.getCommonAttributes().setWord("voiture");
		
		if (cp2.isWin("la voiture roule")==false) {System.exit(1);}
		if (cp2.isWin("autres")==true) {System.exit(1);}
		
		System.out.println("Les tests ont �t� eff�ctu�s correctement");
	}

	public static void main(String[] args) {
		TestsUnitaires u = new TestsUnitaires ();
		
	}

}
