package jp.co.morgan.server.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ThreadLocalConnection {
    private static final ThreadLocal<Connection> threadLocalConnection = new ThreadLocal<Connection>() {
        protected synchronized Connection initialValue() {
            try {
                return getConnection();
            } catch (Exception e) {
                return null;
            }
        }
    };

    /**
     * コネクションを作成する
     * @return Connection
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(Util.getProp("db.driver"));
            conn = DriverManager.getConnection(
                Util.getProp("db.url"), 
                Util.getProp("db.user"),
                Util.getProp("db.password")
            );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        return conn;
    }

    public static Connection get() {
        Connection conn = threadLocalConnection.get();
        try {
            if (conn == null) {
                System.out.println("Connection Failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
    
    /**
     * 自動コミットがTrueだった場合、メッセージを出力する
     */
    public static void set() {
        Connection conn = threadLocalConnection.get();
        try {
            if (conn.getAutoCommit()) {
                System.out.println("Auto Commit is True");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}