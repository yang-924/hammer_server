package com.server.hammer.Controller;

import com.server.hammer.Entity.User;
import com.server.hammer.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/login")
    public User login(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        User user = userService.findUserById(username);
        if(user == null){
            user = new User();
            return user;
        }
        String verify =user.getPassword();
        if (verify != password) {
            user = new User();
        }
        return user;
    };

}
