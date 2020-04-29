package fun.fiver.cct;

import fun.fiver.core.CCTClassVisitor;
import fun.fiver.nodes.CCTClassNode;
import fun.fiver.nodes.CCTInstructionNode;
import fun.fiver.nodes.CCTLabelNode;
import fun.fiver.nodes.CCTMethodNode;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

import java.io.IOException;

public class CCTCollector {

    private static CCTClassNode originalCCTClassNode;
    private static CCTClassNode modifiedCCTClassNode;

    public static CCTClassNode activeCCTClassNode;

    public static void initializeFor(String className){
        originalCCTClassNode = new CCTClassNode(className);
        modifiedCCTClassNode = new CCTClassNode(className);

    }

    public static CCTClassNode getOriginalCCTClassNode(){
        return originalCCTClassNode;
    }

    private static void setOriginalCCTClassNodeAsActive(){
        activeCCTClassNode = originalCCTClassNode;
    }

    public static CCTClassNode getModifiedCCTClassNode(){
        return modifiedCCTClassNode;
    }

    private static void setModifiedCCTClassNodeAsActive(){
        activeCCTClassNode = modifiedCCTClassNode;
    }

    public static void collectForOriginalClass(){

        setOriginalCCTClassNodeAsActive();

        CCTClassVisitor cctClassVisitor = new CCTClassVisitor(Opcodes.ASM8);

        ClassReader classReader;

        String className = activeCCTClassNode.getClassName();

        try {
            classReader = new ClassReader(className);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        classReader.accept(cctClassVisitor, 0);

        setModifiedCCTClassNodeAsActive();

    }

    public static void visitMethodStart(String methodName){
        activeCCTClassNode.addCCTMethodNode(new CCTMethodNode(methodName));
    }

    public static void visitLabel(String labelName){
        activeCCTClassNode.getActiveCCTMethodNode().addCCTLabelNode(new CCTLabelNode(labelName));
    }

    public static void visitLineNumber(String lineNumber){
        visitLineNumber(Integer.parseInt(lineNumber));
    }

    public static void visitLineNumber(int lineNumber){
        activeCCTClassNode.getActiveCCTMethodNode().getActiveCCTLabelNode().setLineNumber(lineNumber);
    }

    public static void visitInstruction(String opcode){
        visitInstruction(Integer.parseInt(opcode));
    }

    public static void visitInstruction(int opcode){
        activeCCTClassNode.getActiveCCTMethodNode().getActiveCCTLabelNode().addCCTInstructionNode(new CCTInstructionNode(opcode));
    }

    public static void visitMethodEnd(){
        activeCCTClassNode.getActiveCCTMethodNode().removeLastAddedLabel();
        activeCCTClassNode.getActiveCCTMethodNode().findMissingLineNumbers();
    }

    private static void printCollectedInformationFor(CCTClassNode cctClassNode){

        if(cctClassNode == originalCCTClassNode){
            System.out.println("******************ORIGINAL*********************");
        } else {
            System.out.println("------------------MODIFIED---------------------");
        }

        System.out.println(cctClassNode.getClassName());

        for(CCTMethodNode cctMethodNode: cctClassNode.getCCTMethodNodeList()){

            System.out.println();
            System.out.println(cctMethodNode.getMethodName());

            for(CCTLabelNode cctLabelNode: cctMethodNode.getCCTLabelNodeList()){

                System.out.println();
                System.out.println(cctLabelNode.getLabel() + " " + cctLabelNode.getLineNumber());

                for(CCTInstructionNode cctInstructionNode: cctLabelNode.getCctInstructionNodeList()){

                    System.out.println(cctInstructionNode.getOpcode());

                }

            }

            System.out.println();

        }


    }

    public static void printCollectedInformation(){
        printCollectedInformationFor(originalCCTClassNode);
        printCollectedInformationFor(modifiedCCTClassNode);
    }


}
