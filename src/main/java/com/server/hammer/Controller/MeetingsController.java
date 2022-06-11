package com.server.hammer.Controller;


import com.server.hammer.Entity.Meeting;
import com.server.hammer.Service.MeetingsService;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@CrossOrigin
@Slf4j
@RestController
public class MeetingsController {
    @Autowired
    MeetingsService meetingsService;

    @GetMapping("/getClass")
    @ResponseBody
    public List<JSONObject> findMidByTid(@RequestParam("uid") String tid){
        List<JSONObject> res=new ArrayList<>() ;
        List<Meeting> meeting=meetingsService.findMeetingsByTeacher(tid);
        for (Meeting meeting1 : meeting){
            JSONObject  obj=new JSONObject();
            obj.put("name",meeting1.getName());
            obj.put("mid",meeting1.getMid());
            obj.put("weekday",meeting1.getWeekday());
            obj.put("time",meeting1.getTime());
            log.info(obj.toString());
            res.add(obj);
        }
        return res;
    }

}
