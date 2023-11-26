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
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.swing.*;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class ViewQuestionsChoice implements Initializable {

    @FXML
    private static Label lblTitle;
    @FXML
    private static Label lblID;
    @FXML
    private static Label lblQuestionType;
    @FXML
    private static Label lblQuestion;
    @FXML
    private static Label lblPage;
    @FXML
    private static Label lblQuestionNo;
    @FXML
    private static Label lblNoQuestion;
    @FXML
    private static RadioButton rbtn1;
    @FXML
    private static RadioButton rbtn2;
    @FXML
    private static RadioButton rbtn3;
    @FXML
    private static RadioButton rbtn4;
    @FXML
    private static Button btnNext;
    @FXML
    private static Button btnPrev;
    @FXML
    private static Button btnAddQ;
    @FXML
    private static Button btnEditQ;
    @FXML
    private static Button btnDeleteQ;

    static String surveyID;
    static String prevPage;
    static int qNo;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        qNo = 1;
        btnPrev.setDisable(true);
        ViewQuestions(surveyID, qNo);
    }

    @FXML
    private void switchSCDashBoard() throws IOException {
        App.setRoot(prevPage);
    }

    public static void SurveyID(String SurveyID) {
        surveyID = SurveyID;
    }

    public static void prevPage(String previousPage) {
        prevPage = previousPage;
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
        ViewQuestions(surveyID, qNo);
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
        ViewQuestions(surveyID, qNo);
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

    public static int getNoOfOptions(String surveyID, int qNo) {
        String fileName = "src/main/java/Text Files/Surveys.txt";
        int NoOfOptions = 0;

        List<String> listOfSurveys;
        try {
            listOfSurveys = Files.readAllLines(Paths.get(fileName));
            for (int i = 0; i < listOfSurveys.size(); i++) {
                String[] e1 = listOfSurveys.get(i).split("␜");
                List<String> surveyDetails = Arrays.asList(e1);
                if (surveyID.equals(surveyDetails.get(0))) {
                    String[] e2 = surveyDetails.get(4).split("␝");
                    List<String> questionList = Arrays.asList(e2);
                    String[] e3 = questionList.get(((qNo - 1) * 2) + 1).split("␞");
                    List<String> questionDetails = Arrays.asList(e3);
                    NoOfOptions = (questionDetails.size() - 1);
                }
            }
        } catch (IOException e) {
            System.out.println("IOException");
        }
        return NoOfOptions;
    }

    public static void ViewQuestions(String surveyID, int qNo) {
        String fileName = "src/main/java/Text Files/Surveys.txt";

        rbtn1.setVisible(false);
        rbtn2.setVisible(false);
        rbtn3.setVisible(false);
        rbtn4.setVisible(false);
        
        lblNoQuestion.setVisible(false);
        btnEditQ.setDisable(false);
        btnDeleteQ.setDisable(false);
        if (getNoOfQs(surveyID) == 0){
            lblPage.setText("Page " + String.valueOf(qNo) + "/" + 1);
            lblQuestion.setVisible(false);
            lblQuestionType.setVisible(false);
            lblQuestionNo.setVisible(false);
            btnEditQ.setDisable(true);
            btnDeleteQ.setDisable(true);
        } else {
            lblPage.setText("Page " + String.valueOf(qNo) + "/" + String.valueOf(getNoOfQs(surveyID)));
        }
        
        if (qNo >= getNoOfQs(surveyID)) {
            btnNext.setDisable(true);
        }
        if (qNo == 1) {
            btnPrev.setDisable(true);
        }

        List<String> listOfSurveys;
        try {
            listOfSurveys = Files.readAllLines(Paths.get(fileName));
            for (int i = 0; i < listOfSurveys.size(); i++) {
                String[] e1 = listOfSurveys.get(i).split("␜");
                List<String> surveyDetails = Arrays.asList(e1);
                if (surveyID.equals(surveyDetails.get(0))) {
                    lblID.setText("ID: " + surveyID);
                    lblTitle.setText("Title: " + surveyDetails.get(1));
                    if (!surveyDetails.get(4).isBlank()){
                        String[] e2 = surveyDetails.get(4).split("␝");
                        List<String> questionList = Arrays.asList(e2);
                        lblQuestionType.setText("" + questionList.get((qNo - 1) * 2));
                        lblQuestionNo.setText("Q" + qNo);
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
                    
                        // System.out.println("Answer: " + i2 + ": " + questionDetails.get(i2));
                    } else {
                        lblNoQuestion.setVisible(true);
                    }
                    // if (rbtn3.getText().equals("RadioButton")) {
                    // rbtn3.setVisible(false);
                    // }
                    // if (rbtn4.getText().equals("RadioButton")) {
                    // rbtn4.setVisible(false);
                    // }

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

    public void EditQuestionButton() {
        EditQuestion(surveyID);
    }

    public void DeleteQuestionButton() {
        DeleteQuestion(surveyID);
    }

    public static void EditQuestion(String surveyID) {
        String fileName = "src/main/java/Text Files/Surveys.txt";

        // qNo = 0 means question 1, qNo = 1 means question 2 etc...

        int qPos = qNo - 1;

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
                        if (i3 == ((qPos * 2) + 1)) {
                            System.out.println(questionList);
                            String[] e3 = questionList.get(i3).split("␞");
                            List<String> questionDetails = Arrays.asList(e3);
                            if (questionList.get(qPos * 2).toLowerCase().equals("mcq") || questionList.get(qPos * 2).toLowerCase().equals("polar")) {
                                String question = JOptionPane.showInputDialog("Edit Question", questionDetails.get(0));
                                // Number of option
                                int NoOfOption = getNoOfOptions(surveyID, qPos + 1);
                                String Answer1 = "";
                                String Answer2 = "";
                                String Answer3 = "";
                                String Answer4 = "";
                                boolean isEmpty = false;

                                Answer1 = JOptionPane.showInputDialog("Edit Option 1:", questionDetails.get(1));
                                Answer2 = JOptionPane.showInputDialog("Edit Option 2:", questionDetails.get(2));
                                if (question.isBlank() || Answer1.isBlank() || Answer2.isBlank()) {
                                    isEmpty = true;
                                }
                                if (NoOfOption > 2) {
                                    Answer3 = JOptionPane.showInputDialog("Edit Option 3:", questionDetails.get(3));
                                    if (Answer3.isBlank()) {
                                        isEmpty = true;
                                    }
                                    if (NoOfOption > 3) {
                                        Answer4 = JOptionPane.showInputDialog("Edit Option 4:", questionDetails.get(4));
                                        if (Answer4.isBlank()) {
                                            isEmpty = true;
                                        }
                                    }
                                }
                                if (isEmpty) {
                                    JOptionPane.showMessageDialog(null, "Blank Field Detected! Abort Question Creation!", "Blank Field", JOptionPane.WARNING_MESSAGE);
                                } else {
                                    questionDetails.set(0, question);
                                    questionDetails.set(1, Answer1);
                                    questionDetails.set(2, Answer2);
                                    if (NoOfOption > 2) {
                                        questionDetails.set(3, Answer3);
                                        if (NoOfOption > 3) {
                                            questionDetails.set(4, Answer4);
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
                                    JOptionPane.showMessageDialog (null, "Updated Successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                                    ViewQuestions(surveyID, qNo);
                                }
                            } else {
                                String question = JOptionPane.showInputDialog("Edit Question", questionDetails.get(0));
                                if (question.isBlank()) {
                                    JOptionPane.showMessageDialog(null, "Blank Field Detected! Abort Question Creation!", "Blank Field", JOptionPane.WARNING_MESSAGE);
                                } else {
                                    questionDetails.set(0, question);
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
                                    JOptionPane.showMessageDialog (null, "Updated Successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                                    ViewQuestions(surveyID, qNo);
                                }
                            }
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
        boolean isEmpty = false;

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

        System.out.println(getNoOfQs(surveyID));
        int[] num = IntStream.range(1, getNoOfQs(surveyID) + 2).toArray();
        IntStream stream = Arrays.stream(num);
        Stream<Integer> boxed = stream.boxed();
        Integer[] result = boxed.toArray(Integer[]::new);
        JComboBox qPosition = new JComboBox(result);
        JOptionPane.showConfirmDialog(null, qPosition, "Choose Question Position", JOptionPane.DEFAULT_OPTION);
        System.out.println(qPosition.getSelectedItem().getClass().getName());
        Integer qPosChosen = (Integer) qPosition.getSelectedItem();

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
                
                JOptionPane.showConfirmDialog(null, cb, "How Many Options (2-4)",
                        JOptionPane.DEFAULT_OPTION);

                int NoOfOption = Integer.parseInt((String) cb.getSelectedItem());

                question = JOptionPane.showInputDialog("Enter Question");
                //no mcqAnswers.add(question, 0) ?

                if (!question.isBlank()) {
                    String Answer1 = "";
                    String Answer2 = "";
                    String Answer3 = "";
                    String Answer4 = "";
                    List<String> mcqAnswers = new ArrayList<>();

                    Answer1 = JOptionPane.showInputDialog("Edit Option 1:");
                    Answer2 = JOptionPane.showInputDialog("Edit Option 2:");
                    if (Answer1.isBlank() || Answer2.isBlank()) {
                        isEmpty = true;
                    }
                    if (NoOfOption > 2) {
                        Answer3 = JOptionPane.showInputDialog("Edit Option 3:");
                        if (Answer3.isBlank()) {
                            isEmpty = true;
                        }
                        if (NoOfOption > 3) {
                            Answer4 = JOptionPane.showInputDialog("Edit Option 4:");
                            if (Answer4.isBlank()) {
                                isEmpty = true;
                            }
                        }
                    }
                    mcqAnswers.add(Answer1);
                    mcqAnswers.add(Answer2);
                    if (NoOfOption > 2) {
                        mcqAnswers.add(Answer3);
                        if (NoOfOption > 3) {
                            mcqAnswers.add(Answer4);
                        }
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
                    String polarAnswer1 = JOptionPane.showInputDialog("Edit Option 1:");
                    String polarAnswer2 = JOptionPane.showInputDialog("Edit Option 2:");
                    if (polarAnswer1.isBlank() || polarAnswer2.isBlank()) {
                        isEmpty = true;
                    }
                    singleQuestion = questionType + "␝" + question;
                    singleQuestion = singleQuestion + "␞" + polarAnswer1 + "␞" + polarAnswer2;
                    System.out.println(singleQuestion);
                } else {
                    isEmpty = true;
                    System.out.println("Question Cannot Be Blank!");
                }
                break;
            default:
                singleQuestion = questionType + "␝" + question;
                break;
        }

        // inserts new question into surveydetails array
        if (isEmpty) {
            //warning box here
            System.out.println("Blank Option Detected! Abort Question Creation!");
        } else {
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
                                if (i2 == (qPosChosen * 2)) {
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
        
    }

    public static void DeleteQuestion(String surveyID){
        String fileName = "src/main/java/Text Files/Surveys.txt";

        // qNo = 0 means question 1, qNo = 1 means question 2 etc...
        int localQNo = qNo - 1;

        int result = JOptionPane.showConfirmDialog(null, "Would you like to delete Question " + localQNo + "?", "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.YES_OPTION){
            List<String> listOfSurveys;
            try {
                listOfSurveys = Files.readAllLines(Paths.get(fileName));
                for (int i = 0; i < listOfSurveys.size(); i++){
                    String[] e1 = listOfSurveys.get(i).split("␜");
                    List<String> surveyDetails = Arrays.asList(e1);
                    if (surveyID.equals(surveyDetails.get(0))){
                        String[] e2 = surveyDetails.get(4).split("␝");
                        List<String> questionList = Arrays.asList(e2);
                        System.out.println(questionList);
                        // questionList.remove(qNo * 2);
                        // questionList.remove((qNo * 2) + 1);
                        String fullQuestions = "";
                        for (int i4 = 0; i4 < questionList.size(); i4++){
                            if (i4 != localQNo * 2 && i4 != (localQNo * 2) + 1){
                                fullQuestions = fullQuestions + questionList.get(i4);
                                if (i4 != (questionList.size() - 1)) {
                                    fullQuestions = fullQuestions + "␝";
                                }
                            }
                        }
                        if (fullQuestions.length() == 0){
                            fullQuestions = " ";
                        }
                        System.out.println(fullQuestions);
                        surveyDetails.set(4, fullQuestions);
                        String newRecord = "";
                        for (int i2 = 0; i2 < surveyDetails.size(); i2++){
                            newRecord = newRecord + surveyDetails.get(i2);
                            if (i2 != (surveyDetails.size() - 1)) {
                                newRecord = newRecord + "␜";
                            }
                        }
                        UpdateFile(fileName, i, newRecord);
                    }
                }
            }  catch (IOException e) {
                System.out.println("IOException");
                e.printStackTrace();
            }
        } else {
            System.out.println("deletion aborted");
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
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }
    }

}
