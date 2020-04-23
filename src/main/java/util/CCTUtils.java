package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CCTUtils {

    public static Map<Integer, String> generateSourceMap(String sourceFilePath) {

        File sourceFile = new File(sourceFilePath);

        Map<Integer, String> lineNumberToSource = new TreeMap<>();

        try {
            FileReader fileReader = new FileReader(sourceFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            int i = 0;
            while ((line = bufferedReader.readLine()) != null) {
                i += 1;
                lineNumberToSource.put(i, line);
            }
            fileReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return lineNumberToSource;
    }

}
