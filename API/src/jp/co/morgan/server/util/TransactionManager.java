package jp.co.morgan.server.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionManager {
    private static TransactionManager transaction;
    private Connection conn;

    /**
     * コンストラクタ
     */
    private TransactionManager(Connection conn) {
        this.conn = conn;
    }
    /**
     * コネクションを取得する
     * @return コネクション
     */
    public Connection getConnection() {
        return this.conn;
    }

    public static TransactionManager begin() {
        try {
            if (null == transaction) {
                Connection conn = DriverManager.getConnection(
                    Util.getProp("db.url"), 
                    Util.getProp("db.user"),
                    Util.getProp("db.password")
                );
                conn.setAutoCommit(false);
                transaction = new TransactionManager(conn);
            }
            return transaction;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * SQLを実行する。
     * @param sql　実行するSQL
     * @param params 設定するパラメーター
     * @return　実行結果
     */
    public int executeUpdate(String sql, List<Object> params) {
        PreparedStatement ps = null;
        try {
            ps = this.makePreparedStatement(sql, params);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(sql, e);
        } finally {
            TransactionManager.closeStatement(ps);
        }
    }
    
    /**
     * PreparedStatementを作成する
     * @param sql 実行するSQL文
     * @param params 設定するパラメーター
     * @return 作成されたPreparedStatement
     * @throws SQLException
     */
    PreparedStatement makePreparedStatement(String sql, List<Object> params) throws SQLException {
        PreparedStatement ps = this.conn.prepareStatement(sql);
        if (null == params) {
            return ps;
        }
        int index = 1;
        for (Object param : params) {
            ps.setObject(index, param);
            index = index + 1;
        }
        return ps;
    }

    /**
     * SQLの実行結果をマップのリストで取得する
     * @param sql 実行するSQL
     * @param paramList　設定するパラメーター
     * @return　実行結果
     */
    public List<Map<String, Object>> executeQuery(String sql, List<Object> paramList) {
        ResultSet rs = null;
        PreparedStatement ps = null;
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        try {
            ps = this.makePreparedStatement(sql, paramList);
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int count = rsmd.getColumnCount();
            List<String> columnList = new ArrayList<String>();
            for (int i = 0; i < count; i++) {
                columnList.add(rsmd.getColumnName(i + 1));
            }
            while (rs.next()) {
                Map<String, Object> row = new HashMap<String, Object>();
                result.add(row);
                for (int i = 0; i < columnList.size(); i++) {
                    String key = (String)columnList.get(i);
                    row.put(key, rs.getObject(key));
                }
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            TransactionManager.closeResultSet(rs);
            TransactionManager.closeStatement(ps);
        }
    }

    /**
     * ロールバックを行う
     */
    public void rollback() {
        try {
            this.conn.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * コミットを行う
     */
    public void commit() {
        try {
            this.conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * トランザクションを終了する
     */
    public static void end() {
        if (null != transaction) {
            transaction.close();
        }
    }

    void close() {
        try {
            this.conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * PreparedStatementのクローズする
     * @param target
     */
    public static void closeStatement(PreparedStatement target) {
        if (null != target) {
            try {
                target.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ResultSetのクローズする
     */
    public static void closeResultSet(ResultSet target) {
        if (null != target) {
            try {
                target.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * コネクションのクローズを行う
     */
    public void closeConnection() {
        try {
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}