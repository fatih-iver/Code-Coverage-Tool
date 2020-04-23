package cct;

import util.CCTUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class CCTReporter {

    //TODO FIND A WAY TO REPLACE THIS HARDCODED FILE PATH
    private static final String CALCULATOR_FILE_PATH = "C:\\Users\\Fatih\\IdeaProjects\\Code Coverage Tool\\src\\main\\java\\Calculator.java";

    public static void generateReport() {

        Map<Integer, String> sourceMap = CCTUtils.generateSourceMap(CALCULATOR_FILE_PATH);

        Set<Integer> visitedLineNumbers = CCTMapper.getVisitedLineNumbers();

        File userDirectory = new File(System.getProperty("user.dir"));

        File outDirectory = new File(userDirectory, "out");

        if (!outDirectory.exists())
            if (!outDirectory.mkdir())
                try {
                    throw new Exception("out directory cannot be created!");
                } catch (Exception e) {
                    e.printStackTrace();
                }

        File reportsDirectory = new File(outDirectory, "reports");

        if (!reportsDirectory.exists())
            if (!reportsDirectory.mkdir())
                try {
                    throw new Exception("reports directory cannot be created!");
                } catch (Exception e) {
                    e.printStackTrace();
                }

        //TODO FIND A WAY TO REPLACE THE HARDCODED CLASS NAME
        File fileCalculatorTest = new File(reportsDirectory, "CalculatorTest.html");

        try (FileWriter fileWriter = new FileWriter(fileCalculatorTest)) {

            fileWriter.write("<pre>" + "\n");
            for (Map.Entry<Integer, String> entry : sourceMap.entrySet()) {
                Integer lineNumber = entry.getKey();
                if (visitedLineNumbers.contains(lineNumber)) {
                    fileWriter.write("<span style=\"color:green\">" + entry.getValue() + "</span>" + "\n");
                } else {
                    fileWriter.write((entry.getValue()) + "\n");
                }
            }
            fileWriter.write("</pre>" + "\n");

        } catch (IOException e) {
            e.printStackTrace();
        }

        /*

        System.out.println("<pre>");
        for(Map.Entry<Integer, String> entry: sourceMap.entrySet()){
            Integer lineNumber = entry.getKey();
            if(visitedLineNumbers.contains(lineNumber)){
                System.out.println("<span style=\"color:green\">" +entry.getValue() +"</span>");
            } else {
                System.out.println(entry.getValue());
            }
        }
        System.out.println("</pre>");

         */

    }

}
