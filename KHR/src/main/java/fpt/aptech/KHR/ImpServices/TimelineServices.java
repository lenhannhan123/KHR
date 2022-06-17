/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.ImpServices;

import fpt.aptech.KHR.Entities.Shift;
import fpt.aptech.KHR.Entities.Timeline;
import fpt.aptech.KHR.Responsitory.TimelineRepository;
import fpt.aptech.KHR.Services.ITimelineServices;
import org.springframework.beans.factory.annotation.Autowired;

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
        return timelineRepository.findAll();
    }

    @Override
    public boolean Create(Timeline timeline) {
        timelineRepository.save(timeline);
        return true;
    }

    @Override
    public boolean Edit(Timeline timeline) {
        timelineRepository.save(timeline);


        return true;
    }

    @Override
    public String Delete(int id) {

        Timeline timeline = timelineRepository.findID(id);

        if (timeline.getId() == null) {
            return "Không tìm thấy lịch làm việc";

        } else {

            ShiftServices shiftServices = new ShiftServices();
            Shift shift = shiftServices.FindByIDTimeLine(timeline.getId());

            if (shift == null) {
                timelineRepository.delete(timeline);
                return "True";
            } else {

                return "False";
            }

        }


    }

    @Override
    public Timeline FindOne(int id) {
        return timelineRepository.findID(id);
    }
}
