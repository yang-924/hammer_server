package com.server.hammer.ServiceImpl;

import com.server.hammer.Entity.Meeting;
import com.server.hammer.Repository.MeetingsRepository;
import com.server.hammer.Service.MeetingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.List;

@Service
public class MeetingsServiceImpl implements MeetingsService {
    @Autowired
    MeetingsRepository meetingsRepository;
    @Override
    public List<String> findMeetings(Integer weekday, Time min, Time max){
        return meetingsRepository.findMeetingsByWeekdayAndTimeBetween(weekday,min,max);
    };

}
