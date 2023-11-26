package sms;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class ManageSurvey extends AdminFunction implements Initializable {
    @FXML
    private Label lblSID1;
    @FXML
    private Label lblSID2;
    @FXML
    private Label lblSID3;
    @FXML
    private Label lblSID4;
    @FXML
    private Label lblSID5;

    @FXML
    private Label lblTitle1;
    @FXML
    private Label lblTitle2;
    @FXML
    private Label lblTitle3;
    @FXML
    private Label lblTitle4;
    @FXML
    private Label lblTitle5;

    @FXML
    private Label lblStatus1;
    @FXML
    private Label lblStatus2;
    @FXML
    private Label lblStatus3;
    @FXML
    private Label lblStatus4;
    @FXML
    private Label lblStatus5;

    @FXML
    private Label lblSCID1;
    @FXML
    private Label lblSCID2;
    @FXML
    private Label lblSCID3;
    @FXML
    private Label lblSCID4;
    @FXML
    private Label lblSCID5;

    @FXML
    private Label lblNoOfQ1;
    @FXML
    private Label lblNoOfQ2;
    @FXML
    private Label lblNoOfQ3;
    @FXML
    private Label lblNoOfQ4;
    @FXML
    private Label lblNoOfQ5;

    @FXML
    private Button btnView1;
    @FXML
    private Button btnView2;
    @FXML
    private Button btnView3;
    @FXML
    private Button btnView4;
    @FXML
    private Button btnView5;

    @FXML
    private Button btnBlock1;
    @FXML
    private Button btnBlock2;
    @FXML
    private Button btnBlock3;
    @FXML
    private Button btnBlock4;
    @FXML
    private Button btnBlock5;

    @FXML
    private Button btnResponses1;
    @FXML
    private Button btnResponses2;
    @FXML
    private Button btnResponses3;
    @FXML
    private Button btnResponses4;
    @FXML
    private Button btnResponses5;

    @FXML
    private Button btnDelete1;
    @FXML
    private Button btnDelete2;
    @FXML
    private Button btnDelete3;
    @FXML
    private Button btnDelete4;
    @FXML
    private Button btnDelete5;

    @FXML
    private Label lblPageNo;
    @FXML
    private Button btnNext;
    @FXML
    private Button btnPrev;

    int pageNo;

    String qnsType;

    HashMap<String, String> surveyDetailsMap = new HashMap<String, String>();

    int index;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        pageNo = 1;
        ViewSurveys(pageNo);
    }

    @FXML
    private void switchAdminDashboard() throws IOException {
        App.setRoot("adminDashboard");
    }

    public void PreviousPage() {
        pageNo--;
        btnNext.setDisable(false);
        if (pageNo == 1) {
            btnPrev.setDisable(true);
            btnNext.setDisable(false);
        }
        ViewSurveys(pageNo);
    }

    public void NextPage() {
        pageNo++;
        if (pageNo > 1) {
            btnPrev.setDisable(false);
        }
        ViewSurveys(pageNo);
    }

    @FXML @Override
    public void ViewSurveys(int pageNo) {
        // blanks all the labels and text fields
        String fileName = "src/main/java/Text Files/Surveys.txt";
        lblSID2.setText("");
        lblTitle2.setText("");
        lblStatus2.setText("");
        lblSCID2.setText("");
        lblNoOfQ2.setText("");
        btnView2.setVisible(false);
        btnBlock2.setVisible(false);
        btnResponses2.setVisible(false);
        btnDelete2.setVisible(false);

        lblSID3.setText("");
        lblTitle3.setText("");
        lblStatus3.setText("");
        lblSCID3.setText("");
        lblNoOfQ3.setText("");
        btnView3.setVisible(false);
        btnBlock3.setVisible(false);
        btnResponses3.setVisible(false);
        btnDelete3.setVisible(false);

        lblSID4.setText("");
        lblTitle4.setText("");
        lblStatus4.setText("");
        lblSCID4.setText("");
        lblNoOfQ4.setText("");
        btnView4.setVisible(false);
        btnBlock4.setVisible(false);
        btnResponses4.setVisible(false);
        btnDelete4.setVisible(false);

        lblSID5.setText("");
        lblTitle5.setText("");
        lblStatus5.setText("");
        lblSCID5.setText("");
        lblNoOfQ5.setText("");
        btnView5.setVisible(false);
        btnBlock5.setVisible(false);
        btnResponses5.setVisible(false);
        btnDelete5.setVisible(false);

        // inserts all survey details into a hashmap depending on survey ID
        List<String> listOfSurveys;
        try {
            listOfSurveys = Files.readAllLines(Paths.get(fileName));
            int noOfSurveys = listOfSurveys.size();
            int i2 = 0;
            for (int i = 0; i < listOfSurveys.size(); i++) {
                String[] e1 = listOfSurveys.get(i).split("␜");
                List<String> surveyDetails = Arrays.asList(e1);
                String[] e2 = surveyDetails.get(4).split("␝");
                List<String> questionList = Arrays.asList(e2);
                // if survey status is deleted, ommit from hashmap
                if (!surveyDetails.get(3).equals("deleted")) {
                    surveyDetailsMap.put("S" + (i2 + 1) + "SID", surveyDetails.get(0));
                    surveyDetailsMap.put("S" + (i2 + 1) + "Title", surveyDetails.get(1));
                    surveyDetailsMap.put("S" + (i2 + 1) + "SCID", surveyDetails.get(2));
                    surveyDetailsMap.put("S" + (i2 + 1) + "Status", surveyDetails.get(3));
                    surveyDetailsMap.put("S" + (i2 + 1) + "NoOfQ", String.valueOf(questionList.size() / 2));
                    i2++;
                } else {
                    noOfSurveys--;
                }
            }
            lblPageNo.setText("Page " + String.valueOf(pageNo) + "/" + (int) Math.ceil((noOfSurveys / 5.0)));
            if (pageNo * 5 >= noOfSurveys) {
                btnNext.setDisable(true);
            } else {
                noOfSurveys = 5;
            }
            if (pageNo == 1) {
                btnPrev.setDisable(true);
            }

            index = (pageNo * 5) - 4;
            // gets information from hashmap and sets it to the respective label
            lblSID1.setText(surveyDetailsMap.get("S" + index + "SID"));
            lblTitle1.setText(surveyDetailsMap.get("S" + index + "Title"));
            lblStatus1.setText(surveyDetailsMap.get("S" + index + "Status"));
            lblSCID1.setText(surveyDetailsMap.get("S" + index + "SCID"));
            lblNoOfQ1.setText(surveyDetailsMap.get("S" + index + "NoOfQ"));
            // change text of button based on current status
            switch (surveyDetailsMap.get("S" + index + "Status")) {
                case "not-approved":
                    btnBlock1.setText("Approve");
                    break;
                case "approved":
                    btnBlock1.setText("Block");
                    break;
                case "blocked":
                    btnBlock1.setText("Unblock");
                    break;
            }

            // calculates number of labels to display based on number of surveys per page
            if (noOfSurveys % 5 > 1 || noOfSurveys % 5 == 0) {
                lblSID2.setText(surveyDetailsMap.get("S" + (index + 1) + "SID"));
                lblTitle2.setText(surveyDetailsMap.get("S" + (index + 1) + "Title"));
                lblStatus2.setText(surveyDetailsMap.get("S" + (index + 1) + "Status"));
                lblSCID2.setText(surveyDetailsMap.get("S" + (index + 1) + "SCID"));
                lblNoOfQ2.setText(surveyDetailsMap.get("S" + (index + 1) + "NoOfQ"));
                btnView2.setVisible(true);
                btnBlock2.setVisible(true);
                switch (surveyDetailsMap.get("S" + (index + 1) + "Status")) {
                    case "not-approved":
                        btnBlock2.setText("Approve");
                        break;
                    case "approved":
                        btnBlock2.setText("Block");
                        break;
                    case "blocked":
                        btnBlock2.setText("Unblock");
                }
                btnResponses2.setVisible(true);
                btnDelete2.setVisible(true);
                if (noOfSurveys % 5 > 2 || noOfSurveys % 5 == 0) {
                    lblSID3.setText(surveyDetailsMap.get("S" + (index + 2) + "SID"));
                    lblTitle3.setText(surveyDetailsMap.get("S" + (index + 2) + "Title"));
                    lblStatus3.setText(surveyDetailsMap.get("S" + (index + 2) + "Status"));
                    lblSCID3.setText(surveyDetailsMap.get("S" + (index + 2) + "SCID"));
                    lblNoOfQ3.setText(surveyDetailsMap.get("S" + (index + 2) + "NoOfQ"));
                    btnView3.setVisible(true);
                    btnBlock3.setVisible(true);
                    switch (surveyDetailsMap.get("S" + (index + 2) + "Status")) {
                        case "not-approved":
                            btnBlock3.setText("Approve");
                            break;
                        case "approved":
                            btnBlock3.setText("Block");
                            break;
                        case "blocked":
                            btnBlock3.setText("Unblock");
                            break;
                    }
                    btnResponses3.setVisible(true);
                    btnDelete3.setVisible(true);
                    if (noOfSurveys % 5 > 3 || noOfSurveys % 5 == 0) {
                        lblSID4.setText(surveyDetailsMap.get("S" + (index + 3) + "SID"));
                        lblTitle4.setText(surveyDetailsMap.get("S" + (index + 3) + "Title"));
                        lblStatus4.setText(surveyDetailsMap.get("S" + (index + 3) + "Status"));
                        lblSCID4.setText(surveyDetailsMap.get("S" + (index + 3) + "SCID"));
                        lblNoOfQ4.setText(surveyDetailsMap.get("S" + (index + 3) + "NoOfQ"));
                        btnView4.setVisible(true);
                        btnBlock4.setVisible(true);
                        switch (surveyDetailsMap.get("S" + (index + 3) + "Status")) {
                            case "not-approved":
                                btnBlock4.setText("Approve");
                                break;
                            case "approved":
                                btnBlock4.setText("Block");
                                break;
                            case "blocked":
                                btnBlock4.setText("Unblock");
                                break;
                        }
                        btnResponses4.setVisible(true);
                        btnDelete4.setVisible(true);
                        if (noOfSurveys % 5 == 0) {
                            lblSID5.setText(surveyDetailsMap.get("S" + (index + 4) + "SID"));
                            lblTitle5.setText(surveyDetailsMap.get("S" + (index + 4) + "Title"));
                            lblStatus5.setText(surveyDetailsMap.get("S" + (index + 4) + "Status"));
                            lblSCID5.setText(surveyDetailsMap.get("S" + (index + 4) + "SCID"));
                            lblNoOfQ5.setText(surveyDetailsMap.get("S" + (index + 4) + "NoOfQ"));
                            btnView5.setVisible(true);
                            btnBlock5.setVisible(true);
                            switch (surveyDetailsMap.get("S" + (index + 4) + "Status")) {
                                case "not-approved":
                                    btnBlock5.setText("Approve");
                                    break;
                                case "approved":
                                    btnBlock5.setText("Block");
                                    break;
                                case "blocked":
                                    btnBlock5.setText("Unblock");
                                    break;
                            }
                            btnResponses5.setVisible(true);
                            btnDelete5.setVisible(true);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }
    }

    public static void ChangeStatus(String surveyID, String status) {
        String fileName = "src/main/java/Text Files/Surveys.txt";

        // inserts all surveys into listOfSurveys
        List<String> listOfSurveys;
        try {
            listOfSurveys = Files.readAllLines(Paths.get(fileName));
            for (int i = 0; i < listOfSurveys.size(); i++) {
                String[] e1 = listOfSurveys.get(i).split("␜");
                List<String> surveyDetails = Arrays.asList(e1);
                if (surveyID.equals(surveyDetails.get(0))) {
                    String newRecord = "";
                    for (int i2 = 0; i2 < surveyDetails.size(); i2++) {
                        // inserts new status into the fourth element
                        if (i2 == 3) {
                            newRecord = newRecord + status;
                        } else {
                            newRecord = newRecord + surveyDetails.get(i2);
                        }
                        if (i2 != (surveyDetails.size() - 1)) {
                            newRecord = newRecord + "␜";
                        }
                    }
                    UpdateFile(fileName, i, newRecord);
                }
            }
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }

    }

    @FXML
    public static void ViewQuestions(String surveyID, int qNo) {
        String fileName = "src/main/java/Text Files/Surveys.txt";

        // inserts all surveys into listOfSurveys
        List<String> listOfSurveys;
        try {
            listOfSurveys = Files.readAllLines(Paths.get(fileName));
            for (int i = 0; i < listOfSurveys.size(); i++) {
                String[] e1 = listOfSurveys.get(i).split("␜");
                List<String> surveyDetails = Arrays.asList(e1);
                if (surveyID.equals(surveyDetails.get(0))) {
                    String[] e2 = surveyDetails.get(4).split("␝");
                    List<String> questionList = Arrays.asList(e2);
                    // switches to viewQuestionsChoice page if there is more than one question
                    if (qNo >= 1) {
                        App.setRoot("viewQuestionsChoice");
                    }
                    try {
                        String[] e3 = questionList.get(((qNo - 1) * 2) + 1).split("␞");
                        List<String> questionDetails = Arrays.asList(e3);
                        for (int i2 = 1; i2 < questionDetails.size(); i2++) {
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }
    }

    public static void UpdateFile(String fileName, int lineNumber, String newRecord) throws IOException {
        // inserts all surveys into listOfSurveys
        List<String> listOfSurveys;
        // if line number exceeds that of the text file, add new line behind
        // if not add to line lineNumber
        try {
            listOfSurveys = Files.readAllLines(Paths.get(fileName));
            if (lineNumber >= listOfSurveys.size()) {
                listOfSurveys.add(newRecord);
            } else {
                listOfSurveys.set(lineNumber, newRecord);
            }
            File file = new File(fileName);
            FileWriter fileWritter = new FileWriter(file, false);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            // write each element of listOfSurveys as its own line into the text file
            for (int i = 0; i < listOfSurveys.size(); i++) {
                if (i != 0) {
                    bufferWritter.write("\n");
                }
                bufferWritter.write(listOfSurveys.get(i));
            }
            bufferWritter.close();
            fileWritter.close();
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }
    }

    // calls ViewQuestions functions with SID of the respective records
    @FXML
    private void ViewButton1() {
        ViewQuestionsChoice.SurveyID(surveyDetailsMap.get("S" + index + "SID"));
        ViewQuestionsChoice.prevPage("manageSurvey");
        ViewQuestions(surveyDetailsMap.get("S" + index + "SID"), 1);
    }

    @FXML
    private void ViewButton2() {
        ViewQuestionsChoice.SurveyID(surveyDetailsMap.get("S" + (index + 1) + "SID"));
        ViewQuestionsChoice.prevPage("manageSurvey");
        ViewQuestions(surveyDetailsMap.get("S" + (index + 1) + "SID"), 1);
    }

    @FXML
    private void ViewButton3() {
        ViewQuestionsChoice.SurveyID(surveyDetailsMap.get("S" + (index + 2) + "SID"));
        ViewQuestionsChoice.prevPage("manageSurvey");
        ViewQuestions(surveyDetailsMap.get("S" + (index + 2) + "SID"), 1);
    }

    @FXML
    private void ViewButton4() {
        ViewQuestionsChoice.SurveyID(surveyDetailsMap.get("S" + (index + 3) + "SID"));
        ViewQuestionsChoice.prevPage("manageSurvey");
        ViewQuestions(surveyDetailsMap.get("S" + (index + 3) + "SID"), 1);
    }

    @FXML
    private void ViewButton5() {
        ViewQuestionsChoice.SurveyID(surveyDetailsMap.get("S" + (index + 4) + "SID"));
        ViewQuestionsChoice.prevPage("manageSurvey");
        ViewQuestions(surveyDetailsMap.get("S" + (index + 4) + "SID"), 1);
    }

    // changes status of respective survey based on current status
    @FXML
    private void StatusButton1() {
        String newStatus = "";
        switch (btnBlock1.getText()) {
            case "Approve":
                newStatus = "approved";
                break;
            case "Block":
                newStatus = "blocked";
                break;
            case "Unblock":
                newStatus = "approved";
                break;
        }
        ChangeStatus(surveyDetailsMap.get("S" + index + "SID"), newStatus);
        JOptionPane.showMessageDialog(null, "Status Updated Successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        initialize(null, null);
    }

    @FXML
    private void StatusButton2() {
        String newStatus = "";
        switch (btnBlock2.getText()) {
            case "Approve":
                newStatus = "approved";
                break;
            case "Block":
                newStatus = "blocked";
                break;
            case "Unblock":
                newStatus = "approved";
                break;
        }
        ChangeStatus(surveyDetailsMap.get("S" + (index + 1) + "SID"), newStatus);
        JOptionPane.showMessageDialog(null, "Status Updated Successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        initialize(null, null);
    }

    @FXML
    private void StatusButton3() {
        String newStatus = "";
        switch (btnBlock3.getText()) {
            case "Approve":
                newStatus = "approved";
                break;
            case "Block":
                newStatus = "blocked";
                break;
            case "Unblock":
                newStatus = "approved";
                break;
        }
        ChangeStatus(surveyDetailsMap.get("S" + (index + 2) + "SID"), newStatus);
        JOptionPane.showMessageDialog(null, "Status Updated Successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        initialize(null, null);
    }

    @FXML
    private void StatusButton4() {
        String newStatus = "";
        switch (btnBlock4.getText()) {
            case "Approve":
                newStatus = "approved";
                break;
            case "Block":
                newStatus = "blocked";
                break;
            case "Unblock":
                newStatus = "approved";
                break;
        }
        ChangeStatus(surveyDetailsMap.get("S" + (index + 3) + "SID"), newStatus);
        JOptionPane.showMessageDialog(null, "Status Updated Successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        initialize(null, null);
    }

    @FXML
    private void StatusButton5() {
        String newStatus = "";
        switch (btnBlock5.getText()) {
            case "Approve":
                newStatus = "approved";
                break;
            case "Block":
                newStatus = "blocked";
                break;
            case "Unblock":
                newStatus = "approved";
                break;
        }
        ChangeStatus(surveyDetailsMap.get("S" + (index + 4) + "SID"), newStatus);
        JOptionPane.showMessageDialog(null, "Status Updated Successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        initialize(null, null);
    }

    // view responses of respective surveys
    @FXML
    private void ResponsesButton1() throws IOException {
        ViewResponses.SurveyID(surveyDetailsMap.get("S" + index + "SID"));
        ViewResponses.prevPage("manageSurvey");
        App.setRoot("viewResponsesText");
    }

    @FXML
    private void ResponsesButton2() throws IOException {
        ViewResponses.SurveyID(surveyDetailsMap.get("S" + (index + 1) + "SID"));
        ViewResponses.prevPage("manageSurvey");
        App.setRoot("viewResponsesText");
    }

    @FXML
    private void ResponsesButton3() throws IOException {
        ViewResponses.SurveyID(surveyDetailsMap.get("S" + (index + 2) + "SID"));
        ViewResponses.prevPage("manageSurvey");
        App.setRoot("viewResponsesText");
    }

    @FXML
    private void ResponsesButton4() throws IOException {
        ViewResponses.SurveyID(surveyDetailsMap.get("S" + (index + 3) + "SID"));
        ViewResponses.prevPage("manageSurvey");
        App.setRoot("viewResponsesText");
    }

    @FXML
    private void ResponsesButton5() throws IOException {
        ViewResponses.SurveyID(surveyDetailsMap.get("S" + (index + 4) + "SID"));
        ViewResponses.prevPage("manageSurvey");
        App.setRoot("viewResponsesText");
    }

    // deletes the respective survey
    @FXML
    private void DeleteButton1() {
        int input = JOptionPane.showConfirmDialog(null,
                "Delete Survey " + surveyDetailsMap.get("S" + index + "SID") + "?", "Discard Changes?",
                JOptionPane.YES_NO_OPTION);
        if (input == 0) {
            ChangeStatus(surveyDetailsMap.get("S" + index + "SID"), "deleted");
            initialize(null, null);
        }
    }

    @FXML
    private void DeleteButton2() {
        int input = JOptionPane.showConfirmDialog(null,
                "Delete Survey " + surveyDetailsMap.get("S" + (index + 1) + "SID") + "?", "Discard Changes?",
                JOptionPane.YES_NO_OPTION);
        if (input == 0) {
            ChangeStatus(surveyDetailsMap.get("S" + (index + 1) + "SID"), "deleted");
            initialize(null, null);
        }
    }

    @FXML
    private void DeleteButton3() {
        int input = JOptionPane.showConfirmDialog(null,
                "Delete Survey " + surveyDetailsMap.get("S" + (index + 2) + "SID") + "?", "Discard Changes?",
                JOptionPane.YES_NO_OPTION);
        if (input == 0) {
            ChangeStatus(surveyDetailsMap.get("S" + (index + 2) + "SID"), "deleted");
            initialize(null, null);
        }
    }

    @FXML
    private void DeleteButton4() {
        int input = JOptionPane.showConfirmDialog(null,
                "Delete Survey " + surveyDetailsMap.get("S" + (index + 3) + "SID") + "?", "Discard Changes?",
                JOptionPane.YES_NO_OPTION);
        if (input == 0) {
            ChangeStatus(surveyDetailsMap.get("S" + (index + 3) + "SID"), "deleted");
            initialize(null, null);
        }
    }

    @FXML
    private void DeleteButton5() {
        int input = JOptionPane.showConfirmDialog(null,
                "Delete Survey " + surveyDetailsMap.get("S" + (index + 4) + "SID") + "?", "Discard Changes?",
                JOptionPane.YES_NO_OPTION);
        if (input == 0) {
            ChangeStatus(surveyDetailsMap.get("S" + (index + 4) + "SID"), "deleted");
            initialize(null, null);
        }
    }

    @Override
    void AdminEditProfile() {
        throw new UnsupportedOperationException("Function Not In Use");
    }

    @Override
    void ViewSCProfile(int pageNo) {
        throw new UnsupportedOperationException("Function Not In Use");
    }

    @Override
    void DeleteSCProfile() {
        throw new UnsupportedOperationException("Function Not In Use");
    }

    @Override
    void RegisterAdmin() {
        throw new UnsupportedOperationException("Function Not In Use");
    }
}
