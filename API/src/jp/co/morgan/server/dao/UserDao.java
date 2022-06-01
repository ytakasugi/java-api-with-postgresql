package jp.co.morgan.server.dao;

import java.util.ArrayList;
import java.util.List;

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
            // コネクションを取得し、APIトランザクションを開始する
            TransactionManager.begin();

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
            // コネクションを取得し、APIトランザクションを終了する(=コネクションを解放)
            TransactionManager.end();
        }
        return userList;
    }

    public static void registNewUser() {
        PreparedStatement stmt = null;

        String sql = Util.getSql("insertNewUser");

        UserDto newUser = new UserDto();
        List<UserDto> newUserList = new ArrayList<UserDto>(); 
        
        for (int i = 0; i < 50; i++) {
            newUser.setUserName("test" + i);
            newUser.setEMail("test" + i + "@example.com");

            newUserList.add(newUser);
        }

        try {
            TransactionManager.begin();
            stmt = TransactionManager.get().prepareStatement(sql);

            int size = newUserList.size();

            for (int i = 0; i < size; i++) {
                UserDto getUser = newUserList.get(i);
                stmt.setString(1, getUser.getUserName());
                stmt.setString(2, getUser.getEMail());
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            TransactionManager.commit();
            TransactionManager.closeStatement(stmt);
            TransactionManager.end();
        }
    }
}