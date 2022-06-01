package jp.co.morgan.server.api;

import java.util.ArrayList;

import jp.co.morgan.server.util.Util;
import jp.co.morgan.server.dto.UserDto;
import jp.co.morgan.server.dao.UserDao;

public class SearchUserMain {
    public static void main(String[] args) {
        try {
            UserDao userDao = new UserDao();
            ArrayList<UserDto> userList = userDao.getAllUserInfo();
            Util.printUserInfo(userList);
            userDao.registNewUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}