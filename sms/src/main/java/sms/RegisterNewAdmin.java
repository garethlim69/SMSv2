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

import javax.swing.JOptionPane;

import Objects.Admin;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class RegisterNewAdmin extends AdminFunction {

    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtPassword2;
    @FXML
    private TextField txtEmail;

    @Override
    public void RegisterAdmin() {
        // deserializes content of Admin.txt to ArrayList<Admin>
        String fileName = "src/main/java/Text Files/Admin.txt";
        ArrayList<Admin> adminList = new ArrayList<Admin>();
        int index;
        boolean isEmpty;
        int flag = 0;

        // retrieve details from text fields
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String password2 = txtPassword2.getText();
        String email = txtEmail.getText();

        // checks if fields are empty
        if (username.isEmpty() || password.isEmpty() || password2.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Empty Fields Detected.", "Empty Fields", JOptionPane.WARNING_MESSAGE);
        } else {
            // checks if password and re-enter password fields match
            if (!password.equals(password2)) {
                JOptionPane.showMessageDialog(null, "Passwords Do Not Match.", "Password Mismatch",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                // gets number of Admins currently registered
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
                    index = Integer.valueOf(adminList.get(adminList.size() - 1).getAdminID().substring(1));
                    index++;
                }

                // generates new admin ID
                String adminID = "A" + index;

                if (!isEmpty) {
                    for (int i = 0; i < adminList.size(); i++) {
                        if (username.equals(adminList.get(i).getUsername())
                                || email.equals(adminList.get(i).getEmail())) {
                            flag = 1;
                        }
                    }
                } else {
                    flag = 0;
                }

                // uses flag to determine if username or email exists, if no, encrypt pw, create new admin object and add to adminList
                if (flag == 0) {
                    // encrypt password
                    String encryptedPassword = encryptPassword(password);
                    Admin admin = new Admin(adminID, username, encryptedPassword, email);

                    adminList.add(admin);

                    // write information to Admin.txt file
                    try {
                        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName, false));
                        os.writeObject(adminList);
                        os.close();
                        JOptionPane.showMessageDialog(null, "Registered Successfully.", "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        switchAdminDashboard();
                    } catch (IOException e1) {
                        System.out.println("IOException");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Username or Email Already Exists. Please Try Again.",
                            "Existing Credentials", JOptionPane.WARNING_MESSAGE);
                }

            }
        }

    }

    @FXML
    private void switchAdminDashboard() throws IOException {
        App.setRoot("adminDashboard");
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

    public void clear() {
        int input = JOptionPane.showConfirmDialog(null, "Discard All Changes?", "Discard Changes?",
                JOptionPane.YES_NO_OPTION);
        if (input == 0) {
            txtUsername.clear();
            txtPassword.clear();
            txtPassword2.clear();
            txtEmail.clear();
        }
    }

    @Override
    void AdminEditProfile() {
        throw new UnsupportedOperationException("Function Not In Use");
    }

    @Override
    void ViewSCProfile(int pageNo) {
        throw new UnsupportedOperationException("Function Not In Use");
    }

    @Override
    void DeleteSCProfile() {
        throw new UnsupportedOperationException("Function Not In Use");
    }

    @Override
    void ViewSurveys(int pageNo) {
        throw new UnsupportedOperationException("Function Not In Use");
    }
}
