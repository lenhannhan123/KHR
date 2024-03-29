/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.ImpServices;

import java.util.Date;
import fpt.aptech.KHR.Entities.*;
import fpt.aptech.KHR.Reponsitory.*;
import fpt.aptech.KHR.Services.IDayOffServices;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author LÊ HỮU TÂM
 */
@Service
public class DayOffService implements IDayOffServices{
    @Autowired
    DayOffRepository dor;
    @Autowired
    AccountNotificationRepository acnores;
    @Autowired
    NotificationRepository notires;
    @Autowired
    AccountRepository accountRepository;
    @Override
    public List<DayOff> findAll() {
        return dor.findAll();
    }

    @Override
    public boolean approve(int id) {
        
        
        DayOff dayOff = dor.findID(id);
        Notification n = new fpt.aptech.KHR.Entities.Notification();
        dayOff.setStatus(Short.valueOf("1"));
        dor.save(dayOff);
        return true;
    }

    @Override
    public boolean denying(int id) {
        DayOff dayOff = dor.findID(id);
        dayOff.setStatus(Short.valueOf("2"));
        dor.save(dayOff);
        return true;
    }

    @Override
    public List<DayOff> findApproveList() {
        return dor.findStatus(Short.valueOf("1"));
    }

    @Override
    public List<DayOff> findDenyingList() {
        return dor.findStatus(Short.valueOf("2"));
    }

    @Override
    public List<DayOff> findNotCheck() {
        return dor.findStatus(Short.valueOf("0"));
    }

    @Override
    public DayOff AddDayOff( DayOff newDayOff) {
        return dor.save(newDayOff);
    }

    @Override
    public DayOff findById(int id) {
        return dor.findID(id);
    }

    @Override
    public List<DayOff> findByMail(String mail) {
        Account acc = accountRepository.findByEmail(mail);
        return dor.findByMail(acc);
    }

    @Override
    public List<DayOff> findByDate(String start, String end) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
            Date s1 = dateFormat.parse(start);
            Date s2 = dateFormat.parse(end);
            List<DayOff> dayOffs = dor.findByDate(s1, s2);
            return dayOffs;
        } catch (ParseException ex) {
            Logger.getLogger(DayOffService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
