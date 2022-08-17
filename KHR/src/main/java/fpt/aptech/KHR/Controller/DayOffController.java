/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Controller;

import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Entities.AccountNotification;
import fpt.aptech.KHR.Entities.AccountToken;
import fpt.aptech.KHR.Entities.DayOff;
import fpt.aptech.KHR.Entities.Notification;
import fpt.aptech.KHR.ImpServices.AccountService;
import fpt.aptech.KHR.ImpServices.JsonServices;
import fpt.aptech.KHR.Routes.RouteWeb;
import fpt.aptech.KHR.Services.IDayOffServices;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import fpt.aptech.KHR.ImpServices.FirebaseMessagingService;
import fpt.aptech.KHR.ImpServices.NotificationService;
import fpt.aptech.KHR.Services.IAccountToken;
import fpt.aptech.KHR.Services.INotificationServices;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpSession;

/**
 *
 * @author LÊ HỮU TÂM
 */
@Controller
public class DayOffController {

    @Autowired
    IDayOffServices idos;
    @Autowired
    INotificationServices ins;
    @Autowired
    IAccountToken accToken;
    @Autowired
    AccountService accountService;
    @Autowired
    NotificationService ns;
    @Autowired
    AccountService acs;
    @Autowired
    FirebaseMessagingService firebaseMessagingService;
//    private final FirebaseMessagingService firebaseService;
//
//    public DayOffController(FirebaseMessagingService firebaseService) {
//        this.firebaseService = firebaseService;
//    }

    @RequestMapping(value = {RouteWeb.dayoffURL}, method = RequestMethod.GET)
    public String Index(Model model, HttpServletRequest request, HttpServletResponse response) {
        List<DayOff> list = idos.findAll();
        List<DayOff> listdayoff = new ArrayList<DayOff>();
        HttpSession session = request.getSession();
        int IdStore = Integer.parseInt(session.getAttribute("IdStore").toString());
        for(int i = list.size()-1;i>=0;i--){
            if(list.get(i).getMail().getIdStore().getId()==IdStore){
            listdayoff.add(list.get(i));
            }
        }
//        JsonServices.dd(JsonServices.ParseToJson(list), response);
//        JsonServices.ParseToJson(list);
        boolean check = false;
        for (DayOff item : listdayoff) {
            if (item.getMail() != null) {
                check = true;
                break;
            }
        }
        
        model.addAttribute("listdateoff", listdayoff);
        model.addAttribute("check", check);
        return "admin/dayoff/index";
    }
        @RequestMapping(value = {"dayoff/list/search"}, method = RequestMethod.POST)
    public String ApprovedList(Model model, HttpServletRequest request, HttpServletResponse response) {
//        List<DayOff> list = idos.findApproveList();
//        List<DayOff> listdayoff = new ArrayList<DayOff>();
//        for(int i = list.size()-1;i>=0;i--){
//            listdayoff.add(list.get(i));
//        }
           String start = request.getParameter("startdate");
           String end = request.getParameter("enddate");
           List<DayOff> list = idos.findByDate(start, end);
                   List<DayOff> listdayoff = new ArrayList<DayOff>();
        HttpSession session = request.getSession();
        int IdStore = Integer.parseInt(session.getAttribute("IdStore").toString());
        for(int i = list.size()-1;i>=0;i--){
            if(list.get(i).getMail().getIdStore().getId()==IdStore){
            listdayoff.add(list.get(i));
            }
        }
           //JsonServices.dd(JsonServices.ParseToJson(list), response);
//        JsonServices.ParseToJson(list);
        boolean check = false;
        for (DayOff item : listdayoff) {
            if (item.getMail() != null) {
                check = true;
                break;
            }
        }
//        
        model.addAttribute("listdateoff", listdayoff);
        model.addAttribute("check", check);
        return "admin/dayoff/index";
    }

    @RequestMapping(value = {"/dayoff/list/approved"}, method = RequestMethod.GET)
    public String SearchdList(Model model, HttpServletRequest request, HttpServletResponse response) {
        List<DayOff> list = idos.findApproveList();
        List<DayOff> listdayoff = new ArrayList<DayOff>();
        HttpSession session = request.getSession();
        int IdStore = Integer.parseInt(session.getAttribute("IdStore").toString());
        for(int i = list.size()-1;i>=0;i--){
            if(list.get(i).getMail().getIdStore().getId()==IdStore){
            listdayoff.add(list.get(i));
            }
        }
//        JsonServices.dd(JsonServices.ParseToJson(list), response);
//        JsonServices.ParseToJson(list);
        boolean check = false;
        for (DayOff item : listdayoff) {
            if (item.getMail() != null) {
                check = true;
                break;
            }
        }
        
        model.addAttribute("listdateoff", listdayoff);
        model.addAttribute("check", check);
        return "admin/dayoff/index";
    }
    

    @RequestMapping(value = {"/dayoff/list/denying"}, method = RequestMethod.GET)
    public String DenyingList(Model model, HttpServletRequest request, HttpServletResponse response) {
        List<DayOff> list = idos.findDenyingList();
        List<DayOff> listdayoff = new ArrayList<DayOff>();
        HttpSession session = request.getSession();
        int IdStore = Integer.parseInt(session.getAttribute("IdStore").toString());
        for(int i = list.size()-1;i>=0;i--){
            if(list.get(i).getMail().getIdStore().getId()==IdStore){
            listdayoff.add(list.get(i));
            }
        }
//        JsonServices.dd(JsonServices.ParseToJson(list), response);
//        JsonServices.ParseToJson(list);
        boolean check = false;
        for (DayOff item : listdayoff) {
            if (item.getMail() != null) {
                check = true;
                break;
            }
        }
        model.addAttribute("listdateoff", listdayoff);
        model.addAttribute("check", check);
        return "admin/dayoff/index";
    }

    @RequestMapping(value = {"/dayoff/list/notcheck"}, method = RequestMethod.GET)
    public String NotCheckList(Model model, HttpServletRequest request, HttpServletResponse response) {
        List<DayOff> list = idos.findNotCheck();
                List<DayOff> listdayoff = new ArrayList<DayOff>();
        HttpSession session = request.getSession();
        int IdStore = Integer.parseInt(session.getAttribute("IdStore").toString());
        for(int i = list.size()-1;i>=0;i--){
            if(list.get(i).getMail().getIdStore().getId()==IdStore){
            listdayoff.add(list.get(i));
            }
        }
//        JsonServices.dd(JsonServices.ParseToJson(list), response);
//        JsonServices.ParseToJson(list);
        boolean check = false;
        for (DayOff item : listdayoff) {
            if (item.getMail() != null) {
                check = true;
                break;
            }
        }
        model.addAttribute("listdateoff", listdayoff);
        model.addAttribute("check", check);
        return "admin/dayoff/index";
    }

    @RequestMapping(value = {RouteWeb.dayoffapproveURL}, method = RequestMethod.GET)
    public String approved(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        int id = Integer.valueOf(request.getParameter("id"));
        //String mail = request.getParameter("mail").toString();
//        fpt.aptech.KHR.Entities.Notification notification = new fpt.aptech.KHR.Entities.Notification();
//        notification.setTitle("Thông báo chấp nhận nghĩ phép");
//        notification.setContent("Quản trị viên đã xem và đã chấp nhận yêu cầu xin nghĩ phép của");

        DayOff di = idos.findById(id);
        idos.approve(id);
        if(di!=null){
        Notification n = new Notification();
        Date date = new Date();
        n.setTitle("Thông báo chấp thuận yêu cầu xin nghĩ");
        n.setContent("Đơn xin nghĩ phép của bạn "+di.getMail().getFullname()+" đã được xét duyệt cho phép nghĩ với thời gian "+di.getStartdate().toString()+" đến "+di.getEnddate()+"/n"
        +"Với lý do "+di.getContent()+" Bạn được nghỉ tổng cộng "+di.getDaynumber()+" (ngày)."+"\n"
        +"Chúng tôi rất cảm thông và hi vọng bạn có thể quay lại làm việc vào ngày sớm nhất ."+"\n"
        +" Xin cảm ơn"
        );
        n.setDateCreate(date);
        Notification ni = ns.AddNotification(n); 
        List<AccountToken> listToken = accToken.GetTokenByMail(di.getMail().getMail());
        List<String> listtokenstring = new ArrayList<>();
        for (AccountToken accountToken : listToken) {
                listtokenstring.add(accountToken.getToken());
            }
        AccountNotification accountNotification = new AccountNotification();
        accountNotification.setIdnotification(ni);
        accountNotification.setMail(di.getMail());
        accountNotification.setStatus(false);
        AccountNotification s = ns.AddAccountNotification(accountNotification);
        firebaseMessagingService.sendMorePeople(s, listtokenstring);
        }

        //       AccountNotification an = ins.CreateNotificationOnMail(di.getMail().getMail(), "Chấp nhận");
//        if(an!=null){
//        AccountToken tken = ins.findSendPeople(di.getMail().getMail());
//        firebaseService.sendAllNotification(an);
//        firebaseService.sendNotification(tken.getToken(), "Thông báo ngày nghĩ", "Nhân viên"+di.getMail().getFullname()+"Đã được chấp nhận");
//        }

        String redirectUrl = "/dayoff";
        return "redirect:" + redirectUrl;
    }

    @RequestMapping(value = {RouteWeb.dayoffdenyingURL}, method = RequestMethod.GET)
    public String denying(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        int id = Integer.valueOf(request.getParameter("id"));
        DayOff di = idos.findById(id);
        if(di!=null){
        idos.denying(id);
        Notification n = new Notification();
        Date date = new Date();
        n.setTitle("Thông báo từ chối yêu cầu xin nghĩ");
        n.setContent("Đơn xin nghĩ phép của bạn "+di.getMail().getFullname()+" đã bị từ chối xét duyệt cho phép nghĩ với thời gian "+di.getStartdate().toString()+" đến "+di.getEnddate()
        +"\n"
        +"Với lý do "+di.getContent()+"\n"
        +"Thời gian nghĩ của bạn hoặc lý do nghỉ không phù hợp !"        
        +"Chúng tôi rất cảm thông với yêu cầu cảu bạn."+"\n"
        +"Chúc bạn một ngày tốt lành."+"\n"
        +"Xin cảm ơn."
        );
        n.setDateCreate(date);
        Notification ni = ns.AddNotification(n); 
        List<AccountToken> listToken = accToken.GetTokenByMail(di.getMail().getMail());
        List<String> listtokenstring = new ArrayList<>();
        for (AccountToken accountToken : listToken) {
                listtokenstring.add(accountToken.getToken());
            }
        AccountNotification accountNotification = new AccountNotification();
        accountNotification.setIdnotification(ni);
        accountNotification.setMail(di.getMail());
        accountNotification.setStatus(false);
        AccountNotification s = ns.AddAccountNotification(accountNotification);
        firebaseMessagingService.sendMorePeople(s, listtokenstring);
        }
        String redirectUrl = "/dayoff";
        return "redirect:" + redirectUrl;
    }

    @RequestMapping(value = {"api/dayoff/add"}, method = RequestMethod.POST)
    public ResponseEntity<DayOff> APIAddDayOff(@RequestBody DayOff dayOff, HttpServletRequest request, HttpServletResponse response) {
        // Account acc = request.getParameter("mail")
        try {
            Account account = accountService.findByMail(dayOff.getMail().getMail());
            dayOff.setMail(account);
            DayOff df = idos.AddDayOff(dayOff);
            DayOff newdf = idos.findById(df.getId());
            if (newdf != null) {
                return new ResponseEntity<DayOff>(newdf, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @RequestMapping(value = {"api/dayoff/token"}, method = RequestMethod.GET)
    public ResponseEntity<List<DayOff>> APIgetDayOffByMail(HttpServletRequest request, HttpServletResponse response) {
        try {
            String mail = request.getParameter("token");
            AccountToken atk = accToken.GetToken(mail);

            List<DayOff> list = idos.findByMail(atk.getMail().getMail());

            if (list != null) {
                return new ResponseEntity<List<DayOff>>(list, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @RequestMapping(value = {"api/dayoff/token/search"}, method = RequestMethod.GET)
    public ResponseEntity<List<DayOff>> APIgetDayOffSearchMail(HttpServletRequest request, HttpServletResponse response) {
        try {
            String mail = request.getParameter("token");
            int year = Integer.valueOf(request.getParameter("year"));
            AccountToken atk = accToken.GetToken(mail);

            List<DayOff> list = idos.findByMail(atk.getMail().getMail());
            List<DayOff> listbyyear = new ArrayList<DayOff>();
            for (DayOff dayOff : list) {
                Date a = dayOff.getStartdate();
                if ((a.getYear()+1900)==year) {
                   listbyyear.add(dayOff); 
                }
                
            }
            if (list != null) {
                return new ResponseEntity<List<DayOff>>(listbyyear, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
    
}
