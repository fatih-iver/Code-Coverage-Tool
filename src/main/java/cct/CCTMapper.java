package cct;

import java.util.*;

public class CCTMapper extends HashMap<String, String> {

    private static Map<String, Set<Integer>> cctMapper = new HashMap<>();

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

        if(cctMapper.containsKey(methodName)){
            System.out.println(methodName + " : " +cctMapper.get(methodName));
            cctMapper.remove(methodName);
        }
    }
}