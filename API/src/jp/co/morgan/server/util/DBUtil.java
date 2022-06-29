package jp.co.morgan.server.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtil {
    /**
     * コンストラクタ
     */
    private DBUtil() {
    }

    /**
     * PreparedStatementを作成し、パラメータを設定する
     * @param sql
     * @param paramList
     * @return PreparedStatement
     * @throws SQLException
     */
    private static PreparedStatement setSqlPram(String sql, List<Object> paramList) throws SQLException {
        PreparedStatement ps = TransactionManager.get().prepareStatement(sql);
        if (null == paramList) {
            return ps;
        }
        int index = 1;
        for (Object param : paramList) {
            ps.setObject(index, param);
            index = index + 1;
        }
        return ps;
    }

    /**
     * PreparedStatementを作成する
     * @param sql
     * @return PreparedStatement
     * @throws SQLException
     */
    private static PreparedStatement setSql(String sql) throws SQLException {
        PreparedStatement ps = TransactionManager.get().prepareStatement(sql);
        
        return ps;
    }

     /**
     * SQLの実行結果をマップのリストで取得する
     * @param sql 実行するSQL
     * @param paramList 設定するパラメーター
     * @return 実行結果
     */
    public static List<Map<String, Object>> executeQuery(String sql, List<Object> paramList) {
        ResultSet rs = null;
        PreparedStatement ps = null;
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        try {
            ps = setSqlPram(sql, paramList);
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
            throw new RuntimeException();
        } finally {
            TransactionManager.closeResultSet(rs);
            TransactionManager.closeStatement(ps);
        }
    }

    /**
     * SQLの実行結果をマップのリストで取得する
     * @param sql 実行するSQL
     * @return 実行結果
     */
    public static List<Map<String, Object>> executeQueryNoParam(String sql) {
        ResultSet rs = null;
        PreparedStatement ps = null;
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        try {
            ps = setSql(sql);
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
            throw new RuntimeException();
        } finally {
            TransactionManager.closeResultSet(rs);
            TransactionManager.closeStatement(ps);
        }
    }

    /**
     * SQLを実行する。
     * @param sql 実行するSQL
     * @param params 設定するパラメーター
     * @return 実行結果
     */
    public int executeUpdate(String sql, List<Object> params) {
        PreparedStatement ps = null;
        try {
            ps = setSqlPram(sql, params);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(sql, e);
        } finally {
            TransactionManager.closeStatement(ps);
        }
    }
}
