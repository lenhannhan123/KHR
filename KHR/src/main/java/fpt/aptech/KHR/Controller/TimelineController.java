package fpt.aptech.KHR.Controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import fpt.aptech.KHR.Entities.*;
import fpt.aptech.KHR.ImpServices.*;
import fpt.aptech.KHR.Routes.RouteWeb;
import fpt.aptech.KHR.Services.ITimelineServices;
import org.json.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class TimelineController {

    @Autowired
    TimelineServices timelineServices;

    @Autowired
    PositionServices positionServices;

    @Autowired
    ShiftServices shiftServices;

    @RequestMapping(value = {RouteWeb.TimelineIndexURL}, method = RequestMethod.GET)
    public String IndexTimeline(Model model) {
        List<Timeline> list = timelineServices.findAll();

        boolean check = false;

        for (Timeline item : list) {
            if (item.getId() != null) {

                check = true;
                break;
            }
        }
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);


        model.addAttribute("check", check);

        model.addAttribute("simpleDateFormat", simpleDateFormat);

        model.addAttribute("list", list);
        return "timeline/index";
    }


    @RequestMapping(value = {RouteWeb.TimelineGetCreateURL}, method = RequestMethod.GET)
    public String GetCreate(Model model) {


        List<Position> ListPosition = positionServices.findAll();

        model.addAttribute("ListPosition", ListPosition);

        return "timeline/create";
    }


    @RequestMapping(value = RouteWeb.TimelineGetCreateURL, method = RequestMethod.POST)
    public String PostCreate(Model model, HttpServletRequest request, HttpServletResponse response) {
        int NumberofPosition = positionServices.CountPosition();
        HttpSession session = request.getSession();

        session.setAttribute("TimelineName", request.getParameter("TimelineName"));
        session.setAttribute("TimelineStartDay", request.getParameter("TimelineStartDay"));
        session.setAttribute("TimelineEndDay", request.getParameter("TimelineEndDay"));
        session.setAttribute("NumberofPosition", NumberofPosition);
        session.setAttribute("numberdayoff", request.getParameter("numberdayoff"));


        String numberdayoff = request.getParameter("numberdayoff");


        if (Integer.parseInt(numberdayoff) > 0) {
            for (int i = 1; i <= Integer.parseInt(numberdayoff); i++) {

                session.setAttribute("dayoff" + i, request.getParameter("dayoff" + i));
            }

        }


        for (int i = 1; i <= NumberofPosition; i++) {
            if (request.getParameter("check" + i) != null || request.getParameter("check" + i) != "") {
                session.setAttribute("check" + i, request.getParameter("check" + i));
                session.setAttribute("value" + i, request.getParameter("value" + i));

            }

        }

        String redirectUrl = "/timeline/cofirm";
        return "redirect:" + redirectUrl;
    }


    @RequestMapping(value = {RouteWeb.TimelineConfirmURL}, method = RequestMethod.GET)
    public String GetCofirm(Model model, HttpServletRequest request, HttpServletResponse response) {

        List<Position> positionList = positionServices.findAll();
        HttpSession session = request.getSession();
        if (session.getAttribute("TimelineStartDay") == null) {
            return "redirect:/timeline/index";
        }


        String TimelineStartDay = session.getAttribute("TimelineStartDay").toString();
        String TimelineEndDay = session.getAttribute("TimelineEndDay").toString();
        String NumberofPosition = session.getAttribute("NumberofPosition").toString();

        String numberdayoff = session.getAttribute("numberdayoff").toString();


        int[][] Position = new int[Integer.parseInt(NumberofPosition)][2];

        int j = 0;

        for (int i = 1; i <= Integer.parseInt(NumberofPosition); i++) {

            if (session.getAttribute("check" + i) != null) {
                Position[j][0] = Integer.parseInt(session.getAttribute("check" + i).toString());
                Position[j][1] = Integer.parseInt(session.getAttribute("value" + i).toString());
                j += 1;
            }


        }

        int Intnumberdayoff = Integer.parseInt(numberdayoff);

//        JsonServices.dd(Intnumberdayoff, response);
        String[] Dayoff = new String[Intnumberdayoff + 1];
        if (Intnumberdayoff > 0) {


            for (int i = 1; i <= Integer.parseInt(numberdayoff); i++) {
                Dayoff[i] = session.getAttribute("dayoff" + i).toString();


            }


        } else {
            Dayoff[0] = "0";

        }


        // Create ObjectMapper object.
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String json = "";
        String PositionJson = "";
        String DayoffJson = "";
        try {
            json = mapper.writeValueAsString(positionList);
            PositionJson = mapper.writeValueAsString(Position);
            DayoffJson = mapper.writeValueAsString(Dayoff);


        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        model.addAttribute("itemsArray", json);
        model.addAttribute("TimelineStartDay", TimelineStartDay);
        model.addAttribute("TimelineEndDay", TimelineEndDay);
        model.addAttribute("Position", PositionJson);
        model.addAttribute("DayoffJson", DayoffJson);

        return "timeline/confirm";
    }


    @RequestMapping(value = {RouteWeb.TimelineConfirmURL}, method = RequestMethod.POST)
    public String PostCofirm(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam String data) {

//        JsonServices.dd(data, response);

        List<ShiftJS> ListShiftJS = new ArrayList<>();


        JSONArray jsonArr;
        try {
            jsonArr = new JSONArray(data);

            for (int i = 0; i < jsonArr.length(); i++) {
                ShiftJS ShiftJS = new ShiftJS();
                JSONObject jsonObj = null;
                jsonObj = jsonArr.getJSONObject(i);

                ShiftJS.setName((String) jsonObj.get("name"));
                ShiftJS.setDate((String) jsonObj.get("date"));


                List<PositionJS> ListpositionJS = new ArrayList<>();
                JSONArray jsonArr1 = (JSONArray) jsonObj.get("position");


                for (int j = 0; j < jsonArr1.length(); j++) {
                    PositionJS positionJS = new PositionJS();

                    JSONObject PositionObj = jsonArr1.getJSONObject(j);

                    positionJS.setId((int) PositionObj.get("id"));
                    positionJS.setIsCheck((boolean) PositionObj.get("isCheck"));
                    positionJS.setNumber((int) PositionObj.get("number"));
                    positionJS.setPositionname((String) PositionObj.get("positionname"));
                    positionJS.setSalarydefault((int) PositionObj.get("salarydefault"));


                    ListpositionJS.add(positionJS);
                }


                ShiftJS.position.addAll(ListpositionJS);


                ListShiftJS.add(ShiftJS);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        HttpSession session = request.getSession();
        String TimelineStartDay = session.getAttribute("TimelineStartDay").toString();
        String TimelineEndDay = session.getAttribute("TimelineEndDay").toString();
        String TimelineName = session.getAttribute("TimelineName").toString();

        Date TimelineStartDayParse;
        Date TimelineEndDayParse;

        try {
            TimelineStartDayParse = new SimpleDateFormat("yyyy-MM-dd").parse(TimelineStartDay);
            TimelineEndDayParse = new SimpleDateFormat("yyyy-MM-dd").parse(TimelineEndDay);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        Timeline timeline = new Timeline();
        timeline.setTimename(TimelineName);
        timeline.setStartdate(TimelineStartDayParse);
        timeline.setEnddate(TimelineEndDayParse);
        timeline.setStatus((short) 0);
        timeline = timelineServices.Create(timeline);


//        JsonServices.dd(timeline.getId(), response);


        int i = 0;
        for (ShiftJS item : ListShiftJS
        ) {

            for (PositionJS position : item.position
            ) {

                if (position.getNumber() > 0) {
                    Shift shift = new Shift();

                    shift.setIdTimeline(new Timeline(timeline.getId()));
                    shift.setIdPosition(new Position(position.getId()));
                    shift.setNumber(position.getNumber());
                    int code = Integer.parseInt("10" + i);
                    Date timestart;
                    Date timeend;

                    if (i == 0 || i % 2 == 0) {
                        try {
                            timestart = new SimpleDateFormat("hh:mm").parse("07:30");
                            timeend = new SimpleDateFormat("hh:mm").parse("11:30");

                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        try {
                            timestart = new SimpleDateFormat("hh:mm").parse("13:00");
                            timeend = new SimpleDateFormat("hh:mm").parse("17:00");

                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }

                    }


                    shift.setShiftcode(code);
                    shift.setIsOT(false);
                    shift.setTimestart(timestart);
                    shift.setTimeend(timeend);

                    shiftServices.Create(shift);
//                JsonServices.dd("nhan", response);
                }


            }

            i += 1;
        }

        session.removeAttribute("TimelineName");
        session.removeAttribute("TimelineStartDay");
        session.removeAttribute("TimelineEndDay");
        String NumberofPosition = session.getAttribute("NumberofPosition").toString();
        String numberdayoff = session.getAttribute("numberdayoff").toString();


        if (Integer.parseInt(numberdayoff) > 0) {
            for (i = 1; i <= Integer.parseInt(numberdayoff); i++) {

                session.removeAttribute("dayoff" + i);
            }

        }


        for (i = 1; i <= Integer.parseInt(NumberofPosition); i++) {
            if (session.getAttribute("check" + i) != null || session.getAttribute("check" + i) != "") {
                session.removeAttribute("check" + i);
                session.removeAttribute("value" + i);
            }

        }


        String redirectUrl = "/timeline/index";
        return "redirect:" + redirectUrl;
    }


    @RequestMapping(value = {RouteWeb.TimelineCheckStartdayURL}, method = RequestMethod.POST)
    public String CheckStartDay(Model model, HttpServletRequest request, HttpServletResponse response) {

        String data = request.getParameter("timelinedtartday").toString();

        Date TimelineStartDayParse;

        try {
            TimelineStartDayParse = new SimpleDateFormat("yyyy-MM-dd").parse(data);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if (timelineServices.countStartDay(TimelineStartDayParse) > 0) {
            JsonServices.dd("0", response);

        } else {

            JsonServices.dd("1", response);

        }


        return "timeline/confirm";
    }

    @RequestMapping(value = {RouteWeb.TimelineCheckEnddayURL}, method = RequestMethod.POST)
    public String CheckEndDay(Model model, HttpServletRequest request, HttpServletResponse response) {

        String data = request.getParameter("timelineendday").toString();

        Date TimelineEndDayParse;

        try {
            TimelineEndDayParse = new SimpleDateFormat("yyyy-MM-dd").parse(data);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if (timelineServices.countEndDay(TimelineEndDayParse) > 0) {
            JsonServices.dd("0", response);

        } else {

            JsonServices.dd("1", response);

        }


        return "timeline/confirm";
    }


    @RequestMapping(value = {RouteWeb.TimelineDeleteURL}, method = RequestMethod.GET)
    public String DeleteTimeLine(Model model, HttpServletRequest request, HttpServletResponse response) {

        String id = request.getParameter("id").toString();

        timelineServices.Delete(Integer.parseInt(id));


        String redirectUrl = "/timeline/index";
        return "redirect:" + redirectUrl;
    }

    @RequestMapping(value = {RouteWeb.TimelineEditNameURL}, method = RequestMethod.POST)
    public String TimelineEditName(Model model, HttpServletRequest request, HttpServletResponse response) {

        String id = request.getParameter("id").toString();

        String Name = request.getParameter("TimelineName").toString();

        Timeline timeline = timelineServices.FindOne(Integer.parseInt(id));
        timeline.setTimename(Name);

        timelineServices.Edit(timeline);


        String redirectUrl = "/timeline/index";
        return "redirect:" + redirectUrl;
    }

    @RequestMapping(value = {RouteWeb.TimelineEditTimelineURL}, method = RequestMethod.GET)
    public String TimelineGetEdit(Model model, HttpServletRequest request, HttpServletResponse response) {

        String idTimelineStr = request.getParameter("id").toString();

        int idTimeline = Integer.parseInt(idTimelineStr);

        List<Shift> ListShifts = shiftServices.FindByIDTimeLine(idTimeline);
        Timeline timeline = timelineServices.FindOne(idTimeline);   //

//        JsonServices.dd(timeline.getTimename(), response);

        List<ShiftJS> listShift = new ArrayList<>();


        List<Position> Listposition = positionServices.findAll();


        List<PositionJS> ListpositionJS = new ArrayList<>();


        for (Position item : Listposition) {
            PositionJS positionJS = new PositionJS();
            positionJS.setId(item.getId());
            positionJS.setPositionname(item.getPositionname());
            positionJS.setSalarydefault(item.getSalarydefault());
            positionJS.setIsCheck(false);
            positionJS.setNumber(0);

            ListpositionJS.add(positionJS);
        }

//        for (int k = 0; k < ListpositionJS.size(); k++) {
//
//            if (k == 2) {
//
//                String str = "id2: " + ListpositionJS.get(k).getId() + " || number: " + ListpositionJS.get(k).getNumber() + "|| name: " + ListpositionJS.get(k).getPositionname() + "|| Salary: " + ListpositionJS.get(k).getSalarydefault();
//
//
//                JsonServices.dd(str, response);
//
//
//            }
//
//        }
//

        int j = 0;

        for (int i = 2; i <= 7; i++) {

            ShiftJS shiftJS = new ShiftJS();

            shiftJS.setName("Ca sáng (7:30 - 11:30)");
            shiftJS.setDate("");

            listShift.add(shiftJS);

            ShiftJS shiftJS1 = new ShiftJS();

            shiftJS1.setName("Ca chiều (13:00 - 17:00)");
            shiftJS1.setDate("");

            listShift.add(shiftJS1);


        }


        for (int i = 0; i < 12; i++) {

            List<Shift> lShift = new ArrayList<>();


            for (Shift item : ListShifts
            ) {
                Shift shift1222 = new Shift();

                if (item.getShiftcode() < 1000) {

                    int codediv = item.getShiftcode() % 10;

                    if (codediv == i) {

                        shift1222.setIdPosition(item.getIdPosition());
                        shift1222.setNumber(item.getNumber());
                        lShift.add(shift1222);
                    }


                } else if (item.getShiftcode() >= 1000 && item.getShiftcode() < 2000) {
                    int codediv = item.getShiftcode() % 100;

                    if (codediv == i) {
                        shift1222.setIdPosition(item.getIdPosition());
                        shift1222.setNumber(item.getNumber());
                        lShift.add(shift1222);
                    }

                }


            }
            List<PositionJS> liListpositionJS = new ArrayList<>();
            liListpositionJS = ListpositionJS;

            for (int k = 0; k < ListpositionJS.size(); k++) {

                for (int l = 0; l < lShift.size(); l++) {
                    if (ListpositionJS.get(k).getId() == lShift.get(l).getIdPosition().getId()) {
                        PositionJS positionJS1 = ListpositionJS.get(k);
                        positionJS1.setNumber(lShift.get(l).getNumber());
                        positionJS1.setIsCheck(true);
                        liListpositionJS.set(k, positionJS1);

                    }

                }


            }


            ShiftJS shift12 = listShift.get(i);
            shift12.position.addAll(liListpositionJS);


            listShift.set(i, shift12);
//            JsonServices.dd(lShift.get(1).getIdPosition().getId(), response);

        }


        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String Data = "";
        try {

            Data = mapper.writeValueAsString(listShift);


        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        JsonServices.dd(Data, response);

//        JsonServices.dd("nhan122", response);
        return "redirect:";
    }


}
