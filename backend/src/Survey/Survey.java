package Survey;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Survey {


    public static void ViewSurveys(){
        String fileName = "src/Text Files/Surveys.txt";

        List<String> listOfSurveys;
        try {
            listOfSurveys = Files.readAllLines(Paths.get(fileName));
            for (int i = 0; i < listOfSurveys.size(); i++){
                String[] e1 = listOfSurveys.get(i).split("␜");
                List<String> surveyDetails = Arrays.asList(e1);
                // for (int i2 = 0; i2 < surveyDetails.size();){
                    System.out.println("SID: " + surveyDetails.get(0));
                    System.out.println("Survey Title: " + surveyDetails.get(1));
                    System.out.println("Created By: " + surveyDetails.get(2));
                    System.out.println("Status: " + surveyDetails.get(3));
                    String[] e2 = surveyDetails.get(4).split("␝");
                    List<String> questionList = Arrays.asList(e2);
                    System.out.println("No of Qs: " + questionList.size() / 2);
                    for (int i3 = 0; i3 < questionList.size(); i3++){
                        if (i3 % 2 == 0){
                            System.out.println("Question Type: " + questionList.get(i3));
                        } else {
                            String[] e3 = questionList.get(i3).split("␞");
                            List<String> questionDetails = Arrays.asList(e3);
                            System.out.println("Question " + ((i3 / 2) + 1) + " : " + questionDetails.get(0));
                            for (int i4 = 1; i4 < questionDetails.size(); i4++){
                                System.out.println("Answer " + i4 + ": " + questionDetails.get(i4));
                            }
                        }
                    }
                    // break;
                // }
                System.out.println("-----------------------------------");
            }
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }
    }

    public static void ChangeStatus(String surveyID, String status){
        String fileName = "src/Text Files/Surveys.txt";

        List<String> listOfSurveys;
        try {
            listOfSurveys = Files.readAllLines(Paths.get(fileName));
            for (int i = 0; i < listOfSurveys.size(); i++){
                String[] e1 = listOfSurveys.get(i).split("␜");
                List<String> surveyDetails = Arrays.asList(e1);
                if (surveyID.equals(surveyDetails.get(0))){
                    // surveyDetails.set(3, status);
                    String newRecord = "";
                    for (int i2 = 0; i2 < surveyDetails.size(); i2++){
                        if (i2 == 3){
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

    public static void CreateSurvey(){
        String fileName = "src/Text Files/Surveys.txt";
        List<String> listOfSurveys;
        int index = 1;

        File file = new File(fileName);
        if (file.length() == 0){
            index = 1;
        } else {
            try {
                listOfSurveys = Files.readAllLines(Paths.get(fileName));
                String[] e1 = listOfSurveys.get(listOfSurveys.size() - 1).split("␜");
                List<String> surveyDetails = Arrays.asList(e1);
                index =  Integer.valueOf(surveyDetails.get(0).substring(1));
                index++;
            } catch (FileNotFoundException e1) {
                System.out.println("File Not Found");
                e1.printStackTrace();
            } catch (IOException e1) {
                System.out.println("IO Exception");
                e1.printStackTrace();
            }
            
        }

        String surveyTitle = "Survey " + index;
        String createdBy = "SC3";
        String status = "not-approved";
        String questionDetails = " ";

        try {
            listOfSurveys = Files.readAllLines(Paths.get(fileName));
            String newRecord = "S" + index + "␜" + surveyTitle + "␜" + createdBy + "␜" + status + "␜"  + questionDetails;
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
    }

    public static void EditTitle(String surveyID, String newTitle){
        String fileName = "src/Text Files/Surveys.txt";

        List<String> listOfSurveys;
        try {
            listOfSurveys = Files.readAllLines(Paths.get(fileName));
            for (int i = 0; i < listOfSurveys.size(); i++){
                String[] e1 = listOfSurveys.get(i).split("␜");
                List<String> surveyDetails = Arrays.asList(e1);
                if (surveyID.equals(surveyDetails.get(0))){
                    String newRecord = "";
                    for (int i2 = 0; i2 < surveyDetails.size(); i2++){
                        if (i2 == 1){
                            newRecord = newRecord + newTitle;
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

    public static void AddQuestion(String surveyID){
        String fileName = "src/Text Files/Surveys.txt";

        //build the question and answers into a single string
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
                for (int i = 0; i < mcqAnswers.size(); i++){
                    singleQuestion = singleQuestion + "␞" + mcqAnswers.get(i);
                }
                break;
            case "Polar":
                singleQuestion = singleQuestion + "␞" + polarAnswer1 + "␞" + polarAnswer2;
                break;
            default:
                break;
        }

        //inserts new question into surveydetails array
        List<String> listOfSurveys;
        try {
            listOfSurveys = Files.readAllLines(Paths.get(fileName));
            for (int i = 0; i < listOfSurveys.size(); i++){
                String[] e1 = listOfSurveys.get(i).split("␜");
                List<String> surveyDetails = Arrays.asList(e1);
                if (surveyID.equals(surveyDetails.get(0))){
                    String[] e2 = surveyDetails.get(4).split("␝");
                    List<String> questionList = Arrays.asList(e2);
                    String fullQuestions = "";
                    if (questionList.size() == 1){
                        fullQuestions = singleQuestion;
                    } else {
                        for (int i2 = 0; i2 < questionList.size(); i2++){
                            if (i2 == (qNo * 2)){
                                fullQuestions = fullQuestions + singleQuestion + "␝";
                            }
                            fullQuestions = fullQuestions + questionList.get(i2);
                            if (i2 != (questionList.size() - 1)) {
                                fullQuestions = fullQuestions + "␝";
                            }
                        }
                        if ((qNo * 2) >= questionList.size()){
                            fullQuestions = fullQuestions + "␝" + singleQuestion;
                        }
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
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }
    }

    public static void EditQuestion(String surveyID){
        String fileName = "src/Text Files/Surveys.txt";

        // qNo = 0 means question 1, qNo = 1 means question 2 etc...
        int qNo = 1;

        String newQ = "How many days in a year?";
        String newA1 = "123";
        String newA2 = "456";
        String newA3 = "789";
        // String newA4 = "32";

        String singleQuestion = "";
        
        List<String> listOfSurveys;
        try {
            listOfSurveys = Files.readAllLines(Paths.get(fileName));
            for (int i = 0; i < listOfSurveys.size(); i++){
                String[] e1 = listOfSurveys.get(i).split("␜");
                List<String> surveyDetails = Arrays.asList(e1);
                if (surveyID.equals(surveyDetails.get(0))){
                    String[] e2 = surveyDetails.get(4).split("␝");
                    List<String> questionList = Arrays.asList(e2);
                    for (int i3 = 0; i3 < questionList.size(); i3++){
                        if (i3 == ((qNo * 2) + 1)){
                            String[] e3 = questionList.get(i3).split("␞");
                            List<String> questionDetails = Arrays.asList(e3);
                            //set new question and answers here
                            questionDetails.set(0, newQ);
                            questionDetails.set(1, newA1);
                            questionDetails.set(2, newA2);
                            questionDetails.set(3, newA3);
                            // questionDetails.set(4, newA4);
                            for (int i4 = 0; i4 < questionDetails.size(); i4++){
                                singleQuestion = singleQuestion + questionDetails.get(i4);
                                if (i4 != questionDetails.size() - 1){
                                    singleQuestion = singleQuestion +  "␞";
                                }
                            }
                            System.out.println(questionDetails);
                            System.out.println(singleQuestion);
                            questionList.set(i3, singleQuestion);
                            String fullQuestions = "";
                            for (int i4 = 0; i4 < questionList.size(); i4++){
                                fullQuestions = fullQuestions + questionList.get(i4);
                                if (i4 != (questionList.size() - 1)) {
                                    fullQuestions = fullQuestions + "␝";
                                }
                            }
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
                }
            }
        }  catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }

    }

    public static void DeleteQuestion(String surveyID){
        String fileName = "src/Text Files/Surveys.txt";

        // qNo = 0 means question 1, qNo = 1 means question 2 etc...
        int qNo = 0;
        
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
                        if (i4 != qNo * 2 && i4 != (qNo * 2) + 1){
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
    }

    public static void UpdateFile(String fileName, int lineNumber, String newRecord) throws IOException {
        List<String> listOfSurveys;
        try {
            listOfSurveys = Files.readAllLines(Paths.get(fileName));
            if (lineNumber >= listOfSurveys.size()){
                listOfSurveys.add(newRecord);
            } else {
                listOfSurveys.set(lineNumber, newRecord);
            }
            File file = new File(fileName);
            FileWriter fileWritter = new FileWriter(file, false);        
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            for (int i = 0; i < listOfSurveys.size(); i++){
                if (i != 0){
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


    public static void ViewResponses(String surveyID){

        List<String> individualResponses = new ArrayList<String>();
        List<String> responseDetails = new ArrayList<String>();

        List<String> allResponses;
        try {
            allResponses = Files.readAllLines(Paths.get("src/Text Files/Responses.txt"));
            for ( int i = 0; i < allResponses.size(); i++){
                if (surveyID.equals(allResponses.get(i).substring(0, 2))){
                    individualResponses.add(allResponses.get(i));
                }
            }
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }

        HashMap<String, Integer> mcqMap = new HashMap<String, Integer>();
        HashMap<String, String> openEndedMap = new HashMap<String, String>();

        List<String> listOfSurveys;
        try {
            listOfSurveys = Files.readAllLines(Paths.get("src/Text Files/Surveys.txt"));
            for (int i = 0; i < listOfSurveys.size(); i++){
                String[] e1 = listOfSurveys.get(i).split("␜");
                List<String> surveyDetails = Arrays.asList(e1);
                if (surveyID.equals(surveyDetails.get(0))){
                    String[] e2 = surveyDetails.get(4).split("␝");
                    List<String> questionList = Arrays.asList(e2);
                    for (int i3 = 0; i3 < questionList.size(); i3++){
                        if (i3 % 2 == 0){
                            System.out.println("Question Type: " + questionList.get(i3));
                        } else {
                            String[] e3 = questionList.get(i3).split("␞");
                            List<String> questionDetails = Arrays.asList(e3);
                            System.out.println("Question " + ((i3 / 2) + 1) + " : " + questionDetails.get(0));
                            int a1Count = 0;
                            int a2Count = 0;
                            int a3Count = 0;
                            int a4Count = 0;
                            for (int i4 = 0; i4 < individualResponses.size(); i4++){
                                String[] e4 = individualResponses.get(i4).split("␜");
                                responseDetails = Arrays.asList(e4);
                                switch (responseDetails.get((i3 / 2) + 1)) {
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
                                        openEndedMap.put("Q" + ((i3 / 2) + 1) + "A" + i4, responseDetails.get((i3 / 2) + 1));
                                        break;
                                }
                            }
                            mcqMap.put("Q" + ((i3 / 2) + 1) + "A1", a1Count);
                            mcqMap.put("Q" + ((i3 / 2) + 1) + "A2", a2Count);
                            mcqMap.put("Q" + ((i3 / 2) + 1) + "A3", a3Count);
                            mcqMap.put("Q" + ((i3 / 2) + 1) + "A4", a4Count);
                            if (questionList.get(i3 - 1).equals("Open-ended")){
                                for(int i4 = 0; i4 < individualResponses.size(); i4++){
                                    System.out.println("Answer " + i4 + ": " + openEndedMap.get("Q" + ((i3 / 2) + 1) + "A" + i4));
                                }
                            }
                            for (int i4 = 1; i4 < questionDetails.size(); i4++){
                                int noOfResponses = mcqMap.get("Q" + ((i3 / 2) + 1) + "A" + i4);
                                int totalReponses = individualResponses.size();
                                double responsePercentage = ((double)noOfResponses / (double)totalReponses) * 100;
                                System.out.println("Answer " + i4 + ": " + questionDetails.get(i4) + " - " + noOfResponses + "/" + totalReponses + " or " + responsePercentage + "%");
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

    public static void SaveResponses(String surveyID){
        String answer1 = "1";
        String answer2 = "2";
        String answer3 = "I am third Y/O";



        List<String> listOfSurveys;
        try {
            listOfSurveys = Files.readAllLines(Paths.get("src/Text Files/Surveys.txt"));
            for (int i = 0; i < listOfSurveys.size(); i++){
                String[] e1 = listOfSurveys.get(i).split("␜");
                List<String> surveyDetails = Arrays.asList(e1);
                if (surveyID.equals(surveyDetails.get(0))){
                    String[] e2 = surveyDetails.get(4).split("␝");
                    List<String> questionList = Arrays.asList(e2);
                    for (int i3 = 0; i3 < questionList.size(); i3++){
                        if (i3 % 2 == 0){
                            System.out.println("Question Type: " + questionList.get(i3));
                        } else {
                            String[] e3 = questionList.get(i3).split("␞");
                            List<String> questionDetails = Arrays.asList(e3);
                            System.out.println("Question " + ((i3 / 2) + 1) + " : " + questionDetails.get(0));
                            for (int i4 = 1; i4 < questionDetails.size(); i4++){
                                System.out.println("Answer " + i4 + ": " + questionDetails.get(i4));
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }

        List<String> answerList = new ArrayList<String>();
        List<String> listOfResponses;
        answerList.add(surveyID);
        answerList.add(answer1);
        answerList.add(answer2);
        answerList.add(answer3);
        String answerString = "";
        boolean isEmpty = false;
        for(int i = 0; i < answerList.size(); i++){
            if(answerList.get(i).equals("")){
                System.out.println("Question " + i + " has not been answered");
                isEmpty = true;
                break;
            } else{
                answerString = answerString + answerList.get(i);
                if (i != (answerList.size() - 1)) {
                    answerString = answerString + "␜";
                }
            }
        }
        if (!isEmpty) {
            try {
                listOfResponses = Files.readAllLines(Paths.get("src/Text Files/Responses.txt"));
                System.out.println(answerString);
                UpdateFile("src/Text Files/Responses.txt", listOfResponses.size() + 1, answerString);
            } catch (IOException e) {
                System.out.println("IO Exception");
                e.printStackTrace();
            }
        }
    }

    public static void ViewQuestions(String surveyID, int qNo){
        String fileName = "src/Text Files/Surveys.txt";

        List<String> listOfSurveys;
        try {
            listOfSurveys = Files.readAllLines(Paths.get(fileName));
            for (int i = 0; i < listOfSurveys.size(); i++){
                String[] e1 = listOfSurveys.get(i).split("␜");
                List<String> surveyDetails = Arrays.asList(e1);
                if (surveyID.equals(surveyDetails.get(0))){
                    String[] e2 = surveyDetails.get(4).split("␝");
                    List<String> questionList = Arrays.asList(e2);
                    System.out.println("Question " + qNo);
                    System.out.println("Quetion Type: " + questionList.get((qNo - 1) * 2));
                    String[] e3 = questionList.get(((qNo - 1) * 2) + 1).split("␞");
                            List<String> questionDetails = Arrays.asList(e3);
                            System.out.println("Question: " + questionDetails.get(0));
                            for (int i2 = 1; i2 < questionDetails.size(); i2++){
                                System.out.println("Answer: " + i2 + ": " + questionDetails.get(i2));
                            }

                }
            }
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }
    }

}
