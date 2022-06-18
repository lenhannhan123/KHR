/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.ImpServices;

import fpt.aptech.KHR.Entities.Position;
import fpt.aptech.KHR.Responsitory.PositionRepository;
import fpt.aptech.KHR.Responsitory.TimelineRepository;
import fpt.aptech.KHR.Services.IPositionServices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Admin
 */
@Service
public class PositionServices implements IPositionServices {


    @Autowired
    private PositionRepository positionRepository;

    @Override
    public List<Position> findAll() {
        return positionRepository.findAll();
    }

    @Override
    public boolean Create(Position position) {
        return false;
    }

    @Override
    public boolean Edit(Position position) {
        return false;
    }

    @Override
    public String Delete(int id) {
        return null;
    }

    @Override
    public Position FindOne(int id) {
        return null;
    }

    @Override
    public int CountPosition() {
        return positionRepository.CouPosition();
    }
}
