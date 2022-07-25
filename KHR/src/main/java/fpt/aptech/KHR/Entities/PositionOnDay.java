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

    int Id_Code;

    public PositionOnDay() {
    }

    public PositionOnDay(int position_id, String mail, int id_Shift) {
        Position_id = position_id;
        this.mail = mail;
        this.Id_Code = id_Shift;
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

    public int getId_Code() {
        return Id_Code;
    }

    public void setId_Code(int id_Shift) {
        Id_Code = id_Shift;
    }
}
