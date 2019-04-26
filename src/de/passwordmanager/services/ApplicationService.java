package de.passwordmanager.services;

import java.io.File;
import java.io.IOException;

import de.passwordmanager.controllers.EnterPasswordDialogController;
import de.passwordmanager.controllers.LayoutController;
import de.passwordmanager.controllers.NewEntryDialogController;
import de.passwordmanager.controllers.PasswordDetailsController;
import de.passwordmanager.controllers.PasswordcheckerController;
import de.passwordmanager.controllers.PasswordgeneratorController;
import de.passwordmanager.entities.GeneratorSetting;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 * Class for spawning windows within the application.
 * @author Moritz Schneider
 *
 */
public class ApplicationService {

    /**
     * Controller of the layout view.
     */
    private LayoutController layoutController;
    
    /**
     * Controller of the new entry dialog.
     */
    private NewEntryDialogController newEntryDialogController;
    
    /**
     * Controller of the passwordgenerator.
     */
    private PasswordgeneratorController passwordgeneratorController;
    
    /**
     * Controller of the enter password dialog.
     */
    private EnterPasswordDialogController enterPasswordDialogController;
    
    /**
     * Controller of  the passwordchecker.
     */
    private PasswordcheckerController passwordcheckerController;
    
    /**
     * Controller of the password details view.
     */
    private PasswordDetailsController passwordDetailsController;

    /**
     * The layout.
     */
    private Parent layout;
    
    /**
     * The new entry dialog.
     */
    private Parent newEntryDialog;
    
    /**
     * The passwordgenerator.
     */
    private Parent passwordgenerator;
    
   /**
    * The enter password dialog.
    */
    private Parent enterPasswordDialog;
    
    /**
     * The passwordsettings view.
     */
    
    private Parent passwordsettings;
    /**
     * The passwordchecker.
     */
    private Parent passwordchecker;
    
    /**
     * The password details view.
     */
    private Parent passwordDetails;

    /**
     * Class constructor without injecting the layout.
     */
    public ApplicationService() {
        initDialogs();
    }

    /**
     * Class constructor with injecting the layout.
     * @param layoutController the controller of the layout.
     * @param layout the layout as a Parent.
     */
    public ApplicationService(LayoutController layoutController, Parent layout) {
        this.layoutController = layoutController;
        this.layout = layout;
        initDialogs();
    }

    /**
     * Initializes all the windows that could get spawned during the runtime of the application.
     */
    private void initDialogs() {
        final FXMLLoader newEntryDialogLoader = new FXMLLoader(
                getClass().getResource("/de/passwordmanager/resources/fxml/NewEntryDialog.fxml"));
        final FXMLLoader passwordgeneratorLoader = new FXMLLoader(getClass()
                .getResource("/de/passwordmanager/resources/fxml/Passwordgenerator.fxml"));
        final FXMLLoader enterPasswordDialogLoader = new FXMLLoader(getClass()
                .getResource("/de/passwordmanager/resources/fxml/EnterPasswordDialog.fxml"));
        final FXMLLoader passwordsettingsLoader = new FXMLLoader(
                getClass().getResource("/de/passwordmanager/resources/fxml/Passwordsettings.fxml"));
        final FXMLLoader passwordcheckerLoader = new FXMLLoader(
                getClass().getResource("/de/passwordmanager/resources/fxml/Passwordchecker.fxml"));
        final FXMLLoader passwordDetailsLoader = new FXMLLoader(
                getClass().getResource("/de/passwordmanager/resources/fxml/PasswordDetails.fxml"));

        try {
            this.newEntryDialog = newEntryDialogLoader.load();
            this.passwordgenerator = passwordgeneratorLoader.load();
            this.enterPasswordDialog = enterPasswordDialogLoader.load();
            this.passwordsettings = passwordsettingsLoader.load();
            this.passwordchecker = passwordcheckerLoader.load();
            this.passwordDetails = passwordDetailsLoader.load();

            this.newEntryDialogController = newEntryDialogLoader.getController();
            this.passwordgeneratorController = passwordgeneratorLoader.getController();
            this.enterPasswordDialogController = enterPasswordDialogLoader.getController();
            this.passwordcheckerController = passwordcheckerLoader.getController();
            this.passwordDetailsController = passwordDetailsLoader.getController();

            this.passwordgeneratorController.injectApplicationService(this);
            this.newEntryDialogController.injectApplicationService(this);
            this.passwordcheckerController.injectApplicationService(this);

            //this.passwordgeneratorController.injectStage(this.passwordgenerator);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the new entry dialog.
     */
    public void showNewEntryDialog() {
        this.newEntryDialogController.reset();
        if (this.newEntryDialogController.getTableService() == null) {
            this.newEntryDialogController.setTableservice(this.layoutController.getTableservice());
        }
        showDialog(newEntryDialog, "Neuer Eintrag");
    }

    /**
     * Shows the new entry dialog in 'edit mode'.
     */
    public void showEditEntryDialog() {
        this.newEntryDialogController.reset();
        this.newEntryDialogController
                .setSelectedEntry(this.layoutController.getSelectedTableEntry());
        showDialog(newEntryDialog, "Eintrag bearbeiten");
    }

    /**
     * Shows the passwordgenerator.
     */
    public void showPasswordgenerator() {
        this.passwordgeneratorController.initialize();
        this.passwordgeneratorController.enableButtons(false);
        showDialog(passwordgenerator, "Passwortgenerator");
    }

    /**
     * Shows the passwordgenerator and returns generated password.
     * @return generated password.
     */
    public String showPasswordgeneratorDialog() {
        this.passwordgeneratorController.initialize();
        this.passwordgeneratorController.enableButtons(true);
        showDialog(passwordgenerator, "Passwortgenerator");
        return this.passwordgeneratorController.getPassword();
    }

    /**
     * Shows the enter password dialog and returns the input text.
     * @return the text the user inputs.
     */
    public String showEnterPasswordDialog() {
        this.enterPasswordDialogController.initialize();
        showDialog(enterPasswordDialog, "Passwort eingeben");
        return this.enterPasswordDialogController.getPassword();
    }

    /**
     * Shows the passwordchecker.
     */
    public void showPasswordChecker() {
        this.passwordcheckerController.reset();
        this.passwordcheckerController.loadData();
        showDialog(passwordchecker, "Passwortüberprüfung");
    }

    /**
     * Shows the password details view.
     */
    public void showPasswordDetails() {
        showDialog(passwordDetails, "Passwort Details");
    }

    /**
     * Shows an alert displaying that now entry has been selected.
     */
    public void showNoEntrySelectedAlert() {
        Alert alert = getAlert(AlertType.ERROR, null, "Eintrag auswählen",
                "Es wurde kein Eintrag ausgewählt. Bitte wählen Sie zuerst einen Eintrag in der Tabelle aus!");
        alert.showAndWait();
    }

    /**
     * Shows an alert displaying that the input passwords don't match.
     */
    public void showPasswordsDontMatchAlert() {
        Alert alert = getAlert(AlertType.ERROR, null, "Passwörter stimmen nicht überein",
                "Die eingegebenen Passwörter stimmen nicht überein.");
        alert.show();
    }

    /**
     * Shows an alert displaying that the input password is wrong.
     */
    public void showPasswordWrongAlert() {
        Alert alert = getAlert(AlertType.ERROR, null, "Falsches Passwort",
                "Das eingegebene Passwort ist nicht richtig.");
        alert.show();
    }
    
    /**
     * Gets a FileChooser that filters '.sesam' files.
     * @return a FileChooser.
     */
    public FileChooser getFileChooser() {
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter(
                "SESAM Passwordmanager Files", "*.sesam");
        chooser.setInitialDirectory(new File(System.getProperty("user.home")));
        chooser.getExtensionFilters().add(ext);
        return chooser;
    }

    /**
     * Gets the passwordgeneratorController.
     * @return the passwordgeneratorController.
     */
    public PasswordgeneratorController getPasswordgeneratorController() {
        return passwordgeneratorController;
    }

    /**
     * Shows an non-resizable, modal window.
     * @param root the Parent object.
     * @param windowName the title of the window.
     */
    private void showDialog(Parent root, String windowName) {
        if (root.getScene() == null) {
            Scene dialogScene = new Scene(root);
        }
        Stage dialogStage = new Stage();
        dialogStage.setTitle(windowName);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setResizable(false);
        dialogStage.setScene(root.getScene());
        dialogStage.showAndWait();
    }

    /**
     * Gets an alert with the specified text.
     * @param type the type of the alert.
     * @param headerText the text of the header.
     * @param title the title of the alert.
     * @param content context text of the alert.
     * @return an alert.
     */
    private Alert getAlert(AlertType type, String headerText, String title, String content) {
        Alert alert = new Alert(type);
        alert.setHeaderText(headerText);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.getDialogPane().getStylesheets().add(getClass()
                .getResource("/de/passwordmanager/resources/css/darkmode.css").toString());
        alert.initModality(Modality.APPLICATION_MODAL);
        return alert;
    }
}
