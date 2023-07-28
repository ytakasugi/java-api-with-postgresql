package jp.co.morgan.server.api;

import jp.co.morgan.server.util.Util;
import jp.co.morgan.server.util.ConnectionManager;
import java.sql.Connection;

import jp.co.morgan.server.dao.TaskDao;

public class SearchTaskMain {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = ConnectionManager.getConnection();
            TaskDao taskDao = new TaskDao();
            Util.printTask(taskDao.getAllTask(connection));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.end(connection);
        }
    }
}
