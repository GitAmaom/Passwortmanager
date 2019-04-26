package de.passwordmanager.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controller class of the "Enter Password Dialog".
 * 
 * @author Moritz Schneider
 *
 */
public class EnterPasswordDialogController {

    /**
     * Textfield for displaying the password when set it is shown.
     */
    @FXML
    private TextField visiblePasswordTextField;
    
    /**
     * Textfield for displaying the password when set it is not shown.
     */
    @FXML
    private PasswordField invisiblePasswordPasswordField;
    
    /**
     * CheckBox for selecting if the password should be shown or not.
     */
    @FXML
    private CheckBox showPasswordCheckBox;
    
    /**
     * Button for confirming the input password.
     */
    @FXML
    private Button confirmButton;

    /**
     * Initializes the Controller.
     */
    public void initialize() {
        this.visiblePasswordTextField.visibleProperty()
                .bind(this.showPasswordCheckBox.selectedProperty());
        this.visiblePasswordTextField.managedProperty()
                .bind(this.showPasswordCheckBox.selectedProperty());
        this.invisiblePasswordPasswordField.visibleProperty()
                .bind(this.showPasswordCheckBox.selectedProperty().not());
        this.invisiblePasswordPasswordField.managedProperty()
                .bind(this.showPasswordCheckBox.selectedProperty().not());

        this.visiblePasswordTextField.textProperty().bindBidirectional(
                this.invisiblePasswordPasswordField.textProperty());
        this.visiblePasswordTextField.setText("");
        this.confirmButton.requestFocus();
    }

    /**
     * Gets the password.
     * @return the password.
     */
    public String getPassword() {
        return this.visiblePasswordTextField.getText();
    }

    /**
     * Hides the stage if a password is input or if not, displays an Alert.
     */
    @FXML
    private void confirmButtonClick() {
        if (this.getPassword().equals("")) {
            Alert a = new Alert(AlertType.WARNING);
            a.setTitle("Passwort eingeben");
            a.setHeaderText(null);
            a.setContentText("Bitte vergeben Sie ein Passwort");
            a.getDialogPane().getStylesheets()
                    .add(getClass().getResource(
                            "/de/passwordmanager/resources/css/darkmode.css")
                            .toString());
            a.initModality(Modality.APPLICATION_MODAL);
            a.show();
        } else {
            this.confirmButton.getScene().getWindow().hide();
        }
    }

}
