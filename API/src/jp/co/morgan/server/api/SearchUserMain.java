package jp.co.morgan.server.api;

import java.util.List;

import jp.co.morgan.server.util.Util;
import jp.co.morgan.server.util.TransactionManager;
import jp.co.morgan.server.dto.UserDto;
import jp.co.morgan.server.dao.UserDao;


public class SearchUserMain {
    public static void main(String[] args) {
        try {
            TransactionManager.begin();
            
            UserDao userDao = new UserDao();
            List<UserDto> userList = userDao.getAllUserInfo();
            Util.printUserList(userList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            TransactionManager.end();
        }
    }
}