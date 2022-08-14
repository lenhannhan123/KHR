/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Controller;

import fpt.aptech.KHR.Entities.*;
import fpt.aptech.KHR.ImpServices.*;
import fpt.aptech.KHR.Routes.RouteAPI;
import fpt.aptech.KHR.Routes.RouteWeb;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Admin
 */
@Controller
public class APITimelineController {

    @Autowired
    AccountService accountService;

    @Autowired
    TransferService transferService;

    @Autowired
    AccountPositionService accountPositionService;

    @Autowired
    PositionServices positionServices;

    @Autowired
    TimelineDetailServices timelineDetailServices;

    @Autowired
    UserTimelineServices userTimelineServices;

    @Autowired
    TimelineServices timelineServices;

    @RequestMapping(value = {RouteAPI.Timelinecheckmytimeline}, method = RequestMethod.GET)
    public void page(Model model, HttpServletRequest request, HttpServletResponse response) {


        String idUser = request.getParameter("id").toString();

        String month = request.getParameter("month").toString();

        String year = request.getParameter("year").toString();


        List<Integer> data = userTimelineServices.GetIdTimeline(idUser);

        List<ModelString> data1 = new ArrayList<>();


        String tmpYear;
        String tmpMonth;


        for (int i = 0; i < data.size(); i++) {

//            data.get(i)
            Timeline tmptimeline = timelineServices.FindOne(data.get(i));

            if (tmptimeline.getStatus() == 0) {


                if ((month.equals("00")) && (!year.equals("00"))) {

                    tmpYear = tmptimeline.getStartdate().toString().substring(0, 4);
                    if (tmpYear.equals(year)) {

                        ModelString tmpdata = new ModelString();
                        tmpdata.setData1(data.get(i).toString());
                        tmpdata.setData2(tmptimeline.getTimename());
                        data1.add(tmpdata);
                    }

                }

                if ((!month.equals("00")) && (year.equals("00"))) {

                    tmpYear = tmptimeline.getStartdate().toString().substring(0, 4);
                    if (tmpYear.equals(year)) {
                        ModelString tmpdata = new ModelString();
                        tmpdata.setData1(data.get(i).toString());
                        tmpdata.setData2(tmptimeline.getTimename());
                        data1.add(tmpdata);


                    }

                }

                if ((month.equals("00")) && (year.equals("00"))) {


                    ModelString tmpdata = new ModelString();
                    tmpdata.setData1(data.get(i).toString());
                    tmpdata.setData2(tmptimeline.getTimename());
                    data1.add(tmpdata);


                }

                if ((!month.equals("00")) && (!year.equals("00"))) {

                    tmpYear = tmptimeline.getStartdate().toString().substring(0, 4);
                    tmpMonth = String.valueOf(tmptimeline.getStartdate().getMonth() + 1);
                    if (tmpYear.equals(year) && tmpMonth.equals(month)) {
                        ModelString tmpdata = new ModelString();
                        tmpdata.setData1(data.get(i).toString());
                        tmpdata.setData2(tmptimeline.getTimename());
                        data1.add(tmpdata);


                    }

                }


            }


        }


        JsonServices.dd(JsonServices.ParseToJson(data1), response);
//        JsonServices.dd(JsonServices.ParseToJson(), response);


    }


    @RequestMapping(value = {RouteAPI.TimelineGetYear}, method = RequestMethod.GET)
    public void GetYear(Model model, HttpServletRequest request, HttpServletResponse response) {

        List<Timeline> timelines = timelineServices.findAll();
        List<Integer> ListYear = new ArrayList<>();
        String tmpYear;
        boolean check = true;

        for (Timeline timeline : timelines) {

            tmpYear = timeline.getStartdate().toString().substring(0, 4);
            check = true;

            for (Integer item : ListYear) {
                if (item == Integer.parseInt(tmpYear)) {
                    check = false;

                }
            }

            if (check == true) {

                ListYear.add(Integer.parseInt(tmpYear));
            }


        }
        if (ListYear.size() != 0) {

            Collections.sort(ListYear);
        }


        JsonServices.dd(JsonServices.ParseToJson(ListYear), response);


    }


    @RequestMapping(value = {RouteAPI.TimelineGetMyTimelineDetail}, method = RequestMethod.GET)
    public void TimelineGetMyTimelineDetail(Model model, HttpServletRequest request, HttpServletResponse response) {

        String idUserTimeline = request.getParameter("id").toString();
        String idUser = request.getParameter("mail").toString();

        List<UserTimeline> userTimeline = new ArrayList<>();
        userTimeline = userTimelineServices.UserTimeline(Integer.parseInt(idUserTimeline), idUser);

        List<Integer> shiftCode = new ArrayList<>();

        if (userTimeline.size() > 0) {

            for (UserTimeline item : userTimeline) {

                shiftCode.add((int) item.getShiftcode());

            }


        }


        JsonServices.dd(JsonServices.ParseToJson(shiftCode), response);

//        JsonServices.dd(JsonServices.ParseToJson(ListYear), response);


    }


    @RequestMapping(value = {RouteAPI.TimelineGetAddTimeline}, method = RequestMethod.GET)
    public void GetAddTimeline(Model model, HttpServletRequest request, HttpServletResponse response) {

        String idUser = request.getParameter("mail").toString();
        List<Timeline> timelines = timelineServices.FindAllWhenStatusOn();
        List<ModelString> models = new ArrayList<>();


        for (Timeline item : timelines) {
            ModelString mdString = new ModelString();
            mdString.setData2(item.getTimename());
            mdString.setData1(item.getId().toString());
            models.add(mdString);
        }
        JsonServices.dd(JsonServices.ParseToJson(models), response);

    }

    @RequestMapping(value = {RouteAPI.GetTimeLineSort}, method = RequestMethod.GET)
    public void GetTimeLineSort(Model model, HttpServletRequest request, HttpServletResponse response) {

        String idUser = request.getParameter("mail").toString();

        Account account = accountService.findByMail(idUser);

        int id_store = account.getIdStore().getId();

        List<Timeline> timelineList = new ArrayList<>();
        timelineList = timelineServices.findAll();

        for (int i = 0; i < timelineList.size(); i++) {
            if (timelineList.get(i).getIdStore().getId() != id_store) {
                timelineList.remove(timelineList.get(i));
                i -= 1;
            }
        }
        for (int i = 0; i < timelineList.size(); i++) {
            List<TimelineDetail> timelineDetail = timelineDetailServices.FindbyIdTimeline(timelineList.get(i).getId());
            if (timelineDetail.size() == 0) {
                timelineList.remove(timelineList.get(i));
                i -= 1;
            }

        }

        List<ModelString> modelStringList = new ArrayList<>();

        for (Timeline item : timelineList) {

            ModelString modelString = new ModelString();
            modelString.setData1(String.valueOf(item.getId()));
            modelString.setData2(item.getTimename());
            modelStringList.add(modelString);

        }

        JsonServices.dd(JsonServices.ParseToJson(modelStringList), response);


    }


    @RequestMapping(value = {RouteAPI.GetTimeLineSort1}, method = RequestMethod.GET)
    public void GetTimeLineSort1(Model model, HttpServletRequest request, HttpServletResponse response) {

        String idUser = request.getParameter("mail").toString();
        String idUserTimeline = request.getParameter("id").toString();

        List<TimelineDetail> timelineDetails = new ArrayList<>();
        timelineDetails = timelineDetailServices.FindbyIdTimeline(Integer.parseInt(idUserTimeline));


        List<Integer> shiftCode = new ArrayList<>();
        for (int i = 0; i < timelineDetails.size(); i++) {
            if (timelineDetails.get(i).getMail().getMail().equals(idUser)) {
                shiftCode.add((int) timelineDetails.get(i).getShiftCode());
            }
        }

        for (int i = 0; i < shiftCode.size(); i++) {

            shiftCode.set(i, shiftCode.get(i) + 1);

        }


        JsonServices.dd(JsonServices.ParseToJson(shiftCode), response);


    }

    @RequestMapping(value = {RouteAPI.GetTimeLineSortDetail}, method = RequestMethod.GET)
    public void GetTimeLineSortDetail(Model model, HttpServletRequest request, HttpServletResponse response) {

        String shiftcode = request.getParameter("shiftcode").toString();
        String idUserTimeline = request.getParameter("id").toString();

        List<TimelineDetail> timelineDetails = new ArrayList<>();
        timelineDetails = timelineDetailServices.FindbyIdTimeline(Integer.parseInt(idUserTimeline));
        shiftcode = String.valueOf(Integer.parseInt(shiftcode) - 1);

        for (int i = 0; i < timelineDetails.size(); i++) {


            if (timelineDetails.get(i).getShiftCode() != Integer.parseInt(shiftcode)) {
                timelineDetails.remove(timelineDetails.get(i));
                i -= 1;
            }

        }


        List<Position> ListPos = positionServices.findAll();
        List<ModelString> modelStringList = new ArrayList<>();

        for (Position item : ListPos) {
            ModelString modelString = new ModelString();
            modelString.setData1(item.getId().toString());
            modelString.setData2(item.getPositionname());
            modelString.setData4("0");
            modelStringList.add(modelString);
        }

        for (int i = 0; i < modelStringList.size(); i++) {


            for (TimelineDetail item2 : timelineDetails) {

                if (modelStringList.get(i).getData1().equals(String.valueOf(item2.getIdPosition().getId()))) {

                    modelStringList.get(i).setData4(String.valueOf(Integer.parseInt(modelStringList.get(i).getData4()) + 1));
                }

            }

        }


        for (int i = 0; i < modelStringList.size(); i++) {
            if (modelStringList.get(i).getData4().equals("0")) {
                modelStringList.remove(modelStringList.get(i));
                i -= 1;
            }

        }

        String text = "";
        for (int i = 0; i < modelStringList.size(); i++) {
            text = "";
            for (TimelineDetail item2 : timelineDetails) {
                if (modelStringList.get(i).getData1().equals(item2.getIdPosition().getId().toString())) {

                    text += item2.getMail().getMail() + ",\n";
                }

            }
            ModelString modelstr = new ModelString();
            modelstr.setData1(modelStringList.get(i).getData1());
            modelstr.setData2(modelStringList.get(i).getData2());
            modelstr.setData3(text);
            modelstr.setData4(modelStringList.get(i).getData4());
            modelStringList.set(i, modelstr);
        }


        JsonServices.dd(JsonServices.ParseToJson(modelStringList), response);


    }

    @RequestMapping(value = {RouteAPI.GetReportChooseTimeline}, method = RequestMethod.GET)
    public void GetReportChooseTimeline(Model model, HttpServletRequest request, HttpServletResponse response) {

        String idUser = request.getParameter("mail").toString();
        List<Integer> ListTimeline = userTimelineServices.GetIdTimeline(idUser);

        int Store = accountService.findByMail(idUser).getIdStore().getId();
        long Todaymili = Long.parseLong(JsonServices.ParseToJson(new Date()).toString());

//
//        JsonServices.dd(JsonServices.ParseToJson(ListTimeline), response);
        List<Timeline> timelineList = new ArrayList<>();


        for (Integer item : ListTimeline) {

            Timeline timeline = timelineServices.FindOne(item);
            long Datemili = Long.parseLong(JsonServices.ParseToJson(timeline.getEnddate()).toString());

            if ((timeline.getIdStore().getId() == Store) && (Datemili > Todaymili)) {

                timelineList.add(timeline);
            }


        }

        List<ModelString> modelStringList = new ArrayList<>();

        for (Timeline item : timelineList) {
            ModelString modelString = new ModelString();
            modelString.setData1(item.getId().toString());
            modelString.setData2(item.getTimename());
            modelStringList.add(modelString);
        }

        JsonServices.dd(JsonServices.ParseToJson(modelStringList), response);
    }


    @RequestMapping(value = {RouteAPI.GetReportMyDate}, method = RequestMethod.GET)
    public void GetReportMyDate(Model model, HttpServletRequest request, HttpServletResponse response) {

        String idUser = request.getParameter("mail").toString();
        String idTimeliine = request.getParameter("id").toString();

        Timeline timeline = timelineServices.FindOne(Integer.parseInt(idTimeliine));

        long today = Long.parseLong(JsonServices.ParseToJson(new Date()).toString());
        long startday = Long.parseLong(JsonServices.ParseToJson(timeline.getStartdate()).toString());
        List<ModelString> stringList = new ArrayList<>();

        if (startday < today) {

            long millinumeberday = today - startday;
            long numberday = millinumeberday / 86400000;
            numberday += 1;

            for (long i = numberday + 2; i <= 8; i++) {
                ModelString modelString = new ModelString();

                if (i < 8) {
                    modelString.setData2("Thứ " + i);

                } else {
                    modelString.setData2("Chủ Nhật");
                }
                modelString.setData1(String.valueOf(i));
                stringList.add(modelString);

            }

        } else {

            for (long i = 2; i <= 8; i++) {
                ModelString modelString = new ModelString();

                if (i < 8) {
                    modelString.setData2("Thứ " + i);

                } else {
                    modelString.setData2("Chủ Nhật");
                }
                modelString.setData1(String.valueOf(i));
                stringList.add(modelString);

            }
        }

        JsonServices.dd(JsonServices.ParseToJson(stringList), response);

//        JsonServices.dd(JsonServices.ParseToJson(timeline),response);


//        JsonServices.dd(JsonServices.ParseToJson(modelStringList), response);
    }


    @RequestMapping(value = {RouteAPI.GetReportMyShift}, method = RequestMethod.GET)
    public void GetReportMyShift(Model model, HttpServletRequest request, HttpServletResponse response) {

        String idUser = request.getParameter("mail").toString();
        String idTimeliine = request.getParameter("id").toString();
        String number = request.getParameter("number").toString();
        final int shiftcodemin = (Integer.parseInt(number) - 2) * 5;
        List<ModelString> modelStringList = new ArrayList<>();

        List<TimelineDetail> timelineDetailList = timelineDetailServices.FindbyIdTimeline(Integer.parseInt(idTimeliine));

        for (int i = 0; i < timelineDetailList.size(); i++) {

            if ((timelineDetailList.get(i).getShiftCode() >= shiftcodemin) && (timelineDetailList.get(i).getShiftCode() < (shiftcodemin + 5))) {

            } else {
                timelineDetailList.remove(timelineDetailList.get(i));
                i -= 1;
            }


        }

        for (int i = 0; i < timelineDetailList.size(); i++) {

            if (!timelineDetailList.get(i).getMail().getMail().equals(idUser)) {

                timelineDetailList.remove(timelineDetailList.get(i));
                i -= 1;
            }
        }

        for (int i = 0; i < timelineDetailList.size(); i++) {

            int code = timelineDetailList.get(i).getShiftCode();

            ModelString modelString = new ModelString();
            modelString.setData1(String.valueOf(code));

            switch (code - shiftcodemin) {

                case 0:
                    modelString.setData2("Ca sáng");
                    break;
                case 1:
                    modelString.setData2("Ca trưa");
                    break;
                case 2:
                    modelString.setData2("Ca chiều");
                    break;
                case 3:
                    modelString.setData2("Ca tối");
                    break;
                default:
                    modelString.setData2("Ca đêm");
                    break;

            }

            modelStringList.add(modelString);

        }


        JsonServices.dd(JsonServices.ParseToJson(modelStringList), response);


    }


    @RequestMapping(value = {RouteAPI.GetReportMyPosition}, method = RequestMethod.GET)
    public void GetReportMyPosition(Model model, HttpServletRequest request, HttpServletResponse response) {

        String idUser = request.getParameter("mail").toString();

        List<AccountPosition> accountPositionList = new ArrayList<>();
        accountPositionList = accountPositionService.findByEmail(new Account(idUser));

        List<ModelString> modelStringList = new ArrayList<>();

        for (AccountPosition item : accountPositionList) {
            ModelString modelString = new ModelString();
            modelString.setData1(item.getIdPosition().getId().toString());
            modelString.setData2(item.getIdPosition().getPositionname());
            modelStringList.add(modelString);
        }


        JsonServices.dd(JsonServices.ParseToJson(modelStringList), response);
    }

    @RequestMapping(value = {RouteAPI.GetReportYourUser}, method = RequestMethod.GET)
    public void GetReportYourUser(Model model, HttpServletRequest request, HttpServletResponse response) {

        String Shiftcode = request.getParameter("code").toString();
        String id = request.getParameter("id").toString();
        String Position = request.getParameter("position").toString();

        List<TimelineDetail> timelineDetail = timelineDetailServices.FindbyShiftcode(Integer.parseInt(Shiftcode), new Timeline(Integer.parseInt(id)));
        List<ModelString> modelStringList = new ArrayList<>();
        if (timelineDetail.size() > 0) {
            for (TimelineDetail item2 : timelineDetail) {
                if (item2.getIdPosition().getId().toString().equals(Position)) {
                    ModelString ModelString = new ModelString();
                    ModelString.setData1(item2.getMail().getMail());
                    ModelString.setData2(item2.getMail().getMail());
                    modelStringList.add(ModelString);
                }


            }
        }

        JsonServices.dd(JsonServices.ParseToJson(modelStringList), response);
    }


    @RequestMapping(value = {RouteAPI.GetReportCheckPosition}, method = RequestMethod.GET)
    public void GetReportCheckPosition(Model model, HttpServletRequest request, HttpServletResponse response) {

        String mycode = request.getParameter("mycode").toString();
        String yourcode = request.getParameter("yourcode").toString();

        String Idtimeline = request.getParameter("id").toString();

        String mymail = request.getParameter("mymail").toString();
        String yourmail = request.getParameter("yourmail").toString();

        String IdPos = request.getParameter("idpos").toString();


        List<ModelString> modelStringList = new ArrayList<>();
        ModelString modelString = new ModelString();


        List<TimelineDetail> timelineDetail = timelineDetailServices.FindbyShiftcode(Integer.parseInt(mycode), new Timeline(Integer.parseInt(Idtimeline)));
        for (TimelineDetail item1 : timelineDetail) {
            if (item1.getMail().getMail().equals(yourmail)) {

                modelString.setData1("Người này cũng đang làm ở ca bạn đổi");
                modelStringList.add(modelString);
                JsonServices.dd(JsonServices.ParseToJson(modelStringList), response);
            }
        }


        List<TimelineDetail> timelineDetail1 = timelineDetailServices.FindbyShiftcode(Integer.parseInt(yourcode), new Timeline(Integer.parseInt(Idtimeline)));

        for (TimelineDetail item1 : timelineDetail1) {
            if (item1.getMail().getMail().equals(mymail)) {
                modelString.setData1("Bạn đang làm ca bạn sắp đổi");
                modelStringList.add(modelString);
                JsonServices.dd(JsonServices.ParseToJson(modelStringList), response);

            }
        }


        List<AccountPosition> accountPositionList = accountPositionService.findByEmail(new Account(yourmail));

        boolean check = false;
        for (AccountPosition item : accountPositionList) {
            if (item.getIdPosition().getId().toString().equals(IdPos)) {
                check = true;
            }

        }

        if (check == false) {

            modelString.setData1("Người bạn đổi không làm được vị trí bạn đang làm");
            modelStringList.add(modelString);
            JsonServices.dd(JsonServices.ParseToJson(modelStringList), response);

        }

        modelString.setData1("done");
        modelStringList.add(modelString);
        JsonServices.dd(JsonServices.ParseToJson(modelStringList), response);


//        JsonServices.dd(JsonServices.ParseToJson(modelStringList), response);
    }


    @RequestMapping(value = {RouteAPI.PostReportSendata}, method = RequestMethod.POST)
    public void PostReportSendata(Model model, HttpServletRequest request, HttpServletResponse response) {

        String mycode = request.getParameter("mycode").toString();
        String yourcode = request.getParameter("yourcode").toString();

        String Idtimeline = request.getParameter("id").toString();

        String mymail = request.getParameter("mymail").toString();
        String yourmail = request.getParameter("yourmail").toString();

        String IdPos = request.getParameter("idpos").toString();

        String content = request.getParameter("content").toString();
        int posfrom = 0;

        List<TimelineDetail> timelineDetail = timelineDetailServices.FindbyShiftcode(Integer.parseInt(mycode), new Timeline(Integer.parseInt(Idtimeline)));

        for (TimelineDetail item : timelineDetail) {

            if (item.getMail().getMail().equals(mymail)) {
                posfrom = item.getIdPosition().getId();
            }

        }


        TransferData transferData = new TransferData();
        transferData.setName(mymail + " Xin đổi ca ");
        transferData.setContent(content);
        transferData.setShiftcodefrom(Integer.parseInt(mycode));
        transferData.setShiftcodeto(Integer.parseInt(yourcode));
        transferData.setPositionfrom(posfrom);
        transferData.setPositionto(Integer.parseInt(IdPos));
        transferData.setMailfrom(mymail);
        transferData.setMailto(yourmail);
        transferData.setIdTimeline(Integer.parseInt(Idtimeline));
        transferData.setStatus(0);

        transferService.Create(transferData);

//        JsonServices.dd(JsonServices.ParseToJson(modelStringList), response);
    }


    @RequestMapping(value = {RouteAPI.GetReport1}, method = RequestMethod.GET)
    public void GetReport1(Model model, HttpServletRequest request, HttpServletResponse response) {

        String User_Id = request.getParameter("mail").toString();
        List<TransferData> transferDataList = transferService.findAll();

        for (int i = 0; i < transferDataList.size(); i++) {


            if (!transferDataList.get(i).getMailfrom().equals(User_Id)) {

                transferDataList.remove(transferDataList.get(i));
                i-=1;
            }

        }

        List<ModelString> modelStringList = new ArrayList<>();

        for (TransferData item : transferDataList) {
            ModelString modelString = new ModelString();
            modelString.setData1(item.getId().toString());
            modelString.setData2(item.getName());
            modelString.setData3(item.getStatus().toString());
            modelStringList.add(modelString);

        }


        JsonServices.dd(JsonServices.ParseToJson(modelStringList), response);
    }

    @RequestMapping(value = {RouteAPI.GetReport1Detail}, method = RequestMethod.GET)
    public void GetReport1Detail(Model model, HttpServletRequest request, HttpServletResponse response) {

        String Id = request.getParameter("id").toString();

        TransferData transferData = transferService.FindOne(Integer.parseInt(Id));

        Timeline timeline = timelineServices.FindOne(transferData.getIdTimeline());
        String time = timeline.getTimename() + " Từ ngày " + timeline.getStartdate() + " Đến ngày " + timeline.getEnddate();

        Position position1  = positionServices.FindOne(transferData.getPositionfrom());
        Position position2  = positionServices.FindOne(transferData.getPositionto());


        int thu1 = (transferData.getShiftcodefrom() / 5) + 2;
        int thu2 = (transferData.getShiftcodeto() / 5) + 2;
        int ca1 = (transferData.getShiftcodefrom() % 5) + 1;
        int ca2 = (transferData.getShiftcodeto() % 5) + 1;

        ModelString modelString = new ModelString();
        modelString.setData1(transferData.getName());
        modelString.setData2(time);
        modelString.setData3("Thứ "+ thu1 + " (Ca "+ca1+")");
        modelString.setData4("Thứ "+thu2 + " (Ca "+ca2+")");
        modelString.setData5(position1.getPositionname());
        modelString.setData6(position2.getPositionname());
        modelString.setData7(transferData.getMailfrom());
        modelString.setData8(transferData.getMailto());
        modelString.setData9(transferData.getContent());
        modelString.setData10(transferData.getStatus().toString());
        modelString.setData11(transferData.getResponse());

        List<ModelString> modelStringList = new ArrayList<>();
        modelStringList.add(modelString);

        JsonServices.dd(JsonServices.ParseToJson(modelStringList), response);
    }

    @RequestMapping(value = {RouteAPI.GetReport2}, method = RequestMethod.GET)
    public void GetReport2(Model model, HttpServletRequest request, HttpServletResponse response) {

        String User_Id = request.getParameter("mail").toString();
        List<TransferData> transferDataList = transferService.findAll();

        for (int i = 0; i < transferDataList.size(); i++) {


            if (!transferDataList.get(i).getMailto().equals(User_Id)) {

                transferDataList.remove(transferDataList.get(i));
                i-=1;
            }

        }

        List<ModelString> modelStringList = new ArrayList<>();

        for (TransferData item : transferDataList) {
            ModelString modelString = new ModelString();
            modelString.setData1(item.getId().toString());
            modelString.setData2(item.getName());
            modelString.setData3(item.getStatus().toString());
            modelStringList.add(modelString);

        }


        JsonServices.dd(JsonServices.ParseToJson(modelStringList), response);
    }


    @RequestMapping(value = {RouteAPI.GetConfirm}, method = RequestMethod.GET)
    public void GetConfirm(Model model, HttpServletRequest request, HttpServletResponse response) {

        String id = request.getParameter("id").toString();
        String status = request.getParameter("status").toString();
        String response1 = request.getParameter("response").toString();

        TransferData transferData = transferService.FindOne(Integer.parseInt(id));

        transferData.setStatus(Integer.parseInt(status));
        transferData.setResponse(response1);
        transferService.Edit(transferData);

        JsonServices.dd("Done", response);
    }

}
