/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.ImpServices;

import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Entities.Salary;
import fpt.aptech.KHR.Entities.Shift;
import fpt.aptech.KHR.Entities.Timekeeping;
import fpt.aptech.KHR.Reponsitory.SalaryRepository;
import fpt.aptech.KHR.Services.ISalaryServices;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author backs
 */
@Service
public class SalaryServices implements ISalaryServices {

    @Autowired
    SalaryRepository salaryRepository;

    @Override
    public List<Salary> findAll() {
        return salaryRepository.findAll();
    }

    @Override
    public List<Salary> findByMail(Account mail) {
        return salaryRepository.findByEmail(mail);
    }

    @Override
    public void save(Salary newSalary) {
        salaryRepository.save(newSalary);
    }

    @Override
    public List<Timekeeping> findTimekeepingByDate(Date timestart, Date timeend, Account mail) {
        return salaryRepository.findTimekeepingByDate(timestart, timeend, mail);
    }

    @Override
    public List<Account> findAccountByTimekeeping() {
        return salaryRepository.findAccountByTimekeeping();
    }

    @Override
    public Timekeeping findTimekeepingByMailAndShiftId(Account mail, Shift shiftId) {
//        return salaryRepository.findTimekeepingByMailAndShiftId(mail, shiftId);
        return null;
    }

    @Override
    public List<Salary> findSalaryListByMail(String mail) {
        return salaryRepository.findSalaryListByMail(mail);
    }

    @Override
    public Salary findOneByDate(String mail, int month, int year) {
        return salaryRepository.findOneByDate(mail, month, year);
    }

}
