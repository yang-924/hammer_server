package com.server.hammer.Service;

import com.server.hammer.Entity.User;
import com.server.hammer.Entity.UserInMeeting;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

public interface UserService {

    List<UserInMeeting> getUsers(String roomId);
    User findUserById(String userid);
    void importExcel(MultipartFile excelFile,String mid);
    User findUserByName(String name);
}
