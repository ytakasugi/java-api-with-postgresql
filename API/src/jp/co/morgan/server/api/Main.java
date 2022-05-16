package jp.co.morgan.server.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jp.co.morgan.server.util.PropertyUtil;



public class Main {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet ret = null;

        String url = PropertyUtil.getProperty("db.url");
        String user = PropertyUtil.getProperty("db.user");;
        String password = PropertyUtil.getProperty("db.password");;

        try {
            conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false);

            stmt = conn.createStatement();
            String sql = "SELECT * FROM USERS;";
            ret = stmt.executeQuery(sql);

            //SELECT結果の受け取り
            while(ret.next()) {
                String user_id = ret.getString("USER_ID");
                String user_name = ret.getString("USER_NAME");
                String e_mail = ret.getString("E_MAIL");
                System.out.println("ID:" + user_id + " , NAME:" + user_name + " , E_MAIL:" + e_mail);
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(ret != null)ret.close();
                if(stmt != null)stmt.close();
                if(conn != null)conn.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}