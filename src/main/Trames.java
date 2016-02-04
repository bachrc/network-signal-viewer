package main;

import java.util.ArrayList;

/**
 *
 * @author Yohann Bacha <y.bacha@live.fr>
 */
public class Trames {
	
	public static ArrayList<Couple> courbe(String trame, TypeCourbe type) throws Exception {
		switch(type) {
			case NRZ :
				return nrz(trame); 
			case NRZi :
				return nrzi(trame); 
			case Manchester :
				return manchester(trame); 
			case ManDiff :
				return mandiff(trame); 
			case Miller :
				return miller(trame); 
			default :
				throw new Exception("Erreur : Courbe non prise en charge.");
		}
	}
	
	private static ArrayList<Couple> nrz(String trame) throws Exception{
		ArrayList<Couple> retour = new ArrayList<>();
		
		for(char c : trame.toCharArray()) 
			if(c == '0') 
				retour.add(new Couple(0,0));
			else if(c == '1')
				retour.add(new Couple(1,1));
			else
				throw new Exception("Erreur : Caractère dans la trame invalide");
		
		return retour;
	}
	
	private static ArrayList<Couple> nrzi(String trame) throws Exception{
		ArrayList<Couple> retour = new ArrayList<>();
		
		for(char c : trame.toCharArray())
			if(c == '0') 
				retour.add(new Couple(1,1));
			else if(c == '1')
				retour.add(new Couple(0,0));
			else
				throw new Exception("Erreur : Caractère dans la trame invalide");
		
		return retour;
	}
	
	private static ArrayList<Couple> manchester(String trame) throws Exception {
		ArrayList<Couple> retour = new ArrayList<>();
		for(int i = 0; i < trame.length(); i++) {
			char val = trame.charAt(i);
			if(val == '0')
				retour.add(new Couple(0,1));
			else if(val == '1')
				retour.add(new Couple(1,0));
			else
				throw new Exception("Erreur : Caractère dans la trame invalide");
		}
		
		return retour;
	}
	
	private static ArrayList<Couple> mandiff(String trame) throws Exception {
		ArrayList<Couple> retour = new ArrayList<>();
		for(int i = 0; i < trame.length(); i++) {
			if(i == 0) retour.add(new Couple(0,1));
			else {
				int lastVal = retour.get(i-1).milieu;
				
				if(trame.charAt(i) == '1')
					retour.add(new Couple(lastVal, (lastVal + 1)%2));
				else if (trame.charAt(i) == '0')
					retour.add(new Couple((lastVal + 1)%2, lastVal));
				else
					throw new Exception("Erreur : Caractère dans la trame invalide");
			}
		}
		
		return retour;
	}
	
	private static ArrayList<Couple> miller(String trame) throws Exception {
		ArrayList<Couple> retour = new ArrayList<>();
		for(int i = 0; i < trame.length(); i++) {
			char last = (i == 0 ? 1 : trame.charAt(i-1));
			int lastVal = (i == 0 ? 1 : retour.get(i-1).milieu);
			if(trame.charAt(i)== '1') {
				retour.add(new Couple(lastVal, (lastVal + 1)%2));
			} else if (trame.charAt(i) == '0') {
				if(last == '0')
					retour.add(new Couple((lastVal + 1)%2, (lastVal + 1)%2));
				else
					retour.add(new Couple(lastVal, lastVal));
			}
			else
				throw new Exception("Erreur : Caractère dans la trame invalide");
				
		}
		
		return retour;
	}
}
