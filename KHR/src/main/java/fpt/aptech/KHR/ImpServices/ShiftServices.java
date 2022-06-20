/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.ImpServices;

import fpt.aptech.KHR.Entities.Shift;
import fpt.aptech.KHR.Responsitory.ShiftRepository;
import fpt.aptech.KHR.Services.IShiftServices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Admin
 */
@Service
public class ShiftServices implements IShiftServices {

    @Autowired
    private ShiftRepository shiftRepository;

    @Override
    public List<Shift> findAll() {
        return null;
    }

    @Override
    public boolean Create(Shift shift) {

//        shiftRepository.CreateNewShift(shift.getIdTimeline(), shift.getIdPosition(), shift.getNumber(), shift.getTimestart(), shift.getIsOT(), shift.getTimeend(), shift.getShiftcode());

        shiftRepository.save(shift);
        return true;
    }

    @Override
    public boolean Edit(Shift shift) {
        return false;
    }

    @Override
    public String Delete(int id) {
        return null;
    }

    @Override
    public Shift FindOne(int id) {
        return null;
    }

    @Override
    public Shift FindByIDTimeLine(int id) {
        return null;
    }
}
