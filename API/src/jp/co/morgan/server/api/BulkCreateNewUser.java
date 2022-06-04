package jp.co.morgan.server.api;

import java.util.List;
import java.util.ArrayList;

import jp.co.morgan.server.dao.UserDao;
import jp.co.morgan.server.dto.UserDto;
import jp.co.morgan.server.util.TransactionManager;

public class BulkCreateNewUser {
    public static void main(String[] args) {
        List<UserDto> newUserList  = new ArrayList<UserDto>();

        for (int i = 0; i < 50; i++) {
            UserDto newUser = new UserDto();
            String name = "test" + String.format("%03d", i);
            String email = "test" + String.format("%03d", i) + "@example.com";

            newUser.setUserName(name);
            newUser.setEMail(email);

            newUserList.add(newUser);
        }

        try {
            // トランザクション開始
            TransactionManager.begin();
            // UserDaoをオブジェクト化
            UserDao userDao = new UserDao();
            // 一括登録を実行
            userDao.bulkInsertNewUser(newUserList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // トランザクション解放
            TransactionManager.end();
        }
    }
}