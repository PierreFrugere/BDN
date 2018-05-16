/**
 * 
 */
package metier;

public class CMatiere {

	/** le nom de la mati�re */
	private String nomMatiere;
	
	/** la note obtenu � la mati�re */
	private float noteMatiere;
	
	/**
	 * constructeur de la classe CMatiere
	 * @param nom
	 * @param note
	 */
	public CMatiere(String nom, float note) {
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
	 * M�thode qui permet d'afficher une matiere
	 * @return une chaine de caract�re qui repr�sente une matiere
	 * @Override
	 */
	public String toString() {
		return ("Matiere , Nom: " + getNomMatiere() + " Note: " + Float.toString(getNoteMatiere()) + " ");
	}
}
