/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Services;

import fpt.aptech.KHR.Entities.UserTimeline;

import java.util.List;

/**
 * @author Admin
 */
public interface IUserTimeServices {


    public List<UserTimeline> findAll();

    public boolean Create(UserTimeline userTimeline);

    public boolean Edit(UserTimeline userTimeline);


    public String Delete(int id);

    public UserTimeline FindOne(int id);

    public List<UserTimeline> FindIDTimeLine(int idTimeLine);

    public boolean CheckUser(int idTimeline, String mail);

    public List<UserTimeline> UserTimeline(int idTimeline, String mail);


    public List<Integer> GetIdTimeline(String mail);

    public List<UserTimeline> GetUserTimeline(String mail);
}
