package com.server.hammer.Repository;

import com.server.hammer.Entity.UserInMeeting;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserInMeetingRepository extends JpaRepository<UserInMeeting, Integer> {

    List<UserInMeeting> findAllByMeetingId(String roomId);
}
