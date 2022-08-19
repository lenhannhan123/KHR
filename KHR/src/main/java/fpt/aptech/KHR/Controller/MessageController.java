/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Entities.AccountNotification;
import fpt.aptech.KHR.Entities.AccountToken;
import fpt.aptech.KHR.Entities.MessageAccount;
import fpt.aptech.KHR.Entities.ModelString;
import fpt.aptech.KHR.Entities.Notification;
import fpt.aptech.KHR.Entities.Store;
import fpt.aptech.KHR.ImpServices.FirebaseMessagingService;
import fpt.aptech.KHR.ImpServices.JsonServices;
import fpt.aptech.KHR.Services.IAccountRepository;
import fpt.aptech.KHR.Services.IAccountToken;
import fpt.aptech.KHR.Services.IMessageservices;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author LÊ HỮU TÂM
 */
@Controller
public class MessageController {

    @Autowired
    IAccountToken accToken;
    @Autowired
    private IAccountRepository accountRepository;
    @Autowired
    private IMessageservices iMessageservices;
    @Autowired
    FirebaseMessagingService firebaseMessagingService;

    @RequestMapping(value = {"/api/message/contact"}, method = RequestMethod.GET)
    public void Getcontact(Model model, HttpServletRequest request, HttpServletResponse response) {
        ModelString modelString = new ModelString();
        List<ModelString> modelStringout = new ArrayList<>();
        modelString.setData1(request.getParameter("mail"));
        Account acc = accountRepository.findByMail(modelString.getData1());
        List<Account> list = accountRepository.findByStore(acc.getIdStore());

        for (int i = 0; i < list.size(); i++) {
            ModelString out = new ModelString();
            Account account = list.get(i);
            if (account.getMail().equals(modelString.getData1())) {
                out.setData1("Logoicon.png");
                out.setData2(list.get(i).getIdStore().getNameStore());
                out.setData3(list.get(i).getIdStore().getId().toString());
                //out.setData4(list.get(i).getRole());
                modelStringout.add(out);

            } else {
                if (account.getRole().equals("2")) {
                } else {
                    out.setData1(list.get(i).getAvatar());
                    out.setData2(list.get(i).getFullname());
                    out.setData3(list.get(i).getMail());
                    out.setData4(list.get(i).getRole());
                    modelStringout.add(out);
                }

            }

        }
        //JsonServices.dd(JsonServices.ParseToJson(modelStringout),response);
        if (modelStringout != null) {
            JsonServices.dd(JsonServices.ParseToJson(modelStringout), response);

        } else {
            modelString.setData5("Unsusssess");
            JsonServices.dd(JsonServices.ParseToJson(modelString), response);
        }
    }

    private int Numday(Date st, Date end) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(st);
        c2.setTime(end);
        int noDay = (int) ((c2.getTime().getTime() - c1.getTime().getTime()) / (24 * 3600 * 1000));
        return noDay;
    }

    @RequestMapping(value = {"/api/message/account"}, method = RequestMethod.GET)
    public void GetMessage(Model model, HttpServletRequest request, HttpServletResponse response) throws ParseException {
        ModelString modelString = new ModelString();
        List<ModelString> modelStringout = new ArrayList<>();
        modelString.setData1(request.getParameter("send"));
        modelString.setData2(request.getParameter("to"));
        //Account acc = accountRepository.findByMail(modelString.getData1());
        Account acc2 = accountRepository.findByMail(modelString.getData2());

        if (acc2 != null) {
            List<MessageAccount> list = iMessageservices.PrivaiteSendTo(modelString.getData2(), modelString.getData1());
            //JsonServices.dd(JsonServices.ParseToJson(list), response);
            for (int i = 0; i < list.size(); i++) {
                ModelString out = new ModelString();
                MessageAccount account = list.get(i);
                Account accc = accountRepository.findByMail(account.getMailSend());
                Account accc1 = accountRepository.findByMail(account.getIdReceive());
                if (account.getMailSend().equals(accc.getMail())) {
                   out.setData1(accc.getAvatar());
                out.setData2(accc.getFullname());
                out.setData3(list.get(i).getMesssageContent());
                Date date = new Date();
                Date dateStart = new SimpleDateFormat("yyyy-MM-dd").parse(list.get(i).getCreateDate().toString());
                int num = Numday(dateStart, date);
                if (num <= 0) {
                    out.setData4("Hôm nay");
                } else {
                    out.setData4(String.valueOf(num) + "Ngày trước");
                }
                //out.setData4(list.get(i).getRole());
                modelStringout.add(out); 
                }else{
                
                out.setData1(accc1.getAvatar());
                out.setData2(accc1.getFullname());
                out.setData3(list.get(i).getMesssageContent());
                Date date = new Date();
                Date dateStart = new SimpleDateFormat("yyyy-MM-dd").parse(list.get(i).getCreateDate().toString());
                int num = Numday(dateStart, date);
                if (num <= 0) {
                    out.setData4("Hôm nay");
                } else {
                    out.setData4(String.valueOf(num) + "Ngày trước");
                }
                //out.setData4(list.get(i).getRole());
                modelStringout.add(out);
                }
                

            }
        } else {
            List<MessageAccount> list = iMessageservices.findSendToStore(modelString.getData2());
            for (int i = 0; i < list.size(); i++) {
                ModelString out = new ModelString();
                MessageAccount account = list.get(i);
                Account accc = accountRepository.findByMail(account.getMailSend());
                out.setData1(accc.getAvatar());
                out.setData2(accc.getFullname());
                out.setData3(list.get(i).getMesssageContent());
                Date date = new Date();
                Date dateStart = new SimpleDateFormat("yyyy-MM-dd").parse(list.get(i).getCreateDate().toString());
                int num = Numday(dateStart, date);
                if (num <= 0) {
                    out.setData4("Hôm nay");
                } else {
                    out.setData4(String.valueOf(num) + "Ngày trước");
                }
                //out.setData4(list.get(i).getRole());
                modelStringout.add(out);

            }

        }

        //JsonServices.dd(JsonServices.ParseToJson(modelStringout),response);
        if (modelStringout != null) {
            JsonServices.dd(JsonServices.ParseToJson(modelStringout), response);

        } else {
            modelString.setData5("Unsusssess");
            JsonServices.dd(JsonServices.ParseToJson(modelString), response);
        }
    }

    @RequestMapping(value = {"/api/message/list"}, method = RequestMethod.GET)
    public void GetMessageList(Model model, HttpServletRequest request, HttpServletResponse response) throws ParseException {
        ModelString modelString = new ModelString();
        List<ModelString> modelStringout = new ArrayList<>();
        modelString.setData1(request.getParameter("send"));
        Account acc = accountRepository.findByMail(modelString.getData1());
//        Account acc2 = accountRepository.findByMail(modelString.getData2());
        List<MessageAccount> ma = iMessageservices.findSendToStore(acc.getIdStore().getId().toString());
        ModelString out1 = new ModelString();
        int is = (ma.size() - 1);
        out1.setData5(acc.getIdStore().getId().toString());
        out1.setData1("Logoicon.png");
        out1.setData2(acc.getIdStore().getNameStore());
        out1.setData3(ma.get(is).getMesssageContent());
        Date date = new Date();
        Date dateStart = new SimpleDateFormat("yyyy-MM-dd").parse(ma.get(is).getCreateDate().toString());
        int num = Numday(dateStart, date);
        if (num <= 0) {
            out1.setData4("Hôm nay");
        } else {
            out1.setData4(String.valueOf(num) + "Ngày trước");
        }
        modelStringout.add(out1);

        List<MessageAccount> list = iMessageservices.findSendAccount(acc.getMail());
        //      JsonServices.dd(JsonServices.ParseToJson(list), response);

        Account accc = null;
        List<ModelString> modelStringoutold = new ArrayList<>();
        List<ModelString> modelStringoutnew = new ArrayList<>();
        List<String> dem = new ArrayList<>();
        List<String> demt = new ArrayList<>();
        for (int i2 = 0; i2 < list.size(); i2++) {
            ModelString out2 = new ModelString();
            MessageAccount account = list.get(i2);
            accc = accountRepository.findByMail(list.get(i2).getIdReceive());
            if (accc != null) {
                out2.setData5(accc.getIdStore().getId().toString());
                out2.setData1(accc.getAvatar());
                out2.setData2(accc.getFullname());
                out2.setData3(account.getMesssageContent());
                Date date2 = new Date();
                Date dateStart2 = new SimpleDateFormat("yyyy-MM-dd").parse(account.getCreateDate().toString());
                int num2 = Numday(dateStart2, date2);
                if (num2 <= 0) {
                    out2.setData4("Hôm nay");
                } else {
                    out2.setData4(String.valueOf(num) + "Ngày trước");
                }
                modelStringoutold.add(out2);
                dem.add(accc.getMail());
            }
        }

        for (int i = 0; i < dem.size(); i++) {
            if (!demt.contains(dem.get(i))) {
                demt.add(dem.get(i));
            }
        }
        for (int i = 0; i < demt.size(); i++) {
            List<MessageAccount> mac = new ArrayList<>();
            mac = iMessageservices.findSendTo(demt.get(i), modelString.getData1());
            Account accs = accountRepository.findByMail(demt.get(i));
            ModelString t = new ModelString();
            int isa = (mac.size() - 1);
            t.setData5(accs.getMail());
            t.setData1(accs.getAvatar());
            t.setData2(accs.getFullname());
            t.setData3(mac.get(isa).getMesssageContent());
            Date datet = new Date();
            Date dateStartt = new SimpleDateFormat("yyyy-MM-dd").parse(mac.get(isa).getCreateDate().toString());
            int numt = Numday(dateStartt, datet);
            if (numt <= 0) {
                t.setData4("Hôm nay");
            } else {
                t.setData4(String.valueOf(num) + "Ngày trước");
            }
            modelStringout.add(t);
        }
        JsonServices.dd(JsonServices.ParseToJson(modelStringout), response);

//        for (int i = 0; i > list.size(); i++) {
//            ModelString out = new ModelString();
//            MessageAccount account = list.get(i);
//            Account accc = accountRepository.findByMail(account.getIdReceive());
//            if (accc != null) {
//                out.setData5(accc.getMail());
//                out.setData1(accc.getAvatar());
//                out.setData2(accc.getFullname());
//                out.setData3(account.getMesssageContent());
//                Date date2 = new Date();
//                Date dateStart2 = new SimpleDateFormat("yyyy-MM-dd").parse(account.getCreateDate().toString());
//                int num1 = Numday(dateStart2, date2);
//                if (num1 <= 0) {
//                    out.setData4("Hôm nay");
//                } else {
//                    out.setData4(String.valueOf(num) + "Ngày trước");
//                }
//                modelStringout.add(out);
//                
//            }
//        }
        if (modelStringout != null) {
            JsonServices.dd(JsonServices.ParseToJson(modelStringout), response);

        } else {
            modelString.setData5("Unsusssess");
            JsonServices.dd(JsonServices.ParseToJson(modelString), response);
        }

    }

    @RequestMapping(value = {"/api/message/send"}, method = RequestMethod.GET)
    public void SendMessage(Model model, HttpServletRequest request,
            HttpServletResponse response) throws ParseException, FirebaseMessagingException {
        ModelString modelString = new ModelString();
        List<ModelString> modelStringout = new ArrayList<>();
        modelString.setData1(request.getParameter("content"));
        modelString.setData2(request.getParameter("send"));
        modelString.setData3(request.getParameter("to"));
        MessageAccount account = new MessageAccount();
        account.setMesssageContent(modelString.getData1());
        account.setMailSend(modelString.getData2());
        account.setIdReceive(modelString.getData3());
        Date date = new Date();
        account.setCreateDate(date);
        iMessageservices.AddMessageAccount(account);
        Account account1 = accountRepository.findByMail(modelString.getData2());
        modelStringout.add(modelString);
        Notification n = new Notification();
        n.setTitle("Thông báo tin nhắn mới !");
        n.setContent(account1.getFullname() + " : " + modelString.getData1() + "." + "\n"
        );
        List<AccountToken> listToken = accToken.GetTokenByMail(modelString.getData3());
        List<String> listtokenstring = new ArrayList<>();
        for (AccountToken accountToken : listToken) {
            listtokenstring.add(accountToken.getToken());
        }
        AccountNotification accountNotification = new AccountNotification();
        accountNotification.setIdnotification(n);
        accountNotification.setMail(account1);
        accountNotification.setStatus(false);
//        if (listtokenstring != null) {
//            firebaseMessagingService.sendMorePeople(accountNotification, listtokenstring);
//        }    
//        } else {
//             Account acc = accountRepository.findByMail(modelString.getData2());
//            List<Account> list = accountRepository.findByStore(acc.getIdStore());
//            list.remove(acc);
//            for (Account account2 : list) {
//                if (account2.getRole().equals("2")){
//                    list.remove(account2);
//                }
//            }
//            List<String> listtokenstrings = new ArrayList<>();
//            for (int i = 0; i < list.size(); i++) {
//                List<AccountToken> listTokens = accToken.GetTokenByMail(list.get(i).getMail());
//                for (AccountToken accountToken : listTokens) {
//                    listtokenstrings.add(accountToken.getToken());
//                }
//            }
//            if (listtokenstrings != null) {
//                firebaseMessagingService.sendMorePeople(accountNotification, listtokenstrings);
//            }
//        }

        if (modelStringout != null) {
            JsonServices.dd(JsonServices.ParseToJson(modelStringout), response);

        } else {
            modelString.setData5("Unsusssess");
            JsonServices.dd(JsonServices.ParseToJson(modelString), response);
        }

    }
}
