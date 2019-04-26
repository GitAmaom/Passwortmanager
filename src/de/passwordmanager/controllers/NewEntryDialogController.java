package de.passwordmanager.controllers;

import java.util.List;

import de.passwordmanager.entities.GeneratorSetting;
import de.passwordmanager.entities.TableEntry;
import de.passwordmanager.helpers.PasswordHelper;
import de.passwordmanager.services.ApplicationService;
import de.passwordmanager.services.TableService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;

/**
 * Controller class of the "New Entry Dialog".
 * 
 * @author Moritz Schneider
 *
 */
public class NewEntryDialogController {

    /**
     * TextField for the title of the table entry.
     */
    @FXML
    private TextField titleTextField;
    
    /**
     * TextField for the username of the table entry.
     */
    @FXML
    private TextField usernameTextField;
    
    /**
     * Visible TextField for the password of the table entry.
     */
    @FXML
    private TextField passwordVisibleTextField;
    
    /**
     * Invisible TextField for the password of the table entry.
     */
    @FXML
    private PasswordField passwordHiddenPasswordField;
    
    /**
     * PasswordField for repeating the password of the table entry.
     */
    @FXML
    private PasswordField repeatPasswordField;
    
    /**
     * TextField for the url of the table entry.
     */
    @FXML
    private TextField urlTextField;
    
    /**
     * TextField for the e-mail of the table entry.
     */
    @FXML
    private TextField emailTextField;
    
    /**
     * TextField for the notes of the table entry.
     */
    @FXML
    private TextArea notesTextArea;
    
    /**
     * MenuButton for generating a password.
     */
    @FXML
    private MenuButton generatePasswordMenuButton;
    
    /**
     * Button for confirming the creation or editing of a table entry.
     */
    @FXML
    private Button confirmButton;
    
    /**
     * Button for canceling the creation or editing of a table entry.
     */
    @FXML
    private Button cancelButton;
    
    /**
     * Button for hiding the password.
     */
    @FXML
    private Button hidePasswordButton;
    
    /**
     * Button for showing the password.
     */
    @FXML
    private Button showPasswordButton;

    /**
     * TableService for interacting with the table.
     */
    private TableService tableservice;
    
    /**
     * Selected TableEntry which gets edited.
     */
    private TableEntry selectedEntry;
    
    /**
     * ApplicationService for spawning other windows.
     */
    private ApplicationService applications;

    // TODO: Move initialize to reset

    /**
     * Initializes the Controller.
     */
    public void initialize() {
        reset();
        this.passwordVisibleTextField.textProperty().bindBidirectional(
                this.passwordHiddenPasswordField.textProperty());
        this.showPasswordButton.visibleProperty()
                .bind(this.hidePasswordButton.visibleProperty().not());
        this.showPasswordButton.managedProperty()
                .bind(this.hidePasswordButton.managedProperty().not());
        this.passwordVisibleTextField.visibleProperty()
                .bind(this.hidePasswordButton.visibleProperty());
        this.passwordVisibleTextField.managedProperty()
                .bind(this.hidePasswordButton.managedProperty());
        this.passwordHiddenPasswordField.visibleProperty()
                .bind(this.showPasswordButton.visibleProperty());
        this.passwordHiddenPasswordField.managedProperty()
                .bind(this.showPasswordButton.managedProperty());
        this.repeatPasswordField.disableProperty()
                .bind(this.hidePasswordButton.visibleProperty());
        this.repeatPasswordField.disableProperty()
                .addListener((o, oldVal, newVal) -> {
                    if (newVal) {
                        this.repeatPasswordField.setText("");
                        this.passwordVisibleTextField.requestFocus();
                    } else {
                        this.repeatPasswordField.setText(
                                this.passwordVisibleTextField.getText());
                        this.passwordHiddenPasswordField.requestFocus();
                    }
                });
        this.generatePasswordMenuButton.getStyleClass().remove("menu-button");
        this.generatePasswordMenuButton.getStyleClass().add("button");

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                updateMenuButton();
            }
        });
    }

    /**
     * Resets the view to default state. Gets called every time the window gets opened.
     */
    public void reset() {
        this.hidePasswordButton.setVisible(false);
        this.hidePasswordButton.setManaged(false);
        this.titleTextField.setText("");
        this.usernameTextField.setText("");
        this.passwordVisibleTextField.setText("");
        this.repeatPasswordField.setText("");
        this.emailTextField.setText("");
        this.urlTextField.setText("");
        this.notesTextArea.setText("");
        this.selectedEntry = null;
        this.fillFields();
        this.titleTextField.requestFocus();
    }

    /**
     * Sets the selected TableEntry which will be edited.
     * @param toEdit the TableEntry which will be edited.
     */
    public void setSelectedEntry(TableEntry toEdit) {
        this.selectedEntry = toEdit;
        fillFields();
    }

    /**
     * Injects the ApplicationService.
     * @param applications the ApplicationService.
     */
    public void injectApplicationService(ApplicationService applications) {
        this.applications = applications;
    }

    /**
     * Gets the tableservice.
     * @return the tableservice.
     */
    public TableService getTableService() {
        return tableservice;
    }

    /**
     * Sets the tableservice.
     * @param tableservice the tableservice.
     */
    public void setTableservice(TableService tableservice) {
        this.tableservice = tableservice;
    }

    /**
     * Switches the visibility of the TextField containing the password.
     */
    @FXML
    private void passwordSwitch() {
        if (this.hidePasswordButton.isVisible() == false) {
            this.hidePasswordButton.setVisible(true);
            this.hidePasswordButton.setManaged(true);
        } else {
            this.hidePasswordButton.setVisible(false);
            this.hidePasswordButton.setManaged(false);
        }
    }

    /**
     * Confirms the changes made to the table and closes the window.
     */
    @FXML
    private void confirmButtonClick() {
        if (doPasswordsMatch()) {
            if (selectedEntry == null) {
                this.tableservice.addEntry(this.titleTextField.getText(),
                        this.usernameTextField.getText(),
                        this.passwordVisibleTextField.getText(),
                        this.urlTextField.getText(),
                        this.emailTextField.getText(),
                        this.notesTextArea.getText());
            } else {
                changeSelectedEntry();
            }
        }
        this.confirmButton.getScene().getWindow().hide();
    }

    /**
     * Closes the window without confirming the changes made to the table.
     */
    @FXML
    private void cancelButtonClick() {
        this.cancelButton.getScene().getWindow().hide();
    }

    /**
     * Shows the password generator and sets the password if a password was generated.
     */
    @FXML
    private void profileButtonClick() {
        String generatedPassword = this.applications.showPasswordgeneratorDialog();
        if(!generatedPassword.isEmpty()) {
            this.passwordVisibleTextField.setText(generatedPassword);
            if (!this.repeatPasswordField.isDisabled()) {
                this.repeatPasswordField.setText(generatedPassword);
            }
        }
        this.updateMenuButton();
    }

    /**
     * Fills the fields if a TableEntry is selected in the table.
     */
    private void fillFields() {
        if (this.selectedEntry != null) {
            this.titleTextField.setText(this.selectedEntry.getTitle());
            this.usernameTextField.setText(this.selectedEntry.getUsername());
            this.passwordVisibleTextField
                    .setText(this.selectedEntry.getPassword());
            this.repeatPasswordField.setText(this.selectedEntry.getPassword());
            this.urlTextField.setText(this.selectedEntry.getUrl());
            this.emailTextField.setText(this.selectedEntry.getEmail());
            this.notesTextArea.setText(this.selectedEntry.getNotes());
        }
    }

    /**
     * Changes the selected TableEntry.
     */
    private void changeSelectedEntry() {
        this.selectedEntry.setTitle(this.titleTextField.getText());
        this.selectedEntry.setUsername(this.usernameTextField.getText());
        this.selectedEntry.setPassword(this.passwordVisibleTextField.getText());
        this.selectedEntry.setUrl(this.urlTextField.getText());
        this.selectedEntry.setEmail(this.emailTextField.getText());
        this.selectedEntry.setNotes(this.notesTextArea.getText());
    }

    /**
     * Updates the MenuButton to the password generator profiles.
     */
    private void updateMenuButton() {
        this.generatePasswordMenuButton.getItems().clear();
        MenuItem openProfiles = new MenuItem("Passwortgenerator öffnen...");
        openProfiles.setOnAction(e -> {
            this.profileButtonClick();
        });
        this.generatePasswordMenuButton.getItems().add(openProfiles);
        SeparatorMenuItem seperator = new SeparatorMenuItem();
        this.generatePasswordMenuButton.getItems().add(seperator);
        List<GeneratorSetting> settings = this.applications
                .getPasswordgeneratorController().getProfileService()
                .getProfiles();
        for (GeneratorSetting setting : settings) {
            MenuItem item = new MenuItem(setting.getName());
            item.setOnAction(e -> menuItemClick(setting));
            this.generatePasswordMenuButton.getItems().add(item);
        }
    }

    /**
     * Generates a password for a given setting.
     * @param setting the password generatorsetting.
     */
    private void menuItemClick(GeneratorSetting setting) {
        String generatedPassword = PasswordHelper.generatePassword(setting);
        this.passwordVisibleTextField.setText(generatedPassword);
        if (this.repeatPasswordField.isDisabled() == false) {
            this.repeatPasswordField.setText(generatedPassword);
        }
    }


    /**
     * Checks if the input passwords match and shows an alert if not.
     * @return true if the passwords match, else false.
     */
    private boolean doPasswordsMatch() {
        if (this.repeatPasswordField.isDisabled() == false) {
            if (this.passwordVisibleTextField.getText()
                    .equals(this.repeatPasswordField.getText()) == false) {
                this.applications.showPasswordsDontMatchAlert();
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

}
