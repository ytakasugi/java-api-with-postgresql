package jp.co.morgan.server.api;

import jp.co.morgan.server.dao.UserDao;
import jp.co.morgan.server.dto.UserDto;
import jp.co.morgan.server.util.TransactionManager;

public class LogicalDeleteUserMain {
    public static void main(String[] args) {
        UserDto userDto = new UserDto();
        userDto.setUserId(351);

        try {
            TransactionManager.begin();
            UserDao userDao = new UserDao();
            userDao.LogicalDeleteUser(userDto);
            TransactionManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            TransactionManager.rollback();
        } finally {
            TransactionManager.end();
        }
    }
}