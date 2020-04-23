package fun.fiver.cct;

import java.util.*;

public class CCTMapper extends HashMap<String, String> {

    private static final Map<String, Set<Integer>> cctMapper = new HashMap<>();

    public static void map(String pairAsString) {

        String[] pairAsArray = pairAsString.split(":");

        String methodName = pairAsArray[0];
        Integer lineNumber = Integer.valueOf(pairAsArray[1]);

        if (!cctMapper.containsKey(methodName)) {
            cctMapper.put(methodName, new TreeSet<>());
        }

        cctMapper.get(methodName).add(lineNumber);

    }

    public static void print(String methodName) {

        if (cctMapper.containsKey(methodName)) {
            System.out.println(methodName + " : " + cctMapper.get(methodName));
            cctMapper.remove(methodName);
        }
    }

    public static void printResults() {

        for (String methodName : cctMapper.keySet()) {
            System.out.println(methodName + " : " + cctMapper.get(methodName));

        }

    }

    public static Set<Integer> getVisitedLineNumbers() {

        Set<Integer> visitedLineNumbers = new HashSet<>();

        for (String methodName : cctMapper.keySet()) {
            visitedLineNumbers.addAll(cctMapper.get(methodName));
        }

        return visitedLineNumbers;
    }

    public static Set<String> getVisitedMethodNames() {

        Set<String> visitedMethodNames = new HashSet<>();

        for (String methodName : cctMapper.keySet()) {
            visitedMethodNames.add(methodName);
        }

        return visitedMethodNames;
    }
}