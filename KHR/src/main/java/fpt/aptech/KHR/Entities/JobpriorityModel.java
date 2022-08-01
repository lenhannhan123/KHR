/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Entities;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Admin
 */
public class JobpriorityModel {

    String Id_user;
    String User_name;
    int number_position;

    int people_Shift;
    List<AccountPosition> accountPositions;
    int number_shift;

    List<PositionTimelineJs> shiftList;


    public JobpriorityModel(String id_user, String user_name, int number_position, List<AccountPosition> accountPositions, int number_shift, List<PositionTimelineJs> position, int people_Shift) {
        accountPositions = new ArrayList<>();
        position = new ArrayList<>();
        Id_user = id_user;
        User_name = user_name;
        this.number_position = number_position;
        this.accountPositions = accountPositions;
        this.number_shift = number_shift;
        this.shiftList = position;
        this.people_Shift = people_Shift;

    }

    public JobpriorityModel() {
        accountPositions = new ArrayList<>();
        shiftList = new ArrayList<>();
    }


    public int getPeople_Shift() {
        return people_Shift;
    }

    public void setPeople_Shift(int people_Shift) {
        this.people_Shift = people_Shift;
    }

    public String getId_user() {
        return Id_user;
    }

    public void setId_user(String id_user) {
        Id_user = id_user;
    }

    public String getUser_name() {
        return User_name;
    }

    public void setUser_name(String user_name) {
        User_name = user_name;
    }

    public int getNumber_position() {
        return number_position;
    }

    public void setNumber_position(int number_position) {
        this.number_position = number_position;
    }

    public List<AccountPosition> getAccountPositions() {
        return accountPositions;
    }

    public void setAccountPositions(List<AccountPosition> accountPositions) {
        this.accountPositions = accountPositions;
    }

    public int getNumber_shift() {
        return number_shift;
    }

    public void setNumber_shift(int number_shift) {
        this.number_shift = number_shift;
    }


    public List<PositionTimelineJs> getshiftList() {
        return shiftList;
    }

    public void setshiftList(List<PositionTimelineJs> position) {
        this.shiftList = position;
    }
}
