/*
 * Controller.java			18/05/2018
 * 3iL - Projet Bulletin de Note - 2018
 */
package application;

import java.io.File;
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
    private JFXComboBox<String> jbxCB_Promotion;		// Promotion Combo box
    
    @FXML // fx:id="jbxCB_Annee"
    private JFXComboBox<String> jbxCB_Annee;			// Annee Combo box
    
    @FXML // fx:id="jbxCB_Eleve"
    private JFXComboBox<String> jbxCB_Eleve;			// Eleve Combo box
    
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
	
    
	@FXML
	private void initialize() {
		// Initialise selectionModel value during instanciation
		this.selectionModel = tp_principal.getSelectionModel();
		
		// Gettings infos to fill combo boxes
		String[] listFichiers;
    	String cheminFichier = new Extracteur("ressources\\ParametrageAccesFichier.xml").ExtracteurCheminFichierDistant("Resources");
    	File repertoire = new File(cheminFichier);
    	
    	listFichiers = repertoire.list();
    	
    	ArrayList<String> listPromo = new ArrayList<String>();
    	ArrayList<String> listAnnee = new ArrayList<String>();    
    	ArrayList<String> listPromosSansDoublons = new ArrayList<String>();
    	ArrayList<String> listAnneesSansDoublons = new ArrayList<String>();
    	
    	for (int cpt = 0; cpt < listFichiers.length; cpt++) {
    		if (listFichiers[cpt].endsWith(".csv")) {
    			if (listFichiers[cpt].length() > 11) {
    				listAnnee.add(listFichiers[cpt].substring(7, 11));
    				listPromo.add(listFichiers[cpt].substring(0, 7));
    			}
    		}
    	}
    	
    	// Remove duplicates from YEARS ArrayList ans sort list
    	Set<String> set = new HashSet<>();
    	set.addAll(listAnnee);
    	listAnneesSansDoublons.addAll(set);
    	Collections.sort(listAnneesSansDoublons);
    	
    	// Remove duplicates from PROMO ArrayList and sort list
    	set.clear();
    	set.addAll(listPromo);
    	listPromosSansDoublons.addAll(set);
    	Collections.sort(listPromosSansDoublons);
    	
    	for (String elm: listAnneesSansDoublons) {
    		System.out.println("Liste Ann�es: " + elm);
    	}
    	
    	for (String elm: listPromosSansDoublons) {
    		System.out.println("Liste Promos: " + elm);
    	}
    	
    	// Add List of YEAR and PROMOTION to respective Combo box
    	ObservableList<String> promotionComboBoxList = FXCollections.observableArrayList(listPromosSansDoublons);
    	jbxCB_Promotion = new JFXComboBox<String>(promotionComboBoxList);
    	
    	ObservableList<String> anneeComboBoxList = FXCollections.observableArrayList(listAnneesSansDoublons);
    	jbxCB_Annee = new JFXComboBox<String>(anneeComboBoxList);
    	
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
    	    	
    }

}
