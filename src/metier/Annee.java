/*
 * Annee.java			18/05/2018
 * 3iL - Projet Bulletin de Note - 2018
 */
package metier;

import java.util.ArrayList;

/**
 * Classe représentant une année de cours 3iL
 * @author WilliamHenry, BenjaminMazoyer & PierreFrugere
 * @version 2.0
 */
public class Annee {

	/** année dans laquelle l'élève se situe.  date = ING1, ING2 ou ING3*/
	private String date;
	
	/** la liste des UE qu'il y a en ING1 */
	private ArrayList<UniteEnseignement> ING1;
	
	/** la liste des UE qu'il y a en ING2 */
	private ArrayList<UniteEnseignement> ING2;
	
	/** la liste des UE qu'il y a en ING3 */
	private ArrayList<UniteEnseignement> ING3;

	/**
	 * Constructeur de la classe Annee
	 * @param date
	 */
	public Annee(String date){
		this.setDate(date);
		this.setING1(new ArrayList<UniteEnseignement>());
		this.setING2(new ArrayList<UniteEnseignement>());
		this.setING3(new ArrayList<UniteEnseignement>());
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

	public ArrayList<UniteEnseignement> getING1() {
		return ING1;
	}

	public void setING1(ArrayList<UniteEnseignement> iNG1) {
		ING1 = iNG1;
	}

	public ArrayList<UniteEnseignement> getING2() {
		return ING2;
	}

	public void setING2(ArrayList<UniteEnseignement> iNG2) {
		ING2 = iNG2;
	}

	public ArrayList<UniteEnseignement> getING3() {
		return ING3;
	}

	public void setING3(ArrayList<UniteEnseignement> iNG3) {
		ING3 = iNG3;
	}

	public ArrayList<UniteEnseignement> getINGx(String ING) {
		if(ING.equals("ING1")){
            return ING1;
        } else if(ING.equals("ING2")) {
            return ING2;
        } else {
            return ING3;
        }
	}

	public void ajouterMatiere1(String nom){
		this.ING1.add(new UniteEnseignement(nom));
	}

	public void ajouterMatiere2(String nom){
		this.ING2.add(new UniteEnseignement(nom));
	}

	public void ajouterMatiere3(String nom){
		this.ING3.add(new UniteEnseignement(nom));
	}
}
