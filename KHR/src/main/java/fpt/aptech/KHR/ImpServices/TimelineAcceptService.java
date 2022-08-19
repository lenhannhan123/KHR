/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.ImpServices;

import fpt.aptech.KHR.Entities.TimeAccept;
import fpt.aptech.KHR.Reponsitory.TimelineAcceptResponsitory;
import fpt.aptech.KHR.Services.ITimelineAccept;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class TimelineAcceptService implements ITimelineAccept{
    
      @Autowired
    private TimelineAcceptResponsitory timelineAcceptResponsitory;

    @Override
    public List<TimeAccept> FindAl() {
        return  timelineAcceptResponsitory.findAll();
    }

    @Override
    public boolean Create(TimeAccept timeAccept) {
        
         timelineAcceptResponsitory.save(timeAccept);
        return true;
    }

    @Override
    public boolean Edit(TimeAccept timeAccept) {
          timelineAcceptResponsitory.save(timeAccept);
          return true;
    }

    @Override
    public boolean Delete(TimeAccept id) {
         timelineAcceptResponsitory.delete(id);
          return true;
    }

    @Override
    public List<Integer> GetIdTimeline(String mail) {
        return timelineAcceptResponsitory.GetIdtimeline(mail);
    }

}
