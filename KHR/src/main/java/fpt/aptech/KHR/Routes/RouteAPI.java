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
    public final static String CheckAuth = "api/auth/signin";

    public final static String GetTimeLineSort = "api/timeline/gettimelinedetail";

    public final static String GetTimeLineSort1 = "api/timeline/sort/gettimeline";

    public final static String GetTimeLineSortDetail = "api/timeline/sort/gettimelinedetail";

    public final static String GetReportChooseTimeline = "api/timeline/report/choose";

    public final static String GetReportMyDate = "api/timeline/report/mydate";

    public final static String GetReportMyShift = "api/timeline/report/myshift";

    public final static String GetReportMyPosition= "api/timeline/report/myposition";

    public final static String GetReportYourUser= "api/timeline/report/youruser";

    public final static String GetReportCheckPosition= "api/timeline/report/checkposition";

    public final static String GetReportSendata= "api/timeline/report/sendata";
}
