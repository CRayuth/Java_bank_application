package bank.application.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Bank;
import utility.AlertUtil;

import com.jfoenix.controls.JFXButton;

import java.io.File;

public class UserProfileController implements Initializable {

    // Main profile screen elements
    @FXML private AnchorPane profileMainPane;
    @FXML private ImageView profileImageView;
    @FXML private Label userFullName;
    @FXML private Label userEmail;
    @FXML private Label userPhone;
    
    // Navigation buttons
    @FXML private JFXButton backButton;
    @FXML private JFXButton myProfileButton;
    @FXML private JFXButton securityButton;
    @FXML private JFXButton developerButton;
    @FXML private JFXButton editImageButton;
    @FXML private JFXButton updateProfileButton;
    @FXML private JFXButton deleteProfileButton;
    
    // Sub-panels
    @FXML private AnchorPane myProfilePane;
    @FXML private AnchorPane securityPane;
    @FXML private AnchorPane developerPane;
    
    private Scene scene;
    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadUserData();
        setupButtonAnimations();
    }
    
    /**
     * Load user data from the logged-in user and display it
     */
    private void loadUserData() {
        Bank loggedInUser = Bank.getLoggedInUser();
        
        if (loggedInUser != null) {
            // Set user full name
            String fullName = loggedInUser.getFirstname() + " " + loggedInUser.getLastname();
            if (userFullName != null) {
                userFullName.setText(fullName);
            }
            
            // Set user email
            if (userEmail != null) {
                userEmail.setText(loggedInUser.getEmail() != null ? loggedInUser.getEmail() : "No email provided");
            }
            
            // Set user phone
            if (userPhone != null) {
                userPhone.setText(loggedInUser.getPhonenumber() != null ? loggedInUser.getPhonenumber() : "No phone provided");
            }
            
            // Set profile image
            setProfileImage(loggedInUser.getIcon());
        } else {
            // Fallback data for demo mode
            if (userFullName != null) userFullName.setText("Demo User");
            if (userEmail != null) userEmail.setText("demo@yuthdommy.com");
            if (userPhone != null) userPhone.setText("demo");
            setProfileImage("/images/user-profile.png");
        }
    }
    
    /**
     * Set up button hover animations for Apple.com-style interactions
     */
    private void setupButtonAnimations() {
        // Add subtle hover effects to main navigation buttons
        addHoverEffect(myProfileButton);
        addHoverEffect(securityButton);
        addHoverEffect(developerButton);
        addHoverEffect(updateProfileButton);
        addHoverEffect(deleteProfileButton);
    }
    
    /**
     * Add Apple.com-style hover effect to buttons
     */
    private void addHoverEffect(JFXButton button) {
        if (button != null) {
            button.setOnMouseEntered(e -> {
                button.setStyle(button.getStyle() + " -fx-scale-x: 1.02; -fx-scale-y: 1.02;");
            });
            
            button.setOnMouseExited(e -> {
                button.setStyle(button.getStyle().replace(" -fx-scale-x: 1.02; -fx-scale-y: 1.02;", ""));
            });
        }
    }
    
    /**
     * Set the profile image
     */
    private void setProfileImage(String imagePath) {
        if (profileImageView != null) {
            try {
                if (imagePath == null || imagePath.isEmpty()) {
                    imagePath = "/images/user-profile.png"; // Fallback image
                }
                
                Image image = new Image(getClass().getResourceAsStream(imagePath));
                profileImageView.setImage(image);
                
                // Make the image circular
                profileImageView.setStyle("-fx-background-radius: 50; -fx-border-radius: 50;");
            } catch (Exception e) {
                System.err.println("Error loading profile image: " + e.getMessage());
                // Load fallback image
                try {
                    Image fallbackImage = new Image(getClass().getResourceAsStream("/images/user-profile.png"));
                    profileImageView.setImage(fallbackImage);
                } catch (Exception ex) {
                    System.err.println("Error loading fallback image: " + ex.getMessage());
                }
            }
        }
    }
    
    /**
     * Navigate back to main screen
     */
    @FXML
    private void backToMain(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainScreen.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtil.showAlert("Error", "Failed to navigate back to main screen.");
        }
    }
    
    /**
     * Show My Profile details
     */
    @FXML
    private void goToMyProfile(ActionEvent event) {
        showPane(myProfilePane);
    }
    
    /**
     * Show Security settings
     */
    @FXML
    private void goToSecurity(ActionEvent event) {
        showPane(securityPane);
    }
    
    /**
     * Show Developer information
     */
    @FXML
    private void goToDeveloper(ActionEvent event) {
        showPane(developerPane);
    }
    
    /**
     * Go back to main profile view
     */
    @FXML
    private void backToProfileMain(ActionEvent event) {
        showPane(profileMainPane);
    }
    
    /**
     * Helper method to show specific pane and hide others
     */
    private void showPane(AnchorPane paneToShow) {
        // Hide all panes
        profileMainPane.setVisible(false);
        myProfilePane.setVisible(false);
        securityPane.setVisible(false);
        developerPane.setVisible(false);
        
        // Show the requested pane
        paneToShow.setVisible(true);
    }
    
    /**
     * Edit profile image - allows user to select new image
     */
    @FXML
    private void editProfileImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Image");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        
        if (selectedFile != null) {
            try {
                // Load the new image
                Image newImage = new Image(selectedFile.toURI().toString());
                profileImageView.setImage(newImage);
                
                // Update the user's profile image path (in a real app, you'd save this to database)
                Bank loggedInUser = Bank.getLoggedInUser();
                if (loggedInUser != null) {
                    loggedInUser.setIcon(selectedFile.getAbsolutePath());
                }
                
                AlertUtil.showAlert("Success", "Profile image updated successfully!");
                
            } catch (Exception e) {
                AlertUtil.showAlert("Error", "Failed to load the selected image. Please try again.");
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Update user profile information
     */
    @FXML
    private void updateProfile(ActionEvent event) {
        // In a real implementation, this would open an edit form
        AlertUtil.showAlert("Update Profile", "Profile update functionality will be implemented here.\n\nThis would typically open a form to edit:\n• Personal information\n• Contact details\n• Preferences");
    }
    
    /**
     * Delete user account
     */
    @FXML
    private void deleteProfile(ActionEvent event) {
        // Show confirmation dialog (simplified for demo)
        AlertUtil.showAlert("Delete Account", "Account deletion is a serious action.\n\nIn a real implementation, this would:\n• Show a confirmation dialog\n• Require password verification\n• Permanently delete user data\n• Redirect to login screen");
    }
}