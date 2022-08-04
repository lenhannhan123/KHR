/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.time.DateUtils;
import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.spring.web.json.Json;

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

    @RequestMapping(value = "/api/timekeeping/year/{mail}", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getYear(@PathVariable("mail") String mail, HttpServletResponse response) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        Account account = accountRepository.findByMail(mail);
        List<Timekeeping> timekeepings = timekeepingServices.findByAccount(account);
        List<String> years = new ArrayList<>();

        for (int i = 0; i < timekeepings.size(); i++) {
            if (!years.contains(simpleDateFormat.format(timekeepings.get(i).getTimestart()))) {
                years.add(simpleDateFormat.format(timekeepings.get(i).getTimestart()));
            }
        }
        //JsonServices.dd(JsonServices.ParseToJson(years.toString()), response);
        if (timekeepings.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<String>>(years, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/api/timekeeping/findByAccount/{mail}", method = RequestMethod.GET)
    public ResponseEntity<List<Timekeeping>> findByAccount(@PathVariable("mail") String mail, HttpServletResponse response) {
        Account account = accountRepository.findByMail(mail);
        List<Timekeeping> timekeepings = timekeepingServices.findByAccount(account);
        Timekeeping timekeeping = timekeepingServices.findByMail(account);
        //JsonServices.dd(JsonServices.ParseToJson(timekeepings.toString()), response);
        return new ResponseEntity<List<Timekeeping>>(timekeepings, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/timekeeping/accountList", method = RequestMethod.GET)
    public ResponseEntity<List<Account>> accountList() {
        List<Account> accountList = accountRepository.findAll();
        if (accountList.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Account>>(accountList, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/api/timekeeping/findAllByDate", method = RequestMethod.GET)
    public ResponseEntity<List<Timekeeping>> findAllByDate(@RequestParam("month") int month, @RequestParam("year") int year) {
        List<Timekeeping> timekeepingList = timekeepingServices.findAllByDate(month, year);
        if (timekeepingList.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Timekeeping>>(timekeepingList, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/api/timekeeping/checkin/{mail}", method = RequestMethod.POST)
    public ResponseEntity<Timekeeping> checkin(@RequestBody Timekeeping timekeeping, @PathVariable("mail") String _mail, HttpServletResponse response) {
        Account account = accountRepository.findByMail(_mail);
        timekeeping.setMail(account);
//            JsonServices.dd(JsonServices.ParseToJson(_account), response);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat hour = new SimpleDateFormat("HH");
        SimpleDateFormat minute = new SimpleDateFormat("mm");
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String dateOfToday = dateFormat.format(date);
        String timeOfToday = hourFormat.format(date);
        timekeeping.setTimestart(java.sql.Timestamp.valueOf(dateOfToday + " " + timeOfToday));
        List<TimelineDetail> timelineDetailList = new ArrayList<>();
        List<Shift> shiftList = timekeepingServices.findShiftByDate(timekeeping.getTimestart());

        for (int i = 0; i < shiftList.size(); i++) {
            TimelineDetail timelineDetail = timekeepingServices.findTimelineDetailByMailAndShift(timekeeping.getMail(), shiftList.get(i));
            if (timelineDetail != null && !timelineDetailList.contains(timelineDetail)) {
                timelineDetailList.add(timelineDetail);
            }
        }

        List<Shift> tempList = new ArrayList<>();
        for (int i = 0; i < timelineDetailList.size(); i++) {
//            Shift shift = shiftServices.FindOne(timelineDetailList.get(i).getIdShift().getId());
//            if (!tempList.contains(shift)) {
//                tempList.add(shift);
//            }
        }

        List<Timekeeping> timekeepings = timekeepingServices.findAll();
        for (int i = 0; i < timekeepings.size(); i++) {
            for (int j = 0; j < tempList.size(); j++) {
                if (timekeepings.get(i).getShiftId().equals(tempList.get(j)) && timekeepings.get(i).getMail().equals(timekeeping.getMail())) {
                    tempList.remove(tempList.get(j));
                }
            }
        }

        int checkinHour = Integer.parseInt(hour.format(timekeeping.getTimestart()));
        //JsonServices.dd(JsonServices.ParseToJson(checkinHour), response);
        //int checkinMinute = Integer.parseInt(minute.format(timekeeping.getTimestart()));
//            Long time = endTime.getTime() - beginTime.getTime();
//            int workingHours = (int) TimeUnit.MILLISECONDS.toHours(time);

        for (int i = 0; i < tempList.size(); i++) {
            int hourStartOfShift = Integer.parseInt(hour.format(tempList.get(i).getTimestart()));
            int hourEndOfShift = Integer.parseInt(hour.format(tempList.get(i).getTimeend()));
            Date beginTime = timekeeping.getTimestart();
            dateOfToday = dateFormat.format(tempList.get(i).getTimestart());
            timeOfToday = hourFormat.format(tempList.get(i).getTimestart());
            Date endTime = java.sql.Timestamp.valueOf(dateOfToday + " " + timeOfToday);
            Long time = endTime.getTime() - beginTime.getTime();
            int checkinMinute = (int) TimeUnit.MILLISECONDS.toMinutes(time);
            JsonServices.dd(JsonServices.ParseToJson(checkinHour + " " + hourStartOfShift + " " + hourEndOfShift), response);   
            if (checkinHour >= hourStartOfShift && checkinHour <= hourEndOfShift && hourEndOfShift - checkinHour >= 1) {
                Shift shift = shiftServices.FindOne(tempList.get(i).getId());
                timekeeping.setShiftId(shift);
                break;
            } else if (checkinMinute <= 15 && checkinHour <= hourEndOfShift && hourEndOfShift - checkinHour >= 1) {
                Shift shift = shiftServices.FindOne(tempList.get(i).getId());
                timekeeping.setShiftId(shift);
                break;
            } else if(dateFormat.format(tempList.get(i).getTimestart()).compareTo(dateFormat.format(tempList.get(i).getTimeend())) <= 0) {
                 JsonServices.dd(JsonServices.ParseToJson(checkinMinute), response);           
            }

        }

        Date timeEndOfShift = timekeeping.getShiftId().getTimeend();
        String timeEnd = dateFormat.format(timeEndOfShift) + " " + "00:00:00";
        timekeeping.setTimeend(java.sql.Timestamp.valueOf(timeEnd));

//            if (timekeeping.getTimestart().compareTo(timeStartOfShift) <= 0) {
//                timekeeping.setTimestart(timeStartOfShift);
//            } else if (dateFormat.parse(dateFormat.format(timeEndOfShift)).compareTo(dateFormat.parse(dateFormat.format(timeStartOfShift))) > 0) {
//                int _minute = Integer.parseInt(minute.format(timekeeping.getTimestart()));
//                if (_minute > 15) {
//                    timekeeping.setTimestart(DateUtils.addHours(timekeeping.getTimestart(), 1));
//                    timekeeping.setTimestart(DateUtils.setMinutes(timekeeping.getTimestart(), 0));
//                    timekeeping.setTimestart(DateUtils.setSeconds(timekeeping.getTimestart(), 0));
//                } else {
//                    timekeeping.setTimestart(DateUtils.setHours(timekeeping.getTimestart(), Integer.parseInt(hour.format(timekeeping.getTimestart()))));
//                    timekeeping.setTimestart(DateUtils.setMinutes(timekeeping.getTimestart(), 0));
//                    timekeeping.setTimestart(DateUtils.setSeconds(timekeeping.getTimestart(), 0));
//                }
//            } else {
//                for (int i = Integer.parseInt(hour.format(timeStartOfShift)); i < Integer.parseInt(hour.format(timeEndOfShift)); i++) {
//                    if (Integer.parseInt(hour.format(timekeeping.getTimestart())) == i) {
//                        int _minute = Integer.parseInt(minute.format(timekeeping.getTimestart()));
//                        if (_minute > 15) {
//                            timekeeping.setTimestart(DateUtils.addHours(timekeeping.getTimestart(), 1));
//                            timekeeping.setTimestart(DateUtils.setMinutes(timekeeping.getTimestart(), 0));
//                            timekeeping.setTimestart(DateUtils.setSeconds(timekeeping.getTimestart(), 0));
//                        } else {
//                            timekeeping.setTimestart(DateUtils.setHours(timekeeping.getTimestart(), i));
//                            timekeeping.setTimestart(DateUtils.setMinutes(timekeeping.getTimestart(), 0));
//                            timekeeping.setTimestart(DateUtils.setSeconds(timekeeping.getTimestart(), 0));
//                        }
//                    }
//                }
//            }
        timekeepingServices.checkin(timekeeping);
        return new ResponseEntity<>(timekeeping, HttpStatus.CREATED);

    }

    @RequestMapping(value = "/api/timekeeping/checkout/{mail}", method = RequestMethod.POST)
    public ResponseEntity<Timekeeping> checkout(@RequestBody Timekeeping timekeeping, @PathVariable("mail") String _mail, HttpServletResponse response
    ) {
        SimpleDateFormat hour = new SimpleDateFormat("HH");
        SimpleDateFormat minute = new SimpleDateFormat("mm");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Account account = accountRepository.findByMail(_mail);
        timekeeping = timekeepingServices.findByMail(account);
        Date date = new Date();
        String dateOfToday = dateFormat.format(date);
        String timeOfToday = hourFormat.format(date);
        timekeeping.setTimeend(java.sql.Timestamp.valueOf(dateOfToday + " " + timeOfToday));
        //JsonServices.dd(JsonServices.ParseToJson(shiftList.toString()), response);

        //Date timeEndOfShift = timekeeping.getShiftId().getTimeend();
//        if (timekeeping.getTimeend().compareTo(timeEndOfShift) >= 0) {
//            timekeeping.setTimeend(timeEndOfShift);
//        }
        int checkinHour = Integer.parseInt(hour.format(timekeeping.getTimestart()));
        int checkinMinute = Integer.parseInt(minute.format(timekeeping.getTimestart()));
        int checkoutHour = Integer.parseInt(hour.format(timekeeping.getTimeend()));
        if (checkinMinute > 15 && checkinHour >= Integer.parseInt(hour.format(timekeeping.getShiftId().getTimestart()))) {
            checkinHour += 1;
        } else {
            checkinHour = Integer.parseInt(hour.format(timekeeping.getShiftId().getTimestart()));
        }

        if (timekeeping.getTimeend().compareTo(timekeeping.getShiftId().getTimeend()) >= 0) {
            timekeeping.setTime(Integer.parseInt(hour.format(timekeeping.getShiftId().getTimeend())) - checkinHour);
        } else {
            timekeeping.setTime(checkoutHour - checkinHour);
        }
//        JsonServices.dd(JsonServices.ParseToJson(checkoutHour + " " + checkinHour), response);
//            Date beginTime = simpleDateFormat.parse(timekeeping.getTimestart().toString());
//            Date endTime = simpleDateFormat.parse(timekeeping.getTimeend().toString());
//            Long time = endTime.getTime() - beginTime.getTime();
//            int workingHours = (int) TimeUnit.MILLISECONDS.toHours(time);
//            timekeeping.setTime(workingHours);
        timekeepingServices.checkout(timekeeping);
        return new ResponseEntity<>(timekeeping, HttpStatus.OK);

    }

    @RequestMapping(value = "/timekeeping/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable int id, Model model
    ) {
        Timekeeping timekeeping = timekeepingServices.findOne(id);
        Account user = accountRepository.findByMail(timekeeping.getMail().getMail());
        model.addAttribute("timekeeping", timekeeping);
        model.addAttribute("user", user);
        return "timekeeping/update";
    }

    @RequestMapping(value = "/timekeeping/edit/{id}", method = RequestMethod.POST)
    public String edit(@PathVariable int id, Model model,
            HttpServletRequest request, HttpServletResponse response
    ) {
        Timekeeping timekeeping = timekeepingServices.findOne(id);
        if (request.getParameter("action").equals("Trở lại")) {
            return index(model);
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat hour = new SimpleDateFormat("HH");
            SimpleDateFormat minute = new SimpleDateFormat("mm");
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
            int checkinHour = Integer.parseInt(hour.format(timekeeping.getTimestart()));
            int checkinMinute = Integer.parseInt(minute.format(timekeeping.getTimestart()));
            int checkoutHour = Integer.parseInt(hour.format(timekeeping.getTimeend()));
            if (checkinMinute > 15 && checkinHour >= Integer.parseInt(hour.format(timekeeping.getShiftId().getTimestart()))) {
                checkinHour += 1;
            } else {
                checkinHour = Integer.parseInt(hour.format(timekeeping.getShiftId().getTimestart()));
            }

            if (timekeeping.getTimeend().compareTo(timekeeping.getShiftId().getTimeend()) >= 0) {
                timekeeping.setTime(Integer.parseInt(hour.format(timekeeping.getShiftId().getTimeend())) - checkinHour);
            } else {
                timekeeping.setTime(checkoutHour - checkinHour);
            }
            //JsonServices.dd(JsonServices.ParseToJson(checkoutHour + " " + checkinHour), response);
            timekeepingServices.checkout(timekeeping);

        }
        return update(timekeeping.getId(), model);
    }

}
