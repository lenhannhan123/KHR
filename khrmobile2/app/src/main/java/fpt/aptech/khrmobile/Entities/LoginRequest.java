package fpt.aptech.khrmobile.Entities;

import java.io.Serializable;

public class LoginRequest  implements Serializable {
    private String mail;
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
