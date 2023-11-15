package sms;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class AdminDashboard implements Initializable {
    static String adminUsername;
    @FXML private Label lblUsername;
    @FXML
    private void switchRegisterNewAdmin() throws IOException {
        App.setRoot("registerNewAdmin");
    }

    @FXML
    private void switchManageSC() throws IOException {
        App.setRoot("manageSCProfiles");
    }

    @FXML
    private void switchManageSurvey() throws IOException {
        App.setRoot("manageSurvey");
    }

    @FXML
    private void switchAdminEditProfile() throws IOException {
        AdminEditProfile.uniqueKey(adminUsername);
        App.setRoot("adminEditProfile");
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        lblUsername.setText(adminUsername);
    }

    @FXML
    private void switchMainMenu() throws IOException {
        App.setRoot("mainMenu");
    }

    public static void uniqueKey(String username){
        adminUsername = username;
    }
}
