/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.ImpServices;

import fpt.aptech.KHR.Entities.Position;
import fpt.aptech.KHR.Reponsitory.PositionRepository;
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
    public Position Create(Position position) {
        return positionRepository.save(position);
    }

    @Override
    public boolean Edit(Position position) {
        positionRepository.save(position);
        return true;
    }

    @Override
    public String Delete(int id) {

        Position position = FindOne(id);

        if (position != null) {

            positionRepository.delete(position);
            return "true";
        }

        return "Không tìm thấy";
    }

    @Override
    public Position FindOne(int id) {
        return positionRepository.findID(id);
    }

    @Override
    public int CountPosition() {
        return positionRepository.CouPosition();
    }

    @Override
    public void save(Position position) {
        positionRepository.save(position);
    }
}
