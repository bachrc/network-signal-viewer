package main;

/**
 *
 * @author Yohann Bacha <y.bacha@live.fr>
 */
public enum TypeCourbe {

	NRZ("NRZ"), NRZi("NRZ inversé"), Manchester("Manchester"), ManDiff("Manchester différentiel"), Miller("Miller");

	private final String nom;

	TypeCourbe(String nom) {
		this.nom = nom;
	}
	
	public String getNom() {
		return this.nom;
	}
}
