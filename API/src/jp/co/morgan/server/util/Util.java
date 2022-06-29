package jp.co.morgan.server.util;

import java.util.List;
import java.util.Map;
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
    public static void printUserList(List<UserDto> userList) {
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

    /**
     * ユーザー情報を標準出力する
     * @param UserDto
     */
    public static void printUser(UserDto user) {
        System.out.printf(
            "%s, %s, %s \n", 
            user.getUserId(), 
            user.getUserName(), 
            user.getEMail()
        );
    }

    /**
     * タスクを標準出力する
     * @param List<Map<String, Object>>
     */
    public static void printTask(List<Map<String, Object>> task) {
        for (int i = 0; i < task.size(); i++) {
            System.out.printf(
                "%s, %s, %s, %s, %s, %s, %s \n",
                task.get(i).get("task_id"),
                task.get(i).get("user_id"),
                task.get(i).get("content"),
                task.get(i).get("created"),
                task.get(i).get("updated"),
                task.get(i).get("dead_line"),
                task.get(i).get("status")
            );
        }
    }
}