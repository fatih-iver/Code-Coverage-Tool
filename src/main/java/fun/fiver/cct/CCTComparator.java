package fun.fiver.cct;

import fun.fiver.nodes.CCTClassNode;
import fun.fiver.nodes.CCTInstructionNode;
import fun.fiver.nodes.CCTLabelNode;
import fun.fiver.nodes.CCTMethodNode;
import jdk.internal.org.objectweb.asm.Opcodes;

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

    public static void initializeWith(CCTClassNode _originalCCTClassNode, CCTClassNode _modifiedClassNode) {
        originalCCTClassNode = _originalCCTClassNode;
        modifiedCCTClassNode = _modifiedClassNode;
    }

    private static void calculateMethodCoverageMap() {

        activeCCTClassNode = originalCCTClassNode;

        originalMethodCoverageMap = new HashMap<>();

        activeMethodCoverageMap = originalMethodCoverageMap;

        formActiveMethodCoverageMap();

        activeCCTClassNode = modifiedCCTClassNode;

        modifiedMethodCoverageMap = new HashMap<>();

        activeMethodCoverageMap = modifiedMethodCoverageMap;

        formActiveMethodCoverageMap();

    }

    private static void compareMethodCoverageMaps() {

        System.out.println();
        System.out.println("Method Line Coverages:");

        for (CCTMethodNode cctMethodNode : originalCCTClassNode.getCCTMethodNodeList()) {

            String methodName = cctMethodNode.getMethodName();

            int originalLineCount = originalMethodCoverageMap.get(methodName).size();

            int modifiedLineCount = modifiedMethodCoverageMap.containsKey(methodName) ? modifiedMethodCoverageMap.get(methodName).size() : 0;

            int coveragePercentage = (int) (1.0 * modifiedLineCount / originalLineCount * 100);

            Set<Integer> diffSet = new TreeSet<>(originalMethodCoverageMap.get(methodName));

            if (modifiedMethodCoverageMap.containsKey(methodName)) {
                diffSet.removeAll(modifiedMethodCoverageMap.get(methodName));
            }

            System.out.format("%1$-16s%2$-8s%3$-2s\n", methodName + ":", "%" + coveragePercentage, diffSet);

            //System.out.println("\t" + methodName + "\t\t" +": %" + coveragePercentage + "\t\t" + " " + diffSet);

        }
    }

    private static void calculateStatementCoverage() {

        System.out.println();
        System.out.println("Method Statement Coverages:");

        Map<String, Map<String, Integer>> modifiedStatementCoverageMap = new HashMap<>();

        for (CCTMethodNode cctMethodNode : modifiedCCTClassNode.getCCTMethodNodeList()) {

            String methodName = cctMethodNode.getMethodName();

            if (!modifiedStatementCoverageMap.containsKey(methodName)) {
                modifiedStatementCoverageMap.put(methodName, new HashMap<>());
            }

            for (CCTLabelNode cctLabelNode : cctMethodNode.getCCTLabelNodeList()) {

                String labelName = cctLabelNode.getLabel();

                if (!modifiedStatementCoverageMap.get(methodName).containsKey(labelName)) {
                    modifiedStatementCoverageMap.get(methodName).put(labelName, cctLabelNode.getCctInstructionNodeList().size());
                } else {
                    int currValue = modifiedStatementCoverageMap.get(methodName).get(labelName);
                    modifiedStatementCoverageMap.get(methodName).put(labelName, Math.max(currValue, cctLabelNode.getCctInstructionNodeList().size()));
                }

            }

        }

        Map<String, Integer> modifiedStatementCountMap = new HashMap<>();

        for (CCTMethodNode cctMethodNode : modifiedCCTClassNode.getCCTMethodNodeList()) {

            String methodName = cctMethodNode.getMethodName();

            int modifiedStatementCount = 0;

            for (String labelName : modifiedStatementCoverageMap.get(methodName).keySet()) {
                modifiedStatementCount += modifiedStatementCoverageMap.get(methodName).get(labelName);
            }

            modifiedStatementCountMap.put(methodName, modifiedStatementCount);

        }

        for (CCTMethodNode cctMethodNode : originalCCTClassNode.getCCTMethodNodeList()) {

            String methodName = cctMethodNode.getMethodName();

            int originalStatementCount = 0;

            for (CCTLabelNode cctLabelNode : cctMethodNode.getCCTLabelNodeList()) {

                originalStatementCount += cctLabelNode.getCctInstructionNodeList().size();

            }

            int modifiedStatementCount = modifiedStatementCountMap.getOrDefault(methodName, 0);

            System.out.format("%1$-16s%2$-8s\n", methodName + ":", "%" + (int) (1.0 * modifiedStatementCount / originalStatementCount * 100));

        }


    }

    public static void startComparing() {

        calculateMethodCoverageMap();

        compareMethodCoverageMaps();

        calculateStatementCoverage();

        calculateBranchCoverage();
    }

    private static void formActiveMethodCoverageMap() {

        for (CCTMethodNode cctMethodNode : activeCCTClassNode.getCCTMethodNodeList()) {

            String methodName = cctMethodNode.getMethodName();

            if (!activeMethodCoverageMap.containsKey(methodName)) {
                activeMethodCoverageMap.put(methodName, new TreeSet<>());
            }

            for (CCTLabelNode cctLabelNode : cctMethodNode.getCCTLabelNodeList()) {

                Integer lineNumber = cctLabelNode.getLineNumber();

                activeMethodCoverageMap.get(methodName).add(lineNumber);
            }

        }

    }

    private static void calculateBranchCoverage() {

        System.out.println();
        System.out.println("Method Branch Coverages:");


        Map<String, Integer> originalMethodNameToBranchCountMap = new HashMap<>();

        for (CCTMethodNode cctMethodNode : originalCCTClassNode.getCCTMethodNodeList()) {

            String methodName = cctMethodNode.getMethodName();

            int originalBranchCount = 0;

            for (CCTLabelNode cctLabelNode : cctMethodNode.getCCTLabelNodeList()) {

                for (CCTInstructionNode cctInstructionNode : cctLabelNode.getCctInstructionNodeList()) {

                    switch (cctInstructionNode.getOpcode()) {
                        case Opcodes.IFEQ:
                        case Opcodes.IFNE:
                        case Opcodes.IFLT:
                        case Opcodes.IFGE:
                        case Opcodes.IFGT:
                        case Opcodes.IFLE:
                        case Opcodes.IF_ICMPEQ:
                        case Opcodes.IF_ICMPNE:
                        case Opcodes.IF_ICMPLT:
                        case Opcodes.IF_ICMPGE:
                        case Opcodes.IF_ICMPGT:
                        case Opcodes.IF_ICMPLE:
                        case Opcodes.IF_ACMPEQ:
                        case Opcodes.IF_ACMPNE:
                        case Opcodes.GOTO:
                        case Opcodes.JSR:
                        case Opcodes.IFNULL:
                        case Opcodes.IFNONNULL:
                            originalBranchCount++;
                    }

                }

            }

            originalMethodNameToBranchCountMap.put(methodName, originalBranchCount);

        }

        Map<String, Map<String, Integer>> modifiedMethodNameToLabelToBranchCountMap = new HashMap<>();

        for (CCTMethodNode cctMethodNode : modifiedCCTClassNode.getCCTMethodNodeList()) {

            String methodName = cctMethodNode.getMethodName();

            if (!modifiedMethodNameToLabelToBranchCountMap.containsKey(methodName)) {
                modifiedMethodNameToLabelToBranchCountMap.put(methodName, new HashMap<>());
            }

            for (CCTLabelNode cctLabelNode : cctMethodNode.getCCTLabelNodeList()) {

                String labelName = cctLabelNode.getLabel();

                int labelBranchCount = 0;

                for (CCTInstructionNode cctInstructionNode : cctLabelNode.getCctInstructionNodeList()) {

                    switch (cctInstructionNode.getOpcode()) {
                        case Opcodes.IFEQ:
                        case Opcodes.IFNE:
                        case Opcodes.IFLT:
                        case Opcodes.IFGE:
                        case Opcodes.IFGT:
                        case Opcodes.IFLE:
                        case Opcodes.IF_ICMPEQ:
                        case Opcodes.IF_ICMPNE:
                        case Opcodes.IF_ICMPLT:
                        case Opcodes.IF_ICMPGE:
                        case Opcodes.IF_ICMPGT:
                        case Opcodes.IF_ICMPLE:
                        case Opcodes.IF_ACMPEQ:
                        case Opcodes.IF_ACMPNE:
                        case Opcodes.GOTO:
                        case Opcodes.JSR:
                        case Opcodes.IFNULL:
                        case Opcodes.IFNONNULL:
                            labelBranchCount++;
                    }

                }

                int soFarLabelBranchCount = 0;

                if (modifiedMethodNameToLabelToBranchCountMap.get(methodName).containsKey(labelName)) {
                    soFarLabelBranchCount = modifiedMethodNameToLabelToBranchCountMap.get(methodName).get(labelName);
                }

                if (labelBranchCount > soFarLabelBranchCount) {
                    modifiedMethodNameToLabelToBranchCountMap.get(methodName).put(labelName, labelBranchCount);
                }

            }


        }

        Map<String, Integer> modifiedMethodNameToBranchCountMap = new HashMap<>();

        for (CCTMethodNode cctMethodNode : modifiedCCTClassNode.getCCTMethodNodeList()) {

            String methodName = cctMethodNode.getMethodName();

            int modifiedMethodBranchCount = 0;

            for (String labelName : modifiedMethodNameToLabelToBranchCountMap.get(methodName).keySet()) {
                modifiedMethodBranchCount += modifiedMethodNameToLabelToBranchCountMap.get(methodName).get(labelName);
            }

            modifiedMethodNameToBranchCountMap.put(methodName, modifiedMethodBranchCount);

        }

        for (CCTMethodNode cctMethodNode : originalCCTClassNode.getCCTMethodNodeList()) {

            String methodName = cctMethodNode.getMethodName();

            int originalMethodBranchCount = originalMethodNameToBranchCountMap.get(methodName);

            int modifiedMethodBranchCount = 0;

            if (modifiedMethodNameToBranchCountMap.containsKey(methodName)) {
                modifiedMethodBranchCount = modifiedMethodNameToBranchCountMap.get(methodName);
            }

            int branchCoveragePercentage = -1;

            if (originalMethodBranchCount != 0) {
                branchCoveragePercentage = (int) (1.0 * modifiedMethodBranchCount / originalMethodBranchCount * 100);
            }

            if (branchCoveragePercentage != -1) {
                System.out.format("%1$-16s%2$-8s\n", methodName + ":", "%" + branchCoveragePercentage);
            } else {
                System.out.format("%1$-16s%2$-8s\n", methodName + ":", "N/A");
            }

        }

    }


}
