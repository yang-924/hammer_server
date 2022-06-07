package com.server.hammer.Controller;

import com.server.hammer.Entity.User;
import com.server.hammer.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Controller
@CrossOrigin
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

    @PostMapping("/upload")
    @ResponseBody
    public Boolean upload(@RequestParam("userId") String userId, MultipartFile file,String fileName)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String directory = simpleDateFormat.format(new Date());
        String fileSavePath = "D:/HCI_TMP_FILE/";
        String path = fileSavePath + directory + "/" + userId;
        File dir = new File(path);
        if(!dir.exists()){
            dir.mkdirs();
        }

        log.info(fileName);
        String suffix = fileName.substring(fileName.lastIndexOf('.'));
        String newFileName = UUID.randomUUID().toString().replaceAll("-","") + suffix;
        File newFile = new File(path + "/" + newFileName); //本机上的文件路径

        try {
            file.transferTo(newFile);
            return true;
        } catch (IOException e){
            return false;
        }
    }
}
