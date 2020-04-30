package fun.fiver.cct;

import fun.fiver.util.CCTUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class CCTReporter {


    public static void generateReport(String fullyQualifiedClassName, String sourceFilePath) {

        CCTComparator.initializeWith(CCTCollector.getOriginalCCTClassNode(), CCTCollector.getModifiedCCTClassNode());

        CCTComparator.startComparing();

        //CCTCollector.printCollectedInformation();

        Map<Integer, String> sourceMap = CCTUtils.generateSourceMap(sourceFilePath);

        Set<Integer> visitedLineNumbers = CCTMapper.getVisitedLineNumbers();

        File userDirectory = new File(System.getProperty("user.dir"));

        File targetDirectory = new File(userDirectory, "target");

        if (!targetDirectory.exists())
            if (!targetDirectory.mkdir())
                try {
                    throw new Exception("target directory cannot be created!");
                } catch (Exception e) {
                    e.printStackTrace();
                }

        File reportsDirectory = new File(targetDirectory, "reports");

        if (!reportsDirectory.exists())
            if (!reportsDirectory.mkdir())
                try {
                    throw new Exception("reports directory cannot be created!");
                } catch (Exception e) {
                    e.printStackTrace();
                }

        File fileCalculatorTest = new File(reportsDirectory, fullyQualifiedClassName + ".html");

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


    }

}
