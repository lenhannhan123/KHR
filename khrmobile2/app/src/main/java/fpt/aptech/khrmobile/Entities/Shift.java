package fpt.aptech.khrmobile.Entities;

import java.util.Date;

public class Shift {
    public int id;
    public int number;
    public Boolean isOT;
    public Date timestart;
    public Date timeend;
    public int shiftcode;

    public Shift() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Boolean getOT() {
        return isOT;
    }

    public void setOT(Boolean OT) {
        isOT = OT;
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

    public int getShiftcode() {
        return shiftcode;
    }

    public void setShiftcode(int shiftcode) {
        this.shiftcode = shiftcode;
    }
}
