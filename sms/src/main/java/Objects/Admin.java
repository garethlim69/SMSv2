package Objects;

import java.io.Serializable;

public class Admin implements Serializable{
    String adminID;
    String username;
    String password;
    String email;

    public Admin(String adminID, String username, String password, String email) {
        this.adminID = adminID;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getAdminID(){
        return this.adminID;
    }

    public void setAdminID(){
        this.adminID = adminID;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}
