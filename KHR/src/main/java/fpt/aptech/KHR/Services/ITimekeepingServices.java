/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Services;

import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Entities.AccountPosition;
import fpt.aptech.KHR.Entities.Position;
import fpt.aptech.KHR.Entities.Shift;
import fpt.aptech.KHR.Entities.Timekeeping;
import fpt.aptech.KHR.Entities.Timeline;
import fpt.aptech.KHR.Entities.TimelineDetail;
import java.util.Date;

import java.util.List;

/**
 * @author backs
 */
public interface ITimekeepingServices {

    List<Timekeeping> findAll();

    Timekeeping findOne(int id);

    Timekeeping findByMail(Account mail);

    List<Timekeeping> findByAccount(Account mail);

    void checkin(Timekeeping timekeeping);

    void checkout(Timekeeping timekeeping);

    public List<String> autocomplete(String keyword);

    public List<Timekeeping> search(String mail);

    public List<Shift> findShiftByDate(Date timeStart);

    public List<Timekeeping> findAllByDate(Account mail, int month, int year);

    public List<TimelineDetail> findTimelineDetailList(Account mail, int id, Position Id_Position, Timeline idTimeline);
    
    public Shift findShiftByShiftCode(int id, Date date, Position idPosition);
    
    public Position findPositionAccountById(int id);
    
    public Shift findShiftByTimekeeping(int id);
    
    public Integer detailId(int id);
}
