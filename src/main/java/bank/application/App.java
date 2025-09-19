package bank.application;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            // Verify resources exist before loading
            URL fxmlResource = getClass().getResource("/fxml/Start.fxml");
            URL iconResource = getClass().getResource("/images/bankIcon.png");
            
            if (fxmlResource == null) {
                showErrorAndExit("FXML file not found: /fxml/Start.fxml\nMake sure resources are copied to target/classes");
                return;
            }
            
            if (iconResource == null) {
                System.out.println("Warning: Bank icon not found, using default icon");
            }
            
            // Load FXML
            Parent root = FXMLLoader.load(fxmlResource);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            
            // Set icon if available
            if (iconResource != null) {
                stage.getIcons().add(new Image(iconResource.toExternalForm()));
            }
            
            // Configure stage
            stage.setResizable(false);
            stage.setTitle("YuthDommy Bank");
            stage.show();
            
            System.out.println("YuthDommy Bank started successfully!");
            System.out.println("Running in Demo Mode - Use phone='demo', password='demo' to login");
            
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAndExit("Failed to start application: " + e.getMessage());
        }
    }
    
    private void showErrorAndExit(String message) {
        try {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Application Error");
            alert.setHeaderText("Failed to start YuthDommy Bank");
            alert.setContentText(message);
            alert.showAndWait();
        } catch (Exception e) {
            // If JavaFX is not properly initialized, just print to console
            System.err.println("ERROR: " + message);
        }
        System.exit(1);
    }

    public static void main(String[] args) {
        System.out.println("Starting YuthDommy Bank...");
        System.out.println("JavaFX Version: " + System.getProperty("javafx.version"));
        System.out.println("Java Version: " + System.getProperty("java.version"));
        launch(args);
    }
}

