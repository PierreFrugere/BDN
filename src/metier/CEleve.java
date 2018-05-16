package metier;

import gestion.CExtracteur;
import parametre.CMatiereParam;
import parametre.CUEParam;

import java.util.ArrayList;

public class CEleve {

    /** nom de l'�l�ve */
    private String nomEleve;

    /** prenom de l'�l�ve */
    private String prenomEleve;

    /** promo de l'�l�ve */
    private String promoEleve;
    
    /** chaque �l�ve a 3 ann�e */
    private CAnnee annee;

    private String adresse;

    private String dateNaissance;

    private String toeic;

    private String Commentaire;

    private String commentaireResponsable;

    /** moyenne g�n�rale de l'�l�ve */
    private float moyenneGenerale;

    

    /**
     * Constructeur de la classe CEleve
     * @param nom
     * @param prenom
     * @param promo
     */
    public CEleve(String nom, String prenom, String promo,float moy, String ING, String NomFichierParam){
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
        annee = new CAnnee(ING);
        CMatiere cMatiere;
        String cheminFichierParam = new CExtracteur("..\\ressources\\ParametrageAccesFichier.xml").ExtracteurCheminFichierDistant("Parametrages");
        CExtracteur extracteurUE = new CExtracteur(cheminFichierParam+"\\Parametrage-"+NomFichierParam+".xml");
        for(cptAnnee=1;cptAnnee<4;cptAnnee++) {
            ArrayList<CUEParam> paramsUE = new ArrayList<CUEParam>();
            paramsUE = extracteurUE.ExtracteurParametres("ING"+cptAnnee);
            ArrayList<CMatiere> matieresUE = new ArrayList<CMatiere>();
            for (CUEParam UEparametrer : paramsUE) {
                for (CMatiereParam UEmatieres : UEparametrer.getMatieresUE()) {
                    cMatiere = new CMatiere(UEmatieres.getNomMatiere(), (float) 0);
                    matieresUE.add(cMatiere);
                }
                this.annee.getINGx("ING"+cptAnnee).add(new CUniteEnseignement(UEparametrer.getNomUE(), new ArrayList<CMatiere>(matieresUE), UEparametrer));

                matieresUE.clear();
            }
        }
    }

    /**
     * Accesseur de l'attribut nomEleve
     * @return le nom de l'�l�ve
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
     * @return le prenom de l'�l�ve
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
     * @return promo de l'�l�ve
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
     * @return moyenne g�n�rale de l'�l�ve
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
     * Cette m�thode permet de calculer la moyenne g�n�rale de l'�l�ve
     * @return la moyenne de l'�l�ve
     */
    public float calculMoyenneGenerale(){
        return (this.moyenneGenerale);
    }
    
    /**
     * Indique si l'eleve correspond bien au donn�e pass� en parametre
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
     * Cette fonction a l'air OK suite au test renvoit trois int le premier pour le num�ro de l'ue seconde numero de la matiere toisieme num�ro de lannee
     * @param nomMatiere
     * @return
     */
    public int[] indexMatiere(String nomMatiere, String Annee) {
        boolean flag = false;
        int index_UE = 0;
        int index_matiere;
        int[] liste_index = {-1,-1,-1};
        while (flag == false && this.annee.getINGx(Annee).size() > index_UE) {
            CUniteEnseignement UE = this.annee.getINGx(Annee).get(index_UE);
            index_matiere = 0;
            while (flag == false && UE.getMatieres().size() > index_matiere) {
                CMatiere UEmatieres = UE.getMatieres().get(index_matiere);
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
     * Cette m�thode permet d'afficher un �l�ve
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

    public CAnnee getAnnee() {
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

    public void setAnnee(CAnnee annee) {
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

