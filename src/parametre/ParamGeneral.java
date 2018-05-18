/*
 * ParamGeneral.java			18/05/2018
 * 3iL - Projet Bulletin de Note - 2018
 */
package parametre;

/**
 * Classe représentant les paramètres généraux globaux à tous les UE
 * @author WilliamHenry, BenjaminMazoyer & PierreFrugere
 * @version 2.0
 */
public class ParamGeneral {

    /** Note minimale pour validation UE */
	private float validationUE;
    /** Note minimale pour validation matiere */
    private float validationMatiere;
    /** Note minimale pour pouvoir compenser matiere */
	private float noteMini;

	
	/**
	 * Constructeur qui requière les informations globales des UE
	 * @param valid note de validation nécessaire pour valider l'UE
	 * @param min note de minimale nécessaire pour pouvoir compenser l'UE
	 */
	public ParamGeneral(float valid, float min, float valMat){
		this.setValidationUE(valid);
		this.setNoteMini(min);
        this.setValidationMatiere(valMat);
	}

	/**
	 * Retourne la note nécessaire à la validation d'un UE
	 * @return la valeur float
	 */
	public float getValidationUE() {
		return validationUE;
	}

	/**
	 * Fixe une note nécessaire à la validation d'un UE
	 * @param validationUE la note
	 */
	public void setValidationUE(float validationUE) {
		this.validationUE = validationUE;
	}

	/**
     * Retourne la note nécessaire pour pouvoir compenser l'UE
     * @return la note
     */
    public float getNoteMini() {
        return noteMini;
    }

    /**
     * Fixe une note nécessaire pour pouvoir compenser l'UE
     * @param noteMini la note
     */
    public void setNoteMini(float noteMini) {
        this.noteMini = noteMini;
    }

    /**
     * Retourne la note nécessaire pour pouvoir valider de facon sur une matiere
     * @return la note
     */
    public float getValidationMatiere() {
        return validationMatiere;
    }

    /**
     * Fixe une note nécessaire pour pouvoir valider de facon sur une matiere
     * @param noteVal la note
     */
    public void setValidationMatiere(float noteVal) {
        this.validationMatiere = noteVal;
    }

}

