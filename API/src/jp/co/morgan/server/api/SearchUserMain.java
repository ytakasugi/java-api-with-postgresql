package jp.co.morgan.server.api;

import jp.co.morgan.server.util.Util;
import jp.co.morgan.server.util.ConnectionManager;

import java.sql.Connection;

import jp.co.morgan.server.dao.UserDao;


public class SearchUserMain {
    public static void main(String[] args) {
        Connection connection = null;

        try {
            connection = ConnectionManager.getConnection();
            
            UserDao userDao = new UserDao();
            Util.printUser(userDao.getAllUserInfo(connection));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.end(connection);
        }
    }
}