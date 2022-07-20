/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Controller;

import fpt.aptech.KHR.Entities.ModelString;
import fpt.aptech.KHR.Entities.Timeline;
import fpt.aptech.KHR.Entities.UserTimeline;
import fpt.aptech.KHR.ImpServices.JsonServices;
import fpt.aptech.KHR.ImpServices.TimelineServices;
import fpt.aptech.KHR.ImpServices.UserTimelineServices;
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
}
