/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Services;

import fpt.aptech.KHR.Entities.Token;
import fpt.aptech.KHR.Reponsitory.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jthie
 */
@Service
public class TokenServiceImp implements ITokenServices {

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public Token createToken(Token token) {
        return tokenRepository.saveAndFlush(token);
    }

    @Override
    public boolean saveToke(Token newtoken) {
        try {
            tokenRepository.save(newtoken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updatedevice(Token device) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
