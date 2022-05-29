package com.server.hammer.ServiceImpl;

import com.server.hammer.Dao.UserDao;
import com.server.hammer.Entity.User;
import com.server.hammer.Entity.UserInRoom;
import com.server.hammer.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public List<UserInRoom> getUsers(String roomId){
        return  userDao.getUsers(roomId);
    }

    @Override
    public User findUserById(String userid)
    {
        return userDao.findUserById(userid);
    }
}
