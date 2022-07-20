package fpt.aptech.khrmobile.Entities;

import java.io.Serializable;
import java.util.Date;

public class AccountLogin implements Serializable {
    private String code;
    private String mail;
    private String firstname;
    private String lastname;
    private boolean status;

    public AccountLogin(String code, String mail, String firstname, String lastname, boolean status) {
        this.code = code;
        this.mail = mail;
        this.firstname = firstname;
        this.lastname = lastname;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return mail;
    }

    public void setEmail(String mail) {
        this.mail = mail;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
