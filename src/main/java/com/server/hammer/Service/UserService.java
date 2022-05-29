package com.server.hammer.Service;

import com.server.hammer.Entity.User;
import com.server.hammer.Entity.UserInRoom;

import java.util.List;

public interface UserService {

    List<UserInRoom> getUsers(String roomId);
    User findUserById(String userid);
}
