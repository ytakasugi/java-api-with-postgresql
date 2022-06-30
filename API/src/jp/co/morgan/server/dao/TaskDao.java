package jp.co.morgan.server.dao;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import jp.co.morgan.server.dto.TaskDto;
import jp.co.morgan.server.util.Util;
import jp.co.morgan.server.util.DBUtil;

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

    public void insertTask(TaskDto taskDto) throws Exception {
        String sql = Util.getSql("insertTask");
        List<Object> paramList = new ArrayList<Object>();
        try {
            paramList.add(taskDto.getUserId());
            paramList.add(taskDto.getContent());
            paramList.add(taskDto.getDeadLine());

            DBUtil.executeUpdate(sql, paramList);
        } catch (Exception e) {
            throw new Exception();
        } 
    }
}