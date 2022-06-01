package jp.co.morgan.server.api;

import jp.co.morgan.server.dao.UserDao;

public class RegistUserMain {
    public static void main(String[] args) {
        try {
            UserDao.registNewUser();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}