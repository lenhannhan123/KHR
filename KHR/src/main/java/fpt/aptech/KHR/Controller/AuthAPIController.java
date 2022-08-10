/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Controller;

import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Entities.AccountPosition;
import fpt.aptech.KHR.Entities.Mail;
import fpt.aptech.KHR.Entities.SmsPojo;
import fpt.aptech.KHR.ImpServices.AccountService;
import fpt.aptech.KHR.ImpServices.SmsService;
import fpt.aptech.KHR.Reponsitory.AccountPositionRepository;
import fpt.aptech.KHR.Services.AccountServiceImp;
import fpt.aptech.KHR.Services.SendMailService;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Admin
 */
@Controller
public class AuthAPIController {

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private SimpMessagingTemplate webSocket;

    private final String TOPIC_DESTINATION = "/api/sms";

    @Autowired
    SmsService sendSmsservice;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountServiceImp accountServiceImp;

    @Autowired
    private SendMailService sendMailService;

    @Autowired
    private AccountPositionRepository accountPositionRepository;

    @PostMapping("api/auth")
    public ResponseEntity<Account> auth(@RequestBody Account account, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                account.getMail(), account.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Account account1 = accountService.findByMail(account.getMail());
        if (account1 == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        account1.setPassword(account.getPassword());
        return new ResponseEntity<>(account1, HttpStatus.OK);
    }

    @RequestMapping(value = "api/view-profile-image", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getProfileImage(String filename) throws IOException {
        ClassPathResource imgFile = new ClassPathResource("images/user-photos/" + filename);
        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(bytes);
    }

    @PostMapping(path = "api/change-pass")
    public ResponseEntity<?> changePass(@RequestBody Account account, @RequestParam("pass_new") String pass_new) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                account.getMail(), account.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        if (authentication.isAuthenticated()) {
            accountServiceImp.updatePassword(passwordEncoder.encode(pass_new), account.getMail());
            return new ResponseEntity<>(account, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect password");
    }

    @PostMapping(path = "api/test-mail")
    public ResponseEntity<Mail> testMail(@RequestBody Mail mail) {
        mail.setSubject(mail.getSubject());
        mail.setRecipient(mail.getRecipient());
        mail.setContent(mail.getContent());
        sendMailService.sendMail(mail);
        return new ResponseEntity<>(mail, HttpStatus.OK);
    }

//    Code sample return string for testing purpose
//    @PostMapping(path = "api/test-recover-code-mail")
//    public ResponseEntity<String> sendRecoverCodeMail(@RequestBody String mail) {
//        sendMailService.sendRecoveryCode(mail);
//        return new ResponseEntity<String>("Code sent", HttpStatus.OK);
//    }
    @PostMapping(path = "api/recover-code-mail")
    public ResponseEntity<Account> sendRecoverCodeMail(@RequestBody Account account) {
        sendMailService.sendRecoveryCode(account.getMail());
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping(path = "api/recover-code-sms")
    public ResponseEntity<Account> sendRecoverCodeSms(@RequestBody Account account) {
        sendSmsservice.sendRecoveryCode(account.getMail());
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

//    Code sample return string for testing purpose
//    @PostMapping(path = "api/recovery-change-pass")
//    public ResponseEntity<?> recoveryChangePass(@RequestParam String mail, @RequestParam String recoverycode, @RequestParam("pass_new") String pass_new) {
//        if (accountServiceImp.checkRecoveryCode(mail, recoverycode) == true) {
//            accountServiceImp.updatePassword(passwordEncoder.encode(pass_new), mail);
//            return new ResponseEntity<>("New password saved!", HttpStatus.OK);
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect recovery code");
//    }
    @PostMapping(path = "api/recovery-change-pass")
    public ResponseEntity<Account> recoveryChangePass(@RequestBody Account account) {
        if (accountServiceImp.checkRecoveryCode(account.getMail(), account.getRecoverycode()) == true) {
            accountServiceImp.updatePassword(passwordEncoder.encode(account.getPassword()), account.getMail());
            return new ResponseEntity<>(account, HttpStatus.OK);
        }
        return new ResponseEntity<>(account, HttpStatus.BAD_REQUEST);
    }

//    WIP
//    @PostMapping(path = "api/get-positions")
//    public ResponseEntity<Account> getJobPositions(@RequestBody Account account, @RequestParam("email") String email) {
//        List<AccountPosition> listAccountPositions = accountPositionRepository.findByEmail(account.getMail());
//    }
    @PostMapping(path = "api/change-profile-info")
    public ResponseEntity<Account> changeBasicInfo(@RequestBody Account account) {
        accountServiceImp.updateBasicInfoMobile(account.getFullname(), account.getPhone(), account.getBirthdate(), account.getGender(), account.getMail());
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping(path = "api/checkGoogleId")
    public ResponseEntity<Account> checkGoogleId(@RequestBody Account account) {
        accountServiceImp.checkGoogleId(account.getMail(), account.getGoogleid());
        Account account1 = accountService.findByMail(account.getMail());
        return new ResponseEntity<>(account1, HttpStatus.OK);

    }

}
