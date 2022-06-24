package fpt.aptech.KHR.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PositionJS {


    public PositionJS() {
    }

    public PositionJS(int id, boolean isCheck, int number, String positionname, int salarydefault, int id_Db) {
        this.id = id;
        this.isCheck = isCheck;
        this.number = number;
        this.positionname = positionname;
        this.salarydefault = salarydefault;
        this.id_Db = id_Db;
    }

    public PositionJS(int id, boolean isCheck, int number, String positionname, int salarydefault) {
        this.id = id;
        this.isCheck = isCheck;
        this.number = number;
        this.positionname = positionname;
        this.salarydefault = salarydefault;
    }

    @JsonProperty("id")
    int id;

    @JsonProperty("isCheck")
    boolean isCheck;


    @JsonProperty("number")
    int number;

    @JsonProperty("positionname")
    String positionname;

    @JsonProperty("salarydefault")
    int salarydefault;

    @JsonProperty("id_Db")
    int id_Db;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIsCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPositionname() {
        return positionname;
    }

    public void setPositionname(String positionname) {
        this.positionname = positionname;
    }

    public int getSalarydefault() {
        return salarydefault;
    }

    public void setSalarydefault(int salarydefault) {
        this.salarydefault = salarydefault;
    }

    public int getId_Db() {
        return id_Db;
    }

    public void setId_Db(int id_Db) {
        this.id_Db = id_Db;
    }


}
