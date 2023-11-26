package sms;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class SCDasboard implements Initializable {

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
    private Label lblMSG;
    @FXML
    private Button btnNext;
    @FXML
    private Button btnPrev;

    int pageNo;

    static String scUsername;
    static String SCID;

    HashMap<String, String> surveyDetailsMap = new HashMap<String, String>();

    int index;
    @FXML
    private Label lblUsername;

    @FXML
    private void switchSCEditProfile() throws IOException {
        SCEditProfile.uniqueKey(scUsername);
        App.setRoot("scEditProfile");
    }

    public void PreviousPage() {
        btnNext.setDisable(false);
        pageNo--;
        if (pageNo == 1) {
            btnPrev.setDisable(true);
            btnNext.setDisable(false);
        }
        ViewSurveys(pageNo, SCID);
    }

    public void NextPage() {
        pageNo++;
        if (pageNo > 1) {
            btnPrev.setDisable(false);
        }
        ViewSurveys(pageNo, SCID);
    }

    @FXML
    public void ViewSurveys(int pageNo, String SCID) {
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
                if (surveyDetails.get(2).equals(SCID) && !surveyDetails.get(3).equals("deleted")) {
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

            // System.out.println(listOfSurveys.size() % 5);
            if (noOfSurveys != 0) {
                lblMSG.setVisible(false);
                lblSID1.setText(surveyDetailsMap.get("S" + index + "SID"));
                lblTitle1.setText(surveyDetailsMap.get("S" + index + "Title"));
                lblStatus1.setText(surveyDetailsMap.get("S" + index + "Status"));
                lblSCID1.setText(surveyDetailsMap.get("S" + index + "SCID"));
                lblNoOfQ1.setText(surveyDetailsMap.get("S" + index + "NoOfQ"));
                switch (surveyDetailsMap.get("S" + index + "Status")) {
                    case "not-approved":
                        btnBlock1.setText("Block");
                        btnBlock1.setDisable(true);
                        break;
                    case "approved":
                        btnBlock1.setText("Block");
                        break;
                    case "blocked":
                        btnBlock1.setText("Unblock");
                        break;
                }

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
                            btnBlock2.setText("Block");
                            btnBlock2.setDisable(true);
                            break;
                        case "approved":
                            btnBlock2.setText("Block");
                            break;
                        case "blocked":
                            btnBlock2.setText("Unblock");
                            break;
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
                                btnBlock3.setText("Block");
                                btnBlock3.setDisable(true);
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
                                    btnBlock4.setText("Block");
                                    btnBlock4.setDisable(true);
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
                                        btnBlock5.setText("Block");
                                        btnBlock5.setDisable(true);
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
            } else {
                lblPageNo.setText("Page 1/1");
                lblSID1.setText("");
                lblTitle1.setText("");
                lblStatus1.setText("");
                lblSCID1.setText("");
                lblNoOfQ1.setText("");
                btnView1.setVisible(false);
                btnBlock1.setVisible(false);
                btnResponses1.setVisible(false);
                btnDelete1.setVisible(false);
            }

        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }
    }

    public static void ChangeStatus(String surveyID, String status) {
        String fileName = "src/main/java/Text Files/Surveys.txt";

        List<String> listOfSurveys;
        try {
            listOfSurveys = Files.readAllLines(Paths.get(fileName));
            for (int i = 0; i < listOfSurveys.size(); i++) {
                String[] e1 = listOfSurveys.get(i).split("␜");
                List<String> surveyDetails = Arrays.asList(e1);
                if (surveyID.equals(surveyDetails.get(0))) {
                    // surveyDetails.set(3, status);
                    String newRecord = "";
                    for (int i2 = 0; i2 < surveyDetails.size(); i2++) {
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

    public void CreateSurvey() {

        var surveyTitle = JOptionPane.showInputDialog("Enter Survey Title");

        if (surveyTitle != null && !surveyTitle.isEmpty()) {
            String fileName = "src/main/java/Text Files/Surveys.txt";
            List<String> listOfSurveys;
            int index = 1;

            File file = new File(fileName);
            if (file.length() == 0) {
                index = 1;
            } else {
                try {
                    listOfSurveys = Files.readAllLines(Paths.get(fileName));
                    String[] e1 = listOfSurveys.get(listOfSurveys.size() - 1).split("␜");
                    List<String> surveyDetails = Arrays.asList(e1);
                    index = Integer.valueOf(surveyDetails.get(0).substring(1));
                    index++;
                } catch (FileNotFoundException e1) {
                    System.out.println("File Not Found");
                    e1.printStackTrace();
                } catch (IOException e1) {
                    System.out.println("IO Exception");
                    e1.printStackTrace();
                }

            }

            String createdBy = SCID;
            String status = "not-approved";
            String questionDetails = " ";

            try {
                listOfSurveys = Files.readAllLines(Paths.get(fileName));
                String newRecord = "S" + index + "␜" + surveyTitle + "␜" + createdBy + "␜" + status + "␜"
                        + questionDetails;
                System.out.println(newRecord);
                UpdateFile(fileName, listOfSurveys.size() + 1, newRecord);

                // FileWriter fileWritter = new FileWriter(file,true);
                // BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
                // bufferWritter.write(newRecord);
                // bufferWritter.close();
                // fileWritter.close();
            } catch (IOException e) {
                System.out.println("IO Exception");
                e.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, "Survey Created");
            initialize(null, null);
        } else {
            JOptionPane.showMessageDialog(null, "Survey Not Created");
        }

    }

    public static void UpdateFile(String fileName, int lineNumber, String newRecord) throws IOException {
        List<String> listOfSurveys;
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
            for (int i = 0; i < listOfSurveys.size(); i++) {
                if (i != 0) {
                    bufferWritter.write("\n");
                }
                bufferWritter.write(listOfSurveys.get(i));
            }
            bufferWritter.close();
            fileWritter.close();
            System.out.println("Updated Successfully");
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }
    }

    public static String encryptPassword(String password) {
        // String password = "myPassword";
        String encryptedPassword = null;
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");

            m.update(password.getBytes());

            byte[] bytes = m.digest();

            StringBuilder s = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            encryptedPassword = s.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return encryptedPassword;
    }

    @FXML
    private void switchMainMenu() throws IOException {
        App.setRoot("mainMenu");
    }

    public static void uniqueKey(String username) {
        scUsername = username;
    }

    public static void SCID(String scID) {
        SCID = scID;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        lblUsername.setText(scUsername);
        pageNo = 1;
        ViewSurveys(pageNo, SCID);
    }

    @FXML
    public static void ViewQuestions(String surveyID, int qNo) {
        String fileName = "src/main/java/Text Files/Surveys.txt";

        List<String> listOfSurveys;
        try {
            listOfSurveys = Files.readAllLines(Paths.get(fileName));
            for (int i = 0; i < listOfSurveys.size(); i++) {
                String[] e1 = listOfSurveys.get(i).split("␜");
                List<String> surveyDetails = Arrays.asList(e1);
                if (surveyID.equals(surveyDetails.get(0))) {
                    String[] e2 = surveyDetails.get(4).split("␝");
                    List<String> questionList = Arrays.asList(e2);
                    // System.out.println("Question " + qNo);
                    // System.out.println("Quetion Type: " + questionList.get((qNo - 1) * 2));
                    if (qNo >= 1) {
                        App.setRoot("viewQuestionsChoice");
                    }
                    try {
                        String[] e3 = questionList.get(((qNo - 1) * 2) + 1).split("␞");
                        List<String> questionDetails = Arrays.asList(e3);
                        // System.out.println("Question: " + questionDetails.get(0));
                        for (int i2 = 1; i2 < questionDetails.size(); i2++) {
                            // System.out.println("Answer: " + i2 + ": " + questionDetails.get(i2));
                        }
                    } catch (Exception e) {
                        System.out.println("no questions");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }
    }

    public static void AddQuestion(String surveyID) {
        String fileName = "src/Text Files/Surveys.txt";

        // build the question and answers into a single string
        // qNo = 0 means question 1, qNo = 1 means question 2 etc...
        int qNo = 0;
        String questionType = "MCQ";
        String question = "How many days in a a year?";

        List<String> mcqAnswers = new ArrayList<>();
        mcqAnswers.add("123");
        mcqAnswers.add("456");
        mcqAnswers.add("789");
        // mcqAnswers.add("32");

        String polarAnswer1 = "Yes";
        String polarAnswer2 = "No";

        String singleQuestion = questionType + "␝" + question;
        switch (questionType) {
            case "MCQ":
                for (int i = 0; i < mcqAnswers.size(); i++) {
                    singleQuestion = singleQuestion + "␞" + mcqAnswers.get(i);
                }
                break;
            case "Polar":
                singleQuestion = singleQuestion + "␞" + polarAnswer1 + "␞" + polarAnswer2;
                break;
            default:
                break;
        }

        // inserts new question into surveydetails array
        List<String> listOfSurveys;
        try {
            listOfSurveys = Files.readAllLines(Paths.get(fileName));
            for (int i = 0; i < listOfSurveys.size(); i++) {
                String[] e1 = listOfSurveys.get(i).split("␜");
                List<String> surveyDetails = Arrays.asList(e1);
                if (surveyID.equals(surveyDetails.get(0))) {
                    String[] e2 = surveyDetails.get(4).split("␝");
                    List<String> questionList = Arrays.asList(e2);
                    String fullQuestions = "";
                    if (questionList.size() == 1) {
                        fullQuestions = singleQuestion;
                    } else {
                        for (int i2 = 0; i2 < questionList.size(); i2++) {
                            if (i2 == (qNo * 2)) {
                                fullQuestions = fullQuestions + singleQuestion + "␝";
                            }
                            fullQuestions = fullQuestions + questionList.get(i2);
                            if (i2 != (questionList.size() - 1)) {
                                fullQuestions = fullQuestions + "␝";
                            }
                        }
                        if ((qNo * 2) >= questionList.size()) {
                            fullQuestions = fullQuestions + "␝" + singleQuestion;
                        }
                    }

                    System.out.println(fullQuestions);
                    surveyDetails.set(4, fullQuestions);
                    String newRecord = "";
                    for (int i2 = 0; i2 < surveyDetails.size(); i2++) {
                        newRecord = newRecord + surveyDetails.get(i2);
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

    // REPLACE SOUT WITH VIEW FUNCTION
    @FXML
    private void ViewButton1() {
        ViewQuestionsChoice.SurveyID(surveyDetailsMap.get("S" + index + "SID"));
        ViewQuestionsChoice.prevPage("scDashboard");
        ViewQuestions(surveyDetailsMap.get("S" + index + "SID"), 1);
    }

    @FXML
    private void ViewButton2() {
        ViewQuestionsChoice.SurveyID(surveyDetailsMap.get("S" + (index + 1) + "SID"));
        ViewQuestionsChoice.prevPage("scDashboard");
        ViewQuestions(surveyDetailsMap.get("S" + (index + 1) + "SID"), 1);
    }

    @FXML
    private void ViewButton3() {
        ViewQuestionsChoice.SurveyID(surveyDetailsMap.get("S" + (index + 2) + "SID"));
        ViewQuestionsChoice.prevPage("scDashboard");
        ViewQuestions(surveyDetailsMap.get("S" + (index + 2) + "SID"), 1);
    }

    @FXML
    private void ViewButton4() {
        ViewQuestionsChoice.SurveyID(surveyDetailsMap.get("S" + (index + 3) + "SID"));
        ViewQuestionsChoice.prevPage("scDashboard");
        ViewQuestions(surveyDetailsMap.get("S" + (index + 3) + "SID"), 1);
    }

    @FXML
    private void ViewButton5() {
        ViewQuestionsChoice.SurveyID(surveyDetailsMap.get("S" + (index + 4) + "SID"));
        ViewQuestionsChoice.prevPage("scDashboard");
        ViewQuestions(surveyDetailsMap.get("S" + (index + 4) + "SID"), 1);
    }

    @FXML
    private void StatusButton1() {
        String newStatus = "";
        switch (btnBlock1.getText()) {
            case "Block":
                newStatus = "blocked";
                break;
            case "Unblock":
                newStatus = "approved";
                break;
        }
        ChangeStatus(surveyDetailsMap.get("S" + index + "SID"), newStatus);
        JOptionPane.showMessageDialog (null, "Status Updated Successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        initialize(null, null);
    }

    @FXML
    private void StatusButton2() {
        String newStatus = "";
        switch (btnBlock2.getText()) {
            case "Block":
                newStatus = "blocked";
                break;
            case "Unblock":
                newStatus = "approved";
                break;
        }
        ChangeStatus(surveyDetailsMap.get("S" + (index + 1) + "SID"), newStatus);
        JOptionPane.showMessageDialog (null, "Status Updated Successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        initialize(null, null);
    }

    @FXML
    private void StatusButton3() {
        String newStatus = "";
        switch (btnBlock3.getText()) {
            case "Block":
                newStatus = "blocked";
                break;
            case "Unblock":
                newStatus = "approved";
                break;
        }
        ChangeStatus(surveyDetailsMap.get("S" + (index + 2) + "SID"), newStatus);
        JOptionPane.showMessageDialog (null, "Status Updated Successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        initialize(null, null);
    }

    @FXML
    private void StatusButton4() {
        String newStatus = "";
        switch (btnBlock4.getText()) {
            case "Block":
                newStatus = "blocked";
                break;
            case "Unblock":
                newStatus = "approved";
                break;
        }
        ChangeStatus(surveyDetailsMap.get("S" + (index + 3) + "SID"), newStatus);
        JOptionPane.showMessageDialog (null, "Status Updated Successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        initialize(null, null);
    }

    @FXML
    private void StatusButton5() {
        String newStatus = "";
        switch (btnBlock5.getText()) {
            case "Block":
                newStatus = "blocked";
                break;
            case "Unblock":
                newStatus = "approved";
                break;
        }
        ChangeStatus(surveyDetailsMap.get("S" + (index + 4) + "SID"), newStatus);
        JOptionPane.showMessageDialog (null, "Status Updated Successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        initialize(null, null);
    }

    @FXML
    private void ResponsesButton1() throws IOException {
        ViewResponses.SurveyID(surveyDetailsMap.get("S" + index + "SID"));
        ViewResponses.prevPage("scDashboard");
        App.setRoot("viewResponsesText");
    }

    @FXML
    private void ResponsesButton2() throws IOException {
        ViewResponses.SurveyID(surveyDetailsMap.get("S" + (index + 1) + "SID"));
        ViewResponses.prevPage("scDashboard");
        App.setRoot("viewResponsesText");
    }

    @FXML
    private void ResponsesButton3() throws IOException {
        ViewResponses.SurveyID(surveyDetailsMap.get("S" + (index + 2) + "SID"));
        ViewResponses.prevPage("scDashboard");
        App.setRoot("viewResponsesText");
    }

    @FXML
    private void ResponsesButton4() throws IOException {
        ViewResponses.SurveyID(surveyDetailsMap.get("S" + (index + 3) + "SID"));
        ViewResponses.prevPage("scDashboard");
        App.setRoot("viewResponsesText");
    }

    @FXML
    private void ResponsesButton5() throws IOException {
        ViewResponses.SurveyID(surveyDetailsMap.get("S" + (index + 4) + "SID"));
        ViewResponses.prevPage("scDashboard");
        App.setRoot("viewResponsesText");
    }

    @FXML
    private void DeleteButton1() {
        int input = JOptionPane.showConfirmDialog(null, "Delete Survey " + surveyDetailsMap.get("S" + index + "SID") + "?", "Discard Changes?", JOptionPane.YES_NO_OPTION);
        if (input == 0){
            ChangeStatus(surveyDetailsMap.get("S" + index + "SID"), "deleted");
            initialize(null, null);
        }
    }

    @FXML
    private void DeleteButton2() {
        int input = JOptionPane.showConfirmDialog(null, "Delete Survey " + surveyDetailsMap.get("S" + (index + 1) + "SID") + "?", "Discard Changes?", JOptionPane.YES_NO_OPTION);
        if (input == 0){
            ChangeStatus(surveyDetailsMap.get("S" + (index + 1) + "SID"), "deleted");
            initialize(null, null);
        }
    }

    @FXML
    private void DeleteButton3() {
        int input = JOptionPane.showConfirmDialog(null, "Delete Survey " + surveyDetailsMap.get("S" + (index + 2) + "SID") + "?", "Discard Changes?", JOptionPane.YES_NO_OPTION);
        if (input == 0){
            ChangeStatus(surveyDetailsMap.get("S" + (index + 2) + "SID"), "deleted");
            initialize(null, null);
        }
    }

    @FXML
    private void DeleteButton4() {
        int input = JOptionPane.showConfirmDialog(null, "Delete Survey " + surveyDetailsMap.get("S" + (index + 3) + "SID") + "?", "Discard Changes?", JOptionPane.YES_NO_OPTION);
        if (input == 0){
            ChangeStatus(surveyDetailsMap.get("S" + (index + 3) + "SID"), "deleted");
            initialize(null, null);
        }
    }

    @FXML
    private void DeleteButton5() {
        int input = JOptionPane.showConfirmDialog(null, "Delete Survey " + surveyDetailsMap.get("S" + (index + 4) + "SID") + "?", "Discard Changes?", JOptionPane.YES_NO_OPTION);
        if (input == 0){
            ChangeStatus(surveyDetailsMap.get("S" + (index + 4) + "SID"), "deleted");
            initialize(null, null);
        }
    }
}
