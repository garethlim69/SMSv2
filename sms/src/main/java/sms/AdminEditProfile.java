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

import Objects.Admin;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class AdminEditProfile implements Initializable{

    @FXML private TextField txtUsername;
    @FXML private TextField txtEmail;
    @FXML private TextField txtPassword;
    @FXML private TextField txtPassword2;

    static String adminUsername;
    String adminID = "";

    public static void uniqueKey(String username){
        adminUsername = username;
    }

    @FXML
    private void switchAdminDashboard() throws IOException {
        App.setRoot("adminDashboard");
    }

    public void Discard() {
        txtUsername.clear();
        txtEmail.clear();
        txtPassword.clear();
        txtPassword2.clear();
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        String fileName = "target/classes/Text Files/Admin.txt";
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

        for (int i = 0; i < adminList.size(); i++){
            if (adminUsername.equals(adminList.get(i).getUsername())) {
                adminID = adminList.get(i).getAdminID();
                txtUsername.setText(adminList.get(i).getUsername());
                txtEmail.setText(adminList.get(i).getEmail());
                txtPassword.setText(adminList.get(i).getPassword());
                txtPassword2.setText(adminList.get(i).getPassword());
            }
        }
    }

    public void AdminEditProfile(){
        //Data from textFields
        String enteredUsername = txtUsername.getText();
        String enteredEmail = txtEmail.getText();
        String enteredPassword = txtPassword.getText();
        String enteredRepeatPassword = txtPassword2.getText();

        String fileName = "target/classes/Text Files/Admin.txt";
        ArrayList<Admin> adminList = new ArrayList<Admin>();
        int flag = 0;
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

        for (int i = 0; i < adminList.size(); i++){
            //searches for respective Admin object
            if (adminID.equals(adminList.get(i).getAdminID())){
                //checks for changes between existing and entered credentials
                if (adminList.get(i).getUsername().equals(enteredUsername) && adminList.get(i).getEmail().equals(enteredEmail) && encryptPassword(enteredPassword).equals(adminList.get(i).getPassword())){
                    flag = 1;
                    System.out.println("No Changes to Save.");
                    break;
                } else {
                    for (int i2 = 0; i2 < adminList.size(); i2++){
                        //checks for existing username and email
                        if (enteredUsername.equals(adminList.get(i2).getUsername()) || enteredEmail.equals(adminList.get(i2).getEmail())) {
                            if (!enteredUsername.equals(adminList.get(i).getUsername()) || !enteredEmail.equals(adminList.get(i).getEmail())){
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
                    //update Admin object
                    adminList.get(i).setUsername(enteredUsername);
                    adminList.get(i).setEmail(enteredEmail);
                    adminList.get(i).setPassword(encryptPassword(enteredPassword));

                    //write details to Admin.txt
                    try {
                        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName, false));
                        os.writeObject(adminList);
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


}
