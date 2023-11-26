package sms;

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

import javax.swing.*;

import javafx.fxml.Initializable;
import Objects.SurveyCreator;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ManageSCProfiles extends AdminFunction implements Initializable {

    @FXML
    private TextField txtUsername;
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
    private TextField txtGender;

    @FXML
    private Label lblPageNo;
    @FXML
    private Button btnNext;
    @FXML
    private Button btnPrev;

    String scID = "";
    int pageNo;

    public void PreviousPage() {
        pageNo--;
        ViewSCProfile(pageNo);
    }

    public void NextPage() {
        pageNo++;
        ViewSCProfile(pageNo);
    }

    @Override
    public void ViewSCProfile(int pageNo) {
        // deserializes content of SurveyCreator.txt to ArrayList<SurveyCreator>
        String fileName = "src/main/java/Text Files/SurveyCreator.txt";
        ArrayList<SurveyCreator> scList = new ArrayList<SurveyCreator>();
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
        lblPageNo.setText("Page " + pageNo + "/" + scList.size());
        if (pageNo == scList.size()) {
            btnNext.setDisable(true);
        } else {
            btnNext.setDisable(false);
        }
        if (pageNo == 1) {
            btnPrev.setDisable(true);
        } else {
            btnPrev.setDisable(false);
        }
        // sets details of survey creator to text fields
        txtUsername.setText(scList.get(pageNo - 1).getCreatorName());
        txtFirstName.setText(scList.get(pageNo - 1).getFirstName());
        txtLastName.setText(scList.get(pageNo - 1).getLastName());
        txtEmail.setText(scList.get(pageNo - 1).getEmail());
        txtPhoneNumber.setText(scList.get(pageNo - 1).getContactNumber());
        txtAge.setText(String.valueOf(scList.get(pageNo - 1).getAge()));
        txtGender.setText(scList.get(pageNo - 1).getGender());
    }

    @Override
    public void DeleteSCProfile() {
        // confirmation popout
        var choice = JOptionPane.showOptionDialog(null, "Are you sure you want to delete?", "Message", 0, 2, null, null,
                null);

        if (choice == 0) {
            // deserializes content of SurveyCreator.txt to ArrayList<SurveyCreator>
            String fileName = "src/main/java/Text Files/SurveyCreator.txt";
            ArrayList<SurveyCreator> scList = new ArrayList<SurveyCreator>();
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
            // removes respective SurveyCreator object from scList
            scList.remove(pageNo - 1);
            // writes new scList to SurveyCreator.txt
            try {
                ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName, false));
                os.writeObject(scList);
                os.close();
                JOptionPane.showMessageDialog(null, "Account Successfully Deleted.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                initialize(null, null);
            } catch (IOException e1) {
                System.out.println("IOException");
                e1.printStackTrace();
            }
        }
    }

    public void ResetSCPw() {
        // deserializes content of SurveyCreator.txt to ArrayList<SurveyCreator>
        String fileName = "src/main/java/Text Files/SurveyCreator.txt";
        ArrayList<SurveyCreator> scList = new ArrayList<SurveyCreator>();
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
        // resets password to "password"
        scList.get(pageNo - 1).setPassword(encryptPassword("password"));
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName, false));
            os.writeObject(scList);
            os.close();
            JOptionPane.showMessageDialog(null, "Password Reset Successfully.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e1) {
            System.out.println("IOException");
        }
    }

    public static String encryptPassword(String password) {
        // encrypt password use MD5 algorithm
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

    @FXML
    private void switchAdminDashboard() throws IOException {
        App.setRoot("AdminDashboard");
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // sets all text fiels to non-editable
        txtUsername.setEditable(false);
        txtFirstName.setEditable(false);
        txtLastName.setEditable(false);
        txtEmail.setEditable(false);
        txtPhoneNumber.setEditable(false);
        txtAge.setEditable(false);
        txtGender.setEditable(false);

        pageNo = 1;
        ViewSCProfile(pageNo);
    }

    @Override
    void AdminEditProfile() {
        throw new UnsupportedOperationException("Function Not In Use");
    }

    @Override
    void ViewSurveys(int pageNo) {
        throw new UnsupportedOperationException("Function Not In Use");
    }


    @Override
    void RegisterAdmin() {
        throw new UnsupportedOperationException("Function Not In Use");
    }

}
