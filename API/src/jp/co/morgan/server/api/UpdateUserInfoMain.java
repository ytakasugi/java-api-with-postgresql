package jp.co.morgan.server.api;

import java.sql.Connection;

import jp.co.morgan.server.dao.UserDao;
import jp.co.morgan.server.dto.UserDto;
import jp.co.morgan.server.util.ConnectionManager;

public class UpdateUserInfoMain {
    public static void main(String[] args) {
        Connection connection = null;

        UserDto userDto = new UserDto();
        userDto.setUserId(360);
        userDto.setEMail("test049test049@example.com");

        try {
            connection = ConnectionManager.getConnection();
            UserDao userDao = new UserDao();
            userDao.updateUserInfo(connection, userDto);
            ConnectionManager.commit(connection);
        } catch (Exception e) {
            e.printStackTrace();
            ConnectionManager.rollback(connection);
        } finally {
            ConnectionManager.end(connection);
        }
        
    }
}