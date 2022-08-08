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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Time;
import java.text.SimpleDateFormat;
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
        for (int i = 0; i <timelineDetails.size() ; i++) {
            if(timelineDetails.get(i).getMail().getMail().equals(idUser)){
                shiftCode.add((int) timelineDetails.get(i).getShiftCode());
            }
        }

        for (int i = 0; i <shiftCode.size() ; i++) {

                shiftCode.set(i,shiftCode.get(i)+1);

        }



        JsonServices.dd(JsonServices.ParseToJson(shiftCode), response);


    }

    @RequestMapping(value = {RouteAPI.GetTimeLineSortDetail}, method = RequestMethod.GET)
    public void GetTimeLineSortDetail(Model model, HttpServletRequest request, HttpServletResponse response) {

        String shiftcode = request.getParameter("shiftcode").toString();
        String idUserTimeline = request.getParameter("id").toString();

        List<TimelineDetail> timelineDetails = new ArrayList<>();
        timelineDetails = timelineDetailServices.FindbyIdTimeline(Integer.parseInt(idUserTimeline));
        shiftcode= String.valueOf(Integer.parseInt(shiftcode)-1);

        for (int i = 0; i < timelineDetails.size(); i++) {


            if (timelineDetails.get(i).getShiftCode() != Integer.parseInt(shiftcode)){
                timelineDetails.remove(timelineDetails.get(i));
                i-=1;
            }

        }


        List<Position> ListPos =positionServices.findAll();
        List<ModelString> modelStringList = new ArrayList<>();

        for (Position item:ListPos ) {
            ModelString modelString = new ModelString();
            modelString.setData1(item.getId().toString());
            modelString.setData2(item.getPositionname());
            modelString.setData4("0");
            modelStringList.add(modelString);
        }

        for (int i = 0; i < modelStringList.size(); i++) {


            for (TimelineDetail item2: timelineDetails ) {

                if (modelStringList.get(i).getData1().equals(String.valueOf(item2.getIdPosition().getId()))){

                    modelStringList.get(i).setData4(String.valueOf(Integer.parseInt(modelStringList.get(i).getData4())+1));
                }

            }

        }


        for (int i = 0; i < modelStringList.size(); i++) {
            if (modelStringList.get(i).getData4().equals("0")){
                modelStringList.remove(modelStringList.get(i));
                i-=1;
            }

        }

        String text="";
        for (int i = 0; i < modelStringList.size(); i++) {
            text="";
            for (TimelineDetail item2: timelineDetails ) {
                if (modelStringList.get(i).getData1().equals(item2.getIdPosition().getId().toString())){

                    text+=item2.getMail().getMail() + ",\n";
                }

            }
            ModelString modelstr = new ModelString();
            modelstr.setData1(modelStringList.get(i).getData1());
            modelstr.setData2(modelStringList.get(i).getData2());
            modelstr.setData3(text);
            modelstr.setData4(modelStringList.get(i).getData4());
            modelStringList.set(i,modelstr);
        }



        JsonServices.dd(JsonServices.ParseToJson(modelStringList), response);


    }


}
