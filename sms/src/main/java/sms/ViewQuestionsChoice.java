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
    private Label lblNoQuestion;
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
    private Button btnAddQ;
    @FXML
    private Button btnEditQ;
    @FXML
    private Button btnDeleteQ;

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
        if (qNo == getNoOfItems(surveyID)) {
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
        if (qNo == getNoOfItems(surveyID)) {
            btnNext.setDisable(true);
        } else {
            btnNext.setDisable(false);
        }
        ViewQuestions(surveyID, qNo);
    }

    // function to get number of questions in a specific survey
    public static int getNoOfItems(String surveyID) {
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

    // gets number of options for specific MCQ/Polar questions
    public static int getNoOfItems(String surveyID, int qNo) {
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

    public void ViewQuestions(String surveyID, int qNo) {
        String fileName = "src/main/java/Text Files/Surveys.txt";

        rbtn1.setVisible(false);
        rbtn2.setVisible(false);
        rbtn3.setVisible(false);
        rbtn4.setVisible(false);

        lblNoQuestion.setVisible(false);
        btnEditQ.setDisable(false);
        btnDeleteQ.setDisable(false);
        // if survey has no questions, hide label and text fields
        if (getNoOfItems(surveyID) == 0) {
            lblPage.setText("Page " + String.valueOf(qNo) + "/" + 1);
            lblQuestion.setVisible(false);
            lblQuestionType.setVisible(false);
            lblQuestionNo.setVisible(false);
            btnEditQ.setDisable(true);
            btnDeleteQ.setDisable(true);
        } else {
            lblPage.setText("Page " + String.valueOf(qNo) + "/" + String.valueOf(getNoOfItems(surveyID)));
        }

        if (qNo >= getNoOfItems(surveyID)) {
            btnNext.setDisable(true);
        }
        if (qNo == 1) {
            btnPrev.setDisable(true);
        }

        // reads survey details from Surveys.txt
        List<String> listOfSurveys;
        try {
            listOfSurveys = Files.readAllLines(Paths.get(fileName));
            for (int i = 0; i < listOfSurveys.size(); i++) {
                String[] e1 = listOfSurveys.get(i).split("␜");
                List<String> surveyDetails = Arrays.asList(e1);
                if (surveyID.equals(surveyDetails.get(0))) {
                    lblID.setText("ID: " + surveyID);
                    lblTitle.setText("Title: " + surveyDetails.get(1));
                    if (!surveyDetails.get(4).isBlank()) {
                        String[] e2 = surveyDetails.get(4).split("␝");
                        List<String> questionList = Arrays.asList(e2);
                        lblQuestionType.setText("" + questionList.get((qNo - 1) * 2));
                        lblQuestionNo.setText("Q" + qNo);
                        String[] e3 = questionList.get(((qNo - 1) * 2) + 1).split("␞");
                        List<String> questionDetails = Arrays.asList(e3);
                        lblQuestion.setText(questionDetails.get(0));
                        // sets radio buttons to visible according to number of options
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
                    } else {
                        lblNoQuestion.setVisible(true);
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

    public void EditQuestionButton() {
        EditQuestion(surveyID);
    }

    public void DeleteQuestionButton() {
        DeleteQuestion(surveyID);
    }

    public void EditQuestion(String surveyID) {
        String fileName = "src/main/java/Text Files/Surveys.txt";

        // qNo = 0 means question 1, qNo = 1 means question 2 etc...
        int qPos = qNo - 1;

        String singleQuestion = "";

        // reads survey details from Surveys.txt
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
                            String[] e3 = questionList.get(i3).split("␞");
                            List<String> questionDetails = Arrays.asList(e3);
                            // if question type if MCQ or Polar, create answer Strings
                            if (questionList.get(qPos * 2).toLowerCase().equals("mcq")
                                    || questionList.get(qPos * 2).toLowerCase().equals("polar")) {
                                String question = JOptionPane.showInputDialog("Edit Question", questionDetails.get(0));
                                // Number of option
                                int NoOfOption = getNoOfItems(surveyID, qPos + 1);
                                String Answer1 = "";
                                String Answer2 = "";
                                String Answer3 = "";
                                String Answer4 = "";
                                boolean isEmpty = false;

                                // show number of questions to add depending on what was selected
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
                                // check if any fields are empty
                                if (isEmpty) {
                                    JOptionPane.showMessageDialog(null,
                                            "Blank Field Detected. Question Creation Aborted.", "Blank Field",
                                            JOptionPane.WARNING_MESSAGE);
                                } else {
                                    // adds questions and answers to questionDetails
                                    questionDetails.set(0, question);
                                    questionDetails.set(1, Answer1);
                                    questionDetails.set(2, Answer2);
                                    if (NoOfOption > 2) {
                                        questionDetails.set(3, Answer3);
                                        if (NoOfOption > 3) {
                                            questionDetails.set(4, Answer4);
                                        }
                                    }
                                    // merges questionDetails into a single string
                                    for (int i4 = 0; i4 < questionDetails.size(); i4++) {
                                        singleQuestion = singleQuestion + questionDetails.get(i4);
                                        if (i4 != questionDetails.size() - 1) {
                                            singleQuestion = singleQuestion + "␞";
                                        }
                                    }
                                    questionList.set(i3, singleQuestion);
                                    String fullQuestions = "";
                                    for (int i4 = 0; i4 < questionList.size(); i4++) {
                                        fullQuestions = fullQuestions + questionList.get(i4);
                                        if (i4 != (questionList.size() - 1)) {
                                            fullQuestions = fullQuestions + "␝";
                                        }
                                    }
                                    // inserts question string into surveyDetails
                                    surveyDetails.set(4, fullQuestions);
                                    String newRecord = "";
                                    for (int i2 = 0; i2 < surveyDetails.size(); i2++) {
                                        newRecord = newRecord + surveyDetails.get(i2);
                                        if (i2 != (surveyDetails.size() - 1)) {
                                            newRecord = newRecord + "␜";
                                        }
                                    }
                                    UpdateFile(fileName, i, newRecord);
                                    JOptionPane.showMessageDialog(null, "Updated Successfully.", "Success",
                                            JOptionPane.INFORMATION_MESSAGE);
                                    ViewQuestions(surveyID, qNo);
                                }
                            } else {
                                // if not MCQ/Polar, write question directly without options
                                String question = JOptionPane.showInputDialog("Edit Question", questionDetails.get(0));
                                if (question.isBlank()) {
                                    JOptionPane.showMessageDialog(null,
                                            "Blank Field Detected. Question Creation Aborted.", "Blank Field",
                                            JOptionPane.WARNING_MESSAGE);
                                } else {
                                    questionList.set(i3, question);
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
                                    JOptionPane.showMessageDialog(null, "Question Updated Successfully.", "Success",
                                            JOptionPane.INFORMATION_MESSAGE);
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

    public void AddQuestion(String surveyID) {
        String fileName = "src/main/java/Text Files/Surveys.txt";
        boolean isEmpty = false;

        String[] optionsType = { "MCQ", "Polar", "Open-Ended" };
        var QuestionType = JOptionPane.showOptionDialog(null, "Choose Question Type:", "Question Type", 0, 3, null,
                optionsType, null);

        // build the question and answers into a single string
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

        int[] num = IntStream.range(1, getNoOfItems(surveyID) + 2).toArray();
        IntStream stream = Arrays.stream(num);
        Stream<Integer> boxed = stream.boxed();
        Integer[] result = boxed.toArray(Integer[]::new);
        JComboBox qPosition = new JComboBox(result);
        JOptionPane.showConfirmDialog(null, qPosition, "Choose Question Position", JOptionPane.DEFAULT_OPTION);
        Integer qPosChosen = (Integer) qPosition.getSelectedItem();

        String question = "";

        String singleQuestion = "";

        switch (questionType) {
            case "MCQ":

                String[] Numbers = { "2", "3", "4" };
                JComboBox cb = new JComboBox(Numbers);

                JOptionPane.showConfirmDialog(null, cb, "How Many Options (2-4)",
                        JOptionPane.DEFAULT_OPTION);

                int NoOfOption = Integer.parseInt((String) cb.getSelectedItem());

                question = JOptionPane.showInputDialog("Enter Question");

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
                    isEmpty = true;
                    JOptionPane.showMessageDialog(null, "Blank Question Detected. Please Try Again", "Blank Question",
                            JOptionPane.WARNING_MESSAGE);
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
                } else {
                    isEmpty = true;
                    JOptionPane.showMessageDialog(null, "Blank Question Detected. Please Try Again", "Blank Question",
                            JOptionPane.WARNING_MESSAGE);
                }
                break;
            default:
                question = JOptionPane.showInputDialog("Enter Question");

                if (!question.isBlank()) {
                    singleQuestion = questionType + "␝" + question;
                } else {
                    isEmpty = true;
                    JOptionPane.showMessageDialog(null, "Blank Question Detected. Please Try Again", "Blank Question",
                            JOptionPane.WARNING_MESSAGE);
                }
                break;
        }

        // inserts new question into surveydetails array
        if (isEmpty) {
            JOptionPane.showMessageDialog(null, "Blank Option Detected. Please Try Again", "Blank Option",
                    JOptionPane.WARNING_MESSAGE);
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
                                if (i2 == ((qPosChosen - 1) * 2)) {
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

                        surveyDetails.set(4, fullQuestions);
                        String newRecord = "";
                        for (int i2 = 0; i2 < surveyDetails.size(); i2++) {
                            newRecord = newRecord + surveyDetails.get(i2);
                            if (i2 != (surveyDetails.size() - 1)) {
                                newRecord = newRecord + "␜";
                            }
                        }
                        UpdateFile(fileName, i, newRecord);
                        JOptionPane.showMessageDialog(null, "Question Added Successfully.", "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        ViewQuestions(surveyID, qNo);
                    }
                }
            } catch (IOException e) {
                System.out.println("IOException");
                e.printStackTrace();
            }
        }

    }

    public void DeleteQuestion(String surveyID) {
        String fileName = "src/main/java/Text Files/Surveys.txt";

        int localQNo = qNo - 1;

        // confirms deletion
        int result = JOptionPane.showConfirmDialog(null, "Delete Question " + (localQNo + 1) + "?", "Confirm Deletion",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
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
                        for (int i4 = 0; i4 < questionList.size(); i4++) {
                            // looks for respective question and ommits it from questionList
                            if (i4 != localQNo * 2 && i4 != (localQNo * 2) + 1) {
                                fullQuestions = fullQuestions + questionList.get(i4);
                                if (i4 != (questionList.size() - 1)) {
                                    fullQuestions = fullQuestions + "␝";
                                }
                            }
                        }
                        if (fullQuestions.length() == 0) {
                            fullQuestions = " ";
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
                        JOptionPane.showMessageDialog(null, "Question Deleted Successfully", "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        ViewQuestions(surveyID, 1);
                    }
                }
            } catch (IOException e) {
                System.out.println("IOException");
                e.printStackTrace();
            }
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
