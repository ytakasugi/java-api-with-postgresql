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