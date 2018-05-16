/**
 * pParametre correspond au paquetage de gestion de tous les param�tre extrait du fichier de param�trage
 */
package parametre;

import metier.CMatiere;

/**
 * Les param�tres en rapport avec les mati�res
 */
public class CMatiereParam {
	
	private String nomMatiere;
	private float coeffMatiere;
	private String enseignant;
	private String observation;

    /**
     * Constructeur stocke tous les param�tres n�cessaires � la gestion des mati�res
     * @param nom de la mati�re
     * @param coeff de la mati�re
     * @param prof de la mati�re
     */
	public CMatiereParam(String nom,float coeff,String prof){
		this.setNomMatiere(nom);
		this.setCoeffMatiere(coeff);
		this.setEnseignant(prof);
        this.setObservation("");
	}

    /**
     * Indique le nom de la mati�re
     * @return le nom de la mati�re
     */
	public String getNomMatiere() {
		return nomMatiere;
	}

    /**
     * Attribut un nom a la mati�re
     * @param nomMatiere le nom de la mati�re
     */
	public void setNomMatiere(String nomMatiere) {
		this.nomMatiere = nomMatiere;
	}

    /**
     * Indique le coefficient de la mati�re
     * @return le coefficient de la mati�re
     */
	public float getCoeffMatiere() {
		return coeffMatiere;
	}

    /**
     * Attribut le coefficient de la mati�re
     * @param coeffMatiere
     */
	public void setCoeffMatiere(float coeffMatiere) {
		this.coeffMatiere = coeffMatiere;
	}

    /**
     * Indique l'enseignant de la mati�re
     * @return un nom d'enseignant
     */
	public String getEnseignant() {
		return enseignant;
	}

    /**
     * Attribut l'observation a la mati�re
     * @param observation
     */
	public void setObservation(String observation) {
		this.observation = observation;
	}

    /**
     * Indique l'observation de la mati�re
     * @return un nom d'observation
     */
    public String getObservation() {
        return observation;
    }

    /**
     * Attribut l'enseignant a la mati�re
     * @param enseignant
     */
    public void setEnseignant(String enseignant) {
        this.enseignant = enseignant;
    }

    /**
     * Cr�er une mati�re � partir des param�tres de la mati�re
     * @return une mati�re
     */
	public CMatiere createMatiere() {
        CMatiere realMatiere = new CMatiere(this.nomMatiere, 0);
        return realMatiere;
	}

	/**
     * Retourne les informations de l'objet sous forme de string
     * @return une chaine d'information
     */
    @Override
    public String toString() {
        return ("Matiere parametrage, Nom: " + getNomMatiere() + " Coeficient: " + Float.toString(getCoeffMatiere()) + " Enseignant: " + getEnseignant() + " ");
    }
}
