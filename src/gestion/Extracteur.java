/*
 * BulletinNote.java			18/05/2018
 * 3iL - Projet Bulletin de Note - 2018
 */
package gestion;

import metier.Eleve;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.opencsv.CSVReader;

import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import parametre.MatiereParam;
import parametre.ParamGeneral;
import parametre.UEParam;

import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Classe qui permet d'extraire les informations de différents fichiers et de les transformer en objet manipulable
 * @author WilliamHenry, BenjaminMazoyer & PierreFrugere
 * @version 2.0
 */
public class Extracteur {

    // Le chemin vers le fichier ou l'on doit extraire quelque chose, le probl�me c'ets que j'ai plusieurs type de fichier
    // du coup plusieurs chemin ?
    private String cheminFichier;
    // On va extraire des eleves est ce qu'il a besoin de d'un parametre avec la liste ? si oui parreille pour les ue ?
    private ArrayList<Eleve> eleves = new ArrayList<Eleve>();

    /**
     * Constructeur ayant besoin du chemin d'acc�s au fichier � manipuler
     * @param cheminFichier le chemin vers le fichier dans lequel les informations vont �tre extraites.
     */
    public Extracteur(String cheminFichier){
        this.cheminFichier = cheminFichier;
    }

    /**
     * Fonction qui extrait la liste des �l�ves du fichier csv au format d�finit dans le cahier des charges
     * @return la liste des �l�ves avec le nom, le prenom, la promo et la moyenne g�n�rale
     */
    public ArrayList<Eleve> ExtracteurEleves(String NomFichierParam) throws ParseException {
        String Nom = "";
        String Prenom = "";
        String Promo = "";
        CSVReader reader = null;
        Number MoyenneGenerale = 0;
        try {
            reader = new CSVReader(new FileReader(this.cheminFichier), ';');
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int cptLigne = 0;
        int cptColone = 0;
        List<String[]> myEntries = null;
        try {
            myEntries = reader.readAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String[] line : myEntries) {
            for (String value : line) {
                if(cptLigne != 0) {
                    if (cptColone == 3 + cptLigne * line.length )
                    {
                        Nom = value;
                    }
                    if (cptColone == 4 + cptLigne * line.length ) {
                        Prenom = value;
                    }
                    if (cptColone == 1 + cptLigne * line.length ) {
                        Promo = value;
                    }
                    if (cptColone == line.length - 1 + cptLigne * line.length ) {
                        MoyenneGenerale = NumberFormat.getNumberInstance(Locale.FRENCH).parse(value);
                    }
                }
                cptColone++;
            }
            if(cptLigne != 0) {
                this.eleves.add(new Eleve(Nom,Prenom,Promo,MoyenneGenerale.floatValue(),Promo,NomFichierParam));
            }
            cptLigne++;
        }

        return(eleves);
    }

    /**
     *
     * @param eleves la liste des �l�ves avec leurs mati�res
     * @return
     */
    public ArrayList<Eleve> ExtracteurNotes(ArrayList<Eleve> eleves, String Annee) throws ParseException {
        int index_eleve = 0;
        ArrayList<String> nomMatieres = new ArrayList<String>();
        CSVReader reader = null;
        int cptLigne = 0;
        List<String[]> myEntries = null;
        try {
            reader = new CSVReader(new FileReader(this.cheminFichier), ';');
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            myEntries = reader.readAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String[] line : myEntries) {
            int cptColone = 0;
            String prenom = "";
            String nom = "";
            String promo = "";
            boolean flag = false;
            for (String value : line) {
                if(cptLigne == 0 && cptColone >= 5) {
                    nomMatieres.add(value);
                }
                if (cptColone == 1){
                    promo = value;
                }
                if (cptColone == 3) {
                    nom = value;
                }
                if (cptColone == 4){
                    prenom = value;
                    index_eleve = 0;
                    while (eleves.size() > index_eleve && flag==false) {
                        Eleve eleve = eleves.get(index_eleve);
                        if (eleve.cmpElevetoValue(nom,prenom,promo)){
                            // j'ai trouv� les eleves
                            flag = true;
                        }
                        index_eleve++;
                    }
                }
                if(flag && cptColone >= 5){
                    // R�cup�ration index UE et index matiere concern�
                    int[] liste_index;
                    //indexMatiere renvois par d�faut 0 donc si il rencontre des matieres inconnu probl�me
                    liste_index = eleves.get(index_eleve - 1).indexMatiere(nomMatieres.get(cptColone - 5),Annee);
                    // Si la mati�re n'est pas trouv� on ne fait rien
                    if(liste_index[0] != -1 && liste_index[1] != -1) {
                        Number number = 0;
                        if(!value.equals("")){
                            number = NumberFormat.getNumberInstance(Locale.FRENCH).parse(value);
                        }
                        eleves.get(index_eleve - 1).setNote(liste_index[0], liste_index[1], number.floatValue(), Annee);
                    }
                }
                cptColone++;
            }
            cptLigne++;
        }

        return(eleves);
    }

    public ArrayList<Eleve> ExtracteurNotesToutesAnnee(ArrayList<Eleve> eleves) throws ParseException {
        eleves = this.ExtracteurNotes(eleves,"ING1");
        eleves = this.ExtracteurNotes(eleves,"ING2");
        eleves = this.ExtracteurNotes(eleves,"ING3");

        return(eleves);
    }

    /**
     * Fonction qui extrait la liste des param�tres du fichier xml au format d�finit dans le cahier des charges
     * @return la liste des param�tres pour les UE et leurs mati�res
     */
    public ParamGeneral ExtracteurParametresGeneraux() {
        // Etape 1 : r�cup�ration d'une instance de la classe "DocumentBuilderFactory"
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        ParamGeneral paramGeneral = new ParamGeneral(0,0,0);

        try {
            // Etape 2 : cr�ation d'un parseur
            final DocumentBuilder builder = factory.newDocumentBuilder();

            // Etape 3 : cr�ation d'un Document
            final Document document = builder.parse(new File(this.cheminFichier));

            // Etape 4 : r�cup�ration de l'Element racine
            final Element racine = document.getDocumentElement();

            // Etape 5 : r�cup�ration des personnes
            final NodeList racineNoeuds = racine.getChildNodes();
            final int nbRacineNoeuds = racineNoeuds.getLength();

            for (int i = 0; i < nbRacineNoeuds; i++) {
                if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    final Element promo = (Element) racineNoeuds.item(i);

                    final NodeList listeuniteEnseignement = promo.getElementsByTagName("UE");
                    final int nbUniteEnseignement = listeuniteEnseignement.getLength();
                    for(int k = 0; k<nbUniteEnseignement; k++) {
                        final Element uniteEnseignement = (Element) listeuniteEnseignement.item(k);

                        // Etape 6 : r�cup�ration du nom et du pr�nom
                        final Element seuil_validation = (Element) uniteEnseignement.getElementsByTagName("seuil_validation").item(0);
                        Number seuil_validationVal = NumberFormat.getNumberInstance(Locale.FRENCH).parse(seuil_validation.getTextContent());
                        paramGeneral.setValidationUE(seuil_validationVal.floatValue());

                        final Element seuil_validation_mat = (Element) uniteEnseignement.getElementsByTagName("seuil_validation_matiere").item(0);
                        Number seuil_validationVal_mat = NumberFormat.getNumberInstance(Locale.FRENCH).parse(seuil_validation_mat.getTextContent());
                        paramGeneral.setValidationMatiere(seuil_validationVal_mat.floatValue());

                        // Etape 7 : r�cup�ration des num�ros de t�l�phone
                        final NodeList matieres = uniteEnseignement.getElementsByTagName("matiere");
                        final int nbMatiereElements = matieres.getLength();

                        for (int j = 0; j < nbMatiereElements; j++) {
                            final Element matiere = (Element) matieres.item(j);

                            final Element note_minimale = (Element) matiere.getElementsByTagName("note_minimale").item(0);
                            Number note_minimaleVal = NumberFormat.getNumberInstance(Locale.FRENCH).parse(note_minimale.getTextContent());
                            paramGeneral.setNoteMini(note_minimaleVal.floatValue());
                        }
                    }
                }
            }
        } catch (final ParserConfigurationException e) {
            e.printStackTrace();
        } catch (final SAXException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return(paramGeneral);
    }

    /**
     * Fonction qui extrait la liste des param�tres du fichier xml au format d�finit dans le cahier des charges
     * @return la liste des param�tres pour les UE et leurs mati�res
     */
    public ArrayList<UEParam>  ExtracteurParametres(String annee) {
        // Etape 1 : r�cup�ration d'une instance de la classe "DocumentBuilderFactory"
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        ArrayList<UEParam> listeParametresUniteEnseignement = new ArrayList<UEParam>();
        ArrayList<MatiereParam> listeParametresMatiere = new ArrayList<MatiereParam>();


        try {
            // Etape 2 : cr�ation d'un parseur
            final DocumentBuilder builder = factory.newDocumentBuilder();

            // Etape 3 : cr�ation d'un Document
            final Document document = builder.parse(new File(this.cheminFichier));

            // Etape 4 : r�cup�ration de l'Element racine
            final Element racine = document.getDocumentElement();

            // Etape 5 : r�cup�ration des personnes
            final NodeList racineNoeuds = racine.getChildNodes();
            final int nbRacineNoeuds = racineNoeuds.getLength();

            for (int i = 0; i < nbRacineNoeuds; i++) {
                if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    final Element promo = (Element) racineNoeuds.item(i);

                    if (promo.getAttribute("nom").equals(annee)) {

                        final NodeList listeuniteEnseignement = promo.getElementsByTagName("UE");
                        final int nbUniteEnseignement = listeuniteEnseignement.getLength();
                        for (int k = 0; k < nbUniteEnseignement; k++) {
                            final Element uniteEnseignement = (Element) listeuniteEnseignement.item(k);

                            // Etape 6 : r�cup�ration du nom et du pr�nom
                            final Element nom = (Element) uniteEnseignement.getElementsByTagName("nom").item(0);
                            final Element credit = (Element) uniteEnseignement.getElementsByTagName("credit").item(0);
                            final Element coeficient = (Element) uniteEnseignement.getElementsByTagName("coeficient").item(0);

                            // Etape 7 : r�cup�ration des num�ros de t�l�phone
                            final NodeList matieres = uniteEnseignement.getElementsByTagName("matiere");
                            final int nbMatiereElements = matieres.getLength();

                            for (int j = 0; j < nbMatiereElements; j++) {
                                final Element matiere = (Element) matieres.item(j);

                                final Element nomMat = (Element) matiere.getElementsByTagName("nom").item(0);
                                final Element enseignant = (Element) matiere.getElementsByTagName("enseignant").item(0);
                                final Element coeficientMat = (Element) matiere.getElementsByTagName("coeficient").item(0);
                                //final Element note_minimale = (Element) matiere.getElementsByTagName("note_minimale").item(0);
                                // Attention Float.parseFloat demande un string bien pr�cit pour devenir float (coef ne devrait pas etre dans cette classe je crois)
                                Number coeficientMatVal = NumberFormat.getNumberInstance(Locale.FRENCH).parse(coeficientMat.getTextContent());
                                listeParametresMatiere.add(new MatiereParam(nomMat.getTextContent(), coeficientMatVal.floatValue(), enseignant.getTextContent()));
                            }
                            Number creditVal = NumberFormat.getNumberInstance(Locale.FRENCH).parse(credit.getTextContent());
                            Number coeficientVal = NumberFormat.getNumberInstance(Locale.FRENCH).parse(coeficient.getTextContent());
                            listeParametresUniteEnseignement.add(new UEParam(nom.getTextContent(), creditVal.floatValue(), coeficientVal.floatValue(), new ArrayList<MatiereParam>(listeParametresMatiere)));
                            listeParametresMatiere.clear();
                        }
                    }
                }
            }
        } catch (final ParserConfigurationException e) {
            e.printStackTrace();
        } catch (final SAXException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return(listeParametresUniteEnseignement);
    }

    public int SearchIndexEleveIndividuel(Eleve person) throws IOException {
        int index=0;
        boolean flag = false;
        FileInputStream fichier = new FileInputStream(cheminFichier);
        final HSSFWorkbook wb = new HSSFWorkbook(fichier);

        while ((flag == false) && index < wb.getNumberOfSheets()) {
            Sheet feuille = wb.getSheetAt(index);
            String nom = "";
            String prenom = "";

            int index_ligne = 1;
            Row row = feuille.getRow(index_ligne++);

            while (row != null && flag == false) {
                row = feuille.getRow(index_ligne++);
                if(index_ligne == 7){
                	if(row.getCell(3) != null) {
                		nom = row.getCell(3).getStringCellValue();
                	} else {
                		nom = "";
                	}
                }
                if(index_ligne == 15){
                	if(row.getCell(5) != null) {
                		prenom = row.getCell(5).getStringCellValue();
                	} else {
                		prenom = "";
                	}
                }
                if (index_ligne == 15) {
                    if (person.getNomEleve().equals(nom) && person.getPrenomEleve().equals(prenom)) {
                        flag = true;
                    }
                }
            }
            index++;
        }

        fichier.close();

        return (--index);
    }


    public Eleve ExtracteurInformationIndividuel(int index, Eleve person, String promo) throws IOException {

        FileInputStream fichier = new FileInputStream(cheminFichier);
        final HSSFWorkbook wb = new HSSFWorkbook(fichier);

        Sheet feuille = wb.getSheetAt(index);
        String nom = "";
        String prenom = "";
        String nom_mat = "";
        boolean flag, flag_bis;

        int index_ligne = 1;
        int cpt_mat,cpt_ue;
        Row row = feuille.getRow(index_ligne++);
        flag_bis = false;

        while (row != null && flag_bis == false) {
            row = feuille.getRow(index_ligne++);
            if(index_ligne == 7){
            	if(row.getCell(3) != null) {
            		nom = row.getCell(3).getStringCellValue();
            	} else {
            		nom = "";
            	}
            }
            if(index_ligne == 15){
            	if(row.getCell(5) != null) {
            		prenom = row.getCell(5).getStringCellValue();
            	} else {
            		prenom = "";
            	}
            }
            // On travail sur le bon eleve
            if(index_ligne >= 25) {
                if (person.getNomEleve().equals(nom) && person.getPrenomEleve().equals(prenom)) {
                    // il manque le choix des ligne pour le moment
                    if(index_ligne == 25) {
                    	if(row.getCell(1) != null) {
                    		person.setAdresse(row.getCell(7).getStringCellValue());
                    	} else {
                    		person.setAdresse("");
                    	}
                    }
                    if(index_ligne == 40) {
                    	if(row.getCell(1) != null) {
                    		person.setDateNaissance(row.getCell(10).getStringCellValue());
                    	} else {
                    		person.setDateNaissance("");
                    	}
                    }

		            if(index_ligne==48) {
		            	if(row.getCell(1) != null) {
		            		person.getAnnee().setDate(row.getCell(20).getStringCellValue());
		            	} else {
		            		person.getAnnee().setDate("");
		            	}
		            }
		            // On cherche pour chaque mati�res les observations
		            if(index_ligne >= 56 && flag_bis == false) {
		                cpt_ue = 0;
		                flag = false;
		                if(row.getCell(1) != null) {
		                	nom_mat = row.getCell(1).getStringCellValue();
		                } else {
		                	nom_mat = "";
		                }
		                while(cpt_ue < person.getAnnee().getINGx(promo).size() && flag == false) {
		                    cpt_mat = 0;
		                    while(cpt_mat < person.getAnnee().getINGx(promo).get(cpt_ue).getParamUE().getMatieresUE().size() && flag == false) {
		                        // Recup�ration du nom de la mati�re
		                        if(!nom_mat.isEmpty()) {
		                            if (person.getAnnee().getINGx(promo).get(cpt_ue).getParamUE().getMatieresUE().get(cpt_mat).getNomMatiere().equals(nom_mat)) {
		                                person.getAnnee().getINGx(promo).get(cpt_ue).getParamUE().getMatieresUE().get(cpt_mat).setObservation(row.getCell(23).getStringCellValue());
		                                flag = true;
		                            }
		                        }
		                        cpt_mat++;
		                    }
		                    cpt_ue++;
		                }
		                // les mati�res sont toutes les 5 lignes a partir de la ligne 56 sauf pour les mission
		                if(nom_mat.contains("mission") || nom_mat.contains("Mission")){
		                    index_ligne += 2;
		                } else if (nom_mat.equals("MOYENNE GENERALE")) {
		                    flag_bis = true;
		                } else if (nom_mat.equals("Observations g�n�rales")) {
		                    index_ligne--;
		                    flag_bis = true;
		                } else if (nom_mat.equals("Appr�ciations g�n�rales")) {
		                    index_ligne -= 2;
		                    flag_bis = true;
		                } else if (nom_mat.equals("Responsable de classe")) {
		                    index_ligne -= 3;
		                    flag_bis = true;
		                } else {
		                    index_ligne += 4;
		                }
		                //System.out.println(nom_mat + " " + index_ligne);
		            }
		            if (flag_bis == true) {
		                // Observation generale
		                row = feuille.getRow(index_ligne);
		                if(row.getCell(13) != null) {
		                	person.setCommentaire(row.getCell(13).getStringCellValue());
		                } else {
		                	person.setCommentaire("");
		                }
		                // Appreciation generale
		                row = feuille.getRow(index_ligne+1);
		                if(row.getCell(13) != null) {
		                	person.setToeic(row.getCell(13).getStringCellValue());
		                } else {
		                	person.setToeic("");
		                }
		                // Responsable
		                row = feuille.getRow(index_ligne+2);
		                if(row.getCell(13) != null) {
		                	person.setCommentaireResponsable(row.getCell(13).getStringCellValue());
		                } else {
		                	person.setCommentaireResponsable("");
		                }
		            }
                } else {
                    //System.out.println("Probl�me mauvais eleve en paramettre : Eleve.nom = " + person.getNomEleve() + " Eleve.prenom = " + person.getPrenomEleve() + " Fichier.nom = " + nom + " Fichier.prenom = " + prenom);
                }
            }
        }

        fichier.close();

        return person;
    }

    public void setCheminFichier(String chemin){
        this.cheminFichier=chemin;
    }

    
    /**
     *
     * @return
     */
    public String ExtracteurCheminFichierDistant(String element) {
        // Etape 1 : r�cup�ration d'une instance de la classe "DocumentBuilderFactory"
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        String Chemin = new String("");

        try {
            // Etape 2 : cr�ation d'un parseur
            final DocumentBuilder builder = factory.newDocumentBuilder();

            // Etape 3 : cr�ation d'un Document
            final Document document = builder.parse(new File(this.cheminFichier));

            // Etape 4 : r�cup�ration de l'Element racine
            final Element racine = document.getDocumentElement();

            // Etape 5 : r�cup�ration des personnes
            final NodeList racineNoeuds = racine.getChildNodes();
            final int nbRacineNoeuds = racineNoeuds.getLength();

            for (int i = 0; i < nbRacineNoeuds; i++) {
                if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    final Element dossier = (Element) racineNoeuds.item(i);

                    final NodeList listedossier = dossier.getElementsByTagName("chemin");
                    final int nbDossier = listedossier.getLength();
                    for(int k = 0; k<nbDossier; k++) {
                        final Element chemin = (Element) listedossier.item(k);
                        if(dossier.getAttribute("nom").equals(element)){
                            Chemin = chemin.getTextContent();
                        }
                    }
                }
            }
        } catch (final ParserConfigurationException e) {
            e.printStackTrace();
        } catch (final SAXException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        }

        return(Chemin);
    }
}
