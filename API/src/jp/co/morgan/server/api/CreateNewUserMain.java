package jp.co.morgan.server.api;

import jp.co.morgan.server.dao.UserDao;
import jp.co.morgan.server.dto.UserDto;
import jp.co.morgan.server.util.TransactionManager;

public class CreateNewUserMain {
    public static void main(String[] args) {
            UserDto newUser = new UserDto();

            newUser.setUserName("admin");
            newUser.setEMail("admin@example.com");

        try {
            // トランザクション開始
            TransactionManager.begin();
            // UserDaoをオブジェクト化
            UserDao userDao = new UserDao();
            // 一括登録を実行
            userDao.insertNewUser(newUser);
            // トランザクションのコミット
            TransactionManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            TransactionManager.rollback();
        } finally {
            // トランザクション解放
            TransactionManager.end();
        }
    }
}