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
     * PreparedStatementを作成する
     * @param sql 実行するSQL文
     * @param params 設定するパラメーター
     * @return 作成されたPreparedStatement
     * @throws SQLException
     */
    private static PreparedStatement makePreparedStatement(String sql, List<Object> params) throws SQLException {
        PreparedStatement ps = TransactionManager.get().prepareStatement(sql);
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
     * @param paramList 設定するパラメーター
     * @return 実行結果
     */
    public static List<Map<String, Object>> executeQuery(String sql, List<Object> paramList) {
        ResultSet rs = null;
        PreparedStatement ps = null;
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        try {
            ps = makePreparedStatement(sql, paramList);
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
}