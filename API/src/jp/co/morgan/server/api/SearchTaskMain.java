package jp.co.morgan.server.api;

import java.util.List;

import jp.co.morgan.server.util.Util;
import jp.co.morgan.server.util.TransactionManager;
import jp.co.morgan.server.dto.TaskDto;
import jp.co.morgan.server.dao.TaskDao;

public class SearchTaskMain {
    public static void main(String[] args) {
        try {
            TransactionManager.get();

            TaskDao taskDao = new TaskDao();
            List<TaskDto> taskList = taskDao.getAllTask();
            Util.printTaskList(taskList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            TransactionManager.end();
        }
    }
}
