/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Routes;

import fpt.aptech.KHR.Controller.*;
import fpt.aptech.KHR.Entities.ModelDemo;

/**
 * @author Admin
 */
public class RouteWeb {

    ModelDemo model = new ModelDemo();

    //    Khu vuc new Model
    HomeController homeController = new HomeController();
    NotificationController notificationController = new NotificationController();
    AccountController accountController = new AccountController();
    DayOffController dayOffController;
    OverTimeController overTimeController = new OverTimeController();
    TimelineController timelineController = new TimelineController();
    TimekeepingController timekeepingController = new TimekeepingController();
    PositionController positionController = new PositionController();

    //    Khu vuc khai bao Route
    public final static String AdminHomeURL = "/home/index";
    String AdminHomeControler = homeController.Index(model.model);

    public final static String index1URL = "/login";
    String Index1Controler = homeController.Index(model.model);

    //Route Notification
    public final static String notificationURL = "/notification";
    String NotificationControler = notificationController.Index(model.model);

    public final static String notificationAddURL = "/notification/add";
    //String NotificationAddControler = notificationController.AddPage(model.model);
    //

    //Route DayOff
    public final static String dayoffURL = "/dayoff";
    //String DayOffControler = dayOffController.Index(model.model);

    public final static String dayoffapproveURL = "/dayoff/approved";
    //String DayOffApprovedControler = dayOffController.approved(model.model);

    public final static String dayoffdenyingURL = "/dayoff/denying";
    //String DayOffDenyingControler = dayOffController.Index(model.model);
    //
    //Route OverTime
    public final static String overtimeURL = "/overtime";
    //String OverTimeControler = overTimeController.Index(model.model);

    public final static String overtimeapproveURL = "/overtime/approved";
    // String OverTimeApprovedControler = dayOffController.Index(model.model);

    public final static String overtimedenyingURL = "/overtime/denying";
    //String OverTimeDenyingControler = dayOffController.Index(model.model);

    //Route Account
    public final static String accountManageURL = "/account/index";
    String AccountControllerString = accountController.AccountList(model.model);

    public final static String AccountGetCreateURL = "/account/create";
    String AccountGetCreateController = accountController.GetCreate(model.model);

    public final static String AccountGetUpdateURL = "/account/update";

    public final static String AccountGetBlockURL = "/account/lock";

    //Route Position
    public final static String positionManageURL = "/position/index";
    String PositionControllerString = positionController.PositionList(model.model);

    public final static String PositionGetCreateURL = "/position/create";
    String PositionGetCreateController = positionController.GetCreate(model.model);

    public final static String PositionGetUpdateURL = "/position/update";

    //Route Timeline
    public final static String TimelineIndexURL = "/timeline/index";
//    String TimelineIndexControler = timelineController.IndexTimeline(model.model);

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

    public final static String TimelineSortURL = "/timeline/sortwork";
    String TimelineSortURLControler = timelineController.GetCreate(model.model);

    public final static String TimelineUsertURL = "/timeline/usertimeline";
    String TimelineUserControler = timelineController.GetCreate(model.model);

    public final static String TimelineChangeStatusURL = "/timeline/changestatus";
    String TimelineChangeStatusControler = timelineController.GetCreate(model.model);

    public final static String TimelineReloadTimeURL = "/timeline/reload";
    String TimelineReloadTimeControler = timelineController.GetCreate(model.model);

    //route Timekeeping
    public final static String TimekeepingIndexURL = "/timekeeping/index";
    String TimeKeepingIndex = timekeepingController.index(model.model);

    public final static String TimelineDetailsURL = "/timeline/detail";
    String TimelineDetailsControler = timelineController.GetCreate(model.model);

}
