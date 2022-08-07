package fpt.aptech.khrmobile.Entities;

import java.util.Date;

public class DayOff {
    private Integer id;
    private Date startdate;
    private Date enddate;
    private int daynumber;
    private String content;
    private short status;
    private Account mail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public int getDaynumber() {
        return daynumber;
    }

    public void setDaynumber(int daynumber) {
        this.daynumber = daynumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public Account getMail() {
        return mail;
    }

    public void setMail(Account mail) {
        this.mail = mail;
    }
}
