package jp.co.morgan.server.api;

import java.util.List;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;

import jp.co.morgan.server.dao.TaskDao;
import jp.co.morgan.server.dto.TaskDto;
import jp.co.morgan.server.util.ConnectionManager;

public class BulkCreateNewTaskMain {
    public static void main(String[] args) {
        Connection connection = null;
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
            connection = ConnectionManager.getConnection();

            TaskDao taskDao = new TaskDao();
            
            for (int i = 0; i < size; i++) {
                taskDao.insertTask(connection, newTaskList.get(i));
                ConnectionManager.commit(connection);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ConnectionManager.rollback(connection);
        } finally {
            ConnectionManager.end(connection);
        }
    }
}