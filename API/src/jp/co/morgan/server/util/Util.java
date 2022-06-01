package jp.co.morgan.server.util;

import java.util.ArrayList;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import jp.co.morgan.server.dto.UserDto;

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
     * @return String
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
     * @return String
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
     * ユーザー情報を標準出力する
     * @param ArrayList<UserDto>
     */
    public static void printUserInfo(ArrayList<UserDto> userList) {
        for (int i = 0; i < userList.size(); i++) {
            UserDto userDto = userList.get(i);
            System.out.printf(
                "%s, %s, %s \n", 
                userDto.getUserId(), 
                userDto.getUserName(), 
                userDto.getEMail()
            );
        }
    }
}