/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Services;

import fpt.aptech.KHR.Entities.Shift;

import java.util.List;

/**
 * @author Admin
 */
public interface IShiftServices {


    public List<Shift> findAll();

    public boolean Create(Shift shift);

    public boolean Edit(Shift shift);


    public String Delete(int id);

    public Shift FindOne(int id);


    public Shift FindByIDTimeLine(int id);

}
