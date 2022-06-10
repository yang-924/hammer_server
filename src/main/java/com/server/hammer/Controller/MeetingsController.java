package com.server.hammer.Controller;


import com.server.hammer.Entity.Meeting;
import com.server.hammer.Service.MeetingsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/meetings")
public class MeetingsController {
    @Autowired
    MeetingsService meetingsService;

    @PostMapping("/teacher")
    public List<String> findMidByTid(String tid){
        List<String> meetings=new ArrayList<>() ;
        List<Meeting> meeting=meetingsService.findMeetingsByTeacher(tid);
        for (Meeting meeting1 : meeting){
            meetings.add(meeting1.getMid());
        }
        return meetings;
    }

}
