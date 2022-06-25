/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Routes;

import fpt.aptech.KHR.Controller.*;
import fpt.aptech.KHR.Entities.ModelDemo;
import fpt.aptech.KHR.Entities.Notification;
import fpt.aptech.KHR.Entities.RouteModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Admin
 */
public class RouteWeb {

    ModelDemo model = new ModelDemo();

    //    Khu vuc new Model
    HomeController homeController = new HomeController();
    NotificationController notificationController = new NotificationController();
    AccountController accountController = new AccountController();
    DayOffController dayOffController = new DayOffController();
    OverTimeController overTimeController = new OverTimeController();

    TimelineController timelineController = new TimelineController();


//    Khu vuc khai bao Route

    public final static String AdminHomeURL = "/home/index";
    String AdminHomeControler = homeController.Index(model.model);


    public final static String index1URL = "/login";
    String Index1Controler = homeController.Index(model.model);


    //Route Notification
    public final static String notificationURL = "/notification";
    String NotificationControler = notificationController.Index(model.model);

    public final static String notificationAddURL = "/notification/add";
    String NotificationAddControler = notificationController.AddPage();
    //

    //Route DayOff
    public final static String dayoffURL = "/dayoff";
    String DayOffControler = dayOffController.Index(model.model);
    //
    //Route OverTime
    public final static String overtimeURL = "/overtime";
    String OverTimeControler = overTimeController.Index(model.model);
    //
    public final static String accountManageURL = "/account/index";
    String AccountControllerString = accountController.AccountList(model.model);

    public final static String AccountGetCreateURL = "/account/create";
    String AccountGetCreateControler = accountController.GetCreate(model.model);



    //Route Timeline
    public final static String TimelineIndexURL = "/timeline/index";
    String TimelineIndexControler = timelineController.IndexTimeline(model.model);


    public final static String TimelineGetCreateURL = "/timeline/create";
    String TimelineGetCreateControler = timelineController.GetCreate(model.model);

    public final static String TimelineConfirmURL = "/timeline/cofirm";
    String TimelineConfirmURLControler = timelineController.GetCreate(model.model);


    public final static String TimelineCheckEnddayURL = "/timeline/checkendday";
    String TimelineCheckEnddaydayControler = timelineController.GetCreate(model.model);

    public final static String TimelineCheckStartdayURL = "/timeline/checkstartday";
    String TimelineCheckStartdayControler = timelineController.GetCreate(model.model);


    public final static String TimelineDeleteURL = "/timeline/delete";
    String TimelineDeleteyControler = timelineController.GetCreate(model.model);

    public final static String TimelineEditNameURL = "/timeline/editname";
    String TimelineEditNameControler = timelineController.GetCreate(model.model);


    public final static String TimelineEditTimelineURL = "/timeline/edittimeline";
    String TimelineEditTimelineControler = timelineController.GetCreate(model.model);
}
