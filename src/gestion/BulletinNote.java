/*
 * BulletinNote.java			18/05/2018
 * 3iL - Projet Bulletin de Note - 2018
 */
package gestion;

import metier.Eleve;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import parametre.ParamGeneral;

import java.awt.*;
import java.lang.Integer;
import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
/**
 * Classe qui rassemble toutes les donnÃ©es et outils permettant de manipuler les bulletins de notes
 * @author WilliamHenry, BenjaminMazoyer & PierreFrugere
 * @version 2.0
 */
public class BulletinNote {

    private ParamGeneral param;

    /**
     * Constructeur on donne les informations d'un Ã©lÃ¨ve et les informations de paramï¿½trage
     * @param nom de l'Ã©lÃ¨ve
     * @param prenom de l'Ã©lÃ¨ve
     * @param promo de l'Ã©lÃ¨ve
     * @param valid information de paramï¿½trage
     * @param min information de paramï¿½trage
     * @param date information de paramï¿½trage
     */
    public BulletinNote(String nom, String prenom, String promo, float valid, float min, String date){
        setParam(new ParamGeneral(valid,min,0));
    }

    /**
     * Fonction gï¿½nï¿½rant un bulletin de note individuel
     */
    public void bulletinIndividuel(String Promo, String NomFichierPromo) throws ParseException, IOException {
        int cptFeuil1, cptMatByUE, cptEleve, cptUE, offset, cptMat, nbMaxMat;

        //
        String cheminFichierSortie= new Extracteur("ressources\\ParametrageAccesFichier.xml").ExtracteurCheminFichierDistant("Sorties");
        String cheminFichierGeneral = new Extracteur("ressources\\ParametrageAccesFichier.xml").ExtracteurCheminFichierDistant("Resources");
        String cheminFichierParam = new Extracteur("ressources\\ParametrageAccesFichier.xml").ExtracteurCheminFichierDistant("Parametrages");

        //
        File fichierGeneral = new File(cheminFichierGeneral + "\\" + NomFichierPromo + Promo + ".csv");
        File fichierParam = new File(cheminFichierParam+"\\Parametrage-" + NomFichierPromo + Promo + ".xml");
        File fichierIndiv = new File(cheminFichierGeneral+"\\Individuels"+ NomFichierPromo + Promo +".xls");

        if( !fichierGeneral.exists() ) {
        	// JAVAFX Alert
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("Information");
        	alert.setHeaderText("Information");
        	alert.setContentText("Fichier " + NomFichierPromo+Promo+".csv introuvable");
        	alert.showAndWait();
        } else if ( !fichierParam.exists() ) {
        	// JAVAFX Alert
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("Information");
        	alert.setHeaderText("Information");
        	alert.setContentText("Fichier " + NomFichierPromo + Promo+".xml introuvable");
        	alert.showAndWait();
        } else if ( !fichierIndiv.exists() ) {
        	// JAVAFX Alert
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("Information");
        	alert.setHeaderText("Information");
        	alert.setContentText("Fichier Individuels"+ NomFichierPromo + Promo +".xls introuvable");
        	alert.showAndWait();
        } else {
        	// JAVAFX Alert
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("Information");
        	alert.setHeaderText("Information");
        	alert.setContentText("Génération des bulletins individuels en cours");
        	alert.showAndWait();

            //
            Extracteur Extraction = new Extracteur(cheminFichierGeneral+"\\" + NomFichierPromo + Promo + ".csv");
            ArrayList<Eleve> eleves_extrait = Extraction.ExtracteurEleves(NomFichierPromo + Promo);
            eleves_extrait = Extraction.ExtracteurNotesToutesAnnee(eleves_extrait);
            Extraction.setCheminFichier(cheminFichierGeneral+"\\Individuels"+ NomFichierPromo + Promo +".xls");

            //
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);

            //1. Crï¿½er un Document vide
            XSSFWorkbook wb = new XSSFWorkbook();
            //
            XSSFCellStyle style = wb.createCellStyle();
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            //
            XSSFCellStyle styleNote = wb.createCellStyle();
            styleNote.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            styleNote.setBorderTop(HSSFCellStyle.BORDER_THIN);
            styleNote.setBorderRight(HSSFCellStyle.BORDER_THIN);
            styleNote.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            //
            XSSFCellStyle styleTitre = wb.createCellStyle();
            styleTitre.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            styleTitre.setBorderTop(HSSFCellStyle.BORDER_THIN);
            styleTitre.setBorderRight(HSSFCellStyle.BORDER_THIN);
            styleTitre.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            styleTitre.setAlignment(styleTitre.ALIGN_CENTER);
            //
            XSSFCellStyle styleTitreUE = wb.createCellStyle();
            styleTitreUE.setAlignment(styleTitreUE.ALIGN_CENTER);
            //
            XSSFCellStyle styleTitrePrincipal = wb.createCellStyle();
            styleTitrePrincipal.setAlignment(styleTitrePrincipal.ALIGN_CENTER);
            //
            XSSFCellStyle styleAlignLeft = wb.createCellStyle();
            styleAlignLeft.setAlignment(styleTitrePrincipal.ALIGN_LEFT);
            //
            XSSFFont font = wb.createFont();
            font.setBold(true);
            font.setFontHeightInPoints((short) 16);
            font.setFontName("Calibri");  
            styleTitrePrincipal.setFont(font);
            //
            XSSFFont fontUE = wb.createFont();
            fontUE.setBold(true);
            fontUE.setFontHeightInPoints((short) 11);
            fontUE.setFontName("Calibri");  
            styleTitreUE.setFont(fontUE);
            //
            XSSFFont fontNote = wb.createFont();
            fontNote.setBold(true);
            fontNote.setFontHeightInPoints((short) 11);
            fontNote.setFontName("Calibri");  
            styleNote.setFont(fontNote);

            nbMaxMat = 0;
            for (cptUE = 0; cptUE < eleves_extrait.get(0).getAnnee().getINGx(eleves_extrait.get(0).getPromoEleve()).size(); cptUE++) {
                if (nbMaxMat < eleves_extrait.get(0).getAnnee().getINGx(eleves_extrait.get(0).getPromoEleve()).get(cptUE).getMatieres().size()) {
                    nbMaxMat = eleves_extrait.get(0).getAnnee().getINGx(eleves_extrait.get(0).getPromoEleve()).get(cptUE).getMatieres().size();
                }
            }
            float[][][] liste_notes = new float[eleves_extrait.get(0).getAnnee().getINGx(eleves_extrait.get(0).getPromoEleve()).size() + 1][nbMaxMat][eleves_extrait.size()];
            for (cptEleve = 0; cptEleve <= eleves_extrait.size() - 1; cptEleve++) {
                for (cptUE = 0; cptUE < eleves_extrait.get(cptEleve).getAnnee().getINGx(eleves_extrait.get(cptEleve).getPromoEleve()).size(); cptUE++) {
                    for (cptMat = 0; cptMat <= eleves_extrait.get(cptEleve).getAnnee().getINGx(eleves_extrait.get(cptEleve).getPromoEleve()).get(cptUE).getMatieres().size() - 1; cptMat++) {
                        liste_notes[cptUE][cptMat][cptEleve] = (eleves_extrait.get(cptEleve).getAnnee().getINGx(eleves_extrait.get(cptEleve).getPromoEleve()).get(cptUE).getMatieres().get(cptMat).getNoteMatiere());
                    }
                }
            }
            for (cptEleve = 0; cptEleve <= eleves_extrait.size() - 1; cptEleve++) {
                for (cptUE = 0; cptUE < eleves_extrait.get(cptEleve).getAnnee().getINGx(eleves_extrait.get(cptEleve).getPromoEleve()).size(); cptUE++) {
                    for (cptMat = 0; cptMat <= eleves_extrait.get(cptEleve).getAnnee().getINGx(eleves_extrait.get(cptEleve).getPromoEleve()).get(cptUE).getMatieres().size() - 1; cptMat++) {
                        Arrays.sort(liste_notes[cptUE][cptMat]);
                    }
                }
            }

            for (cptEleve = 0; cptEleve <= eleves_extrait.size() - 1; cptEleve++) {
                Extraction.ExtracteurInformationIndividuel(Extraction.SearchIndexEleveIndividuel(eleves_extrait.get(cptEleve)), eleves_extrait.get(cptEleve), eleves_extrait.get(cptEleve).getPromoEleve());

                Sheet feuille = wb.createSheet(eleves_extrait.get(cptEleve).getNomEleve());
                ArrayList<Row> listeRow = new ArrayList<Row>();

                int nombre_ligne_feuille_individuel = 23 + (eleves_extrait.get(cptEleve).getAnnee().getINGx(eleves_extrait.get(cptEleve).getPromoEleve()).size()) * 3;
                // on ajoute le nombre de ligne pour chaque matiere de chaque UE
                for (cptMatByUE = 0; cptMatByUE <= eleves_extrait.get(cptEleve).getAnnee().getINGx(eleves_extrait.get(cptEleve).getPromoEleve()).size() - 1; cptMatByUE++) {
                    nombre_ligne_feuille_individuel += eleves_extrait.get(cptEleve).getAnnee().getINGx(eleves_extrait.get(cptEleve).getPromoEleve()).get(cptMatByUE).getMatieres().size();
                }
                // Crï¿½ation du nombre de lignes nï¿½cessaire pour la suite
                for (cptFeuil1 = 0; cptFeuil1 <= nombre_ligne_feuille_individuel; cptFeuil1++) {
                    Row ligne = feuille.createRow((short) cptFeuil1);
                    listeRow.add(ligne);
                }

                /**
                 * CrÃ©ation lignes d'entete pareille pour tous
                 */
                feuille.addMergedRegion(new CellRangeAddress(
                        1, // mention first row here
                        1, //mention last row here, it is 1 as we are doing a column wise merging
                        0, //mention first column of merging
                        8 //mention last column to include in merge
                ));
                // Titre + premiere ligne d'info
                listeRow.get(1).createCell(0).setCellValue("BULLETIN DE NOTES");
                listeRow.get(1).getCell(0).setCellStyle(styleTitrePrincipal);

                listeRow.get(3).createCell(0).setCellValue("Nom: " + eleves_extrait.get(cptEleve).getNomEleve());
                listeRow.get(3).createCell(6).setCellValue("Formation:");
                listeRow.get(3).createCell(7).setCellValue("3iL ingénieur");
                // deuxieme ligne d'info
                listeRow.get(5).createCell(0).setCellValue("Prénom: " + eleves_extrait.get(cptEleve).getPrenomEleve());
                listeRow.get(5).createCell(6).setCellValue("Classe:");
                switch(eleves_extrait.get(cptEleve).getPromoEleve()) {
                    case "ING1": listeRow.get(5).createCell(7).setCellValue("1ère année");
                        break;
                    case "ING2":listeRow.get(5).createCell(7).setCellValue("2ème année");
                        break;
                    case "ING3": listeRow.get(5).createCell(7).setCellValue("3ème année");
                        break;
                    default: listeRow.get(5).createCell(7).setCellValue("");
                }
                // Troisieme ligne d'info
                listeRow.get(7).createCell(0).setCellValue("Adresse: " + eleves_extrait.get(cptEleve).getAdresse().replace("\n", " "));
                feuille.addMergedRegion(new CellRangeAddress(
                        7, // mention first row here
                        7, //mention last row here, it is 1 as we are doing a column wise merging
                        0, //mention first column of merging
                        5 //mention last column to include in merge
                ));
                listeRow.get(7).createCell(6).setCellValue("Nombre d'élèves:");
                listeRow.get(7).createCell(7).setCellValue(eleves_extrait.size());
                listeRow.get(7).getCell(7).setCellStyle(styleAlignLeft);
                // Quatrieme ligne d'info
                listeRow.get(9).createCell(0).setCellValue("Date de naissance: " + eleves_extrait.get(cptEleve).getDateNaissance());
                listeRow.get(9).createCell(7).setCellValue(eleves_extrait.get(cptEleve).getAnnee().getDate());

                String format = "dd/MM/yy";
                java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat( format );
                java.util.Date date = new java.util.Date();

                listeRow.get(10).createCell(0).setCellValue("Relevé du " + formater.format( date ));

                /**
                 * Crï¿½ation des lignes pour les UE et les matieres
                 */
                offset = 11;
                for (cptUE = 0; cptUE < eleves_extrait.get(cptEleve).getAnnee().getINGx(eleves_extrait.get(cptEleve).getPromoEleve()).size(); cptUE++) {
                    // Premiere ligne
                    listeRow.get(offset).createCell(0).setCellValue(eleves_extrait.get(cptEleve).getAnnee().getINGx(eleves_extrait.get(cptEleve).getPromoEleve()).get(cptUE).getNomUE().toUpperCase());
                    listeRow.get(offset).getCell(0).setCellStyle(styleTitreUE);
                    feuille.addMergedRegion(new CellRangeAddress(
                            offset, // mention first row here
                            offset + 2, //mention last row here, it is 1 as we are doing a column wise merging
                            0, //mention first column of merging
                            0 //mention last column to include in merge
                    ));
                    RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset, offset + 2, 0, 0), feuille, wb);
                    RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset, offset + 2, 0, 0), feuille, wb);
                    RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset, offset + 2, 0, 0), feuille, wb);
                    RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset, offset + 2, 0, 0), feuille, wb);
                    RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset, offset + 2, 0, 0), feuille, wb);

                    feuille.addMergedRegion(new CellRangeAddress(
                            offset, // mention first row here
                            offset, //mention last row here, it is 1 as we are doing a column wise merging
                            1, //mention first column of merging
                            8 //mention last column to include in merge
                    ));
                    RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset, offset, 1, 8), feuille, wb);
                    RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset, offset, 1, 8), feuille, wb);
                    RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset, offset, 1, 8), feuille, wb);
                    RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset, offset, 1, 8), feuille, wb);
                    RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset, offset, 1, 8), feuille, wb);

                    // Deuxieme ligne
                    listeRow.get(offset + 1).createCell(1).setCellValue("Elève");
                    feuille.addMergedRegion(new CellRangeAddress(
                            offset + 1, // mention first row here
                            offset + 1, //mention last row here, it is 1 as we are doing a column wise merging
                            1, //mention first column of merging
                            2 //mention last column to include in merge
                    ));
                    listeRow.get(offset + 1).getCell(1).setCellStyle(styleTitre);

                    listeRow.get(offset + 1).createCell(3).setCellValue("Classe");
                    feuille.addMergedRegion(new CellRangeAddress(
                            offset + 1, // mention first row here
                            offset + 1, //mention last row here, it is 1 as we are doing a column wise merging
                            3, //mention first column of merging
                            5 //mention last column to include in merge
                    ));
                    listeRow.get(offset + 1).getCell(3).setCellStyle(styleTitre);

                    listeRow.get(offset + 1).createCell(6).setCellValue("Enseignant");
                    listeRow.get(offset + 1).getCell(6).setCellStyle(styleTitre);
                    listeRow.get(offset + 1).createCell(7).setCellValue("Observation");
                    listeRow.get(offset + 1).getCell(7).setCellStyle(styleTitre);
                    listeRow.get(offset + 1).createCell(8).setCellValue("Coef.");
                    listeRow.get(offset + 1).getCell(8).setCellStyle(styleTitre);
                    // Troisieme ligne
                    listeRow.get(offset + 2).createCell(1).setCellValue("Moy.(*)");
                    listeRow.get(offset + 2).getCell(1).setCellStyle(style);
                    listeRow.get(offset + 2).createCell(2).setCellValue("Rang");
                    listeRow.get(offset + 2).getCell(2).setCellStyle(style);
                    listeRow.get(offset + 2).createCell(3).setCellValue("Moy.");
                    listeRow.get(offset + 2).getCell(3).setCellStyle(style);
                    listeRow.get(offset + 2).createCell(4).setCellValue("Min");
                    listeRow.get(offset + 2).getCell(4).setCellStyle(style);
                    listeRow.get(offset + 2).createCell(5).setCellValue("Max");
                    listeRow.get(offset + 2).getCell(5).setCellStyle(style);

                    feuille.addMergedRegion(new CellRangeAddress(
                            offset + 2, // mention first row here
                            offset + 2, //mention last row here, it is 1 as we are doing a column wise merging
                            6, //mention first column of merging
                            8 //mention last column to include in merge
                    ));
                    RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 2, offset + 2, 6, 8), feuille, wb);
                    RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 2, offset + 2, 6, 8), feuille, wb);
                    RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 2, offset + 2, 6, 8), feuille, wb);
                    RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 2, offset + 2, 6, 8), feuille, wb);
                    RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 2, offset + 2, 6, 8), feuille, wb);

                    // Une ligne par matiere
                    for (cptMat = 0; cptMat <= eleves_extrait.get(cptEleve).getAnnee().getINGx(eleves_extrait.get(cptEleve).getPromoEleve()).get(cptUE).getMatieres().size() - 1; cptMat++) {
                        listeRow.get(offset + 3 + cptMat).createCell(0).setCellValue(eleves_extrait.get(cptEleve).getAnnee().getINGx(eleves_extrait.get(cptEleve).getPromoEleve()).get(cptUE).getMatieres().get(cptMat).getNomMatiere());
                        listeRow.get(offset + 3 + cptMat).getCell(0).setCellStyle(style);
                        listeRow.get(offset + 3 + cptMat).createCell(1).setCellValue(df.format(eleves_extrait.get(cptEleve).getAnnee().getINGx(eleves_extrait.get(cptEleve).getPromoEleve()).get(cptUE).getMatieres().get(cptMat).getNoteMatiere()));
                        listeRow.get(offset + 3 + cptMat).getCell(1).setCellStyle(styleNote);

                        float moyenne = 0;
                        int rang = 1;
                        for (float moy : liste_notes[cptUE][cptMat]) {
                            moyenne += moy;
                            if (moy > eleves_extrait.get(cptEleve).getAnnee().getINGx(eleves_extrait.get(cptEleve).getPromoEleve()).get(cptUE).getMatieres().get(cptMat).getNoteMatiere()) {
                                rang++;
                            }
                        }
                        listeRow.get(offset + 3 + cptMat).createCell(2).setCellValue(rang);
                        listeRow.get(offset + 3 + cptMat).getCell(2).setCellStyle(style);
                        listeRow.get(offset + 3 + cptMat).createCell(3).setCellValue(df.format(moyenne / eleves_extrait.size()));
                        listeRow.get(offset + 3 + cptMat).getCell(3).setCellStyle(style);

                        listeRow.get(offset + 3 + cptMat).createCell(4).setCellValue(df.format(liste_notes[cptUE][cptMat][0]));
                        listeRow.get(offset + 3 + cptMat).getCell(4).setCellStyle(style);
                        listeRow.get(offset + 3 + cptMat).createCell(5).setCellValue(df.format(liste_notes[cptUE][cptMat][eleves_extrait.size() - 1]));
                        listeRow.get(offset + 3 + cptMat).getCell(5).setCellStyle(style);
                        // deux infos des generales
                        listeRow.get(offset + 3 + cptMat).createCell(6).setCellValue(eleves_extrait.get(cptEleve).getAnnee().getINGx(eleves_extrait.get(cptEleve).getPromoEleve()).get(cptUE).getParamUE().getMatieresUE().get(cptMat).getEnseignant());
                        listeRow.get(offset + 3 + cptMat).getCell(6).setCellStyle(style);
                        listeRow.get(offset + 3 + cptMat).createCell(7).setCellValue(eleves_extrait.get(cptEleve).getAnnee().getINGx(eleves_extrait.get(cptEleve).getPromoEleve()).get(cptUE).getParamUE().getMatieresUE().get(cptMat).getObservation());
                        listeRow.get(offset + 3 + cptMat).getCell(7).setCellStyle(style);

                        listeRow.get(offset + 3 + cptMat).createCell(8).setCellValue(eleves_extrait.get(cptEleve).getAnnee().getINGx(eleves_extrait.get(cptEleve).getPromoEleve()).get(cptUE).getParamUE().getMatieresUE().get(cptMat).getCoeffMatiere());
                        listeRow.get(offset + 3 + cptMat).getCell(8).setCellStyle(style);
                    }

                    // On augmente l'offset
                    offset += 3 + (eleves_extrait.get(cptEleve).getAnnee().getINGx(eleves_extrait.get(cptEleve).getPromoEleve()).get(cptUE).getMatieres().size());
                }

                // Lignes sur les information gï¿½nï¿½rale a la fin
                listeRow.get(offset + 1).createCell(0).setCellValue("MOYENNE GENERALE");
                listeRow.get(offset + 1).getCell(0).setCellStyle(style);
                listeRow.get(offset + 1).createCell(1).setCellValue(df.format(eleves_extrait.get(cptEleve).getMoyenneGenerale()));
                feuille.addMergedRegion(new CellRangeAddress(
                        offset + 1, // mention first row here
                        offset + 1, //mention last row here, it is 1 as we are doing a column wise merging
                        1, //mention first column of merging
                        6 //mention last column to include in merge
                ));
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 1, offset + 1, 1, 6), feuille, wb);
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 1, offset + 1, 1, 6), feuille, wb);
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 1, offset + 1, 1, 6), feuille, wb);
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 1, offset + 1, 1, 6), feuille, wb);
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 1, offset + 1, 1, 6), feuille, wb);
                listeRow.get(offset + 1).createCell(7).setCellValue(eleves_extrait.get(cptEleve).getToeic());
                feuille.addMergedRegion(new CellRangeAddress(
                        offset + 1, // mention first row here
                        offset + 1, //mention last row here, it is 1 as we are doing a column wise merging
                        7, //mention first column of merging
                        8 //mention last column to include in merge
                ));
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 1, offset + 1, 7, 8), feuille, wb);
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 1, offset + 1, 7, 8), feuille, wb);
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 1, offset + 1, 7, 8), feuille, wb);
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 1, offset + 1, 7, 8), feuille, wb);
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 1, offset + 1, 7, 8), feuille, wb);
                listeRow.get(offset + 2).createCell(0).setCellValue("Observation générales");
                listeRow.get(offset + 2).getCell(0).setCellStyle(style);
                listeRow.get(offset + 2).createCell(1).setCellValue(eleves_extrait.get(cptEleve).getCommentaire().replace("\n", " "));
                feuille.addMergedRegion(new CellRangeAddress(
                        offset + 2, // mention first row here
                        offset + 2, //mention last row here, it is 1 as we are doing a column wise merging
                        1, //mention first column of merging
                        8 //mention last column to include in merge
                ));
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 2, offset + 2, 1, 8), feuille, wb);
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 2, offset + 2, 1, 8), feuille, wb);
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 2, offset + 2, 1, 8), feuille, wb);
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 2, offset + 2, 1, 8), feuille, wb);
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 2, offset + 2, 1, 8), feuille, wb);
                listeRow.get(offset + 3).createCell(0).setCellValue("Responsable de classe");
                listeRow.get(offset + 3).getCell(0).setCellStyle(style);
                listeRow.get(offset + 3).createCell(1).setCellValue(eleves_extrait.get(cptEleve).getCommentaireResponsable().replace("\n", " "));
                feuille.addMergedRegion(new CellRangeAddress(
                        offset + 3, // mention first row here
                        offset + 3, //mention last row here, it is 1 as we are doing a column wise merging
                        1, //mention first column of merging
                        8 //mention last column to include in merge
                ));
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 3, offset + 3, 1, 8), feuille, wb);
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 3, offset + 3, 1, 8), feuille, wb);
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 3, offset + 3, 1, 8), feuille, wb);
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 3, offset + 3, 1, 8), feuille, wb);
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 3, offset + 3, 1, 8), feuille, wb);
                listeRow.get(offset + 5).createCell(0).setCellValue("Absences");
                feuille.addMergedRegion(new CellRangeAddress(
                        offset + 5, // mention first row here
                        offset + 5, //mention last row here, it is 1 as we are doing a column wise merging
                        0, //mention first column of merging
                        8 //mention last column to include in merge
                ));
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 5, offset + 5, 0, 8), feuille, wb);
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 5, offset + 5, 0, 8), feuille, wb);
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 5, offset + 5, 0, 8), feuille, wb);
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 5, offset + 5, 0, 8), feuille, wb);
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 5, offset + 5, 0, 8), feuille, wb);
                listeRow.get(offset + 6).createCell(0).setCellValue("Du");
                listeRow.get(offset + 6).getCell(0).setCellStyle(style);
                listeRow.get(offset + 6).createCell(1).setCellValue("Au");
                feuille.addMergedRegion(new CellRangeAddress(
                        offset + 6, // mention first row here
                        offset + 6, //mention last row here, it is 1 as we are doing a column wise merging
                        1, //mention first column of merging
                        5 //mention last column to include in merge
                ));
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 6, offset + 6, 1, 5), feuille, wb);
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 6, offset + 6, 1, 5), feuille, wb);
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 6, offset + 6, 1, 5), feuille, wb);
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 6, offset + 6, 1, 5), feuille, wb);
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 6, offset + 6, 1, 5), feuille, wb);
                //listeRow.get(offset + 6).createCell(0).setCellValue("Du");
                //listeRow.get(offset + 6).createCell(1).setCellValue("Au");
                listeRow.get(offset + 7).createCell(0).setCellValue("De");
                listeRow.get(offset + 7).getCell(0).setCellStyle(style);
                listeRow.get(offset + 7).createCell(1).setCellValue("A");
                feuille.addMergedRegion(new CellRangeAddress(
                        offset + 7, // mention first row here
                        offset + 7, //mention last row here, it is 1 as we are doing a column wise merging
                        1, //mention first column of merging
                        5 //mention last column to include in merge
                ));
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 7, offset + 7, 1, 5), feuille, wb);
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 7, offset + 7, 1, 5), feuille, wb);
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 7, offset + 7, 1, 5), feuille, wb);
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 7, offset + 7, 1, 5), feuille, wb);
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 7, offset + 7, 1, 5), feuille, wb);
                listeRow.get(offset + 7).createCell(6).setCellStyle(style);
                listeRow.get(offset + 7).createCell(7).setCellStyle(style);
                listeRow.get(offset + 7).createCell(8).setCellStyle(style);
                listeRow.get(offset + 6).createCell(6).setCellValue("Type");
                listeRow.get(offset + 6).getCell(6).setCellStyle(style);
                listeRow.get(offset + 6).createCell(7).setCellValue("Observation");
                listeRow.get(offset + 6).getCell(7).setCellStyle(style);
                listeRow.get(offset + 6).createCell(8).setCellValue("Durée");
                listeRow.get(offset + 6).getCell(8).setCellStyle(style);

                listeRow.get(offset + 9).createCell(0).setCellValue("Cumul Types");
                feuille.addMergedRegion(new CellRangeAddress(
                        offset + 9, // mention first row here
                        offset + 9, //mention last row here, it is 1 as we are doing a column wise merging
                        0, //mention first column of merging
                        8 //mention last column to include in merge
                ));
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 9, offset + 9, 0, 8), feuille, wb);
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 9, offset + 9, 0, 8), feuille, wb);
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 9, offset + 9, 0, 8), feuille, wb);
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 9, offset + 9, 0, 8), feuille, wb);
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 9, offset + 9, 0, 8), feuille, wb);
                listeRow.get(offset + 10).createCell(0).setCellValue("Total");
                listeRow.get(offset + 10).getCell(0).setCellStyle(style);
                feuille.addMergedRegion(new CellRangeAddress(
                        offset + 10, // mention first row here
                        offset + 10, //mention last row here, it is 1 as we are doing a column wise merging
                        1, //mention first column of merging
                        8 //mention last column to include in merge
                ));
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 10, offset + 10, 1, 8), feuille, wb);
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 10, offset + 10, 1, 8), feuille, wb);
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 10, offset + 10, 1, 8), feuille, wb);
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 10, offset + 10, 1, 8), feuille, wb);
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 10, offset + 10, 1, 8), feuille, wb);

                feuille.setFitToPage(true);
                feuille.autoSizeColumn(0, true);
                feuille.autoSizeColumn(1, true);
                feuille.autoSizeColumn(2, true);
                feuille.autoSizeColumn(3, true);
                feuille.autoSizeColumn(4, true);
                feuille.autoSizeColumn(5, true);
                feuille.autoSizeColumn(6, true);
                feuille.autoSizeColumn(7, true);
                feuille.autoSizeColumn(8, true);
            }

            FileOutputStream fileOut;
            try {
                fileOut = new FileOutputStream(cheminFichierSortie+"\\bulletinsIndividuel" + NomFichierPromo + Promo + ".xlsx");
                wb.write(fileOut);
                fileOut.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // JAVAFX Alert
        	alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("Information");
        	alert.setHeaderText("Information");
        	alert.setContentText("Bulletins individuels générés");
        	alert.showAndWait();
            
        }
    }

    /**
     * Fonction gï¿½nï¿½rant un bulletin de note individuel
     */
    public void bulletinIndividuel(String Nom, String Prenom, String Promo, String NomFichierPromo) throws ParseException, IOException {
        int cptFeuil1, cptMatByUE, cptEleve, cptUE, offset, cptMat, indexEleve, nbMaxMat;

        //
        String cheminFichierSortie= new Extracteur("ressources\\ParametrageAccesFichier.xml").ExtracteurCheminFichierDistant("Sorties");
        String cheminFichierGeneral = new Extracteur("ressources\\ParametrageAccesFichier.xml").ExtracteurCheminFichierDistant("Resources");
        String cheminFichierParam = new Extracteur("ressources\\ParametrageAccesFichier.xml").ExtracteurCheminFichierDistant("Parametrages");

        //
        File fichierGeneral = new File(cheminFichierGeneral + "\\" + NomFichierPromo + Promo + ".csv");
        File fichierParam = new File(cheminFichierParam+"\\Parametrage-" + NomFichierPromo + Promo + ".xml");
        File fichierIndiv = new File(cheminFichierGeneral+"\\Individuels" + NomFichierPromo + Promo + ".xls");

        if( !fichierGeneral.exists() ) {
        	// JAVAFX Alert
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("Information");
        	alert.setHeaderText("Information");
        	alert.setContentText("Fichier " + NomFichierPromo+Promo+".csv introuvable");
        	alert.showAndWait();
        } else if ( !fichierParam.exists() ) {
        	// JAVAFX Alert
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("Information");
        	alert.setHeaderText("Information");
        	alert.setContentText("Fichier " + "Parametrage-"+NomFichierPromo + Promo+".xml introuvable");
        	alert.showAndWait();
        } else if ( !fichierIndiv.exists() ) {
        	// JAVAFX Alert
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("Information");
        	alert.setHeaderText("Information");
        	alert.setContentText("Fichier Individuels" + NomFichierPromo+Promo + ".xls introuvable");
        	alert.showAndWait();
        } else {
        	// JAVAFX Alert
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("Information");
        	alert.setHeaderText("Information");
        	alert.setContentText("Fichier " + "Génération des bulletins individuel en cours");
        	alert.showAndWait();

            Extracteur Extraction = new Extracteur(cheminFichierGeneral+"\\" + NomFichierPromo + Promo + ".csv");
            ArrayList<Eleve> eleves_extrait = Extraction.ExtracteurEleves(NomFichierPromo + Promo);
            eleves_extrait = Extraction.ExtracteurNotesToutesAnnee(eleves_extrait);
            Extraction.setCheminFichier(cheminFichierGeneral+"\\Individuels"+NomFichierPromo+Promo+".xls");
            alert.showAndWait();

            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            // Normalement il devrait y avoir une recherche de l'index de l'eleve
            // et une recherhe de l'index dans le fichier excel

            indexEleve = 0;
            while (indexEleve <= eleves_extrait.size() - 1 && !eleves_extrait.get(indexEleve).cmpElevetoValue(Nom, Prenom, Promo)) {
                indexEleve++;
            }

            //1. Crï¿½er un Document vide
            XSSFWorkbook wb = new XSSFWorkbook();
            //
            XSSFCellStyle style = wb.createCellStyle();
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            //
            XSSFCellStyle styleNote = wb.createCellStyle();
            styleNote.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            styleNote.setBorderTop(HSSFCellStyle.BORDER_THIN);
            styleNote.setBorderRight(HSSFCellStyle.BORDER_THIN);
            styleNote.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            //
            XSSFCellStyle styleTitre = wb.createCellStyle();
            styleTitre.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            styleTitre.setBorderTop(HSSFCellStyle.BORDER_THIN);
            styleTitre.setBorderRight(HSSFCellStyle.BORDER_THIN);
            styleTitre.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            styleTitre.setAlignment(styleTitre.ALIGN_CENTER);
            //
            XSSFCellStyle styleTitreUE = wb.createCellStyle();
            styleTitreUE.setAlignment(styleTitreUE.ALIGN_CENTER);
            //
            XSSFCellStyle styleTitrePrincipal = wb.createCellStyle();
            styleTitrePrincipal.setAlignment(styleTitrePrincipal.ALIGN_CENTER);
            //
            XSSFCellStyle styleAlignLeft = wb.createCellStyle();
            styleAlignLeft.setAlignment(styleTitrePrincipal.ALIGN_LEFT);
            //
            XSSFFont font = wb.createFont();
            font.setBold(true);

            font.setFontHeightInPoints((short) 16);
            font.setFontName("Calibri");  
            styleTitrePrincipal.setFont(font);
            //
            XSSFFont fontUE = wb.createFont();
            fontUE.setBold(true);
            fontUE.setFontHeightInPoints((short) 11);
            fontUE.setFontName("Calibri");  
            styleTitreUE.setFont(fontUE);
            //
            XSSFFont fontNote = wb.createFont();
            fontNote.setBold(true);
            fontNote.setFontHeightInPoints((short) 11);
            fontNote.setFontName("Calibri");  
            styleNote.setFont(fontNote);

            nbMaxMat = 0;
            for (cptUE = 0; cptUE < eleves_extrait.get(0).getAnnee().getINGx(eleves_extrait.get(0).getPromoEleve()).size(); cptUE++) {
                if (nbMaxMat < eleves_extrait.get(0).getAnnee().getINGx(eleves_extrait.get(0).getPromoEleve()).get(cptUE).getMatieres().size()) {
                    nbMaxMat = eleves_extrait.get(0).getAnnee().getINGx(eleves_extrait.get(0).getPromoEleve()).get(cptUE).getMatieres().size();
                }
            }
            // 0 mit en dur a la place du numero d'eleve peux posï¿½ problï¿½me ???????
            float[][][] liste_notes = new float[eleves_extrait.get(0).getAnnee().getINGx(eleves_extrait.get(0).getPromoEleve()).size() + 1][nbMaxMat][eleves_extrait.size()];
            //float[][][] liste_notes = new float[100][100][100];
            for (cptEleve = 0; cptEleve <= eleves_extrait.size() - 1; cptEleve++) {
                for (cptUE = 0; cptUE < eleves_extrait.get(cptEleve).getAnnee().getINGx(eleves_extrait.get(cptEleve).getPromoEleve()).size(); cptUE++) {
                    for (cptMat = 0; cptMat <= eleves_extrait.get(cptEleve).getAnnee().getINGx(eleves_extrait.get(cptEleve).getPromoEleve()).get(cptUE).getMatieres().size() - 1; cptMat++) {
                        liste_notes[cptUE][cptMat][cptEleve] = (eleves_extrait.get(cptEleve).getAnnee().getINGx(eleves_extrait.get(cptEleve).getPromoEleve()).get(cptUE).getMatieres().get(cptMat).getNoteMatiere());
                    }
                }
            }
            for (cptEleve = 0; cptEleve <= eleves_extrait.size() - 1; cptEleve++) {
                for (cptUE = 0; cptUE < eleves_extrait.get(cptEleve).getAnnee().getINGx(eleves_extrait.get(cptEleve).getPromoEleve()).size(); cptUE++) {
                    for (cptMat = 0; cptMat <= eleves_extrait.get(cptEleve).getAnnee().getINGx(eleves_extrait.get(cptEleve).getPromoEleve()).get(cptUE).getMatieres().size() - 1; cptMat++) {
                        Arrays.sort(liste_notes[cptUE][cptMat]);
                    }
                }
            }

            //System.out.println(Extraction.SearchIndexEleveIndividuel(eleves_extrait.get(cptEleve)));
            if (indexEleve == eleves_extrait.size() || Extraction.SearchIndexEleveIndividuel(eleves_extrait.get(indexEleve)) == -1) {
                //javax.swing.JOptionPane.showMessageDialog(null, "Erreur Ã©lÃ¨ve introuvable !");
            } else {
                Extraction.ExtracteurInformationIndividuel(Extraction.SearchIndexEleveIndividuel(eleves_extrait.get(indexEleve)), eleves_extrait.get(indexEleve), eleves_extrait.get(indexEleve).getPromoEleve());

                //2. Crï¿½er une Feuille de calcul vide
                /**
                 * Tous les get(0) doivent etre changer pour l eleve voulu
                 */
                Sheet feuille = wb.createSheet(eleves_extrait.get(indexEleve).getNomEleve());
                ArrayList<Row> listeRow = new ArrayList<Row>();

                int nombre_ligne_feuille_individuel = 23 + (eleves_extrait.get(indexEleve).getAnnee().getINGx(eleves_extrait.get(indexEleve).getPromoEleve()).size()) * 3;
                // on ajoute le nombre de ligne pour chaque matiere de chaque UE
                for (cptMatByUE = 0; cptMatByUE <= eleves_extrait.get(indexEleve).getAnnee().getINGx(eleves_extrait.get(indexEleve).getPromoEleve()).size() - 1; cptMatByUE++) {
                    nombre_ligne_feuille_individuel += eleves_extrait.get(indexEleve).getAnnee().getINGx(eleves_extrait.get(indexEleve).getPromoEleve()).get(cptMatByUE).getMatieres().size();
                }
                // Crï¿½ation du nombre de lignes nï¿½cessaire pour la suite
                for (cptFeuil1 = 0; cptFeuil1 <= nombre_ligne_feuille_individuel; cptFeuil1++) {
                    Row ligne = feuille.createRow((short) cptFeuil1);
                    listeRow.add(ligne);
                }

                /**
                 * CrÃ©ation lignes d'entete pareille pour tous
                 */
                feuille.addMergedRegion(new CellRangeAddress(
                        1, // mention first row here
                        1, //mention last row here, it is 1 as we are doing a column wise merging
                        0, //mention first column of merging
                        8 //mention last column to include in merge
                ));
                // Titre + premiere ligne d'info
                listeRow.get(1).createCell(0).setCellValue("BULLETIN DE NOTES");
                listeRow.get(1).getCell(0).setCellStyle(styleTitrePrincipal);

                listeRow.get(3).createCell(0).setCellValue("Nom: " + eleves_extrait.get(indexEleve).getNomEleve());
                //listeRow.get(3).createCell(1).setCellValue(eleves_extrait.get(indexEleve).getNomEleve());
                listeRow.get(3).createCell(6).setCellValue("Formation:");
                listeRow.get(3).createCell(7).setCellValue("3iL ingénieur");
                // deuxieme ligne d'info
                listeRow.get(5).createCell(0).setCellValue("Prénom: " + eleves_extrait.get(indexEleve).getPrenomEleve());
                //listeRow.get(5).createCell(1).setCellValue(eleves_extrait.get(indexEleve).getPrenomEleve());
                listeRow.get(5).createCell(6).setCellValue("Classe:");
                switch(eleves_extrait.get(indexEleve).getPromoEleve()) {
                    case "ING1": listeRow.get(5).createCell(7).setCellValue("1ère année");
                        break;
                    case "ING2":listeRow.get(5).createCell(7).setCellValue("2ème année");
                        break;
                    case "ING3": listeRow.get(5).createCell(7).setCellValue("3ème année");
                        break;
                    default: listeRow.get(5).createCell(7).setCellValue("");
                }
                // Troisieme ligne d'info
                listeRow.get(7).createCell(0).setCellValue("Adresse: " + eleves_extrait.get(indexEleve).getAdresse().replace("\n", " "));
                feuille.addMergedRegion(new CellRangeAddress(
                        7, // mention first row here
                        7, //mention last row here, it is 1 as we are doing a column wise merging
                        0, //mention first column of merging
                        5 //mention last column to include in merge
                ));
                //listeRow.get(7).createCell(1).setCellValue(eleves_extrait.get(indexEleve).getAdresse());
                listeRow.get(7).createCell(6).setCellValue("Nombre d'élèves:");
                listeRow.get(7).createCell(7).setCellValue(eleves_extrait.size());
                listeRow.get(7).getCell(7).setCellStyle(styleAlignLeft);
                // Quatrieme ligne d'info
                listeRow.get(9).createCell(0).setCellValue("Date de naissance: " + eleves_extrait.get(indexEleve).getDateNaissance());
                listeRow.get(9).createCell(7).setCellValue(eleves_extrait.get(indexEleve).getAnnee().getDate());

                String format = "dd/MM/yy";
                java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat( format );
                java.util.Date date = new java.util.Date();

                listeRow.get(10).createCell(0).setCellValue("Relevé du " + formater.format( date ));
                //listeRow.get(9).createCell(1).setCellValue(eleves_extrait.get(indexEleve).getDateNaissance());

                /**
                 * CrÃ©ation des lignes pour les UE et les matieres
                 */
                offset = 11;
                for (cptUE = 0; cptUE < eleves_extrait.get(indexEleve).getAnnee().getINGx(eleves_extrait.get(indexEleve).getPromoEleve()).size(); cptUE++) {
                    // Premiere ligne
                    listeRow.get(offset).createCell(0).setCellValue(eleves_extrait.get(indexEleve).getAnnee().getINGx(eleves_extrait.get(indexEleve).getPromoEleve()).get(cptUE).getNomUE().toUpperCase());
                    listeRow.get(offset).getCell(0).setCellStyle(styleTitreUE);
                    feuille.addMergedRegion(new CellRangeAddress(
                            offset, // mention first row here
                            offset + 2, //mention last row here, it is 1 as we are doing a column wise merging
                            0, //mention first column of merging
                            0 //mention last column to include in merge
                    ));
                    RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset, offset + 2, 0, 0), feuille, wb);
                    RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset, offset + 2, 0, 0), feuille, wb);
                    RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset, offset + 2, 0, 0), feuille, wb);
                    RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset, offset + 2, 0, 0), feuille, wb);
                    RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset, offset + 2, 0, 0), feuille, wb);

                    feuille.addMergedRegion(new CellRangeAddress(
                            offset, // mention first row here
                            offset, //mention last row here, it is 1 as we are doing a column wise merging
                            1, //mention first column of merging
                            8 //mention last column to include in merge
                    ));
                    RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset, offset, 1, 8), feuille, wb);
                    RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset, offset, 1, 8), feuille, wb);
                    RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset, offset, 1, 8), feuille, wb);
                    RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset, offset, 1, 8), feuille, wb);
                    RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset, offset, 1, 8), feuille, wb);

                    // Deuxieme ligne
                    listeRow.get(offset + 1).createCell(1).setCellValue("Elève");
                    feuille.addMergedRegion(new CellRangeAddress(
                            offset + 1, // mention first row here
                            offset + 1, //mention last row here, it is 1 as we are doing a column wise merging
                            1, //mention first column of merging
                            2 //mention last column to include in merge
                    ));
                    listeRow.get(offset + 1).getCell(1).setCellStyle(styleTitre);

                    listeRow.get(offset + 1).createCell(3).setCellValue("Classe");
                    feuille.addMergedRegion(new CellRangeAddress(
                            offset + 1, // mention first row here
                            offset + 1, //mention last row here, it is 1 as we are doing a column wise merging
                            3, //mention first column of merging
                            5 //mention last column to include in merge
                    ));
                    listeRow.get(offset + 1).getCell(3).setCellStyle(styleTitre);

                    listeRow.get(offset + 1).createCell(6).setCellValue("Enseignant");
                    listeRow.get(offset + 1).getCell(6).setCellStyle(styleTitre);
                    listeRow.get(offset + 1).createCell(7).setCellValue("Observation");
                    listeRow.get(offset + 1).getCell(7).setCellStyle(styleTitre);
                    listeRow.get(offset + 1).createCell(8).setCellValue("Coef.");
                    listeRow.get(offset + 1).getCell(8).setCellStyle(styleTitre);
                    // Troisieme ligne
                    listeRow.get(offset + 2).createCell(1).setCellValue("Moy.(*)");
                    listeRow.get(offset + 2).getCell(1).setCellStyle(style);
                    listeRow.get(offset + 2).createCell(2).setCellValue("Rang");
                    listeRow.get(offset + 2).getCell(2).setCellStyle(style);
                    listeRow.get(offset + 2).createCell(3).setCellValue("Moy.");
                    listeRow.get(offset + 2).getCell(3).setCellStyle(style);
                    listeRow.get(offset + 2).createCell(4).setCellValue("Min");
                    listeRow.get(offset + 2).getCell(4).setCellStyle(style);
                    listeRow.get(offset + 2).createCell(5).setCellValue("Max");
                    listeRow.get(offset + 2).getCell(5).setCellStyle(style);

                    feuille.addMergedRegion(new CellRangeAddress(
                            offset + 2, // mention first row here
                            offset + 2, //mention last row here, it is 1 as we are doing a column wise merging
                            6, //mention first column of merging
                            8 //mention last column to include in merge
                    ));
                    RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 2, offset + 2, 6, 8), feuille, wb);
                    RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 2, offset + 2, 6, 8), feuille, wb);
                    RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 2, offset + 2, 6, 8), feuille, wb);
                    RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 2, offset + 2, 6, 8), feuille, wb);
                    RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 2, offset + 2, 6, 8), feuille, wb);

                    // Une ligne par matiere
                    for (cptMat = 0; cptMat <= eleves_extrait.get(indexEleve).getAnnee().getINGx(eleves_extrait.get(indexEleve).getPromoEleve()).get(cptUE).getMatieres().size() - 1; cptMat++) {
                        listeRow.get(offset + 3 + cptMat).createCell(0).setCellValue(eleves_extrait.get(indexEleve).getAnnee().getINGx(eleves_extrait.get(indexEleve).getPromoEleve()).get(cptUE).getMatieres().get(cptMat).getNomMatiere());
                        listeRow.get(offset + 3 + cptMat).getCell(0).setCellStyle(style);
                        listeRow.get(offset + 3 + cptMat).createCell(1).setCellValue(df.format(eleves_extrait.get(indexEleve).getAnnee().getINGx(eleves_extrait.get(indexEleve).getPromoEleve()).get(cptUE).getMatieres().get(cptMat).getNoteMatiere()));
                        listeRow.get(offset + 3 + cptMat).getCell(1).setCellStyle(styleNote);

                        float moyenne = 0;
                        int rang = 1;
                        for (float moy : liste_notes[cptUE][cptMat]) {
                            moyenne += moy;
                            if (moy > eleves_extrait.get(indexEleve).getAnnee().getINGx(eleves_extrait.get(indexEleve).getPromoEleve()).get(cptUE).getMatieres().get(cptMat).getNoteMatiere()) {
                                rang++;
                            }
                        }
                        listeRow.get(offset + 3 + cptMat).createCell(2).setCellValue(rang);
                        listeRow.get(offset + 3 + cptMat).getCell(2).setCellStyle(style);
                        listeRow.get(offset + 3 + cptMat).createCell(3).setCellValue(df.format(moyenne / eleves_extrait.size()));
                        listeRow.get(offset + 3 + cptMat).getCell(3).setCellStyle(style);

                        listeRow.get(offset + 3 + cptMat).createCell(4).setCellValue(df.format(liste_notes[cptUE][cptMat][0]));
                        listeRow.get(offset + 3 + cptMat).getCell(4).setCellStyle(style);
                        listeRow.get(offset + 3 + cptMat).createCell(5).setCellValue(df.format(liste_notes[cptUE][cptMat][eleves_extrait.size() - 1]));
                        listeRow.get(offset + 3 + cptMat).getCell(5).setCellStyle(style);
                        listeRow.get(offset + 3 + cptMat).createCell(6).setCellValue(eleves_extrait.get(indexEleve).getAnnee().getINGx(eleves_extrait.get(indexEleve).getPromoEleve()).get(cptUE).getParamUE().getMatieresUE().get(cptMat).getEnseignant());
                        listeRow.get(offset + 3 + cptMat).getCell(6).setCellStyle(style);
                        listeRow.get(offset + 3 + cptMat).createCell(7).setCellValue(eleves_extrait.get(indexEleve).getAnnee().getINGx(eleves_extrait.get(indexEleve).getPromoEleve()).get(cptUE).getParamUE().getMatieresUE().get(cptMat).getObservation());
                        listeRow.get(offset + 3 + cptMat).getCell(7).setCellStyle(style);
                        // Pas sur de remplir avec les bonnes infos
                        listeRow.get(offset + 3 + cptMat).createCell(8).setCellValue(eleves_extrait.get(indexEleve).getAnnee().getINGx(eleves_extrait.get(indexEleve).getPromoEleve()).get(cptUE).getParamUE().getMatieresUE().get(cptMat).getCoeffMatiere());
                        listeRow.get(offset + 3 + cptMat).getCell(8).setCellStyle(style);
                    }

                    // On augmente l'offset
                    offset += 3 + (eleves_extrait.get(indexEleve).getAnnee().getINGx(eleves_extrait.get(indexEleve).getPromoEleve()).get(cptUE).getMatieres().size());
                }

                // Lignes sur les information gÃ©nÃ©rale a la fin
                /**
                 * Peut erte qu'il faut mieux les rÃ©cupÃ©rer dirrectement depuis l'ancien excel et les copier coller ?
                 */
                listeRow.get(offset + 1).createCell(0).setCellValue("MOYENNE GENERALE");
                listeRow.get(offset + 1).getCell(0).setCellStyle(style);
                listeRow.get(offset + 1).createCell(1).setCellValue(df.format(eleves_extrait.get(indexEleve).getMoyenneGenerale()));
                feuille.addMergedRegion(new CellRangeAddress(
                        offset + 1, // mention first row here
                        offset + 1, //mention last row here, it is 1 as we are doing a column wise merging
                        1, //mention first column of merging
                        6 //mention last column to include in merge
                ));
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 1, offset + 1, 1, 6), feuille, wb);
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 1, offset + 1, 1, 6), feuille, wb);
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 1, offset + 1, 1, 6), feuille, wb);
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 1, offset + 1, 1, 6), feuille, wb);
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 1, offset + 1, 1, 6), feuille, wb);
                listeRow.get(offset + 1).createCell(7).setCellValue(eleves_extrait.get(indexEleve).getToeic());
                feuille.addMergedRegion(new CellRangeAddress(
                        offset + 1, // mention first row here
                        offset + 1, //mention last row here, it is 1 as we are doing a column wise merging
                        7, //mention first column of merging
                        8 //mention last column to include in merge
                ));
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 1, offset + 1, 7, 8), feuille, wb);
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 1, offset + 1, 7, 8), feuille, wb);
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 1, offset + 1, 7, 8), feuille, wb);
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 1, offset + 1, 7, 8), feuille, wb);
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 1, offset + 1, 7, 8), feuille, wb);
                listeRow.get(offset + 2).createCell(0).setCellValue("Observation générales");
                listeRow.get(offset + 2).getCell(0).setCellStyle(style);
                listeRow.get(offset + 2).createCell(1).setCellValue(eleves_extrait.get(indexEleve).getCommentaire().replace("\n", " "));
                feuille.addMergedRegion(new CellRangeAddress(
                        offset + 2, // mention first row here
                        offset + 2, //mention last row here, it is 1 as we are doing a column wise merging
                        1, //mention first column of merging
                        8 //mention last column to include in merge
                ));
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 2, offset + 2, 1, 8), feuille, wb);
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 2, offset + 2, 1, 8), feuille, wb);
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 2, offset + 2, 1, 8), feuille, wb);
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 2, offset + 2, 1, 8), feuille, wb);
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 2, offset + 2, 1, 8), feuille, wb);
                listeRow.get(offset + 3).createCell(0).setCellValue("Responsable de classe");
                listeRow.get(offset + 3).getCell(0).setCellStyle(style);
                listeRow.get(offset + 3).createCell(1).setCellValue(eleves_extrait.get(indexEleve).getCommentaireResponsable().replace("\n", " "));
                feuille.addMergedRegion(new CellRangeAddress(
                        offset + 3, // mention first row here
                        offset + 3, //mention last row here, it is 1 as we are doing a column wise merging
                        1, //mention first column of merging
                        8 //mention last column to include in merge
                ));
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 3, offset + 3, 1, 8), feuille, wb);
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 3, offset + 3, 1, 8), feuille, wb);
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 3, offset + 3, 1, 8), feuille, wb);
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 3, offset + 3, 1, 8), feuille, wb);
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 3, offset + 3, 1, 8), feuille, wb);
                listeRow.get(offset + 5).createCell(0).setCellValue("Absences");
                feuille.addMergedRegion(new CellRangeAddress(
                        offset + 5, // mention first row here
                        offset + 5, //mention last row here, it is 1 as we are doing a column wise merging
                        0, //mention first column of merging
                        8 //mention last column to include in merge
                ));
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 5, offset + 5, 0, 8), feuille, wb);
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 5, offset + 5, 0, 8), feuille, wb);
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 5, offset + 5, 0, 8), feuille, wb);
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 5, offset + 5, 0, 8), feuille, wb);
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 5, offset + 5, 0, 8), feuille, wb);
                listeRow.get(offset + 6).createCell(0).setCellValue("Du");
                listeRow.get(offset + 6).getCell(0).setCellStyle(style);
                listeRow.get(offset + 6).createCell(1).setCellValue("Au");
                feuille.addMergedRegion(new CellRangeAddress(
                        offset + 6, // mention first row here
                        offset + 6, //mention last row here, it is 1 as we are doing a column wise merging
                        1, //mention first column of merging
                        5 //mention last column to include in merge
                ));
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 6, offset + 6, 1, 5), feuille, wb);
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 6, offset + 6, 1, 5), feuille, wb);
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 6, offset + 6, 1, 5), feuille, wb);
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 6, offset + 6, 1, 5), feuille, wb);
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 6, offset + 6, 1, 5), feuille, wb);
                //listeRow.get(offset + 6).createCell(0).setCellValue("Du");
                //listeRow.get(offset + 6).createCell(1).setCellValue("Au");
                listeRow.get(offset + 7).createCell(0).setCellValue("De");
                listeRow.get(offset + 7).getCell(0).setCellStyle(style);
                listeRow.get(offset + 7).createCell(1).setCellValue("A");
                feuille.addMergedRegion(new CellRangeAddress(
                        offset + 7, // mention first row here
                        offset + 7, //mention last row here, it is 1 as we are doing a column wise merging
                        1, //mention first column of merging
                        5 //mention last column to include in merge
                ));
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 7, offset + 7, 1, 5), feuille, wb);
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 7, offset + 7, 1, 5), feuille, wb);
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 7, offset + 7, 1, 5), feuille, wb);
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 7, offset + 7, 1, 5), feuille, wb);
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 7, offset + 7, 1, 5), feuille, wb);
                listeRow.get(offset + 7).createCell(6).setCellStyle(style);
                listeRow.get(offset + 7).createCell(7).setCellStyle(style);
                listeRow.get(offset + 7).createCell(8).setCellStyle(style);
                listeRow.get(offset + 6).createCell(6).setCellValue("Type");
                listeRow.get(offset + 6).getCell(6).setCellStyle(style);
                listeRow.get(offset + 6).createCell(7).setCellValue("Observation");
                listeRow.get(offset + 6).getCell(7).setCellStyle(style);
                listeRow.get(offset + 6).createCell(8).setCellValue("Durée");
                listeRow.get(offset + 6).getCell(8).setCellStyle(style);

                listeRow.get(offset + 9).createCell(0).setCellValue("Cumul Types");
                feuille.addMergedRegion(new CellRangeAddress(
                        offset + 9, // mention first row here
                        offset + 9, //mention last row here, it is 1 as we are doing a column wise merging
                        0, //mention first column of merging
                        8 //mention last column to include in merge
                ));
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 9, offset + 9, 0, 8), feuille, wb);
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 9, offset + 9, 0, 8), feuille, wb);
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 9, offset + 9, 0, 8), feuille, wb);
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 9, offset + 9, 0, 8), feuille, wb);
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 9, offset + 9, 0, 8), feuille, wb);
                listeRow.get(offset + 10).createCell(0).setCellValue("Total");
                listeRow.get(offset + 10).getCell(0).setCellStyle(style);
                feuille.addMergedRegion(new CellRangeAddress(
                        offset + 10, // mention first row here
                        offset + 10, //mention last row here, it is 1 as we are doing a column wise merging
                        1, //mention first column of merging
                        8 //mention last column to include in merge
                ));
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 10, offset + 10, 1, 8), feuille, wb);
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 10, offset + 10, 1, 8), feuille, wb);
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 10, offset + 10, 1, 8), feuille, wb);
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 10, offset + 10, 1, 8), feuille, wb);
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(offset + 10, offset + 10, 1, 8), feuille, wb);

                feuille.setFitToPage(true);
                feuille.autoSizeColumn(0, true);
                feuille.autoSizeColumn(1, true);
                feuille.autoSizeColumn(2, true);
                feuille.autoSizeColumn(3, true);
                feuille.autoSizeColumn(4, true);
                feuille.autoSizeColumn(5, true);
                feuille.autoSizeColumn(6, true);
                feuille.autoSizeColumn(7, true);
                feuille.autoSizeColumn(8, true);

                FileOutputStream fileOut;
                try {
                    fileOut = new FileOutputStream(cheminFichierSortie+"\\bulletin" + eleves_extrait.get(indexEleve).getNomEleve() + eleves_extrait.get(indexEleve).getPromoEleve() + ".xlsx");
                    wb.write(fileOut);
                    fileOut.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
                // JAVAFX Alert
            	alert = new Alert(AlertType.INFORMATION);
            	alert.setTitle("Information");
            	alert.setHeaderText("Information");
            	alert.setContentText("Bulletin Individuel généré");
            	alert.showAndWait();
            }
        }
    }

    /**
     * Fonction gï¿½nï¿½rant le bulletin de note gï¿½nï¿½rale avec tous les Ã©lÃ¨ves
     * @throws ParseException
     */
    public void bulletinGeneral(String Promo, String NomFichierPromo) throws ParseException{

        //
        String cheminFichierSortie= new Extracteur("ressources\\ParametrageAccesFichier.xml").ExtracteurCheminFichierDistant("Sorties");
        String cheminFichierGeneral = new Extracteur("ressources\\ParametrageAccesFichier.xml").ExtracteurCheminFichierDistant("Resources");
        String cheminFichierParam = new Extracteur("ressources\\ParametrageAccesFichier.xml").ExtracteurCheminFichierDistant("Parametrages");

        File fichierGeneral = new File(cheminFichierGeneral+"\\"+NomFichierPromo+Promo+".csv");
        File fichierParam = new File(cheminFichierParam+"\\Parametrage-"+NomFichierPromo + Promo+".xml");
        //File fichierIndiv = new File("ressources\\projet.xls");
        int i,j,cptEleve,k,offset, cptUE,cptMat,nbMaxMat,cptFormule;
        float sommeCoef=0,coeffMat=0;
        double moyenneUE=0,sommeCoeffMat=0,sommeNoteUE=0;
        cptMat=0;
        if( !fichierGeneral.exists() ) {
        	// JAVAFX Alert
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("Information");
        	alert.setHeaderText("Information");
        	alert.setContentText("Fichier " + NomFichierPromo+Promo+".csv introuvable");
        	alert.showAndWait();
        } else if ( !fichierParam.exists() ) {
        	// JAVAFX Alert
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("Information");
        	alert.setHeaderText("Information");
        	alert.setContentText("Fichier Parametrage-"+NomFichierPromo + Promo+".xml introuvable");
        	alert.showAndWait();
        } else {
        	// JAVAFX Alert
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("Information");
        	alert.setHeaderText("Information");
        	alert.setContentText("Génération du bulletin général en cours");
        	alert.showAndWait();
        	
            Extracteur Extraction = new Extracteur(cheminFichierGeneral+"\\" + NomFichierPromo + Promo + ".csv");
            ArrayList<Eleve> eleves_extrait = Extraction.ExtracteurEleves(NomFichierPromo + Promo);
            eleves_extrait = Extraction.ExtracteurNotesToutesAnnee(eleves_extrait);
            //Extraction.setCheminFichier("ressources\\projet.xls");

            //
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            
            
        
        
        //1. Crï¿½er un Document vide
        XSSFWorkbook wb = new XSSFWorkbook();
        //
        XSSFCellStyle style = wb.createCellStyle();
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        //
        XSSFCellStyle styleTitre = wb.createCellStyle();
        styleTitre.setAlignment(styleTitre.ALIGN_CENTER);
        //
        XSSFCellStyle styleTitreUE = wb.createCellStyle();
        styleTitreUE.setAlignment(styleTitreUE.ALIGN_CENTER);
        //
        XSSFCellStyle styleTitrePrincipal = wb.createCellStyle();
        styleTitrePrincipal.setAlignment(styleTitrePrincipal.ALIGN_CENTER);
        //
        XSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 16);
        font.setFontName("Calibri");
        font.setBold(true);
        styleTitrePrincipal.setFont(font);
        
        
        //2. Crï¿½er une Feuille de calcul vide
        Sheet feuille = wb.createSheet("Bulletin général");
        ArrayList<Row> listeRow = new ArrayList<Row>();

        // Crï¿½ation du nombre de lignes nï¿½cessaire pour la suite
        for(i=0;i<=9;i++){
            Row ligne = feuille.createRow((short)i);
            listeRow.add(ligne);
        }
        // Crï¿½ation du nombre de lignes nï¿½cessaire pour la suite
        for(j=10;j<=(eleves_extrait.size()+10 );j++){
            Row ligne = feuille.createRow((short)j);
            listeRow.add(ligne);
        }
        
        nbMaxMat = 0;
        for(cptUE=0;cptUE < eleves_extrait.get(0).getAnnee().getINGx(eleves_extrait.get(0).getPromoEleve()).size(); cptUE++) {
            if(nbMaxMat < eleves_extrait.get(0).getAnnee().getINGx(eleves_extrait.get(0).getPromoEleve()).get(cptUE).getMatieres().size()){
                nbMaxMat = eleves_extrait.get(0).getAnnee().getINGx(eleves_extrait.get(0).getPromoEleve()).get(cptUE).getMatieres().size();
            }
        }
        // tableau rang moyenne de la matiere
        float[][][] liste_notes_mat = new float[eleves_extrait.get(0).getAnnee().getINGx(eleves_extrait.get(0).getPromoEleve()).size() + 1][nbMaxMat][eleves_extrait.size()];
        for (cptEleve = 0; cptEleve <= eleves_extrait.size() - 1; cptEleve++) {
            for (cptUE = 0; cptUE < eleves_extrait.get(cptEleve).getAnnee().getINGx(eleves_extrait.get(cptEleve).getPromoEleve()).size(); cptUE++) {
                for (cptMat = 0; cptMat <= eleves_extrait.get(cptEleve).getAnnee().getINGx(eleves_extrait.get(cptEleve).getPromoEleve()).get(cptUE).getMatieres().size() - 1; cptMat++) {
                    liste_notes_mat[cptUE][cptMat][cptEleve] = (eleves_extrait.get(cptEleve).getAnnee().getINGx(eleves_extrait.get(cptEleve).getPromoEleve()).get(cptUE).getMatieres().get(cptMat).getNoteMatiere());
                }
            }
        }
        for (cptEleve = 0; cptEleve <= eleves_extrait.size() - 1; cptEleve++) {
            for (cptUE = 0; cptUE < eleves_extrait.get(cptEleve).getAnnee().getINGx(eleves_extrait.get(cptEleve).getPromoEleve()).size(); cptUE++) {
                for (cptMat = 0; cptMat <= eleves_extrait.get(cptEleve).getAnnee().getINGx(eleves_extrait.get(cptEleve).getPromoEleve()).get(cptUE).getMatieres().size() - 1; cptMat++) {
                    Arrays.sort(liste_notes_mat[cptUE][cptMat]);
                }
            }
        }
        
        // tableau rang moyenne de l'ue
        float[][][] liste_notes_ue = new float[eleves_extrait.size()][eleves_extrait.get(0).getAnnee().getINGx(eleves_extrait.get(0).getPromoEleve()).size()+1][nbMaxMat];
        for(cptEleve=0;cptEleve<=eleves_extrait.size()-1;cptEleve++) {
            for(cptUE=0;cptUE < eleves_extrait.get(cptEleve).getAnnee().getINGx(eleves_extrait.get(cptEleve).getPromoEleve()).size(); cptUE++) {
                for (cptMat = 0; cptMat <= eleves_extrait.get(cptEleve).getAnnee().getINGx(eleves_extrait.get(cptEleve).getPromoEleve()).get(cptUE).getMatieres().size() - 1; cptMat++) {
                    liste_notes_ue[cptEleve][cptUE][cptMat] = (eleves_extrait.get(cptEleve).getAnnee().getINGx(eleves_extrait.get(cptEleve).getPromoEleve()).get(cptUE).getMatieres().get(cptMat).getNoteMatiere());
                }
            }
        }
        
        
        float[][] liste_moyenne_non_trie = new float[eleves_extrait.size()][eleves_extrait.get(0).getAnnee().getINGx(eleves_extrait.get(0).getPromoEleve()).size()+1];
        float[][] liste_moyenne_trie = new float[eleves_extrait.size()][eleves_extrait.get(0).getAnnee().getINGx(eleves_extrait.get(0).getPromoEleve()).size()+1];
        
        //construction du bulletin de note gï¿½nï¿½rale
        listeRow.get(0).createCell(0).setCellValue("CFA SECTION");
        listeRow.get(0).getCell(0).setCellStyle(style);
        listeRow.get(0).createCell(1).setCellValue("NOM SECTION");
        listeRow.get(0).getCell(1).setCellStyle(style);
        listeRow.get(0).createCell(2).setCellValue("NOM PROMO");
        listeRow.get(0).getCell(2).setCellStyle(style);
        listeRow.get(1).createCell(0).setCellValue("RODEZ (3IL)");
        listeRow.get(1).getCell(0).setCellStyle(style);
        
        String date = eleves_extrait.get(0).getAnnee().getDate();
        listeRow.get(1).createCell(1).setCellValue(date);
        listeRow.get(1).getCell(1).setCellStyle(style);
        
        String promo = eleves_extrait.get(0).getPromoEleve();
        listeRow.get(1).createCell(2).setCellValue(promo);
        listeRow.get(1).getCell(2).setCellStyle(style);
        
        //debug
        int tailleUE = eleves_extrait.get(0).getAnnee().getING1().size();
      
        //listeRow.get(3).createCell(0).setCellValue(tailleUE);
        int tailleMatiere = eleves_extrait.get(0).getAnnee().getING1().size();
     
        //listeRow.get(3).createCell(1).setCellValue(tailleMatiere);

       listeRow.get(3).createCell(0).setCellValue("Nom UE");
       listeRow.get(3).getCell(0).setCellStyle(style);
       listeRow.get(4).createCell(0).setCellValue("Coeff UE");
       listeRow.get(4).getCell(0).setCellStyle(style);
       listeRow.get(5).createCell(0).setCellValue("Crédit UE");
       listeRow.get(5).getCell(0).setCellStyle(style);
       listeRow.get(6).createCell(0).setCellValue("Matière");
       listeRow.get(6).getCell(0).setCellStyle(style);
       listeRow.get(7).createCell(0).setCellValue("Coeff matière");
       listeRow.get(7).getCell(0).setCellStyle(style);
       listeRow.get(8).createCell(0).setCellValue("Enseignant");
       listeRow.get(8).getCell(0).setCellStyle(style);
       listeRow.get(9).createCell(0).setCellValue("Nom");
       listeRow.get(9).getCell(0).setCellStyle(style);
       listeRow.get(9).createCell(1).setCellValue("Prenom");
       listeRow.get(9).getCell(1).setCellStyle(style);


        offset = 2;
        if(date.equals("ING1")){

            for(i=0;i<eleves_extrait.get(0).getAnnee().getING1().size();i++){

                listeRow.get(3).createCell(offset).setCellValue(eleves_extrait.get(0).getAnnee().getING1().get(i).getNomUE());
                listeRow.get(3).getCell(offset).setCellStyle(style);
                listeRow.get(4).createCell(offset).setCellValue(eleves_extrait.get(0).getAnnee().getING1().get(i).getParamUE().getCoeffUE());
                listeRow.get(4).getCell(offset).setCellStyle(style);
                listeRow.get(5).createCell(offset).setCellValue(eleves_extrait.get(0).getAnnee().getING1().get(i).getParamUE().getECTSUE());
                listeRow.get(5).getCell(offset).setCellStyle(style);

                //nbMatiere = this.eleve.getAnnee().getING1().get(0).getMatieres().size();


                //****************fusion de cellule***********************
                feuille.addMergedRegion(new CellRangeAddress(
                        3, // mention first row here
                        3, //mention last row here, it is 1 as we are doing a column wise merging
                        offset, //mention first column of merging
                        offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2 //mention last column to include in merge
                ));
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(3, 3, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(3, 3, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(3, 3, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(3, 3, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(3, 3, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                
                feuille.addMergedRegion(new CellRangeAddress(
                        4, // mention first row here
                        4, //mention last row here, it is 1 as we are doing a column wise merging
                        offset, //mention first column of merging
                        offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2 //mention last column to include in merge
                ));
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(4, 4, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(4, 4, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(4, 4, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(4, 4, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(4, 4, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                
                feuille.addMergedRegion(new CellRangeAddress(
                        5, // mention first row here
                        5, //mention last row here, it is 1 as we are doing a column wise merging
                        offset, //mention first column of merging
                        offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2 //mention last column to include in merge
                ));
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(5, 5, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(5, 5, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(5, 5, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(5, 5, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(5, 5, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                
                //***************

                    for(k=0;k<eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size();k++){
                        listeRow.get(9).createCell(offset+2*k).setCellValue("Rang");
                        listeRow.get(9).getCell(offset+2*k).setCellStyle(style);
                        listeRow.get(9).createCell(offset+2*k+1).setCellValue("Moyenne");
                        listeRow.get(9).getCell(offset+2*k+1).setCellStyle(style);
                    }
                    
                    //listeRow.get(6).createCell(offset+2*eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()).setCellValue("Rang");
                    //listeRow.get(6).getCell(offset+2*eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()).setCellStyle(style);
                    listeRow.get(6).createCell(offset+2*eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()).setCellValue("Moyenne");
                    listeRow.get(6).getCell(offset+2*eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()).setCellStyle(style);
                    

                // fusion rang - moy
                feuille.addMergedRegion(new CellRangeAddress(
                        6, // mention first row here
                        9, //mention last row here, it is 1 as we are doing a column wise merging
                        offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size(), //mention first column of merging
                        offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() //mention last column to include in merge
                ));
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 9, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size(), offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()), feuille, wb);
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 9, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size(), offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()), feuille, wb);
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 9, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size(), offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()), feuille, wb);
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 9, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size(), offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()), feuille, wb);
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 9, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size(), offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()), feuille, wb);
                
                /*
                feuille.addMergedRegion(new CellRangeAddress(
                        6, // mention first row here
                        9, //mention last row here, it is 1 as we are doing a column wise merging
                        offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1, //mention first column of merging
                        offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1 //mention last column to include in merge
                ));
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 9, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()+1, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()+1), feuille, wb);
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 9, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()+1, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()+1), feuille, wb);
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 9, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()+1, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()+1), feuille, wb);
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 9, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()+1, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()+1), feuille, wb);
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 9, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()+1, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()+1), feuille, wb);
                */
                //***************fin de fusion de cellule*************
                
                
                
                for(j=0;j<eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size();j++){
                                    
                    listeRow.get(6).createCell(2*j+offset).setCellValue(eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().get(j).getNomMatiere());
                    listeRow.get(6).getCell(2*j+offset).setCellStyle(style);
                    listeRow.get(7).createCell(2*j+offset).setCellValue(eleves_extrait.get(0).getAnnee().getING1().get(i).getParamUE().getMatieresUE().get(j).getCoeffMatiere());
                    listeRow.get(7).getCell(2*j+offset).setCellStyle(style);
                    listeRow.get(8).createCell(2*j+offset).setCellValue(eleves_extrait.get(0).getAnnee().getING1().get(i).getParamUE().getMatieresUE().get(j).getEnseignant());
                    listeRow.get(8).getCell(2*j+offset).setCellStyle(style);
                    
                    //fusion de cellule
                    feuille.addMergedRegion(new CellRangeAddress(
                            6, // mention first row here
                            6, //mention last row here, it is 1 as we are doing a column wise merging
                            j*2+offset, //mention first column of merging
                            j*2+offset+1 //mention last column to include in merge
                    ));
                    RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 6, j*2+offset, j*2+offset+1), feuille, wb);
                    RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 6, j*2+offset, j*2+offset+1), feuille, wb);
                    RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 6, j*2+offset, j*2+offset+1), feuille, wb);
                    RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 6, j*2+offset, j*2+offset+1), feuille, wb);
                    RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 6, j*2+offset, j*2+offset+1), feuille, wb);
                    
                    feuille.addMergedRegion(new CellRangeAddress(
                            7, // mention first row here
                            7, //mention last row here, it is 1 as we are doing a column wise merging
                            j*2+offset, //mention first column of merging
                            2*j+offset+1 //mention last column to include in merge
                    ));
                    RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(7, 7, j*2+offset, j*2+offset+1), feuille, wb);
                    RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(7, 7, j*2+offset, j*2+offset+1), feuille, wb);
                    RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(7, 7, j*2+offset, j*2+offset+1), feuille, wb);
                    RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(7, 7, j*2+offset, j*2+offset+1), feuille, wb);
                    RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(7, 7, j*2+offset, j*2+offset+1), feuille, wb);
                    
                    feuille.addMergedRegion(new CellRangeAddress(
                            8, // mention first row here
                            8, //mention last row here, it is 1 as we are doing a column wise merging
                            j*2+offset, //mention first column of merging
                            2*j+offset+1 //mention last column to include in merge
                    ));
                    RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(8, 8, j*2+offset, j*2+offset+1), feuille, wb);
                    RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(8, 8, j*2+offset, j*2+offset+1), feuille, wb);
                    RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(8, 8, j*2+offset, j*2+offset+1), feuille, wb);
                    RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(8, 8, j*2+offset, j*2+offset+1), feuille, wb);
                    RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(8, 8, j*2+offset, j*2+offset+1), feuille, wb);
                    


                }//end for j
                
                offset = offset + 1 + 2*(eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size());
                
            } // end for i
            
            // fusions des cellules (les titres ï¿½ gauche)
            for(k=3;k<=8;k++){
                feuille.addMergedRegion(new CellRangeAddress(
                        k, // mention first row here
                        k, //mention last row here, it is 1 as we are doing a column wise merging
                        0, //mention first column of merging
                        1 //mention last column to include in merge
                ));
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(k,k,0,1), feuille, wb);
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(k,k,0,1), feuille, wb);
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(k,k,0,1),feuille, wb);
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(k,k,0,1), feuille, wb);
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(k,k,0,1), feuille, wb);
            }


            // Maintenant on remplit pour chaque eleves
            for (cptEleve = 0; cptEleve < eleves_extrait.size(); cptEleve++) {
                offset = 2;
                listeRow.get(10+cptEleve).createCell(0).setCellValue(eleves_extrait.get(cptEleve).getNomEleve());
                listeRow.get(10+cptEleve).getCell(0).setCellStyle(style);
                listeRow.get(10+cptEleve).createCell(1).setCellValue(eleves_extrait.get(cptEleve).getPrenomEleve());
                listeRow.get(10+cptEleve).getCell(1).setCellStyle(style);
                for(i=0; i < eleves_extrait.get(cptEleve).getAnnee().getING1().size();i++){
                    sommeNoteUE=0;
                    sommeCoeffMat=0;
                    for (j = 0; j < eleves_extrait.get(cptEleve).getAnnee().getING1().get(i).getMatieres().size(); j++) {
                        listeRow.get(10+cptEleve).createCell(2*j+1+offset).setCellValue(df.format(eleves_extrait.get(cptEleve).getAnnee().getING1().get(i).getMatieres().get(j).getNoteMatiere()));
                        listeRow.get(10+cptEleve).getCell(2*j+1+offset).setCellStyle(style);
                        
                      //calcul rang matiere 
                       float moyenne = 0;
                        int rang_mat = 1;
                        for (float moy : liste_notes_mat[i][j]) {
                            moyenne += moy;
                            if (moy > eleves_extrait.get(cptEleve).getAnnee().getINGx(eleves_extrait.get(cptEleve).getPromoEleve()).get(i).getMatieres().get(j).getNoteMatiere()) {
                                rang_mat++;
                            }
                        }
                        listeRow.get(10+cptEleve).createCell(2*j+offset).setCellValue(rang_mat);
                        listeRow.get(10+cptEleve).getCell(2*j+offset).setCellStyle(style);
                                        
                        /*
                         *  moyenne de l'ue
                         */
                        coeffMat = eleves_extrait.get(0).getAnnee().getINGx(Promo).get(i).getParamUE().getMatieresUE().get(j).getCoeffMatiere();
                        sommeCoeffMat += eleves_extrait.get(0).getAnnee().getINGx(Promo).get(i).getParamUE().getMatieresUE().get(j).getCoeffMatiere();
                        sommeNoteUE += eleves_extrait.get(cptEleve).getAnnee().getINGx(Promo).get(i).getMatieres().get(j).getNoteMatiere()*coeffMat;
                       
                    }// end for matiere
                    
                    moyenneUE = sommeNoteUE/sommeCoeffMat;
                    listeRow.get(10+cptEleve).createCell(offset + 2*eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()).setCellValue(df.format(moyenneUE));
                    listeRow.get(10+cptEleve).getCell(offset + 2*eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()).setCellStyle(style);
                    
                    offset = offset + 2*eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() +1;
                }// end for ue
                listeRow.get(10+cptEleve).createCell(offset).setCellValue(df.format(eleves_extrait.get(cptEleve).getMoyenneGenerale()));
                listeRow.get(10+cptEleve).getCell(offset).setCellStyle(style);
            }//end for eleve


        }else if(date.equals("ING2")){
            
            
            for(i=0;i<eleves_extrait.get(0).getAnnee().getING1().size();i++){

                listeRow.get(3).createCell(offset).setCellValue(eleves_extrait.get(0).getAnnee().getING1().get(i).getNomUE());
                listeRow.get(3).getCell(offset).setCellStyle(style);
                listeRow.get(4).createCell(offset).setCellValue(eleves_extrait.get(0).getAnnee().getING1().get(i).getParamUE().getCoeffUE());
                listeRow.get(4).getCell(offset).setCellStyle(style);
                listeRow.get(5).createCell(offset).setCellValue(eleves_extrait.get(0).getAnnee().getING1().get(i).getParamUE().getECTSUE());
                listeRow.get(5).getCell(offset).setCellStyle(style);

                //nbMatiere = this.eleve.getAnnee().getING1().get(0).getMatieres().size();


                //****************fusion de cellule***********************
                feuille.addMergedRegion(new CellRangeAddress(
                        3, // mention first row here
                        3, //mention last row here, it is 1 as we are doing a column wise merging
                        offset, //mention first column of merging
                        offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2 //mention last column to include in merge
                ));
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(3, 3, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(3, 3, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(3, 3, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(3, 3, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(3, 3, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                
                feuille.addMergedRegion(new CellRangeAddress(
                        4, // mention first row here
                        4, //mention last row here, it is 1 as we are doing a column wise merging
                        offset, //mention first column of merging
                        offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2 //mention last column to include in merge
                ));
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(4, 4, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(4, 4, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(4, 4, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(4, 4, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(4, 4, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                
                feuille.addMergedRegion(new CellRangeAddress(
                        5, // mention first row here
                        5, //mention last row here, it is 1 as we are doing a column wise merging
                        offset, //mention first column of merging
                        offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2 //mention last column to include in merge
                ));
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(5, 5, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(5, 5, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(5, 5, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(5, 5, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(5, 5, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                
                //***************

                    for(k=0;k<eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size();k++){
                        listeRow.get(9).createCell(offset+2*k).setCellValue("Rang");
                        listeRow.get(9).getCell(offset+2*k).setCellStyle(style);
                        listeRow.get(9).createCell(offset+2*k+1).setCellValue("Moyenne");
                        listeRow.get(9).getCell(offset+2*k+1).setCellStyle(style);
                    }
                    
                    //listeRow.get(6).createCell(offset+2*eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()).setCellValue("Rang");
                    //listeRow.get(6).getCell(offset+2*eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()).setCellStyle(style);
                    listeRow.get(6).createCell(offset+2*eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()).setCellValue("Moyenne");
                    listeRow.get(6).getCell(offset+2*eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()).setCellStyle(style);
                    

                // fusion rang - moy
                feuille.addMergedRegion(new CellRangeAddress(
                        6, // mention first row here
                        9, //mention last row here, it is 1 as we are doing a column wise merging
                        offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size(), //mention first column of merging
                        offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() //mention last column to include in merge
                ));
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 9, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size(), offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()), feuille, wb);
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 9, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size(), offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()), feuille, wb);
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 9, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size(), offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()), feuille, wb);
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 9, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size(), offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()), feuille, wb);
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 9, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size(), offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()), feuille, wb);
                /*
                feuille.addMergedRegion(new CellRangeAddress(
                        6, // mention first row here
                        9, //mention last row here, it is 1 as we are doing a column wise merging
                        offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1, //mention first column of merging
                        offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1 //mention last column to include in merge
                ));
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 9, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()+1, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()+1), feuille, wb);
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 9, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()+1, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()+1), feuille, wb);
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 9, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()+1, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()+1), feuille, wb);
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 9, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()+1, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()+1), feuille, wb);
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 9, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()+1, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()+1), feuille, wb);
				*/
                //***************fin de fusion de cellule*************




                for(j=0;j<eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size();j++){
                                    
                    listeRow.get(6).createCell(2*j+offset).setCellValue(eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().get(j).getNomMatiere());
                    listeRow.get(6).getCell(2*j+offset).setCellStyle(style);
                    listeRow.get(7).createCell(2*j+offset).setCellValue(eleves_extrait.get(0).getAnnee().getING1().get(i).getParamUE().getMatieresUE().get(j).getCoeffMatiere());
                    listeRow.get(7).getCell(2*j+offset).setCellStyle(style);
                    listeRow.get(8).createCell(2*j+offset).setCellValue(eleves_extrait.get(0).getAnnee().getING1().get(i).getParamUE().getMatieresUE().get(j).getEnseignant());
                    listeRow.get(8).getCell(2*j+offset).setCellStyle(style);

                    //fusion de cellule
                    feuille.addMergedRegion(new CellRangeAddress(
                            6, // mention first row here
                            6, //mention last row here, it is 1 as we are doing a column wise merging
                            j*2+offset, //mention first column of merging
                            j*2+offset+1 //mention last column to include in merge
                    ));
                    RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 6, j*2+offset, j*2+offset+1), feuille, wb);
                    RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 6, j*2+offset, j*2+offset+1), feuille, wb);
                    RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 6, j*2+offset, j*2+offset+1), feuille, wb);
                    RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 6, j*2+offset, j*2+offset+1), feuille, wb);
                    RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 6, j*2+offset, j*2+offset+1), feuille, wb);
                    
                    feuille.addMergedRegion(new CellRangeAddress(
                            7, // mention first row here
                            7, //mention last row here, it is 1 as we are doing a column wise merging
                            j*2+offset, //mention first column of merging
                            2*j+offset+1 //mention last column to include in merge
                    ));
                    RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(7, 7, j*2+offset, j*2+offset+1), feuille, wb);
                    RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(7, 7, j*2+offset, j*2+offset+1), feuille, wb);
                    RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(7, 7, j*2+offset, j*2+offset+1), feuille, wb);
                    RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(7, 7, j*2+offset, j*2+offset+1), feuille, wb);
                    RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(7, 7, j*2+offset, j*2+offset+1), feuille, wb);
                    
                    feuille.addMergedRegion(new CellRangeAddress(
                            8, // mention first row here
                            8, //mention last row here, it is 1 as we are doing a column wise merging
                            j*2+offset, //mention first column of merging
                            2*j+offset+1 //mention last column to include in merge
                    ));
                    RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(8, 8, j*2+offset, j*2+offset+1), feuille, wb);
                    RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(8, 8, j*2+offset, j*2+offset+1), feuille, wb);
                    RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(8, 8, j*2+offset, j*2+offset+1), feuille, wb);
                    RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(8, 8, j*2+offset, j*2+offset+1), feuille, wb);
                    RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(8, 8, j*2+offset, j*2+offset+1), feuille, wb);
                    


            
                }//end for j

                offset = offset + 1 + 2*(eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size());

            } // end for i

            // fusions des cellules (les titres ï¿½ gauche)
            for(k=3;k<=8;k++){
                feuille.addMergedRegion(new CellRangeAddress(
                        k, // mention first row here
                        k, //mention last row here, it is 1 as we are doing a column wise merging
                        0, //mention first column of merging
                        1 //mention last column to include in merge
                ));
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(k,k,0,1), feuille, wb);
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(k,k,0,1), feuille, wb);
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(k,k,0,1),feuille, wb);
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(k,k,0,1), feuille, wb);
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(k,k,0,1), feuille, wb);
            }


            // Maintenant on remplit pour chaque eleves
            for (cptEleve = 0; cptEleve < eleves_extrait.size(); cptEleve++) {
                offset = 2;
                listeRow.get(10+cptEleve).createCell(0).setCellValue(eleves_extrait.get(cptEleve).getNomEleve());
                listeRow.get(10+cptEleve).getCell(0).setCellStyle(style);
                listeRow.get(10+cptEleve).createCell(1).setCellValue(eleves_extrait.get(cptEleve).getPrenomEleve());
                listeRow.get(10+cptEleve).getCell(1).setCellStyle(style);
                for(i=0; i < eleves_extrait.get(cptEleve).getAnnee().getING2().size();i++){
                    sommeNoteUE=0;
                    sommeCoeffMat=0;
                    for (j = 0; j < eleves_extrait.get(cptEleve).getAnnee().getING2().get(i).getMatieres().size(); j++) {
                        listeRow.get(10+cptEleve).createCell(2*j+1+offset).setCellValue(df.format(eleves_extrait.get(cptEleve).getAnnee().getING1().get(i).getMatieres().get(j).getNoteMatiere()));
                        listeRow.get(10+cptEleve).getCell(2*j+1+offset).setCellStyle(style);
                        
                      //calcul rang matiere 
                       float moyenne = 0;
                        int rang_mat = 1;
                        for (float moy : liste_notes_mat[i][j]) {
                            moyenne += moy;
                            if (moy > eleves_extrait.get(cptEleve).getAnnee().getINGx(eleves_extrait.get(cptEleve).getPromoEleve()).get(i).getMatieres().get(j).getNoteMatiere()) {
                                rang_mat++;
                            }
                        }
                        listeRow.get(10+cptEleve).createCell(2*j+offset).setCellValue(rang_mat);
                        listeRow.get(10+cptEleve).getCell(2*j+offset).setCellStyle(style);
                                        
                        /*
                         *  moyenne de l'ue
                         */
                        coeffMat = eleves_extrait.get(0).getAnnee().getINGx(Promo).get(i).getParamUE().getMatieresUE().get(j).getCoeffMatiere();
                        sommeCoeffMat += eleves_extrait.get(0).getAnnee().getINGx(Promo).get(i).getParamUE().getMatieresUE().get(j).getCoeffMatiere();
                        sommeNoteUE += eleves_extrait.get(cptEleve).getAnnee().getINGx(Promo).get(i).getMatieres().get(j).getNoteMatiere()*coeffMat;
                       
                    }// end for matiere
                    
                    moyenneUE = sommeNoteUE/sommeCoeffMat;
                    listeRow.get(10+cptEleve).createCell(offset + 2*eleves_extrait.get(0).getAnnee().getING2().get(i).getMatieres().size() ).setCellValue(df.format(moyenneUE));
                    listeRow.get(10+cptEleve).getCell(offset + 2*eleves_extrait.get(0).getAnnee().getING2().get(i).getMatieres().size() ).setCellStyle(style);
                    
                    offset = offset + 2*eleves_extrait.get(0).getAnnee().getING2().get(i).getMatieres().size() +1;
                }// end for ue
                listeRow.get(10+cptEleve).createCell(offset).setCellValue(df.format(eleves_extrait.get(cptEleve).getMoyenneGenerale()));
                listeRow.get(10+cptEleve).getCell(offset).setCellStyle(style);
            }//end for eleve
            
            
        }else{
            
            
            for(i=0;i<eleves_extrait.get(0).getAnnee().getING1().size();i++){

                listeRow.get(3).createCell(offset).setCellValue(eleves_extrait.get(0).getAnnee().getING1().get(i).getNomUE());
                listeRow.get(3).getCell(offset).setCellStyle(style);
                listeRow.get(4).createCell(offset).setCellValue(eleves_extrait.get(0).getAnnee().getING1().get(i).getParamUE().getCoeffUE());
                listeRow.get(4).getCell(offset).setCellStyle(style);
                listeRow.get(5).createCell(offset).setCellValue(eleves_extrait.get(0).getAnnee().getING1().get(i).getParamUE().getECTSUE());
                listeRow.get(5).getCell(offset).setCellStyle(style);

                //nbMatiere = this.eleve.getAnnee().getING1().get(0).getMatieres().size();


                //****************fusion de cellule***********************
                feuille.addMergedRegion(new CellRangeAddress(
                        3, // mention first row here
                        3, //mention last row here, it is 1 as we are doing a column wise merging
                        offset, //mention first column of merging
                        offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2 //mention last column to include in merge
                ));
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(3, 3, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(3, 3, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(3, 3, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(3, 3, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(3, 3, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                
                feuille.addMergedRegion(new CellRangeAddress(
                        4, // mention first row here
                        4, //mention last row here, it is 1 as we are doing a column wise merging
                        offset, //mention first column of merging
                        offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2 //mention last column to include in merge
                ));
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(4, 4, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(4, 4, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(4, 4, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(4, 4, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(4, 4, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                
                feuille.addMergedRegion(new CellRangeAddress(
                        5, // mention first row here
                        5, //mention last row here, it is 1 as we are doing a column wise merging
                        offset, //mention first column of merging
                        offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2 //mention last column to include in merge
                ));
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(5, 5, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(5, 5, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(5, 5, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(5, 5, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(5, 5, offset, offset + 2 * (eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1) - 2), feuille, wb);
                
                //***************

                    for(k=0;k<eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size();k++){
                        listeRow.get(9).createCell(offset+2*k).setCellValue("Rang");
                        listeRow.get(9).getCell(offset+2*k).setCellStyle(style);
                        listeRow.get(9).createCell(offset+2*k+1).setCellValue("Moyenne");
                        listeRow.get(9).getCell(offset+2*k+1).setCellStyle(style);
                    }
                    
                    //listeRow.get(6).createCell(offset+2*eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()).setCellValue("Rang");
                    //listeRow.get(6).getCell(offset+2*eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()).setCellStyle(style);
                    listeRow.get(6).createCell(offset+2*eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()).setCellValue("Moyenne");
                    listeRow.get(6).getCell(offset+2*eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()).setCellStyle(style);
                    

                // fusion rang - moy
                feuille.addMergedRegion(new CellRangeAddress(
                        6, // mention first row here
                        9, //mention last row here, it is 1 as we are doing a column wise merging
                        offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size(), //mention first column of merging
                        offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() //mention last column to include in merge
                ));
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 9, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size(), offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()), feuille, wb);
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 9, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size(), offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()), feuille, wb);
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 9, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size(), offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()), feuille, wb);
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 9, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size(), offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()), feuille, wb);
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 9, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size(), offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()), feuille, wb);
                /*
                feuille.addMergedRegion(new CellRangeAddress(
                        6, // mention first row here
                        9, //mention last row here, it is 1 as we are doing a column wise merging
                        offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1, //mention first column of merging
                        offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size() + 1 //mention last column to include in merge
                ));
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 9, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()+1, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()+1), feuille, wb);
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 9, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()+1, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()+1), feuille, wb);
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 9, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()+1, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()+1), feuille, wb);
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 9, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()+1, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()+1), feuille, wb);
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 9, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()+1, offset + 2 * eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size()+1), feuille, wb);
				*/
                //***************fin de fusion de cellule*************



                for(j=0;j<eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size();j++){
                                    
                    listeRow.get(6).createCell(2*j+offset).setCellValue(eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().get(j).getNomMatiere());
                    listeRow.get(6).getCell(2*j+offset).setCellStyle(style);
                    listeRow.get(7).createCell(2*j+offset).setCellValue(eleves_extrait.get(0).getAnnee().getING1().get(i).getParamUE().getMatieresUE().get(j).getCoeffMatiere());
                    listeRow.get(7).getCell(2*j+offset).setCellStyle(style);
                    listeRow.get(8).createCell(2*j+offset).setCellValue(eleves_extrait.get(0).getAnnee().getING1().get(i).getParamUE().getMatieresUE().get(j).getEnseignant());
                    listeRow.get(8).getCell(2*j+offset).setCellStyle(style);

                    //fusion de cellule
                    feuille.addMergedRegion(new CellRangeAddress(
                            6, // mention first row here
                            6, //mention last row here, it is 1 as we are doing a column wise merging
                            j*2+offset, //mention first column of merging
                            j*2+offset+1 //mention last column to include in merge
                    ));
                    RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 6, j*2+offset, j*2+offset+1), feuille, wb);
                    RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 6, j*2+offset, j*2+offset+1), feuille, wb);
                    RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 6, j*2+offset, j*2+offset+1), feuille, wb);
                    RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 6, j*2+offset, j*2+offset+1), feuille, wb);
                    RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(6, 6, j*2+offset, j*2+offset+1), feuille, wb);
                    
                    feuille.addMergedRegion(new CellRangeAddress(
                            7, // mention first row here
                            7, //mention last row here, it is 1 as we are doing a column wise merging
                            j*2+offset, //mention first column of merging
                            2*j+offset+1 //mention last column to include in merge
                    ));
                    RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(7, 7, j*2+offset, j*2+offset+1), feuille, wb);
                    RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(7, 7, j*2+offset, j*2+offset+1), feuille, wb);
                    RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(7, 7, j*2+offset, j*2+offset+1), feuille, wb);
                    RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(7, 7, j*2+offset, j*2+offset+1), feuille, wb);
                    RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(7, 7, j*2+offset, j*2+offset+1), feuille, wb);
                    
                    feuille.addMergedRegion(new CellRangeAddress(
                            8, // mention first row here
                            8, //mention last row here, it is 1 as we are doing a column wise merging
                            j*2+offset, //mention first column of merging
                            2*j+offset+1 //mention last column to include in merge
                    ));
                    RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(8, 8, j*2+offset, j*2+offset+1), feuille, wb);
                    RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(8, 8, j*2+offset, j*2+offset+1), feuille, wb);
                    RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(8, 8, j*2+offset, j*2+offset+1), feuille, wb);
                    RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(8, 8, j*2+offset, j*2+offset+1), feuille, wb);
                    RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(8, 8, j*2+offset, j*2+offset+1), feuille, wb);
                    


                }//end for j

                offset = offset + 1 + 2*(eleves_extrait.get(0).getAnnee().getING1().get(i).getMatieres().size());

            } // end for i

            // fusions des cellules (les titres ï¿½ gauche)
            for(k=3;k<=8;k++){
                feuille.addMergedRegion(new CellRangeAddress(
                        k, // mention first row here
                        k, //mention last row here, it is 1 as we are doing a column wise merging
                        0, //mention first column of merging
                        1 //mention last column to include in merge
                ));
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(k,k,0,1), feuille, wb);
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(k,k,0,1), feuille, wb);
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(k,k,0,1),feuille, wb);
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(k,k,0,1), feuille, wb);
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(k,k,0,1), feuille, wb);
            }


            // Maintenant on remplit pour chaque eleves
            for (cptEleve = 0; cptEleve < eleves_extrait.size(); cptEleve++) {
                offset = 2;
                listeRow.get(10+cptEleve).createCell(0).setCellValue(eleves_extrait.get(cptEleve).getNomEleve());
                listeRow.get(10+cptEleve).getCell(0).setCellStyle(style);
                listeRow.get(10+cptEleve).createCell(1).setCellValue(eleves_extrait.get(cptEleve).getPrenomEleve());
                listeRow.get(10+cptEleve).getCell(1).setCellStyle(style);
                for(i=0; i < eleves_extrait.get(cptEleve).getAnnee().getING3().size();i++){
                    sommeNoteUE=0;
                    sommeCoeffMat=0;
                    for (j = 0; j < eleves_extrait.get(cptEleve).getAnnee().getING3().get(i).getMatieres().size(); j++) {
                        listeRow.get(10+cptEleve).createCell(2*j+1+offset).setCellValue(df.format(eleves_extrait.get(cptEleve).getAnnee().getING1().get(i).getMatieres().get(j).getNoteMatiere()));
                        listeRow.get(10+cptEleve).getCell(2*j+1+offset).setCellStyle(style);
                        
                      //calcul rang matiere 
                       float moyenne = 0;
                        int rang_mat = 1;
                        for (float moy : liste_notes_mat[i][j]) {
                            moyenne += moy;
                            if (moy > eleves_extrait.get(cptEleve).getAnnee().getINGx(eleves_extrait.get(cptEleve).getPromoEleve()).get(i).getMatieres().get(j).getNoteMatiere()) {
                                rang_mat++;
                            }
                        }
                        listeRow.get(10+cptEleve).createCell(2*j+offset).setCellValue(rang_mat);
                        listeRow.get(10+cptEleve).getCell(2*j+offset).setCellStyle(style);
                                        
                        /*
                         *  moyenne de l'ue
                         */
                        coeffMat = eleves_extrait.get(0).getAnnee().getINGx(Promo).get(i).getParamUE().getMatieresUE().get(j).getCoeffMatiere();
                        sommeCoeffMat += eleves_extrait.get(0).getAnnee().getINGx(Promo).get(i).getParamUE().getMatieresUE().get(j).getCoeffMatiere();
                        sommeNoteUE += eleves_extrait.get(cptEleve).getAnnee().getINGx(Promo).get(i).getMatieres().get(j).getNoteMatiere()*coeffMat;
                       
                    }// end for matiere
                    
                    moyenneUE = sommeNoteUE/sommeCoeffMat;
                    listeRow.get(10+cptEleve).createCell(offset + 2*eleves_extrait.get(0).getAnnee().getING3().get(i).getMatieres().size()).setCellValue(df.format(moyenneUE));
                    listeRow.get(10+cptEleve).getCell(offset + 2*eleves_extrait.get(0).getAnnee().getING3().get(i).getMatieres().size()).setCellStyle(style);
                    
                    offset = offset + 2*eleves_extrait.get(0).getAnnee().getING3().get(i).getMatieres().size() +1;
                }// end for ue
                
                listeRow.get(10+cptEleve).createCell(offset).setCellValue(df.format(eleves_extrait.get(cptEleve).getMoyenneGenerale()));
                listeRow.get(10+cptEleve).getCell(offset).setCellStyle(style);
            }//end for eleve
        }//end for ING
        
        //affichage moyenne gï¿½nï¿½rale
        
        //merge des cellules et titres
        feuille.addMergedRegion(new CellRangeAddress(
                3, // mention first row here
                9, //mention last row here, it is 1 as we are doing a column wise merging
                offset, //mention first column of merging
                offset //mention last column to include in merge
        ));
        RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(3, 9, offset, offset), feuille, wb);
        RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(3, 9, offset, offset), feuille, wb);
        RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(3, 9, offset, offset), feuille, wb);
        RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(3, 9, offset, offset), feuille, wb);
        RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(3, 9, offset, offset), feuille, wb);
        
        listeRow.get(3).createCell(offset).setCellValue("Moyenne de l'année");
        listeRow.get(3).getCell(offset).setCellStyle(style);
      
        int p;
        for(p=0; p<=100;p++){
            feuille.autoSizeColumn(p, true);
        }
      
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(cheminFichierSortie+"\\bulletinGeneral"+NomFichierPromo + Promo+".xlsx");
            wb.write(fileOut);
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
     
        // JAVAFX Alert
        alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Information");
        alert.setContentText("Bulletin général généré");
        alert.showAndWait();
        }
    }


    /**
     * Fonction gï¿½nï¿½rant le bilan des compensations de tous les Ã©lÃ¨ves
     */
    public void bilanCompensation(String Promo, String NomFichierPromo) throws ParseException {
        int cptUE,cptMatiere,cptEleve,cptFeuil1,cptFormule,cpt;
        float moyenne, sommeCoef ;
        boolean flag_valid;

        //
        String cheminFichierSortie= new Extracteur("ressources\\ParametrageAccesFichier.xml").ExtracteurCheminFichierDistant("Sorties");
        String cheminFichierGeneral = new Extracteur("ressources\\ParametrageAccesFichier.xml").ExtracteurCheminFichierDistant("Resources");
        String cheminFichierParam = new Extracteur("ressources\\ParametrageAccesFichier.xml").ExtracteurCheminFichierDistant("Parametrages");
        //
        File fichierGeneral = new File(cheminFichierGeneral + "\\" + NomFichierPromo + Promo + ".csv");
        File fichierParam = new File(cheminFichierParam+"\\Parametrage-" + NomFichierPromo + Promo + ".xml");

        //
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        if( !fichierGeneral.exists() ) {
        	// JAVAFX Alert
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("Information");
        	alert.setHeaderText("Information");
        	alert.setContentText("Fichier " + NomFichierPromo + Promo+".csv introuvable");
        	alert.showAndWait();
        } else if ( !fichierParam.exists() ) {
        	// JAVAFX Alert
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("Information");
        	alert.setHeaderText("Information");
        	alert.setContentText("Fichier " + NomFichierPromo + Promo+".xml introuvable");
        	alert.showAndWait();
        } else {
        	// JAVAFX Alert
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("Information");
        	alert.setHeaderText("Information");
        	alert.setContentText("Génération du bilan de compensation en cours");
        	alert.showAndWait();

            ParamGeneral extractParam = new Extracteur(cheminFichierParam+"\\Parametrage-" + NomFichierPromo + Promo + ".xml").ExtracteurParametresGeneraux();
            Extracteur extractEleve = new Extracteur(cheminFichierGeneral+"\\" + NomFichierPromo + Promo + ".csv");
            ArrayList<Eleve> eleves = extractEleve.ExtracteurEleves(NomFichierPromo + Promo);
            eleves = extractEleve.ExtracteurNotesToutesAnnee(eleves);

            //1. Crï¿½er un Document vide
            XSSFWorkbook wb = new XSSFWorkbook();

            //
            XSSFCellStyle style = wb.createCellStyle();
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);

            //2. Crï¿½er une Feuille de calcul vide
            Sheet feuille = wb.createSheet(Promo);
            ArrayList<Row> listeRow = new ArrayList<Row>();

            // Crï¿½ation du nombre de lignes nï¿½cessaire pour la suite
            for (cptFeuil1 = 0; cptFeuil1 <= ((eleves.size() + 5) * (eleves.get(0).getAnnee().getINGx(Promo).size() - 1)); cptFeuil1++) {
                Row ligne = feuille.createRow((short) cptFeuil1);
                listeRow.add(ligne);
            }

            // a faire pour chaque UE
            for (cptUE = 0; cptUE < eleves.get(0).getAnnee().getINGx(Promo).size() - 1; cptUE++) {
                // deux lignes et trois colones vides puis le nom de l'UE
                // des vï¿½rifications doivent ï¿½tre faite avant d'accï¿½der au valeur
                listeRow.get(2 + (eleves.size() + 5) * cptUE ).createCell(3).setCellValue(eleves.get(0).getAnnee().getINGx(Promo).get(cptUE).getNomUE());
                listeRow.get(2 + (eleves.size() + 5) * cptUE ).getCell(3).setCellStyle(style);
                feuille.addMergedRegion(new CellRangeAddress(
                        2 + (eleves.size() + 5) * cptUE, // mention first row here
                        2 + (eleves.size() + 5) * cptUE, //mention last row here, it is 1 as we are doing a column wise merging
                        3, //mention first column of merging
                        3 + eleves.get(0).getAnnee().getINGx(Promo).get(cptUE).getMatieres().size() - 1 //mention last column to include in merge
                ));
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(2 + (eleves.size() + 5) * cptUE, 2 + (eleves.size() + 5) * cptUE, 3, 3 + eleves.get(0).getAnnee().getINGx(Promo).get(cptUE).getMatieres().size() - 1), feuille, wb);
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(2 + (eleves.size() + 5) * cptUE,  2 + (eleves.size() + 5) * cptUE, 3, 3 + eleves.get(0).getAnnee().getINGx(Promo).get(cptUE).getMatieres().size() - 1), feuille, wb);
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(2 + (eleves.size() + 5) * cptUE,  2 + (eleves.size() + 5) * cptUE, 3, 3 + eleves.get(0).getAnnee().getINGx(Promo).get(cptUE).getMatieres().size() - 1), feuille, wb);
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(2 + (eleves.size() + 5) * cptUE,  2 + (eleves.size() + 5) * cptUE, 3, 3 + eleves.get(0).getAnnee().getINGx(Promo).get(cptUE).getMatieres().size() - 1), feuille, wb);
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(2 + (eleves.size() + 5) * cptUE,  2 + (eleves.size() + 5) * cptUE, 3, 3 + eleves.get(0).getAnnee().getINGx(Promo).get(cptUE).getMatieres().size() - 1), feuille, wb);

                // a cï¿½tï¿½ sachant que la case du nom de la matiere doit etre grande on a le crï¿½dit
                listeRow.get(2 + (eleves.size() + 5) * cptUE ).createCell(eleves.get(0).getAnnee().getINGx(Promo).get(cptUE).getMatieres().size() + 3).setCellValue("Credit UE : " + eleves.get(0).getAnnee().getINGx(Promo).get(cptUE).getParamUE().getECTSUE());
                listeRow.get(2 + (eleves.size() + 5) * cptUE ).getCell(eleves.get(0).getAnnee().getINGx(Promo).get(cptUE).getMatieres().size() + 3).setCellStyle(style);
                // la ligne des matieres
                for (cptMatiere = 0; cptMatiere < eleves.get(0).getAnnee().getINGx(Promo).get(cptUE).getMatieres().size(); cptMatiere++) {
                    listeRow.get(3 + (eleves.size() + 5) * cptUE ).createCell(3 + cptMatiere).setCellValue(eleves.get(0).getAnnee().getINGx(Promo).get(cptUE).getMatieres().get(cptMatiere).getNomMatiere());
                    listeRow.get(3 + (eleves.size() + 5) * cptUE ).getCell(3 + cptMatiere).setCellStyle(style);
                }
                // Ajout des deux case moyenne et validation
                listeRow.get(3 + (eleves.size() + 5) * cptUE ).createCell(3 + cptMatiere).setCellValue("Moyenne de l'UE");
                listeRow.get(3 + (eleves.size() + 5) * cptUE ).getCell(3 + cptMatiere).setCellStyle(style);
                listeRow.get(3 + (eleves.size() + 5) * cptUE ).createCell(4 + cptMatiere).setCellValue("Validation de l'UE");
                listeRow.get(3 + (eleves.size() + 5) * cptUE ).getCell(4 + cptMatiere).setCellStyle(style);
                // la ligne des coef des matieres
                listeRow.get(4 + (eleves.size() + 5) * cptUE ).createCell(2).setCellValue("Coeff");
                listeRow.get(4 + (eleves.size() + 5) * cptUE ).getCell(2).setCellStyle(style);
                for (cptMatiere = 0; cptMatiere < eleves.get(0).getAnnee().getINGx(Promo).get(cptUE).getMatieres().size(); cptMatiere++) {
                    listeRow.get(4 + (eleves.size() + 5) * cptUE ).createCell(3 + cptMatiere).setCellValue(eleves.get(0).getAnnee().getINGx(Promo).get(cptUE).getParamUE().getMatieresUE().get(cptMatiere).getCoeffMatiere());
                    listeRow.get(4 + (eleves.size() + 5) * cptUE ).getCell(3 + cptMatiere).setCellStyle(style);
                }
                listeRow.get(4 + (eleves.size() + 5) * cptUE ).createCell(3 + eleves.get(0).getAnnee().getINGx(Promo).get(cptUE).getMatieres().size()).setCellStyle(style);
                listeRow.get(4 + (eleves.size() + 5) * cptUE ).createCell(4 + eleves.get(0).getAnnee().getINGx(Promo).get(cptUE).getMatieres().size()).setCellStyle(style);
                // Maintenant on remplit pour chaque eleves
                for (cptEleve = 0; cptEleve < eleves.size(); cptEleve++) {
                    listeRow.get(5 + (eleves.size() + 5) * cptUE + cptEleve ).createCell(1).setCellValue(eleves.get(cptEleve).getNomEleve());
                    listeRow.get(5 + (eleves.size() + 5) * cptUE + cptEleve ).getCell(1).setCellStyle(style);
                    listeRow.get(5 + (eleves.size() + 5) * cptUE + cptEleve ).createCell(2).setCellValue(eleves.get(cptEleve).getPrenomEleve());
                    listeRow.get(5 + (eleves.size() + 5) * cptUE + cptEleve ).getCell(2).setCellStyle(style);
                    moyenne = 0;
                    sommeCoef = 0;
                    flag_valid = true;
                    for (cptMatiere = 0; cptMatiere < eleves.get(0).getAnnee().getINGx(Promo).get(cptUE).getMatieres().size(); cptMatiere++) {
                        //System.out.println(listeRow.get(4 + (eleves.size() + 5) * cptUE + cptEleve + cptligne).getCell(3 + cptMatiere).getStringCellValue());
                        double ValMat = listeRow.get(4 + (eleves.size() + 5) * cptUE ).getCell(3 + cptMatiere).getNumericCellValue();
                        sommeCoef += listeRow.get(4 + (eleves.size() + 5) * cptUE ).getCell(3 + cptMatiere).getNumericCellValue();
                        moyenne += eleves.get(cptEleve).getAnnee().getINGx(Promo).get(cptUE).getMatieres().get(cptMatiere).getNoteMatiere() * ValMat;
                        listeRow.get(5 + (eleves.size() + 5) * cptUE + cptEleve ).createCell(3 + cptMatiere).setCellValue(df.format(eleves.get(cptEleve).getAnnee().getINGx(Promo).get(cptUE).getMatieres().get(cptMatiere).getNoteMatiere()));
                        XSSFCellStyle csCouleur = wb.createCellStyle();
                        csCouleur.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                        csCouleur.setBorderTop(HSSFCellStyle.BORDER_THIN);
                        csCouleur.setBorderRight(HSSFCellStyle.BORDER_THIN);
                        csCouleur.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                        if (eleves.get(cptEleve).getAnnee().getINGx(Promo).get(cptUE).getMatieres().get(cptMatiere).getNoteMatiere() >= extractParam.getValidationMatiere()) {
                            csCouleur.setFillForegroundColor(new XSSFColor(new Color(146, 208, 80)));
                        } else if (eleves.get(cptEleve).getAnnee().getINGx(Promo).get(cptUE).getMatieres().get(cptMatiere).getNoteMatiere() >= extractParam.getNoteMini()) {
                            csCouleur.setFillForegroundColor(new XSSFColor(new Color(255, 192, 0)));
                        } else {
                            csCouleur.setFillForegroundColor(new XSSFColor(new Color(255, 0, 0)));
                            flag_valid = false;
                        }
                        csCouleur.setFillPattern(csCouleur.SOLID_FOREGROUND);
                        listeRow.get(5 + (eleves.size() + 5) * cptUE + cptEleve ).getCell(3 + cptMatiere).setCellStyle(csCouleur);
                        // La moyenne a l'UE avec une formule Excel
                        if (cptMatiere == eleves.get(0).getAnnee().getINGx(Promo).get(cptUE).getMatieres().size() - 1) {
                            String formule = "(" + String.valueOf(Character.toChars(3 + cptMatiere + 65)) + "" + Integer.toString(5 + (eleves.size() + 5) * cptUE);
                            formule += "*" + String.valueOf(Character.toChars(3 + cptMatiere + 65)) + "" + Integer.toString(6 + (eleves.size() + 5) * cptUE + cptEleve);
                            for (cptFormule = cptMatiere; cptFormule > 0; cptFormule--) {
                                formule += "+" + String.valueOf(Character.toChars(3 + cptMatiere + 65 - cptFormule)) + "" + Integer.toString(5 + (eleves.size() + 5) * cptUE);
                                formule += "*" + String.valueOf(Character.toChars(3 + cptMatiere + 65 - cptFormule)) + "" + Integer.toString(6 + (eleves.size() + 5) * cptUE + cptEleve);
                            }
                            formule += ")/(" + String.valueOf(Character.toChars(3 + cptMatiere + 65)) + "" + Integer.toString(5 + (eleves.size() + 5) * cptUE);
                            for (cptFormule = cptMatiere; cptFormule > 0; cptFormule--) {
                                formule += "+" + String.valueOf(Character.toChars(3 + cptMatiere + 65 - cptFormule)) + "" + Integer.toString(5 + (eleves.size() + 5) * cptUE);
                            }
                            formule += ")";
                            listeRow.get(5 + (eleves.size() + 5) * cptUE + cptEleve ).createCell(4 + cptMatiere).setCellFormula(formule);
                        }
                    }
                    XSSFCellStyle csCouleur = wb.createCellStyle();
                    csCouleur.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                    csCouleur.setBorderTop(HSSFCellStyle.BORDER_THIN);
                    csCouleur.setBorderRight(HSSFCellStyle.BORDER_THIN);
                    csCouleur.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                    csCouleur.setDataFormat(wb.createDataFormat().getFormat("#.##"));
                    if ((moyenne / sommeCoef) >= extractParam.getValidationUE() && flag_valid) {
                        csCouleur.setFillForegroundColor(new XSSFColor(new Color(146, 208, 80)));
                        listeRow.get(5 + (eleves.size() + 5) * cptUE + cptEleve ).createCell(eleves.get(0).getAnnee().getINGx(Promo).get(cptUE).getMatieres().size() + 4).setCellValue("Oui");
                    } else {
                        csCouleur.setFillForegroundColor(new XSSFColor(new Color(255, 0, 0)));
                        listeRow.get(5 + (eleves.size() + 5) * cptUE + cptEleve ).createCell(eleves.get(0).getAnnee().getINGx(Promo).get(cptUE).getMatieres().size() + 4).setCellValue("Non");
                    }
                    listeRow.get(5 + (eleves.size() + 5) * cptUE + cptEleve ).getCell(eleves.get(0).getAnnee().getINGx(Promo).get(cptUE).getMatieres().size() + 4).setCellStyle(style);
                    csCouleur.setFillPattern(csCouleur.SOLID_FOREGROUND);
                    listeRow.get(5 + (eleves.size() + 5) * cptUE + cptEleve ).getCell(eleves.get(0).getAnnee().getINGx(Promo).get(cptUE).getMatieres().size() + 3).setCellStyle(csCouleur);
                }
            }

            for (cpt=0;cpt<20;cpt++){
                feuille.autoSizeColumn(cpt, true);
            }

            FileOutputStream fileOut;
            try {
                fileOut = new FileOutputStream(cheminFichierSortie+"\\bilanCompensation-"+ NomFichierPromo + Promo +".xlsx");
                wb.write(fileOut);
                fileOut.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // JAVAFX Alert
        	alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("Information");
        	alert.setHeaderText("Information");
        	alert.setContentText("Bilan de compensation généré");
        	alert.showAndWait();
            
        }
    }

    /**
     * Fonction gï¿½nï¿½rant le rï¿½partitif des 3 derniï¿½re annï¿½es
     * @throws ParseException 
     */
    public void recapSur3ans(String NomFichierPromo) throws ParseException{

        //
        String cheminFichierSortie= new Extracteur("ressources\\ParametrageAccesFichier.xml").ExtracteurCheminFichierDistant("Sorties");
        String cheminFichierGeneral = new Extracteur("ressources\\ParametrageAccesFichier.xml").ExtracteurCheminFichierDistant("Resources");
        String cheminFichierParam = new Extracteur("ressources\\ParametrageAccesFichier.xml").ExtracteurCheminFichierDistant("Parametrages");

        File fichierGeneral1 = new File(cheminFichierGeneral+"\\"+NomFichierPromo+"ING1.csv");
        File fichierGeneral2 = new File(cheminFichierGeneral+"\\"+NomFichierPromo+"ING2.csv");
        File fichierGeneral3 = new File(cheminFichierGeneral+"\\"+NomFichierPromo+"ING3.csv");
        File fichierParam1 = new File(cheminFichierParam+"\\Parametrage-"+NomFichierPromo + "ING1.xml");
        File fichierParam2 = new File(cheminFichierParam+"\\Parametrage-"+NomFichierPromo + "ING2.xml");
        File fichierParam3 = new File(cheminFichierParam+"\\Parametrage-"+NomFichierPromo + "ING3.xml");
        //File fichierIndiv = new File("ressources\\projet.xls");
        int i,cptEleve,offset, cptUE,cptMat,cptFeuil1,nbMaxMatING1,nbMaxMatING2,nbMaxMatING3;
        float somme_moy_ING1,somme_moy_ING2,somme_moy_ING3;
        float coeffMat;
        double sommeCoeffMat=0,sommeNoteUE=0,moyenneUE=0;
        i=0;
        
        if( !fichierGeneral1.exists() ) {
        	// JAVAFX Alert
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("Information");
        	alert.setHeaderText("Information");
        	alert.setContentText("Fichier " +NomFichierPromo+"ING1.csv introuvable");
        	alert.showAndWait();
        } else if ( !fichierGeneral2.exists() ) {
        	// JAVAFX Alert
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("Information");
        	alert.setHeaderText("Information");
        	alert.setContentText("Fichier " +NomFichierPromo+"ING2.csv introuvable");
        	alert.showAndWait();
        } else if ( !fichierGeneral3.exists() ) {
        	// JAVAFX Alert
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("Information");
        	alert.setHeaderText("Information");
        	alert.setContentText("Fichier " +NomFichierPromo+"ING3.csv introuvable");
        	alert.showAndWait();
        } else if ( !fichierParam3.exists() ) {
        	// JAVAFX Alert
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("Information");
        	alert.setHeaderText("Information");
        	alert.setContentText("Fichier Parametrage-"+NomFichierPromo + "ING3.xml introuvable");
        	alert.showAndWait();
        } else if ( !fichierParam2.exists() ) {
        	// JAVAFX Alert
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("Information");
        	alert.setHeaderText("Information");
        	alert.setContentText("Fichier Parametrage-"+NomFichierPromo + "ING2.xml introuvable");
        	alert.showAndWait();
        } else if ( !fichierParam1.exists() ) {
        	// JAVAFX Alert
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("Information");
        	alert.setHeaderText("Information");
        	alert.setContentText("Fichier Parametrage-"+NomFichierPromo + "ING1.xml introuvable");
        	alert.showAndWait();
        } else {
        	// JAVAFX Alert
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("Information");
        	alert.setHeaderText("Information");
        	alert.setContentText("Génération récapitulatif sur 3 ans en cours");
        	alert.showAndWait();
            

            ParamGeneral extractParam1 = new Extracteur(cheminFichierParam+"\\Parametrage-" + NomFichierPromo + "ING1" + ".xml").ExtracteurParametresGeneraux();
            ParamGeneral extractParam2 = new Extracteur(cheminFichierParam+"\\Parametrage-" + NomFichierPromo + "ING2" + ".xml").ExtracteurParametresGeneraux();
            ParamGeneral extractParam3 = new Extracteur(cheminFichierParam+"\\Parametrage-" + NomFichierPromo + "ING3" + ".xml").ExtracteurParametresGeneraux();
            
            Extracteur ExtractionING1 = new Extracteur(cheminFichierGeneral+"\\" + NomFichierPromo + "ING1.csv");
            ArrayList<Eleve> eleves_ING1 = ExtractionING1.ExtracteurEleves(NomFichierPromo + "ING1");
            eleves_ING1 = ExtractionING1.ExtracteurNotesToutesAnnee(eleves_ING1);
            //ExtractionING1.setCheminFichier("ressources\\projet.xls");

            Extracteur ExtractionING2 = new Extracteur(cheminFichierGeneral+"\\" + NomFichierPromo + "ING2.csv");
            ArrayList<Eleve> eleves_ING2 = ExtractionING2.ExtracteurEleves(NomFichierPromo + "ING2");
            eleves_ING2 = ExtractionING2.ExtracteurNotesToutesAnnee(eleves_ING2);
            //ExtractionING2.setCheminFichier("ressources\\projet.xls");
            
            Extracteur ExtractionING3 = new Extracteur(cheminFichierGeneral+"\\" + NomFichierPromo + "ING3.csv");
            ArrayList<Eleve> eleves_ING3 = ExtractionING3.ExtracteurEleves(NomFichierPromo + "ING3");
            eleves_ING3 = ExtractionING3.ExtracteurNotesToutesAnnee(eleves_ING3);
            //ExtractionING3.setCheminFichier("ressources\\projet.xls");
            
            //
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            
            
        //1. Crï¿½er un Document vide
        XSSFWorkbook wb = new XSSFWorkbook();
        //
        XSSFCellStyle style = wb.createCellStyle();
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        //
        XSSFCellStyle styleTitreUE = wb.createCellStyle();
        styleTitreUE.setAlignment(styleTitreUE.ALIGN_CENTER);
        //
        XSSFCellStyle styleTitrePrincipal = wb.createCellStyle();
        styleTitrePrincipal.setAlignment(styleTitrePrincipal.ALIGN_CENTER);
        //
        XSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 16);
        font.setFontName("Calibri");
        font.setBold(true);
        styleTitrePrincipal.setFont(font);
        
        
        //2. Crï¿½er une Feuille de calcul vide
        Sheet feuille = wb.createSheet("Synthèse sur 3 ans");
        ArrayList<Row> listeRow = new ArrayList<Row>();
        
        // Crï¿½ation du nombre de lignes nï¿½cessaire pour la suite
        for (cptFeuil1 = 0; cptFeuil1<= 100; cptFeuil1++) {
            Row ligne = feuille.createRow((short) cptFeuil1);
            listeRow.add(ligne);
        }
        
        
        listeRow.get(0).createCell(0).setCellValue("Synthèse sur 3 ans");
        feuille.addMergedRegion(new CellRangeAddress(
                0, // mention first row here
                0, //mention last row here, it is 1 as we are doing a column wise merging
                0, //mention first column of merging
                7 //mention last column to include in merge
        ));
        RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(0,0,0,7), feuille, wb);
        RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(0,0,0,7), feuille, wb);
        RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(0,0,0,7), feuille, wb);
        RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(0,0,0,7), feuille, wb);
        RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(0,0,0,7), feuille, wb);
        
        offset=2;
        
        for (i=1;i<=3;i++){
            
            if(i==1){
            
                    listeRow.get(2).createCell(offset).setCellValue("1ère année");
                    feuille.addMergedRegion(new CellRangeAddress(
                            2, // mention first row here
                            2, //mention last row here, it is 1 as we are doing a column wise merging
                            offset, //mention first column of merging
                            offset + eleves_ING1.get(0).getAnnee().getING1().size() + 1 //mention last column to include in merge
                    ));
                    RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(2,2,offset,offset + eleves_ING1.get(0).getAnnee().getING1().size() + 1), feuille, wb);
                    RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(2,2,offset,offset + eleves_ING1.get(0).getAnnee().getING1().size() + 1), feuille, wb);
                    RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(2,2,offset,offset + eleves_ING1.get(0).getAnnee().getING1().size() + 1), feuille, wb);
                    RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(2,2,offset,offset + eleves_ING1.get(0).getAnnee().getING1().size() + 1), feuille, wb);
                    RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(2,2,offset,offset + eleves_ING1.get(0).getAnnee().getING1().size() + 1), feuille, wb);
                    
                    listeRow.get(4).createCell(1).setCellValue("crédit");
                    listeRow.get(4).getCell(1).setCellStyle(style);
                    
                    for (cptUE=0;cptUE<=eleves_ING1.get(0).getAnnee().getING1().size()-1;cptUE++){
                        listeRow.get(3).createCell(offset+cptUE).setCellValue(eleves_ING1.get(0).getAnnee().getING1().get(cptUE).getNomUE());
                        listeRow.get(3).getCell(offset+cptUE).setCellStyle(style);
                        listeRow.get(4).createCell(offset+cptUE).setCellValue(eleves_ING1.get(0).getAnnee().getING1().get(cptUE).getParamUE().getECTSUE());
                        listeRow.get(4).getCell(offset+cptUE).setCellStyle(style);
                    }
                    listeRow.get(3).createCell(offset+eleves_ING1.get(0).getAnnee().getING1().size()).setCellValue("Moyenne de l'année");
                    listeRow.get(3).getCell(offset+eleves_ING1.get(0).getAnnee().getING1().size()).setCellStyle(style);
                    listeRow.get(3).createCell(offset+eleves_ING1.get(0).getAnnee().getING1().size()+1).setCellValue("Nombre d'ETCS validé");
                    listeRow.get(3).getCell(offset+eleves_ING1.get(0).getAnnee().getING1().size()+1).setCellStyle(style);
                    
                        
                    offset = offset + eleves_ING1.get(0).getAnnee().getING1().size() + 2;
                    
            }else if(i==2){
                
                listeRow.get(2).createCell(offset).setCellValue("2ème année");
                feuille.addMergedRegion(new CellRangeAddress(
                        2, // mention first row here
                        2, //mention last row here, it is 1 as we are doing a column wise merging
                        offset, //mention first column of merging
                        offset + eleves_ING2.get(0).getAnnee().getING2().size() + 1 //mention last column to include in merge
                ));
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(2,2,offset,offset + eleves_ING2.get(0).getAnnee().getING2().size() + 1), feuille, wb);
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(2,2,offset,offset + eleves_ING2.get(0).getAnnee().getING2().size() + 1), feuille, wb);
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(2,2,offset,offset + eleves_ING2.get(0).getAnnee().getING2().size() + 1), feuille, wb);
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(2,2,offset,offset + eleves_ING2.get(0).getAnnee().getING2().size() + 1), feuille, wb);
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(2,2,offset,offset + eleves_ING2.get(0).getAnnee().getING2().size() + 1), feuille, wb);
                
                for (cptUE=0;cptUE<=eleves_ING2.get(0).getAnnee().getING2().size()-1;cptUE++){
                    listeRow.get(3).createCell(offset+cptUE).setCellValue(eleves_ING2.get(0).getAnnee().getING2().get(cptUE).getNomUE());
                    listeRow.get(3).getCell(offset+cptUE).setCellStyle(style);
                    listeRow.get(4).createCell(offset+cptUE).setCellValue(eleves_ING2.get(0).getAnnee().getING2().get(cptUE).getParamUE().getECTSUE());
                    listeRow.get(4).getCell(offset+cptUE).setCellStyle(style);
                }
                listeRow.get(3).createCell(offset+eleves_ING2.get(0).getAnnee().getING2().size()).setCellValue("Moyenne de l'année");
                listeRow.get(3).getCell(offset+eleves_ING2.get(0).getAnnee().getING2().size()).setCellStyle(style);
                listeRow.get(3).createCell(offset+eleves_ING2.get(0).getAnnee().getING2().size()+1).setCellValue("Nombre d'ETCS validé");
                listeRow.get(3).getCell(offset+eleves_ING2.get(0).getAnnee().getING2().size()+1).setCellStyle(style);
                
                offset = offset + eleves_ING2.get(0).getAnnee().getING2().size() + 2;
                
            }else{
                
                listeRow.get(2).createCell(offset).setCellValue("3ème année");
                feuille.addMergedRegion(new CellRangeAddress(
                        2, // mention first row here
                        2, //mention last row here, it is 1 as we are doing a column wise merging
                        offset, //mention first column of merging
                        offset + eleves_ING3.get(0).getAnnee().getING3().size() + 1 //mention last column to include in merge
                ));
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(2,2,offset,offset + eleves_ING3.get(0).getAnnee().getING3().size() + 1), feuille, wb);
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(2,2,offset,offset + eleves_ING3.get(0).getAnnee().getING3().size() + 1), feuille, wb);
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(2,2,offset,offset + eleves_ING3.get(0).getAnnee().getING3().size() + 1), feuille, wb);
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(2,2,offset,offset + eleves_ING3.get(0).getAnnee().getING3().size() + 1), feuille, wb);
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(2,2,offset,offset + eleves_ING3.get(0).getAnnee().getING3().size() + 1), feuille, wb);
                
                for (cptUE=0;cptUE<=eleves_ING3.get(0).getAnnee().getING3().size()-1;cptUE++){
                    listeRow.get(3).createCell(offset+cptUE).setCellValue(eleves_ING3.get(0).getAnnee().getING3().get(cptUE).getNomUE());
                    listeRow.get(3).getCell(offset+cptUE).setCellStyle(style);
                    listeRow.get(4).createCell(offset+cptUE).setCellValue(eleves_ING3.get(0).getAnnee().getING3().get(cptUE).getParamUE().getECTSUE());
                    listeRow.get(4).getCell(offset+cptUE).setCellStyle(style);
                }
                listeRow.get(3).createCell(offset+eleves_ING3.get(0).getAnnee().getING3().size()).setCellValue("Moyenne de l'année");
                listeRow.get(3).getCell(offset+eleves_ING3.get(0).getAnnee().getING3().size()).setCellStyle(style);
                listeRow.get(3).createCell(offset+eleves_ING3.get(0).getAnnee().getING3().size()+1).setCellValue("Nombre d'ETCS validé");
                listeRow.get(3).getCell(offset+eleves_ING3.get(0).getAnnee().getING3().size()+1).setCellStyle(style);
                
                offset = offset + eleves_ING3.get(0).getAnnee().getING3().size() + 2;
               
            }
            
        
        }
        offset = 2;
        float ECTSvalide = 0;
        for (i=1;i<=3;i++){  // pour chaque annee
            if(i==1){
                
                for(cptEleve=0;cptEleve<=eleves_ING1.size()-1;cptEleve++){ //pour chaque Ã©lÃ¨ve

                    listeRow.get(5+cptEleve).createCell(0).setCellValue(eleves_ING1.get(cptEleve).getNomEleve());
                    listeRow.get(5+cptEleve).getCell(0).setCellStyle(style);
                    listeRow.get(5+cptEleve).createCell(1).setCellValue(eleves_ING1.get(cptEleve).getPrenomEleve());
                    listeRow.get(5+cptEleve).getCell(1).setCellStyle(style);
                    
                    ECTSvalide=0;
                            
                    for(cptUE=0;cptUE<eleves_ING1.get(cptEleve).getAnnee().getING1().size();cptUE++){ // pour chaque ue
                        
                       
                        sommeCoeffMat=0;
                        sommeNoteUE=0;
                        
                        for(cptMat=0;cptMat<eleves_ING1.get(cptEleve).getAnnee().getING1().get(cptUE).getMatieres().size()-1;cptMat++)
                        {   
                            //calcul de la moyenne de l'ue
                            //TODO

                            coeffMat = eleves_ING1.get(0).getAnnee().getING1().get(cptUE).getParamUE().getMatieresUE().get(cptMat).getCoeffMatiere();
                            sommeCoeffMat += eleves_ING1.get(0).getAnnee().getING1().get(cptUE).getParamUE().getMatieresUE().get(cptMat).getCoeffMatiere();
                            sommeNoteUE += eleves_ING1.get(cptEleve).getAnnee().getING1().get(cptUE).getMatieres().get(cptMat).getNoteMatiere()*coeffMat;

                        }
                    
                
                        moyenneUE = sommeNoteUE/sommeCoeffMat;
                        listeRow.get(5+cptEleve).createCell(offset + cptUE).setCellValue(df.format(moyenneUE));
                        listeRow.get(5+cptEleve).getCell(offset + cptUE).setCellStyle(style);
                        
                        
                        //listeRow.get(5+cptEleve).createCell(offset+cptUE).setCellValue("MoyenneUE" + this.eleve.getAnnee().getING1().get(cptUE).getNomUE());
                        //listeRow.get(5+cptEleve).getCell(offset+cptUE).setCellStyle(style);
                        
                        if(moyenneUE >= extractParam1.getValidationUE()){             
                        	ECTSvalide = ECTSvalide + eleves_ING1.get(cptEleve).getAnnee().getING1().get(cptUE).getParamUE().getECTSUE();
                        }
                        
                        listeRow.get(5+cptEleve).createCell(offset+eleves_ING1.get(0).getAnnee().getING1().size()+1).setCellValue(ECTSvalide);
                        listeRow.get(5+cptEleve).getCell(offset+eleves_ING1.get(0).getAnnee().getING1().size()+1).setCellStyle(style);
                    }
                    listeRow.get(5+cptEleve).createCell(offset+eleves_ING1.get(0).getAnnee().getING1().size()).setCellValue(eleves_ING1.get(cptEleve).getMoyenneGenerale());
                    listeRow.get(5+cptEleve).getCell(offset+eleves_ING1.get(0).getAnnee().getING1().size()).setCellStyle(style);
                    
                    
                    listeRow.get(5+cptEleve).createCell(offset+eleves_ING1.get(0).getAnnee().getING1().size()+1).setCellValue(ECTSvalide);
                    listeRow.get(5+cptEleve).getCell(offset+eleves_ING1.get(0).getAnnee().getING1().size()+1).setCellStyle(style);

                }
                
                offset = offset + eleves_ING1.get(0).getAnnee().getING1().size() + 2;
                
            }else if(i==2){
                
                for(cptEleve=0;cptEleve<=eleves_ING1.size()-1;cptEleve++){ //pour chaque Ã©lÃ¨ve

                    listeRow.get(5+cptEleve).createCell(0).setCellValue(eleves_ING1.get(cptEleve).getNomEleve());
                    listeRow.get(5+cptEleve).getCell(0).setCellStyle(style);
                    listeRow.get(5+cptEleve).createCell(1).setCellValue(eleves_ING1.get(cptEleve).getPrenomEleve());
                    listeRow.get(5+cptEleve).getCell(1).setCellStyle(style);
                    
                    ECTSvalide=0;
                            
                    for(cptUE=0;cptUE<eleves_ING2.get(cptEleve).getAnnee().getING2().size();cptUE++){ // pour chaque ue
                        
                        sommeCoeffMat=0;
                        sommeNoteUE=0;
                        
                        for(cptMat=0;cptMat<eleves_ING2.get(cptEleve).getAnnee().getING2().get(cptUE).getMatieres().size()-1;cptMat++)
                        {   
                            //calcul de la moyenne de l'ue
                            //TODO

                            coeffMat = eleves_ING2.get(0).getAnnee().getING2().get(cptUE).getParamUE().getMatieresUE().get(cptMat).getCoeffMatiere();
                            sommeCoeffMat += eleves_ING2.get(0).getAnnee().getING2().get(cptUE).getParamUE().getMatieresUE().get(cptMat).getCoeffMatiere();
                            sommeNoteUE += eleves_ING2.get(cptEleve).getAnnee().getING2().get(cptUE).getMatieres().get(cptMat).getNoteMatiere()*coeffMat;

                        }
                        
                        moyenneUE = sommeNoteUE/sommeCoeffMat;
                        listeRow.get(5+cptEleve).createCell(offset + cptUE).setCellValue(df.format(moyenneUE));
                        listeRow.get(5+cptEleve).getCell(offset + cptUE).setCellStyle(style);
                        
                        
                        //listeRow.get(5+cptEleve).createCell(offset+cptUE).setCellValue("MoyenneUE" + this.eleve.getAnnee().getING1().get(cptUE).getNomUE());
                        //listeRow.get(5+cptEleve).getCell(offset+cptUE).setCellStyle(style);
                        //ECTSvalide = ECTSvalide + eleves_ING2.get(cptEleve).getAnnee().getING2().get(cptUE).getMoyenneUE();
                        if(moyenneUE >= /*11*/extractParam2.getValidationUE()){             
                        	ECTSvalide = ECTSvalide + eleves_ING2.get(cptEleve).getAnnee().getING2().get(cptUE).getParamUE().getECTSUE();
                        }
                        
                        listeRow.get(5+cptEleve).createCell(offset+eleves_ING2.get(0).getAnnee().getING2().size()+1).setCellValue(ECTSvalide);
                        listeRow.get(5+cptEleve).getCell(offset+eleves_ING2.get(0).getAnnee().getING2().size()+1).setCellStyle(style);
                    }
                    listeRow.get(5+cptEleve).createCell(offset+eleves_ING2.get(0).getAnnee().getING2().size()).setCellValue(eleves_ING2.get(cptEleve).getMoyenneGenerale());
                    listeRow.get(5+cptEleve).getCell(offset+eleves_ING2.get(0).getAnnee().getING2().size()).setCellStyle(style);
                    
                    
                    //listeRow.get(5+cptEleve).createCell(offset+eleves_ING1.get(0).getAnnee().getING1().size()+1).setCellValue(ECTSvalide);
                    //listeRow.get(5+cptEleve).getCell(offset+eleves_ING1.get(0).getAnnee().getING1().size()+1).setCellStyle(style);

                }
                
                offset = offset + eleves_ING2.get(0).getAnnee().getING2().size() + 2;
                
            }else{
                
                for(cptEleve=0;cptEleve<=eleves_ING3.size()-1;cptEleve++){ //pour chaque Ã©lÃ¨ve

                    listeRow.get(5+cptEleve).createCell(0).setCellValue(eleves_ING3.get(cptEleve).getNomEleve());
                    listeRow.get(5+cptEleve).getCell(0).setCellStyle(style);
                    listeRow.get(5+cptEleve).createCell(1).setCellValue(eleves_ING3.get(cptEleve).getPrenomEleve());
                    listeRow.get(5+cptEleve).getCell(1).setCellStyle(style);
                    
                    ECTSvalide=0;
                            
                    for(cptUE=0;cptUE<eleves_ING3.get(cptEleve).getAnnee().getING3().size();cptUE++){ // pour chaque ue
                        
                        sommeCoeffMat=0;
                        sommeNoteUE=0;
                        
                        for(cptMat=0;cptMat<eleves_ING3.get(cptEleve).getAnnee().getING3().get(cptUE).getMatieres().size()-1;cptMat++)
                        {   
                            //calcul de la moyenne de l'ue
                            //TODO

                            coeffMat = eleves_ING3.get(0).getAnnee().getING3().get(cptUE).getParamUE().getMatieresUE().get(cptMat).getCoeffMatiere();
                            sommeCoeffMat += eleves_ING3.get(0).getAnnee().getING3().get(cptUE).getParamUE().getMatieresUE().get(cptMat).getCoeffMatiere();
                            sommeNoteUE += eleves_ING3.get(cptEleve).getAnnee().getING3().get(cptUE).getMatieres().get(cptMat).getNoteMatiere()*coeffMat;

                        }
                        
                        moyenneUE = sommeNoteUE/sommeCoeffMat;
                        listeRow.get(5+cptEleve).createCell(offset + cptUE).setCellValue(df.format(moyenneUE));
                        listeRow.get(5+cptEleve).getCell(offset + cptUE).setCellStyle(style);
                        
                        
                        //listeRow.get(5+cptEleve).createCell(offset+cptUE).setCellValue("MoyenneUE" + this.eleve.getAnnee().getING1().get(cptUE).getNomUE());
                        //listeRow.get(5+cptEleve).getCell(offset+cptUE).setCellStyle(style);
                        //ECTSvalide = ECTSvalide + eleves_ING3.get(cptEleve).getAnnee().getING3().get(cptUE).getMoyenneUE();
                        
                        if(moyenneUE >= extractParam3.getValidationUE()){             
                        	ECTSvalide = ECTSvalide + eleves_ING3.get(cptEleve).getAnnee().getING3().get(cptUE).getParamUE().getECTSUE();
                        }
                        
                        listeRow.get(5+cptEleve).createCell(offset+eleves_ING3.get(0).getAnnee().getING3().size()+1).setCellValue(ECTSvalide);
                        listeRow.get(5+cptEleve).getCell(offset+eleves_ING3.get(0).getAnnee().getING3().size()+1).setCellStyle(style);
                    }
                    listeRow.get(5+cptEleve).createCell(offset+eleves_ING3.get(0).getAnnee().getING3().size()).setCellValue(eleves_ING3.get(cptEleve).getMoyenneGenerale());
                    listeRow.get(5+cptEleve).getCell(offset+eleves_ING3.get(0).getAnnee().getING3().size()).setCellStyle(style);
                    
                    
                    //listeRow.get(5+cptEleve).createCell(offset+eleves_ING1.get(0).getAnnee().getING1().size()+1).setCellValue(ECTSvalide);
                    //listeRow.get(5+cptEleve).getCell(offset+eleves_ING1.get(0).getAnnee().getING1().size()+1).setCellStyle(style);

                }
                
                
                
            }
        
        }
        
        //moyenne gï¿½nï¿½ral des trois ans
        listeRow.get(2).createCell(offset+eleves_ING3.get(0).getAnnee().getING3().size()+2).setCellValue("Moyenne de l'élève");
        feuille.addMergedRegion(new CellRangeAddress(
                2, // mention first row here
                4, //mention last row here, it is 1 as we are doing a column wise merging
                offset+eleves_ING3.get(0).getAnnee().getING3().size()+2, //mention first column of merging
                offset+eleves_ING3.get(0).getAnnee().getING3().size()+2 //mention last column to include in merge
        ));

        RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(2,4,offset+eleves_ING3.get(0).getAnnee().getING3().size()+2,offset+eleves_ING3.get(0).getAnnee().getING3().size()+2), feuille, wb);
        RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(2,4,offset+eleves_ING3.get(0).getAnnee().getING3().size()+2,offset+eleves_ING3.get(0).getAnnee().getING3().size()+2), feuille, wb);
        RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(2,4,offset+eleves_ING3.get(0).getAnnee().getING3().size()+2,offset+eleves_ING3.get(0).getAnnee().getING3().size()+2), feuille, wb);
        RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, new CellRangeAddress(2,4,offset+eleves_ING3.get(0).getAnnee().getING3().size()+2,offset+eleves_ING3.get(0).getAnnee().getING3().size()+2), feuille, wb);
        
        
        for(cptEleve=0;cptEleve<=eleves_ING3.size()-1;cptEleve++){ //pour chaque Ã©lÃ¨ve
            listeRow.get(5+cptEleve).createCell(offset+eleves_ING3.get(0).getAnnee().getING3().size()+2).setCellValue(df.format((eleves_ING1.get(cptEleve).getMoyenneGenerale()+eleves_ING2.get(cptEleve).getMoyenneGenerale()+eleves_ING3.get(cptEleve).getMoyenneGenerale())/3));
            listeRow.get(5+cptEleve).getCell(offset+eleves_ING3.get(0).getAnnee().getING3().size()+2).setCellStyle(style);
        }
        
        
        int p;
        for(p=0; p<=100;p++){
            feuille.autoSizeColumn(p, true);
        }
        
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(cheminFichierSortie+"\\RecapitulatifSur3ans"+NomFichierPromo+".xlsx");
            wb.write(fileOut);
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // JAVAFX Alert
    	alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Information");
    	alert.setHeaderText("Information");
    	alert.setContentText("Récapitulatif sur 3 ans généré");
    	alert.showAndWait();
        }

        
    }

    public ParamGeneral getParam() {
        return param;
    }

    public void setParam(ParamGeneral param) {
        this.param = param;
    }

}