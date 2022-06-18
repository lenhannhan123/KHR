/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.ImpServices;

import fpt.aptech.KHR.Entities.Shift;
import fpt.aptech.KHR.Services.IShiftServices;

import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author Admin
 */
@Service
public class ShiftServices implements IShiftServices {

    @Override
    public List<Shift> findAll() {
        return null;
    }

    @Override
    public boolean Create(Shift shift) {
        return false;
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
