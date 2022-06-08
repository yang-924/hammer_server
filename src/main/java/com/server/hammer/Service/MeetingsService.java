package com.server.hammer.Service;

import java.sql.Time;
import java.util.List;

public interface MeetingsService {
    List<String> findMeetings(Integer weekday, Time min, Time max);

}
