package jp.co.morgan.server.dao;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import jp.co.morgan.server.dto.TaskDto;
import jp.co.morgan.server.util.Util;
import jp.co.morgan.server.util.DBUtil;
import jp.co.morgan.server.util.TransactionManager;

public class TaskDao {
    public List<Map<String, Object>> getTask(TaskDto taskDto) throws Exception {
        String sql = Util.getSql("getTask");
        List<Object> paramList = new ArrayList<Object>();
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        try {
            paramList.add(taskDto.getTaskId());
            paramList.add(taskDto.getUserId());
            paramList.add(taskDto.getStatus()); 

            resultList = DBUtil.executeQuery(sql, paramList);

        } catch (Exception e) {
            throw new Exception();
        }
        return resultList;
    }

    
    public List<Map<String, Object>> getAllTask() throws Exception {
        String sql = Util.getSql("getAllTask");
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        
        try {
            resultList = DBUtil.executeQueryNoParam(sql);
        } catch (Exception e) {
            throw new Exception();
        }
        return resultList;
    }

    public void insertTask(TaskDto taskDto) {
        PreparedStatement stmt = null;
        String sql = Util.getSql("insertTask");

        try {
            stmt = TransactionManager.get().prepareStatement(sql);
            stmt.setInt(1, taskDto.getUserId());
            stmt.setString(2, taskDto.getContent());
            stmt.setDate(3, taskDto.getDeadLine());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            TransactionManager.closeStatement(stmt);
        }
    }


    public int bulkInsertTask(List<TaskDto> newTaskList) {
        PreparedStatement stmt = null;
        int RowsCount = 0;
        String sql = Util.getSql("insertTask");

        try {
            stmt = TransactionManager.get().prepareStatement(sql);

            int size = newTaskList.size();

            for (int i = 0; i < size; i++) {
                TaskDto task = newTaskList.get(i);

                stmt.setInt(1, task.getUserId());
                stmt.setString(2, task.getContent());
                stmt.setDate(3, task.getDeadLine());
                stmt.executeUpdate();

                RowsCount = RowsCount + 1;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            TransactionManager.closeStatement(stmt);
        }
        System.out.println(RowsCount + "行作成しました.");
        return RowsCount;
    }
}