/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.ImpServices;

import fpt.aptech.KHR.Entities.TimelineDetail;
import fpt.aptech.KHR.Services.ITimelineDetailServices;

import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author Admin
 */
@Service
public class TimelineDetailServices implements ITimelineDetailServices {

    @Override
    public List<TimelineDetail> findAll() {
        return null;
    }

    @Override
    public boolean Create(TimelineDetail timelineDetail) {
        return false;
    }

    @Override
    public boolean Edit(TimelineDetail timelineDetail) {
        return false;
    }

    @Override
    public String Delete(int id) {
        return null;
    }

    @Override
    public TimelineDetail FindOne(int id) {
        return null;
    }
}
