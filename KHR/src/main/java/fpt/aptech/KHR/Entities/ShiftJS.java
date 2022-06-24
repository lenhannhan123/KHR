package fpt.aptech.KHR.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShiftJS {

    public ShiftJS() {

    }

    public ShiftJS(String date, String name, PositionJS position) {
        this.date = date;
        this.name = name;
        this.position.add(position);
    }

    @JsonProperty("date")
    String date;

    @JsonProperty("name")
    String name;

    @JsonProperty("position")

    public List<PositionJS> position;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
