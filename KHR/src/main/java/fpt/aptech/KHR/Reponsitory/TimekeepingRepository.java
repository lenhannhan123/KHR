/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Reponsitory;

import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Entities.AccountPosition;
import fpt.aptech.KHR.Entities.Position;
import fpt.aptech.KHR.Entities.Shift;

import fpt.aptech.KHR.Entities.Timekeeping;
import fpt.aptech.KHR.Entities.Timeline;
import fpt.aptech.KHR.Entities.TimelineDetail;
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

    @Query(value = "select t from Timekeeping t where t.mail = :mail")
    public List<Timekeeping> findByAccount(@RequestParam("mail") Account mail);

    @Query("select s from Shift s where DATE(s.timestart) = :timestart")
    public List<Shift> findShiftByDate(@RequestParam("value") Date timestart);

    @Query("select t from Timekeeping t where t.mail = :mail and MONTH(t.timestart) = :month and YEAR(t.timestart) = :year")
    public List<Timekeeping> findAllByDate(@RequestParam("value") Account mail, @RequestParam("value") int month, @RequestParam("value") int year);

//    @Query("select t from TimelineDetail t where t.mail = :mail and t.shiftCode = :id and t.idPosition = :Id_Position and t.idTimeline = :idTimeline")
//    public List<TimelineDetail> findTimelineDetailList(@RequestParam("value") Account mail, @RequestParam("value") int id, @RequestParam("value") Position Id_Position, @RequestParam("value") Timeline idTimeline);
    @Query("SELECT s FROM Shift s WHERE s.shiftcode = :shiftcode and DATE(s.timestart) = :date and s.idPosition = :idPosition")
    Shift findShiftByShiftCode(@PathVariable("shiftcode") int shiftcode, Date date, Position idPosition);

//    @Query("SELECT t.shiftId.id FROM Timekeeping t WHERE t.id = :id")
//    Integer detailId(@PathVariable("value") int id);
    @Query("SELECT p FROM Position p WHERE p.id = :id")
    Position findPositionAccountById(@PathVariable("value") int id);

    @Query("select s from Shift s where s.id = :id")
    public Shift findShiftByTimekeeping(@RequestParam("value") int id);

    @Query("select t from Timeline t")
    public List<Timeline> findTimelineList();

    @Query("select t from Timeline t where DATE(t.startdate) <= :date and DATE(t.enddate) >= :date")
    public Timeline findTimelineByDate(@RequestParam("value") Date date);

    @Query("select t from TimelineDetail t where t.mail = :mail and t.shiftCode = :id and t.idTimeline = :idTimeline")
    public TimelineDetail findTimelineDetail(@RequestParam("value") Account mail, @RequestParam("value") int id, @RequestParam("value") Timeline idTimeline);

    @Query("select MAX(t.shiftCode) from TimelineDetail t")
    public int findMaxShiftCodeInTimelineDetail();
}
