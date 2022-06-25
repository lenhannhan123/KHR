/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Routes;

import fpt.aptech.KHR.Controller.TimelineController;
import fpt.aptech.KHR.Entities.ModelDemo;

/**
 * @author Admin
 */
public class RouteAPI {

    ModelDemo model = new ModelDemo();

    //    Khu vuc new Model

    TimelineController timelineController = new TimelineController();

    public final static String TimelineListAPI = "api/timeline/index";
    String TimelineListAPIControler = timelineController.GetCreate(model.model);


    public final static String CheckAccountStatusAPI = "api/timeline/checkaccount";
    String CheckAccountStatusControler = timelineController.GetCreate(model.model);

}
