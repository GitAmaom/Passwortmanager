package de.passwordmanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * Starts the main application by loading the Layout view.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass()
                .getResource("/de/passwordmanager/resources/fxml/Layout.fxml"));
        Scene mainScene = new Scene(root);
        primaryStage.setTitle("Passwortmanager");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    /**
     * Main method that launches the application.
     * @param args the arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
