/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Services;

import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Entities.Salary;
import fpt.aptech.KHR.Entities.Shift;
import fpt.aptech.KHR.Entities.Timekeeping;
import java.util.Date;
import java.util.List;

/**
 *
 * @author backs
 */
public interface ISalaryServices {
    public List<Salary> findAll();
    
    public List<Salary> findByMail(Account mail);
    
    public void save(Salary newSalary);
    
    public List<Account> findAccountByTimekeeping();
    
    public List<Shift> findShiftByDate(Date timestart, Date timeend);
    
    public Timekeeping findTimekeepingByMailAndShiftId(Account mail, Shift shiftId);
    
    public List<Salary> findSalaryListByMail(String mail);

    public Salary findOneByDate(String mail, int month, int year);
}
