package jp.co.morgan.server.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionManager {
    private static ThreadLocal<Connection> threadLocalConnection = new ThreadLocal<>();

    static {
        try {
            Class.forName(Util.getProp("db.driver"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * コンストラクタ
     */
    private ConnectionManager() {
    }

    /**
     * コネクションを取得・作成する
     * @return Connection
     */
    public static Connection getConnection() {
        Connection conn = threadLocalConnection.get();

        if (conn == null) {
            try {
                conn = DriverManager.getConnection(
                    Util.getProp("db.url"), 
                    Util.getProp("db.user"),
                    Util.getProp("db.password")
                );
                conn.setAutoCommit(false);
                threadLocalConnection.set(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    /**
     * 自動コミットがTrueだった場合、メッセージを出力する
     */
    public static void validate(Connection conn) {
        try {
            if (conn.getAutoCommit()) {
                System.out.println("Auto Commit is True");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * ThreadLocalからスレッドを削除する
     * @param flag
     */
    private static void remove(boolean flag) {
        if (flag) {
            threadLocalConnection.remove();
        }
    }

    /**
     * トランザクションを終了する
     */
    public static void end(Connection conn) {
        if (conn != null) {
            try {
                closeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                remove(true);
            }
        }
    }

    /**
     * コミット
     */
    public static void commit(Connection conn) {
        if (conn != null) {
            try {
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ロールバック
     */
    public static void rollback(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Connectionをクローズする
     * @param target
     */
    private static void closeConnection(Connection target) {
        if (target != null) {
            try {
                target.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
