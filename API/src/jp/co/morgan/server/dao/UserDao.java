package jp.co.morgan.server.dao;

import java.util.List;
import java.util.ArrayList;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

import jp.co.morgan.server.dto.UserDto;
import jp.co.morgan.server.util.Util;
import jp.co.morgan.server.util.TransactionManager;

public class UserDao {
    // 全ユーザーを取得するメソッド
    public ArrayList<UserDto> getAllUserInfo() {
        PreparedStatement stmt = null;
        ResultSet ret = null;
        // 結果格納用配列の作成
        ArrayList<UserDto> userList = new ArrayList<UserDto>(); 
        // SQL文作成
        String sql = Util.getSql("getAllUser");
        
        try {
            stmt = TransactionManager.get().prepareStatement(sql);
            ret = stmt.executeQuery();
            

            while(ret.next()) {
                UserDto user = new UserDto();
                user.setUserId(ret.getString("user_id"));
                user.setUserName(ret.getString("user_name"));
                user.setEMail(ret.getString("e_mail"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            TransactionManager.closeResultSet(ret);
            TransactionManager.closeStatement(stmt);
        }
        return userList;
    }

    public int bulkInsertNewUser(List<UserDto> newUserList) {
        PreparedStatement stmt = null;
        int RowsCount = 0;
        String sql = Util.getSql("bulkInsertNewUser");
        
        try {
            stmt = TransactionManager.get().prepareStatement(sql);

            int size = newUserList.size();

            for (int i = 0; i < size; i++) {
                UserDto user = newUserList.get(i);
                stmt.setString(1, user.getUserName());
                stmt.setString(2, user.getEMail());
                stmt.executeUpdate();

                // 行カウントをインクリメント
                RowsCount = RowsCount + 1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            TransactionManager.commit();
            TransactionManager.closeStatement(stmt);
        }
        System.out.println(RowsCount + "行作成しました.");
        return RowsCount;
    }
}