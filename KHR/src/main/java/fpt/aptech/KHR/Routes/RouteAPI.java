/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Routes;

import fpt.aptech.KHR.Controller.TimekeepingController;
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

    // api timekeeping

    public final static String CreateUserAPI = "api/timeline/createtimeuser";
    String CreateUserControler = timelineController.GetCreate(model.model);

    // api timekeeping
    TimekeepingController timekeepingController = new TimekeepingController();

    public final static String checkinAPI = "api/timekeeping/checkin";
    public final static String checkoutAPI = "api/timekeeping/checkout";
    public final static String saveTimekeepingAPI = "api/timekeeping/save";

    public final static String Timelinecheckmytimeline = "api/timeline/mytimeline";

    public final static String TimelineGetYear = "api/timeline/getyear";

    public final static String TimelineGetMyTimelineDetail = "api/timeline/mytimeline/detail";

    public final static String TimelineGetAddTimeline = "api/timeline/addtimeline/list";

}
