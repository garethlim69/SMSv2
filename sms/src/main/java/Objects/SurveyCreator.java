package Objects;

import java.io.Serializable;

public class SurveyCreator implements Serializable{
    String scID;
    String creatorName;
    String password;
    String firstName;
    String lastName;
    String email;
    String contactNumber;
    int Age;
    String gender;

    public SurveyCreator(String scID, String creatorName, String password, String firstName, String lastName, String email, String contactNumber, int Age, String gender) {
        this.scID = scID;
        this.creatorName = creatorName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contactNumber = contactNumber;
        this.Age = Age;
        this.gender = gender;
    }

    public String getScID() {
        return this.scID;
    }

    public void setScID(String scID) {
        this.scID = scID;
    }

    public String getCreatorName() {
        return this.creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return this.contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public int getAge() {
        return this.Age;
    }

    public void setAge(int Age) {
        this.Age = Age;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
