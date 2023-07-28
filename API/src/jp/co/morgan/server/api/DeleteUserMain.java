package jp.co.morgan.server.api;

import java.sql.Connection;

import jp.co.morgan.server.dao.UserDao;
import jp.co.morgan.server.dto.UserDto;
import jp.co.morgan.server.util.ConnectionManager;

public class DeleteUserMain {
    public static void main(String[] args) {
        Connection connection = null;
        UserDto userDto = new UserDto();
        userDto.setUserId(351);

        try {
            connection = ConnectionManager.getConnection();
            UserDao userDao = new UserDao();
            userDao.DeleteUser(connection, userDto);
            ConnectionManager.commit(connection);
        } catch (Exception e) {
            e.printStackTrace();
            ConnectionManager.rollback(connection);
        } finally {
            ConnectionManager.end(connection);
        }
    }
}