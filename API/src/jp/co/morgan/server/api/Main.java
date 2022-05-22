package jp.co.morgan.server.api;

import java.util.ArrayList;

import jp.co.morgan.server.dto.UserDto;
import jp.co.morgan.server.dao.UserDao;

public class Main {
    private static ArrayList<UserDto> userList = null;
    public static void main(String[] args) {
        try {
            UserDao userDao = new UserDao();
            userList = userDao.getAllUserInfo();
            display();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void display() {
        for (int i = 0; i < userList.size(); i++) {
            UserDto userDto = userList.get(i);
            System.out.println("USER_ID: " + userDto.getUserId());
            System.out.println("USER_NAME :" + userDto.getUserName());
            System.out.println("E-mail :" + userDto.getEMail());
        }
    }
}