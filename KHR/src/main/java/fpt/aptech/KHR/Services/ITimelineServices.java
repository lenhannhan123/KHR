/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Services;

import fpt.aptech.KHR.Entities.Timeline;

import java.util.List;

/**
 * @author Admin
 */
public interface ITimelineServices {

    public List<Timeline> findAll();

    public Timeline Create(Timeline timeline);

    public boolean Edit(Timeline timeline);


    public String Delete(int id);

    public Timeline FindOne(int id);
}
