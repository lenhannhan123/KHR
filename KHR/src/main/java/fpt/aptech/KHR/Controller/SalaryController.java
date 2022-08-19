///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package fpt.aptech.KHR.Controller;
//
//import fpt.aptech.KHR.Entities.Account;
//import fpt.aptech.KHR.Entities.ModelString;
//import fpt.aptech.KHR.Entities.Salary;
//import fpt.aptech.KHR.Entities.Shift;
//import fpt.aptech.KHR.Entities.Timekeeping;
//import fpt.aptech.KHR.ImpServices.JsonServices;
//import fpt.aptech.KHR.Services.IAccountRepository;
//import fpt.aptech.KHR.Services.ISalaryServices;
//import fpt.aptech.KHR.Services.ITimekeepingServices;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//
///**
// *
// * @author backs
// */
//@Controller
//public class SalaryController {
//
//    @Autowired
//    ISalaryServices salaryServices;
//
//    @Autowired
//    ITimekeepingServices timekeepingServices;
//
//    @Autowired
//    IAccountRepository accountRepository;
//
//    @RequestMapping(value = "api/salary/index", method = RequestMethod.GET)
//    public String index(Model model, HttpServletResponse response) {
//        model.addAttribute("accountList", accountRepository.findAll());
//        model.addAttribute("list", salaryServices.findAll());
////        Double s = Double.parseDouble(String.valueOf(salaryServices.findAll().get(0).getSalary()));     
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
//
//        //JsonServices.dd(JsonServices.ParseToJson(simpleDateFormat.format(date)), response);
//        Date currentDate = new Date();
//        Date systemDate = new Date();
//        Date _systemDate = new Date();
//        String currentDay = dayFormat.format(currentDate);
//
//        if (Integer.parseInt(currentDay) < 28) {
//            _systemDate.setDate(28);
//            _systemDate.setMonth(currentDate.getMonth() - 1);
//
//            systemDate.setDate(27);
//            systemDate.setMonth(systemDate.getMonth());
//
//            if (simpleDateFormat.format(currentDate).compareTo(simpleDateFormat.format(_systemDate)) >= 0 && simpleDateFormat.format(currentDate).compareTo(simpleDateFormat.format(systemDate)) <= 0) {
//                boolean check = false;
//                //JsonServices.dd(JsonServices.ParseToJson(simpleDateFormat.format(_systemDate) + " " + simpleDateFormat.format(systemDate) + "hi"), response);
//                List<Salary> salaryList = salaryServices.findAll();
//                for (Salary item : salaryList) {
////                    JsonServices.dd(JsonServices.ParseToJson(simpleDateFormat.format(item.getDate()) + " " + simpleDateFormat.format(_systemDate)), response);
//                    if (simpleDateFormat.format(item.getDate()).compareTo(simpleDateFormat.format(_systemDate)) == 0) {
//                        //JsonServices.ParseToJson(monthFormat.format(item.getDate()));
//                        check = true;
//                    }
//                }
//
//                if (check == false) {
//                    Date before = new Date();
//                    before.setDate(28);
//                    before.setMonth(_systemDate.getMonth() - 1);
//
//                    Date after = new Date();
//                    after.setDate(28);
//                    after.setMonth(_systemDate.getMonth());
//                    //JsonServices.dd(JsonServices.ParseToJson(simpleDateFormat.format(before) + " " + simpleDateFormat.format(after)), response);
//
//                    //JsonServices.dd(simpleDateFormat.format(before) + " " + simpleDateFormat.format(after), response);
//                    List<Shift> shiftList = salaryServices.findShiftByDate(before, after);
//                    List<Account> users = salaryServices.findAccountByTimekeeping();
//                    for (int i = 0; i < users.size(); i++) {
//                        List<Timekeeping> timekeepings = new ArrayList<>();
//                        for (int j = 0; j < shiftList.size(); j++) {
//                            Timekeeping item = salaryServices.findTimekeepingByMailAndShiftId(users.get(i), shiftList.get(j));
//                            if (item != null) {
//                                timekeepings.add(item);
//                            }
//                        }
//
//                        int coin = 0;
//                        int time = 0;
//                        for (int j = 0; j < timekeepings.size(); j++) {
//                            time += timekeepings.get(j).getTime();
//                            coin += timekeepings.get(j).getShiftId().getIdPosition().getSalarydefault() * timekeepings.get(j).getTime();
//                        }
//
//                        if (coin != 0 && time != 0) {
//                            Salary salary = new Salary();
//                            salary.setMail(users.get(i));
//                            salary.setDate(_systemDate);
//                            salary.setSalary(coin);
//                            salary.setTotalTime(time);
//                            salaryServices.save(salary);
//                        }
//
//                    }
//                }
//
//            }
//        } else {
//            _systemDate.setDate(28);
//            _systemDate.setMonth(currentDate.getMonth());
//
//            systemDate.setDate(27);
//            systemDate.setMonth(systemDate.getMonth() + 1);
//
//            //JsonServices.dd(JsonServices.ParseToJson(simpleDateFormat.format(_systemDate) + " " + simpleDateFormat.format(systemDate)), response);
//            if (simpleDateFormat.format(currentDate).compareTo(simpleDateFormat.format(_systemDate)) >= 0 && simpleDateFormat.format(currentDate).compareTo(simpleDateFormat.format(systemDate)) <= 0) {
//                boolean check = false;
//                //JsonServices.dd(JsonServices.ParseToJson(simpleDateFormat.format(_systemDate) + " " + simpleDateFormat.format(systemDate)), response);
//                List<Salary> salaryList = salaryServices.findAll();
//                for (Salary item : salaryList) {
////                    JsonServices.dd(JsonServices.ParseToJson(simpleDateFormat.format(item.getDate()) + " " + simpleDateFormat.format(_systemDate)), response);
//                    if (simpleDateFormat.format(item.getDate()).compareTo(simpleDateFormat.format(_systemDate)) == 0) {
//                        //JsonServices.ParseToJson(monthFormat.format(item.getDate()));
//                        check = true;
//                    }
//                }
//
//                if (check == false) {
//                    Date before = new Date();
//                    before.setDate(28);
//                    before.setMonth(_systemDate.getMonth() - 1);
//
//                    Date after = new Date();
//                    after.setDate(28);
//                    after.setMonth(_systemDate.getMonth());
//                    //JsonServices.dd(JsonServices.ParseToJson(simpleDateFormat.format(before) + " " + simpleDateFormat.format(after) + " hi"), response);
//
//                    //JsonServices.dd(simpleDateFormat.format(before) + " " + simpleDateFormat.format(after), response);
//                    List<Shift> shiftList = salaryServices.findShiftByDate(before, after);
//                    List<Account> users = salaryServices.findAccountByTimekeeping();
//                    for (int i = 0; i < users.size(); i++) {
//                        List<Timekeeping> timekeepings = new ArrayList<>();
//                        for (int j = 0; j < shiftList.size(); j++) {
//                            Timekeeping item = salaryServices.findTimekeepingByMailAndShiftId(users.get(i), shiftList.get(j));
//                            if (item != null) {
//                                timekeepings.add(item);
//                            }
//                        }
//
//                        int coin = 0;
//                        int time = 0;
//                        for (int j = 0; j < timekeepings.size(); j++) {
//                            time += timekeepings.get(j).getTime();
//                            coin += timekeepings.get(j).getShiftId().getIdPosition().getSalarydefault() * timekeepings.get(j).getTime();
//                        }
//
//                        if (coin != 0 && time != 0) {
//                            Salary salary = new Salary();
//                            salary.setMail(users.get(i));
//                            salary.setDate(_systemDate);
//                            salary.setSalary(coin);
//                            salary.setTotalTime(time);
//                            salaryServices.save(salary);
//                        }
//
//                    }
//                }
//
//            }
//        }
//
//        //JsonServices.dd(JsonServices.ParseToJson(simpleDateFormat.format(_systemDate) + " " + simpleDateFormat.format(systemDate)), response);
//        //JsonServices.dd(JsonServices.ParseToJson(monthFormat.format(currentDate)), response);
//        return "admin/salary/index";
//    }
//
//    @RequestMapping(value = "/salary/autocomplete", method = RequestMethod.GET)
//    public ResponseEntity<List<String>> autocomplete(@RequestParam("value") String input, HttpServletRequest request) {
//        return new ResponseEntity<List<String>>(timekeepingServices.autocomplete(input), HttpStatus.OK);
//    }
//
//    @RequestMapping(value = "/salary/search", method = RequestMethod.GET)
//    public String search(HttpServletRequest request, Model model, HttpServletResponse response) {
//        model.addAttribute("accountList", accountRepository.findAll());
//        if (request.getParameter("mail").equals("")) {
//            return index(model, response);
//        } else {
//            model.addAttribute("list", salaryServices.findSalaryListByMail(request.getParameter("mail")));
//        }
//        return "admin/salary/index";
//    }
//
//    @RequestMapping(value = "/api/salary/year/{mail}", method = RequestMethod.GET)
//    public ResponseEntity<List<String>> getYear(@PathVariable("mail") String mail, HttpServletResponse response) {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
//        Account account = accountRepository.findByMail(mail);
//        List<Salary> salarys = salaryServices.findSalaryListByMail(mail);
//        List<String> years = new ArrayList<>();
//
//        if (salarys.isEmpty()) {
//            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
//        } else {
//            for (int i = 0; i < salarys.size(); i++) {
//                if (!years.contains(simpleDateFormat.format(salarys.get(i).getDate()))) {
//                    years.add(simpleDateFormat.format(salarys.get(i).getDate()));
//                }
//            }
//            //JsonServices.dd(JsonServices.ParseToJson(years.toString()), response);    
//            return new ResponseEntity<List<String>>(years, HttpStatus.OK);
//        }
//    }
//
//    @RequestMapping(value = "/api/salary/findOneByDate", method = RequestMethod.GET)
//    public void findOneByDate(@RequestParam("mail") String mail, @RequestParam("month") int month, @RequestParam("year") int year, HttpServletResponse response) {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
//        Salary salary = salaryServices.findOneByDate(mail, month, year);
//        //JsonServices.dd(JsonServices.ParseToJson(salary), response);
//        List<ModelString> stringList = new ArrayList<>();
//        //data.add(salary.toString());
//        Date date = new Date();
//        date = salary.getDate();
//        Date _date = new Date();
//        _date.setDate(28);
//        _date.setMonth(date.getMonth() - 1);
//        List<Shift> shiftList = salaryServices.findShiftByDate(_date, date);
//        List<Timekeeping> timekeepings = new ArrayList<>();
//        for (int i = 0; i < shiftList.size(); i++) {
//            Timekeeping timekeeping = salaryServices.findTimekeepingByMailAndShiftId(salary.getMail(), shiftList.get(i));
//            if (timekeeping != null) {
//                timekeepings.add(timekeeping);
//            }
//        }
//
//        List<String> positionList = new ArrayList<>();
//        Map<String, String> hashMap = new HashMap<String, String>();
//        for (int i = 0; i < timekeepings.size(); i++) {
//            if (!positionList.contains(timekeepings.get(i).getShiftId().getIdPosition().getPositionname())) {
//                positionList.add(timekeepings.get(i).getShiftId().getIdPosition().getPositionname());
//            }
//            hashMap.put(timekeepings.get(i).getShiftId().getIdPosition().getPositionname() + String.valueOf(i), String.valueOf(timekeepings.get(i).getTime()));
//        }
//
//        Map<String, String> _hashMap = new HashMap<String, String>();
//        _hashMap.put("numberOfTimekeeping", String.valueOf(timekeepings.size()));
//        for (int i = 0; i < positionList.size(); i++) {
//            int _time = 0;
//            for (Map.Entry<String, String> item : hashMap.entrySet()) {
//                String key = item.getKey();
//                String value = item.getValue();
//
//                if (key.contains(positionList.get(i))) {
//                    _time += Integer.parseInt(value);
//                }
//
//            }
//            _hashMap.put(positionList.get(i), String.valueOf(_time));
//        }
//        
//        int count = 0;
//        for (Map.Entry<String, String> item : _hashMap.entrySet()) {
//                String key = item.getKey();
//                String value = item.getValue();
//
//                ModelString modelString = new ModelString();
//                modelString.setData1(key);
//                modelString.setData2(value);
//                stringList.add(modelString);
//            }
//        JsonServices.dd(JsonServices.ParseToJson(stringList), response);
//        //return new ResponseEntity<List<ModelString>>(stringList, HttpStatus.OK);
//
//    }
//
//}
