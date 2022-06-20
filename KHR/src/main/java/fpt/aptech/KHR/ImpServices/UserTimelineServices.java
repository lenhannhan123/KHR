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
        return null;
    }

    @Override
    public boolean Create(UserTimeline userTimeline) {
        return false;
    }

    @Override
    public boolean Edit(UserTimeline userTimeline) {
        return false;
    }

    @Override
    public String Delete(int id) {
        return null;
    }

    @Override
    public UserTimeline FindOne(int id) {
        return null;
    }
}
