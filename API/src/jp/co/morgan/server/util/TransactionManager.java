package jp.co.morgan.server.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DB関係のクラスメソッドを定義する
 */
public class TransactionManager {
    /**
     * コンストラクタ
     */
    private TransactionManager() {
    }

    /**
     * トランザクションを開始する
     */
    public static void begin() {
        // コネクションを取得する
        Connection conn = ThreadLocalConnection.get();
        try {
            if (conn != null) {
                conn.setAutoCommit(false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * トランザクションを取得する
     * @return Connection
     */
    public static Connection get() {
        // 自動コミットがTrueかどうか確認する
        ThreadLocalConnection.set();
        return ThreadLocalConnection.get();
    }

    /**
     * トランザクションを終了する
     */
    public static void end() {
        // コネクションを取得する
        Connection conn = ThreadLocalConnection.get();
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
        Connection conn = ThreadLocalConnection.get();
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
        Connection conn = ThreadLocalConnection.get();
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