package jp.co.morgan.server.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DB関係のクラスメソッドを定義する
 */
public class TransactionManager {
    private static final ThreadLocal<Connection> threadLocalConnection = new ThreadLocal<Connection>();
    //private static final ThreadLocal<PreparedStatement> threadLocalStatement = new ThreadLocal<PreparedStatement>();
    //private static final ThreadLocal<ResultSet> threadLocalResultSet = new ThreadLocal<ResultSet>();

    /**
     * コンストラクタ
     */
    private TransactionManager() {
    }

    /**
     * コネクションを作成する
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                Util.getProp("db.url"), 
                Util.getProp("db.user"),
                Util.getProp("db.password")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * トランザクションを開始する
     */
    public static void begin() {
        // コネクションを取得する
        Connection conn = threadLocalConnection.get();
        try {
            if (conn != null) {
                conn.setAutoCommit(false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * トランザクションを終了する
     */
    public static void end() {
        // コネクションを取得する
        Connection conn = threadLocalConnection.get();
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * コミット
     */
    public static void commit() {
        Connection conn = threadLocalConnection.get();
        try {
            if (conn != null) {
                conn.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * ロールバック
     */
    public static void rollback() {
        Connection conn = threadLocalConnection.get();
        try {
            if (conn != null) {
                conn.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * PreparedStatementのクローズする
     * @param target
     */
    public static void closeStatement(PreparedStatement target) {
        if (target != null) {
            try {
                target.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ResultSetのクローズする
     * @param target
     */
    public static void closeResultSet(ResultSet target) {
        if (target != null) {
            try {
                target.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}