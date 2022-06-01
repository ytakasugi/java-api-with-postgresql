package jp.co.morgan.server.api;

import java.util.ArrayList;

import jp.co.morgan.server.dto.UserDto;
import jp.co.morgan.server.dao.UserDao;

public class SearchUserMain {
    private static ArrayList<UserDto> userList = null;
    public static void main(String[] args) {
        try {
            UserDao userDao = new UserDao();
            userList = userDao.getAllUserInfo();
            UserInfoListDisplay();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void UserInfoListDisplay() {
        for (int i = 0; i < userList.size(); i++) {
            UserDto userDto = userList.get(i);
            System.out.printf(
                "%s, %s, %s \n", 
                userDto.getUserId(), 
                userDto.getUserName(), 
                userDto.getEMail()
            );
        }
    }
}