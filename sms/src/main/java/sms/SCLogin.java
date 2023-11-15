package sms;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import Objects.SurveyCreator;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SCLogin {

    @FXML private TextField txtUsername;
    @FXML private TextField txtPassword;
    
    public void LoginSC() throws IOException{
        String fileName = "target/classes/Text Files/SurveyCreator.txt";
        ArrayList<SurveyCreator> scList = new ArrayList<SurveyCreator>();
        boolean isLoginSuccess = false;

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

        //entered fields
        String enteredUsername = txtUsername.getText();
        String enteredPassword = txtPassword.getText();

        for (int i = 0; i < scList.size(); i++){
            if (enteredUsername.equals(scList.get(i).getCreatorName()) && encryptPassword(enteredPassword).equals(scList.get(i).getPassword())){
                isLoginSuccess = true;
                SCDasboard.SCID(scList.get(i).getScID());
                System.out.println(scList.get(i).getCreatorName());
            }
        }
        if (isLoginSuccess) {
            SCDasboard.uniqueKey(enteredUsername);
            App.setRoot("scDashboard");
            System.out.println("Success");
        } else {
            System.out.println("Failed");
        }
    }
    
    @FXML
    private void switchMainMenu() throws IOException {
        App.setRoot("mainMenu");
    }

    @FXML
    private void switchSCRegister() throws IOException {
        App.setRoot("scRegister");
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
