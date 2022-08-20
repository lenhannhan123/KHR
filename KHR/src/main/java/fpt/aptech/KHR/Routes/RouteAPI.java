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


    public final static String CheckAccountStatusAPI = "api/timeline/checkaccount";


    // api timekeeping

    public final static String CreateUserAPI = "api/timeline/createtimeuser";


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

    public final static String PostReportSendata= "api/timeline/report/sendata";

    public final static String GetReport1= "api/timeline/report/getreport1";

    public final static String GetReport1Detail= "api/timeline/report/getreport1detail";

    public final static String GetReport2= "api/timeline/report/getreport2";

    public final static String GetConfirm= "api/timeline/report/getConfirm";
    
    public final static String GetProfileInfo= "api/get-profile-info";
    
    public final static String GetAccountPositions= "api/get-account-position";
    
    public final static String UploadFile= "api/upload-file";

    public final static String AccountLogin= "api/accountLogin";
    
    public final static String UpdatePhotoProfile= "api/update-photo-profile";
    
}
