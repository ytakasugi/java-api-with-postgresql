package jp.co.morgan.server.dao;

import java.util.List;
import java.util.ArrayList;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

import jp.co.morgan.server.dto.TaskDto;
import jp.co.morgan.server.util.Util;
import jp.co.morgan.server.util.DBUtil;
import jp.co.morgan.server.constants.StatusCode;
import jp.co.morgan.server.util.TransactionManager;

public class TaskDao {
    public void getTask(TaskDto taskDto) {
        //PreparedStatement stmt = null;
        //ResultSet ret = null;
        String sql = Util.getSql("getTask");
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(1);
        paramList.add(452);
        paramList.add(StatusCode.Code0.getCodeValue()); 
        DBUtil.executeQuery(sql, paramList);
        try {
            paramList.add(taskDto.getTaskId());
            paramList.add(taskDto.getUserId());
            paramList.add(StatusCode.Code0.getCodeValue()); 
            DBUtil.executeQuery(sql, paramList);
            /*
            stmt = TransactionManager.get().prepareStatement(sql);
            stmt.setInt(1, taskDto.getTaskId());
            stmt.setInt(2, taskDto.getUserId());
            stmt.setString(3, taskDto.getStatus());
            ret = stmt.executeQuery();

            while(ret.next()) {
                taskDto.setTaskId(ret.getInt("task_id"));
                taskDto.setUserId(ret.getInt("user_id"));
                taskDto.setContent(ret.getString("content"));
                taskDto.setCreated(ret.getTimestamp("created"));
                taskDto.setUpdated(ret.getTimestamp("updated"));
                taskDto.setDeadLine(ret.getDate("dead_line"));
                taskDto.setStatus(ret.getString("status"));
            }
            */
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //TransactionManager.closeResultSet(ret);
            //TransactionManager.closeStatement(stmt);
        }
    }

    
    public List<TaskDto> getAllTask() {
        PreparedStatement stmt = null;
        ResultSet ret = null;
        List<TaskDto> taskList = new ArrayList<TaskDto>();
        String sql = Util.getSql("getAllTask");

        try {
            stmt = TransactionManager.get().prepareStatement(sql);
            ret = stmt.executeQuery();

            while(ret.next()) {
                TaskDto task = new TaskDto();
                task.setTaskId(ret.getInt("task_id"));
                task.setUserId(ret.getInt("user_id"));
                task.setContent(ret.getString("content"));
                task.setCreated(ret.getTimestamp("created"));
                task.setUpdated(ret.getTimestamp("updated"));
                task.setDeadLine(ret.getDate("dead_line"));
                task.setStatus(ret.getString("status"));

                taskList.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            TransactionManager.closeResultSet(ret);
            TransactionManager.closeStatement(stmt);
        }
        return taskList;
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