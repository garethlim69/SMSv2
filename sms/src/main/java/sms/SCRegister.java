package sms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import Objects.SurveyCreator;
import javafx.fxml.FXML;
import javafx.scene.control.*;



public class SCRegister {

    @FXML private TextField txtUsername;
    @FXML private TextField txtPassword;
    @FXML private TextField txtPassword2;
    @FXML private TextField txtFirstName;
    @FXML private TextField txtLastName;
    @FXML private TextField txtEmail;
    @FXML private TextField txtPhoneNumber;
    @FXML private TextField txtAge;
    @FXML private TextField txtGender;

    @FXML
    private void switchSCLogin() throws IOException {
        App.setRoot("scLogin");
    }

    public void Clear(){
        txtUsername.clear();
        txtPassword.clear();
        txtPassword2.clear();
        txtFirstName.clear();
        txtLastName.clear();
        txtEmail.clear();
        txtPhoneNumber.clear();
        txtAge.clear();
        txtGender.clear();
    }

    public void RegisterSC(){        
        String fileName = "target/classes/Text Files/SurveyCreator.txt";
        ArrayList<SurveyCreator> scList = new ArrayList<SurveyCreator>();
        int index;
        boolean isEmpty;
        int flag = 0;
        String creatorName = txtUsername.getText();
        String password = txtPassword.getText();
        String email = txtEmail.getText();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String contactNumber = txtPhoneNumber.getText();
        int Age = 0;
        String gender = txtGender.getText();

        File file = new File(fileName);
        if (file.length() == 0){
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
            index =  Integer.valueOf(scList.get(scList.size() - 1).getScID().substring(2));
            index++;
        }
 
        String scID = "SC" + index;

        if (!isEmpty){
            for (int i = 0; i < scList.size(); i++){
                if (creatorName.equals(scList.get(i).getCreatorName()) || email.equals(scList.get(i).getEmail()) || contactNumber.equals(scList.get(i).getContactNumber())){
                    flag = 1;
                }
            }
            if (!contactNumber.matches("[0-9]+")){
                flag = 2;
            }
            try {
                Age = Integer.parseInt(txtAge.getText());
            } catch (Exception e) {
                flag = 3;
            }
        } else {
            flag = 0;
        }
            
        if (flag == 0) {
            //encrypt password
            String encryptedPassword = encryptPassword(password);
            SurveyCreator surveyCreator = new SurveyCreator(scID, creatorName, encryptedPassword, firstName, lastName, email, contactNumber, Age, gender);

            scList.add(surveyCreator);

            //write information to Admin.txt file
            try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName, false));
            os.writeObject(scList);
            os.close();
            System.out.println("Registered Successfully");
            txtUsername.clear();
            txtPassword.clear();
            txtPassword2.clear();
            txtFirstName.clear();
            txtLastName.clear();
            txtEmail.clear();
            txtPhoneNumber.clear();
            txtAge.clear();
            txtGender.clear();
            } catch (IOException e1){
            System.out.println("IOException");
            e1.printStackTrace();
            }    
        } else if (flag == 1) {
            System.out.println("Username, E-mail or Contact Number Already Exists. Please Try Another.");
        } else if (flag == 2) {
            System.out.println("Contact Number Must Contain Only Numbers.");
        }else if (flag == 3) {
            System.out.println("Age Must Contain Only Numbers");
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
}
