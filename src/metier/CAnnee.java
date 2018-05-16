/**
 * 
 */
package metier;

import java.util.ArrayList;

public class CAnnee {

	/** ann�e dans laquelle l'�l�ve se situe.  date = ING1, ING2 ou ING3*/
	private String date;
	
	/** la liste des UE qu'il y a en ING1 */
	private ArrayList<CUniteEnseignement> ING1;
	
	/** la liste des UE qu'il y a en ING2 */
	private ArrayList<CUniteEnseignement> ING2;
	
	/** la liste des UE qu'il y a en ING3 */
	private ArrayList<CUniteEnseignement> ING3;

	/**
	 * Constructeur de la classe CAnnee
	 * @param date
	 */
	public CAnnee(String date){
		this.setDate(date);
		this.setING1(new ArrayList<CUniteEnseignement>());
		this.setING2(new ArrayList<CUniteEnseignement>());
		this.setING3(new ArrayList<CUniteEnseignement>());
	}

	/**
	 * Accesseur de l'attribut date
	 * @return l'ann�e dans laquelle l'�l�ve se situe
	 */
	public String getDate() {
		return date;
	}

	/**
	 * modifieur de l'attribut date
	 * @param date
	 */
	public void setDate(String date) {
		this.date = date;
	}

	public ArrayList<CUniteEnseignement> getING1() {
		return ING1;
	}

	public void setING1(ArrayList<CUniteEnseignement> iNG1) {
		ING1 = iNG1;
	}

	public ArrayList<CUniteEnseignement> getING2() {
		return ING2;
	}

	public void setING2(ArrayList<CUniteEnseignement> iNG2) {
		ING2 = iNG2;
	}

	public ArrayList<CUniteEnseignement> getING3() {
		return ING3;
	}

	public void setING3(ArrayList<CUniteEnseignement> iNG3) {
		ING3 = iNG3;
	}

	public ArrayList<CUniteEnseignement> getINGx(String ING) {
		if(ING.equals("ING1")){
            return ING1;
        } else if(ING.equals("ING2")) {
            return ING2;
        } else {
            return ING3;
        }
	}

	public void ajouterMatiere1(String nom){
		this.ING1.add(new CUniteEnseignement(nom));
	}

	public void ajouterMatiere2(String nom){
		this.ING2.add(new CUniteEnseignement(nom));
	}

	public void ajouterMatiere3(String nom){
		this.ING3.add(new CUniteEnseignement(nom));
	}
}
