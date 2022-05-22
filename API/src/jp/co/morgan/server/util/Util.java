package jp.co.morgan.server.util;

import java.util.Properties;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class Util {

    private static final String INIT_FILE_PATH = "/workspace/common.properties";
    private static final Properties properties;
    private Connection conn;


    private Util() throws Exception {
    }

    static {
        properties = new Properties();
        try {
            properties.load(Files.newBufferedReader(Paths.get(INIT_FILE_PATH), StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
            // ファイル読み込みに失敗
            System.out.println(String.format("ファイルの読み込みに失敗しました。ファイル名:%s", INIT_FILE_PATH));
        }
    }

    /**
     * プロパティ値を取得する
     *
     * @param key キー
     * @return 値
     */
    public static String getProperty(final String key) {
        return getProperty(key, "");
    }

    /**
     * プロパティ値を取得する
     *
     * @param key キー
     * @param defaultValue デフォルト値
     * @return キーが存在しない場合、デフォルト値
     *          存在する場合、値
     */
    public static String getProperty(final String key, final String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * DB接続をする
     */
    public static Connection connection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                getProperty("db.url"), 
                getProperty("db.user"),
                getProperty("db.password")
            );
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
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