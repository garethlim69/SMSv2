package sms;

import java.io.IOException;
import javafx.fxml.FXML;

public class MainMenu {
    @FXML
    private void switchAdmin() throws IOException {
        App.setRoot("adminLogin");
    }

    @FXML
    private void switchSC() throws IOException {
        App.setRoot("scLogin");
    }

    @FXML
    private void switchRespondent() throws IOException {
        App.setRoot("respondentViewSurvey");
    }
}
