package jp.co.morgan.server.util;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String COMMON_PROP_PATH = "/workspace/common.properties";
    private static final String SQL_PROP_PATH = "/workspace/sql.properties";

    /**
     * コンストラクタ
     */
    private Util() {
    }

    public static void init() {
        try {
            Class.forName(Util.getProp("db.driver"));
            DriverManager.getConnection(
                Util.getProp("db.url"), 
                Util.getProp("db.user"),
                Util.getProp("db.password")
            );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } 
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
}