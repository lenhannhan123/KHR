/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Controller;

import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Entities.Shift;
import fpt.aptech.KHR.Entities.Timekeeping;
import fpt.aptech.KHR.Routes.RouteAPI;
import fpt.aptech.KHR.Routes.RouteWeb;
import fpt.aptech.KHR.Services.IAccountRepository;
import fpt.aptech.KHR.Services.IShiftServices;
import fpt.aptech.KHR.Services.ITimekeepingServices;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(value = {RouteWeb.TimekeepingIndexURL}, method = RequestMethod.GET)
    public ResponseEntity<List<Timekeeping>> index(Model model) {
        model.addAttribute("list", timekeepingServices.findAll());
        return new ResponseEntity<>(timekeepingServices.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = {RouteAPI.checkinAPI}, method = RequestMethod.GET)
    ResponseEntity<String> checkin() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        String strDate = sdfDate.format(date);
        return new ResponseEntity<String>(strDate, HttpStatus.OK);
    }

    @RequestMapping(value = {RouteAPI.checkoutAPI}, method = RequestMethod.GET)
    ResponseEntity<String> checkout() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        String strDate = sdfDate.format(date);
        return new ResponseEntity<String>(strDate, HttpStatus.OK);
    }

    @RequestMapping(value = "timekeeping/add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("timekeeping", new Timekeeping());
        return "timekeeping/create";
    }

    @RequestMapping(value = "/api/timekeeping/findAccount", method = RequestMethod.GET)
    public ResponseEntity<Account> findByMail() {
        try {
            Account account = accountRepository.findByMail("vuongpham@gmail.com");
            return new ResponseEntity<>(account, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "/api/timekeeping/save", method = RequestMethod.POST)
    public ResponseEntity<Timekeeping> insert(@RequestBody Timekeeping timekeeping) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            //String time = formatter.format(new Date());
            //String date = formatter.format(java.sql.Time.valueOf(new Date().toString()));
            Account account = accountRepository.findByMail("vuongpham@gmail.com");
            timekeeping.setMail(account);
            timekeeping.setTimestart(java.sql.Time.valueOf("07:30:00"));
            timekeeping.setTimeend(java.sql.Time.valueOf("11:30:00"));
            Date timeStart = formatter.parse(timekeeping.getTimestart().toString());
            Date timeEnd = formatter.parse(timekeeping.getTimeend().toString());
            Long time = timeEnd.getTime() - timeStart.getTime();
            int workingHours = (int) TimeUnit.MILLISECONDS.toHours(time);
            timekeeping.setTime(workingHours);
            timekeepingServices.saveTimekeeping(timekeeping);
            return new ResponseEntity<>(timekeeping, HttpStatus.CREATED);
        } catch (ParseException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

//    @PostMapping(value = "timekeeping/save")
//    public ResponseEntity<Timekeeping> insert(@RequestBody Timekeeping timekeeping) {
//        try {
//            timekeepingServices.saveTimekeeping(timekeeping);
//            return new ResponseEntity<>(timekeeping, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
