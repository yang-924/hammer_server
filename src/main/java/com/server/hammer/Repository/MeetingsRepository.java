package com.server.hammer.Repository;

import com.server.hammer.Entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.List;

@Repository
public interface MeetingsRepository extends JpaRepository<Meeting,Integer> {

    List<Meeting> findAllByWeekdayAndTimeBetween(Integer weekday, Time min,Time max);
    List<Meeting> findAllByTid(String tid);
}
