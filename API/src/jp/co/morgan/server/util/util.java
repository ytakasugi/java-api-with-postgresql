package jp.co.morgan.server.util;

import java.nio.file.Paths;
import java.nio.file.Path;

public class util {
    public static void main(String[] args) {
        System.out.println(Util.getProperty("db.url"));
        
        String filename = "common.properties";
        Path pathToFile = Paths.get(filename);
        System.out.println(pathToFile.toAbsolutePath());
        
    }
}