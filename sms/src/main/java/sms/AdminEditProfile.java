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

import javax.swing.JOptionPane;

import Objects.Admin;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class AdminEditProfile implements Initializable {
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtPassword2;

    static String adminUsername;
    String adminID = "";

    public static void uniqueKey(String username) {
        adminUsername = username;
    }

    @FXML
    private void switchAdminDashboard() throws IOException {
        App.setRoot("adminDashboard");
    }

    public void Discard() {
        int input = JOptionPane.showConfirmDialog(null, "Discard All Changes?", "Discard Changes?",
                JOptionPane.YES_NO_OPTION);
        if (input == 0) {
            initialize(null, null);
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // deserializes content of Admin.txt to ArrayList<Admin>
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

        // displays information of currently logged in Admin
        for (int i = 0; i < adminList.size(); i++) {
            if (adminUsername.equals(adminList.get(i).getUsername())) {
                adminID = adminList.get(i).getAdminID();
                txtUsername.setText(adminList.get(i).getUsername());
                txtEmail.setText(adminList.get(i).getEmail());
                txtPassword.setText(adminList.get(i).getPassword());
                txtPassword2.setText(adminList.get(i).getPassword());
            }
        }
    }

    public void AdminEditProfile() {
        // retrieve data from text fields
        String enteredUsername = txtUsername.getText();
        String enteredEmail = txtEmail.getText();
        String enteredPassword = txtPassword.getText();
        String enteredRepeatPassword = txtPassword2.getText();

        // deserializes content of Admin.txt to ArrayList<Admin>
        String fileName = "src/main/java/Text Files/Admin.txt";
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

        for (int i = 0; i < adminList.size(); i++) {
            // searches for respective Admin object
            if (adminID.equals(adminList.get(i).getAdminID())) {
                // checks for changes between existing and entered credentials
                if (adminList.get(i).getUsername().equals(enteredUsername)
                        && adminList.get(i).getEmail().equals(enteredEmail)
                        && encryptPassword(enteredPassword).equals(adminList.get(i).getPassword())) {
                    flag = 1;
                    JOptionPane.showMessageDialog(null, "No Changes to Save.", "No Changes",
                            JOptionPane.WARNING_MESSAGE);
                    break;
                } else {
                    for (int i2 = 0; i2 < adminList.size(); i2++) {
                        // checks for existing username and email
                        if (enteredUsername.equals(adminList.get(i2).getUsername())
                                || enteredEmail.equals(adminList.get(i2).getEmail())) {
                            if (!enteredUsername.equals(adminList.get(i).getUsername())
                                    || !enteredEmail.equals(adminList.get(i).getEmail())) {
                                flag = 1;
                                JOptionPane.showMessageDialog(null,
                                        "Username or Email Already Exists. Please Try Again.", "Existing Credentials",
                                        JOptionPane.WARNING_MESSAGE);
                                break;
                            }
                        } else {
                            // checks if password and re-entered password are the same
                            if (!enteredPassword.equals(enteredRepeatPassword)) {
                                flag = 1;
                                JOptionPane.showMessageDialog(null, "Passwords Do Not Match.", "Password Mismatch",
                                        JOptionPane.WARNING_MESSAGE);
                                break;
                            }
                        }
                    }
                }
                if (flag == 0) {
                    // update Admin object with new information
                    adminList.get(i).setUsername(enteredUsername);
                    adminList.get(i).setEmail(enteredEmail);
                    adminList.get(i).setPassword(encryptPassword(enteredPassword));

                    // serialize and write data back to Admin.txt
                    try {
                        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName, false));
                        os.writeObject(adminList);
                        os.close();
                        JOptionPane.showMessageDialog(null, "Updated Successfully.", "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException e1) {
                        System.out.println("IOException");
                    }
                }
                break;
            }
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

}
