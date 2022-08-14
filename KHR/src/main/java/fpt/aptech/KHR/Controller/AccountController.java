/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Controller;

import fpt.aptech.KHR.Entities.*;
import fpt.aptech.KHR.FileUpload.FileUploadUtil;
import fpt.aptech.KHR.ImpServices.AccountPositionService;
import fpt.aptech.KHR.ImpServices.JsonServices;
import fpt.aptech.KHR.ImpServices.PositionServices;
import fpt.aptech.KHR.ImpServices.StoreService;
import fpt.aptech.KHR.Reponsitory.AccountPositionRepository;
import fpt.aptech.KHR.Services.IAccountRepository;
import fpt.aptech.KHR.Routes.RouteWeb;
import fpt.aptech.KHR.Services.AccountServiceImp;
import fpt.aptech.KHR.Services.IAccountPositionServices;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import fpt.aptech.KHR.Services.IPositionServices;

import java.io.IOException;

import static java.lang.System.out;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author jthie
 */
@Controller
public class AccountController {

    @Autowired
    private IAccountRepository accountRepository;
    @Autowired
    private PositionServices positionServices;
    @Autowired
    private AccountPositionRepository accountPositionRepository;
    @Autowired
    private AccountPositionService accountPositionService;

    @Autowired
    private StoreService storeService;

    @RequestMapping(value = {RouteWeb.accountManageURL}, method = RequestMethod.GET)
    public String AccountList(Model model, @Param("keyword") String keyword ,HttpServletResponse response, HttpServletRequest request) {

        HttpSession session = request.getSession();
        int IdStore = Integer.parseInt(session.getAttribute("IdStore").toString());


        List<Account> list1 = accountRepository.listAll(keyword);
        List<Account> list = new ArrayList<>();

        if (list1.size() > 0) {

            for (Account item : list1
            ) {
                if (item.getRole().equals("0") || item.getRole().equals("1") || item.getRole().equals("2")) {
                    if (item.getIdStore().getId() == IdStore) {
                        list.add(item);
                    }

                }
            }

        }

        boolean check = false;
        for (Account item : list) {
            if (item.getMail() != null) {

                check = true;
                break;
            }
        }
        
        model.addAttribute("list", list);
        model.addAttribute("check", check);
        model.addAttribute("keyword", keyword);
        return "admin/account/index";
    }

    @RequestMapping(value = {RouteWeb.AccountGetCreateURL}, method = RequestMethod.GET)
    public String GetCreate(Model model) {
        List<Position> positions = positionServices.findAll();
        model.addAttribute("positions", positions);
        return "admin/account/create";
    }

    @RequestMapping(value = {RouteWeb.AccountGetCreateURL}, method = RequestMethod.POST)
    public String PostCreate(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam("image") MultipartFile multipartFile) throws IOException {

        HttpSession session = request.getSession();
        int IdStore = Integer.parseInt(session.getAttribute("IdStore").toString());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String mail = request.getParameter("txtAccountMail");
        String name = request.getParameter("txtFullName");
        String phone = request.getParameter("txtPhone");
        boolean gender = Boolean.parseBoolean(request.getParameter("radioGender"));
        String strBday = request.getParameter("txtBirthDay");
        if(strBday.equals("")||strBday==null){
            strBday = "2000-01-01";
        }
        

        Date bday = null;
        try {
            bday = new SimpleDateFormat("yyyy-mm-dd").parse(strBday);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String role = request.getParameter("txtRole");

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        Account account = new Account(mail, encoder.encode("123"), name, phone, bday, gender, encoder.encode(mail), role, true, fileName);
        account.setIdStore(new Store(IdStore));
        if (fileName.equals("") || fileName == null) {
            account.setAvatar("defaultUserIcon.png");
        } else {
            String uploadDir = "src/main/resources/images/user-photos/";
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        }

        accountRepository.save(account);

        List<Position> positions = positionServices.findAll();
        for (int i = 0; i < positions.size(); i++) {
            if (request.getParameter("check" + i) != null) {
                AccountPosition accountPosition = new AccountPosition();
                accountPosition.setIdPosition(new Position(Integer.parseInt(request.getParameter("check" + i).toString())));
                accountPosition.setMail(new Account(mail));
                accountPosition.setSalary(Integer.parseInt(request.getParameter("checkvalue" + i).toString()));
                accountPositionRepository.save(accountPosition);
            }
        }
        String redirectUrl = "/account/index";
        return "redirect:" + redirectUrl;
    }

    @RequestMapping(value = {RouteWeb.AccountGetUpdateURL}, method = RequestMethod.GET)
    public String GetUpdate(Model model, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        Account account = accountRepository.findByMail(id);
        model.addAttribute("Account", account);

//        Get account position list
        Account _account = new Account();
        _account.setMail(id);
        List<AccountPosition> accountPositions = accountPositionService.findByEmail(_account);
        model.addAttribute("AccountPosition", accountPositions);

        List<Position> positions = positionServices.findAll();
        model.addAttribute("Positions", positions);

        String[][] positionList = new String[positions.size()][4];

        for (int i = 0; i < positions.size(); i++) {
            positionList[i][0] = positions.get(i).getId().toString();
            positionList[i][1] = positions.get(i).getPositionname().toString();
            positionList[i][2] = String.valueOf(positions.get(i).getSalarydefault());
            positionList[i][3] = "false";
        }

        for (int i = 0; i < positions.size(); i++) {
            for (int j = 0; j < accountPositions.size(); j++) {
                if (positions.get(i).getId() == accountPositions.get(j).getIdPosition().getId()) {
                    positionList[i][2] = String.valueOf(accountPositions.get(j).getSalary());
                    positionList[i][3] = "true";
                }
            }
        }

        model.addAttribute("positionList", positionList);

        return "admin/account/update";
    }

    @RequestMapping(value = {RouteWeb.AccountGetUpdateURL}, method = RequestMethod.POST)
    public String PostUpdate(Model model, HttpServletRequest request, HttpServletResponse response) {
        String mail = request.getParameter("txtAccountMail");
        String name = request.getParameter("txtFullName");
        String phone = request.getParameter("txtPhone");
        boolean gender = Boolean.parseBoolean(request.getParameter("radioGender"));
        String strBday = request.getParameter("txtBirthDay");
        Date bday = null;
        try {
            bday = new SimpleDateFormat("yyyy-mm-dd").parse(strBday);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String role = request.getParameter("txtRole");


        Account account = accountRepository.findByMail(mail);

        account.setFullname(name);
        account.setPhone(phone);
        account.setGender(gender);
        account.setBirthdate(bday);
        account.setRole(role);


        accountRepository.save(account);
//        JsonServices.dd(JsonServices.ParseToJson(account), response);

        List<Position> positions = positionServices.findAll();
        List<AccountPosition> accountPositions = accountPositionService.findAll();
        int number = positions.size();

        boolean check = true;

        for (int i = 0; i < accountPositions.size(); i++) {
            check = true;
            for (int j = 1; j <= number; j++) {

                if (request.getParameter("check" + j) != null) {

                    int id = Integer.parseInt(request.getParameter("check" + j));

                    if ((id == accountPositions.get(i).getIdPosition().getId()) && (accountPositions.get(i).getMail().getMail().equals(mail))) {

                        check = false;
                    }
                }
            }

            if (check == true) {
                if (accountPositions.get(i).getMail().getMail().equals(mail)) {

                    AccountPosition accountPosition1 = new AccountPosition(accountPositions.get(i).getId(), accountPositions.get(i).getSalary(), accountPositions.get(i).getMail(), accountPositions.get(i).getIdPosition());
                    accountPositionService.delete(accountPosition1);
                }


            }
        }

        boolean checkAccountPosition = false;

        int id = 0;
        for (int i = 1; i <= number; i++) {
            checkAccountPosition = false;
            if (request.getParameter("check" + i) != null) {
                id = Integer.parseInt(request.getParameter("check" + i));
                for (AccountPosition item : accountPositions) {
                    if (item.getIdPosition().getId() == id && item.getMail().getMail().equals(mail)) {
                        item.setSalary(Integer.parseInt(request.getParameter("checkvalue" + i)));
                        accountPositionService.save(item);
                        checkAccountPosition = true;
                    }
                }
//                JsonServices.dd(checkAccountPosition, response);
                if (checkAccountPosition == false) {
                    AccountPosition accountPosition = new AccountPosition();
                    accountPosition.setIdPosition(new Position(Integer.parseInt(request.getParameter("check" + i).toString())));
                    accountPosition.setMail(new Account(mail));
                    accountPosition.setSalary(Integer.parseInt(request.getParameter("checkvalue" + i).toString()));
                    accountPositionRepository.save(accountPosition);

                }
            }
        }

        String redirectUrl = "/account/index";
        return "redirect:" + redirectUrl;
    }

    @RequestMapping(value = {RouteWeb.AccountGetBlockURL}, method = RequestMethod.GET)
    public String GetBlockAccount(Model model, HttpServletRequest request, HttpServletResponse response) {
        String mail = request.getParameter("id");
        Account account = accountRepository.findByMail(mail);
        if (account.getStatus() == true) {
            account.setStatus(false);
            accountRepository.save(account);
        } else {
            account.setStatus(true);
            accountRepository.save(account);
        }
        String redirectUrl = "/account/index";
        return "redirect:" + redirectUrl;
    }

    @RequestMapping(value = {RouteWeb.AccountResetPassURL}, method = RequestMethod.GET)
    public String ResetPass(Model model, HttpServletRequest request, HttpServletResponse response) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String mail = request.getParameter("id");
        Account account = accountRepository.findByMail(mail);
        account.setPassword(encoder.encode("123"));
        accountRepository.save(account);
        String redirectUrl = "/account/index";
        return "redirect:" + redirectUrl;
    }


    @RequestMapping(value = {RouteWeb.BossAccountIndex}, method = RequestMethod.GET)
    public String BossAccountList(Model model, HttpServletResponse response) {
        List<Account> list1 = accountRepository.findAll();

        List<Account> list = new ArrayList<>();

        for (Account item : list1
        ) {
            if (item.getRole().equals("1") || item.getRole().equals("3")) {
                list.add(item);

            }

        }

        boolean check = false;
        for (Account item : list) {
            if (item.getMail() != null) {

                check = true;
                break;
            }
        }
        model.addAttribute("list", list);
        model.addAttribute("check", check);
        return "Boss/account/index";
    }

    @RequestMapping(value = {RouteWeb.BossAccountCreate}, method = RequestMethod.GET)
    public String GetCreateBoss(Model model) {
        List<Store> stores = storeService.FindAl();
        model.addAttribute("stores", stores);
        return "Boss/account/create";
    }


    @RequestMapping(value = {RouteWeb.BossAccountCreate}, method = RequestMethod.POST)
    public String PostCreateBoss(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam("image") MultipartFile multipartFile) throws IOException {


        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String mail = request.getParameter("txtAccountMail");
        String name = request.getParameter("txtFullName");
        String phone = request.getParameter("txtPhone");
        boolean gender = Boolean.parseBoolean(request.getParameter("radioGender"));
        String strBday = request.getParameter("txtBirthDay");

        Date bday = null;
        try {
            bday = new SimpleDateFormat("yyyy-mm-dd").parse(strBday);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String role = request.getParameter("txtRole");
        String Store = request.getParameter("txtStore");

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        if (role.equals("3")) {
            Account account = new Account(mail, encoder.encode("123"), name, phone, bday, gender, encoder.encode(mail), role, true, fileName);
            if (fileName.equals("") || fileName == null) {

            } else {
                String uploadDir = "src/main/resources/images/user-photos/";
                FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

            }
            accountRepository.save(account);
        } else {
            Account account = new Account(mail, encoder.encode("123"), name, phone, bday, gender, encoder.encode(mail), role, true, fileName);
            account.setIdStore(new Store(Integer.parseInt(Store)));
            if (fileName.equals("") || fileName == null) {

            } else {
                String uploadDir = "src/main/resources/images/user-photos/";
                FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

            }
            accountRepository.save(account);
        }


        String redirectUrl = "/boss/account/index";
        return "redirect:" + redirectUrl;
    }


    @RequestMapping(value = {RouteWeb.BossAccountEdit}, method = RequestMethod.GET)
    public String GetUpdateBoss(Model model, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");

        Account account = accountRepository.findByMail(id);
        model.addAttribute("Account", account);


        List<Store> stores = storeService.FindAl();
        model.addAttribute("stores", stores);


        return "boss/account/update";
    }

    @RequestMapping(value = {RouteWeb.BossAccountEdit}, method = RequestMethod.POST)
    public String PostUpdateBoss(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {


        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String mail = request.getParameter("txtAccountMail");
        String name = request.getParameter("txtFullName");
        String phone = request.getParameter("txtPhone");
        boolean gender = Boolean.parseBoolean(request.getParameter("radioGender"));
        String strBday = request.getParameter("txtBirthDay");

        Date bday = null;
        try {
            bday = new SimpleDateFormat("yyyy-mm-dd").parse(strBday);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String role = request.getParameter("txtRole");
        String Store = request.getParameter("txtStore");

        Account account1 = accountRepository.findByMail(mail);


        if (role.equals("3")) {
            account1.setPassword(encoder.encode("123"));
            account1.setFullname(name);
            account1.setPhone(phone);
            account1.setBirthdate(bday);
            account1.setGender(gender);
            account1.setCode(encoder.encode(mail));
            account1.setRole(role);
            account1.setIdStore(null);
            accountRepository.save(account1);
        } else {
            account1.setPassword(encoder.encode("123"));
            account1.setFullname(name);
            account1.setPhone(phone);
            account1.setBirthdate(bday);
            account1.setGender(gender);
            account1.setCode(encoder.encode(mail));
            account1.setRole(role);
            ;
            account1.setIdStore(new Store(Integer.parseInt(Store)));

            accountRepository.save(account1);
        }


        String redirectUrl = "/boss/account/index";
        return "redirect:" + redirectUrl;
    }


    @RequestMapping(value = {RouteWeb.BossAccountBlock}, method = RequestMethod.GET)
    public String GetBlockAccountBoss(Model model, HttpServletRequest request, HttpServletResponse response) {
        String mail = request.getParameter("id");
        Account account = accountRepository.findByMail(mail);
        if (account.getStatus() == true) {
            account.setStatus(false);
            accountRepository.save(account);
        } else {
            account.setStatus(true);
            accountRepository.save(account);
        }
        String redirectUrl = "/boss/account/index";
        return "redirect:" + redirectUrl;
    }

    @RequestMapping(value = {RouteWeb.BossAccountReset}, method = RequestMethod.GET)
    public String ResetPassBoss(Model model, HttpServletRequest request, HttpServletResponse response) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String mail = request.getParameter("id");
        Account account = accountRepository.findByMail(mail);
        account.setPassword(encoder.encode("123"));
        accountRepository.save(account);
        String redirectUrl = "/boss/account/index";
        return "redirect:" + redirectUrl;
    }


    @RequestMapping(value = {RouteWeb.RedirectLogout}, method = RequestMethod.GET)
    public String RedirectLogout(Model model, HttpServletResponse response, HttpServletRequest request) {

        HttpSession session = request.getSession();
        session.removeAttribute("IdStore");
        session.removeAttribute("NameStore");

        String redirectUrl = "/logout";
        return "redirect:" + redirectUrl;
    }
}
