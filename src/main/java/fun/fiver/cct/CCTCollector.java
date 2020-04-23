package fun.fiver.cct;

import fun.fiver.core.CCTClassVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

import java.io.IOException;
import java.util.*;

public class CCTCollector {

    private static List<String> methodNames = new ArrayList<>();

    private static Map<String, List<String>> methodNameToLabelsMap = new HashMap<>();

    private static Map<String, Map<String, Integer>> methodNameToLabelToLineNumberMap = new HashMap<>();

    public static void startCollectingFor(String className){

        CCTClassVisitor cctClassVisitor = new CCTClassVisitor(Opcodes.ASM8);

        ClassReader classReader = null;

        try {
            classReader = new ClassReader(className);
        } catch (IOException e) {
            e.printStackTrace();
        }

        classReader.accept(cctClassVisitor, 0);

    }

    public static void addMethod(String methodName){
        methodNames.add(methodName);
    }

    public static void addLabel(String methodName, String label){
        if(!methodNameToLabelsMap.containsKey(methodName)){
            methodNameToLabelsMap.put(methodName, new ArrayList<>());
        }

        methodNameToLabelsMap.get(methodName).add(label);

    }

    public static void matchLabelToLineNumber(String methodName, String label, Integer lineNumber){
        if(!methodNameToLabelToLineNumberMap.containsKey(methodName)){
            methodNameToLabelToLineNumberMap.put(methodName, new TreeMap<>());
        }

        methodNameToLabelToLineNumberMap.get(methodName).put(label, lineNumber);

    }

    private static void removeLastLabelFor(String methodName){
        methodNameToLabelsMap.get(methodName).remove(methodNameToLabelsMap.get(methodName).size() - 1);
    }

    private static void findLineNumbersOfUnmatchedLabelsFor(String methodName){

        List<String> labels = methodNameToLabelsMap.get(methodName);

        Map<String, Integer> labelToLineNumberMap = methodNameToLabelToLineNumberMap.get(methodName);

        for(int i = 1; i < labels.size(); i++){

            String label = labels.get(i);

            if(!labelToLineNumberMap.containsKey(label)){
                String previousLabel = labels.get(i-1);
                Integer lineNumber = labelToLineNumberMap.get(previousLabel);
                labelToLineNumberMap.put(label, lineNumber);
            }

        }
    }

    public static void finishCollectingFor(String methodName){
        removeLastLabelFor(methodName);
        findLineNumbersOfUnmatchedLabelsFor(methodName);
    }

    private static void printCollectedInformation(){

        System.out.println("****************************************");

        for(String methodName: methodNames){

            System.out.println(methodName);

            List<String> labels = methodNameToLabelsMap.get(methodName);

            Map<String, Integer> labelToLineNumberMap = methodNameToLabelToLineNumberMap.get(methodName);

            for(String label: labels){
                System.out.println(label + ":" + labelToLineNumberMap.get(label));
            }

            System.out.println("------------------------------------------------");

        }

        System.out.println("****************************************");
    }


    public static void finishCollecting(){
        printCollectedInformation();
    }

}
