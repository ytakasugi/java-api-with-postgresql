# java-api-with-postgresql

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