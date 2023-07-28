package jp.co.morgan.server.api;

import java.sql.Connection;

import jp.co.morgan.server.dao.UserDao;
import jp.co.morgan.server.dto.UserDto;
import jp.co.morgan.server.util.ConnectionManager;

public class CreateNewUserMain {
    public static void main(String[] args) {
        Connection connection = null;
        UserDto newUser = new UserDto();

        newUser.setUserName("admin");
        newUser.setEMail("admin@example.com");

        try {
            connection = ConnectionManager.getConnection();
            // UserDaoをオブジェクト化
            UserDao userDao = new UserDao();
            // 一括登録を実行
            userDao.insertNewUser(connection, newUser);
            // トランザクションのコミット
            ConnectionManager.commit(connection);
        } catch (Exception e) {
            e.printStackTrace();
            ConnectionManager.rollback(connection);
        } finally {
            // トランザクション解放
            ConnectionManager.end(connection);
        }
    }
}