package sms;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;

public class AnswerQuestions implements Initializable {
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
    @FXML
    private Button btnSave;

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
        // disables previous question button when at first question
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
        // disables next question button when at last question
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
        // checks for blank responses
        boolean isEmpty = false;
        String answerString = "";
        List<String> listOfResponses;
        for (int i = 0; i < answerList.size(); i++) {
            if (answerList.get(i).isBlank()) {
                JOptionPane.showMessageDialog(null, "Question " + i + " is blank. Please answer it before submitting.",
                        "Blank Question", JOptionPane.WARNING_MESSAGE);
                isEmpty = true;
                break;
            } else {
                answerString = answerString + answerList.get(i);
                if (i != (answerList.size() - 1)) {
                    answerString = answerString + "␜";
                }
            }
        }
        // if there are no blank responses, write answer to text file
        if (!isEmpty) {
            try {
                listOfResponses = Files.readAllLines(Paths.get("src/main/java/Text Files/Responses.txt"));
                UpdateFile("src/main/java/Text Files/Responses.txt", listOfResponses.size() + 1, answerString);
                App.setRoot("respondentViewSurvey");
            } catch (IOException e) {
                System.out.println("IO Exception");
                e.printStackTrace();
            }
        }
    }

    // set radio button as selected for selected answer
    public void radioBtn1() {
        answerList.set(qNo, "1");
    }

    public void radioBtn2() {
        answerList.set(qNo, "2");
    }

    public void radioBtn3() {
        answerList.set(qNo, "3");
    }

    public void radioBtn4() {
        answerList.set(qNo, "4");
    }

    // saves open-ended answer to answerList list
    public void saveBtn() {
        answerList.set(qNo, txtAnswer.getText());
    }

    public static int getNoOfQs(String surveyID) {
        // reads Surveys.txt and returns number of questions for the survey with matching surveyID
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

    public void AnswerSurveyQuestion(String surveyID, int qNo) {
        String fileName = "src/main/java/Text Files/Surveys.txt";

        RadioButton selectedRdbtn;
        answer = "";

        // sets surveyID to first index of answerList
        answerList.set(0, surveyID);

        // adds all the radio buttons to a toggle group
        rbtn1.setToggleGroup(togglegroup);
        rbtn2.setToggleGroup(togglegroup);
        rbtn3.setToggleGroup(togglegroup);
        rbtn4.setToggleGroup(togglegroup);

        rbtn1.setVisible(false);
        rbtn2.setVisible(false);
        rbtn3.setVisible(false);
        rbtn4.setVisible(false);
        txtAnswer.setVisible(false);
        btnSave.setVisible(false);

        // mark radio button as selected based on chosen answer
        switch (answerList.get(qNo)) {
            case "1":
                togglegroup.selectToggle(rbtn1);
                break;
            case "2":
                togglegroup.selectToggle(rbtn2);
                break;
            case "3":
                togglegroup.selectToggle(rbtn3);
                break;
            case "4":
                togglegroup.selectToggle(rbtn4);
                break;
            default:
                togglegroup.selectToggle(null);
        }

        if (qNo >= getNoOfQs(surveyID)) {
            btnNext.setDisable(true);
        }
        if (qNo == 1) {
            btnPrev.setDisable(true);
        }

        // set text for page label
        lblPage.setText("Page " + String.valueOf(qNo) + "/" + String.valueOf(getNoOfQs(surveyID)));

        // inserts all surveys into listOfSurveys
        List<String> listOfSurveys;
        try {
            listOfSurveys = Files.readAllLines(Paths.get(fileName));
            for (int i = 0; i < listOfSurveys.size(); i++) {
                // splits each line into individual survey details
                String[] e1 = listOfSurveys.get(i).split("␜");
                List<String> surveyDetails = Arrays.asList(e1);
                // finds matching survey and displays survey ID and Title
                if (surveyID.equals(surveyDetails.get(0))) {
                    lblID.setText("ID: " + surveyID);
                    lblTitle.setText("Title: " + surveyDetails.get(1));
                    // further splits survey details into questionList
                    String[] e2 = surveyDetails.get(4).split("␝");
                    List<String> questionList = Arrays.asList(e2);
                    // displays question type and question itself of selected question
                    lblQuestionType.setText("Question Type: " + questionList.get((qNo - 1) * 2));
                    lblQuestionNo.setText("Question " + qNo);
                    // splits questionList into individual question and answers
                    String[] e3 = questionList.get(((qNo - 1) * 2) + 1).split("␞");
                    List<String> questionDetails = Arrays.asList(e3);
                    lblQuestion.setText(questionDetails.get(0));
                    // if question type is MCQ or Polar, set show radio buttons based on number of options for said question
                    if (questionList.get((qNo - 1) * 2).equals("MCQ")
                            || questionList.get((qNo - 1) * 2).equals("Polar")) {
                        for (int i2 = 1; i2 < questionDetails.size(); i2++) {
                            switch (i2) {
                                case 1:
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

                        // detect which radio button has been selected
                        selectedRdbtn = (RadioButton) togglegroup.getSelectedToggle();
                        if (selectedRdbtn == rbtn1) {
                            radioBtn1();
                        } else if (selectedRdbtn == rbtn2) {
                            radioBtn2();
                        } else if (selectedRdbtn == rbtn3) {
                            radioBtn3();
                        } else if (selectedRdbtn == rbtn4) {
                            radioBtn4();
                        } else {
                            answer = "";
                        }
                    } else {
                        // shows text area for answering open-ended questions
                        btnSave.setVisible(true);
                        txtAnswer.setEditable(true);
                        if (!answerList.get(qNo).isBlank()) {
                            txtAnswer.setText(answerList.get(qNo));
                        } else {
                            txtAnswer.setText("");
                        }
                        txtAnswer.setVisible(true);
                        answer = txtAnswer.getText();
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
            JOptionPane.showMessageDialog(null, "Your Response Has Been Submitted", "Submitted",
                    JOptionPane.INFORMATION_MESSAGE);
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
