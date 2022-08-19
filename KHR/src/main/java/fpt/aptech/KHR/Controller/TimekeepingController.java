/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.util.DateTime;
import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Entities.AccountPosition;
import fpt.aptech.KHR.Entities.Position;
import fpt.aptech.KHR.Entities.Shift;
import fpt.aptech.KHR.Entities.Timekeeping;
import fpt.aptech.KHR.Entities.Timeline;
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
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
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
    public String index(Model model, HttpServletRequest request) {
        request.setAttribute("sidebar","5");
        model.addAttribute("accountList", accountRepository.findAll());
        model.addAttribute("list", timekeepingServices.findAll());
        return "admin/timekeeping/index";
    }

    @RequestMapping(value = "/timekeeping/autocomplete", method = RequestMethod.GET)
    public ResponseEntity<List<String>> autocomplete(@RequestParam("value") String input, HttpServletRequest request) {
        request.setAttribute("sidebar","5");
        return new ResponseEntity<List<String>>(timekeepingServices.autocomplete(input), HttpStatus.OK);
    }

    @RequestMapping(value = "/timekeeping/search", method = RequestMethod.GET)
    public String search(HttpServletRequest request, Model model) {
        model.addAttribute("accountList", accountRepository.findAll());
        request.setAttribute("sidebar","5");
        if (request.getParameter("mail").equals("")) {
            return index(model, request);
        } else {
            model.addAttribute("list", timekeepingServices.search(request.getParameter("mail")));
        }
        return "admin/timekeeping/index";
    }

    @RequestMapping(value = "/timekeeping/create", method = RequestMethod.GET)
    public String create(Model model, HttpServletRequest request) {
        request.setAttribute("sidebar","5");
        return "admin/timekeeping/create";
    }

//    @RequestMapping(value = "/timekeeping//{id}", method = RequestMethod.GET)
//    public ResponseEntity<String> getShifts(@PathVariable("id") String id) {
//        //Shift shift = shiftServices.FindOne(id);
//        Account account = accountRepository.findByMail(id);
//        return new ResponseEntity<String>(account.getFullname(), HttpStatus.OK);
//    }
//    @RequestMapping(value = "/timekeeping/save", method = RequestMethod.POST)
//    public String save(Model model, HttpServletRequest request, HttpServletResponse response) {
//        try {
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
//            Timekeeping timekeeping = new Timekeeping();
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            SimpleDateFormat hour = new SimpleDateFormat("HH");
//            SimpleDateFormat minute = new SimpleDateFormat("mm");
//            String dateStartString = request.getParameter("dateStart");
//            String day, month, year;
//            day = dateStartString.substring(0, 2);
//            month = dateStartString.substring(3, 5);
//            year = dateStartString.substring(6, 10);
//            String dateStart = year + "-" + month + "-" + day;
//            String checkinTime = dateStart + " " + request.getParameter("timeStart") + ":00";
//            String dateEndString = request.getParameter("dateEnd");
//            day = dateEndString.substring(0, 2);
//            month = dateEndString.substring(3, 5);
//            year = dateEndString.substring(6, 10);
//            String dateEnd = year + "-" + month + "-" + day;
//            String checkoutTime = dateEnd + " " + request.getParameter("timeEnd") + ":00";
////          Account account = accountRepository.findByMail(_mail);
////          timekeeping.setMail(account);
//            Account account = new Account();
//            account.setMail(request.getParameter("mail"));
//            timekeeping.setMail(account);
//            timekeeping.setTimestart(java.sql.Timestamp.valueOf(checkinTime));
//            timekeeping.setTimeend(java.sql.Timestamp.valueOf(checkoutTime));
//
//            List<Shift> shiftList = timekeepingServices.findShiftByDate(timekeeping.getTimestart());
//            Date date = dateFormat.parse(timekeeping.getTimestart().toString());
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(date);
//            calendar.add(Calendar.DATE, -1);
//            Date yesterday = calendar.getTime();
//
//            List<Shift> shiftListDateBefore = timekeepingServices.findShiftByDate(simpleDateFormat.parse(simpleDateFormat.format(yesterday)));
//            shiftList.addAll(shiftListDateBefore);
//
//            List<TimelineDetail> timelineDetailList = new ArrayList<>();
//            for (int i = 0; i < shiftList.size(); i++) {
//                List<TimelineDetail> _timelineDetailList = new ArrayList<>();
//                String shiftCode = shiftList.get(i).getShiftcode().toString();
//                if (shiftCode.length() == 4) {
//                    _timelineDetailList = timekeepingServices.findTimelineDetailList(timekeeping.getMail(), shiftList.get(i).getShiftcode() - 1000, shiftList.get(i).getIdPosition(), shiftList.get(i).getIdTimeline());
//                } else {
//                    _timelineDetailList = timekeepingServices.findTimelineDetailList(timekeeping.getMail(), shiftList.get(i).getShiftcode() - 100, shiftList.get(i).getIdPosition(), shiftList.get(i).getIdTimeline());
//                }
//
//                for (TimelineDetail item : _timelineDetailList) {
//                    if (!timelineDetailList.contains(item)) {
//                        timelineDetailList.add(item);
//                    }
//                }
//            }
//
//            List<Shift> tempList = new ArrayList<>();
//            for (int i = 0; i < timelineDetailList.size(); i++) {
//                String shiftCode = String.valueOf(timelineDetailList.get(i).getShiftCode());
//                if (shiftCode.length() > 1) {
//                    Shift shift = timekeepingServices.findShiftByShiftCode(timelineDetailList.get(i).getShiftCode() + 1000, timekeeping.getTimestart(), timelineDetailList.get(i).getIdPosition());
//                    if (shift != null) {
//                        tempList.add(shift);
//                    }
//                } else {
//                    Shift shift = timekeepingServices.findShiftByShiftCode(timelineDetailList.get(i).getShiftCode() + 100, timekeeping.getTimestart(), timelineDetailList.get(i).getIdPosition());
//                    if (shift != null) {
//                        tempList.add(shift);
//                    }
//                }
//
//            }
//
//            //JsonServices.dd(JsonServices.ParseToJson(timelineDetailList.toString() + " " + tempList.toString()), response);
//            for (int i = 0; i < timelineDetailList.size(); i++) {
//                Shift shiftDateBefore = timekeepingServices.findShiftByShiftCode(timelineDetailList.get(i).getShiftCode() + 100, simpleDateFormat.parse(simpleDateFormat.format(yesterday)), timelineDetailList.get(i).getIdPosition());
//                if (shiftDateBefore != null) {
//                    tempList.add(shiftDateBefore);
//                }
//            }
//
//            List<Timekeeping> timekeepings = timekeepingServices.findAll();
//            for (int i = 0; i < timekeepings.size(); i++) {
//                for (int j = 0; j < tempList.size(); j++) {
//                    if (timekeepings.get(i).getShiftId().equals(tempList.get(j)) && timekeepings.get(i).getMail().equals(timekeeping.getMail())) {
//                        tempList.remove(tempList.get(j));
//                    }
//                }
//            }
//
//            //JsonServices.dd(JsonServices.ParseToJson(tempList.toString()), response);
//            return index(model);
//        } catch (ParseException ex) {
//            Logger.getLogger(TimekeepingController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
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
    public ResponseEntity<List<Timekeeping>> findAllByDate(@RequestParam("mail") Account mail, @RequestParam("month") int month, @RequestParam("year") int year) {
        List<Timekeeping> timekeepingList = timekeepingServices.findAllByDate(mail, month, year);
        if (timekeepingList.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Timekeeping>>(timekeepingList, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/api/timekeeping/detailId", method = RequestMethod.GET)
    public ResponseEntity<Integer> detailId(@RequestParam("id") int id, HttpServletResponse response) {
        return new ResponseEntity<Integer>(timekeepingServices.detailId(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/api/timekeeping/detail", method = RequestMethod.GET)
    public ResponseEntity<List<String>> detail(@RequestParam("shiftId") int id, HttpServletResponse response) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
        Shift shift = timekeepingServices.findShiftByTimekeeping(id);
        List<String> dataList = new ArrayList<>();
        dataList.add(simpleDateFormat.format(shift.getTimestart()));
        dataList.add(hourFormat.format(shift.getTimestart()));
        Position position = timekeepingServices.findPositionAccountById(shift.getIdPosition().getId());
        dataList.add(position.getPositionname());
//        JsonServices.dd(JsonServices.ParseToJson(dataList.toString()), response);
        if (dataList.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<String>>(dataList, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/api/timekeeping/checkin/{mail}", method = RequestMethod.POST)
    public ResponseEntity<Timekeeping> checkin(@RequestBody Timekeeping timekeeping, @PathVariable("mail") String _mail, HttpServletResponse response) {
        Account account = accountRepository.findByMail(_mail);
        timekeeping.setMail(account);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat hour = new SimpleDateFormat("HH");
        SimpleDateFormat minute = new SimpleDateFormat("mm");
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String dateOfToday = dateFormat.format(date);
        String timeOfToday = hourFormat.format(date);
        timekeeping.setTimestart(java.sql.Timestamp.valueOf(dateOfToday + " " + timeOfToday));

//        dateOfToday = dateFormat.format(dateBefore);
//        JsonServices.dd(JsonServices.ParseToJson(simpleDateFormat.format(dateBefore)), response);
//        List<Timeline> timelineList = timekeepingServices.findTimelineList();
//        List<Timeline> tempList = timekeepingServices.findTimelineList();
        Timeline timeline = timekeepingServices.findTimelineByDate(timekeeping.getTimestart());

        timekeeping.setIdTimeline(timeline);
        int day = timekeeping.getTimestart().getDay();

        List<Integer> shiftCodeOnDay = new ArrayList<>();
        int maxShiftCodeOnday = 0;

        switch (day) {
            case 1:
                maxShiftCodeOnday = 5;
                break;
            case 2:
                maxShiftCodeOnday = 10;
                break;
            case 3:
                maxShiftCodeOnday = 15;
                break;
            case 4:
                maxShiftCodeOnday = 20;
                break;
            case 5:
                maxShiftCodeOnday = 25;
                break;
            case 6:
                maxShiftCodeOnday = 30;
                break;
            case 0:
                maxShiftCodeOnday = 35;
                break;
            default:
                break;
        }

        for (int i = maxShiftCodeOnday - 5; i < maxShiftCodeOnday; i++) {
            shiftCodeOnDay.add(i);
        }

        List<TimelineDetail> timelineDetailList = new ArrayList<>();
        for (int i = 0; i < shiftCodeOnDay.size(); i++) {
            TimelineDetail timelineDetail = timekeepingServices.findTimelineDetail(account, shiftCodeOnDay.get(i), timeline);
            if (timelineDetail != null) {
                timelineDetailList.add(timelineDetail);
            }
        }

//        JsonServices.dd(JsonServices.ParseToJson(simpleDateFormat.format(timekeeping.getTimestart())), response);
        //JsonServices.dd(JsonServices.ParseToJson(timelineDetailList.toString()), response);
        HashMap<Date, Integer> shiftOnDay = new HashMap<Date, Integer>();
        HashMap<Date, Date> hashMap = new HashMap<Date, Date>();
        for (int i = 0; i < timelineDetailList.size(); i++) {
            if (timelineDetailList.get(i).getShiftCode() % 5 == 0) {
                String timeBeginWork = "06:00:00";
                String dateBeginWork = dateFormat.format(timekeeping.getTimestart());
                String timeEndWork = "10:00:00";
                String dateEndWork = dateFormat.format(timekeeping.getTimestart());
                shiftOnDay.put(java.sql.Timestamp.valueOf(dateBeginWork + " " + timeBeginWork), timelineDetailList.get(i).getShiftCode());
                hashMap.put(java.sql.Timestamp.valueOf(dateBeginWork + " " + timeBeginWork), java.sql.Timestamp.valueOf(dateEndWork + " " + timeEndWork));

            } else if (timelineDetailList.get(i).getShiftCode() % 5 == 1) {
                String timeBeginWork = "10:00:00";
                String dateBeginWork = dateFormat.format(timekeeping.getTimestart());
                String timeEndWork = "14:00:00";
                String dateEndWork = dateFormat.format(timekeeping.getTimestart());
                shiftOnDay.put(java.sql.Timestamp.valueOf(dateBeginWork + " " + timeBeginWork), timelineDetailList.get(i).getShiftCode());
                hashMap.put(java.sql.Timestamp.valueOf(dateBeginWork + " " + timeBeginWork), java.sql.Timestamp.valueOf(dateEndWork + " " + timeEndWork));

            } else if (timelineDetailList.get(i).getShiftCode() % 5 == 2) {
                String timeBeginWork = "14:00:00";
                String dateBeginWork = dateFormat.format(timekeeping.getTimestart());
                String timeEndWork = "18:00:00";
                String dateEndWork = dateFormat.format(timekeeping.getTimestart());
                shiftOnDay.put(java.sql.Timestamp.valueOf(dateBeginWork + " " + timeBeginWork), timelineDetailList.get(i).getShiftCode());
                hashMap.put(java.sql.Timestamp.valueOf(dateBeginWork + " " + timeBeginWork), java.sql.Timestamp.valueOf(dateEndWork + " " + timeEndWork));

            } else if (timelineDetailList.get(i).getShiftCode() % 5 == 3) {
                String timeBeginWork = "18:00:00";
                String dateBeginWork = dateFormat.format(timekeeping.getTimestart());
                String timeEndWork = "22:00:00";
                String dateEndWork = dateFormat.format(timekeeping.getTimestart());
                shiftOnDay.put(java.sql.Timestamp.valueOf(dateBeginWork + " " + timeBeginWork), timelineDetailList.get(i).getShiftCode());
                hashMap.put(java.sql.Timestamp.valueOf(dateBeginWork + " " + timeBeginWork), java.sql.Timestamp.valueOf(dateEndWork + " " + timeEndWork));

            } else if (timelineDetailList.get(i).getShiftCode() % 5 == 4) {
                String timeBeginWork = "22:00:00";
                String dateBeginWork = dateFormat.format(timekeeping.getTimestart());
                String timeEndWork = "06:00:00";
                String dateEndWork = dateFormat.format(timekeeping.getTimestart().getTime() + (1000 * 60 * 60 * 24));
                shiftOnDay.put(java.sql.Timestamp.valueOf(dateBeginWork + " " + timeBeginWork), timelineDetailList.get(i).getShiftCode());
                hashMap.put(java.sql.Timestamp.valueOf(dateBeginWork + " " + timeBeginWork), java.sql.Timestamp.valueOf(dateEndWork + " " + timeEndWork));

            }
        }

        int root = 0;
        switch (day) {
            case 0:
                root = 29;
                break;
            case 1:
                root = 34;
                break;
            case 2:
                root = 4;
                break;
            case 3:
                root = 9;
                break;
            case 4:
                root = 14;
                break;
            case 5:
                root = 19;
                break;
            case 6:
                root = 24;
                break;
            default:
                break;
        }

        TimelineDetail timelineDetail = null;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date yesterday = calendar.getTime();
        Timeline beforeTimeline = timekeepingServices.findTimelineByDate(yesterday);
        //JsonServices.dd(JsonServices.ParseToJson(beforeTimeline.toString()), response);
        if (day == 1) {
            timelineDetail = timekeepingServices.findTimelineDetail(account, root, beforeTimeline);
        } else {
            timelineDetail = timekeepingServices.findTimelineDetail(account, root, timeline);
        }

        if (timelineDetail != null) {
            String timeBeginWork = "22:00:00";
            String dateBeginWork = dateFormat.format(timekeeping.getTimestart().getTime() - (1000 * 60 * 60 * 24));
            String timeEndWork = "06:00:00";
            String dateEndWork = dateFormat.format(timekeeping.getTimestart());
            shiftOnDay.put(java.sql.Timestamp.valueOf(dateBeginWork + " " + timeBeginWork), root);
            hashMap.put(java.sql.Timestamp.valueOf(dateBeginWork + " " + timeBeginWork), java.sql.Timestamp.valueOf(dateEndWork + " " + timeEndWork));
        }

        List<Timekeeping> timekeepings = timekeepingServices.findAll();
        for (int i = 0; i < timekeepings.size(); i++) {
            for (Map.Entry<Date, Integer> entry : shiftOnDay.entrySet()) {
                if (timekeepings.get(i).getMail().equals(account) && timekeepings.get(i).getIdTimeline().equals(timeline) && timekeepings.get(i).getShiftCode() == entry.getValue()
                        || timekeepings.get(i).getMail().equals(account) && timekeepings.get(i).getIdTimeline().equals(beforeTimeline) && timekeepings.get(i).getShiftCode() == entry.getValue()) {
                    for (Map.Entry<Date, Date> _entry : hashMap.entrySet()) {
                        if (_entry.getKey().compareTo(entry.getKey()) == 0) {
                            hashMap.remove(_entry.getKey());
                            break;
                        }
                    }
                }
            }

        }

        //JsonServices.dd(JsonServices.ParseToJson(hashMap.toString()), response);
        int realMinute = timekeeping.getTimestart().getMinutes();
        int realSecond = timekeeping.getTimestart().getSeconds();
//        JsonServices.dd(JsonServices.ParseToJson(hashMap.toString()), response);

        List<String> viewList = new ArrayList<>();
        for (Map.Entry<Date, Date> entry : hashMap.entrySet()) {
            Date key = entry.getKey();
            Date value = entry.getValue();

            Date beginTime = timekeeping.getTimestart();
            dateOfToday = dateFormat.format(key);
            timeOfToday = hourFormat.format(key);
            Date endTime = java.sql.Timestamp.valueOf(dateOfToday + " " + timeOfToday);
            Long time = endTime.getTime() - beginTime.getTime();
            int checkinMinute = (int) TimeUnit.MILLISECONDS.toMinutes(time);
            //viewList.add(simpleDateFormat.format(key) + " " + simpleDateFormat.format(value));
            timekeeping.getTimestart().setMinutes(0);
            timekeeping.getTimestart().setSeconds(0);
            if (realMinute > 15) {
                time = value.getTime() - timekeeping.getTimestart().getTime() - 3600 * 1000;
            } else {
                time = value.getTime() - timekeeping.getTimestart().getTime();
            }
            int diff = (int) TimeUnit.MILLISECONDS.toMinutes(time) / 60;
            //viewList.add(String.valueOf(checkinMinute));
            //JsonServices.dd(JsonServices.ParseToJson(diff), response);
            //JsonServices.dd(JsonServices.ParseToJson(realMinute), response);
            Long today = timekeeping.getTimestart().getTime();
            Long _beginTime = key.getTime();
            Long _afterTime = value.getTime();
            viewList.add(dateFormat.format(key) + " " + dateFormat.format(value));

            if (today >= _beginTime && today <= _afterTime && diff >= 1) {
                for (Map.Entry<Date, Integer> _entry : shiftOnDay.entrySet()) {
                    if (simpleDateFormat.format(_entry.getKey()).compareTo(simpleDateFormat.format(entry.getKey())) == 0) {
                        timekeeping.setShiftCode(_entry.getValue());
                        if (dateFormat.format(key).compareTo(dateFormat.format(value)) < 0 && beforeTimeline != null) {
                            timekeeping.setIdTimeline(beforeTimeline);

                        } else {
                            timekeeping.setIdTimeline(timeline);
                        }
                        String timeEnd = dateFormat.format(value) + " " + "00:00:00";
                        timekeeping.setTimeend(java.sql.Timestamp.valueOf(timeEnd));
                        timekeeping.setTime(0);
                        timekeeping.getTimestart().setMinutes(realMinute);
                        timekeeping.getTimestart().setSeconds(realSecond);
                        timekeepingServices.checkin(timekeeping);
                        return new ResponseEntity<>(timekeeping, HttpStatus.CREATED);
                    }
                }

            } else if (checkinMinute <= 15 && today <= _afterTime && diff >= 1) {
                for (Map.Entry<Date, Integer> _entry : shiftOnDay.entrySet()) {
                    if (simpleDateFormat.format(_entry.getKey()).compareTo(simpleDateFormat.format(entry.getKey())) == 0) {
                        timekeeping.setShiftCode(_entry.getValue());
                        if (dateFormat.format(key).compareTo(dateFormat.format(value)) < 0 && beforeTimeline != null) {
                            timekeeping.setIdTimeline(beforeTimeline);

                        } else {
                            timekeeping.setIdTimeline(timeline);
                        }
                        String timeEnd = dateFormat.format(value) + " " + "00:00:00";
                        timekeeping.setTimeend(java.sql.Timestamp.valueOf(timeEnd));
                        timekeeping.setTime(0);
                        timekeeping.getTimestart().setMinutes(realMinute);
                        timekeeping.getTimestart().setSeconds(realSecond);
                        timekeepingServices.checkin(timekeeping);
                        return new ResponseEntity<>(timekeeping, HttpStatus.CREATED);
                    }
                }
            }
            timekeeping.getTimestart().setMinutes(realMinute);
            timekeeping.getTimestart().setSeconds(realSecond);
        }
        JsonServices.dd(JsonServices.ParseToJson(hashMap.toString()), response);
        return new ResponseEntity<>(timekeeping, HttpStatus.CREATED);

    }

    @RequestMapping(value = "/api/timekeeping/checkout/{mail}", method = RequestMethod.POST)
    public ResponseEntity<Timekeeping> checkout(@RequestBody Timekeeping timekeeping, @PathVariable("mail") String _mail, HttpServletResponse response
    ) {
        SimpleDateFormat hour = new SimpleDateFormat("HH");
        SimpleDateFormat minute = new SimpleDateFormat("mm");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat _simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Account account = accountRepository.findByMail(_mail);
        timekeeping = timekeepingServices.findByMail(account);
        Date date = new Date();
        String dateOfToday = dateFormat.format(date);
        String timeOfToday = hourFormat.format(date);
        timekeeping.setTimeend(java.sql.Timestamp.valueOf(dateOfToday + " " + timeOfToday));

        Timeline timeline = timekeepingServices.findTimelineByDate(timekeeping.getTimestart());
        int day = timekeeping.getTimestart().getDay();

        List<Integer> shiftCodeOnDay = new ArrayList<>();
        int maxShiftCodeOnday = 0;

        switch (day) {
            case 1:
                maxShiftCodeOnday = 5;
                break;
            case 2:
                maxShiftCodeOnday = 10;
                break;
            case 3:
                maxShiftCodeOnday = 15;
                break;
            case 4:
                maxShiftCodeOnday = 20;
                break;
            case 5:
                maxShiftCodeOnday = 25;
                break;
            case 6:
                maxShiftCodeOnday = 30;
                break;
            case 0:
                maxShiftCodeOnday = 35;
                break;
            default:
                break;
        }

        for (int i = maxShiftCodeOnday - 5; i < maxShiftCodeOnday; i++) {
            shiftCodeOnDay.add(i);
        }

        List<TimelineDetail> timelineDetailList = new ArrayList<>();
        for (int i = 0; i < shiftCodeOnDay.size(); i++) {
            TimelineDetail timelineDetail = timekeepingServices.findTimelineDetail(account, shiftCodeOnDay.get(i), timeline);
            if (timelineDetail != null) {
                timelineDetailList.add(timelineDetail);
            }
        }

//        JsonServices.dd(JsonServices.ParseToJson(simpleDateFormat.format(timekeeping.getTimestart())), response);
        //JsonServices.dd(JsonServices.ParseToJson(timelineDetailList.toString()), response);
        HashMap<Date, Integer> shiftOnDay = new HashMap<Date, Integer>();
        HashMap<Date, Date> hashMap = new HashMap<Date, Date>();
        for (int i = 0; i < timelineDetailList.size(); i++) {
            if (timelineDetailList.get(i).getShiftCode() % 5 == 0) {
                String timeBeginWork = "06:00:00";
                String dateBeginWork = dateFormat.format(timekeeping.getTimestart());
                String timeEndWork = "10:00:00";
                String dateEndWork = dateFormat.format(timekeeping.getTimestart());
                shiftOnDay.put(java.sql.Timestamp.valueOf(dateBeginWork + " " + timeBeginWork), timelineDetailList.get(i).getShiftCode());
                hashMap.put(java.sql.Timestamp.valueOf(dateBeginWork + " " + timeBeginWork), java.sql.Timestamp.valueOf(dateEndWork + " " + timeEndWork));

            } else if (timelineDetailList.get(i).getShiftCode() % 5 == 1) {
                String timeBeginWork = "10:00:00";
                String dateBeginWork = dateFormat.format(timekeeping.getTimestart());
                String timeEndWork = "14:00:00";
                String dateEndWork = dateFormat.format(timekeeping.getTimestart());
                shiftOnDay.put(java.sql.Timestamp.valueOf(dateBeginWork + " " + timeBeginWork), timelineDetailList.get(i).getShiftCode());
                hashMap.put(java.sql.Timestamp.valueOf(dateBeginWork + " " + timeBeginWork), java.sql.Timestamp.valueOf(dateEndWork + " " + timeEndWork));

            } else if (timelineDetailList.get(i).getShiftCode() % 5 == 2) {
                String timeBeginWork = "14:00:00";
                String dateBeginWork = dateFormat.format(timekeeping.getTimestart());
                String timeEndWork = "18:00:00";
                String dateEndWork = dateFormat.format(timekeeping.getTimestart());
                shiftOnDay.put(java.sql.Timestamp.valueOf(dateBeginWork + " " + timeBeginWork), timelineDetailList.get(i).getShiftCode());
                hashMap.put(java.sql.Timestamp.valueOf(dateBeginWork + " " + timeBeginWork), java.sql.Timestamp.valueOf(dateEndWork + " " + timeEndWork));

            } else if (timelineDetailList.get(i).getShiftCode() % 5 == 3) {
                String timeBeginWork = "18:00:00";
                String dateBeginWork = dateFormat.format(timekeeping.getTimestart());
                String timeEndWork = "22:00:00";
                String dateEndWork = dateFormat.format(timekeeping.getTimestart());
                shiftOnDay.put(java.sql.Timestamp.valueOf(dateBeginWork + " " + timeBeginWork), timelineDetailList.get(i).getShiftCode());
                hashMap.put(java.sql.Timestamp.valueOf(dateBeginWork + " " + timeBeginWork), java.sql.Timestamp.valueOf(dateEndWork + " " + timeEndWork));

            } else if (timelineDetailList.get(i).getShiftCode() % 5 == 4) {
                String timeBeginWork = "22:00:00";
                String dateBeginWork = dateFormat.format(timekeeping.getTimestart());
                String timeEndWork = "06:00:00";
                String dateEndWork = dateFormat.format(timekeeping.getTimestart().getTime() + (1000 * 60 * 60 * 24));
                shiftOnDay.put(java.sql.Timestamp.valueOf(dateBeginWork + " " + timeBeginWork), timelineDetailList.get(i).getShiftCode());
                hashMap.put(java.sql.Timestamp.valueOf(dateBeginWork + " " + timeBeginWork), java.sql.Timestamp.valueOf(dateEndWork + " " + timeEndWork));

            }
        }

        int root = 0;
        switch (day) {
            case 0:
                root = 29;
                break;
            case 1:
                root = 34;
                break;
            case 2:
                root = 4;
                break;
            case 3:
                root = 9;
                break;
            case 4:
                root = 14;
                break;
            case 5:
                root = 19;
                break;
            case 6:
                root = 24;
                break;
            default:
                break;
        }

        TimelineDetail timelineDetail = null;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date yesterday = calendar.getTime();
        Timeline beforeTimeline = timekeepingServices.findTimelineByDate(yesterday);
        //JsonServices.dd(JsonServices.ParseToJson(beforeTimeline.toString()), response);
        if (day == 1) {
            timelineDetail = timekeepingServices.findTimelineDetail(account, root, beforeTimeline);
        } else {
            timelineDetail = timekeepingServices.findTimelineDetail(account, root, timeline);
        }

        if (timelineDetail != null) {
            String timeBeginWork = "22:00:00";
            String dateBeginWork = dateFormat.format(timekeeping.getTimestart().getTime() - (1000 * 60 * 60 * 24));
            String timeEndWork = "06:00:00";
            String dateEndWork = dateFormat.format(timekeeping.getTimestart());
            shiftOnDay.put(java.sql.Timestamp.valueOf(dateBeginWork + " " + timeBeginWork), root);
            hashMap.put(java.sql.Timestamp.valueOf(dateBeginWork + " " + timeBeginWork), java.sql.Timestamp.valueOf(dateEndWork + " " + timeEndWork));
        }

        //JsonServices.dd(JsonServices.ParseToJson(hashMap.toString()), response); 
        List<String> viewList = new ArrayList<>();
        List<Integer> shiftCodeList = new ArrayList<>();
        Map<Integer, Integer> shiftMap = new HashMap<Integer, Integer>();
        for (int i = 0; i < timelineDetailList.size(); i++) {
            if (i + 1 < timelineDetailList.size()) {
                if (timelineDetailList.get(i).getShiftCode() + 1 == timelineDetailList.get(i + 1).getShiftCode()) {
                    shiftMap.put(timelineDetailList.get(i).getShiftCode(), timelineDetailList.get(i + 1).getShiftCode());
                }
            }

        }

        List<Date> dates = new ArrayList<>();
        for (Entry<Integer, Integer> entry : shiftMap.entrySet()) {
            int key = entry.getKey();
            int val = entry.getValue();
            shiftCodeList.add(key);
            shiftCodeList.add(val);
        }

        for (int i = 0; i < shiftCodeList.size(); i++) {
            if (i + 1 < shiftCodeList.size()) {
                if (shiftCodeList.get(i) == shiftCodeList.get(i + 1)) {
                    shiftCodeList.remove(shiftCodeList.get(i + 1));
                }
            }
        }

        for (int i = 0; i < shiftCodeList.size(); i++) {
            dates.addAll(getTimeOfWork(shiftCodeList.get(i), timekeeping));
        }

        Map<Integer, Integer> handleMap = new HashMap<Integer, Integer>();
        int count = timekeeping.getShiftCode();
        for (int i = 0; i < dates.size(); i++) {
            if (i + 1 < dates.size()) {
                if (simpleDateFormat.format(dates.get(i)).compareTo(simpleDateFormat.format(dates.get(i + 1))) == 0) {
                    //viewList.add(String.valueOf(diff));
                    //viewList.add(simpleDateFormat.format(dates.get(i)) + " " + simpleDateFormat.format(dates.get(i)));
                    if (simpleDateFormat.format(timekeeping.getTimeend()).compareTo(simpleDateFormat.format(dates.get(i + 1))) >= 0) {
                        //viewList.add(simpleDateFormat.format(dates.get(i)));
                        Long time = timekeeping.getTimeend().getTime() - dates.get(i).getTime();
                        int diff = (int) TimeUnit.MILLISECONDS.toMinutes(time) / 60;
                        //viewList.add(simpleDateFormat.format(dates.get(i + 1)) + " " + simpleDateFormat.format(dates.get(i)));                    
                        //viewList.add(String.valueOf(diff));

                        if (diff >= 1) {
                            handleMap.put(++count, diff);

                        }

                    }

                }
            }
        }

        if (handleMap.size() >= 1) {
            for (Entry<Integer, Integer> entry : handleMap.entrySet()) {
                List<Date> dateList = getTimeOfWork(entry.getKey(), timekeeping);
                timekeeping.setTimeend(dateList.get(0));
                timeHandle(timekeeping.getId(), response);
                Timekeeping item = new Timekeeping();
                item.setMail(timekeeping.getMail());
                item.setIdTimeline(timekeeping.getIdTimeline());
                item.setShiftCode(entry.getKey());
                item.setTimestart(dateList.get(1));
                item.setTimeend(java.sql.Timestamp.valueOf(dateOfToday + " " + timeOfToday));
                item.setTime(entry.getValue());
                timekeepingServices.checkin(item);
                timeHandle(timekeeping.getId(), response);
                //viewList.add(String.valueOf(entry.getKey()) + " " + String.valueOf(entry.getValue()));
                //JsonServices.dd(JsonServices.ParseToJson(dateList.toString()), response);
            }

            List<Timekeeping> timekeepings = timekeepingServices.findAll();
            List<Timekeeping> tempList = new ArrayList<>();
            for (int i = 0; i < timekeepings.size(); i++) {
                for (int j = 0; j < shiftCodeList.size(); j++) {
                    if (timekeepings.get(i).getMail().equals(timekeeping.getMail()) && timekeepings.get(i).getIdTimeline().equals(timekeeping.getIdTimeline()) && timekeepings.get(i).getShiftCode() == shiftCodeList.get(j)) {
                        tempList.add(timekeepings.get(i));
                    }
                }
            }

            for (int i = 0; i < shiftCodeList.size(); i++) {
                for (int j = 0; j < tempList.size(); j++) {
                    if (tempList.get(j).getShiftCode() == shiftCodeList.get(i)) {
                        List<Date> dateList = getTimeOfWork(shiftCodeList.get(i), timekeeping);
                        if (j == tempList.size() - 1) {
                            tempList.get(j).setTimestart(dateList.get(0));
                            tempList.get(j).setTimeend(java.sql.Timestamp.valueOf(dateOfToday + " " + timeOfToday));
                            timeHandle(tempList.get(j).getId(), response);
                        } else {
                            tempList.get(j).setTimestart(dateList.get(0));
                            tempList.get(j).setTimeend(dateList.get(1));
                            timeHandle(tempList.get(j).getId(), response);
                            Long time = dateList.get(1).getTime() - dateList.get(0).getTime();
                        }

//                    int workingHours = (int) TimeUnit.MILLISECONDS.toMinutes(time) / 60;
//                    viewList.add(String.valueOf(tempList.get(j).getTime()));
//                    if (tempList.get(j).getTime() > workingHours) {
//                        tempList.get(j).setTime(workingHours);
//                    }else{
//                        
//                    }
                    }
                }
            }
        }

        //timekeepingServices.checkout(timekeeping);
        JsonServices.dd(JsonServices.ParseToJson(String.valueOf(handleMap.size())), response);
        //timekeeping.setTimeend(java.sql.Timestamp.valueOf(dateOfToday + " " + timeOfToday));
        timeHandle(timekeeping.getId(), response);
        return new ResponseEntity<>(timekeeping, HttpStatus.OK);
    }

    public List<Date> getTimeOfWork(int id, Timekeeping timekeeping) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<Date> dates = new ArrayList<>();
        if (id % 5 == 0) {
            String timeBeginWork = "06:00:00";
            String dateBeginWork = dateFormat.format(timekeeping.getTimestart());
            String timeEndWork = "10:00:00";
            String dateEndWork = dateFormat.format(timekeeping.getTimestart());
            dates.add(java.sql.Timestamp.valueOf(dateBeginWork + " " + timeBeginWork));
            dates.add(java.sql.Timestamp.valueOf(dateEndWork + " " + timeEndWork));
        } else if (id % 5 == 1) {
            String timeBeginWork = "10:00:00";
            String dateBeginWork = dateFormat.format(timekeeping.getTimestart());
            String timeEndWork = "14:00:00";
            String dateEndWork = dateFormat.format(timekeeping.getTimestart());
            dates.add(java.sql.Timestamp.valueOf(dateBeginWork + " " + timeBeginWork));
            dates.add(java.sql.Timestamp.valueOf(dateEndWork + " " + timeEndWork));
        } else if (id % 5 == 2) {
            String timeBeginWork = "14:00:00";
            String dateBeginWork = dateFormat.format(timekeeping.getTimestart());
            String timeEndWork = "18:00:00";
            String dateEndWork = dateFormat.format(timekeeping.getTimestart());
            dates.add(java.sql.Timestamp.valueOf(dateBeginWork + " " + timeBeginWork));
            dates.add(java.sql.Timestamp.valueOf(dateEndWork + " " + timeEndWork));
        } else if (id % 5 == 3) {
            String timeBeginWork = "18:00:00";
            String dateBeginWork = dateFormat.format(timekeeping.getTimestart());
            String timeEndWork = "22:00:00";
            String dateEndWork = dateFormat.format(timekeeping.getTimestart());
            dates.add(java.sql.Timestamp.valueOf(dateBeginWork + " " + timeBeginWork));
            dates.add(java.sql.Timestamp.valueOf(dateEndWork + " " + timeEndWork));
        } else if (id % 5 == 4) {
            String timeBeginWork = "22:00:00";
            String dateBeginWork = dateFormat.format(timekeeping.getTimestart());
            String timeEndWork = "06:00:00";
            String dateEndWork = dateFormat.format(timekeeping.getTimestart().getTime() + (1000 * 60 * 60 * 24));
            dates.add(java.sql.Timestamp.valueOf(dateBeginWork + " " + timeBeginWork));
            dates.add(java.sql.Timestamp.valueOf(dateEndWork + " " + timeEndWork));
        }
        return dates;
    }

    public void timeHandle(int id, HttpServletResponse response) {
        SimpleDateFormat hour = new SimpleDateFormat("HH");
        SimpleDateFormat minute = new SimpleDateFormat("mm");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Timekeeping timekeeping = timekeepingServices.findOne(id);

        HashMap<Date, Date> hashMap = new HashMap<Date, Date>();
        if (timekeeping.getShiftCode() % 5 == 0) {
            String timeBeginWork = "06:00:00";
            String dateBeginWork = dateFormat.format(timekeeping.getTimestart());
            String timeEndWork = "10:00:00";
            String dateEndWork = dateFormat.format(timekeeping.getTimestart());
            hashMap.put(java.sql.Timestamp.valueOf(dateBeginWork + " " + timeBeginWork), java.sql.Timestamp.valueOf(dateEndWork + " " + timeEndWork));
        } else if (timekeeping.getShiftCode() % 5 == 1) {
            String timeBeginWork = "10:00:00";
            String dateBeginWork = dateFormat.format(timekeeping.getTimestart());
            String timeEndWork = "14:00:00";
            String dateEndWork = dateFormat.format(timekeeping.getTimestart());
            hashMap.put(java.sql.Timestamp.valueOf(dateBeginWork + " " + timeBeginWork), java.sql.Timestamp.valueOf(dateEndWork + " " + timeEndWork));
        } else if (timekeeping.getShiftCode() % 5 == 2) {
            String timeBeginWork = "14:00:00";
            String dateBeginWork = dateFormat.format(timekeeping.getTimestart());
            String timeEndWork = "18:00:00";
            String dateEndWork = dateFormat.format(timekeeping.getTimestart());
            hashMap.put(java.sql.Timestamp.valueOf(dateBeginWork + " " + timeBeginWork), java.sql.Timestamp.valueOf(dateEndWork + " " + timeEndWork));
        } else if (timekeeping.getShiftCode() % 5 == 3) {
            String timeBeginWork = "18:00:00";
            String dateBeginWork = dateFormat.format(timekeeping.getTimestart());
            String timeEndWork = "22:00:00";
            String dateEndWork = dateFormat.format(timekeeping.getTimestart());
            hashMap.put(java.sql.Timestamp.valueOf(dateBeginWork + " " + timeBeginWork), java.sql.Timestamp.valueOf(dateEndWork + " " + timeEndWork));

        } else if (timekeeping.getShiftCode() % 5 == 4) {
            String timeBeginWork = "22:00:00";
            String dateBeginWork = dateFormat.format(timekeeping.getTimestart());
            String timeEndWork = "06:00:00";
            String dateEndWork = dateFormat.format(timekeeping.getTimestart().getTime() + (1000 * 60 * 60 * 24));
            hashMap.put(java.sql.Timestamp.valueOf(dateBeginWork + " " + timeBeginWork), java.sql.Timestamp.valueOf(dateEndWork + " " + timeEndWork));
        }

        int checkinHour = Integer.parseInt(hour.format(timekeeping.getTimestart()));

        int checkinMinute = Integer.parseInt(minute.format(timekeeping.getTimestart()));
        int checkoutHour = Integer.parseInt(hour.format(timekeeping.getTimeend()));

        for (Map.Entry<Date, Date> entry : hashMap.entrySet()) {
            if (dateFormat.format(entry.getKey()).compareTo(dateFormat.format(entry.getValue())) == 0) {
                if (checkinMinute > 15 && checkinHour >= Integer.parseInt(hour.format(entry.getKey()))) {
                    checkinHour += 1;
                } else if (simpleDateFormat.format(timekeeping.getTimestart()).compareTo(simpleDateFormat.format(entry.getKey())) <= 0) {
                    checkinHour = Integer.parseInt(hour.format(entry.getKey()));
                    //JsonServices.dd(JsonServices.ParseToJson(String.valueOf(checkinHour)), response);
                }

                //JsonServices.dd(JsonServices.ParseToJson(String.valueOf(Integer.parseInt(hour.format(entry.getKey()))) + " hi"), response);
                if (timekeeping.getTimeend().compareTo(entry.getValue()) >= 0) {
                    timekeeping.setTime(Integer.parseInt(hour.format(entry.getValue())) - checkinHour);
                } else {
                    timekeeping.setTime(checkoutHour - checkinHour);
                    //JsonServices.dd(JsonServices.ParseToJson(String.valueOf("hwo")), response);
                }
                //JsonServices.dd(JsonServices.ParseToJson(String.valueOf(checkinHour) + " " + String.valueOf(checkoutHour) + " " + String.valueOf(timekeeping.getTime())), response);
            } else if (dateFormat.format(entry.getKey()).compareTo(dateFormat.format(entry.getValue())) < 0) {
                Date _beginTime = timekeeping.getTimestart();
                int minuteTimeStart = _beginTime.getMinutes();
                int secondTimeStart = _beginTime.getSeconds();
                _beginTime.setMinutes(0);
                _beginTime.setSeconds(0);
                Date beginTime = _beginTime;
                Date endTime = timekeeping.getTimeend();
                Long time = null;
                if (checkinMinute > 15) {
                    time = endTime.getTime() - (beginTime.getTime() + TimeUnit.HOURS.toMillis(1));
                } else {
                    time = endTime.getTime() - beginTime.getTime();
                }
                int workingHours = (int) TimeUnit.MILLISECONDS.toMinutes(time) / 60;
                //JsonServices.dd(JsonServices.ParseToJson(workingHours), response);
                timekeeping.getTimestart().setMinutes(minuteTimeStart);
                timekeeping.getTimestart().setSeconds(secondTimeStart);
                timekeeping.setTime(workingHours);
            }
        }
        timekeepingServices.checkout(timekeeping);
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
//

    @RequestMapping(value = "/timekeeping/edit/{id}", method = RequestMethod.POST)
    public String edit(@PathVariable int id, Model model,
            HttpServletRequest request, HttpServletResponse response
    ) {
        Timekeeping timekeeping = timekeepingServices.findOne(id);
        if (request.getParameter("action").equals("Trở lại")) {
            return index(model, request);
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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

            HashMap<Date, Date> hashMap = new HashMap<Date, Date>();
            if (timekeeping.getShiftCode() % 5 == 0) {
                String timeBeginWork = "06:00:00";
                String dateBeginWork = dateFormat.format(timekeeping.getTimestart());
                String timeEndWork = "10:00:00";
                String dateEndWork = dateFormat.format(timekeeping.getTimestart());
                hashMap.put(java.sql.Timestamp.valueOf(dateBeginWork + " " + timeBeginWork), java.sql.Timestamp.valueOf(dateEndWork + " " + timeEndWork));
            } else if (timekeeping.getShiftCode() % 5 == 1) {
                String timeBeginWork = "10:00:00";
                String dateBeginWork = dateFormat.format(timekeeping.getTimestart());
                String timeEndWork = "14:00:00";
                String dateEndWork = dateFormat.format(timekeeping.getTimestart());
                hashMap.put(java.sql.Timestamp.valueOf(dateBeginWork + " " + timeBeginWork), java.sql.Timestamp.valueOf(dateEndWork + " " + timeEndWork));
            } else if (timekeeping.getShiftCode() % 5 == 2) {
                String timeBeginWork = "14:00:00";
                String dateBeginWork = dateFormat.format(timekeeping.getTimestart());
                String timeEndWork = "18:00:00";
                String dateEndWork = dateFormat.format(timekeeping.getTimestart());
                hashMap.put(java.sql.Timestamp.valueOf(dateBeginWork + " " + timeBeginWork), java.sql.Timestamp.valueOf(dateEndWork + " " + timeEndWork));
            } else if (timekeeping.getShiftCode() % 5 == 3) {
                String timeBeginWork = "18:00:00";
                String dateBeginWork = dateFormat.format(timekeeping.getTimestart());
                String timeEndWork = "22:00:00";
                String dateEndWork = dateFormat.format(timekeeping.getTimestart());
                hashMap.put(java.sql.Timestamp.valueOf(dateBeginWork + " " + timeBeginWork), java.sql.Timestamp.valueOf(dateEndWork + " " + timeEndWork));

            } else if (timekeeping.getShiftCode() % 5 == 4) {
                String timeBeginWork = "22:00:00";
                String dateBeginWork = dateFormat.format(timekeeping.getTimestart());
                String timeEndWork = "06:00:00";
                String dateEndWork = dateFormat.format(timekeeping.getTimestart().getTime() + (1000 * 60 * 60 * 24));
                hashMap.put(java.sql.Timestamp.valueOf(dateBeginWork + " " + timeBeginWork), java.sql.Timestamp.valueOf(dateEndWork + " " + timeEndWork));
            }

            timekeeping.setTimestart(java.sql.Timestamp.valueOf(checkinTime));
            timekeeping.setTimeend(java.sql.Timestamp.valueOf(checkoutTime));
            int checkinHour = Integer.parseInt(hour.format(timekeeping.getTimestart()));
            int checkinMinute = Integer.parseInt(minute.format(timekeeping.getTimestart()));
            int checkoutHour = Integer.parseInt(hour.format(timekeeping.getTimeend()));
            for (Map.Entry<Date, Date> entry : hashMap.entrySet()) {
                if (dateFormat.format(entry.getKey()).compareTo(dateFormat.format(entry.getValue())) == 0) {
                    if (checkinMinute > 15 && checkinHour >= Integer.parseInt(hour.format(entry.getKey()))) {
                        checkinHour += 1;
                    } else if (simpleDateFormat.format(timekeeping.getTimestart()).compareTo(simpleDateFormat.format(entry.getKey())) <= 0) {
                        checkinHour = Integer.parseInt(hour.format(entry.getKey()));
                        //JsonServices.dd(JsonServices.ParseToJson(String.valueOf(checkinHour)), response);
                    }

                    if (timekeeping.getTimeend().compareTo(entry.getValue()) >= 0) {
                        timekeeping.setTime(Integer.parseInt(hour.format(entry.getValue())) - checkinHour);
                    } else {
                        timekeeping.setTime(checkoutHour - checkinHour);
                    }
                    //JsonServices.dd(JsonServices.ParseToJson(String.valueOf(checkinHour) + " " + String.valueOf(checkoutHour) + " " + String.valueOf(timekeeping.getTime())), response);
                } else if (dateFormat.format(entry.getKey()).compareTo(dateFormat.format(entry.getValue())) < 0) {
                    Date _beginTime = timekeeping.getTimestart();
                    int minuteTimeStart = _beginTime.getMinutes();
                    int secondTimeStart = _beginTime.getSeconds();
                    _beginTime.setMinutes(0);
                    _beginTime.setSeconds(0);
                    Date beginTime = _beginTime;
                    Date endTime = timekeeping.getTimeend();
                    Long time = null;
                    if (checkinMinute > 15) {
                        time = endTime.getTime() - (beginTime.getTime() + TimeUnit.HOURS.toMillis(1));
                    } else {
                        time = endTime.getTime() - beginTime.getTime();
                    }
                    int workingHours = (int) TimeUnit.MILLISECONDS.toMinutes(time) / 60;
                    //JsonServices.dd(JsonServices.ParseToJson(workingHours), response);
                    timekeeping.getTimestart().setMinutes(minuteTimeStart);
                    timekeeping.getTimestart().setSeconds(secondTimeStart);
                    timekeeping.setTime(workingHours);
                }
            }
            timekeepingServices.checkout(timekeeping);
        }
        return update(timekeeping.getId(), model);
    }
}
