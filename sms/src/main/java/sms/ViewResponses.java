package sms;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class ViewResponses implements Initializable {
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblID;
    @FXML
    private Label lblQuestionType;
    @FXML
    private Label lblQuestion;
    @FXML
    private TextArea txtAnswer;
    @FXML
    private Label lblPage;
    @FXML
    private Label lblQuestionNo;
    @FXML
    private Label lblAnswerPage;
    @FXML
    private Button btnNextAnswer;
    @FXML
    private Button btnPrevAnswer;
    @FXML
    private Button btnNext;
    @FXML
    private Button btnPrev;

    static String surveyID;
    static String prevPage;
    static int qNo;
    static int answerNo;
    static int noOfResponses = 0;

    public static void prevPage(String previousPage) {
        prevPage = previousPage;
    }

    public static void SurveyID(String SurveyID) {
        surveyID = SurveyID;
    }

    public void PreviousQuestion() {
        qNo--;
        if (qNo == 1) {
            btnPrev.setDisable(true);
            btnNext.setDisable(false);
        }
        if (qNo == getNoOfQs(surveyID)) {
            btnNext.setDisable(true);
        } else {
            btnNext.setDisable(false);
        }
        ViewResponseDetails(surveyID, qNo, answerNo);
    }

    public void NextQuestion() {
        qNo++;
        if (qNo > 1) {
            btnPrev.setDisable(false);
        }
        if (qNo == getNoOfQs(surveyID)) {
            btnNext.setDisable(true);
        } else {
            btnNext.setDisable(false);
        }
        ViewResponseDetails(surveyID, qNo, answerNo);
    }
    public void NextAnswer() {
        answerNo++;
        if (answerNo > 1) {
            btnPrevAnswer.setDisable(false);
        }
        if (answerNo == noOfResponses) {
            btnNextAnswer.setDisable(true);
        } else {
            btnNextAnswer.setDisable(false);
        }
        ViewResponseDetails(surveyID, qNo, answerNo);
    }

    public void PreviousAnswer() {
        answerNo--;
        if (answerNo == 1) {
            btnPrevAnswer.setDisable(true);
            btnNextAnswer.setDisable(false);
        }
        if (answerNo == noOfResponses) {
            btnNextAnswer.setDisable(true);
        } else {
            btnNextAnswer.setDisable(false);
        }
        ViewResponseDetails(surveyID, qNo, answerNo);
    }

    public static int getNoOfQs(String surveyID) {
        String fileName = "src/main/java/Text Files/Surveys.txt";
        int NoOfQ = 0;

        List<String> listOfSurveys;
        try {
            listOfSurveys = Files.readAllLines(Paths.get(fileName));
            for (int i = 0; i < listOfSurveys.size(); i++) {
                String[] e1 = listOfSurveys.get(i).split("␜");
                List<String> surveyDetails = Arrays.asList(e1);
                if (surveyID.equals(surveyDetails.get(0))) {
                    String[] e2 = surveyDetails.get(4).split("␝");
                    List<String> questionList = Arrays.asList(e2);
                    NoOfQ = questionList.size() / 2;
                }
            }
        } catch (IOException e) {
            System.out.println("IOException");
        }
        return NoOfQ;
    }

    public void ViewResponseDetails(String surveyID, int qNo, int answerNo){
        txtAnswer.setText("");
        btnPrevAnswer.setVisible(false);
        btnNextAnswer.setVisible(false);
        lblAnswerPage.setVisible(false);
        txtAnswer.setEditable(false);

        lblPage.setText("Page " + String.valueOf(qNo) + "/" + String.valueOf(getNoOfQs(surveyID)));
        List<String> individualResponses = new ArrayList<String>();
        List<String> responseDetails = new ArrayList<String>();

        if (qNo >= getNoOfQs(surveyID)) {
            btnNext.setDisable(true);
        }
        if (qNo == 1) {
            btnPrev.setDisable(true);
        }

        List<String> allResponses;
        try {
            allResponses = Files.readAllLines(Paths.get("src/main/java/Text Files/Responses.txt"));
            for ( int i = 0; i < allResponses.size(); i++){
                if (surveyID.equals(allResponses.get(i).substring(0, 2))){
                    individualResponses.add(allResponses.get(i));
                }
            }
            noOfResponses = individualResponses.size();
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }

        HashMap<String, Integer> mcqMap = new HashMap<String, Integer>();
        HashMap<String, String> openEndedMap = new HashMap<String, String>();

        
        List<String> listOfSurveys;
        try {
            listOfSurveys = Files.readAllLines(Paths.get("src/main/java/Text Files/Surveys.txt"));
            for (int i = 0; i < listOfSurveys.size(); i++){
                String[] e1 = listOfSurveys.get(i).split("␜");
                List<String> surveyDetails = Arrays.asList(e1);
                if (surveyID.equals(surveyDetails.get(0))){
                    lblID.setText("ID: " + surveyID);
                    lblTitle.setText("Title: " + surveyDetails.get(1));
                    String[] e2 = surveyDetails.get(4).split("␝");
                    List<String> questionList = Arrays.asList(e2);
                    for (int i3 = 0; i3 < questionList.size(); i3++){
                        if (i3 % 2 == 0){
                            lblQuestionType.setText("Question Type: " + questionList.get((qNo - 1) * 2));
                        } else {
                            //question array if qNo is 1, ans is 1, qNo is 2, ans is 3, qNo is 3, ans is 5
                            //formula for question array is qNo - 1 * 2 + 1
                            String[] e3 = questionList.get(((qNo - 1) * 2) + 1).split("␞");
                            List<String> questionDetails = Arrays.asList(e3);
                            lblQuestionNo.setText("Question " + qNo);
                            lblQuestion.setText(questionDetails.get(0));
                            int a1Count = 0;
                            int a2Count = 0;
                            int a3Count = 0;
                            int a4Count = 0;
                            for (int i4 = 0; i4 < individualResponses.size(); i4++){
                                String[] e4 = individualResponses.get(i4).split("␜");
                                responseDetails = Arrays.asList(e4);
                                switch (responseDetails.get(qNo)) {
                                    case "1":
                                        a1Count++;
                                        break;
                                    case "2":
                                        a2Count++;
                                        break;
                                    case "3":
                                        a3Count++;
                                        break;
                                    case "4":
                                        a4Count++;
                                        break;
                                    default:
                                        openEndedMap.put("Q" + qNo + "A" + i4, responseDetails.get(qNo));
                                        break;
                                }
                            }
                            mcqMap.put("Q" + qNo + "A1", a1Count);
                            mcqMap.put("Q" + qNo + "A2", a2Count);
                            mcqMap.put("Q" + qNo + "A3", a3Count);
                            mcqMap.put("Q" + qNo + "A4", a4Count);
                            if (noOfResponses != 0) {
                               if (questionList.get((qNo - 1) * 2).equals("Open-ended")){
                                    btnPrevAnswer.setVisible(true);
                                    btnNextAnswer.setVisible(true);
                                    lblAnswerPage.setVisible(true);
                                    txtAnswer.setText("Response " + (answerNo) + "/" + individualResponses.size() + ": " + openEndedMap.get("Q" + qNo + "A" + (answerNo - 1)));
                                    if (answerNo == 1) {
                                        btnPrevAnswer.setDisable(true);
                                    }
                                    if (answerNo == individualResponses.size()) {
                                        btnNextAnswer.setDisable(true);
                                    }
                                }
                                for (int i4 = 1; i4 < questionDetails.size(); i4++){
                                    int noOfResponses = mcqMap.get("Q" + qNo + "A" + i4);
                                    int totalReponses = individualResponses.size();
                                    double responsePercentage = ((double)noOfResponses / (double)totalReponses) * 100;
                                    txtAnswer.appendText("Answer " + i4 + ": " + questionDetails.get(i4) + " - " + noOfResponses + "/" + totalReponses + " of responses  or  " + responsePercentage + "%\n");
                                }
                                break; 
                            } else {
                                txtAnswer.setText("This Survey Has No Responses Yet");;
                            }
                            
                        }
                    }
                    lblAnswerPage.setText(String.valueOf(answerNo) + "/" + String.valueOf(individualResponses.size()));

                }
            }
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        qNo = 1;
        answerNo = 1;
        btnPrev.setDisable(true);
        ViewResponseDetails(surveyID, qNo, answerNo);
    }

    @FXML
    private void switchSCDashBoard() throws IOException {
        App.setRoot(prevPage);
    }
}
