package fpt.aptech.KHR.Controller;

import fpt.aptech.KHR.Entities.Position;
import fpt.aptech.KHR.Entities.Timeline;
import fpt.aptech.KHR.ImpServices.NotificationService;
import fpt.aptech.KHR.ImpServices.PositionServices;
import fpt.aptech.KHR.ImpServices.TimelineServices;
import fpt.aptech.KHR.Routes.RouteWeb;
import fpt.aptech.KHR.Services.ITimelineServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class TimelineController {

    @Autowired
    TimelineServices timelineServices;

    @Autowired
    PositionServices positionServices;

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


        model.addAttribute("check", check);

        model.addAttribute("list", list);
        return "timeline/index";
    }


    @RequestMapping(value = {RouteWeb.TimelineGetCreateURL}, method = RequestMethod.GET)
    public String GetCreate(Model model) {


        List<Position> ListPosition = positionServices.findAll();

        model.addAttribute("ListPosition", ListPosition);

        return "timeline/create";
    }


}
