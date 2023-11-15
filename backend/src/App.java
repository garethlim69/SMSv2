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
import Objects.SurveyCreator;
import Survey.Survey;

public class App {
    public static void main(String[] args) throws Exception {
        // RegisterAdmin();
        // LoginAdmin();
        // LoginSC();
        // PrintDetails();
        // AdminEditProfile();
        // PrintDetailsSC();
        // RegisterSC();
        // ResetSCPw("SC3");
        // DeleteSCProfile("SC1");
        // PrintDetailsSC();
        // Survey.ViewSurveys();
        Survey.ViewQuestions("S5", 6);
        // Survey.ChangeStatus("S5", "not-approved");
        // Survey.EditTitle("S1", "New Survey 1");
        // Survey.CreateSurvey();
        // Survey.AddQuestion("S6");
        // Survey.EditQuestion("S7");
        // Survey.DeleteQuestion("S4");
        // Survey.ViewResponses("S1");
        // Survey.SaveResponses("S1");
    }

    public static void PrintDetailsAdmin(){
        String fileName = "src/Text Files/Admin.txt";
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
        System.out.println("-------------------------------------");
        for (int i = 0; i < adminList.size(); i++){
            System.out.println("Admin ID: " + adminList.get(i).getAdminID());
            System.out.println("Username: " + adminList.get(i).getUsername());
            System.out.println("Email: " + adminList.get(i).getEmail());
            System.out.println("Password: " + adminList.get(i).getPassword());
        }
        System.out.println("-------------------------------------");
    }

    public static void PrintDetailsSC(){
        String fileName = "src/Text Files/SurveyCreator.txt";
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
        System.out.println("-------------------------------------");
        for (int i = 0; i < scList.size(); i++){
            System.out.println("SCID: " + scList.get(i).getScID());
            System.out.println("Username: " + scList.get(i).getCreatorName());
            System.out.println("Password: " + scList.get(i).getPassword());
            System.out.println("First Name: " + scList.get(i).getFirstName());
            System.out.println("Last Name: " + scList.get(i).getLastName());
            System.out.println("Email: " + scList.get(i).getEmail());
            System.out.println("Contact Number: " + scList.get(i).getContactNumber());
            System.out.println("Age: " + scList.get(i).getAge());
            System.out.println("Gender: " + scList.get(i).getGender());
        }
        System.out.println("-------------------------------------");
    }

    public static void ResetSCPw(String scID){
        String fileName = "src/Text Files/SurveyCreator.txt";
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

    public static void DeleteSCProfile(String scID){
        String fileName = "src/Text Files/SurveyCreator.txt";
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
    }

    public static void AdminEditProfile(){
        //AdminID passed from another function (hardcoded for now)
        String adminID = "A2";
        
        //Data from textFields
        String enteredUsername = "Admin2";
        String enteredEmail = "mail2@mail.com";
        String enteredPassword = "pw1";
        String enteredRepeatPassword = "pw1";

        String fileName = "src/Text Files/Admin.txt";
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

    public static void SCUpdateProfile(){
    //AdminID passed from another function (hardcoded for now)
        String scID = "SC1";
        
        //Data from textFields
        String enteredCreatorName = "SurveyCreator3";
        String enteredPassword = "pw1";
        String enteredRepeatPassword = "pw1";
        String enteredFirstName = "Joe";
        String enteredLastName = "Mama";
        String enteredEmail = "mail1@mail.com";
        String enteredContactNumber = "0123456789";
        String enteredAge = "45";
        String enteredGender = "M";

        String fileName = "src/Text Files/SurveyCreator.txt";
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
                encryptPassword(enteredPassword).equals(scList.get(i).getPassword()) &&
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
                        if (enteredCreatorName.equals(scList.get(i2).getCreatorName()) || enteredEmail.equals(scList.get(i2).getEmail()) || enteredContactNumber.equals(scList.get(i2).getContactNumber())) {
                            if (!enteredCreatorName.equals(scList.get(i).getCreatorName()) || !enteredEmail.equals(scList.get(i).getEmail()) || !enteredContactNumber.equals(scList.get(i).getContactNumber())){
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
                    scList.get(i).setCreatorName(enteredCreatorName);
                    scList.get(i).setEmail(enteredEmail);
                    scList.get(i).setPassword(encryptPassword(enteredPassword));

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

    public static void RegisterAdmin(){
        String fileName = "src/Text Files/Admin.txt";
        ArrayList<Admin> adminList = new ArrayList<Admin>();
        int index;
        boolean isEmpty;
        int flag = 0;

        //username = usernameField.getText();
        //password = passwordField.getText();
        //email = emailField.getText();
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
        String username = "Admin3";
        String password = "pw3";
        String email = "mail3@mail.com";

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

    public static void LoginAdmin(){
        String fileName = "src/Text Files/Admin.txt";
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
        String enteredUsername = "Admin1";
        String enteredPassword = "pw1";

        for (int i = 0; i < adminList.size(); i++){
            if (enteredUsername.equals(adminList.get(i).getUsername()) && encryptPassword(enteredPassword).equals(adminList.get(i).getPassword())){
                System.out.println("login success");
            } else {
                System.out.println("failed");
            }
        }
    }

    public static void RegisterSC(){        
        String fileName = "src/Text Files/SurveyCreator.txt";
        ArrayList<SurveyCreator> scList = new ArrayList<SurveyCreator>();
        int index;
        boolean isEmpty;
        int flag = 0;

        //username = usernameField.getText();
        //password = passwordField.getText();
        //email = emailField.getText();
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
        String creatorName = "SurveyCreator4";
        String password = "pw4";
        String firstName = "Joe";
        String lastName = "Mama";
        String email = "mail4@mail.com";
        String contactNumber = "0123456780";
        int Age = 45;
        String gender = "M";

        if (!isEmpty){
            for (int i = 0; i < scList.size(); i++){
                if (creatorName.equals(scList.get(i).getCreatorName()) || email.equals(scList.get(i).getEmail()) || contactNumber.equals(scList.get(i).getContactNumber())){
                    flag = 1;
                }
            }
            if (!contactNumber.matches("[0-9]+")){
                flag = 2;
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
            } catch (IOException e1){
            System.out.println("IOException");
            }    
        } else if (flag == 1) {
            System.out.println("Username, E-mail or Contact Number Already Exists. Please Try Another.");
        } else if (flag == 2) {
            System.out.println("Contact Number Must Contain Only Numbers.");
        }
    }

    public static void LoginSC(){
        String fileName = "src/Text Files/SurveyCreator.txt";
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
        String enteredUsername = "SurveyCreator2";
        String enteredPassword = "pw321";

        for (int i = 0; i < scList.size(); i++){
            if (enteredUsername.equals(scList.get(i).getCreatorName()) && encryptPassword(enteredPassword).equals(scList.get(i).getPassword())){
                isLoginSuccess = true;
            }
        }
        if (isLoginSuccess) {
            System.out.println("Success");
        } else {
            System.out.println("Failed");
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