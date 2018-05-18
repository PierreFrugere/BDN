/*
 * Controller.java			18/05/2018
 * 3iL - Projet Bulletin de Note - 2018
 */
package application;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

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

}
