/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Entities;

/**
 *
 * @author Admin
 */
public class RouteModel {

   public final String URL;
   public String Controller;

    public RouteModel(String URL, String Controller) {

        this.Controller = this.Controller;
        this.URL = URL;
    }

  

    public String getURL() {
        return URL;
    }

   

    public String getController() {
        return Controller;
    }

    public void setController(String Controller) {
        this.Controller = Controller;
    }

}
