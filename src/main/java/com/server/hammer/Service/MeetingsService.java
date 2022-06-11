package com.server.hammer.Service;

import com.server.hammer.Entity.Meeting;

import java.sql.Time;
import java.util.List;

public interface MeetingsService {
    List<Meeting> findMeetings(Integer weekday, Time min, Time max);
    List<Meeting> findMeetingsByTeacher(String tid);
    Meeting findMeetingById(String mid);
}
