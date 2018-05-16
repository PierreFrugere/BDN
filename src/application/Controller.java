package application;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

public class Controller {

    @FXML //  fx:id="tp_principal"
    private TabPane tp_principal; // Value injected by FXMLLoader

    @FXML //  fx:id="jfxb_bilanCompensation"
    private JFXButton jfxb_bilanCompensation; // Value injected by FXMLLoader


    @FXML
    private void handleBilanCompensationAction(ActionEvent event) {
     System.out.println("Test");
    }

}
