package com.server.hammer.Dao;

import com.server.hammer.Entity.User;
import com.server.hammer.Entity.UserInMeeting;


import java.util.List;

public interface UserDao {
    List<UserInMeeting> getUsers(String roomId);
    User findUserById(String userid);
    User findUserByName(String name);
}
