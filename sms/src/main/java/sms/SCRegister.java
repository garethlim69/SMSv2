package sms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import Objects.SurveyCreator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class SCRegister implements Initializable {

    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtPassword2;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPhoneNumber;
    @FXML
    private TextField txtAge;
    @FXML
    private ChoiceBox cboxGender;

    @FXML
    private void switchSCLogin() throws IOException {
        App.setRoot("scLogin");
    }

    public void Clear() {
        // empties all text fields
        int input = JOptionPane.showConfirmDialog(null, "Discard All Changes?", "Discard Changes?",
                JOptionPane.YES_NO_OPTION);
        if (input == 0) {
            txtUsername.clear();
            txtPassword.clear();
            txtPassword2.clear();
            txtFirstName.clear();
            txtLastName.clear();
            txtEmail.clear();
            txtPhoneNumber.clear();
            txtAge.clear();
            cboxGender.valueProperty().set(null);
        }

    }

    public void RegisterSC() {
        String fileName = "src/main/java/Text Files/SurveyCreator.txt";
        ArrayList<SurveyCreator> scList = new ArrayList<SurveyCreator>();
        int index;
        boolean isEmpty;
        int flag = 0;

        // retrieves information from text fields
        String creatorName = txtUsername.getText();
        String password = txtPassword.getText();
        String email = txtEmail.getText();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String contactNumber = txtPhoneNumber.getText();
        int Age = 0;
        String gender = cboxGender.getValue().toString();

        // checks if file is empty, if not, deserializes content of SurveyCreator.txt to ArrayList<SurveyCreator>
        File file = new File(fileName);
        if (file.length() == 0) {
            isEmpty = true;
            index = 1;
        } else {
            isEmpty = false;
            ObjectInputStream is;
            try {
                is = new ObjectInputStream(new FileInputStream(fileName));
                try {
                    scList = (ArrayList) is.readObject();
                } catch (ClassNotFoundException e1) {
                    System.out.println("Class Not Found");
                    e1.printStackTrace();
                }
                is.close();
            } catch (FileNotFoundException e1) {
                System.out.println("File Not Found");
                e1.printStackTrace();
            } catch (IOException e1) {
                System.out.println("IO Exception");
                e1.printStackTrace();
            }
            // generates SCID
            index = Integer.valueOf(scList.get(scList.size() - 1).getScID().substring(2));
            index++;
        }

        String scID = "SC" + index;

        // if SurveyCreator.txt is not empty, search file for matching existing credentials
        if (!isEmpty) {
            for (int i = 0; i < scList.size(); i++) {
                if (creatorName.equals(scList.get(i).getCreatorName()) || email.equals(scList.get(i).getEmail())
                        || contactNumber.equals(scList.get(i).getContactNumber())) {
                    flag = 1;
                }
            }
            // checks if contact number if numbers only
            if (!contactNumber.matches("[0-9]+")) {
                flag = 2;
            }
            // checks if age is number only
            try {
                Age = Integer.parseInt(txtAge.getText());
            } catch (Exception e) {
                flag = 3;
            }
        } else {
            flag = 0;
        }

        // if all validations passed, insert new surveyCreator object into scList
        if (flag == 0) {
            // encrypt password
            String encryptedPassword = encryptPassword(password);
            SurveyCreator surveyCreator = new SurveyCreator(scID, creatorName, encryptedPassword, firstName, lastName,
                    email, contactNumber, Age, gender);

            scList.add(surveyCreator);

            // write information to SurveyCreator.txt file
            try {
                ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName, false));
                os.writeObject(scList);
                os.close();
                JOptionPane.showMessageDialog(null, "Registered Successfully", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                App.setRoot("scLogin");
            } catch (IOException e1) {
                System.out.println("IOException");
                e1.printStackTrace();
            }
        } else if (flag == 1) {
            JOptionPane.showMessageDialog(null, "Username or Email Already Exists. Please Try Again.",
                    "Existing Credentials", JOptionPane.WARNING_MESSAGE);
        } else if (flag == 2) {
            JOptionPane.showMessageDialog(null, "Contact Number Must Contain Only Numbers.", "Invalid Contact Number",
                    JOptionPane.WARNING_MESSAGE);
        } else if (flag == 3) {
            JOptionPane.showMessageDialog(null, "Age Must Contain Only Numbers.", "Invalid Age",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    public static String encryptPassword(String password) {
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

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        cboxGender.getItems().removeAll(cboxGender.getItems());
        cboxGender.getItems().addAll("M", "F");
    }
}
