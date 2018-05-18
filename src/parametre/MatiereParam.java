/*
 * MatiereParam.java			18/05/2018
 * 3iL - Projet Bulletin de Note - 2018
 */
package parametre;

import metier.Matiere;

/**
 * Classe représentant les paramètres en rapport avec les matières
 * @author WilliamHenry, BenjaminMazoyer & PierreFrugere
 * @version 2.0
 */
public class MatiereParam {
	
	private String nomMatiere;
	private float coeffMatiere;
	private String enseignant;
	private String observation;

    /**
     * Constructeur stocke tous les paramètres nécessaires à la gestion des matiéres
     * @param nom de la matière
     * @param coeff de la matière
     * @param prof de la matière
     */
	public MatiereParam(String nom, float coeff, String prof){
		this.setNomMatiere(nom);
		this.setCoeffMatiere(coeff);
		this.setEnseignant(prof);
        this.setObservation("");
	}

    /**
     * Indique le nom de la matière
     * @return le nom de la matière
     */
	public String getNomMatiere() {
		return nomMatiere;
	}

    /**
     * Attribut un nom a la matière
     * @param nomMatiere le nom de la matière
     */
	public void setNomMatiere(String nomMatiere) {
		this.nomMatiere = nomMatiere;
	}

    /**
     * Indique le coefficient de la matière
     * @return le coefficient de la matière
     */
	public float getCoeffMatiere() {
		return coeffMatiere;
	}

    /**
     * Attribut le coefficient de la matière
     * @param coeffMatiere
     */
	public void setCoeffMatiere(float coeffMatiere) {
		this.coeffMatiere = coeffMatiere;
	}

    /**
     * Indique l'enseignant de la matière
     * @return un nom d'enseignant
     */
	public String getEnseignant() {
		return enseignant;
	}

    /**
     * Attribut l'observation a la matière
     * @param observation
     */
	public void setObservation(String observation) {
		this.observation = observation;
	}

    /**
     * Indique l'observation de la matière
     * @return un nom d'observation
     */
    public String getObservation() {
        return observation;
    }

    /**
     * Attribut l'enseignant a la matière
     * @param enseignant
     */
    public void setEnseignant(String enseignant) {
        this.enseignant = enseignant;
    }

    /**
     * Créer une matière à partir des paramétres de la matière
     * @return une matière
     */
	public Matiere createMatiere() {
        Matiere realMatiere = new Matiere(this.nomMatiere, 0);
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
