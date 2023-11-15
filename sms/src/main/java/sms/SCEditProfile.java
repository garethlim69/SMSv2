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

import Objects.SurveyCreator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class SCEditProfile implements Initializable{
    @FXML private TextField txtUsername;
    @FXML private TextField txtEmail;
    @FXML private TextField txtPassword;
    @FXML private TextField txtPassword2;
    @FXML private TextField txtFirstName;
    @FXML private TextField txtLastName;
    @FXML private TextField txtPhoneNumber;
    @FXML private TextField txtAge;
    @FXML private TextField txtGender;      

    static String scUsername;

    String scID = "";

    int flag = 0;

    ArrayList<SurveyCreator> scInitialList = new ArrayList<SurveyCreator>();
    String oldPassword = "";

    public void UndoChanges(){
        txtUsername.setText(scInitialList.get(flag).getCreatorName());
        txtEmail.setText(scInitialList.get(flag).getEmail());
        txtPassword.setText(scInitialList.get(flag).getPassword());
        txtPassword2.setText(scInitialList.get(flag).getPassword());
        txtFirstName.setText(scInitialList.get(flag).getFirstName());
        txtLastName.setText(scInitialList.get(flag).getLastName());
        txtPhoneNumber.setText(scInitialList.get(flag).getContactNumber());
        txtAge.setText(String.valueOf(scInitialList.get(flag).getAge()));
        txtGender.setText(scInitialList.get(flag).getGender());
    }

    public void SCUpdateProfile(){
        
        //Data from textFields
        String enteredCreatorName = txtUsername.getText();
        String enteredPassword = txtPassword.getText();
        String enteredRepeatPassword = txtPassword2.getText();
        String enteredFirstName = txtFirstName.getText();
        String enteredLastName = txtLastName.getText();
        String enteredEmail = txtEmail.getText();
        String enteredContactNumber = txtPhoneNumber.getText();
        int enteredAge = Integer.parseInt(txtAge.getText());
        String enteredGender = txtGender.getText();


        String fileName = "target/classes/Text Files/SurveyCreator.txt";
        ArrayList<SurveyCreator> scList = new ArrayList<SurveyCreator>();
        int flag = 0;
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
            //searches for respective Admin object
            if (scID.equals(scList.get(i).getScID())){
                //checks for changes between existing and entered credentials
                if (scList.get(i).getCreatorName().equals(enteredCreatorName) &&
                enteredPassword.equals(scList.get(i).getPassword()) &&
                scList.get(i).getEmail().equals(enteredEmail) &&
                scList.get(i).getFirstName().equals(enteredFirstName) &&
                scList.get(i).getLastName().equals(enteredLastName) &&
                scList.get(i).getContactNumber().equals(enteredContactNumber) &&
                scList.get(i).getAge() == (Integer.valueOf(enteredAge)) &&
                scList.get(i).getGender().equals(enteredGender)
                ){
                    flag = 1;
                    System.out.println("No Changes to Save.");
                    break;
                } else {
                    for (int i2 = 0; i2 < scList.size(); i2++){
                        //checks for existing username and email
                        if (enteredCreatorName.equals(scList.get(i2).getCreatorName()) || enteredEmail.equals(scList.get(i2).getEmail())) {
                            if (!enteredCreatorName.equals(scList.get(i).getCreatorName()) || !enteredEmail.equals(scList.get(i).getEmail())){
                                flag = 1;
                                System.out.println("Username or Email Already Exists. Please Try Again.");
                                break;
                            }
                        } else {
                            //checks if password and re-enter password are the same
                            if (!enteredPassword.equals(enteredRepeatPassword)){
                                flag = 1;
                                System.out.println("Passwords Do Not Match.");
                                break;
                            }
                        }
                    }
                }
                if (flag == 0){

                    if (enteredPassword.equals(oldPassword)) {
                        scList.get(i).setCreatorName(enteredCreatorName);
                        scList.get(i).setEmail(enteredEmail);
                        scList.get(i).setPassword(enteredPassword);
                        scList.get(i).setFirstName(enteredFirstName);
                        scList.get(i).setLastName(enteredLastName);
                        scList.get(i).setContactNumber(enteredContactNumber);
                        scList.get(i).setAge(enteredAge);
                        scList.get(i).setGender(enteredGender);
                    }else {
                        scList.get(i).setCreatorName(enteredCreatorName);
                        scList.get(i).setEmail(enteredEmail);
                        scList.get(i).setPassword(encryptPassword(enteredRepeatPassword));
                        scList.get(i).setFirstName(enteredFirstName);
                        scList.get(i).setLastName(enteredLastName);
                        scList.get(i).setContactNumber(enteredContactNumber);
                        scList.get(i).setAge(enteredAge);
                        scList.get(i).setGender(enteredGender);
                    }
                    //update Admin object
                    

                    //write details to Admin.txt
                    try {
                        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName, false));
                        os.writeObject(scList);
                        os.close();
                        System.out.println("Details Updated Successfully.");
                        } catch (IOException e1){
                        System.out.println("IOException");
                    }
                }   
                break;
            }
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
    private void switchSCDashboard() throws IOException {
        App.setRoot("scDashboard");
    }

    public static void uniqueKey(String username){
        scUsername = username;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        String fileName = "target/classes/Text Files/SurveyCreator.txt";
        ArrayList<SurveyCreator> scList = new ArrayList<SurveyCreator>();
        ObjectInputStream is;
        try {
            is = new ObjectInputStream(new FileInputStream(fileName));
            try {
                scList = (ArrayList) is.readObject();
                scInitialList = scList;
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
            if (scUsername.equals(scList.get(i).getCreatorName())) {
                flag = i;
                scID = scList.get(i).getScID();
                txtUsername.setText(scList.get(i).getCreatorName());
                txtEmail.setText(scList.get(i).getEmail());
                txtPassword.setText(scList.get(i).getPassword());
                txtPassword2.setText(scList.get(i).getPassword());
                txtFirstName.setText(scList.get(i).getFirstName());
                txtLastName.setText(scList.get(i).getLastName());
                txtPhoneNumber.setText(scList.get(i).getContactNumber());
                txtAge.setText(String.valueOf(scList.get(i).getAge()));
                txtGender.setText(scList.get(i).getGender());
                oldPassword = scList.get(i).getPassword();
            }
        }
    }
}
    

