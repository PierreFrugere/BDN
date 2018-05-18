/*
 * Matière.java			18/05/2018
 * 3iL - Projet Bulletin de Note - 2018
 */
package metier;

/**
 * Classe représentant une matière de cours de l'école 3iL
 * @author WilliamHenry, BenjaminMazoyer & PierreFrugere
 * @version 2.0
 */
public class Matiere {

	/** le nom de la matière */
	private String nomMatiere;
	
	/** la note obtenu à la matière */
	private float noteMatiere;
	
	/**
	 * constructeur de la classe Matiere
	 * @param nom
	 * @param note
	 */
	public Matiere(String nom, float note) {
		this.nomMatiere = nom;
		this.noteMatiere = note;
	}
	
	/**
	 * Accesseur de l'attribut nomEleve
	 * @return nom de la matiere
	 */
	public String getNomMatiere() {
		return (this.nomMatiere);
	}
	
	/**
	 * modifieur de l'attribut nomEleve
	 * @param nom
	 */
	public void setNomMatier(String nom){
		this.nomMatiere = nom;
	}

	
	/**
	 * Accesseur de l'attribut nomEleve
	 * @return nom de la matiere
	 */
	public float getNoteMatiere() {
		return (this.noteMatiere);
	}
	
	/**
	 * modifieur de l'attribut noteMatiere
	 * @param note
	 */
	public void setNoteMatiere(float note){
		this.noteMatiere = note;
	}

	@Override
	/**
	 * Méthode qui permet d'afficher une matiere
	 * @return une chaine de caractère qui représente une matiere
	 * @Override
	 */
	public String toString() {
		return ("Matiere , Nom: " + getNomMatiere() + " Note: " + Float.toString(getNoteMatiere()) + " ");
	}
}
