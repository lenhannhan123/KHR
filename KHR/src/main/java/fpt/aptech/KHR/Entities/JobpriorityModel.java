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

    int Id_user;
    String User_name;
    int number_position;
    List<AccountPosition> accountPositions;
    int number_shift;
    boolean shift1 = true;
    boolean shift2 = true;
    boolean shift3 = true;
    boolean shift4 = true;
    boolean shift5 = true;
    boolean shift6 = true;
    boolean shift7 = true;
    boolean shift8 = true;
    boolean shift9 = true;
    boolean shift10 = true;
    boolean shift11 = true;
    boolean shift12 = true;
    boolean shift13 = true;
    boolean shift14 = true;
    boolean shift15 = true;
    boolean shift16 = true;
    boolean shift17 = true;
    boolean shift18 = true;
    boolean shift19 = true;
    boolean shift20 = true;
    boolean shift21 = true;
    boolean shift22 = true;
    boolean shift23 = true;
    boolean shift24 = true;
    boolean shift25 = true;
    boolean shift26 = true;
    boolean shift27 = true;
    boolean shift28 = true;
    boolean shift29 = true;
    boolean shift30 = true;
    boolean shift31 = true;
    boolean shift32 = true;
    boolean shift33 = true;
    boolean shift34 = true;
    boolean shift35 = true;

    public JobpriorityModel(int id_user, String user_name, int number_position, List<AccountPosition> accountPositions, int number_shift, boolean shift1, boolean shift2, boolean shift3, boolean shift4, boolean shift5, boolean shift6, boolean shift7, boolean shift8, boolean shift9, boolean shift10, boolean shift11, boolean shift12, boolean shift13, boolean shift14, boolean shift15, boolean shift16, boolean shift17, boolean shift18, boolean shift19, boolean shift20, boolean shift21, boolean shift22, boolean shift23, boolean shift24, boolean shift25, boolean shift26, boolean shift27, boolean shift28, boolean shift29, boolean shift30, boolean shift31, boolean shift32, boolean shift33, boolean shift34, boolean shift35) {
        accountPositions = new ArrayList<>();
        Id_user = id_user;
        User_name = user_name;
        this.number_position = number_position;
        this.accountPositions = accountPositions;
        this.number_shift = number_shift;
        this.shift1 = shift1;
        this.shift2 = shift2;
        this.shift3 = shift3;
        this.shift4 = shift4;
        this.shift5 = shift5;
        this.shift6 = shift6;
        this.shift7 = shift7;
        this.shift8 = shift8;
        this.shift9 = shift9;
        this.shift10 = shift10;
        this.shift11 = shift11;
        this.shift12 = shift12;
        this.shift13 = shift13;
        this.shift14 = shift14;
        this.shift15 = shift15;
        this.shift16 = shift16;
        this.shift17 = shift17;
        this.shift18 = shift18;
        this.shift19 = shift19;
        this.shift20 = shift20;
        this.shift21 = shift21;
        this.shift22 = shift22;
        this.shift23 = shift23;
        this.shift24 = shift24;
        this.shift25 = shift25;
        this.shift26 = shift26;
        this.shift27 = shift27;
        this.shift28 = shift28;
        this.shift29 = shift29;
        this.shift30 = shift30;
        this.shift31 = shift31;
        this.shift32 = shift32;
        this.shift33 = shift33;
        this.shift34 = shift34;
        this.shift35 = shift35;
    }

    public JobpriorityModel() {
        accountPositions = new ArrayList<>();
    }


    public int getId_user() {
        return Id_user;
    }

    public void setId_user(int id_user) {
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

    public boolean isShift1() {
        return shift1;
    }

    public void setShift1(boolean shift1) {
        this.shift1 = shift1;
    }

    public boolean isShift2() {
        return shift2;
    }

    public void setShift2(boolean shift2) {
        this.shift2 = shift2;
    }

    public boolean isShift3() {
        return shift3;
    }

    public void setShift3(boolean shift3) {
        this.shift3 = shift3;
    }

    public boolean isShift4() {
        return shift4;
    }

    public void setShift4(boolean shift4) {
        this.shift4 = shift4;
    }

    public boolean isShift5() {
        return shift5;
    }

    public void setShift5(boolean shift5) {
        this.shift5 = shift5;
    }

    public boolean isShift6() {
        return shift6;
    }

    public void setShift6(boolean shift6) {
        this.shift6 = shift6;
    }

    public boolean isShift7() {
        return shift7;
    }

    public void setShift7(boolean shift7) {
        this.shift7 = shift7;
    }

    public boolean isShift8() {
        return shift8;
    }

    public void setShift8(boolean shift8) {
        this.shift8 = shift8;
    }

    public boolean isShift9() {
        return shift9;
    }

    public void setShift9(boolean shift9) {
        this.shift9 = shift9;
    }

    public boolean isShift10() {
        return shift10;
    }

    public void setShift10(boolean shift10) {
        this.shift10 = shift10;
    }

    public boolean isShift11() {
        return shift11;
    }

    public void setShift11(boolean shift11) {
        this.shift11 = shift11;
    }

    public boolean isShift12() {
        return shift12;
    }

    public void setShift12(boolean shift12) {
        this.shift12 = shift12;
    }

    public boolean isShift13() {
        return shift13;
    }

    public void setShift13(boolean shift13) {
        this.shift13 = shift13;
    }

    public boolean isShift14() {
        return shift14;
    }

    public void setShift14(boolean shift14) {
        this.shift14 = shift14;
    }

    public boolean isShift15() {
        return shift15;
    }

    public void setShift15(boolean shift15) {
        this.shift15 = shift15;
    }

    public boolean isShift16() {
        return shift16;
    }

    public void setShift16(boolean shift16) {
        this.shift16 = shift16;
    }

    public boolean isShift17() {
        return shift17;
    }

    public void setShift17(boolean shift17) {
        this.shift17 = shift17;
    }

    public boolean isShift18() {
        return shift18;
    }

    public void setShift18(boolean shift18) {
        this.shift18 = shift18;
    }

    public boolean isShift19() {
        return shift19;
    }

    public void setShift19(boolean shift19) {
        this.shift19 = shift19;
    }

    public boolean isShift20() {
        return shift20;
    }

    public void setShift20(boolean shift20) {
        this.shift20 = shift20;
    }

    public boolean isShift21() {
        return shift21;
    }

    public void setShift21(boolean shift21) {
        this.shift21 = shift21;
    }

    public boolean isShift22() {
        return shift22;
    }

    public void setShift22(boolean shift22) {
        this.shift22 = shift22;
    }

    public boolean isShift23() {
        return shift23;
    }

    public void setShift23(boolean shift23) {
        this.shift23 = shift23;
    }

    public boolean isShift24() {
        return shift24;
    }

    public void setShift24(boolean shift24) {
        this.shift24 = shift24;
    }

    public boolean isShift25() {
        return shift25;
    }

    public void setShift25(boolean shift25) {
        this.shift25 = shift25;
    }

    public boolean isShift26() {
        return shift26;
    }

    public void setShift26(boolean shift26) {
        this.shift26 = shift26;
    }

    public boolean isShift27() {
        return shift27;
    }

    public void setShift27(boolean shift27) {
        this.shift27 = shift27;
    }

    public boolean isShift28() {
        return shift28;
    }

    public void setShift28(boolean shift28) {
        this.shift28 = shift28;
    }

    public boolean isShift29() {
        return shift29;
    }

    public void setShift29(boolean shift29) {
        this.shift29 = shift29;
    }

    public boolean isShift30() {
        return shift30;
    }

    public void setShift30(boolean shift30) {
        this.shift30 = shift30;
    }

    public boolean isShift31() {
        return shift31;
    }

    public void setShift31(boolean shift31) {
        this.shift31 = shift31;
    }

    public boolean isShift32() {
        return shift32;
    }

    public void setShift32(boolean shift32) {
        this.shift32 = shift32;
    }

    public boolean isShift33() {
        return shift33;
    }

    public void setShift33(boolean shift33) {
        this.shift33 = shift33;
    }

    public boolean isShift34() {
        return shift34;
    }

    public void setShift34(boolean shift34) {
        this.shift34 = shift34;
    }

    public boolean isShift35() {
        return shift35;
    }

    public void setShift35(boolean shift35) {
        this.shift35 = shift35;
    }
}
