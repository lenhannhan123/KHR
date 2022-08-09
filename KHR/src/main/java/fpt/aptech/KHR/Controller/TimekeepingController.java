/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Entities.AccountPosition;
import fpt.aptech.KHR.Entities.Position;
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
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
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
        dataList.add(hourFormat .format(shift.getTimestart()));
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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat hour = new SimpleDateFormat("HH");
        SimpleDateFormat minute = new SimpleDateFormat("mm");
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String dateOfToday = dateFormat.format(date);
        String timeOfToday = hourFormat.format(date);
        timekeeping.setTimestart(java.sql.Timestamp.valueOf(dateOfToday + " " + timeOfToday));

        List<Shift> shiftList = timekeepingServices.findShiftByDate(timekeeping.getTimestart());
        Instant now = Instant.now(); //current date
        Instant before = now.minus(Duration.ofDays(1));
        Date dateBefore = Date.from(before);
        dateOfToday = dateFormat.format(dateBefore);
        List<Shift> shiftListDateBefore = timekeepingServices.findShiftByDate(java.sql.Timestamp.valueOf(dateOfToday + " " + timeOfToday));
        shiftList.addAll(shiftListDateBefore);

        List<TimelineDetail> timelineDetailList = new ArrayList<>();
        for (int i = 0; i < shiftList.size(); i++) {
            List<TimelineDetail> _timelineDetailList = new ArrayList<>();
            String shiftCode = shiftList.get(i).getShiftcode().toString();
            if (shiftCode.length() == 4) {
//                viewList.add(shiftCode + " " + shiftList.get(i).getIdPosition().getId().toString() + " " + shiftList.get(i).getIdTimeline().getId().toString());
                _timelineDetailList = timekeepingServices.findTimelineDetailList(timekeeping.getMail(), shiftList.get(i).getShiftcode() - 1000, shiftList.get(i).getIdPosition(), shiftList.get(i).getIdTimeline());
            } else {
                _timelineDetailList = timekeepingServices.findTimelineDetailList(timekeeping.getMail(), shiftList.get(i).getShiftcode() - 100, shiftList.get(i).getIdPosition(), shiftList.get(i).getIdTimeline());
            }

            for (TimelineDetail item : _timelineDetailList) {
                if (!timelineDetailList.contains(item)) {
                    timelineDetailList.add(item);
                }
            }
        }

        List<Shift> tempList = new ArrayList<>();
        for (int i = 0; i < timelineDetailList.size(); i++) {
            String shiftCode = String.valueOf(timelineDetailList.get(i).getShiftCode());
            if (shiftCode.length() > 1) {
                Shift shift = timekeepingServices.findShiftByShiftCode(timelineDetailList.get(i).getShiftCode() + 1000, timekeeping.getTimestart(), timelineDetailList.get(i).getIdPosition());
                if (shift != null) {
                    tempList.add(shift);
                }
            } else {
                Shift shift = timekeepingServices.findShiftByShiftCode(timelineDetailList.get(i).getShiftCode() + 100, timekeeping.getTimestart(), timelineDetailList.get(i).getIdPosition());
                if (shift != null) {
                    tempList.add(shift);
                }
            }

        }

        for (int i = 0; i < timelineDetailList.size(); i++) {
            Shift shiftDateBefore = timekeepingServices.findShiftByShiftCode(timelineDetailList.get(i).getShiftCode() + 100, java.sql.Timestamp.valueOf(dateOfToday + " " + timeOfToday), timelineDetailList.get(i).getIdPosition());
            if (shiftDateBefore != null) {
                tempList.add(shiftDateBefore);
            }
        }

        List<Timekeeping> timekeepings = timekeepingServices.findAll();
        for (int i = 0; i < timekeepings.size(); i++) {
            for (int j = 0; j < tempList.size(); j++) {
                if (timekeepings.get(i).getShiftId().equals(tempList.get(j)) && timekeepings.get(i).getMail().equals(timekeeping.getMail())) {
                    tempList.remove(tempList.get(j));
                }
            }
        }

        JsonServices.dd(JsonServices.ParseToJson(tempList.toString()), response);

        int minuteTimeStart = timekeeping.getTimestart().getMinutes();
        int secondTimeStart = timekeeping.getTimestart().getSeconds();
        int checkinMinute = Integer.parseInt(minute.format(timekeeping.getTimestart()));

        for (int i = 0; i < tempList.size(); i++) {
            Date beginTime = timekeeping.getTimestart();
            dateOfToday = dateFormat.format(tempList.get(i).getTimestart());
            timeOfToday = hourFormat.format(tempList.get(i).getTimestart());
            Date endTime = java.sql.Timestamp.valueOf(dateOfToday + " " + timeOfToday);
            Long time = endTime.getTime() - beginTime.getTime();
            checkinMinute = (int) TimeUnit.MILLISECONDS.toMinutes(time);
            timekeeping.getTimestart().setMinutes(0);
            timekeeping.getTimestart().setSeconds(0);
            if (minuteTimeStart > 15) {
                time = tempList.get(i).getTimeend().getTime() - timekeeping.getTimestart().getTime() - 3600 * 1000;
            } else {
                time = tempList.get(i).getTimeend().getTime() - timekeeping.getTimestart().getTime();
            }
            int diff = (int) TimeUnit.MILLISECONDS.toMinutes(time) / 60;

            if (simpleDateFormat.format(timekeeping.getTimestart()).compareTo(simpleDateFormat.format(tempList.get(i).getTimestart())) >= 0 && simpleDateFormat.format(timekeeping.getTimestart()).compareTo(simpleDateFormat.format(tempList.get(i).getTimeend())) <= 0 && diff >= 1) {
                Shift shift = shiftServices.FindOne(tempList.get(i).getId());
                timekeeping.setShiftId(shift);
                break;
            } else if (checkinMinute <= 15 && simpleDateFormat.format(timekeeping.getTimestart()).compareTo(simpleDateFormat.format(tempList.get(i).getTimeend())) <= 0 && diff >= 1) {
                Shift shift = shiftServices.FindOne(tempList.get(i).getId());
                timekeeping.setShiftId(shift);
                break;
            } else if (dateFormat.format(tempList.get(i).getTimestart()).compareTo(dateFormat.format(tempList.get(i).getTimeend())) < 0) {
                if (dateFormat.format(timekeeping.getTimestart()).compareTo(dateFormat.format(tempList.get(i).getTimestart())) == 0) {
                    if (simpleDateFormat.format(timekeeping.getTimestart()).compareTo(simpleDateFormat.format(tempList.get(i).getTimestart())) >= 0 && simpleDateFormat.format(timekeeping.getTimestart()).compareTo(simpleDateFormat.format(tempList.get(i).getTimeend())) <= 0 && diff >= 1) {
                        Shift shift = shiftServices.FindOne(tempList.get(i).getId());
                        timekeeping.setShiftId(shift);
                        break;
                    } else if (checkinMinute <= 15 && diff >= 1) {
                        Shift shift = shiftServices.FindOne(tempList.get(i).getId());
                        timekeeping.setShiftId(shift);
                        break;
                    }
                }
            }
        }
        timekeeping.getTimestart().setMinutes(minuteTimeStart);
        timekeeping.getTimestart().setSeconds(secondTimeStart);
        Date timeEndOfShift = timekeeping.getShiftId().getTimeend();
        String timeEnd = dateFormat.format(timeEndOfShift) + " " + "00:00:00";
        timekeeping.setTimeend(java.sql.Timestamp.valueOf(timeEnd));
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

        int checkinHour = Integer.parseInt(hour.format(timekeeping.getTimestart()));
        int checkinMinute = Integer.parseInt(minute.format(timekeeping.getTimestart()));
        int checkoutHour = Integer.parseInt(hour.format(timekeeping.getTimeend()));

        if (dateFormat.format(timekeeping.getShiftId().getTimestart()).compareTo(dateFormat.format(timekeeping.getShiftId().getTimeend())) == 0) {
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
        } else if (dateFormat.format(timekeeping.getShiftId().getTimestart()).compareTo(dateFormat.format(timekeeping.getShiftId().getTimeend())) < 0) {
            Date _beginTime = timekeeping.getTimestart();
            int minuteTimeStart = _beginTime.getMinutes();
            int secondTimeStart = _beginTime.getSeconds();
            _beginTime.setMinutes(0);
            _beginTime.setSeconds(0);
            Date beginTime = _beginTime;
            Date endTime = timekeeping.getShiftId().getTimeend();
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

            timekeeping.setTimestart(java.sql.Timestamp.valueOf(checkinTime));
            timekeeping.setTimeend(java.sql.Timestamp.valueOf(checkoutTime));
            int checkinHour = Integer.parseInt(hour.format(timekeeping.getTimestart()));
            int checkinMinute = Integer.parseInt(minute.format(timekeeping.getTimestart()));
            int checkoutHour = Integer.parseInt(hour.format(timekeeping.getTimeend()));
            if (dateFormat.format(timekeeping.getShiftId().getTimestart()).compareTo(dateFormat.format(timekeeping.getShiftId().getTimeend())) == 0) {
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
            } else if (dateFormat.format(timekeeping.getShiftId().getTimestart()).compareTo(dateFormat.format(timekeeping.getShiftId().getTimeend())) < 0) {
//                JsonServices.dd(JsonServices.ParseToJson(simpleDateFormat.format(timekeeping.getTimestart()) + " " + simpleDateFormat.format(timekeeping.getTimeend())), response);
                Date _beginTime = timekeeping.getTimestart();
                int minuteTimeStart = _beginTime.getMinutes();
                int secondTimeStart = _beginTime.getSeconds();
                _beginTime.setMinutes(0);
                _beginTime.setSeconds(0);
                Date beginTime = _beginTime;
                Date endTime = timekeeping.getShiftId().getTimeend();
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
            //JsonServices.dd(JsonServices.ParseToJson(checkoutHour + " " + checkinHour), response);
            timekeepingServices.checkout(timekeeping);

        }
        return update(timekeeping.getId(), model);
    }

}
