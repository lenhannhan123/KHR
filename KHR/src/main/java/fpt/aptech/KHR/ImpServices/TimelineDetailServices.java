/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.ImpServices;

import fpt.aptech.KHR.Entities.Timeline;
import fpt.aptech.KHR.Entities.TimelineDetail;
import fpt.aptech.KHR.Reponsitory.ShiftRepository;
import fpt.aptech.KHR.Reponsitory.TimelineDetailRepository;
import fpt.aptech.KHR.Reponsitory.TimelineRepository;
import fpt.aptech.KHR.Services.ITimelineDetailServices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Admin
 */
@Service
public class TimelineDetailServices implements ITimelineDetailServices {


    @Autowired
    private TimelineDetailRepository timelineDetailRepository;

    @Override
    public List<TimelineDetail> findAll() {
        return timelineDetailRepository.findAll();
    }

    @Override
    public boolean Create(TimelineDetail timelineDetail) {
        timelineDetailRepository.save(timelineDetail);
        return true;
    }

    @Override
    public boolean Edit(TimelineDetail timelineDetail) {
        timelineDetailRepository.save(timelineDetail);
        return true;
    }

    @Override
    public String Delete(int id) {

        timelineDetailRepository.delete(new TimelineDetail(id));
        return "true";
    }

    @Override
    public TimelineDetail FindOne(int id) {
        return timelineDetailRepository.findID(id);
    }

    @Override
    public List<TimelineDetail> FindbyIdTimeline(int id) {
        return timelineDetailRepository.findbyIdTimeline(new Timeline(id));
    }
}
