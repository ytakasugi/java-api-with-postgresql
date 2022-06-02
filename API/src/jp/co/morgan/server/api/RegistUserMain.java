package jp.co.morgan.server.api;

import jp.co.morgan.server.util.TransactionManager;
import jp.co.morgan.server.dao.UserDao;

public class RegistUserMain {
    public static void main(String[] args) {
        try {
            TransactionManager.begin();
            
            UserDao userDao = new UserDao();
            userDao.registNewUser();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            TransactionManager.end();
        }

    }
}