package jp.co.morgan.server.api;

import java.util.List;
import java.sql.Date;
import java.util.ArrayList;

import jp.co.morgan.server.dao.TaskDao;
import jp.co.morgan.server.dto.TaskDto;
import jp.co.morgan.server.util.TransactionManager;

public class BulkCreateNewTaskMain {
    public static void main(String[] args) {
        List<TaskDto> newTaskList = new ArrayList<TaskDto>();

        for (int i = 0; i < 10; i++) {
            TaskDto newTask = new TaskDto();
            int userId = 452;
            String content = "new task no." + String.format("%03d", i);
            Date deadLine = Date.valueOf("2022-06-30");

            newTask.setUserId(userId);
            newTask.setContent(content);
            newTask.setDeadLine(deadLine);

            newTaskList.add(newTask);
        }

        int size = newTaskList.size();

        try {
            TransactionManager.begin();
            TaskDao taskDao = new TaskDao();
            
            for (int i = 0; i < size; i++) {
                taskDao.insertTask(newTaskList.get(i));
                TransactionManager.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionManager.rollback();
        } finally {
            TransactionManager.end();
        }
    }
}