/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.ImpServices;

import fpt.aptech.KHR.Services.*;
import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Entities.AccountPosition;
import fpt.aptech.KHR.Entities.Position;
import fpt.aptech.KHR.Entities.Shift;
import fpt.aptech.KHR.Entities.Timekeeping;
import fpt.aptech.KHR.Entities.TimelineDetail;
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
    public List<Shift> findShiftByDate(Date timeStart) {
        return timekeepingRepository.findShiftByDate(timeStart);
    }

    @Override
    public List<Timekeeping> search(String mail) {
        return timekeepingRepository.search(mail);
    }

    @Override
    public TimelineDetail findTimelineDetailByMailAndShift(Account mail, int id) {
        return timekeepingRepository.findTimelineDetailByMailAndShift(mail, id);
    }

    @Override
    public List<Timekeeping> findByAccount(Account mail) {
        return timekeepingRepository.findByAccount(mail);
    }

    @Override
    public List<Timekeeping> findAllByDate(int month, int year) {
        return timekeepingRepository.findAllByDate(month, year);
    }

    @Override
    public Shift findShiftByShiftCode(int id, Date date, Position idPosition) {
        return timekeepingRepository.findShiftByShiftCode(id, date, idPosition);
    }

    @Override
    public List<AccountPosition> findIdPositionByAccount(Account mail) {
        return timekeepingRepository.findIdPositionByAccount(mail);
    }
    
}
