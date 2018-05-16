/**
 * pParametre correspond au paquetage de gestion de tous les param�tre extrait du fichier de param�trage
 */
package parametre;

import java.util.ArrayList;

/**
 * Les param�tres en rapport avec les UE
 */
public class CUEParam {

	private String nomUE;
	private float ECTS;
	private float coeffUE;
	private ArrayList<CMatiereParam> matieres = new ArrayList<CMatiereParam>();

    /**
     * Constructeur stocke tous les param�tres n�cessaires � la gestion des UE
     * @param nom de l'UE
     * @param credit de l'UE
     * @param coeff de l'UE
     * @param listeMatiere liste des mati�res de l'UE
     */
	public CUEParam(String nom, float credit, float coeff, ArrayList<CMatiereParam> listeMatiere){
		this.setNomUE(nom);
		this.setECTSUE(credit);
		this.setCoeffUE(coeff);
        this.matieres = listeMatiere;
	}

    /**
     * Constructeur stocke tous les param�tres n�cessaires � la gestion des UE sans la liste des mati�res de l'UE
     * @param nom de l'UE
     * @param credit de l'UE
     * @param coeff de l'UE
     */
	public CUEParam(String nom, float credit, float coeff){
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
     * Retourne la liste des mati�res associ�es � l'UE
     * @return la liste des mati�res
     */
    public ArrayList<CMatiereParam> getMatieresUE() {
        return this.matieres;
    }

    /**
     * Attribut une liste de mati�res a l'UE
     * @param listeMatiere liste de mati�res associ� � l'UE
     */
    public void setMatieresUE(ArrayList<CMatiereParam> listeMatiere) {
        for (CMatiereParam matiere : listeMatiere) {
            this.matieres.add(matiere);
        }
    }

    /**
     * Ajoute une mati�re a la liste des mati�res de l'UE
     * @param matiere la mati�re a ajout�
     */
    public void setMatieresUE(CMatiereParam matiere){
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
        for (CMatiereParam matiere : matieres) {
            liste_matiere_string += matiere.toString();
        }
        return ("Unite d'enseignement parametrage{ Nom: " + getNomUE() + " Credit:" + getECTSUE() + " Coeficient: " + getCoeffUE() + " Liste matieres: {" + liste_matiere_string + "}} " );
    }
	
	//manque ajouterMatiere, supprimerMatiere, modifierMatiere (fichier xml)
}
