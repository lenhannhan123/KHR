/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Controller;

import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Entities.ModelString;
import fpt.aptech.KHR.Entities.Salary;
import fpt.aptech.KHR.Entities.Shift;
import fpt.aptech.KHR.Entities.Timekeeping;
import fpt.aptech.KHR.Entities.Timeline;
import fpt.aptech.KHR.Entities.TimelineDetail;
import fpt.aptech.KHR.ImpServices.JsonServices;
import fpt.aptech.KHR.Services.IAccountRepository;
import fpt.aptech.KHR.Services.ISalaryServices;
import fpt.aptech.KHR.Services.ITimekeepingServices;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author backs
 */
@Controller
public class SalaryController {

    @Autowired
    ISalaryServices salaryServices;

    @Autowired
    ITimekeepingServices timekeepingServices;

    @Autowired
    IAccountRepository accountRepository;

    @RequestMapping(value = "api/salary/index", method = RequestMethod.GET)
    public String index(Model model, HttpServletResponse response) {
        model.addAttribute("accountList", accountRepository.findAll());
        model.addAttribute("list", salaryServices.findAll());
//        Double s = Double.parseDouble(String.valueOf(salaryServices.findAll().get(0).getSalary()));     
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");

        Date currentDate = new Date();
        Date systemDate = new Date();
        Date _systemDate = new Date();
        String currentDay = dayFormat.format(currentDate);

        if (Integer.parseInt(currentDay) < 28) {
            _systemDate.setDate(28);
            _systemDate.setMonth(currentDate.getMonth() - 1);

            systemDate.setDate(27);
            systemDate.setMonth(systemDate.getMonth());

//            JsonServices.dd(JsonServices.ParseToJson(simpleDateFormat.format(_systemDate) + " hz " + simpleDateFormat.format(systemDate)), response);
            if (simpleDateFormat.format(currentDate).compareTo(simpleDateFormat.format(_systemDate)) >= 0 && simpleDateFormat.format(currentDate).compareTo(simpleDateFormat.format(systemDate)) <= 0) {
                boolean check = false;
                //JsonServices.dd(JsonServices.ParseToJson(simpleDateFormat.format(_systemDate) + " " + simpleDateFormat.format(systemDate) + "hi"), response);
                List<Salary> salaryList = salaryServices.findAll();
                for (Salary item : salaryList) {
//                    JsonServices.dd(JsonServices.ParseToJson(simpleDateFormat.format(item.getDate()) + " " + simpleDateFormat.format(_systemDate)), response);
                    if (simpleDateFormat.format(item.getDate()).compareTo(simpleDateFormat.format(_systemDate)) == 0) {
                        //JsonServices.ParseToJson(monthFormat.format(item.getDate()));
                        check = true;
                    }
                }

                if (check == false) {
                    Date before = new Date();
                    before.setDate(28);
                    before.setMonth(_systemDate.getMonth() - 1);

                    Date after = new Date();
                    after.setDate(28);
                    after.setMonth(_systemDate.getMonth());
                    //JsonServices.dd(JsonServices.ParseToJson(simpleDateFormat.format(before) + " hiz " + simpleDateFormat.format(after)), response);

                    //JsonServices.dd(simpleDateFormat.format(before) + " why " + simpleDateFormat.format(after), response);
//                    List<Shift> shiftList = salaryServices.findShiftByDate(before, after);
                    List<Account> users = salaryServices.findAccountByTimekeeping();
                    for (int i = 0; i < users.size(); i++) {
                        List<Timekeeping> timekeepings = new ArrayList<>();
                        //JsonServices.dd(JsonServices.ParseToJson(simpleDateFormat.format(before) + " " + simpleDateFormat.format(after) + " " + users.get(i)), response);
                        timekeepings = salaryServices.findTimekeepingByDate(before, after, users.get(i));
                        //JsonServices.dd(JsonServices.ParseToJson(timekeepings.toString()), response);

                        int coin = 0;
                        int time = 0;

                        for (int j = 0; j < timekeepings.size(); j++) {
                            time += timekeepings.get(j).getTime();
                            TimelineDetail timelineDetail = timekeepingServices.findTimelineDetail(timekeepings.get(j).getMail(), timekeepings.get(j).getShiftCode(), timekeepings.get(j).getIdTimeline());
                            coin += timelineDetail.getIdPosition().getSalarydefault() * timekeepings.get(j).getTime();
                        }

                        if (time != 0 && coin != 0) {
                            Salary salary = new Salary();
                            salary.setMail(users.get(i));
                            salary.setDate(_systemDate);
                            salary.setSalary(coin);
                            salary.setTotalTime(time);
                            salaryServices.save(salary);
                        }

                    }
                }

            }
        } else {
            _systemDate.setDate(28);
            _systemDate.setMonth(currentDate.getMonth());

            systemDate.setDate(27);
            systemDate.setMonth(systemDate.getMonth() + 1);

            //JsonServices.dd(JsonServices.ParseToJson(simpleDateFormat.format(_systemDate) + " hu " + simpleDateFormat.format(systemDate)), response);
            if (simpleDateFormat.format(currentDate).compareTo(simpleDateFormat.format(_systemDate)) >= 0 && simpleDateFormat.format(currentDate).compareTo(simpleDateFormat.format(systemDate)) <= 0) {
                boolean check = false;
                //JsonServices.dd(JsonServices.ParseToJson(simpleDateFormat.format(_systemDate) + " " + simpleDateFormat.format(systemDate)), response);
                List<Salary> salaryList = salaryServices.findAll();
                for (Salary item : salaryList) {
                    //JsonServices.dd(JsonServices.ParseToJson(simpleDateFormat.format(item.getDate()) + " " + simpleDateFormat.format(_systemDate)), response);
                    if (simpleDateFormat.format(item.getDate()).compareTo(simpleDateFormat.format(_systemDate)) == 0) {
                        //JsonServices.ParseToJson(monthFormat.format(item.getDate()));
                        check = true;
                    }
                }

                if (check == false) {
                    Date before = new Date();
                    before.setDate(28);
                    before.setMonth(_systemDate.getMonth() - 1);

                    Date after = new Date();
                    after.setDate(28);
                    after.setMonth(_systemDate.getMonth());
                    //JsonServices.dd(JsonServices.ParseToJson(simpleDateFormat.format(before) + " " + simpleDateFormat.format(after) + " what"), response);

                    //JsonServices.dd(simpleDateFormat.format(before) + " " + simpleDateFormat.format(after), response);
                    List<Account> users = salaryServices.findAccountByTimekeeping();

                    for (int i = 0; i < users.size(); i++) {
                        List<Timekeeping> timekeepings = new ArrayList<>();
                        //JsonServices.dd(JsonServices.ParseToJson(simpleDateFormat.format(before) + " " + simpleDateFormat.format(after) + " " + users.get(i)), response);
                        timekeepings = salaryServices.findTimekeepingByDate(before, after, users.get(i));
                        //JsonServices.dd(JsonServices.ParseToJson(timekeepings.toString()), response);

                        int coin = 0;
                        int time = 0;

                        for (int j = 0; j < timekeepings.size(); j++) {
                            time += timekeepings.get(j).getTime();
                            TimelineDetail timelineDetail = timekeepingServices.findTimelineDetail(timekeepings.get(j).getMail(), timekeepings.get(j).getShiftCode(), timekeepings.get(j).getIdTimeline());
                            coin += timelineDetail.getIdPosition().getSalarydefault() * timekeepings.get(j).getTime();
                        }

                        if (time != 0 && coin != 0) {
                            Salary salary = new Salary();
                            salary.setMail(users.get(i));
                            salary.setDate(_systemDate);
                            salary.setSalary(coin);
                            salary.setTotalTime(time);
                            salaryServices.save(salary);
                        }

                    }
                }

            }
        }

        //JsonServices.dd(JsonServices.ParseToJson(simpleDateFormat.format(_systemDate) + " " + simpleDateFormat.format(systemDate)), response);
        //JsonServices.dd(JsonServices.ParseToJson(monthFormat.format(currentDate)), response);
        return "admin/salary/index";
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

    @RequestMapping(value = "/salary/autocomplete", method = RequestMethod.GET)
    public ResponseEntity<List<String>> autocomplete(@RequestParam("value") String input, HttpServletRequest request) {
        return new ResponseEntity<List<String>>(timekeepingServices.autocomplete(input), HttpStatus.OK);
    }

    @RequestMapping(value = "/salary/search", method = RequestMethod.GET)
    public String search(HttpServletRequest request, Model model, HttpServletResponse response) {
        model.addAttribute("accountList", accountRepository.findAll());
        if (request.getParameter("mail").equals("")) {
            return index(model, response);
        } else {
            model.addAttribute("list", salaryServices.findSalaryListByMail(request.getParameter("mail")));
        }
        return "admin/salary/index";
    }

    @RequestMapping(value = "/api/salary/year/{mail}", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getYear(@PathVariable("mail") String mail, HttpServletResponse response) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        Account account = accountRepository.findByMail(mail);
        List<Salary> salarys = salaryServices.findSalaryListByMail(mail);
        List<String> years = new ArrayList<>();

        if (salarys.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else {
            for (int i = 0; i < salarys.size(); i++) {
                if (!years.contains(simpleDateFormat.format(salarys.get(i).getDate()))) {
                    years.add(simpleDateFormat.format(salarys.get(i).getDate()));
                }
            }
            //JsonServices.dd(JsonServices.ParseToJson(years.toString()), response);    
            return new ResponseEntity<List<String>>(years, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/api/salary/findOneByDate", method = RequestMethod.GET)
    public ResponseEntity<List<ModelString>> findOneByDate(@RequestParam("mail") String mail, @RequestParam("month") int month, @RequestParam("year") int year, HttpServletResponse response) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Salary salary = salaryServices.findOneByDate(mail, month, year);
        //JsonServices.dd(JsonServices.ParseToJson(salary), response);
        List<ModelString> stringList = new ArrayList<>();
        //data.add(salary.toString());
        Date date = new Date();
        date.setDate(salary.getDate().getDate());
        date.setMonth(month);
        date.setYear(year);
        date = salary.getDate();
        Date _date = new Date();
        _date.setDate(28);
        _date.setMonth(date.getMonth() - 1);
        //JsonServices.dd(JsonServices.ParseToJson(sdf.format(date) + " " + sdf.format(_date)), response);
        List<Timekeeping> timekeepings = salaryServices.findTimekeepingByDate(_date, date, salary.getMail());

        List<String> positionList = new ArrayList<>();
        Map<String, String> hashMap = new HashMap<String, String>();
        for (int i = 0; i < timekeepings.size(); i++) {
            TimelineDetail timelineDetail = timekeepingServices.findTimelineDetail(timekeepings.get(i).getMail(), timekeepings.get(i).getShiftCode(), timekeepings.get(i).getIdTimeline());
            if (!positionList.contains(timelineDetail.getIdPosition().getPositionname())) {
                positionList.add(timelineDetail.getIdPosition().getPositionname());
            }
            hashMap.put(timelineDetail.getIdPosition().getPositionname() + String.valueOf(i), String.valueOf(timekeepings.get(i).getTime()));
        }

        Map<String, String> _hashMap = new HashMap<String, String>();
        for (int i = 0; i < positionList.size(); i++) {
            int _time = 0;
            for (Map.Entry<String, String> item : hashMap.entrySet()) {
                String key = item.getKey();
                String value = item.getValue();

                if (key.contains(positionList.get(i))) {
                    _time += Integer.parseInt(value);
                }

            }
            _hashMap.put(positionList.get(i), String.valueOf(_time));
        }

        //JsonServices.dd(JsonServices.ParseToJson(_hashMap.toString()), response);
        List<ModelString> modelStrings = new ArrayList<>();
        ModelString modelString = new ModelString();
        modelString.setData1("numberOfShift");
        modelString.setData2(String.valueOf(timekeepings.size()));
        modelString.setData3("totalMoney");
        modelString.setData4(String.valueOf(salary.getSalary()));
        modelStrings.add(modelString);
        for (Map.Entry<String, String> entry : _hashMap.entrySet()) {
            String key = entry.getKey();
            String val = entry.getValue();
            ModelString _modelString = new ModelString();
            _modelString.setData1(key);
            _modelString.setData2(val);
            modelStrings.add(_modelString);
        }
        
         //JsonServices.dd(JsonServices.ParseToJson(modelStrings), response);
//
//        int count = 0;
//        for (Map.Entry<String, String> item : _hashMap.entrySet()) {
//            String key = item.getKey();
//            String value = item.getValue();
//
//            ModelString modelString = new ModelString();
//            modelString.setData1(key);
//            modelString.setData2(value);
//            stringList.add(modelString);
//        }

        return new ResponseEntity<List<ModelString>>(modelStrings, HttpStatus.OK);
    }
}
