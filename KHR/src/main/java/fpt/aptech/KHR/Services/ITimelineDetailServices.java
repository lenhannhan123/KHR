/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Services;

import fpt.aptech.KHR.Entities.Shift;
import fpt.aptech.KHR.Entities.Timeline;
import fpt.aptech.KHR.Entities.TimelineDetail;

import java.util.List;

/**
 * @author Admin
 */
public interface ITimelineDetailServices {


    public List<TimelineDetail> findAll();

    public boolean Create(TimelineDetail timelineDetail);

    public boolean Edit(TimelineDetail timelineDetail);


    public String Delete(int id);

    public TimelineDetail FindOne(int id);

}
