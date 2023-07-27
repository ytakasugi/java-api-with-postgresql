# java-api-with-postgresql

### ビルド

```
javac -sourcepath src -d classes src/jp/co/morgan/server/api/*java
jar cvfm morgan-api.jar META-INF/MANIFEST.MF -C classes .
jar tvf morgan-api.jar
```

## サンプルコード一覧

```java
public class util {
    public static void main(String[] args) {
        System.out.println(Util.getProperty("db.url"));
        
        String filename = "common.properties";
        Path pathToFile = Paths.get(filename);
        System.out.println(pathToFile.toAbsolutePath());
        
    }
}
```

```java
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

/**
 * DBにアクセスするJDBCのラッピング
 * http://aoyagikouhei.blog8.fc2.com/blog-entry-74.html
 */
public class DbAccess {
    private static final String KEY_DRIVER = "/setting/jdbc/driver";
    private static final String KEY_SQL = "/setting/jdbc/url";
    private static final String KEY_USER = "/setting/jdbc/user";
    private static final String KEY_PASSWORD = "/setting/jdbc/password";
    
    private static DbAccess instance;
    
    /**
     * DbAccessオブジェクトを取得する
     * @param setting 設定ファイル
     * @return DbAccessオブジェクト
     */
    public static DbAccess getDbAccess(Setting setting) {
        try {
            if (null == instance) {
                Class.forName(setting.getValue(KEY_DRIVER));
                Connection conn = DriverManager.getConnection(
                        setting.getValue(KEY_SQL),
                        setting.getValue(KEY_USER),
                        setting.getValue(KEY_PASSWORD));
                conn.setAutoCommit(false);
                instance = new DbAccess(conn);
            }
            return instance;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * すべてをクローズする。
     */
    public static void closeAll() {
        if (null != instance) {
            instance.close();
        }
    }
    
    /**
     * PreparedStatementを静かにクローズする
     * @param target クローズする対象
     */
    public static void closeQuietly(PreparedStatement target) {
        if (null != target) {
            try {
                target.close();
            } catch (SQLException e) {
            }
        }
    }
    
    /**
     * ResultSetを静かにクローズする
     * @param target クローズする対象
     */
    public static void closeQuietly(ResultSet target) {
        if (null != target) {
            try {
                target.close();
            } catch (SQLException e) {
            }
        }
    }
    
    private Connection conn;
    
    /**
     * コンストラクター
     * @param conn コネクション
     */
    private DbAccess(Connection conn) {
        this.conn = conn;
    }
    
    /**
     * コネクションを取得する
     * @return コネクション
     */
    public Connection getConnection() {
        return this.conn;
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
            ps = this.makePreparedStatement(sql, params);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(sql, e);
        } finally {
            DbAccess.closeQuietly(ps);
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
     * クローズを行う
     */
    void close() {
        try {
            this.conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * SQLの実行結果をマップのリストで取得する
     * @param sql 実行するSQL
     * @param paramList 設定するパラメーター
     * @return 実行結果
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
                columnList.add(rsmd.getColumnName(i+1));
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
            DbAccess.closeQuietly(rs);
            DbAccess.closeQuietly(ps);
        }
    }
}
```

## HikariCPを使用したコネクション作成

```java
import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class TransactionManager {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/your_database";
    private static final String USERNAME = "your_username";
    private static final String PASSWORD = "your_password";

    private static HikariDataSource dataSource;
    private ThreadLocal<Connection> threadLocalConnection = new ThreadLocal<>();

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(JDBC_URL);
        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);

        dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        Connection connection = threadLocalConnection.get();
        if (connection == null || connection.isClosed()) {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            threadLocalConnection.set(connection);
        }
        return connection;
    }

    public void commitTransaction() throws SQLException {
        Connection connection = threadLocalConnection.get();
        if (connection != null) {
            connection.commit();
            connection.setAutoCommit(true);
        }
    }

    public void rollbackTransaction() throws SQLException {
        Connection connection = threadLocalConnection.get();
        if (connection != null) {
            connection.rollback();
            connection.setAutoCommit(true);
        }
    }

    public void closeConnection() {
        Connection connection = threadLocalConnection.get();
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            threadLocalConnection.remove();
        }
    }

    public void closeDataSource() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
```