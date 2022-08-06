package fpt.aptech.khrmobile.Entities;

import java.io.Serializable;

public class AccountNotification implements Serializable {
    private Integer id;
    private boolean status;
    private Account mail;
    private Notification idnotification;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Account getMail() {
        return mail;
    }

    public void setMail(Account mail) {
        this.mail = mail;
    }

    public Notification getIdnotification() {
        return idnotification;
    }

    public void setIdnotification(Notification idnotification) {
        this.idnotification = idnotification;
    }
}
