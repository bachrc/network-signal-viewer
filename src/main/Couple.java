package main;

/**
 *
 * @author Yohann Bacha <y.bacha@live.fr>
 */
public class Couple {
	public int debut;
	public int milieu;

	public Couple(int debut, int milieu) {
		this.debut = debut;
		this.milieu = milieu;
	}

	public String toString() {
		return "(" + debut + ", " + milieu + ")";
	}
}
