package sms;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;

public class AnswerQuestions implements Initializable{
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblID;
    @FXML
    private Label lblQuestionType;
    @FXML
    private Label lblQuestion;
    @FXML
    private Label lblQuestionNo;
    @FXML
    private Label lblPage;
    @FXML
    private TextArea txtAnswer;
    @FXML
    private RadioButton rbtn1;
    @FXML
    private RadioButton rbtn2;
    @FXML
    private RadioButton rbtn3;
    @FXML
    private RadioButton rbtn4;
    @FXML
    private Button btnNext;
    @FXML
    private Button btnPrev;

    static String surveyID;
    static int qNo;
    String answer;
    List<String> answerList = new ArrayList<String>();

    ToggleGroup togglegroup = new ToggleGroup();

    public static void SurveyID(String SurveyID) {
        surveyID = SurveyID;
    }

    @FXML
    private void prevPage() throws IOException {
        App.setRoot("respondentViewSurvey");
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
        AnswerSurveyQuestion(surveyID, qNo);
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
        AnswerSurveyQuestion(surveyID, qNo);
    }

    public void Submit() {
        System.out.println(qNo);
        saveAnswer(qNo, answer);
        System.out.println(answerList);
        // for (int i = 0; i < answerList.size(); i++){
        //     if (answerList.get(i).isBlank()){
        //         int blankQ = i + 1;
        //         System.out.println(blankQ);
        //         //joption pane that question blankQ is blank
        //         break;
        //     } else {

        //     }
        // }
    }

    public void saveAnswer(int questionNo, String answer) {
        answerList.set(questionNo, answer);
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

    public void AnswerSurveyQuestion(String surveyID, int qNo){
        String fileName = "src/main/java/Text Files/Surveys.txt";

        RadioButton selectedRdbtn;
        answer = "";
        
        answerList.set(0, surveyID);

        rbtn1.setToggleGroup(togglegroup);
        rbtn2.setToggleGroup(togglegroup);
        rbtn3.setToggleGroup(togglegroup);
        rbtn4.setToggleGroup(togglegroup);
        rbtn1.setVisible(false);
        rbtn2.setVisible(false);
        rbtn3.setVisible(false);
        rbtn4.setVisible(false);
        txtAnswer.setVisible(false);

        if (qNo >= getNoOfQs(surveyID)) {
            btnNext.setDisable(true);
        }
        if (qNo == 1) {
            btnPrev.setDisable(true);
        }

        lblPage.setText("Page " + String.valueOf(qNo) + "/" + String.valueOf(getNoOfQs(surveyID)));

        List<String> listOfSurveys;
        try {
            listOfSurveys = Files.readAllLines(Paths.get(fileName));
            for (int i = 0; i < listOfSurveys.size(); i++) {
                String[] e1 = listOfSurveys.get(i).split("␜");
                List<String> surveyDetails = Arrays.asList(e1);
                if (surveyID.equals(surveyDetails.get(0))) {
                    lblID.setText("ID: " + surveyID);
                    lblTitle.setText("Title: " + surveyDetails.get(1));
                    String[] e2 = surveyDetails.get(4).split("␝");
                    List<String> questionList = Arrays.asList(e2);
                    lblQuestionType.setText("Question Type: " + questionList.get((qNo - 1) * 2));
                    lblQuestionNo.setText("Question " + qNo);
                    // System.out.println("Question " + qNo);
                    // System.out.println("Quetion Type: " + questionList.get((qNo - 1) * 2));
                    String[] e3 = questionList.get(((qNo - 1) * 2) + 1).split("␞");
                    List<String> questionDetails = Arrays.asList(e3);
                    lblQuestion.setText(questionDetails.get(0));
                    // System.out.println("Question: " + questionDetails.get(0));
                    System.out.println(questionList.get((qNo - 1) * 2));
                    if (questionList.get((qNo - 1) * 2).equals("MCQ") || questionList.get((qNo - 1) * 2).equals("Polar")){
                        for (int i2 = 1; i2 < questionDetails.size(); i2++) {
                            switch (i2) {
                                case 1:
                                    // System.out.println(questionDetails.get(i2));
                                    rbtn1.setVisible(true);
                                    rbtn1.setText(questionDetails.get(i2));
                                    break;
                                case 2:
                                    rbtn2.setVisible(true);
                                    rbtn2.setText(questionDetails.get(i2));
                                    break;
                                case 3:
                                    rbtn3.setVisible(true);
                                    rbtn3.setText(questionDetails.get(i2));
                                    break;
                                case 4:
                                    rbtn4.setVisible(true);
                                    rbtn4.setText(questionDetails.get(i2));
                                    break;
                                default:
                                    break;
                            }
                        }
                        System.out.println(togglegroup.getSelectedToggle());
                        selectedRdbtn = (RadioButton) togglegroup.getSelectedToggle();
                        if (selectedRdbtn == rbtn1) {
                            answer = "1";
                            System.out.println("first one was chosen");
                        } else if (selectedRdbtn == rbtn2) {
                            answer = "2";
                            System.out.println("second one was chosen");
                        } else if (selectedRdbtn == rbtn3) {
                            answer = "3";
                            System.out.println("third one was chosen");
                        } else if (selectedRdbtn == rbtn4) {
                            answer = "4";
                            System.out.println("fourth one was chosen");
                        } else {
                            answer = "";
                        }
                    } else {
                        txtAnswer.setVisible(true);
                        answer = txtAnswer.getText();
                    }
                    saveAnswer(qNo - 1, answer);
                }
            }
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }
    }

    

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        for (int i = 0; i < getNoOfQs(surveyID) + 1; i++) {
            answerList.add("");
        }
        qNo = 1;
        btnPrev.setDisable(true);
        AnswerSurveyQuestion(surveyID, qNo);
    }
}
