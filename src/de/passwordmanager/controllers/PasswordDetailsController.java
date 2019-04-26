package de.passwordmanager.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Node;

/**
 * Controller class of the "Password Details".
 * 
 * @author Moritz Schneider
 *
 */
public class PasswordDetailsController {

    @FXML
    private Button closeButton;

    @FXML
    private void closeButtonClick(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).hide();;
    }
}