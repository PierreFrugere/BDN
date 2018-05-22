/*
 * Controller.java			18/05/2018
 * 3iL - Projet Bulletin de Note - 2018
 */
package application;

import java.io.File;
import java.io.IOException;
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
    private JFXButton jfxb_bulletinGeneral; // Home "Bulletin G√©n√©ral" Button
    
    @FXML //  fx:id="jfxb_HomeFromBilanCompensation"
    private JFXButton jfxb_HomeFromBilanCompensation; // "Bilan de compensation" Home Button
    
    @FXML //  fx:id="jfxb_HomeFromBilanIndividuel"
    private JFXButton jfxb_HomeFromBilanIndividuel; // "Bilan Individuel" Home Button
    
    @FXML //  fx:id="jfxb_HomeFromRecapTroisAns"
    private JFXButton jfxb_HomeFromRecapTroisAns; // "R√©cap sur 3 ans" Home Button
    
    @FXML //  fx:id="jfxb_HomeFromBulletinGeneral"
    private JFXButton jfxb_HomeFromBulletinGeneral; // "Bulletin G√©n√©ral" Home Button
    
    @FXML //  fx:id="jfxb_EditionBilanIndividuel"
    private JFXButton jfxb_EditionBilanIndividuel; // "Bulletin Individuel Edition Button
    
    @FXML //  fx:id="jfxb_EditionBulletinGeneral"
    private JFXButton jfxb_EditionBulletinGeneral; // "Bulletin GÈnÈral Edition Button
    
    @FXML //  fx:id="jfxb_EditionBilanCompensation"
    private JFXButton jfxb_EditionBilanCompensation; // "Bilan Compensation Edition Button
    
    @FXML //  fx:id="jfxb_EditionRecapTroisAns"
    private JFXButton jfxb_EditionRecapTroisAns; // "RÈcap sur 3 ans Edition Button
    
    
    /*
     * Combo Box
     */
    
    @FXML // fx:id="jfxCB_PromotionInd"
    private JFXComboBox<String> jfxCB_PromotionInd;		// Promotion Combo box
    
    @FXML // fx:id="jfxCB_AnneeInd"
    private JFXComboBox<String> jfxCB_AnneeInd;			// Annee Combo box
    
    @FXML // fx:id="jfxCB_EleveInd"
    private JFXComboBox<String> jfxCB_EleveInd;			// Eleve Combo box
    
    @FXML // fx:id="jfxCB_PromotionGen"
    private JFXComboBox<String> jfxCB_PromotionGen;		// Promotion Combo box Bulletin General
    
    @FXML // fx:id="jfxCB_AnneeGen"
    private JFXComboBox<String> jfxCB_AnneeGen;			// Annee Combo box Bulletin General
    
    @FXML // fx:id="jfxCB_PromotionComp"
    private JFXComboBox<String> jfxCB_PromotionComp;	// Promotion Combo box Bilan de compensation
    
    @FXML // fx:id="jfxCB_AnneeComp"
    private JFXComboBox<String> jfxCB_AnneeComp;		// Annee Combo box Bilan de compensation
    
    @FXML // fx:id="jfxCB_PromotionRecap"
    private JFXComboBox<String> jfxCB_PromotionRecap;	// Promotion Combo box RÈcap sur 3 ans
    
    /*
     * Tabs
     */
    
    @FXML //  fx:id="t_bilanCompensation"
    private Tab t_accueil; // "Accueil" Tab
    
    @FXML //  fx:id="t_bilanCompensation"
    private Tab t_bilanCompensation; // "Bilan de Compensation" Tab
    
    @FXML //  fx:id="t_bilanIndividuel"
    private Tab t_bilanIndividuel; // "Bilan Individuel" Tab
    
    @FXML //  fx:id="t_recapTroisAns"
    private Tab t_recapTroisAns; // "R√©cap 3 ans" Tab
    
    @FXML //  fx:id="t_bulletinGeneral"
    private Tab t_bulletinGeneral; // "Bulletin G√©n√©ral" Tab
    
    // Selection Model to switch between tabs
    private SingleSelectionModel<Tab> selectionModel;
    
    /*
     * Working variables
     */
    String cheminFichier;
    ArrayList<Eleve> listEleves = new ArrayList<Eleve>();
	
    
    /*
     * Initialise all scene on first show
     */
	@FXML
	private void initialize() {
		// Initialise selectionModel value during instanciation
		this.selectionModel = tp_principal.getSelectionModel();	
		
		// Initialize Bilan individuel tab
		this.initialiseBilanIndividuelTab();
		
		// Initialise Bulletin General Tab
		this.initialiseBulletinGeneralTab();
		
		// Initialise Bilan Compensation Tab
		this.initialiseBilanCompensationTab();
		
		// Initialise Recap sur 3 ans Tab
		this.initialiseRecapTroisAnsTab();
	}

	/*
	 * Home Buttons Action
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
    	this.selectionModel.select(t_recapTroisAns);
    }
    
    @FXML
    private void handleHomeBulletinGeneralAction(ActionEvent event) {   	
    	// Switch to "Bulletin GÈnÈral" Tab
    	this.selectionModel.select(t_bulletinGeneral);
    }
    
    @FXML
    private void handleReturnHomeAction(ActionEvent event) {   	
    	// Switch to "Accueil" Tab
    	this.selectionModel.select(t_accueil);
    }
    
    /*
     * Edition Buttons Action
     */
    
    @FXML
    private void handleEditionButtonBulletinIndividuel(ActionEvent event) {
    	
    	if (jfxCB_EleveInd.getSelectionModel().getSelectedItem() != "") {
    		// Retrieve index of selected student
        	int indexOfSelectedStudent = jfxCB_EleveInd.getSelectionModel().getSelectedIndex();
        	
        	// Retrieve selected Eleve object
        	if (!this.listEleves.isEmpty()) {
        		Eleve selectedEleve = this.listEleves.get(indexOfSelectedStudent);
        		System.out.println("Selected Student: " + selectedEleve.getPrenomEleve() + " " + selectedEleve.getNomEleve() + "\tAt Index: " + indexOfSelectedStudent);
        		
        		BulletinNote bulletin = new BulletinNote(selectedEleve.getNomEleve(), selectedEleve.getPrenomEleve(), selectedEleve.getPromoEleve(), 11, 7, "01011970");
        		try {
    				bulletin.bulletinIndividuel(selectedEleve.getNomEleve(), selectedEleve.getPrenomEleve(), jfxCB_AnneeInd.getValue(), jfxCB_PromotionInd.getValue());
    			} catch (ParseException | IOException e) {
    				e.printStackTrace();
    			}
        	}
    	} else {
    		BulletinNote bulletin = new BulletinNote("", "", jfxCB_PromotionInd.getValue(), 11, 7, "01011970");
    		try {
				bulletin.bulletinIndividuel(jfxCB_AnneeInd.getValue(), jfxCB_PromotionInd.getValue());
			} catch (ParseException | IOException e) {
				e.printStackTrace();
			}
    	} 	
    }
    
    @FXML
    private void handleEditionButtonBulletinGeneral(ActionEvent event) {
    	
    	BulletinNote bulletin = new BulletinNote("", "", jfxCB_PromotionGen.getValue(), 11, 7, "01011970");
		try {
			bulletin.bulletinGeneral(jfxCB_AnneeGen.getValue(), jfxCB_PromotionGen.getValue());
		} catch (ParseException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    private void handleEditionButtonBilanCompensation(ActionEvent event) {
    	BulletinNote bulletin = new BulletinNote("", "", jfxCB_PromotionComp.getValue(), 11, 7, "01011970");
		try {
			bulletin.bilanCompensation(jfxCB_AnneeComp.getValue(), jfxCB_PromotionComp.getValue());
		} catch (ParseException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    private void handleEditionButtonRecapTroisAns(ActionEvent event) {
    	BulletinNote bulletin = new BulletinNote("", "", jfxCB_PromotionRecap.getValue(), 11, 7, "01011970");
		try {
			bulletin.recapSur3ans(jfxCB_PromotionRecap.getValue());
		} catch (ParseException e) {
			e.printStackTrace();
		}
    }
    
    /*
     * Behavior of UI changing depending on combo box value
     * For the Bulletin Individuel Tab
     */
    
    @FXML
    private void promoIsFilledOnBulletinIndiv(ActionEvent event) {
    	String promo = "";
    	ArrayList<String> listAnneesSansDoublons = new ArrayList<String>();
    	
    	if (jfxCB_PromotionInd.getValue() != "") {
    		promo = jfxCB_PromotionInd.getValue();
    		
    		listAnneesSansDoublons = this.retrieveAvailableAnneeByPromo(promo);
        	
        	// Adding content in combo box
        	ObservableList<String> anneeComboBoxList = FXCollections.observableArrayList(listAnneesSansDoublons);
        	jfxCB_AnneeInd.getItems().clear();
        	jfxCB_AnneeInd.getItems().addAll(anneeComboBoxList);
        	
        	// Enabling comboBox
        	jfxCB_AnneeInd.setDisable(false);
        	
    	} else {
    		jfxCB_AnneeInd.setDisable(true);
    		jfxCB_AnneeInd.getItems().clear();
    		jfxCB_AnneeInd.setValue("");
    		jfxb_EditionBilanIndividuel.setDisable(true);
    	}
    }
    
    @FXML
    private void yearAndPromoAreFilledOnBulletinIndiv(ActionEvent event) {
    	// Retrieving year and promotion selected by user
    	String year = "";
    	String promo = "";
    	if (jfxCB_AnneeInd.getValue() != null) {
    		year = jfxCB_AnneeInd.getValue();
    	}
    	
    	if (jfxCB_PromotionInd.getValue() != null) {
    		promo = jfxCB_PromotionInd.getValue();
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
    			jfxCB_EleveInd.getItems().clear();
    	    	jfxCB_EleveInd.getItems().addAll(elevesComboBoxList);
    	    	
    	    	// Enabling Eleves Combo box
    	    	jfxCB_EleveInd.setDisable(false);
    	    	
    	    	// Enabling Edition Button
    	    	jfxb_EditionBilanIndividuel.setDisable(false);
 
    		} catch (ParseException e) {
    			e.printStackTrace();
    		}
    	} else {
    		// Disabling eleves combo box
    		jfxCB_EleveInd.setDisable(true);
    		jfxCB_EleveInd.getItems().clear();
    		jfxCB_EleveInd.setValue("");
    		
    		// Disabling Edition Button
    		jfxb_EditionBilanIndividuel.setDisable(true);
    	}
    }
    
  
    /*
     * Behavior of UI changing depending on combo box value
     * For the Bulletin General Tab
     */
    
    @FXML
    private void promoIsFilledOnBulletinGeneral(ActionEvent event) {
    	String promo = "";
    	ArrayList<String> listAnneesSansDoublons = new ArrayList<String>();
    	
    	if (jfxCB_PromotionGen.getValue() != "") {
    		promo = jfxCB_PromotionGen.getValue();
    		
    		listAnneesSansDoublons = this.retrieveAvailableAnneeByPromo(promo);
        	
        	// Adding content in combo box
        	ObservableList<String> anneeComboBoxList = FXCollections.observableArrayList(listAnneesSansDoublons);
        	jfxCB_AnneeGen.getItems().clear();
        	jfxCB_AnneeGen.getItems().addAll(anneeComboBoxList);
        	
        	// Enabling comboBox
        	jfxCB_AnneeGen.setDisable(false);
        	
    	} else {
    		jfxCB_AnneeGen.setDisable(true);
    		jfxCB_AnneeGen.getItems().clear();
    		jfxCB_AnneeGen.setValue("");
    	}
    	
    }
    
    @FXML
    private void anneeIsFilledOnBulletinGeneral(ActionEvent event) {
    	
    	// If the year is selected the edition is possible, otherwise it's not
    	if (jfxCB_AnneeGen.getValue() != "") {
    		jfxb_EditionBulletinGeneral.setDisable(false);
    	} else {
    		jfxb_EditionBulletinGeneral.setDisable(true);
    	}
    }
    
    
    /*
     * Behavior of UI changing depending on combo box value
     * For the Bilan Compensation Tab
     */
    
    @FXML
    private void promoIsFilledOnBilanCompensation(ActionEvent event) {
    	String promo = "";
    	ArrayList<String> listAnneesSansDoublons = new ArrayList<String>();
    	
    	if (jfxCB_PromotionComp.getValue() != "") {
    		promo = jfxCB_PromotionComp.getValue();
    		
    		listAnneesSansDoublons = this.retrieveAvailableAnneeByPromo(promo);
        	
        	// Adding content in combo box
        	ObservableList<String> anneeComboBoxList = FXCollections.observableArrayList(listAnneesSansDoublons);
        	jfxCB_AnneeComp.getItems().clear();
        	jfxCB_AnneeComp.getItems().addAll(anneeComboBoxList);
        	
        	// Enabling comboBox
        	jfxCB_AnneeComp.setDisable(false);
        	
    	} else {
    		jfxCB_AnneeComp.setDisable(true);
    		jfxCB_AnneeComp.getItems().clear();
    		jfxCB_AnneeComp.setValue("");
    	}
    	
    }
    
    @FXML
    private void anneeIsFilledOnBilanCompensation(ActionEvent event) {
    	
    	// If the year is selected the edition is possible, otherwise it's not
    	if (jfxCB_AnneeComp.getValue() != "") {
    		jfxb_EditionBilanCompensation.setDisable(false);
    	} else {
    		jfxb_EditionBilanCompensation.setDisable(true);
    	}
    }
    
    
    /*
     * Behavior of UI changing depending on combo box value
     * For the RÈcapitulatif 3 ans Tab
     */
    
    @FXML
    private void promoIsFilledOnRecapTroisAns(ActionEvent event) {
    	
    	// If the year is selected the edition is possible, otherwise it's not
    	if (jfxCB_PromotionRecap.getValue() != "") {
    		jfxb_EditionRecapTroisAns.setDisable(false);
    	} else {
    		jfxb_EditionRecapTroisAns.setDisable(true);
    	}
    }
    
    
    /*
     * Initialise the behavior of the tabs
     */
    
    private void initialiseBilanIndividuelTab() {
    	ArrayList<String> listPromosSansDoublons = new ArrayList<String>();

    	listPromosSansDoublons = this.retrieveAvailablePromos();

    	// Add List of PROMOTION to respective Combo box
    	ObservableList<String> promotionComboBoxList = FXCollections.observableArrayList(listPromosSansDoublons);
    	jfxCB_PromotionInd.getItems().addAll(promotionComboBoxList);

    	// Disabling Eleves combo box
    	jfxCB_EleveInd.setDisable(true);
    	jfxCB_EleveInd.setValue("");

    	// Disabling Year combo box
    	jfxCB_AnneeInd.setDisable(true);
    	jfxCB_AnneeInd.setValue("");

    	// Disabling Edition button
    	jfxb_EditionBilanIndividuel.setDisable(true);
    }
    
    private void initialiseBulletinGeneralTab() {
    	ArrayList<String> listPromosSansDoublons = new ArrayList<String>();
    	
    	listPromosSansDoublons = this.retrieveAvailablePromos();
    	
    	// Add List of PROMOTION to respective Combo box
    	ObservableList<String> promotionComboBoxList = FXCollections.observableArrayList(listPromosSansDoublons);
    	jfxCB_PromotionGen.getItems().addAll(promotionComboBoxList);
    	
    	// Disabling Year Combo box
    	jfxCB_AnneeGen.setDisable(true);
    	jfxCB_AnneeGen.setValue("");
    	
    	// Disabling edition button
    	jfxb_EditionBulletinGeneral.setDisable(true);
    }
    
    private void initialiseBilanCompensationTab() {
    	ArrayList<String> listPromosSansDoublons = new ArrayList<String>();
    	
    	listPromosSansDoublons = this.retrieveAvailablePromos();
    	
    	// Add List of PROMOTION to respective Combo box
    	ObservableList<String> promotionComboBoxList = FXCollections.observableArrayList(listPromosSansDoublons);
    	jfxCB_PromotionComp.getItems().addAll(promotionComboBoxList);
    	
    	// Disabling Year Combo box
    	jfxCB_AnneeComp.setDisable(true);
    	jfxCB_AnneeComp.setValue("");
    	
    	// Disabling edition button
    	jfxb_EditionBilanCompensation.setDisable(true);
    }
    
    private void initialiseRecapTroisAnsTab() {
    	ArrayList<String> listPromosSansDoublons = new ArrayList<String>();
    	
    	listPromosSansDoublons = this.retrieveAvailablePromos();
    	
    	// Add List of PROMOTION to respective Combo box
    	ObservableList<String> promotionComboBoxList = FXCollections.observableArrayList(listPromosSansDoublons);
    	jfxCB_PromotionRecap.getItems().addAll(promotionComboBoxList);
    	
    	// Disabling edition button
    	jfxb_EditionRecapTroisAns.setDisable(true);
    }
    
    
    
    /*
     * Common used functions to retrieve data to fill combo boxes
     */
    
    private String[] retrieveListOfFiles() {
    	
    	// Gettings infos to fill combo boxes
    	String[] listFichiers;
    	this.cheminFichier = new Extracteur("ressources\\ParametrageAccesFichier.xml").ExtracteurCheminFichierDistant("Resources");
    	File repertoire = new File(this.cheminFichier);

    	listFichiers = repertoire.list();
    	
    	return listFichiers;
    }
    
    private ArrayList<String> retrieveAvailablePromos() {
    	
    	String[] listFichiers = this.retrieveListOfFiles();
    	
    	ArrayList<String> listPromo = new ArrayList<String>();
    	ArrayList<String> listPromosSansDoublons = new ArrayList<String>();
    	
    	for (int cpt = 0; cpt < listFichiers.length; cpt++) {
    		if (listFichiers[cpt].endsWith(".csv")) {
    			if (listFichiers[cpt].length() > 11) {
    				listPromo.add(listFichiers[cpt].substring(0, 7));
    			}
    		}
    	}
    	
    	// Remove duplicates from PROMO ArrayList and sort list
    	Set<String> set = new HashSet<>();
    	set.clear();
    	set.addAll(listPromo);
    	listPromosSansDoublons.addAll(set);
    	listPromosSansDoublons.add("");			// Adding empty value to reset combo
    	Collections.sort(listPromosSansDoublons);
    	
    	return listPromosSansDoublons;
    }
    
    private ArrayList<String> retrieveAvailableAnneeByPromo(String promo) {
    	
    	ArrayList<String> listAnnee = new ArrayList<String>(); 
    	ArrayList<String> listAnneesSansDoublons = new ArrayList<String>();
    	
    	// Gettings infos to fill combo boxes
    	String[] listFichiers = this.retrieveListOfFiles();
		
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
    	
    	return listAnneesSansDoublons;
    }

}
