/*
 * Eleve.java			18/05/2018
 * 3iL - Projet Bulletin de Note - 2018
 */
package metier;

import gestion.Extracteur;
import parametre.MatiereParam;
import parametre.UEParam;

import java.util.ArrayList;

/**
 * Classe représentant un élève de l'école 3iL
 * @author WilliamHenry, BenjaminMazoyer & PierreFrugere
 * @version 2.0
 */
public class Eleve {

    /** nom de l'élève */
    private String nomEleve;

    /** prenom de l'élève */
    private String prenomEleve;

    /** promo de l'élève */
    private String promoEleve;
    
    /** chaque élève a 3 année */
    private Annee annee;

    private String adresse;

    private String dateNaissance;

    private String toeic;

    private String Commentaire;

    private String commentaireResponsable;

    /** moyenne générale de l'élève */
    private float moyenneGenerale;

    

    /**
     * Constructeur de la classe Eleve
     * @param nom
     * @param prenom
     * @param promo
     */
    public Eleve(String nom, String prenom, String promo, float moy, String ING, String NomFichierParam){
        int cptAnnee;
        this.setNomEleve(nom);
        this.setPrenomEleve(prenom);
        this.setPromoEleve(promo);
        this.setMoyenneGenerale(moy);
        this.setAdresse("");
        this.setDateNaissance("");
        this.setToeic("");
        this.setCommentaire("");
        this.setCommentaireResponsable("");
        annee = new Annee(ING);
        Matiere Matiere;
        String cheminFichierParam = new Extracteur("..\\ressources\\ParametrageAccesFichier.xml").ExtracteurCheminFichierDistant("Parametrages");
        Extracteur extracteurUE = new Extracteur(cheminFichierParam+"\\Parametrage-"+NomFichierParam+".xml");
        for(cptAnnee=1;cptAnnee<4;cptAnnee++) {
            ArrayList<UEParam> paramsUE = new ArrayList<UEParam>();
            paramsUE = extracteurUE.ExtracteurParametres("ING"+cptAnnee);
            ArrayList<metier.Matiere> matieresUE = new ArrayList<metier.Matiere>();
            for (UEParam UEparametrer : paramsUE) {
                for (MatiereParam UEmatieres : UEparametrer.getMatieresUE()) {
                    Matiere = new Matiere(UEmatieres.getNomMatiere(), (float) 0);
                    matieresUE.add(Matiere);
                }
                this.annee.getINGx("ING"+cptAnnee).add(new UniteEnseignement(UEparametrer.getNomUE(), new ArrayList<metier.Matiere>(matieresUE), UEparametrer));

                matieresUE.clear();
            }
        }
    }

    /**
     * Accesseur de l'attribut nomEleve
     * @return le nom de l'élève
     */
    public String getNomEleve() {
        return nomEleve;
    }

    /**
     * Modifieur de l'attribut nomEleve
     * @param nomEleve
     */
    public void setNomEleve(String nomEleve) {
        this.nomEleve = nomEleve;
    }

    /**
     * Accesseur de l'attribut prenomEleve
     * @return le prenom de l'élève
     */
    public String getPrenomEleve() {
        return prenomEleve;
    }

    /**
     * Modifieur de l'attribut prenomEleve
     * @param prenomEleve
     */
    public void setPrenomEleve(String prenomEleve) {
        this.prenomEleve = prenomEleve;
    }

    /**
     * Accesseur de l'attribut promoEleve
     * @return promo de l'élève
     */
    public String getPromoEleve() {
        return promoEleve;
    }

    /**
     * Modifieur de l'attribut promoEleve
     * @param promoEleve
     */
    public void setPromoEleve(String promoEleve) {
        this.promoEleve = promoEleve;
    }

    /**
     * Accesseur de l'attribut moyenneGenerale
     * @return moyenne générale de l'élève
     */
    public float getMoyenneGenerale() {
        return (this.moyenneGenerale);
    }

    /**
     * Modifieur de l'attribut moyenneGenerale
     * @param moyenneGenerale
     */
    public void setMoyenneGenerale(float moyenneGenerale) {
        this.moyenneGenerale = moyenneGenerale;
    }

    /**
     * Cette m�thode permet de calculer la moyenne g�n�rale de l'élève
     * @return la moyenne de l'élève
     */
    public float calculMoyenneGenerale(){
        return (this.moyenneGenerale);
    }
    
    /**
     * Indique si l'eleve correspond bien au donnée passé en parametre
     * @param nom
     * @param prenom
     * @param promo
     * @return
     */
    public boolean cmpElevetoValue(String nom, String prenom, String promo){
        boolean flag;

        if(this.getPrenomEleve().equals(prenom) && this.getNomEleve().equals(nom) && this.getPromoEleve().equals(promo)) {
            flag = true;
        } else {
            flag = false;
        }

        return flag;
    }

    /**
     * Cette fonction a l'air OK suite au test renvoit trois int le premier pour le numéro de l'ue seconde numero de la matiere toisieme numéro de lannee
     * @param nomMatiere
     * @return
     */
    public int[] indexMatiere(String nomMatiere, String Annee) {
        boolean flag = false;
        int index_UE = 0;
        int index_matiere;
        int[] liste_index = {-1,-1,-1};
        while (flag == false && this.annee.getINGx(Annee).size() > index_UE) {
            UniteEnseignement UE = this.annee.getINGx(Annee).get(index_UE);
            index_matiere = 0;
            while (flag == false && UE.getMatieres().size() > index_matiere) {
                Matiere UEmatieres = UE.getMatieres().get(index_matiere);
                if (UEmatieres.getNomMatiere().equals(nomMatiere)) {
                    flag = true;
                    liste_index[0] = index_UE;
                    liste_index[1] = index_matiere;
                }
                index_matiere++;
            }
            index_UE++;
        }

        return liste_index;
    }

    public void setNote(int index_UE, int index_Matiere, float note, String annee){
        if(annee.equals("ING1")){
            this.annee.getING1().get(index_UE).getMatieres().get(index_Matiere).setNoteMatiere(note);
        }else if(annee.equals("ING2")){
            this.annee.getING2().get(index_UE).getMatieres().get(index_Matiere).setNoteMatiere(note);
        }else if (annee.equals("ING3")) {
            this.annee.getING3().get(index_UE).getMatieres().get(index_Matiere).setNoteMatiere(note);
        }else{
            System.out.println("ING = autre chose  (setNote)");
        }
    }

    @Override
    /**
     * Cette méthode permet d'afficher un élève
     * @return
     * @Override
     */
    public String toString() {
        String retour;
        retour = "Eleve: " + getPrenomEleve() + " " + getNomEleve() + " Promo: " + getPromoEleve() + " Moyenne Generale: " + Float.toString(getMoyenneGenerale()) + " Adresse: " + getAdresse() + " Date de naissance: " + getDateNaissance();
        retour += ("\n\tING1 Unite Enseignement: " + this.annee.getING1().toString());
        retour += ("\n\tING2 Unite Enseignement: " + this.annee.getING2().toString());
        retour += ("\n\tING3 Unite Enseignement: " + this.annee.getING3().toString());

        return retour;
    }

    public Annee getAnnee() {
        return annee;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public String getDateNaissance() {
        return this.dateNaissance;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setDateNaissance(String date) {
        this.dateNaissance = date;
    }

    public void setAnnee(Annee annee) {
        this.annee = annee;
    }

    public String getToeic() {
        return this.toeic;
    }

    public void setToeic(String comm) {
        this.toeic = comm;
    }

    public String getCommentaire() {
        return Commentaire;
    }

    public void setCommentaire(String commentaire) {
        Commentaire = commentaire;
    }

    public String getCommentaireResponsable() {
        return commentaireResponsable;
    }

    public void setCommentaireResponsable(String commentaireResponsable) {
        this.commentaireResponsable = commentaireResponsable;
    }
}

