/*
 * UEParam.java			18/05/2018
 * 3iL - Projet Bulletin de Note - 2018
 */
package parametre;

import java.util.ArrayList;

/**
 * Classe représentant les paramètres en rapport avec les UE
 * @author WilliamHenry, BenjaminMazoyer & PierreFrugere
 * @version 2.0
 */
public class UEParam {

	private String nomUE;
	private float ECTS;
	private float coeffUE;
	private ArrayList<MatiereParam> matieres = new ArrayList<MatiereParam>();

    /**
     * Constructeur stocke tous les paramètres nécessaires à la gestion des UE
     * @param nom de l'UE
     * @param credit de l'UE
     * @param coeff de l'UE
     * @param listeMatiere liste des matières de l'UE
     */
	public UEParam(String nom, float credit, float coeff, ArrayList<MatiereParam> listeMatiere){
		this.setNomUE(nom);
		this.setECTSUE(credit);
		this.setCoeffUE(coeff);
        this.matieres = listeMatiere;
	}

    /**
     * Constructeur stocke tous les paramétres nécessaires à la gestion des UE sans la liste des matières de l'UE
     * @param nom de l'UE
     * @param credit de l'UE
     * @param coeff de l'UE
     */
	public UEParam(String nom, float credit, float coeff){
		this.setNomUE(nom);
		this.setECTSUE(credit);
		this.setCoeffUE(coeff);
		this.matieres = null;
	}

    /**
     * Retourne le nom de l'UE
     * @return nom de l'UE
     */
	public String getNomUE() {
		return nomUE;
	}

    /**
     * Attribut un nom � l'UE
     * @param nomUE nom de l'UE
     */
	public void setNomUE(String nomUE) {
		this.nomUE = nomUE;
	}

    /**
     * Retourne les cr�dits de l'UE
     * @return cr�dit de l'UE
     */
	public float getECTSUE() {
		return ECTS;
	}

    /**
     * Attribut des cr�dits a un UE
     * @param eCTS cr�dit de l'UE
     */
	public void setECTSUE(float eCTS) {
		ECTS = eCTS;
	}

    /**
     * Retourne le coefficient de l'UE
     * @return coefficient de l'UE
     */
	public float getCoeffUE() {
		return coeffUE;
	}

    /**
     * Attribut le coefficient de l'UE
     * @param coeffUE coefficient de l'UE
     */
	public void setCoeffUE(float coeffUE) {
		this.coeffUE = coeffUE;
	}

    /**
     * Retourne la liste des matières associées à l'UE
     * @return la liste des matière
     */
    public ArrayList<MatiereParam> getMatieresUE() {
        return this.matieres;
    }

    /**
     * Attribut une liste de matière a l'UE
     * @param listeMatiere liste de matière associé à l'UE
     */
    public void setMatieresUE(ArrayList<MatiereParam> listeMatiere) {
        for (MatiereParam matiere : listeMatiere) {
            this.matieres.add(matiere);
        }
    }

    /**
     * Ajoute une matiére a la liste des matière de l'UE
     * @param matiere la matiére à ajouter
     */
    public void setMatieresUE(MatiereParam matiere){
        this.matieres.add(matiere);
    }

    /**
     * Retourne les informations de l'objet sous forme de string
     * @return une chaine d'information
     */
    @Override
    public String toString() {
        String liste_matiere_string;
        liste_matiere_string = "";
        for (MatiereParam matiere : matieres) {
            liste_matiere_string += matiere.toString();
        }
        return ("Unite d'enseignement parametrage{ Nom: " + getNomUE() + " Credit:" + getECTSUE() + " Coeficient: " + getCoeffUE() + " Liste matieres: {" + liste_matiere_string + "}} " );
    }
	
	//manque ajouterMatiere, supprimerMatiere, modifierMatiere (fichier xml)
}
