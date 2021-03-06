/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Reponsitory;

import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Entities.Shift;

import fpt.aptech.KHR.Entities.Timekeeping;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author backs
 */
public interface TimekeepingRepository extends JpaRepository<Timekeeping, Integer> {

    @Query("select t from Timekeeping t where t.id = :id")
    Timekeeping findOne(@PathVariable("id") int id);

    @Query(value = "select * from Timekeeping where mail = :mail order by id DESC limit 1", nativeQuery = true)
    Timekeeping findByMail(@PathVariable("mail") Account mail);

    @Query("select distinct mail.mail from Timekeeping t inner join t.mail mail where t.mail.mail like %:keyword%")
    public List<String> autocomplete(@RequestParam("keyword") String keyword);
    
    @Query("select t from Timekeeping t where t.mail.mail = :mail")
    public List<Timekeeping> search(@PathVariable("value") String mail);

    @Query("select s from Shift s where s.timestart = :timestart")
    public List<Shift> findShiftByTimeStart(@RequestParam("value") Date timestart);
}
