/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.ImpServices;

import fpt.aptech.KHR.Entities.UserTimeline;
import fpt.aptech.KHR.Responsitory.TimelineRepository;
import fpt.aptech.KHR.Responsitory.UserTimeLineRepository;
import fpt.aptech.KHR.Services.IUserTimeServices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Admin
 */
@Service
public class UserTimelineServices implements IUserTimeServices {

    @Autowired
    private UserTimeLineRepository userTimeLineRepository;

    @Override
    public List<UserTimeline> findAll() {
        return userTimeLineRepository.findAll();
    }

    @Override
    public boolean Create(UserTimeline userTimeline) {

        userTimeLineRepository.save(userTimeline);
        return true;
    }

    @Override
    public boolean Edit(UserTimeline userTimeline) {

        userTimeLineRepository.save(userTimeline);

        return true;
    }

    @Override
    public String Delete(int id) {

        UserTimeline userTimeline = FindOne(id);

        if (userTimeline != null) {
            userTimeLineRepository.delete(userTimeline);
            return "true";
        }

        return "false";
    }

    @Override
    public UserTimeline FindOne(int id) {
        return userTimeLineRepository.findID(id);
    }

    @Override
    public List<UserTimeline> FindIDTimeLine(int idTimeLine) {

        try {
            return userTimeLineRepository.findIDTimeLine(idTimeLine);
        } catch (Exception e) {
            return null;

        }


    }

    @Override
    public boolean CheckUser(int idTimeline, String mail) {
        UserTimeline userTimeline;
        try {
            userTimeline = userTimeLineRepository.checkUserTimeline(idTimeline, mail);
        } catch (Exception e) {
            userTimeline = null;
        }


        if (userTimeline == null) {

            return false;

        } else {
            return true;
        }

    }
}
