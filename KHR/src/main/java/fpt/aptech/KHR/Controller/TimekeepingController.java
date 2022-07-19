/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Controller;

import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Entities.Timekeeping;
import fpt.aptech.KHR.ImpServices.JsonServices;
import fpt.aptech.KHR.Routes.RouteAPI;
import fpt.aptech.KHR.Routes.RouteWeb;
import fpt.aptech.KHR.Services.IAccountRepository;
import fpt.aptech.KHR.Services.IShiftServices;
import fpt.aptech.KHR.Services.ITimekeepingServices;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.springframework.web.bind.annotation.RequestParam;
/**
 *
 * @author backs
 */
@Controller
public class TimekeepingController {

    @Autowired
    ITimekeepingServices timekeepingServices;

    @Autowired
    IAccountRepository accountRepository;

    @Autowired
    IShiftServices shiftServices;

    @RequestMapping(value = "/timekeeping/index", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("accountList", accountRepository.findAll());
        model.addAttribute("list", timekeepingServices.findAll());
        return "timekeeping/index";
    }

    @RequestMapping(value = "/timekeeping/search", method = RequestMethod.GET)
    public ResponseEntity<List<String>> search(@RequestParam("value") String input, HttpServletRequest request) {
        return new ResponseEntity<List<String>>(timekeepingServices.search(input), HttpStatus.OK);
    }

    @RequestMapping(value = "/api/timekeeping/checkin", method = RequestMethod.POST)
    public ResponseEntity<Timekeeping> checkin(@RequestBody Timekeeping timekeeping) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Account account = accountRepository.findByMail("vlogcuocsongvacongnghe@gmail.com");
        timekeeping.setMail(account);
        LocalTime localTime = LocalTime.now();
        //timekeeping.setTimestart(java.sql.Time.valueOf(localTime));
        timekeeping.setTimestart(java.sql.Time.valueOf("07:30:00"));
        timekeeping.setTimeend(java.sql.Time.valueOf("00:00:00"));
        timekeepingServices.checkin(timekeeping);
        return new ResponseEntity<>(timekeeping, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/api/timekeeping/checkout", method = RequestMethod.POST)
    public ResponseEntity<Timekeeping> checkout(@RequestBody Timekeeping timekeeping) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            Account account = accountRepository.findByMail("vlogcuocsongvacongnghe@gmail.com");
            timekeeping.setMail(account);
            timekeeping = timekeepingServices.findByMail(timekeeping.getMail());
            LocalTime localTime = LocalTime.now();
            //timekeeping.setTimeend(java.sql.Time.valueOf(localTime));
            timekeeping.setTimeend(java.sql.Time.valueOf("11:30:00"));
            Date timeStart = formatter.parse(timekeeping.getTimestart().toString());
            Date timeEnd = formatter.parse(timekeeping.getTimeend().toString());
            Long time = timeEnd.getTime() - timeStart.getTime();
            int workingHours = (int) TimeUnit.MILLISECONDS.toHours(time);
            timekeeping.setTime(workingHours);
            timekeepingServices.checkout(timekeeping);
            return new ResponseEntity<>(timekeeping, HttpStatus.OK);
        } catch (ParseException ex) {
            Logger.getLogger(TimekeepingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/timekeeping/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable int id, Model model) {
        Timekeeping timekeeping = timekeepingServices.findOne(id);
        Account user = accountRepository.findByMail(timekeeping.getMail().getMail());
        model.addAttribute("timekeeping", timekeeping);
        model.addAttribute("user", user);
        return "timekeeping/update";
    }

    @RequestMapping(value = "/timekeeping/edit/{id}", method = RequestMethod.POST)
    public String edit(@PathVariable int id, Model model, HttpServletRequest request, HttpServletResponse response) {
        Timekeeping timekeeping = timekeepingServices.findOne(id);
        if (request.getParameter("action").equals("Trở lại")) {
            return index(model);
        } else {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                
                String checkin = request.getParameter("timeStart") + ":00";
                //JsonServices.dd(JsonServices.ParseToJson(checkin), response);
                String checkout = request.getParameter("timeEnd") + ":00";
                timekeeping.setTimestart(java.sql.Time.valueOf(checkin));
                timekeeping.setTimeend(java.sql.Time.valueOf(checkout));
                Date timeStart = formatter.parse(timekeeping.getTimestart().toString());
                Date timeEnd = formatter.parse(timekeeping.getTimeend().toString());
                Long time = timeEnd.getTime() - timeStart.getTime();
                int workingHours = (int) TimeUnit.MILLISECONDS.toHours(time);
                timekeeping.setTime(workingHours);
                timekeepingServices.checkout(timekeeping);
            } catch (ParseException ex) {
                Logger.getLogger(TimekeepingController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return update(timekeeping.getId(), model);
    }
}
