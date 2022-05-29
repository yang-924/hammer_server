package com.server.hammer.Dao;

import com.server.hammer.Entity.User;
import com.server.hammer.Entity.UserInRoom;

import java.util.List;

public interface UserDao {
    List<UserInRoom> getUsers(String roomId);
    public User findUserById(String userid);
}
