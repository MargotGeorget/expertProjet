
public class Methode {
	
	public static boolean similarWord(String msg, String target) { 
		//Si tout un des mots de msg est "similaire" ра target, renvoie TRUE, FALSE sinon.
		//Faille :  Ne prend pas en compte les accents, donc si target = щtщ et cdc = ete, aucun probleme n'est detecte.

		String tab[] = msg.split("\\s");
		boolean isSimilar = false;
		int k = 0;
		while (k<tab.length && !isSimilar) {
			int a = 0;
			double b = target.length()*(0.9);
			System.out.println(target.length()+"---"+ b);
			for(int i=0; i < tab[k].length(); i++) {
				for(int j=0; j< target.length(); j++) {
					if(String.valueOf(tab[k].charAt(i)).equals(String.valueOf(target.charAt(j))))
						a+=1 ;
				}
			}
			System.out.println("test mot "+tab[k]+" a="+a);
			if(a >= b)
				isSimilar= true;
			else
				isSimilar= false;
			k++;
		}
		return isSimilar;
	}
}
