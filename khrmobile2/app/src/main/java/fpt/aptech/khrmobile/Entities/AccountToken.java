package fpt.aptech.khrmobile.Entities;

import java.io.Serializable;

public class AccountToken implements Serializable {
    private String token;
    private Account mail;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Account getMail() {
        return mail;
    }

    public void setMail(Account mail) {
        this.mail = mail;
    }
}
