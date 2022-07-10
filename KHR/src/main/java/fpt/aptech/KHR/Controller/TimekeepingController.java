/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Controller;

import fpt.aptech.KHR.Entities.Shift;
import fpt.aptech.KHR.Entities.Timekeeping;
import fpt.aptech.KHR.Routes.RouteAPI;
import fpt.aptech.KHR.Routes.RouteWeb;
import fpt.aptech.KHR.Services.IShiftServices;
import fpt.aptech.KHR.Services.ITimekeepingServices;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author backs
 */
@RestController
public class TimekeepingController {

    @Autowired
    ITimekeepingServices timekeepingServices;

    @Autowired
    IShiftServices shiftServices;

    @RequestMapping(value = {RouteWeb.TimekeepingIndexURL}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("list", timekeepingServices.findAll());
        return "timekeeping/index";
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

//    @PostMapping("shift")
//    List<Shift> findShiftByTime() throws ParseException {
//        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
//        Date date = new Date();
//        String time = formatter.format(date);
//        //String date = formatter.format(java.sql.Time.valueOf(new Date().toString()));
//        List<Shift> shift = new ArrayList<>();
//        shift.addAll(shiftServices.findByTime(java.sql.Time.valueOf("07:30:00"), java.sql.Time.valueOf("11:30:00")));
//        return shift;
//    }
    
//    @PostMapping(value = "timekeeping/save")
//    public ResponseEntity<Shift> insert(@RequestBody Timekeeping timekeeping) {
//        try {
//            _service.saveEmployees(employees);
//            return new ResponseEntity<>(employees, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

}
