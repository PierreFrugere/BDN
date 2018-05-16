/**
 * pParametre correspond au paquetage de gestion de tous les param�tre extrait du fichier de param�trage
 */
package parametre;


/**
 * Param�trage g�n�rale globaux a tous les UE
 */
public class CParamGeneral {

    /** Note minimale pour validation UE */
	private float validationUE;
    /** Note minimale pour validation matiere */
    private float validationMatiere;
    /** Note minimale pour pouvoir compenser matiere */
	private float noteMini;

	
	/**
	 * Constructeur qui requi�re les informations globales des UE
	 * @param valid note de validation n�cessaire pour valider l'UE
	 * @param min note de minimale n�cessaire pour pouvoir compenser l'UE
	 */
	public CParamGeneral(float valid, float min, float valMat){
		this.setValidationUE(valid);
		this.setNoteMini(min);
        this.setValidationMatiere(valMat);
	}

	/**
	 * Retourne la note n�cessaire � la validation d'un UE
	 * @return la valeur float
	 */
	public float getValidationUE() {
		return validationUE;
	}

	/**
	 * Fixe une note n�cessaire � la validation d'un UE
	 * @param validationUE la note
	 */
	public void setValidationUE(float validationUE) {
		this.validationUE = validationUE;
	}

	/**
     * Retourne la note n�cessaire pour pouvoir compenser l'UE
     * @return la note
     */
    public float getNoteMini() {
        return noteMini;
    }

    /**
     * Fixe une note n�cessaire pour pouvoir compenser l'UE
     * @param noteMini la note
     */
    public void setNoteMini(float noteMini) {
        this.noteMini = noteMini;
    }

    /**
     * Retourne la note n�cessaire pour pouvoir valider de facon sur une matiere
     * @return la note
     */
    public float getValidationMatiere() {
        return validationMatiere;
    }

    /**
     * Fixe une note n�cessaire pour pouvoir valider de facon sur une matiere
     * @param noteVal la note
     */
    public void setValidationMatiere(float noteVal) {
        this.validationMatiere = noteVal;
    }

}

