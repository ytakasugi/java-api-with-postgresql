package jp.co.morgan.server.dao;

import java.util.List;
//import java.util.ArrayList;

import java.sql.SQLException;
//import java.sql.ResultSet;
//import java.sql.Timestamp;
import java.sql.PreparedStatement;

import jp.co.morgan.server.dto.TaskDto;
import jp.co.morgan.server.util.Util;
import jp.co.morgan.server.util.TransactionManager;

public class TaskDao {
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