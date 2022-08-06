/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.ImpServices;

import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Entities.AccountToken;
import fpt.aptech.KHR.Reponsitory.AccountRepository;
import fpt.aptech.KHR.Reponsitory.AccountTokenRepository;
import fpt.aptech.KHR.Services.IAccountToken;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author LÊ HỮU TÂM
 */
@Service
public class AccountTokenSevices implements IAccountToken{
    @Autowired 
    AccountTokenRepository tokenRepository;
    @Autowired 
    AccountRepository accountRepository;
    @Override
    public AccountToken NewToken(AccountToken accountToken) {
       return tokenRepository.save(accountToken);
    }

    @Override
    public List<AccountToken> GetTokenByMail(String mail) {
        Account acc = accountRepository.findByEmail(mail);
        List<AccountToken> accountTokens = tokenRepository.findByMail(acc);
//        List<String> listtoken = new List<>();
//        for (AccountToken acKen : accountTokens) {
//            listtoken.add(acKen.getToken());
//        }
        return accountTokens;
    }

    @Override
    public AccountToken GetToken(String token) {
        return tokenRepository.findByToken(token);
    }
    
}
