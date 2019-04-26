package de.passwordmanager.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.concurrent.ExecutionException;

import de.passwordmanager.helpers.IOHelper;
import de.passwordmanager.helpers.PasswordHelper;
import de.passwordmanager.services.ApplicationService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;

/**
 * Controller class of the "Passwordchecker".
 * 
 * @author Moritz Schneider
 *
 */
public class PasswordcheckerController {

    /**
     * TextField for entering the password that gets checked.
     */
    @FXML
    private TextField passwordTextField;

    /**
     * Label displaying the strength of the input password.
     */
    @FXML
    private Label strengthLabel;

    /**
     * Label displaying the number entropy bits of the input passwords.
     */
    @FXML
    private Label bitsLabel;

    /**
     * Label displaying if the input password is contained in a dictionary.
     */
    @FXML
    private Label dictionaryLabel;

    /**
     * Label displaying how much time a brute force attack would need to crack the input password.
     */
    @FXML
    private Label timeBruteforceLabel;

    /**
     * Button for showing details.
     */
    @FXML
    private Button detailButton;

    /**
     * Button for closing the window.
     */
    @FXML
    private Button closeButton;
    
    /**
     * ProgressBar displaying how much of the dictionary is loaded.
     */
    @FXML
    private ProgressBar loadProgressBar;
    
    /**
     * Label displaying if the loading is finished.
     */
    @FXML
    private Label loadLabel;
    
    /**
     * Task for loading the dictionary.
     */
    private Task<HashSet<String>> loadTask;
    
    /**
     * ApplicationService for spawning windows.
     */
    private ApplicationService applications;

    /**
     * HashSet containing the dictionary.
     */
    private HashSet<String> dictionary; 
    
    /**
     * Initializes the controller.
     */
    public void initialize() {
        this.passwordTextField.setDisable(true);
        this.detailButton.setDisable(true);
        reset();
        initTask();
        this.loadProgressBar.progressProperty().bind(loadTask.progressProperty());
    }
    
    /**
     * Resets the controller to default state.
     */
    public void reset() {
        this.passwordTextField.setText("");
        this.passwordTextField.requestFocus();
    }
    
    /**
     * Loads the dictionary data if it hasn't been loaded before.
     */
    public void loadData() {
        if(this.loadTask.isCancelled()) {
            initTask();
        }
        if(this.dictionary == null || this.dictionary.isEmpty()) {
            try {
                Thread thread = new Thread(loadTask);
                thread.start();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    } 

    /**
     * Injects the ApplicationService.
     * @param applications the ApplicationService.
     */
    public void injectApplicationService(ApplicationService applications) {
        this.applications = applications;
    }
    
    /**
     * Closes the window and stops the loading task, if running.
     */
    @FXML
    private void closeButtonClick() {
        if(this.loadTask.isRunning()) {
            this.loadTask.cancel();
        }
        this.closeButton.getScene().getWindow().hide();
    }

    /**
     * Shows details about the rating of the input password.
     */
    @FXML
    private void detailButtonClick() {
        this.applications.showPasswordDetails();
    }
    
    /**
     * Updates the rating labels.
     */
    private void updateLabels() {
        this.updateBitsLabel();
        this.updateStrengthLabel();
        this.updateDictionaryLabel();
        this.updateTimeBruteforceLabel();
    }

    /**
     * Updates the Label displaying the bruteforce time.
     */
    private void updateTimeBruteforceLabel() {
        String password = this.passwordTextField.getText();
        if (dictionary.contains(password)) {
            this.timeBruteforceLabel.setText("Sofort");
        } else {
            BigDecimal seconds = PasswordHelper.calculateCrackTime(password, 100000000);
            String text = PasswordHelper.convertSecondsToPrintableTime(seconds);
            this.timeBruteforceLabel.setText(text);
        }
    }

    /**
     * Updates the Label displaying if the input password is in a dictionary.
     */
    private void updateDictionaryLabel() {

        /*if (this.dictionary.isEmpty()) {
            long startTime = System.nanoTime();
            File file;
            try {
                file = IOHelper.readInternalFile("1-million-pass.txt"); //Laden der Datei als Background Task
                this.dictionary = IOHelper.fileToHashSet(file, 14000000, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            long endTime = System.nanoTime();
            System.out.printf("Laden der Liste (ms): %s%n", (endTime - startTime)/1000000);
        }*/
        String password = this.passwordTextField.getText();
        if (this.dictionary.contains(password)) {
            this.dictionaryLabel.setText("Ja");
        } else {
            this.dictionaryLabel.setText("Nein");
        }
    }

    /**
     * Updates the Label displaying the input password strength.
     */
    private void updateStrengthLabel() {
        String password = this.passwordTextField.getText();
        if (this.dictionary.contains(password)) {
            String feedback = "Kompromittiert";
            this.strengthLabel.setText(feedback);
        } else {
            String feedback = PasswordHelper.getPasswordStrength(password);
            this.strengthLabel.setText(feedback);
        }
    }

    /**
     * Updates the Label displaying the entropy bits.
     */
    private void updateBitsLabel() {
        int bits = PasswordHelper.calculateBitLength(passwordTextField.getText());
        this.bitsLabel.setText(Integer.toString(bits));
    }
    
    /**
     * Initializes the task loading the dictionary.
     */
    private void initTask() {
        InputStream stream = null;
        try {
            stream = IOHelper.readInternalFile("1-million-pass.txt");
            this.loadTask = IOHelper.fileToHashSetTask(stream, 14000000);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        loadTask.setOnSucceeded(e -> {try {
            this.dictionary = loadTask.get();
            this.passwordTextField.textProperty().addListener(i -> updateLabels());
            this.passwordTextField.setDisable(false);
            this.detailButton.setDisable(false);
            this.loadLabel.setText("Fertig");
        } catch (InterruptedException | ExecutionException e1) {
            e1.printStackTrace();
        }});
        loadTask.setOnCancelled(e -> {
            this.dictionary = null;
        });
    }

}
