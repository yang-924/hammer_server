package com.server.hammer.ServiceImpl;

import com.server.hammer.Dao.UserDao;
import com.server.hammer.Entity.User;
import com.server.hammer.Entity.UserInMeeting;

import com.server.hammer.Repository.UserInMeetingRepository;
import com.server.hammer.Repository.UserRepository;
import com.server.hammer.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    UserInMeetingRepository userInMeetingRepository;

    @Override
    public List<UserInMeeting> getUsers(String roomId){
        return  userDao.getUsers(roomId);
    }

    @Override
    public User findUserById(String userid)
    {
        return userDao.findUserById(userid);
    }


    public void importExcel(MultipartFile excelFile,String mid){
        if (excelFile==null|| excelFile.getSize()==0){
            log.error("文件上传错误，重新上传");
        }
        String filename = excelFile.getOriginalFilename();
        if (!(filename.endsWith(".xls")|| filename.endsWith(".xlsx"))){
            log.error("文件上传格式错误，请重新上传");
        }

        List<UserInMeeting>list = null;
        try {
            if (filename.endsWith(".xls")){
                list = readXLS(excelFile,mid);
            }else {
                list=readXLSX(excelFile,mid);
            }
        }catch (IOException e) {
            e.printStackTrace();
            log.error("文件内容读取失败，请重试");
        }
        userInMeetingRepository.saveAll(list);
    }


    public List<UserInMeeting> readXLS(MultipartFile file,String mid) throws IOException {
        List<UserInMeeting> list =new ArrayList<>();

        InputStream inputStream = file.getInputStream();
        HSSFWorkbook workbook = new HSSFWorkbook(inputStream);

        //读取第一张sheet
        HSSFSheet sheet = workbook.getSheetAt(0);
        String errorMsg="";
        //遍历每一行Excel获取内容
        for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
            HSSFRow row = sheet.getRow(rowNum);
            if (row!=null){
                UserInMeeting user = new UserInMeeting();
                row.getCell(0).setCellType(CellType.STRING);
                user.setUserId(row.getCell(0).getStringCellValue());
                user.setMeetingId(mid);
                list.add(user);
            }
        }
        return list;
    }


    public List<UserInMeeting> readXLSX(MultipartFile file,String mid) throws IOException {
        ArrayList<UserInMeeting> list = new ArrayList<>();

        InputStream inputStream = file.getInputStream();
        XSSFWorkbook Workbook = new XSSFWorkbook(inputStream);

        XSSFSheet sheet = Workbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        for (int rowNum = 1; rowNum <= lastRowNum; rowNum++) {
            XSSFRow row = sheet.getRow(rowNum);
            if (row != null) {
                UserInMeeting user = new UserInMeeting();
                row.getCell(0).setCellType(CellType.STRING);
                user.setUserId(row.getCell(0).getStringCellValue());
                user.setMeetingId(mid);
                list.add(user);

            }
        }
        return list;

    }
    /*
    获取没有文件后缀的文件名
    */

    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }


}
