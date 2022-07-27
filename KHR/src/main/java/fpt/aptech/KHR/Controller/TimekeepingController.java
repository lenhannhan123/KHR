/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Controller;

import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Entities.Shift;
import fpt.aptech.KHR.Entities.Timekeeping;
import fpt.aptech.KHR.Entities.TimelineDetail;
import fpt.aptech.KHR.ImpServices.JsonServices;
import fpt.aptech.KHR.Routes.RouteAPI;
import fpt.aptech.KHR.Routes.RouteWeb;
import fpt.aptech.KHR.Services.IAccountRepository;
import fpt.aptech.KHR.Services.IShiftServices;
import fpt.aptech.KHR.Services.ITimekeepingServices;
import java.sql.Timestamp;

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
import org.apache.commons.lang3.time.DateUtils;
import org.json.JSONObject;

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

    @RequestMapping(value = "timekeeping/index", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("accountList", accountRepository.findAll());
        model.addAttribute("list", timekeepingServices.findAll());
        return "admin/timekeeping/index";
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

        return "admin/timekeeping/index";
    }

    @RequestMapping(value = "/api/timekeeping/checkin", method = RequestMethod.POST)
    public ResponseEntity<Timekeeping> checkin(@RequestBody Timekeeping timekeeping, HttpServletResponse response) {
        try {
            SimpleDateFormat hour = new SimpleDateFormat("HH");
            SimpleDateFormat minute = new SimpleDateFormat("mm");
            SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Account account = accountRepository.findByMail("thanhnhan@gmail.com");
            timekeeping.setMail(account);
            Date date = new Date();
            String dateOfToday = dateFormat.format(date);
            String timeOfToday = hourFormat.format(date);
            timekeeping.setTimestart(java.sql.Timestamp.valueOf(dateOfToday + " " + timeOfToday));

            List<Shift> shiftList = timekeepingServices.findShiftByDate(timekeeping.getTimestart());
            for (int i = 0; i < shiftList.size(); i++) {
                TimelineDetail timelineDetail = timekeepingServices.findTimelineDetailByMailAndShift(timekeeping.getMail(), shiftList.get(i));
                if (timelineDetail != null) {
                    //JsonServices.dd(JsonServices.ParseToJson(timelineDetail.toString()), response);
                    Shift shift = shiftServices.FindOne(timelineDetail.getIdShift().getId());
                    timekeeping.setShiftId(shift);
                }

            }

            Date timeStartOfShift = timekeeping.getShiftId().getTimestart();
            Date timeEndOfShift = timekeeping.getShiftId().getTimeend();

            String timeEnd = dateFormat.format(timeEndOfShift) + " " + "00:00:00";
            timekeeping.setTimeend(java.sql.Timestamp.valueOf(timeEnd));

            if (timekeeping.getTimestart().compareTo(timeStartOfShift) <= 0) {
                timekeeping.setTimestart(timeStartOfShift);
            } else if (dateFormat.parse(dateFormat.format(timeEndOfShift)).compareTo(dateFormat.parse(dateFormat.format(timeStartOfShift))) > 0) {
                //JsonServices.dd(JsonServices.ParseToJson("What"), response);
                int _minute = Integer.parseInt(minute.format(timekeeping.getTimestart()));
                if (_minute > 15) {
                    timekeeping.setTimestart(DateUtils.addHours(timekeeping.getTimestart(), 1));
                    timekeeping.setTimestart(DateUtils.setMinutes(timekeeping.getTimestart(), 0));
                    timekeeping.setTimestart(DateUtils.setSeconds(timekeeping.getTimestart(), 0));
                } else {
                    timekeeping.setTimestart(DateUtils.setHours(timekeeping.getTimestart(), Integer.parseInt(hour.format(timekeeping.getTimestart()))));
                    timekeeping.setTimestart(DateUtils.setMinutes(timekeeping.getTimestart(), 0));
                    timekeeping.setTimestart(DateUtils.setSeconds(timekeeping.getTimestart(), 0));
                }
            } else {
                for (int i = Integer.parseInt(hour.format(timeStartOfShift)); i < Integer.parseInt(hour.format(timeEndOfShift)); i++) {
                    if (Integer.parseInt(hour.format(timekeeping.getTimestart())) == i) {
                        int _minute = Integer.parseInt(minute.format(timekeeping.getTimestart()));
                        if (_minute > 15) {
                            timekeeping.setTimestart(DateUtils.addHours(timekeeping.getTimestart(), 1));
                            timekeeping.setTimestart(DateUtils.setMinutes(timekeeping.getTimestart(), 0));
                            timekeeping.setTimestart(DateUtils.setSeconds(timekeeping.getTimestart(), 0));
                        } else {
                            timekeeping.setTimestart(DateUtils.setHours(timekeeping.getTimestart(), i));
                            timekeeping.setTimestart(DateUtils.setMinutes(timekeeping.getTimestart(), 0));
                            timekeeping.setTimestart(DateUtils.setSeconds(timekeeping.getTimestart(), 0));
                        }
                    }
                }
            }

            timekeepingServices.checkin(timekeeping);
            return new ResponseEntity<>(timekeeping, HttpStatus.CREATED);
        } catch (ParseException ex) {
            Logger.getLogger(TimekeepingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/api/timekeeping/checkout", method = RequestMethod.POST)
    public ResponseEntity<Timekeeping> checkout(@RequestBody Timekeeping timekeeping, HttpServletResponse response
    ) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Account account = accountRepository.findByMail("thanhnhan@gmail.com");
            timekeeping.setMail(account);
            timekeeping = timekeepingServices.findByMail(timekeeping.getMail());
            Date date = new Date();
            String dateOfToday = dateFormat.format(date);
            String timeOfToday = hourFormat.format(date);
            timekeeping.setTimeend(java.sql.Timestamp.valueOf(dateOfToday + " " + timeOfToday));
            //JsonServices.dd(JsonServices.ParseToJson(shiftList.toString()), response);

            Date timeEndOfShift = timekeeping.getShiftId().getTimeend();

            if (timekeeping.getTimeend().compareTo(timeEndOfShift) >= 0) {
                timekeeping.setTimeend(timeEndOfShift);
            }

            Date beginTime = simpleDateFormat.parse(timekeeping.getTimestart().toString());
            Date endTime = simpleDateFormat.parse(timekeeping.getTimeend().toString());
            Long time = endTime.getTime() - beginTime.getTime();
            int workingHours = (int) TimeUnit.MILLISECONDS.toHours(time);
            timekeeping.setTime(workingHours);
            timekeepingServices.checkout(timekeeping);
            return new ResponseEntity<>(timekeeping, HttpStatus.OK);
        } catch (ParseException ex) {
            Logger.getLogger(TimekeepingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(value = "/timekeeping/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable int id, Model model
    ) {
        Timekeeping timekeeping = timekeepingServices.findOne(id);
        Account user = accountRepository.findByMail(timekeeping.getMail().getMail());
        model.addAttribute("timekeeping", timekeeping);
        model.addAttribute("user", user);
        return "admin/timekeeping/update";
    }

    @RequestMapping(value = "/timekeeping/edit/{id}", method = RequestMethod.POST)
    public String edit(@PathVariable int id, Model model,
            HttpServletRequest request, HttpServletResponse response
    ) {
        Timekeeping timekeeping = timekeepingServices.findOne(id);
        if (request.getParameter("action").equals("Trở lại")) {
            return index(model);
        } else {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateStartString = request.getParameter("dateStart");
                String day, month, year;
                day = dateStartString.substring(0, 2);
                month = dateStartString.substring(3, 5);
                year = dateStartString.substring(6, 10);
                String dateStart = year + "-" + month + "-" + day;
                String checkinTime = dateStart + " " + request.getParameter("timeStart") + ":00";

                String dateEndString = request.getParameter("dateEnd");
                day = dateEndString.substring(0, 2);
                month = dateEndString.substring(3, 5);
                year = dateEndString.substring(6, 10);
                String dateEnd = year + "-" + month + "-" + day;
                String checkoutTime = dateEnd + " " + request.getParameter("timeEnd") + ":00";

                timekeeping.setTimestart(java.sql.Timestamp.valueOf(checkinTime));
                timekeeping.setTimeend(java.sql.Timestamp.valueOf(checkoutTime));
                Date beginTime = simpleDateFormat.parse(timekeeping.getTimestart().toString());
                Date endTime = simpleDateFormat.parse(timekeeping.getTimeend().toString());
                Long time = endTime.getTime() - beginTime.getTime();
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
