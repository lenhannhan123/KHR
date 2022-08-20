package fpt.aptech.khrmobile.Entities;

import java.io.Serializable;
import java.util.Date;

public class Timekeeping implements Serializable {
    private int id;
    private Date timestart;
    private Date timeend;
    private int time;
    private Account mail;
    private int shiftCode;
    private int idTimeline;

    public Timekeeping() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getMail() {
        return mail;
    }

    public void setMail(Account mail) {
        this.mail = mail;
    }

    public Date getTimestart() {
        return timestart;
    }

    public void setTimestart(Date timestart) {
        this.timestart = timestart;
    }

    public Date getTimeend() {
        return timeend;
    }

    public void setTimeend(Date timeend) {
        this.timeend = timeend;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getShiftCode() {
        return shiftCode;
    }

    public void setShiftCode(int shiftCode) {
        this.shiftCode = shiftCode;
    }

    public int getIdTimeline() {
        return idTimeline;
    }

    public void setIdTimeline(int idTimeline) {
        this.idTimeline = idTimeline;
    }
}
