package com.server.hammer.Controller;

import com.alibaba.fastjson2.JSONObject;
import com.server.hammer.Entity.User;
import com.server.hammer.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@CrossOrigin
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/login")
    @ResponseBody
    JSONObject login(String userid, String password)  {
        User user = userService.findUserById(userid);
            log.info(userid+password);
        JSONObject obj= new JSONObject();
        if(user == null){
            obj.put("success",false);
            obj.put("account_type",-1);
            obj.put("username","");
            return obj;
        }
        String verify =user.getPassword();
        if (!verify.equals(password)) {
            obj.put("success",false);
            obj.put("account_type",-1);
            obj.put("username","");
            return obj;
        }
        obj.put("success",true);
        obj.put("account_type",0);
        obj.put("username",user.getUserName());
        return obj;
    };

    @PostMapping("/upload/new")
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

    @PostMapping("/upload")
    @ResponseBody
    public void importExcel(@RequestParam("mid") String mid,MultipartFile file){
        userService.importExcel(file,mid);
    }


}
