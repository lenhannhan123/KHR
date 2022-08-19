/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Reponsitory;

import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Entities.Salary;
import fpt.aptech.KHR.Entities.Shift;
import fpt.aptech.KHR.Entities.Timekeeping;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author backs
 */
public interface SalaryRepository extends JpaRepository<Salary, Integer> {

    @Query("select s from Salary s WHERE s.mail = :mail")
    List<Salary> findByEmail(@PathVariable("mail") Account mail);

    @Query("select t from Timekeeping t where DATE(t.timestart) >= :timestart and DATE(t.timestart) < :timeend and t.mail = :mail")
    public List<Timekeeping> findTimekeepingByDate(@RequestParam("value") Date timestart, @RequestParam("value") Date timeend, @RequestParam("mail") Account mail);

    @Query("select distinct mail from Timekeeping")
    public List<Account> findAccountByTimekeeping();

//    @Query("select t from Timekeeping t where t.mail = :mail and t.shiftId = :shiftId")
//    public Timekeeping findTimekeepingByMailAndShiftId(@RequestParam("value") Account mail, @RequestParam("value") Shift shiftId);

    @Query("select s from Salary s where s.mail.mail = :mail")
    public List<Salary> findSalaryListByMail(@RequestParam("value") String mail);

    @Query("select s from Salary s where s.mail.mail = :mail and MONTH(s.date) = :month and YEAR(s.date) = :year")
    public Salary findOneByDate(@RequestParam("value") String mail, @RequestParam("value") int month, @RequestParam("value") int year);
}
