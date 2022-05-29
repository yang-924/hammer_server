package com.server.hammer.DaoImpl;

import com.server.hammer.Dao.UserDao;

import com.server.hammer.Entity.User;
import com.server.hammer.Entity.UserInRoom;
import com.server.hammer.Repository.UserInRoomRepository;
import com.server.hammer.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    UserInRoomRepository userInRoomRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public List<UserInRoom> getUsers(String roomId){
        return userInRoomRepository.findAllByRoomId(roomId);
    }

    public User findUserById(String userid){
        return  userRepository.findUserByUserId(userid);
    }



}
