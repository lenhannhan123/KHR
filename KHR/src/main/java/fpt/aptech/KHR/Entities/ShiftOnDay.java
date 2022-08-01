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
public class ShiftOnDay {

    List<PositionOnDay> positionOnDays;


    public ShiftOnDay(List<PositionOnDay> positionOnDays) {
        positionOnDays = new ArrayList<>();
        this.positionOnDays = positionOnDays;
    }

    public ShiftOnDay() {
    }

    public List<PositionOnDay> getPositionOnDays() {
        return positionOnDays;
    }

    public void setPositionOnDays(List<PositionOnDay> positionOnDays) {
        this.positionOnDays = positionOnDays;
    }
}
