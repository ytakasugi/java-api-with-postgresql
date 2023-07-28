package jp.co.morgan.server.api;

import java.util.List;
import java.sql.Connection;
import java.util.ArrayList;

import jp.co.morgan.server.dao.UserDao;
import jp.co.morgan.server.dto.UserDto;
import jp.co.morgan.server.util.ConnectionManager;

public class BulkCreateNewUserMain {
    public static void main(String[] args) {
        Connection connection = null;
        List<UserDto> newUserList  = new ArrayList<UserDto>();

        for (int i = 0; i < 50; i++) {
            UserDto newUser = new UserDto();
            String name = "test" + String.format("%03d", i);
            String email = "test" + String.format("%03d", i) + "@example.com";

            newUser.setUserName(name);
            newUser.setEMail(email);

            newUserList.add(newUser);
        }

        int size = newUserList.size();

        try {
            connection = ConnectionManager.getConnection();
            // UserDaoをオブジェクト化
            UserDao userDao = new UserDao();
            for (int i = 0; i < size; i++) {
                userDao.insertNewUser(connection, newUserList.get(i));
                ConnectionManager.commit(connection);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ConnectionManager.rollback(connection);
        } finally {
            // トランザクション解放
            ConnectionManager.end(connection);
        }
    }
}