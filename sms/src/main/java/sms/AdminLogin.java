package sms;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import Objects.Admin;


public class AdminLogin {
    @FXML private TextField txtUsername;
    @FXML private TextField txtPassword;

    @FXML
    private void switchMainMenu() throws IOException {
        App.setRoot("mainMenu");
    }

    @FXML
    private void switchAdminDashboard() throws IOException {
        String fileName = "src/main/java/Text Files/Admin.txt";
        ArrayList<Admin> adminList = new ArrayList<Admin>();
        ObjectInputStream is;
        try {
            is = new ObjectInputStream(new FileInputStream(fileName));
            try {
                adminList = (ArrayList) is.readObject();
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

        int i2 = 0;
        for (int i = 0; i < adminList.size(); i++){
            if (enteredUsername.equals(adminList.get(i).getUsername()) && encryptPassword(enteredPassword).equals(adminList.get(i).getPassword())){
                AdminDashboard.uniqueKey(adminList.get(i).getUsername());
                App.setRoot("adminDashboard");
                break;
            } else {
                i2++;
            }
        }
        if (i2 == adminList.size()){
            JOptionPane.showMessageDialog(null, "Incorrect Username or Password Entered", "Incorrect Credentials", JOptionPane.WARNING_MESSAGE);
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
