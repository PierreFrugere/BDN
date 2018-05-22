/*
 * Controller.java			18/05/2018
 * 3iL - Projet Bulletin de Note - 2018
 */
package application;

import java.io.File;
<<<<<<< HEAD
import java.io.IOException;
=======
>>>>>>> ab36d7d6a58bfcaaa4cefd2c5b3d6e2f75f856be
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import gestion.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import metier.Eleve;

/**
 * Controller de la vue "application.fxml"
 * @author WilliamHenry, BenjaminMazoyer & PierreFrugere
 * @version 2.0
 */
public class Controller {

    @FXML //  fx:id="tp_principal"
    private TabPane tp_principal; // Value injected by FXMLLoader

    /*
     * Buttons
     */
    
    @FXML //  fx:id="jfxb_bilanCompensation"
    private JFXButton jfxb_bilanCompensation; // Home "Bilan de Compensation" Button
    
    @FXML //  fx:id="jfxb_bulletinIndividuel"
    private JFXButton jfxb_bulletinIndividuel; // Home "Bulletin Individuel" Button
    
    @FXML //  fx:id="jfxb_recapTroisAn"
    private JFXButton jfxb_recapTroisAn; // Home "Recap 3 ans" Button
    
    @FXML //  fx:id="jfxb_bulletinGeneral"
    private JFXButton jfxb_bulletinGeneral; // Home "Bulletin Général" Button
    
    @FXML //  fx:id="jfxb_HomeFromBilanCompensation"
    private JFXButton jfxb_HomeFromBilanCompensation; // "Bilan de compensation" Home Button
    
    @FXML //  fx:id="jfxb_HomeFromBilanIndividuel"
    private JFXButton jfxb_HomeFromBilanIndividuel; // "Bilan Individuel" Home Button
    
    @FXML //  fx:id="jfxb_HomeFromRecapTroisAns"
    private JFXButton jfxb_HomeFromRecapTroisAns; // "Récap sur 3 ans" Home Button
    
    @FXML //  fx:id="jfxb_HomeFromBulletinGeneral"
    private JFXButton jfxb_HomeFromBulletinGeneral; // "Bulletin Général" Home Button
    
    
    /*
     * Combo Box
     */
    
    @FXML // fx:id="jfxCB_Promotion"
    private JFXComboBox<String> jfxCB_Promotion;		// Promotion Combo box
    
<<<<<<< HEAD
    @FXML // fx:id="jbxCB_Annee"
    private JFXComboBox<String> jfxCB_Annee;			// Annee Combo box
    
    @FXML // fx:id="jbxCB_Eleve"
    private JFXComboBox<String> jfxCB_Eleve;			// Eleve Combo box
    
    @FXML // fx:id="test"
    private ComboBox<String> test;
    
=======
    @FXML // fx:id="jfxCB_Annee"
    private JFXComboBox<String> jfxCB_Annee;			// Annee Combo box
    
    @FXML // fx:id="jfxCB_Eleve"
    private JFXComboBox<String> jfxCB_Eleve;			// Eleve Combo box
    
>>>>>>> ab36d7d6a58bfcaaa4cefd2c5b3d6e2f75f856be
    /*
     * Tabs
     */
    
    @FXML //  fx:id="t_bilanCompensation"
    private Tab t_accueil; // "Accueil" Tab
    
    @FXML //  fx:id="t_bilanCompensation"
    private Tab t_bilanCompensation; // "Bilan de Compensation" Tab
    
    @FXML //  fx:id="t_bilanIndividuel"
    private Tab t_bilanIndividuel; // "Bilan Individuel" Tab
    
    @FXML //  fx:id="t_recapTroisAn"
    private Tab t_recapTroisAn; // "Récap 3 ans" Tab
    
    @FXML //  fx:id="t_bulletinGeneral"
    private Tab t_bulletinGeneral; // "Bulletin Général" Tab
    
    // Selection Model to switch between tabs
    private SingleSelectionModel<Tab> selectionModel;
    
    /*
     * Working variables
     */
    String cheminFichier;
    ArrayList<Eleve> listEleves = new ArrayList<Eleve>();
	
    
	@FXML
	private void initialize() {
		// Initialise selectionModel value during instanciation
		this.selectionModel = tp_principal.getSelectionModel();
		
		// Gettings infos to fill combo boxes
		String[] listFichiers;
<<<<<<< HEAD
    	this.cheminFichier = new Extracteur("ressources\\ParametrageAccesFichier.xml").ExtracteurCheminFichierDistant("Resources");
    	File repertoire = new File(this.cheminFichier);
=======
    	String cheminFichier = new Extracteur("ressources\\ParametrageAccesFichier.xml").ExtracteurCheminFichierDistant("Resources");
    	File repertoire = new File(cheminFichier);
>>>>>>> ab36d7d6a58bfcaaa4cefd2c5b3d6e2f75f856be
    	
    	listFichiers = repertoire.list();
    	
    	ArrayList<String> listPromo = new ArrayList<String>();
<<<<<<< HEAD
//    	ArrayList<String> listAnnee = new ArrayList<String>();    
    	ArrayList<String> listPromosSansDoublons = new ArrayList<String>();
//    	ArrayList<String> listAnneesSansDoublons = new ArrayList<String>();
=======
    	ArrayList<String> listAnnee = new ArrayList<String>();    
    	ArrayList<String> listPromosSansDoublons = new ArrayList<String>();
    	ArrayList<String> listAnneesSansDoublons = new ArrayList<String>();
>>>>>>> ab36d7d6a58bfcaaa4cefd2c5b3d6e2f75f856be
    	
    	for (int cpt = 0; cpt < listFichiers.length; cpt++) {
    		if (listFichiers[cpt].endsWith(".csv")) {
    			if (listFichiers[cpt].length() > 11) {
<<<<<<< HEAD
//    				listAnnee.add(listFichiers[cpt].substring(7, 11));
=======
    				listAnnee.add(listFichiers[cpt].substring(7, 11));
>>>>>>> ab36d7d6a58bfcaaa4cefd2c5b3d6e2f75f856be
    				listPromo.add(listFichiers[cpt].substring(0, 7));
    			}
    		}
    	}
    	
    	// Remove duplicates from YEARS ArrayList ans sort list
    	Set<String> set = new HashSet<>();
<<<<<<< HEAD
//    	set.addAll(listAnnee);
//    	listAnneesSansDoublons.addAll(set);
//    	listAnneesSansDoublons.add(""); 		// Adding empty value to reset combo
//    	Collections.sort(listAnneesSansDoublons);
=======
    	set.addAll(listAnnee);
    	listAnneesSansDoublons.addAll(set);
    	Collections.sort(listAnneesSansDoublons);
>>>>>>> ab36d7d6a58bfcaaa4cefd2c5b3d6e2f75f856be
    	
    	// Remove duplicates from PROMO ArrayList and sort list
    	set.clear();
    	set.addAll(listPromo);
    	listPromosSansDoublons.addAll(set);
<<<<<<< HEAD
    	listPromosSansDoublons.add("");			// Adding empty value to reset combo
    	Collections.sort(listPromosSansDoublons);
    	
    	// Add List of YEAR and PROMOTION to respective Combo box
    	ObservableList<String> promotionComboBoxList = FXCollections.observableArrayList(listPromosSansDoublons);
    	jfxCB_Promotion.getItems().addAll(promotionComboBoxList);
    	
//    	ObservableList<String> anneeComboBoxList = FXCollections.observableArrayList(listAnneesSansDoublons);
//    	jfxCB_Annee.getItems().addAll(anneeComboBoxList);
    	
    	// Disabling Eleves combo box
    	jfxCB_Eleve.setDisable(true);
		jfxCB_Eleve.setValue("");
		
		// Disabling Year combo box
		jfxCB_Annee.setDisable(true);
		jfxCB_Annee.setValue("");
=======
    	Collections.sort(listPromosSansDoublons);
    	
    	for (String elm: listAnneesSansDoublons) {
    		System.out.println("Liste Ann�es: " + elm);
    	}
    	
    	for (String elm: listPromosSansDoublons) {
    		System.out.println("Liste Promos: " + elm);
    	}
    	
    	// Add List of YEAR and PROMOTION to respective Combo box
    	ObservableList<String> promotionComboBoxList = FXCollections.observableArrayList(listPromosSansDoublons);
    	jfxCB_Promotion = new JFXComboBox<String>(promotionComboBoxList);
    	
    	ObservableList<String> anneeComboBoxList = FXCollections.observableArrayList(listAnneesSansDoublons);
    	jfxCB_Annee = new JFXComboBox<String>(anneeComboBoxList);
    	
    	// Extract Eleves
    	ArrayList<Eleve> listEleves = new ArrayList<Eleve>();
    	Extracteur extracteurEleve = new Extracteur(cheminFichier + "\\Promo11ING1.csv");
    	
    	try {
			listEleves = extracteurEleve.ExtracteurEleves("Promo11ING1");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	for (Eleve elm: listEleves) {
    		System.out.println("Eleve: " + elm.getPrenomEleve() + " " + elm.getNomEleve());
    	}
		
>>>>>>> ab36d7d6a58bfcaaa4cefd2c5b3d6e2f75f856be
	}

	/*
	 * Button Actions
	 */
    @FXML
    private void handleHomeBilanCompensationAction(ActionEvent event) {   	
    	// Switch to "Bilan de compensation" Tab
    	this.selectionModel.select(t_bilanCompensation);
    }
    
    @FXML
    private void handleHomeBulletinIndividuelAction(ActionEvent event) {   	
    	// Switch to "Bulletin Individuel" Tab
    	this.selectionModel.select(t_bilanIndividuel);
    }
    
    @FXML
    private void handleHomeRecapTroisAnsAction(ActionEvent event) {   	
    	// Switch to "Recap sur 3 ans" Tab
    	this.selectionModel.select(t_recapTroisAn);
    }
    
    @FXML
    private void handleHomeBulletinGeneralAction(ActionEvent event) {   	
    	// Switch to "Bulletin Général" Tab
    	this.selectionModel.select(t_bulletinGeneral);
    }
    
    @FXML
    private void handleReturnHomeAction(ActionEvent event) {   	
    	// Switch to "Accueil" Tab
    	this.selectionModel.select(t_accueil);
    }
    
    @FXML
    private void handleEditionButtonBulletinIndividuel(ActionEvent event) {
<<<<<<< HEAD
    	
    	// Retrieve index of selected student
    	int indexOfSelectedStudent = jfxCB_Eleve.getSelectionModel().getSelectedIndex();
    	
    	// Retrieve selected Eleve object
    	if (!this.listEleves.isEmpty()) {
    		Eleve selectedEleve = this.listEleves.get(indexOfSelectedStudent);
    		System.out.println("Selected Student: " + selectedEleve.getPrenomEleve() + " " + selectedEleve.getNomEleve() + "\tAt Index: " + indexOfSelectedStudent);
    		
    		BulletinNote bulletin = new BulletinNote(selectedEleve.getNomEleve(), selectedEleve.getPrenomEleve(), selectedEleve.getPromoEleve(), 11, 7, "01011970");
    		try {
				bulletin.bulletinIndividuel(selectedEleve.getNomEleve(), selectedEleve.getPrenomEleve(), jfxCB_Annee.getValue(), jfxCB_Promotion.getValue());
			} catch (ParseException | IOException e) {
				e.printStackTrace();
			}
    	}
      	
    }
    
    @FXML
    private void yearAndPromoAreFilled(ActionEvent event) {
    	// Retrieving year and promotion selected by user
    	String year = "";
    	String promo = "";
    	if (jfxCB_Annee.getValue() != null) {
    		year = jfxCB_Annee.getValue();
    	}
    	
    	if (jfxCB_Promotion.getValue() != null) {
    		promo = jfxCB_Promotion.getValue();
    	}
    	
    	if (year != "" && promo != "") {
    		// Extract Eleves depending on selected promotion and year
        	Extracteur extracteurEleve = new Extracteur(this.cheminFichier + "\\" + promo + year + ".csv");
        	
        	try {
        		
    			this.listEleves = extracteurEleve.ExtracteurEleves(promo + year);
    			
    			// Getting a list of eleves name and firstname
    			ArrayList<String> listeEleveByNameAndFirstName = new ArrayList<String>();
    			for (Eleve eleve: this.listEleves) {
    				listeEleveByNameAndFirstName.add(eleve.getPrenomEleve() + " " + eleve.getNomEleve());
    			}
    			
    			// Set list of eleves into the combo box
    			ObservableList<String> elevesComboBoxList = FXCollections.observableArrayList(listeEleveByNameAndFirstName);
    			jfxCB_Eleve.getItems().clear();
    	    	jfxCB_Eleve.getItems().addAll(elevesComboBoxList);
    	    	
    	    	// Enabling Eleves Combo box
    	    	jfxCB_Eleve.setDisable(false);
 
    		} catch (ParseException e) {
    			e.printStackTrace();
    		}
    	} else {
    		// Disabling eleves combo box
    		jfxCB_Eleve.setDisable(true);
    		jfxCB_Eleve.getItems().clear();
    		jfxCB_Eleve.setValue("");
    	}
    }
    
    @FXML
    private void promoIsFilled(ActionEvent event) {
    	String promo = "";
    	ArrayList<String> listAnnee = new ArrayList<String>(); 
    	ArrayList<String> listAnneesSansDoublons = new ArrayList<String>();
    	
    	// Gettings infos to fill combo boxes
    	String[] listFichiers;
    	this.cheminFichier = new Extracteur("ressources\\ParametrageAccesFichier.xml").ExtracteurCheminFichierDistant("Resources");
    	File repertoire = new File(this.cheminFichier);
    	    	
    	listFichiers = repertoire.list();
    	
    	if (jfxCB_Promotion.getValue() != "") {
    		promo = jfxCB_Promotion.getValue();
    		
    		listAnnee.clear();
    		
    		for (int cpt = 0; cpt < listFichiers.length; cpt++) {
        		if (listFichiers[cpt].endsWith(".csv")) {
        			if (listFichiers[cpt].length() > 11 && listFichiers[cpt].contains(promo)) {
        				listAnnee.add(listFichiers[cpt].substring(7, 11));
        			}
        		}
        	}
    		
    		// Remove duplicates from YEARS ArrayList ans sort list
        	Set<String> set = new HashSet<>();
        	set.addAll(listAnnee);
        	listAnneesSansDoublons.clear();
        	listAnneesSansDoublons.addAll(set);
        	listAnneesSansDoublons.add(""); 		// Adding empty value to reset combo
        	Collections.sort(listAnneesSansDoublons);
        	
        	// Adding content in combo box
        	ObservableList<String> anneeComboBoxList = FXCollections.observableArrayList(listAnneesSansDoublons);
        	jfxCB_Annee.getItems().clear();
        	jfxCB_Annee.getItems().addAll(anneeComboBoxList);
        	
        	// Enabling comboBox
        	jfxCB_Annee.setDisable(false);
        	
    	} else {
    		jfxCB_Annee.setDisable(true);
    		jfxCB_Annee.getItems().clear();
    		jfxCB_Annee.setValue("");
    	}
=======
    	    	
>>>>>>> ab36d7d6a58bfcaaa4cefd2c5b3d6e2f75f856be
    }

}
