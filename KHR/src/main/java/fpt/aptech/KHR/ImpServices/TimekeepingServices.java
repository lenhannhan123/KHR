/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.ImpServices;

import fpt.aptech.KHR.Services.*;
import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Entities.Shift;
import fpt.aptech.KHR.Entities.Timekeeping;
import fpt.aptech.KHR.Reponsitory.TimekeepingRepository;
import java.util.Date;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author backs
 */
@Service
public class TimekeepingServices implements ITimekeepingServices {

    @Autowired
    TimekeepingRepository timekeepingRepository;

    @Override
    public List<Timekeeping> findAll() {
        return timekeepingRepository.findAll();
    }

    @Override
    public Timekeeping findOne(int id) {
        return timekeepingRepository.findOne(id);
    }

    @Override
    public Timekeeping findByMail(Account mail) {
        return timekeepingRepository.findByMail(mail);
    }


    @Override
    public void checkin(Timekeeping timekeeping) {
        timekeepingRepository.save(timekeeping);
    }

    @Override
    public void checkout(Timekeeping timekeeping) {
        timekeepingRepository.save(timekeeping);
    }

    @Override
    public List<String> autocomplete(String keyword) {
        return timekeepingRepository.autocomplete(keyword);
    }

    @Override
    public List<Shift> findShiftByTimeStart(Date timeStart) {
        return timekeepingRepository.findShiftByTimeStart(timeStart);
    }

    @Override
    public List<Timekeeping> search(String mail) {
        return timekeepingRepository.search(mail);
    }
    
}
