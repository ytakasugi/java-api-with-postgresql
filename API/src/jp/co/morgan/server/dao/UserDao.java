package jp.co.morgan.server.dao;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.sql.Connection;
import java.sql.Timestamp;

import jp.co.morgan.server.dto.UserDto;
import jp.co.morgan.server.util.Util;
import jp.co.morgan.server.util.DBUtil;

public class UserDao {
    // 全ユーザーを取得するメソッド
    public List<Map<String, Object>> getAllUserInfo(Connection connection) throws Exception {
        // 結果格納用配列の作成
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        // SQL文作成
        String sql = Util.getSql("getAllUser");
        
        try {
            resultList = DBUtil.executeQueryNoParam(connection, sql);
        } catch (Exception e) {
            throw new Exception();
        } finally {
        }
        return resultList;
    }

    // ユーザー情報を更新する
    public void updateUserInfo(Connection connection, UserDto userDto) throws Exception {
        String sql = Util.getSql("updateUserInfo");
        List<Object> paramList = new ArrayList<Object>();
        Timestamp now = new Timestamp(System.currentTimeMillis());
    
        try {
            paramList.add(userDto.getEMail());
            paramList.add(now);
            paramList.add(userDto.getUserId());
            DBUtil.executeUpdate(connection, sql, paramList);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    // ユーザー情報を論理削除するメソッド
    public void LogicalDeleteUser(Connection connection, UserDto userDto) throws Exception {
        String sql = Util.getSql("logicalDeleteUser");
        List<Object> paramList = new ArrayList<Object>();
        Timestamp now = new Timestamp(System.currentTimeMillis());

        try {
            paramList.add(now);
            paramList.add(userDto.getUserId());

            DBUtil.executeUpdate(connection, sql, paramList);

        } catch (Exception e) {
            throw new Exception();
        }
    }

    // ユーザー情報を物理削除するメソッド
    public void DeleteUser(Connection connection, UserDto userDto) throws Exception {
        String sql = Util.getSql("deleteUser");
        List<Object> paramList = new ArrayList<Object>();

        try {
            paramList.add(userDto.getUserId());
            DBUtil.executeUpdate(connection, sql, paramList);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    // ユーザー情報を登録するメソッド
    public void insertNewUser(Connection connection, UserDto userDto) throws Exception {
        String sql = Util.getSql("insertNewUser");
        List<Object> paramList = new ArrayList<Object>();

        try {
            paramList.add(userDto.getUserName());
            paramList.add(userDto.getEMail());
            DBUtil.executeUpdate(connection, sql, paramList);

        } catch (Exception e) {
            throw new Exception();
        }
    }
}