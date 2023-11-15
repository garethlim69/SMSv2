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

import Objects.Admin;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class RegisterNewAdmin {

    @FXML private TextField txtUsername;
    @FXML private TextField txtPassword;
    @FXML private TextField txtPassword2;
    @FXML private TextField txtEmail;

    public void RegisterAdmin(){
        String fileName = "target/classes/Text Files/Admin.txt";
        ArrayList<Admin> adminList = new ArrayList<Admin>();
        int index;
        boolean isEmpty;
        int flag = 0;

        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String password2 = txtPassword2.getText();
        String email = txtEmail.getText();

        if (username.isEmpty() || password.isEmpty() || password2.isEmpty() || email.isEmpty()) {
            System.out.println("Required Field is Empty");
        }else{
            if (!password.equals(password2)) {
            System.out.println("Password Does Not Match");
            } else {
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
                    index =  Integer.valueOf(adminList.get(adminList.size() - 1).getAdminID().substring(1));
                    index++;
                }
        
                String adminID = "A" + index;

                if (!isEmpty){
                    for (int i = 0; i < adminList.size(); i++){
                        if (username.equals(adminList.get(i).getUsername()) || email.equals(adminList.get(i).getEmail())){
                            flag = 1;
                        }
                    }
                } else {
                    flag = 0;
                }
                    
                if (flag == 0) {
                    //encrypt password
                    String encryptedPassword = encryptPassword(password);
                    Admin admin = new Admin(adminID, username, encryptedPassword, email);

                    adminList.add(admin);

                    //write information to Admin.txt file
                    try {
                    ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName, false));
                    os.writeObject(adminList);
                    os.close();
                    System.out.println("Registered Successfully");
                    } catch (IOException e1){
                    System.out.println("IOException");
                    }    
                } else {
                    System.out.println("Username or Email Already Exists. Please Try Another.");
                }

            }
        }

    }

    @FXML
    private void switchAdminDashboard() throws IOException {
        App.setRoot("adminDashboard");
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

    public void clear(){
        txtUsername.clear();
        txtPassword.clear();
        txtPassword2.clear();
        txtEmail.clear();
    }
}
