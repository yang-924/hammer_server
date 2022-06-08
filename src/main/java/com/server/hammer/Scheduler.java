package com.server.hammer;

import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.server.hammer.Config.WebSocketServer;
import com.server.hammer.Service.MeetingsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class Scheduler {
    @Autowired
    private MeetingsService meetingsService;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public Integer getWeekday(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if(week_index<0){
            week_index = 0;
        }
        return week_index;

    }

    @Scheduled(cron="0 0 8,10,14,16 * * ? ")//每天的8，10，14，16点各执行一次
    public void testTasks() throws IOException {
        Date date=new Date();
        int weekday=getWeekday(date);
        //获取当前时间
        Time min=new Time(date.getTime());
        //获取半小时之后的时间
        long currentTime = System.currentTimeMillis() + 30 * 60 * 1000;
        Date date1 = new Date(currentTime);
        Time max=new Time(date1.getTime());

        //为节假日
        if(1<=weekday&&weekday<=5) {
            log.info("工作日");
            WebSocketServer webSocketServer=new WebSocketServer();

             List<String> meetings=meetingsService.findMeetings(weekday,min,max);
            for(String meeting : meetings){
                webSocketServer.AutoMeeting(meeting);
            }
        }else {
            log.info("节假日不上课");

        }

    }


}
