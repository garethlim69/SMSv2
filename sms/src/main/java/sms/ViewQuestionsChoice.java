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

import javax.swing.*;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class ViewQuestionsChoice implements Initializable {

    @FXML
    private Label lblTitle;
    @FXML
    private Label lblID;
    @FXML
    private Label lblQuestionType;
    @FXML
    private Label lblQuestion;
    @FXML
    private Label lblPage;
    @FXML
    private Label lblQuestionNo;
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
    static int qNo = 1;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        btnPrev.setDisable(true);
        ViewQuestions(surveyID, qNo);
    }

    @FXML
    private void switchSCDashBoard() throws IOException {
        App.setRoot("scDashboard");
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
        ViewQuestions(surveyID, qNo);
    }

    public void NextQuestion() {
        qNo++;
        if (qNo > 1) {
            btnPrev.setDisable(false);
        }
        ViewQuestions(surveyID, qNo);
    }

    public void ViewQuestions(String surveyID, int qNo) {
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
                    lblID.setText("ID: " + surveyID);
                    lblTitle.setText("Title: " + surveyDetails.get(1));
                    lblQuestionType.setText("Question Type: " + questionList.get((qNo - 1) * 2));
                    lblQuestionNo.setText("Question " + qNo);
                    // System.out.println("Question " + qNo);
                    // System.out.println("Quetion Type: " + questionList.get((qNo - 1) * 2));
                    String[] e3 = questionList.get(((qNo - 1) * 2) + 1).split("␞");
                    List<String> questionDetails = Arrays.asList(e3);
                    lblQuestion.setText(questionDetails.get(0));
                    // System.out.println("Question: " + questionDetails.get(0));
                    for (int i2 = 1; i2 < questionDetails.size(); i2++) {
                        switch (i2) {
                            case 1:
                                // System.out.println(questionDetails.get(i2));
                                rbtn1.setText(questionDetails.get(i2));
                                break;
                            case 2:
                                rbtn2.setText(questionDetails.get(i2));
                                break;
                            case 3:
                                rbtn3.setText(questionDetails.get(i2));
                                break;
                            case 4:
                                rbtn4.setText(questionDetails.get(i2));
                                break;
                            default:
                                break;
                        }
                        // System.out.println("Answer: " + i2 + ": " + questionDetails.get(i2));
                    }
                    if (rbtn3.getText().equals("RadioButton")) {
                        rbtn3.setVisible(false);
                    }
                    if (rbtn4.getText().equals("RadioButton")) {
                        rbtn4.setVisible(false);
                    }

                }
            }
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }
    }

    public void AddQuestionButton() {
        AddQuestion(surveyID);
    }

    public static void EditQuestion(String surveyID) {
        String fileName = "src/main/java/Text Files/Surveys.txt";

        // qNo = 0 means question 1, qNo = 1 means question 2 etc...

        // String newQ = "How many days in a year?";
        // String newA1 = "123";
        // String newA2 = "456";
        // String newA3 = "789";
        // String newA4 = "32";

        String singleQuestion = "";

        List<String> listOfSurveys;
        try {
            listOfSurveys = Files.readAllLines(Paths.get(fileName));
            for (int i = 0; i < listOfSurveys.size(); i++) {
                String[] e1 = listOfSurveys.get(i).split("␜");
                List<String> surveyDetails = Arrays.asList(e1);
                if (surveyID.equals(surveyDetails.get(0))) {
                    String[] e2 = surveyDetails.get(4).split("␝");
                    List<String> questionList = Arrays.asList(e2);
                    for (int i3 = 0; i3 < questionList.size(); i3++) {
                        if (i3 == ((qNo * 2) + 1)) {
                            String[] e3 = questionList.get(i3).split("␞");
                            List<String> questionDetails = Arrays.asList(e3);
                            if (questionList.get((qNo - 1) * 2).toLowerCase().equals("mcq")) {
                                String question = JOptionPane.showInputDialog("Edit Question", questionDetails.get(0));
                                // Number of option
                                int NoOfOption = 0;
                                String Answer1 = "";
                                String Answer2 = "";
                                String Answer3 = "";
                                String Answer4 = "";
                                switch (NoOfOption) {
                                    case 2:
                                        Answer1 = JOptionPane.showInputDialog("Edit Option 1:");
                                        Answer2 = JOptionPane.showInputDialog("Edit Option 2:");
                                        if (!Answer1.isBlank() || !Answer2.isBlank()) {
                                            questionDetails.set(0, question);
                                            questionDetails.set(1, Answer1);
                                            questionDetails.set(2, Answer2);
                                        } else
                                            System.out.println("Blank Option Detected! Abort Question Creation!");

                                        break;
                                    case 3:
                                        Answer1 = JOptionPane.showInputDialog("Edit Option 1:");
                                        Answer2 = JOptionPane.showInputDialog("Edit Option 2:");
                                        Answer3 = JOptionPane.showInputDialog("Edit Option 1:");
                                        if (!Answer1.isBlank() || !Answer2.isBlank() || !Answer3.isBlank()) {
                                            questionDetails.set(0, question);
                                            questionDetails.set(1, Answer1);
                                            questionDetails.set(2, Answer2);
                                            questionDetails.set(3, Answer3);
                                        } else
                                            System.out.println("Blank Option Detected! Abort Question Creation!");
                                        break;
                                    case 4:
                                        Answer1 = JOptionPane.showInputDialog("Edit Option 1:");
                                        Answer2 = JOptionPane.showInputDialog("Edit Option 2:");
                                        Answer3 = JOptionPane.showInputDialog("Edit Option 1:");
                                        Answer4 = JOptionPane.showInputDialog("Edit Option 2:");
                                        if (!Answer1.isBlank() || !Answer2.isBlank() || !Answer3.isBlank()
                                                || !Answer4.isBlank()) {
                                            questionDetails.set(0, question);
                                            questionDetails.set(1, Answer1);
                                            questionDetails.set(2, Answer2);
                                            questionDetails.set(3, Answer3);
                                            questionDetails.set(4, Answer4);
                                        } else
                                            System.out.println("Blank Option Detected! Abort Question Creation!");
                                        break;
                                    default:
                                        break;
                                }
                            }
                            for (int i4 = 0; i4 < questionDetails.size(); i4++) {
                                singleQuestion = singleQuestion + questionDetails.get(i4);
                                if (i4 != questionDetails.size() - 1) {
                                    singleQuestion = singleQuestion + "␞";
                                }
                            }
                            System.out.println(questionDetails);
                            System.out.println(singleQuestion);
                            questionList.set(i3, singleQuestion);
                            String fullQuestions = "";
                            for (int i4 = 0; i4 < questionList.size(); i4++) {
                                fullQuestions = fullQuestions + questionList.get(i4);
                                if (i4 != (questionList.size() - 1)) {
                                    fullQuestions = fullQuestions + "␝";
                                }
                            }
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
                }
            }
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }

    }

    public static void AddQuestion(String surveyID) {
        String fileName = "src/main/java/Text Files/Surveys.txt";

        String[] optionsType = { "MCQ", "Polar", "Open-Ended" };
        var QuestionType = JOptionPane.showOptionDialog(null, "Choose Question Type:", "Question Type", 0, 3, null,
                optionsType, null);

        // build the question and answers into a single string
        // qNo = 0 means question 1, qNo = 1 means question 2 etc...
        String questionType = "";

        switch (QuestionType) {
            case 0:
                questionType = "MCQ";
                break;
            case 1:
                questionType = "Polar";
                break;
            case 2:
                questionType = "Open-Ended";
                break;
            default:
                break;
        }

        String question = "";

        // List<String> mcqAnswers = new ArrayList<>();
        // mcqAnswers.add("123");
        // mcqAnswers.add("456");
        // mcqAnswers.add("789");
        // mcqAnswers.add("32");

        String singleQuestion = "";

        switch (questionType) {
            case "MCQ":

                String[] Numbers = { "2", "3", "4" };
                JComboBox cb = new JComboBox(Numbers);
                int NoOfAnswer = JOptionPane.showConfirmDialog(null, cb, "How Many Options (2-4)",
                        JOptionPane.DEFAULT_OPTION);

                int NoOfOption = Integer.parseInt((String) cb.getSelectedItem());

                question = JOptionPane.showInputDialog("Enter Question");

                if (!question.isBlank()) {
                    String Answer1 = "";
                    String Answer2 = "";
                    String Answer3 = "";
                    String Answer4 = "";
                    List<String> mcqAnswers = new ArrayList<>();

                    switch (NoOfOption) {
                        case 2:
                            Answer1 = JOptionPane.showInputDialog("Enter Option 1:");
                            Answer2 = JOptionPane.showInputDialog("Enter Option 2:");
                            if (!Answer1.isBlank() || !Answer2.isBlank()) {
                                mcqAnswers.add(Answer1);
                                mcqAnswers.add(Answer2);
                            } else
                                System.out.println("Blank Option Detected! Abort Question Creation!");

                            break;
                        case 3:
                            Answer1 = JOptionPane.showInputDialog("Enter Option 1:");
                            Answer2 = JOptionPane.showInputDialog("Enter Option 2:");
                            Answer3 = JOptionPane.showInputDialog("Enter Option 1:");
                            if (!Answer1.isBlank() || !Answer2.isBlank() || !Answer3.isBlank()) {
                                mcqAnswers.add(Answer1);
                                mcqAnswers.add(Answer2);
                                mcqAnswers.add(Answer3);
                            } else
                                System.out.println("Blank Option Detected! Abort Question Creation!");
                            break;
                        case 4:
                            Answer1 = JOptionPane.showInputDialog("Enter Option 1:");
                            Answer2 = JOptionPane.showInputDialog("Enter Option 2:");
                            Answer3 = JOptionPane.showInputDialog("Enter Option 1:");
                            Answer4 = JOptionPane.showInputDialog("Enter Option 2:");
                            if (!Answer1.isBlank() || !Answer2.isBlank() || !Answer3.isBlank() || !Answer4.isBlank()) {
                                mcqAnswers.add(Answer1);
                                mcqAnswers.add(Answer2);
                                mcqAnswers.add(Answer3);
                                mcqAnswers.add(Answer4);
                            } else
                                System.out.println("Blank Option Detected! Abort Question Creation!");
                            break;
                        default:
                            break;
                    }

                    singleQuestion = questionType + "␝" + question;

                    for (int i = 0; i < mcqAnswers.size(); i++) {
                        singleQuestion = singleQuestion + "␞" + mcqAnswers.get(i);
                    }
                } else {
                    System.out.println("Question Cannot Be Blank!");
                }

                break;
            case "Polar":
                question = JOptionPane.showInputDialog("Enter Question");
                if (!question.isBlank()) {
                    String polarAnswer1 = "Yes";
                    String polarAnswer2 = "No";
                    singleQuestion = questionType + "␝" + question;
                    singleQuestion = singleQuestion + "␞" + polarAnswer1 + "␞" + polarAnswer2;
                } else {
                    System.out.println("Question Cannot Be Blank!");
                }
                break;
            default:
                singleQuestion = questionType + "␝" + question;
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

}
