/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Services;

import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Entities.Timekeeping;
import fpt.aptech.KHR.Reponsitory.TimekeepingRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author backs
 */
@Service
public class TimekeepingServiceImp implements ITimekeepingServices {

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

}
