/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Entities;

/**
 * @author Admin
 */
public class PositionOnDay {

    int Position_id;
    String mail;

    int Shift_Code;

    public PositionOnDay() {
    }

    public PositionOnDay(int position_id, String mail, int id_Shift) {
        Position_id = position_id;
        this.mail = mail;
        this.Shift_Code = id_Shift;
    }

    public int getPosition_id() {
        return Position_id;
    }

    public void setPosition_id(int position_id) {
        Position_id = position_id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getShift_Code() {
        return Shift_Code;
    }

    public void setShift_Code(int id_Shift) {
        Shift_Code = id_Shift;
    }
}
