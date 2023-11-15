package sms;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ViewResponses {

    public static void ViewResponses(String surveyID){

        List<String> individualResponses = new ArrayList<String>();
        List<String> responseDetails = new ArrayList<String>();

        List<String> allResponses;
        try {
            allResponses = Files.readAllLines(Paths.get("target/classes/Text Files/Responses.txt"));
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
            listOfSurveys = Files.readAllLines(Paths.get("target/classes/Text Files/Surveys.txt"));
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
}
