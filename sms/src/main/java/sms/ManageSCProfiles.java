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

public class ManageSCProfiles implements Initializable{

    @FXML private TextField txtUsername;
    @FXML private TextField txtFirstName;
    @FXML private TextField txtLastName;
    @FXML private TextField txtEmail;
    @FXML private TextField txtPhoneNumber;
    @FXML private TextField txtAge;
    @FXML private TextField txtGender;

    @FXML private Label lblPageNo;
    @FXML private Button btnNext;
    @FXML private Button btnPrev;

    String scID = "";
    int pageNo;

    public void PreviousPage(){
        pageNo--;
        ViewSCProfile(pageNo);
    }

    public void NextPage(){
        pageNo++;
        ViewSCProfile(pageNo);
    }

    public void ViewSCProfile(int pageNo){
        String fileName = "target/classes/Text Files/SurveyCreator.txt";
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
        if (pageNo == scList.size()){
            btnNext.setDisable(true);
        } else {
            btnNext.setDisable(false);
        }
        if (pageNo == 1){
            btnPrev.setDisable(true);
        } else {
            btnPrev.setDisable(false);
        }
        txtUsername.setText(scList.get(pageNo - 1).getCreatorName());
        txtFirstName.setText(scList.get(pageNo - 1).getFirstName());
        txtLastName.setText(scList.get(pageNo - 1).getLastName());
        txtEmail.setText(scList.get(pageNo - 1).getEmail());
        txtPhoneNumber.setText(scList.get(pageNo - 1).getContactNumber());
        txtAge.setText(String.valueOf(scList.get(pageNo - 1).getAge()));
        txtGender.setText(scList.get(pageNo - 1).getGender());
    }

    public void DeleteSCProfile(){

        var choice = JOptionPane.showOptionDialog(null, "Are you sure you want to delete?", "Message", 0, 2, null, null, null);

        if (choice == 0) {
            String fileName = "target/classes/Text Files/SurveyCreator.txt";
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
            for (int i = 0; i < scList.size(); i++){
                if (scID.equals(scList.get(i).getScID())){
                    scList.remove(i);
                }
            }
            try {
                ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName, false));
                os.writeObject(scList);
                os.close();
                System.out.println("Profile Deleted Successfully.");
                } catch (IOException e1){
                System.out.println("IOException");
            }

            //when got next button, use that method. After delete straight display next survey creator's details

            txtUsername.clear();
            txtFirstName.clear();
            txtLastName.clear();
            txtEmail.clear();
            txtPhoneNumber.clear();
            txtAge.clear();
            txtGender.clear();
        }else if (choice == 1) {
            System.out.println("Account not deleted");

        }
    }

    public void ResetSCPw(){
        String fileName = "target/classes/Text Files/SurveyCreator.txt";
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
        for (int i = 0; i < scList.size(); i++){
            if (scID.equals(scList.get(i).getScID())){
                scList.get(i).setPassword(encryptPassword("pw2"));
            }
        }
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName, false));
            os.writeObject(scList);
            os.close();
            System.out.println("Password Reset Successfully.");
            } catch (IOException e1){
            System.out.println("IOException");
        }
    } 

    public static String encryptPassword(String password){
        // String password = "myPassword";
        String encryptedPassword = null;
        try   
        {   
            MessageDigest m = MessageDigest.getInstance("MD5");  
              
            m.update(password.getBytes());  
              
            byte[] bytes = m.digest();  
              
            StringBuilder s = new StringBuilder();  
            for(int i=0; i< bytes.length ;i++)  
            {  
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));  
            }  
              
            encryptedPassword = s.toString();  
        }   
        catch (NoSuchAlgorithmException e)   
        {  
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
        txtUsername.setEditable(false);
        txtFirstName.setEditable(false);
        txtLastName.setEditable(false);
        txtEmail.setEditable(false);
        txtPhoneNumber.setEditable(false);
        txtAge.setEditable(false);
        txtGender.setEditable(false);

        pageNo = 1;
        ViewSCProfile(pageNo);
        // String fileName = "target/classes/Text Files/SurveyCreator.txt";
        // ArrayList<SurveyCreator> scList = new ArrayList<SurveyCreator>();
        // ObjectInputStream is;
        // try {
        //     is = new ObjectInputStream(new FileInputStream(fileName));
        //     try {
        //         scList = (ArrayList) is.readObject();
        //     } catch (ClassNotFoundException e1) {
        //         System.out.println("Class Not Found");
        //         e1.printStackTrace();
        //     }
        //     is.close();
        // } catch (FileNotFoundException e1) {
        //     System.out.println("File Not Found");
        //     e1.printStackTrace();
        // } catch (IOException e1) {
        //     System.out.println("IO Exception");
        //     e1.printStackTrace();
        // }

        // scID = scList.get(0).getScID();


        // txtUsername.setText(scList.get(0).getCreatorName());
        // txtFirstName.setText(scList.get(0).getFirstName());
        // txtLastName.setText(scList.get(0).getLastName());
        // txtEmail.setText(scList.get(0).getEmail());
        // txtPhoneNumber.setText(scList.get(0).getContactNumber());
        // txtAge.setText(String.valueOf(scList.get(0).getAge()));
        // txtGender.setText(scList.get(0).getGender());
    }


}
