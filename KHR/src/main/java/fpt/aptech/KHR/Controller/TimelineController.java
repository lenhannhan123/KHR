package fpt.aptech.KHR.Controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import fpt.aptech.KHR.Entities.*;
import fpt.aptech.KHR.ImpServices.*;
import fpt.aptech.KHR.Reponsitory.AccountPositionRepository;
import fpt.aptech.KHR.Routes.RouteAPI;
import fpt.aptech.KHR.Routes.RouteWeb;
import fpt.aptech.KHR.Services.ITimelineServices;
import org.json.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
import java.time.LocalDate;
import java.util.*;

@Controller
@CrossOrigin(maxAge = 3600)
public class TimelineController {

    @Autowired
    TimelineServices timelineServices;

    @Autowired
    PositionServices positionServices;

    @Autowired
    ShiftServices shiftServices;

    @Autowired
    UserTimelineServices userTimelineServices;

    @Autowired
    AccountService accountService;

    @Autowired
    private AccountPositionRepository accountPositionServices;


    @RequestMapping(value = {RouteWeb.TimelineIndexURL}, method = RequestMethod.GET)
    public String IndexTimeline(Model model, HttpServletRequest request, HttpServletResponse response) {


        List<Timeline> list = timelineServices.findAll();

        HttpSession session = request.getSession();
        int IdStore = Integer.parseInt(session.getAttribute("IdStore").toString());

//        JsonServices.dd(IdStore, response);


        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIdStore().getId() == IdStore) {
            } else {
                list.remove(list.get(i));
                i -= 1;

            }
        }


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
        return "admin/timeline/index";
    }


    @RequestMapping(value = {RouteWeb.TimelineGetCreateURL}, method = RequestMethod.GET)
    public String GetCreate(Model model) {


        List<Position> ListPosition = positionServices.findAll();

        model.addAttribute("ListPosition", ListPosition);

        return "admin/timeline/create";
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

        return "admin/timeline/confirm";
    }


    @RequestMapping(value = {RouteWeb.TimelineConfirmURL}, method = RequestMethod.POST)
    public String PostCofirm(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam String data) {

//        JsonServices.dd(data, response);

        List<ShiftJS> ListShiftJS = new ArrayList<>();


        JSONArray jsonArr;
        try {
            jsonArr = new JSONArray(data);


            for (int i = 0; i < jsonArr.length(); i++) {

                ShiftJS shiftJS = new ShiftJS();
                JSONObject jsonObj = null;
                jsonObj = jsonArr.getJSONObject(i);

                shiftJS.setName((String) jsonObj.get("name"));
                shiftJS.setDate((String) jsonObj.get("date"));


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

                shiftJS.position = new ArrayList<>();
                for (PositionJS item : ListpositionJS
                ) {

                    shiftJS.position.add(new PositionJS(item.getId(), item.isIsCheck(), item.getNumber(), item.getPositionname(), item.getSalarydefault()));
                }


//                int kkk = 0;
//                for (PositionJS item : shiftJS.position) {
//
//                    if (kkk == 1) {
//
//                        String str = "id: " + item.getId() + " Position name: " + item.getPositionname() + " Number: " + item.getNumber();
//
//                        JsonServices.dd(str, response);
//                    }
//
//                    kkk += 1;
//
//                }

//                ShiftJS.position.addAll(ListpositionJS);


                ListShiftJS.add(shiftJS);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


//        JsonServices.dd(JsonServices.ParseToJson(ListShiftJS), response);


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

        int IdStore = Integer.parseInt(session.getAttribute("IdStore").toString());


        Timeline timeline = new Timeline();
        timeline.setTimename(TimelineName);
        timeline.setStartdate(TimelineStartDayParse);
        timeline.setEnddate(TimelineEndDayParse);
        timeline.setStatus((short) 0);
        timeline.setIdStore(new Store(IdStore));
        timeline = timelineServices.Create(timeline);


//        JsonServices.dd(timeline.getId(), response);
        int k = 0;
        int i = 0;
        int t = 0;
        int date = 0;
        boolean OTT = false;
        SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        Date tmpDateStart, tmpDateEnd;

//        JsonServices.dd(JsonServices.ParseToJson(ListShiftJS), response);

        for (ShiftJS item : ListShiftJS
        ) {

            long mili = Long.parseLong(JsonServices.ParseToJson(TimelineStartDayParse).toString()) + (86400000 * date);


            long miliend = Long.parseLong(JsonServices.ParseToJson(TimelineStartDayParse).toString()) + (86400000 * date);


//            JsonServices.dd(JsonServices.ParseToJson(TimelineStartDayParse).toString(), response);
//            JsonServices.dd(tmpDateStart.toString(), response);

            t = 0;
            OTT = false;
            for (PositionJS position : item.position
            ) {
                if (position.isIsCheck() == false) {

                    t += 1;
                }

            }

            if (t == item.position.size()) {
                OTT = true;

            }


            for (PositionJS position : item.position
            ) {


                if (position.getNumber() > 0 || OTT == true) {
                    Shift shift = new Shift();

                    shift.setIdTimeline(new Timeline(timeline.getId()));
                    shift.setIdPosition(new Position(position.getId()));
                    shift.setNumber(position.getNumber());
                    int code = Integer.parseInt("10" + i);
                    Date timestart;
                    Date timeend;


                    switch (k) {
                        case 0:

                            timestart = new Date(mili + 21600000);
                            timeend = new Date(miliend + 36000000);


                            break;

                        case 1:

                            timestart = new Date(mili + 36000000);
                            timeend = new Date(miliend + 50400000);


                            break;
                        case 2:

                            timestart = new Date(mili + 50400000);
                            timeend = new Date(miliend + 64800000);

                            break;
                        case 3:

                            timestart = new Date(mili + 64800000);
                            timeend = new Date(miliend + 79200000);


                            break;
                        case 4:

                            timestart = new Date(mili + 79200000);
                            timeend = new Date(miliend + 108000000);


                            break;
                        default:

                            timestart = new Date(mili + 79200001);
                            timeend = new Date(miliend + 86400001);


                            break;
                    }


//                    if (i == 0 || i % 2 == 0) {
//                        try {
//                            timestart = new SimpleDateFormat("hh:mm").parse("07:30");
//                            timeend = new SimpleDateFormat("hh:mm").parse("11:30");
//
//                        } catch (ParseException e) {
//                            throw new RuntimeException(e);
//                        }
//                    } else {
//                        try {
//                            timestart = new SimpleDateFormat("hh:mm").parse("13:00");
//                            timeend = new SimpleDateFormat("hh:mm").parse("17:00");
//
//                        } catch (ParseException e) {
//                            throw new RuntimeException(e);
//                        }
//
//                    }


                    shift.setShiftcode(code);
                    shift.setIsOT(OTT);
                    shift.setTimestart(timestart);
                    shift.setTimeend(timeend);

                    shiftServices.Create(shift);
//                JsonServices.dd("nhan", response);
                }


            }

            if (k == 4) {
                k = -1;
                date += 1;

            }
            k += 1;
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


        return "admin/timeline/confirm";
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
            positionJS.setId_Db(0);

            ListpositionJS.add(positionJS);
        }


        int j = 0;

        for (int i = 2; i <= 8; i++) {

            ShiftJS shiftJS = new ShiftJS();
            shiftJS.setName("Ca sáng (6:00 - 10:00)");
            shiftJS.setDate(String.valueOf(i));
            listShift.add(shiftJS);


            ShiftJS shiftJS1 = new ShiftJS();
            shiftJS1.setName("Ca trưa (10:00 - 14:00)");
            shiftJS1.setDate(String.valueOf(i));
            listShift.add(shiftJS1);


            ShiftJS shiftJS2 = new ShiftJS();
            shiftJS2.setName("Ca chiều (14:00 - 18:00)");
            shiftJS2.setDate(String.valueOf(i));
            listShift.add(shiftJS2);

            ShiftJS shiftJS3 = new ShiftJS();
            shiftJS3.setName("Ca tối (18:00 - 22:00)");
            shiftJS3.setDate(String.valueOf(i));
            listShift.add(shiftJS3);

            ShiftJS shiftJS4 = new ShiftJS();
            shiftJS4.setName("Ca đêm (22:00 - 06:00)");
            shiftJS4.setDate(String.valueOf(i));
            listShift.add(shiftJS4);


        }
//        JsonServices.dd(JsonServices.ParseToJson(listShift));


        for (int i = 0; i < 35; i++) {

            List<Shift> lShift = new ArrayList<>();


            for (Shift item : ListShifts
            ) {
                Shift shift1222 = new Shift();

                if (item.getShiftcode() < 1000) {

                    int codediv = item.getShiftcode() % 10;

                    if (codediv == i) {

                        shift1222.setIdPosition(item.getIdPosition());
                        shift1222.setNumber(item.getNumber());
                        shift1222.setId(item.getId());
                        lShift.add(shift1222);
                    }


                } else if (item.getShiftcode() >= 1000 && item.getShiftcode() < 2000) {
                    int codediv = item.getShiftcode() % 100;

                    if (codediv == i) {
                        shift1222.setIdPosition(item.getIdPosition());
                        shift1222.setNumber(item.getNumber());
                        shift1222.setId(item.getId());
                        lShift.add(shift1222);
                    }

                }


            }

//            ListShifts : Số lượng Position trong ngày i


//            JsonServices.dd(lShift.size(), response);

//            for (int k = 0; k < lShift.size(); k++) {
//
//                if (k == 2 && i == 2) {
//
//                    String str = "id: " + lShift.get(k).getId();
//
//
//                    JsonServices.dd(str, response);
//
//
//                }
//
//            }

//            ListShifts : Số lượng Position trong ngày i
            List<PositionJS> liListpositionJS = new ArrayList<>();
            liListpositionJS.addAll(ListpositionJS);


            for (int k = 0; k < ListpositionJS.size(); k++) {

                for (int l = 0; l < lShift.size(); l++) {
                    if (ListpositionJS.get(k).getId() == lShift.get(l).getIdPosition().getId()) {
                        PositionJS positionJS1 = new PositionJS(ListpositionJS.get(k).getId(), ListpositionJS.get(k).isIsCheck(), ListpositionJS.get(k).getNumber(), ListpositionJS.get(k).getPositionname(), ListpositionJS.get(k).getSalarydefault(), ListpositionJS.get(k).getId_Db());
//                        positionJS1 = ;


                        positionJS1.setNumber(lShift.get(l).getNumber());
                        positionJS1.setIsCheck(true);
                        positionJS1.setId_Db(lShift.get(l).getId());

//                        if (i == 1 ) {
//
//                            String str = "name: " + positionJS1.getPositionname() + " check: " + positionJS1.isIsCheck() + i;
//
//
//                            JsonServices.dd(str, response);
//
//
//                        }


                        liListpositionJS.set(k, positionJS1);


                    }

                }


            }


            ShiftJS shift12 = new ShiftJS();
            shift12 = listShift.get(i);
            listShift.get(i).position = new ArrayList<>();
            shift12.position = new ArrayList<>();


            for (PositionJS ps : liListpositionJS
            ) {
                shift12.position.add(new PositionJS(ps.getId(), ps.isIsCheck(), ps.getNumber(), ps.getPositionname(), ps.getSalarydefault(), ps.getId_Db()));
            }


//            for (int k = 0; k < liListpositionJS.size(); k++) {
//
//                if (k == 3 && i == 4) {
//
//                    String str = "name: " + liListpositionJS.get(k).getPositionname() + " check1 " + liListpositionJS.get(k).isIsCheck();
//
//
//                    JsonServices.dd(str, response);
//
//
//                }
//
//            }


            listShift.set(i, shift12);


        }


        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String Data = "";
        try {

            Data = mapper.writeValueAsString(listShift);


        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        model.addAttribute("data", Data);
        model.addAttribute("TimelineStartDay", timeline.getStartdate());
        model.addAttribute("TimelineEndDay", timeline.getEnddate());
        model.addAttribute("IdTimeLine", timeline.getId());


//        JsonServices.dd("nhan122", response);
        return "admin/timeline/timelinedit";
    }


    @RequestMapping(value = {RouteWeb.TimelineEditTimelineURL}, method = RequestMethod.POST)
    public String TimelinePostEdit(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam String data, @RequestParam int idtimelineform) {
        List<ShiftJS> ListShiftJS = new ArrayList<>();


        JSONArray jsonArr;
        try {
            jsonArr = new JSONArray(data);

            for (int i = 0; i < jsonArr.length(); i++) {
                ShiftJS ShiftJS = new ShiftJS();
                ShiftJS.position = new ArrayList<>();

                JSONObject jsonObj = null;
                jsonObj = jsonArr.getJSONObject(i);

                ShiftJS.setName((String) jsonObj.get("name"));
                ShiftJS.setDate((String) jsonObj.get("date"));


                List<PositionJS> ListpositionJS = new ArrayList<>();
                JSONArray jsonArr1 = (JSONArray) jsonObj.get("position");
//                JsonServices.dd(((JSONArray) jsonObj.get("position")).toString(), response);


                for (int j = 0; j < jsonArr1.length(); j++) {
                    PositionJS positionJS = new PositionJS();

                    JSONObject PositionObj = jsonArr1.getJSONObject(j);

                    positionJS.setId((int) PositionObj.get("id"));
                    positionJS.setIsCheck((boolean) PositionObj.get("isCheck"));
                    positionJS.setNumber((int) PositionObj.get("number"));
                    positionJS.setPositionname((String) PositionObj.get("positionname"));
                    positionJS.setSalarydefault((int) PositionObj.get("salarydefault"));
                    positionJS.setId_Db((int) PositionObj.get("iddb"));

                    ListpositionJS.add(positionJS);
                }


                for (PositionJS item : ListpositionJS
                ) {
                    ShiftJS.position.add(new PositionJS(item.getId(), item.isIsCheck(), item.getNumber(), item.getPositionname(), item.getSalarydefault(), item.getId_Db()));
                }


                ListShiftJS.add(ShiftJS);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        int id_timeline1 = idtimelineform;
        int i = 0;
        int t = 0;
        int k = 0;
        int date = 0;
        boolean OTT = false;

        Timeline tl = timelineServices.FindOne(id_timeline1);

        for (ShiftJS item : ListShiftJS
        ) {
            t = 0;
            OTT = false;
            for (PositionJS position : item.position
            ) {
                if (position.isIsCheck() == false) {

                    t += 1;
                }

            }
            long mili = Long.parseLong(JsonServices.ParseToJson(tl.getStartdate()).toString()) + (86400000 * date);


            long miliend = Long.parseLong(JsonServices.ParseToJson(tl.getStartdate()).toString()) + (86400000 * date);

            if (t == item.position.size()) {
                OTT = true;

            }


            for (PositionJS posjs : item.position
            ) {

                Shift shiftnew = new Shift();
                shiftnew = shiftServices.FindOne(posjs.getId_Db());


                if (shiftnew != null) {

                    if (posjs.getNumber() == 0 && OTT == false) {
                        shiftServices.Delete(posjs.getId_Db());
                    } else {
                        shiftnew.setNumber(posjs.getNumber());
                        shiftServices.Edit(shiftnew);
                    }

                } else {

                    if (posjs.getNumber() > 0 || OTT == true) {


                        Shift shift = new Shift();


                        shift.setIdTimeline(new Timeline(id_timeline1));
                        shift.setIdPosition(new Position(posjs.getId()));
                        shift.setNumber(posjs.getNumber());
                        int code = Integer.parseInt("10" + i);
                        Date timestart;
                        Date timeend;
                        switch (k) {
                            case 0:

                                timestart = new Date(mili + 21600000);
                                timeend = new Date(miliend + 36000000);


                                break;

                            case 1:

                                timestart = new Date(mili + 36000000);
                                timeend = new Date(miliend + 50400000);


                                break;
                            case 2:

                                timestart = new Date(mili + 50400000);
                                timeend = new Date(miliend + 64800000);

                                break;
                            case 3:

                                timestart = new Date(mili + 64800000);
                                timeend = new Date(miliend + 79200000);


                                break;
                            case 4:

                                timestart = new Date(mili + 79200000);
                                timeend = new Date(miliend + 108000000);


                                break;
                            default:

                                timestart = new Date(mili + 79200001);
                                timeend = new Date(miliend + 86400001);


                                break;
                        }
//
//
//                        if (i == 0 || i % 2 == 0) {
//                            try {
//                                timestart = new SimpleDateFormat("hh:mm").parse("07:30");
//                                timeend = new SimpleDateFormat("hh:mm").parse("11:30");
//
//                            } catch (ParseException e) {
//                                throw new RuntimeException(e);
//                            }
//                        } else {
//                            try {
//                                timestart = new SimpleDateFormat("hh:mm").parse("13:00");
//                                timeend = new SimpleDateFormat("hh:mm").parse("17:00");
//
//                            } catch (ParseException e) {
//                                throw new RuntimeException(e);
//                            }
//
//                        }


                        shift.setShiftcode(code);
                        shift.setIsOT(OTT);
                        shift.setTimestart(timestart);
                        shift.setTimeend(timeend);

//                        String iss = "Id Timeline: " + shift.getIdTimeline()
//                                + " Number: " + shift.getNumber()
//                                + " Position: " + shift.getIdPosition()
//                                + " Shiftcode: " + shift.getShiftcode();


//                        JsonServices.dd(iss, response);

                        shiftServices.Create(shift);
//                JsonServices.dd("nhan", response);

                    }


                }

            }

            if (k == 4) {
                k = -1;
                date += 1;

            }
            k += 1;

            i += 1;

        }


//        ObjectMapper mapper = new ObjectMapper();
//        mapper.enable(SerializationFeature.INDENT_OUTPUT);
//        String Data = "";
//        try {
//
//            Data = mapper.writeValueAsString(ListShiftJS);
//
//
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//


//        id_Timeline

        String redirectUrl = "/timeline/index";
        return "redirect:" + redirectUrl;
    }


    @RequestMapping(value = {RouteWeb.TimelineSortURL}, method = RequestMethod.GET)
    public String TimelineSort(Model model, HttpServletRequest request, HttpServletResponse response) {

        String idTimelineStr = request.getParameter("id").toString();

        List<UserTimeline> userTimeline = userTimelineServices.FindIDTimeLine(Integer.parseInt(idTimelineStr));

        if (userTimeline == null) {
            model.addAttribute("Texterror", "Vui lòng cho nhân viên thêm timeline trước khi sắp xếp lịch");
            model.addAttribute("Backlink", "/timeline/index");
            return "errorpage";

        }


        List<Account> account = accountService.findAllUser();

        HttpSession session = request.getSession();
        int Id_Store = Integer.parseInt(session.getAttribute("IdStore").toString());

        for (int i = 0; i < account.size(); i++) {
            if (account.get(i).getIdStore().getId() == Id_Store) {
            } else {
                account.remove(account.get(i));
                i -= 1;

            }
        }

        List<Shift> ListShift = shiftServices.FindByIDTimeLine(Integer.parseInt(idTimelineStr));

        int Number_Position = 0;
        int People_Shift = 0;

        //Số vị trí làm trong tuần
        for (Shift item : ListShift
        ) {
            Number_Position += item.getNumber();
        }

        //Số vị trí của 1 người trong tuần
        People_Shift = Number_Position / account.size();
        int modPeople_Shift = Number_Position % account.size();

        if (modPeople_Shift > 0) {
            People_Shift += 1;
        }


        //Tạo dữ liệu user --> đầu ra UserPropertyListTemplate

        List<JobpriorityModel> UserPropertyListTemplate = new ArrayList<>();

        int number = 0;
        double randomDouble;
        int randomInt;

        for (Account item : account) {

            List<PositionTimelineJs> positionTimelineJs = new ArrayList<>();
            for (int i = 1; i <= 35; i++) {
                PositionTimelineJs positionTimelineJs1 = new PositionTimelineJs();
                positionTimelineJs1.setShift(true);
                positionTimelineJs.add(positionTimelineJs1);
            }

            JobpriorityModel UserProperty = new JobpriorityModel();

            UserProperty.setId_user(item.getMail());
            UserProperty.setUser_name(item.getFullname());
            List<AccountPosition> accountPositions = accountPositionServices.findByEmail(new Account(item.getMail()));
            UserProperty.setAccountPositions(accountPositions);
            List<UserTimeline> userTimelineList = userTimelineServices.UserTimeline(Integer.parseInt(idTimelineStr), item.getMail());
            UserProperty.setshiftList(positionTimelineJs);

            int Shiftcode = 0;

            for (UserTimeline item1 : userTimelineList
            ) {

                Shiftcode = item1.getShiftcode();
                UserProperty.getshiftList().get(Shiftcode - 1).setShift(false);

            }

            int j = 0;

            for (PositionTimelineJs item3 : UserProperty.getshiftList()) {
                if (item3.isShift() == true) {
                    j += 1;
                }
            }


            UserProperty.setNumber_position(UserProperty.getAccountPositions().size());
            UserProperty.setNumber_shift(j);
            UserProperty.setPeople_Shift(People_Shift);
            UserPropertyListTemplate.add(UserProperty);
        }


        // Xử lý tạo dữ list để sắp xếp --> đầu ra shiftOnDayList

        List<ShiftOnDay> shiftOnDayList = new ArrayList<>();

        for (int i = 0; i < 35; i++) {
            number = 0;
            ShiftOnDay shiftOnDay = new ShiftOnDay();
            List<PositionOnDay> positionOnDaysList = new ArrayList<>();
            List<Shift> timelines = shiftServices.findByShiftCode(Integer.parseInt("10" + i), Integer.parseInt(idTimelineStr));

            for (Shift item : timelines) {
                number = 0;
                number = item.getNumber();

                for (int j = 0; j < number; j++) {
                    PositionOnDay positionOnDay = new PositionOnDay();
                    positionOnDay.setPosition_id(item.getIdPosition().getId());
                    positionOnDay.setShift_Code(i);
                    positionOnDaysList.add(positionOnDay);
                }

            }

            shiftOnDay.setPositionOnDays(positionOnDaysList);
            shiftOnDayList.add(shiftOnDay);


        }

        // Tìm số vị trí mà ít người làm được


        List<Position> positionList = positionServices.findAll();
        List<ModelString> ListPosition = new ArrayList<>();
        for (Position item : positionList) {
            ModelString stringdata = new ModelString();
            stringdata.setData1(String.valueOf(item.getId()));
            ListPosition.add(stringdata);
        }


        int count = 0;
        int min = 0, max = 0;
        for (int i = 0; i < ListPosition.size(); i++) {
            count = 0;
            int id_Pos = Integer.parseInt(ListPosition.get(i).getData1());
//            JsonServices.dd(id_Pos, response);

            for (JobpriorityModel item : UserPropertyListTemplate) {
                for (AccountPosition item2 : item.getAccountPositions()) {
                    if (item2.getIdPosition().getId() == id_Pos) {
                        count += 1;
                    }

                }
            }

            ListPosition.get(i).setData2(String.valueOf(count));
        }

        // Xóa vị trí không ai làm được
        for (int i = 0; i < ListPosition.size(); i++) {
            if (Integer.parseInt(ListPosition.get(i).getData2()) == 0) {
                ListPosition.remove(ListPosition.get(i));
                i = -1;
            }
        }
        // Sắp xếp
        ModelString String1 = new ModelString();
        for (int i = 0; i < ListPosition.size(); i++) {
            for (int j = i + 1; j < ListPosition.size(); j++) {
                if (Integer.parseInt(ListPosition.get(i).getData2()) > Integer.parseInt(ListPosition.get(j).getData2())) {
                    String1.setData1(ListPosition.get(i).getData1());
                    String1.setData2(ListPosition.get(i).getData2());

                    ListPosition.get(i).setData1(ListPosition.get(j).getData1());
                    ListPosition.get(i).setData2(ListPosition.get(j).getData2());

                    ListPosition.get(j).setData1(String1.getData1());
                    ListPosition.get(j).setData2(String1.getData2());
                }
            }
        }

        //Sắp xếp

        Boolean check = true;

        for (ModelString item : ListPosition) {

            for (int i = 0; i < shiftOnDayList.size(); i++) {


//                for (PositionOnDay item2 : shiftOnDayList.get(i).getPositionOnDays()) {
                for (int k = 0; k < shiftOnDayList.get(i).getPositionOnDays().size(); k++) {


                    if (Integer.parseInt(item.getData1()) == shiftOnDayList.get(i).getPositionOnDays().get(k).getPosition_id()) {

                        List<JobpriorityModel> UserPropertyList = new ArrayList<>();
                        UserPropertyList.addAll(UserPropertyListTemplate);

                        //Xoa People_shift 0<=
                        for (int j = 0; j < UserPropertyList.size(); j++) {

                            if (UserPropertyList.get(j).getPeople_Shift() <= 0) {
                                UserPropertyList.remove(UserPropertyList.get(j));
                                j -= 1;
                            }

                        }
                        if (UserPropertyList.size() == 1) {


                            shiftOnDayList.get(i).getPositionOnDays().get(k).setMail(UserPropertyList.get(0).getId_user());
                            for (int j = 0; j < UserPropertyListTemplate.size(); j++) {
                                if (UserPropertyListTemplate.get(j).getId_user().equals(UserPropertyList.get(0).getId_user())) {
                                    UserPropertyListTemplate.get(j).setPeople_Shift(UserPropertyListTemplate.get(j).getPeople_Shift() - 1);

                                }

                            }
                            break;
                        }

                        //Tìm user làm dc trong 1 ca và có vị trí đó -> đầu ra UserPropertyList

                        for (int j = 0; j < UserPropertyList.size(); j++) {


                            if (UserPropertyList.get(j).getshiftList().get(i).isShift() == false) {
                                UserPropertyList.remove(UserPropertyList.get(j));
                                j -= 1;
                            }

                        }


                        for (int j = 0; j < UserPropertyList.size(); j++) {
                            check = true;
                            for (AccountPosition accpos : UserPropertyList.get(j).getAccountPositions()) {
                                if (accpos.getIdPosition().getId() == Integer.parseInt(item.getData1())) {
                                    check = false;
                                }
                            }

                            if (check == true) {
                                UserPropertyList.remove(UserPropertyList.get(j));

                            }
                        }

                        //Có vị trí khác không

                        boolean checkmail = true;

                        for (int j = 0; j < UserPropertyList.size(); j++) {

                            checkmail = true;
                            for (PositionOnDay posonday : shiftOnDayList.get(i).getPositionOnDays()) {
                                if (UserPropertyList.get(j).getId_user().equals(posonday.getMail())) {
                                    checkmail = false;
                                }
                            }

                            if (checkmail == false) {

                                UserPropertyList.remove(UserPropertyList.get(j));
                                j -= 1;
                            }


                        }


                        if (UserPropertyList.size() == 1) {


                            shiftOnDayList.get(i).getPositionOnDays().get(k).setMail(UserPropertyList.get(0).getId_user());
                            for (int j = 0; j < UserPropertyListTemplate.size(); j++) {
                                if (UserPropertyListTemplate.get(j).getId_user().equals(UserPropertyList.get(0).getId_user())) {
                                    UserPropertyListTemplate.get(j).setPeople_Shift(UserPropertyListTemplate.get(j).getPeople_Shift() - 1);

                                }

                            }
                            break;
                        } else if (UserPropertyList.size() == 0) {
                            break;
                        }

                        //Xóa số lần làm thấp nhất

                        min = 0;
                        max = 0;
                        min = UserPropertyList.get(0).getPeople_Shift();
                        max = UserPropertyList.get(0).getPeople_Shift();

                        for (int j = 1; j < UserPropertyList.size(); j++) {
                            if (UserPropertyList.get(j).getPeople_Shift() > max) {
                                max = UserPropertyList.get(j).getPeople_Shift();
                            }
                        }
                        for (int j = 1; j < UserPropertyList.size(); j++) {
                            if (UserPropertyList.get(j).getPeople_Shift() < min) {
                                min = UserPropertyList.get(j).getPeople_Shift();
                            }
                        }

                        if (min > max) {
                            for (int j = 1; j < UserPropertyList.size(); j++) {
                                if (UserPropertyList.get(j).getPeople_Shift() == min) {
                                    UserPropertyList.remove(UserPropertyList.get(j));
                                }
                            }
                        }
                        if (UserPropertyList.size() == 1) {


                            shiftOnDayList.get(i).getPositionOnDays().get(k).setMail(UserPropertyList.get(0).getId_user());
                            for (int j = 0; j < UserPropertyListTemplate.size(); j++) {
                                if (UserPropertyListTemplate.get(j).getId_user().equals(UserPropertyList.get(0).getId_user())) {
                                    UserPropertyListTemplate.get(j).setPeople_Shift(UserPropertyListTemplate.get(j).getPeople_Shift() - 1);

                                }

                            }
                            break;
                        }

                        //Xóa số ca làm nhiều nhất
                        max = UserPropertyList.get(0).getNumber_shift();
                        for (int j = 1; j < UserPropertyList.size(); j++) {
                            if (UserPropertyList.get(j).getNumber_shift() > max) {
                                max = UserPropertyList.get(j).getNumber_shift();
                            }
                        }
                        for (int j = 1; j < UserPropertyList.size(); j++) {
                            if (UserPropertyList.get(j).getNumber_shift() == max) {
                                UserPropertyList.remove(UserPropertyList.get(j));
                            }
                        }

                        if (UserPropertyList.size() == 1) {


                            shiftOnDayList.get(i).getPositionOnDays().get(k).setMail(UserPropertyList.get(0).getId_user());
                            for (int j = 0; j < UserPropertyListTemplate.size(); j++) {
                                if (UserPropertyListTemplate.get(j).getId_user().equals(UserPropertyList.get(0).getId_user())) {
                                    UserPropertyListTemplate.get(j).setPeople_Shift(UserPropertyListTemplate.get(j).getPeople_Shift() - 1);

                                }

                            }
                            break;
                        }
                        //Xóa số ca làm nhiều nhất
                        max = UserPropertyList.get(0).getNumber_position();
                        for (int j = 1; j < UserPropertyList.size(); j++) {
                            if (UserPropertyList.get(j).getNumber_position() > max) {
                                max = UserPropertyList.get(j).getNumber_position();
                            }
                        }
                        for (int j = 1; j < UserPropertyList.size(); j++) {
                            if (UserPropertyList.get(j).getNumber_position() == max) {
                                UserPropertyList.remove(UserPropertyList.get(j));
                            }
                        }

                        if (UserPropertyList.size() > 1) {

                            while (true) {
                                randomDouble = Math.random();
                                randomDouble = randomDouble * 10 + 1;
                                randomInt = (int) randomDouble;

                                if (randomInt >= 0 && randomInt < UserPropertyList.size()) {
                                    break;
                                }
                            }


                            shiftOnDayList.get(i).getPositionOnDays().get(k).setMail(UserPropertyList.get(randomInt).getId_user());

                            for (int j = 0; j < UserPropertyListTemplate.size(); j++) {
                                if (UserPropertyListTemplate.get(j).getId_user().equals(UserPropertyList.get(randomInt).getId_user())) {
                                    UserPropertyListTemplate.get(j).setPeople_Shift(UserPropertyListTemplate.get(j).getPeople_Shift() - 1);

                                }

                            }


//                            UserPropertyList.get(randomInt)

                        } else {
                            shiftOnDayList.get(i).getPositionOnDays().get(k).setMail(UserPropertyList.get(0).getId_user());
                            for (int j = 0; j < UserPropertyListTemplate.size(); j++) {
                                if (UserPropertyListTemplate.get(j).getId_user().equals(UserPropertyList.get(0).getId_user())) {
                                    UserPropertyListTemplate.get(j).setPeople_Shift(UserPropertyListTemplate.get(j).getPeople_Shift() - 1);

                                }

                            }
                        }


                    }
                }


            }


        }

        for (int i = 0; i < shiftOnDayList.size(); i++) {
            for (int k = 0; k < shiftOnDayList.get(i).getPositionOnDays().size(); k++) {
                if (shiftOnDayList.get(i).getPositionOnDays().get(k).getMail() == null) {
                    List<JobpriorityModel> UserPropertyList = new ArrayList<>();
                    UserPropertyList.addAll(UserPropertyListTemplate);

                    //Xoa People_shift 0<=
                    for (int j = 0; j < UserPropertyList.size(); j++) {

                        if (UserPropertyList.get(j).getPeople_Shift() <= 0) {
                            UserPropertyList.remove(UserPropertyList.get(j));
                            j -= 1;
                        }

                    }
                    if (UserPropertyList.size() == 1) {


                        shiftOnDayList.get(i).getPositionOnDays().get(k).setMail(UserPropertyList.get(0).getId_user());
                        for (int j = 0; j < UserPropertyListTemplate.size(); j++) {
                            if (UserPropertyListTemplate.get(j).getId_user().equals(UserPropertyList.get(0).getId_user())) {
                                UserPropertyListTemplate.get(j).setPeople_Shift(UserPropertyListTemplate.get(j).getPeople_Shift() - 1);

                            }

                        }
                        break;
                    }

                    //Tìm user làm dc trong 1 ca và có vị trí đó -> đầu ra UserPropertyList

                    for (int j = 0; j < UserPropertyList.size(); j++) {


                        if (UserPropertyList.get(j).getshiftList().get(i).isShift() == false) {
                            UserPropertyList.remove(UserPropertyList.get(j));
                            j -= 1;
                        }

                    }


                    for (int j = 0; j < UserPropertyList.size(); j++) {
                        check = true;
                        for (AccountPosition accpos : UserPropertyList.get(j).getAccountPositions()) {
                            if (accpos.getIdPosition().getId() == shiftOnDayList.get(i).getPositionOnDays().get(k).getPosition_id()) {
                                check = false;
                            }
                        }

                        if (check == true) {
                            UserPropertyList.remove(UserPropertyList.get(j));

                        }
                    }

                    //Có vị trí khác không

                    boolean checkmail = true;

                    for (int j = 0; j < UserPropertyList.size(); j++) {

                        checkmail = true;
                        for (PositionOnDay posonday : shiftOnDayList.get(i).getPositionOnDays()) {
                            if (UserPropertyList.get(j).getId_user().equals(posonday.getMail())) {
                                checkmail = false;
                            }
                        }

                        if (checkmail == false) {

                            UserPropertyList.remove(UserPropertyList.get(j));
                            j -= 1;
                        }


                    }


                    if (UserPropertyList.size() == 1) {


                        shiftOnDayList.get(i).getPositionOnDays().get(k).setMail(UserPropertyList.get(0).getId_user());
                        for (int j = 0; j < UserPropertyListTemplate.size(); j++) {
                            if (UserPropertyListTemplate.get(j).getId_user().equals(UserPropertyList.get(0).getId_user())) {
                                UserPropertyListTemplate.get(j).setPeople_Shift(UserPropertyListTemplate.get(j).getPeople_Shift() - 1);

                            }

                        }
                        break;
                    } else if (UserPropertyList.size() > 1) {
                        while (true) {
                            randomDouble = Math.random();
                            randomDouble = randomDouble * 10 + 1;
                            randomInt = (int) randomDouble;

                            if (randomInt >= 0 && randomInt < UserPropertyList.size()) {
                                break;
                            }
                        }


                        shiftOnDayList.get(i).getPositionOnDays().get(k).setMail(UserPropertyList.get(randomInt).getId_user());

                        for (int j = 0; j < UserPropertyListTemplate.size(); j++) {
                            if (UserPropertyListTemplate.get(j).getId_user().equals(UserPropertyList.get(randomInt).getId_user())) {
                                UserPropertyListTemplate.get(j).setPeople_Shift(UserPropertyListTemplate.get(j).getPeople_Shift() - 1);

                            }

                        }

                    }

                }

            }

        }

        for (int i = 0; i < shiftOnDayList.size(); i++) {
            for (int k = 0; k < shiftOnDayList.get(i).getPositionOnDays().size(); k++) {
                if (shiftOnDayList.get(i).getPositionOnDays().get(k).getMail() == null) {
                    List<JobpriorityModel> UserPropertyList = new ArrayList<>();
                    UserPropertyList.addAll(UserPropertyListTemplate);


                    //Tìm user làm dc trong 1 ca và có vị trí đó -> đầu ra UserPropertyList

                    for (int j = 0; j < UserPropertyList.size(); j++) {


                        if (UserPropertyList.get(j).getshiftList().get(i).isShift() == false) {
                            UserPropertyList.remove(UserPropertyList.get(j));
                            j -= 1;
                        }

                    }


                    for (int j = 0; j < UserPropertyList.size(); j++) {
                        check = true;
                        for (AccountPosition accpos : UserPropertyList.get(j).getAccountPositions()) {
                            if (accpos.getIdPosition().getId() == shiftOnDayList.get(i).getPositionOnDays().get(k).getPosition_id()) {
                                check = false;
                            }
                        }

                        if (check == true) {
                            UserPropertyList.remove(UserPropertyList.get(j));

                        }
                    }

                    //Có vị trí khác không

                    boolean checkmail = true;

                    for (int j = 0; j < UserPropertyList.size(); j++) {

                        checkmail = true;
                        for (PositionOnDay posonday : shiftOnDayList.get(i).getPositionOnDays()) {
                            if (UserPropertyList.get(j).getId_user().equals(posonday.getMail())) {
                                checkmail = false;
                            }
                        }

                        if (checkmail == false) {

                            UserPropertyList.remove(UserPropertyList.get(j));
                            j -= 1;
                        }


                    }


                    if (UserPropertyList.size() == 1) {


                        shiftOnDayList.get(i).getPositionOnDays().get(k).setMail(UserPropertyList.get(0).getId_user());
                        for (int j = 0; j < UserPropertyListTemplate.size(); j++) {
                            if (UserPropertyListTemplate.get(j).getId_user().equals(UserPropertyList.get(0).getId_user())) {
                                UserPropertyListTemplate.get(j).setPeople_Shift(UserPropertyListTemplate.get(j).getPeople_Shift() - 1);

                            }

                        }
                        break;
                    } else if (UserPropertyList.size() > 1) {
                        while (true) {
                            randomDouble = Math.random();
                            randomDouble = randomDouble * 10 + 1;
                            randomInt = (int) randomDouble;

                            if (randomInt >= 0 && randomInt < UserPropertyList.size()) {
                                break;
                            }
                        }


                        shiftOnDayList.get(i).getPositionOnDays().get(k).setMail(UserPropertyList.get(randomInt).getId_user());

                        for (int j = 0; j < UserPropertyListTemplate.size(); j++) {
                            if (UserPropertyListTemplate.get(j).getId_user().equals(UserPropertyList.get(randomInt).getId_user())) {
                                UserPropertyListTemplate.get(j).setPeople_Shift(UserPropertyListTemplate.get(j).getPeople_Shift() - 1);

                            }

                        }

                    }

                }

            }

        }

//        JsonServices.dd(JsonServices.ParseToJson(UserPropertyListTemplate), response);
//
//
//        JsonServices.dd(People_Shift, response);
        JsonServices.dd(JsonServices.ParseToJson(shiftOnDayList), response);


        return "errorpage";
    }

    @RequestMapping(value = {RouteWeb.TimelineUsertURL}, method = RequestMethod.GET)
    public String TimelineUser(Model model, HttpServletRequest request, HttpServletResponse response) {
        String idTimelineStr = request.getParameter("id").toString();

        List<Account> account = accountService.findAllUser();
        List<UserTimelineJS> userTimelineJS = new ArrayList<>();

        HttpSession session = request.getSession();
//        JsonServices.dd(userTimelineServices.CheckUser(756, "user1@gmail.com"), response);
        int Id_Store = Integer.parseInt(session.getAttribute("IdStore").toString());

        for (int i = 0; i < account.size(); i++) {
            if (account.get(i).getIdStore().getId() == Id_Store) {
            } else {
                account.remove(account.get(i));
                i -= 1;

            }
        }


        for (Account item : account
        ) {

            userTimelineJS.add(new UserTimelineJS(
                            item.getMail(),
                            item.getFullname(),
                            userTimelineServices.CheckUser(Integer.parseInt(idTimelineStr), item.getMail())
                    )
            );

        }


        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String Data = "";
        try {

            Data = mapper.writeValueAsString(userTimelineJS);


        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Timeline timeline = timelineServices.FindOne(Integer.parseInt(idTimelineStr));

        if (timeline.getStatus() == 0) {
            model.addAttribute("status", false);
        } else {
            model.addAttribute("status", true);

        }


        model.addAttribute("data", Data);
        model.addAttribute("idTimeline", idTimelineStr);


        return "admin/timeline/usertimelineindex";
    }

    @RequestMapping(value = {RouteWeb.TimelineChangeStatusURL}, method = RequestMethod.POST)
    public String TimelineChangeStatus(Model model, HttpServletRequest request, HttpServletResponse response) {

        String idTimelineStr = request.getParameter("id").toString();

        Timeline timeline = timelineServices.FindOne(Integer.parseInt(idTimelineStr));

        Short Status = timeline.getStatus();

        Short changeSatuss = 0;

        if (Status == 0) {

            changeSatuss = 1;
        } else {
            changeSatuss = 0;
        }
        timeline.setStatus(changeSatuss);

        timelineServices.Edit(timeline);

        JsonServices.dd(changeSatuss, response);

        return "errorpage";
    }


    @RequestMapping(value = {RouteWeb.TimelineReloadTimeURL}, method = RequestMethod.POST)
    public String TimelineUserReload(Model model, HttpServletRequest request, HttpServletResponse response) {


        String idTimelineStr = request.getParameter("id").toString();

        List<Account> account = accountService.findAllUser();
        List<UserTimelineJS> userTimelineJS = new ArrayList<>();

        HttpSession session = request.getSession();
//        JsonServices.dd(userTimelineServices.CheckUser(756, "user1@gmail.com"), response);
        int Id_Store = Integer.parseInt(session.getAttribute("IdStore").toString());

        for (int i = 0; i < account.size(); i++) {
            if (account.get(i).getIdStore().getId() == Id_Store) {
            } else {
                account.remove(account.get(i));
                i -= 1;

            }
        }


        for (Account item : account
        ) {

            userTimelineJS.add(new UserTimelineJS(
                            item.getMail(),
                            item.getFullname(),
                            userTimelineServices.CheckUser(Integer.parseInt(idTimelineStr), item.getMail())
                    )
            );

        }


        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String Data = "";
        try {

            Data = mapper.writeValueAsString(userTimelineJS);


        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Timeline timeline = timelineServices.FindOne(Integer.parseInt(idTimelineStr));


        JsonServices.dd(Data, response);


        return "admin/timeline/usertimelineindex";
    }


    @RequestMapping(value = {RouteWeb.TimelineDetailsURL}, method = RequestMethod.GET)
    public String DetailTimelineUser(Model model, HttpServletRequest request, HttpServletResponse response) {

        String mail = request.getParameter("mail");
        int idTimeline = Integer.parseInt(request.getParameter("id"));

        List<UserTimeline> timelines = userTimelineServices.UserTimeline(idTimeline, mail);

        boolean[] usertimeline = new boolean[36];

        for (int i = 1; i <= 35; i++) {
            usertimeline[i] = false;
        }

        for (int i = 1; i <= 36; i++) {

            for (UserTimeline item : timelines
            ) {

                if (item.getShiftcode() == i) {

                    usertimeline[i] = true;
                }

            }

        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String Data = "";
        try {

            Data = mapper.writeValueAsString(usertimeline);


        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        Account account = accountService.findByMail(mail);

        Timeline timeline = timelineServices.FindOne(idTimeline);

        model.addAttribute("startday", timeline.getStartdate());

        model.addAttribute("account", account);

        model.addAttribute("data", Data);

        return "admin/timeline/usertimelinedetail";
    }


    //    API

    @RequestMapping(value = {RouteAPI.CheckAccountStatusAPI}, method = RequestMethod.POST)
    public void AccountStatusAPI(Model model, HttpServletRequest request, HttpServletResponse response) {


        String mail = request.getParameter("mail").toString();
        int idTimeline = Integer.parseInt(request.getParameter("idTimeline").toString());


        Account account = accountService.findByMail(mail);


        if (account == null) {
            JsonServices.dd("Tài khoản không tồn tại", response);
        }

        Timeline timeline = timelineServices.FindOne(idTimeline);

        if (timeline == null) {
            JsonServices.dd("ID Timeline không tồn tại", response);
        }

        if (timeline.getStatus() == 0) {

            JsonServices.dd("Quản trị viên chưa mở điểm danh", response);
        }

        boolean check = userTimelineServices.CheckUser(idTimeline, mail);

        if (check == true) {
            JsonServices.dd("Đã điểm danh rồi", response);
        }


        JsonServices.dd("Kết nối thành công!", response);


//        return new ResponseEntity<Object>(list, HttpStatus.OK);
    }


    @RequestMapping(value = {RouteAPI.TimelineListAPI}, method = RequestMethod.GET)
    public ResponseEntity<Object> IndexTimelineAPI(Model model, HttpServletRequest request, HttpServletResponse response) {
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


        return new ResponseEntity<Object>(list, HttpStatus.OK);
    }


    @RequestMapping(value = {RouteAPI.CreateUserAPI}, method = RequestMethod.POST)
    public void CreateTimelineUser(Model model, HttpServletRequest request, HttpServletResponse response) {
        String mail = request.getParameter("mail");
        int idTimeline = Integer.parseInt(request.getParameter("idTimeline"));


        Boolean[] data = new Boolean[36];

        for (int i = 1; i <= 35; i++) {

            data[i] = Boolean.parseBoolean(request.getParameter("data" + i));

        }

        boolean check = userTimelineServices.CheckUser(idTimeline, mail);

        if (check == true) {

            List<UserTimeline> userTimeline = userTimelineServices.UserTimeline(idTimeline, mail);

            for (UserTimeline item : userTimeline) {
                for (int i = 1; i <= 35; i++) {
                    if (item.getShiftcode() == i) {
                        if (data[i] == false) {
                            userTimelineServices.Delete(item.getId());
                        }

                    }
                }
            }

            Boolean check1 = true;
            for (int i = 1; i <= 35; i++) {
                check1 = true;
                for (UserTimeline item : userTimeline) {

                    if (item.getShiftcode() == i) {
                        check1 = false;
                    }

                }

                if (check1 == true && data[i] == true) {

                    UserTimeline userTimeline1 = new UserTimeline();

                    userTimeline1.setIdTimeline(new Timeline(idTimeline));
                    userTimeline1.setShiftcode((short) i);
                    userTimeline1.setMail(new Account(mail));
                    userTimelineServices.Create(userTimeline1);
                }


            }


        } else {
            for (int i = 1; i <= 35; i++) {


                if (data[i] == true) {

                    UserTimeline userTimeline = new UserTimeline();

                    userTimeline.setIdTimeline(new Timeline(idTimeline));
                    userTimeline.setShiftcode((short) i);
                    userTimeline.setMail(new Account(mail));
                    userTimelineServices.Create(userTimeline);

                }

            }
        }

        List<String> str = new ArrayList<>();
        str.add("Thêm thành công");

        JsonServices.dd(JsonServices.ParseToJson(str), response);


//        return new ResponseEntity<Object>(list, HttpStatus.OK);
    }


}
