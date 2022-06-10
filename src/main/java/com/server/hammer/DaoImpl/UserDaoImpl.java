package com.server.hammer.DaoImpl;

import com.server.hammer.Dao.UserDao;

import com.server.hammer.Entity.User;
import com.server.hammer.Entity.UserInMeeting;

import com.server.hammer.Repository.UserInMeetingRepository;
import com.server.hammer.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    UserInMeetingRepository userInMeetingRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public List<UserInMeeting> getUsers(String roomId){
        return userInMeetingRepository.findAllByMeetingId(roomId);
    }

    public User findUserById(String userid){
        return  userRepository.findUserByUserId(userid);
    }



}
