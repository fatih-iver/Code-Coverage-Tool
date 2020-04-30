package fun.fiver.cct;

import fun.fiver.nodes.CCTClassNode;
import fun.fiver.nodes.CCTLabelNode;
import fun.fiver.nodes.CCTMethodNode;

import javax.naming.ldap.PagedResultsControl;
import javax.xml.ws.spi.http.HttpHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class CCTComparator {

    private static CCTComparator cctComparator = new CCTComparator();

    private static CCTClassNode originalCCTClassNode;
    private static CCTClassNode modifiedCCTClassNode;

    private static CCTClassNode activeCCTClassNode;

    private static Map<String, Set<Integer>> originalMethodCoverageMap;
    private static Map<String, Set<Integer>> modifiedMethodCoverageMap;

    private static Map<String, Set<Integer>> activeMethodCoverageMap;

    public static void initializeWith(CCTClassNode _originalCCTClassNode, CCTClassNode _modifiedClassNode){
        originalCCTClassNode = _originalCCTClassNode;
        modifiedCCTClassNode = _modifiedClassNode;
    }

    private static void calculateMethodCoverageMap(){

        activeCCTClassNode = originalCCTClassNode;

        originalMethodCoverageMap = new HashMap<>();

        activeMethodCoverageMap = originalMethodCoverageMap;

        formActiveMethodCoverageMap();

        activeCCTClassNode = modifiedCCTClassNode;

        modifiedMethodCoverageMap = new HashMap<>();

        activeMethodCoverageMap = modifiedMethodCoverageMap;

        formActiveMethodCoverageMap();

    }

    private static void compareMethodCoverageMaps(){

        System.out.println();
        System.out.println("Method Line Coverages:");

        for(CCTMethodNode cctMethodNode: originalCCTClassNode.getCCTMethodNodeList()){

            String methodName = cctMethodNode.getMethodName();

            int originalLineCount = originalMethodCoverageMap.get(methodName).size();

            int modifiedLineCount = modifiedMethodCoverageMap.containsKey(methodName) ? modifiedMethodCoverageMap.get(methodName).size() : 0;

            int coveragePercentage = (int) (1.0 * modifiedLineCount / originalLineCount * 100);

            Set<Integer> diffSet = new TreeSet<>(originalMethodCoverageMap.get(methodName));

            if(modifiedMethodCoverageMap.containsKey(methodName)){
                diffSet.removeAll(modifiedMethodCoverageMap.get(methodName));
            }

            System.out.format("%1$-16s%2$-8s%3$-2s\n", methodName + ":", "%" + coveragePercentage, diffSet);

            //System.out.println("\t" + methodName + "\t\t" +": %" + coveragePercentage + "\t\t" + " " + diffSet);

        }
    }

    private static void calculateStatementCoverage(){

        System.out.println();
        System.out.println("Method Statement Coverages:");

        Map<String, Map<String, Integer>> modifiedStatementCoverageMap = new HashMap<>();

        for(CCTMethodNode cctMethodNode: modifiedCCTClassNode.getCCTMethodNodeList()){

            String methodName = cctMethodNode.getMethodName();

            if(!modifiedStatementCoverageMap.containsKey(methodName)){
                modifiedStatementCoverageMap.put(methodName, new HashMap<>());
            }

            for(CCTLabelNode cctLabelNode: cctMethodNode.getCCTLabelNodeList()){

                String labelName = cctLabelNode.getLabel();

                if(!modifiedStatementCoverageMap.get(methodName).containsKey(labelName)){
                    modifiedStatementCoverageMap.get(methodName).put(labelName, cctLabelNode.getCctInstructionNodeList().size());
                } else {
                    int currValue = modifiedStatementCoverageMap.get(methodName).get(labelName);
                    modifiedStatementCoverageMap.get(methodName).put(labelName, Math.max(currValue, cctLabelNode.getCctInstructionNodeList().size()));
                }

            }

        }

        Map<String, Integer> modifiedStatementCountMap = new HashMap<>();

        for(CCTMethodNode cctMethodNode: modifiedCCTClassNode.getCCTMethodNodeList()) {

            String methodName = cctMethodNode.getMethodName();

            int modifiedStatementCount = 0;

            for(String labelName: modifiedStatementCoverageMap.get(methodName).keySet()){
                modifiedStatementCount += modifiedStatementCoverageMap.get(methodName).get(labelName);
            }

            modifiedStatementCountMap.put(methodName, modifiedStatementCount);

        }

        for(CCTMethodNode cctMethodNode: originalCCTClassNode.getCCTMethodNodeList()) {

            String methodName = cctMethodNode.getMethodName();

            int originalStatementCount = 0;

            for(CCTLabelNode cctLabelNode: cctMethodNode.getCCTLabelNodeList()){

                originalStatementCount += cctLabelNode.getCctInstructionNodeList().size();

            }

            int modifiedStatementCount = modifiedStatementCountMap.getOrDefault(methodName, 0);

            System.out.format("%1$-16s%2$-8s\n", methodName + ":", "%" + (int) (1.0 * modifiedStatementCount / originalStatementCount * 100));

        }





    }

    public static void startComparing(){

        calculateMethodCoverageMap();

        compareMethodCoverageMaps();

        calculateStatementCoverage();
    }

    private static void formActiveMethodCoverageMap(){

        for(CCTMethodNode cctMethodNode: activeCCTClassNode.getCCTMethodNodeList()){

            String methodName = cctMethodNode.getMethodName();

            if(!activeMethodCoverageMap.containsKey(methodName)){
                activeMethodCoverageMap.put(methodName, new TreeSet<>());
            }

            for(CCTLabelNode cctLabelNode: cctMethodNode.getCCTLabelNodeList()){

                Integer lineNumber = cctLabelNode.getLineNumber();

                activeMethodCoverageMap.get(methodName).add(lineNumber);
            }

        }

    }


}
