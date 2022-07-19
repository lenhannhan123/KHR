/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.ImpServices;

import fpt.aptech.KHR.Entities.Timeline;
import fpt.aptech.KHR.Reponsitory.TimelineRepository;
import fpt.aptech.KHR.Services.ITimelineServices;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * @author Admin
 */
@Service
public class TimelineServices implements ITimelineServices {

    @Autowired
    private TimelineRepository timelineRepository;

    @Override
    public List<Timeline> findAll() {
        return timelineRepository.findAllByDelete((short) 0);
    }

    @Override
    public Timeline Create(Timeline timeline) {

        return timelineRepository.save(timeline);
    }

    @Override
    public boolean Edit(Timeline timeline) {
        timelineRepository.save(timeline);


        return true;
    }

    @Override
    public Boolean Delete(int id) {

        Timeline timeline = timelineRepository.findID(id, (short) 0);


        timeline.setIsDelete((short) 1);

        timelineRepository.save(timeline);
        return true;

    }

    @Override
    public Timeline FindOne(int id) {
        return timelineRepository.findID(id, (short) 0);
    }

    @Override
    public int countStartDay(Date StartDay) {
        return timelineRepository.findStartDay(StartDay, (short) 0);
    }

    @Override
    public int countEndDay(Date EndDay) {
        return timelineRepository.findEndDay(EndDay, (short) 0);
    }
}
