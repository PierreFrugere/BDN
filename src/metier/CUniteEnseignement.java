/**
 * 
 */
package metier;

import java.util.ArrayList;

import parametre.CUEParam;

public class CUniteEnseignement {

	/** nom de l'UE */
	private String nomUE;
	
	/** moyenne de l'UE */
	private float moyenneUE;
	
	/** liste de mati�res */
	private ArrayList<CMatiere> matieres; 
	
	private CUEParam paramUE;
	
	/**
	 * Constructeur de la classe CUniteEnseignement
	 * @param nomUE
	 */
	public CUniteEnseignement(String nomUE){
		this.nomUE = nomUE;
        this.moyenneUE = 0;
        this.matieres = null;
	}

	/**
	 * Constructeur de la classe CUniteEnseignement 
	 * @param nomUE
	 * @param matieres
	 */
	public CUniteEnseignement(String nomUE,ArrayList<CMatiere> matieres,CUEParam ue){
		this.nomUE = nomUE;
		this.moyenneUE = 0;
		this.matieres =  matieres;
		this.paramUE = ue;
	}
	
	/**
	 * Accesseur de l'attribut nomEleve
	 * @return nom de la matiere
	 */
	public String getNomUE(){
		return(this.nomUE);
	}
	
	/**
	 * modifieur de l'attribut nomUE
	 * @param nom
	 */
	public void setNomUE(String nom){
		this.nomUE = nom;
	}

	/**
	 * Cette m�thode permet de calculer la moyenne de l'UE
	 * @return moyenne de l'UE
	 */
	public float calculerMoyenne(){
		return (this.moyenneUE);

	}

	/**
	 * Cette m�thode permet d'ajouter une liste de mati�res
	 * @param listeMatiere
	 */
	public void ajouterMatiere(ArrayList<CMatiere> listeMatiere){
        for (CMatiere matiere : listeMatiere) {
            this.matieres.add(matiere);
        }
	}

	/**
	 * Cette m�thode permet d'ajouter une mati�re
	 * @param matiere
	 */
    public void ajouterMatiere(CMatiere matiere){
        this.matieres.add(matiere);
    }

    /**
	 * Accesseur de l'attribut moyenneUE
	 * @return moyenne de l'UE
	 */
    public float getMoyenneUE() {
        return this.moyenneUE;
    }

    public ArrayList<CMatiere> getMatieres(){
    	return(this.matieres);
    }
    
    @Override
	/**
	 * Cette m�thode permet d'afficher une Unit� d'Enseignament
	 * @return une chaine de caract�re qui repr�sente une UE
	 * @Override
	 */
	public String toString() {
		String liste_matiere_string;
		liste_matiere_string = "";
		for (CMatiere matiere : matieres) {
			liste_matiere_string += matiere.toString();
		}
		return ("Unite d'enseignement { Nom: " + getNomUE() + " Moyenne:" + Float.toString(getMoyenneUE()) + " Liste matieres: {" + liste_matiere_string + "}} " );
	}

	public CUEParam getParamUE() {
		return paramUE;
	}

	public void setParamUE(CUEParam paramUE) {
		this.paramUE = paramUE;
	}

}
