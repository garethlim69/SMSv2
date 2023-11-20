package sms;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class RespondentViewSurvey implements Initializable {

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
    private Button btnAnswer1;
    @FXML
    private Button btnAnswer2;
    @FXML
    private Button btnAnswer3;
    @FXML
    private Button btnAnswer4;
    @FXML
    private Button btnAnswer5;

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

    @FXML
    private void switchMainMenu() throws IOException {
        App.setRoot("mainMenu");
    }

    public void PreviousPage() {
        pageNo--;
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

    @FXML
    public void ViewSurveys(int pageNo) {
        String fileName = "src/main/java/Text Files/Surveys.txt";
        lblSID2.setText("");
        lblTitle2.setText("");
        lblSCID2.setText("");
        lblNoOfQ2.setText("");
        btnAnswer2.setVisible(false);

        lblSID3.setText("");
        lblTitle3.setText("");
        lblSCID3.setText("");
        lblNoOfQ3.setText("");
        btnAnswer3.setVisible(false);

        lblSID4.setText("");
        lblTitle4.setText("");
        lblSCID4.setText("");
        lblNoOfQ4.setText("");
        btnAnswer4.setVisible(false);

        lblSID5.setText("");
        lblTitle5.setText("");
        lblSCID5.setText("");
        lblNoOfQ5.setText("");
        btnAnswer5.setVisible(false);

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
            if (surveyDetailsMap.get("S" + index + "NoOfQ").equals("0")) {
                btnAnswer1.setVisible(false);
            } else {
                lblSID1.setText(surveyDetailsMap.get("S" + index + "SID"));
                lblTitle1.setText(surveyDetailsMap.get("S" + index + "Title"));
                lblSCID1.setText(surveyDetailsMap.get("S" + index + "SCID"));
                lblNoOfQ1.setText(surveyDetailsMap.get("S" + index + "NoOfQ"));
                btnAnswer1.setVisible(true);
            }
            if (noOfSurveys % 5 > 1 || noOfSurveys % 5 == 0) {
                if (surveyDetailsMap.get("S" + (index + 1) + "NoOfQ").equals("0")) {
                    btnAnswer2.setVisible(false);
                } else {
                    lblSID2.setText(surveyDetailsMap.get("S" + (index + 1) + "SID"));
                    lblTitle2.setText(surveyDetailsMap.get("S" + (index + 1) + "Title"));
                    lblSCID2.setText(surveyDetailsMap.get("S" + (index + 1) + "SCID"));
                    lblNoOfQ2.setText(surveyDetailsMap.get("S" + (index + 1) + "NoOfQ"));
                    btnAnswer2.setVisible(true);
                }
                if (noOfSurveys % 5 > 2 || noOfSurveys % 5 == 0) {
                    if (surveyDetailsMap.get("S" + (index + 2) + "NoOfQ").equals("0")) {
                        btnAnswer3.setVisible(false);
                    } else {
                        lblSID3.setText(surveyDetailsMap.get("S" + (index + 2) + "SID"));
                        lblTitle3.setText(surveyDetailsMap.get("S" + (index + 2) + "Title"));
                        lblSCID3.setText(surveyDetailsMap.get("S" + (index + 2) + "SCID"));
                        lblNoOfQ3.setText(surveyDetailsMap.get("S" + (index + 2) + "NoOfQ"));
                        btnAnswer3.setVisible(true);
                    }
                    if (noOfSurveys % 5 > 3 || noOfSurveys % 5 == 0) {
                        if (surveyDetailsMap.get("S" + (index + 3) + "NoOfQ").equals("0")) {
                            btnAnswer4.setVisible(false);
                        } else {
                            lblSID4.setText(surveyDetailsMap.get("S" + (index + 3) + "SID"));
                            lblTitle4.setText(surveyDetailsMap.get("S" + (index + 3) + "Title"));
                            lblSCID4.setText(surveyDetailsMap.get("S" + (index + 3) + "SCID"));
                            lblNoOfQ4.setText(surveyDetailsMap.get("S" + (index + 3) + "NoOfQ"));
                            btnAnswer4.setVisible(true);
                        }
                        if (noOfSurveys % 5 == 0) {
                            if (surveyDetailsMap.get("S" + (index + 4) + "NoOfQ").equals("0")) {
                                btnAnswer5.setVisible(false);
                            } else {
                                lblSID5.setText(surveyDetailsMap.get("S" + (index + 4) + "SID"));
                                lblTitle5.setText(surveyDetailsMap.get("S" + (index + 4) + "Title"));
                                lblSCID5.setText(surveyDetailsMap.get("S" + (index + 4) + "SCID"));
                                lblNoOfQ5.setText(surveyDetailsMap.get("S" + (index + 4) + "NoOfQ"));
                                btnAnswer5.setVisible(true);
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

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        pageNo = 1;
        ViewSurveys(pageNo);
    }

    @FXML
    private void AnswerButton1(){
        System.out.println("Answering " + surveyDetailsMap.get("S" + index + "SID"));
    }

    @FXML
    private void AnswerButton2(){
        System.out.println("Answering " + surveyDetailsMap.get("S" + (index + 1) + "SID"));
    }

    @FXML
    private void AnswerButton3(){
        System.out.println("Answering " + surveyDetailsMap.get("S" + (index + 2) + "SID"));
    }

    @FXML
    private void AnswerButton4(){
        System.out.println("Answering " + surveyDetailsMap.get("S" + (index + 3) + "SID"));
    }

    @FXML
    private void AnswerButton5(){
        System.out.println("Answering " + surveyDetailsMap.get("S" + (index + 4) + "SID"));
    }
}
