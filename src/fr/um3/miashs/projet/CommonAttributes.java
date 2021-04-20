package fr.um3.miashs.projet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class CommonAttributes {
	
	//Attributes
	private String word="";
	private boolean win = false;
	private boolean closeConnexion = false;
	private int nbMsg = 0;
	
	private ArrayList<String> wordList = readFile();
	
	//Constructor:
	public CommonAttributes () {
		word =wordChoice();
	}

	public ArrayList<String> readFile() {
		//Permet de lire notre fichier txt où se trouve nos mots et de créer une List avec tout les mots
		
		BufferedReader inFile = null;
		ArrayList<String> tab = new ArrayList<String>();
		
		try
		{
			inFile = new BufferedReader(new FileReader(new File("mots.txt"))); //Declaration et affectation d'un flux en lecture
			
			String line = ""; //Lecture du fichier texte ligne par ligne
			
			while((line = inFile.readLine()) != null){
				tab.add(line); //Ajout dans l'ArrayList de chaque mot du fichier texte
			}		
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}		
		finally {
			if(inFile != null) {
				try {
					inFile.close();
				}catch(IOException e){e.printStackTrace();}
			}
		}
		return tab;		
 	}
			
 	public String wordChoice() {
		int a = (int)(Math.random()*(wordList.size()));
		word = wordList.get(a);
		return new String(word.getBytes(),Charset.forName("UTF-8"));
	}
	
	//Getters and Setters:
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}

	public boolean isWin() {
		return win;
	}
	public void setWin(boolean win) {
		this.win = win;
	}

	public boolean isCloseConnexion() {
		return closeConnexion;
	}
	public void setCloseConnexion(boolean closeConnexion) {
		this.closeConnexion = closeConnexion;
	}
	
	public int getNbMsg() {
		return nbMsg;
	}
	public void setNbMsg(int nbMsg) {
		this.nbMsg = nbMsg;
	}
	
	
}
