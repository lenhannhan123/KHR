/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Services;

import fpt.aptech.KHR.Entities.Position;
import fpt.aptech.KHR.Entities.Timeline;

import java.util.List;

/**
 * @author Admin
 */
public interface IPositionServices {


    public List<Position> findAll();

    public boolean Create(Position position);

    public boolean Edit(Position position);


    public String Delete(int id);

    public Position FindOne(int id);


    public int CountPosition();

}
