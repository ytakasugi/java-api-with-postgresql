package jp.co.morgan.server.api;

import jp.co.morgan.server.dao.TaskDao;
import jp.co.morgan.server.dto.TaskDto;
import jp.co.morgan.server.util.TransactionManager;
import jp.co.morgan.server.util.Util;
import jp.co.morgan.server.constants.StatusCode;

public class getTaskMain {
    public static void main(String[] args) throws Exception {
        TaskDto taskDto = new TaskDto();
        taskDto.setTaskId(1);
        taskDto.setUserId(452);
        taskDto.setStatus(StatusCode.Code0.getCodeValue());

        try {
            TransactionManager.begin();
            TaskDao taskDao = new TaskDao();
            Util.printTask(taskDao.getTask(taskDto));
        } catch (Exception e) {
            throw new Exception();
        } finally {
            TransactionManager.end();
        }
    }
}