/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Controller;

import com.google.gson.JsonArray;
import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Entities.Shift;
import fpt.aptech.KHR.Entities.Timekeeping;
import fpt.aptech.KHR.ImpServices.JsonServices;
import fpt.aptech.KHR.Routes.RouteAPI;
import fpt.aptech.KHR.Routes.RouteWeb;
import fpt.aptech.KHR.Services.IAccountRepository;
import fpt.aptech.KHR.Services.IShiftServices;
import fpt.aptech.KHR.Services.ITimekeepingServices;
import java.sql.Timestamp;

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
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

    @RequestMapping(value = "/timekeeping/autocomplete", method = RequestMethod.GET)
    public ResponseEntity<List<String>> autocomplete(@RequestParam("value") String input, HttpServletRequest request) {
        return new ResponseEntity<List<String>>(timekeepingServices.autocomplete(input), HttpStatus.OK);
    }

    @RequestMapping(value = "/timekeeping/search", method = RequestMethod.GET)
    public String search(HttpServletRequest request, Model model) {
        model.addAttribute("accountList", accountRepository.findAll());
        if (request.getParameter("mail").equals("")) {
            return index(model);
        } else {
            model.addAttribute("list", timekeepingServices.search(request.getParameter("mail")));
        }

        return "timekeeping/index";
    }

    @RequestMapping(value = "/api/timekeeping/checkin", method = RequestMethod.POST)
    public ResponseEntity<Timekeeping> checkin(@RequestBody Timekeeping timekeeping, HttpServletResponse response) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd");
        Account account = accountRepository.findByMail("vuongpham@gmail.com");
        timekeeping.setMail(account);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String timeStart = formatter.format(timestamp);
        timekeeping.setTimestart(java.sql.Timestamp.valueOf(timeStart));
        String dateOfData = formatterDate.format(timekeeping.getTimestart());
        String timeEnd = dateOfData + " " + "00:00:00";
        timekeeping.setTimeend(java.sql.Timestamp.valueOf(timeEnd));
        //JsonServices.dd(JsonServices.ParseToJson(timekeeping.getTimestart()), response);
//            timekeeping.setTimestart(java.sql.Timestamp.valueOf("2022-07-25 06:00:00"));
//            timekeeping.setTimeend(java.sql.Timestamp.valueOf("2022-07-25 00:00:00"));
//            List<Shift> shiftList = timekeepingServices.findShiftByTimeStart(timekeeping.getTimestart());
//            Shift shift = new Shift();
//            shift.setId(1760);
//            for (int i = 0; i < shiftList.size(); i++) {
//                // temporary
//                timekeeping.setShiftId(shiftList.get(0));
//            }
        timekeepingServices.checkin(timekeeping);
        return new ResponseEntity<>(timekeeping, HttpStatus.CREATED);

    }

    @RequestMapping(value = "/api/timekeeping/checkout", method = RequestMethod.POST)
    public ResponseEntity<Timekeeping> checkout(@RequestBody Timekeeping timekeeping, HttpServletResponse response) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd");
            String dateOfData = formatterDate.format(timekeeping.getTimestart());
            Account account = accountRepository.findByMail("vuongpham@gmail.com");
            timekeeping.setMail(account);
            timekeeping = timekeepingServices.findByMail(timekeeping.getMail());
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String timeEnd = formatter.format(timestamp);
            timekeeping.setTimeend(java.sql.Timestamp.valueOf(timeEnd));
            //timekeeping.setTimeend(java.sql.Timestamp.valueOf("2022-07-25 10:00:00"));
            //JsonServices.dd(JsonServices.ParseToJson(formatter.format(java.sql.Timestamp.valueOf(timeEnd))), response);
            Date startTime = formatter.parse(timekeeping.getTimestart().toString());
            Date endTime = formatter.parse(timekeeping.getTimeend().toString());
            Long time = endTime.getTime() - startTime.getTime();
            int workingHours = (int) TimeUnit.MILLISECONDS.toHours(time);
            JsonServices.dd(JsonServices.ParseToJson(workingHours), response);
            timekeeping.setTime(workingHours);
            timekeepingServices.checkout(timekeeping);
            return new ResponseEntity<>(timekeeping, HttpStatus.OK);
        } catch (ParseException ex) {
            Logger.getLogger(TimekeepingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(value = "/timekeeping/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable int id, Model model) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Timekeeping timekeeping = timekeepingServices.findOne(id);
        Account user = accountRepository.findByMail(timekeeping.getMail().getMail());
        model.addAttribute("timekeeping", timekeeping);
        model.addAttribute("timeStart", formatter.format(timekeeping.getTimestart()));
        model.addAttribute("timeEnd", formatter.format(timekeeping.getTimeend()));
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
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd");
                String dateOfData = formatterDate.format(timekeeping.getTimestart());
                String checkin = dateOfData + " " + request.getParameter("timeStart") + ":00";
                //JsonServices.dd(JsonServices.ParseToJson(checkin), response);
                String checkout = dateOfData + " " + request.getParameter("timeEnd") + ":00";
                timekeeping.setTimestart(java.sql.Timestamp.valueOf(checkin));
                timekeeping.setTimeend(java.sql.Timestamp.valueOf(checkout));
                Date startTime = formatter.parse(timekeeping.getTimestart().toString());
                Date endTime = formatter.parse(timekeeping.getTimeend().toString());
                Long time = endTime.getTime() - startTime.getTime();
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
