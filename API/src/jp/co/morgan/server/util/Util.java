package jp.co.morgan.server.util;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class Util {

    private static final String COMMON_PROP_PATH = "/workspace/common.properties";
    private static final String SQL_PROP_PATH = "/workspace/sql.properties";

    /**
     * コンストラクタ
     */
    private Util() {
    }

    /**
     * SQLプロパティを読み込み、keyと一致する値(SQL)を取得する
     * @param key
     * @return
     */
    public static String getSql(String key) {
        Properties prop = new Properties();
        try {
            InputStream is = new FileInputStream(SQL_PROP_PATH);
            prop.load(is);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return prop.getProperty(key);
    }

    /**
     * 共通プロパティを読み込み、keyと一致する値を取得する
     * @param key
     * @return
     */
    public static String getProp(String key) {
        Properties prop = new Properties();
        try {
            InputStream is = new FileInputStream(COMMON_PROP_PATH);
            prop.load(is);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return prop.getProperty(key);
    }

    /**
     * DB接続をする
     */
    public static Connection connection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                getProp("db.url"), 
                getProp("db.user"),
                getProp("db.password")
            );
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
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
     * Connectionのクローズを行う
     */
    public static void closeConnection(Connection target) {
        if (null != target) {
            try {
                target.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}