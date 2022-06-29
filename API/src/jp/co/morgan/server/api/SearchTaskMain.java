package jp.co.morgan.server.api;

import jp.co.morgan.server.util.Util;
import jp.co.morgan.server.util.TransactionManager;
import jp.co.morgan.server.dao.TaskDao;

public class SearchTaskMain {
    public static void main(String[] args) {
        try {
            TransactionManager.get();

            TaskDao taskDao = new TaskDao();
            Util.printTask(taskDao.getAllTask());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            TransactionManager.end();
        }
    }
}
