package jp.co.morgan.server.dao;

import java.util.List;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Map;

import jp.co.morgan.server.dto.TaskDto;
import jp.co.morgan.server.util.Util;
import jp.co.morgan.server.util.DBUtil;

public class TaskDao {
    public List<Map<String, Object>> getTask(Connection connection, TaskDto taskDto) throws Exception {
        String sql = Util.getSql("getTask");
        List<Object> paramList = new ArrayList<Object>();
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        try {
            paramList.add(taskDto.getTaskId());
            paramList.add(taskDto.getUserId());
            paramList.add(taskDto.getStatus()); 

            resultList = DBUtil.executeQuery(connection, sql, paramList);

        } catch (Exception e) {
            throw new Exception();
        }
        return resultList;
    }

    
    public List<Map<String, Object>> getAllTask(Connection connection) throws Exception {
        String sql = Util.getSql("getAllTask");
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        try {
            resultList = DBUtil.executeQueryNoParam(connection, sql);
        } catch (Exception e) {
            throw new Exception();
        }
        return resultList;
    }

    public void insertTask(Connection connection, TaskDto taskDto) throws Exception {
        String sql = Util.getSql("insertTask");
        List<Object> paramList = new ArrayList<Object>();
        try {
            paramList.add(taskDto.getUserId());
            paramList.add(taskDto.getContent());
            paramList.add(taskDto.getDeadLine());

            DBUtil.executeUpdate(connection, sql, paramList);
        } catch (Exception e) {
            throw new Exception();
        } 
    }
}