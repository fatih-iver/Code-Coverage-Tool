package fun.fiver.core;


import fun.fiver.cct.CCTCollector;

import org.objectweb.asm.*;

public class CCTMethodVisitor extends MethodVisitor {

    private String methodName;

    public CCTMethodVisitor(int api, String methodName) {
        super(api);
        this.methodName = methodName;
    }

    @Override
    public void visitCode() {
        CCTCollector.visitMethodStart(methodName);
    }

    @Override
    public void visitLabel(Label label) {
        CCTCollector.visitLabel(label.toString());
    }

    @Override
    public void visitLineNumber(int line, Label start) {
        CCTCollector.visitLineNumber(line);
     }

    @Override
    public void visitInsn(int opcode) {
        CCTCollector.visitInstruction(opcode);
    }


    @Override
    public void visitIntInsn(int opcode, int operand) {
        CCTCollector.visitInstruction(opcode);
    }


    @Override
    public void visitVarInsn(int opcode, int var) {
        CCTCollector.visitInstruction(opcode);
    }


    @Override
    public void visitTypeInsn(int opcode, String type) {
        CCTCollector.visitInstruction(opcode);
    }


    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
        CCTCollector.visitInstruction(opcode);
    }


    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        CCTCollector.visitInstruction(opcode);
    }


    @Override
    public void visitJumpInsn(int opcode, Label label) {
        CCTCollector.visitInstruction(opcode);
    }

    @Override
    public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
        CCTCollector.visitInstruction(Opcodes.INVOKEDYNAMIC);
    }

    @Override
    public void visitLdcInsn(Object value) {
        CCTCollector.visitInstruction(Opcodes.LDC);
    }

    @Override
    public void visitIincInsn(int var, int increment) {
        CCTCollector.visitInstruction(Opcodes.IINC);
    }

    @Override
    public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
        CCTCollector.visitInstruction(Opcodes.TABLESWITCH);
    }

    @Override
    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
        CCTCollector.visitInstruction(Opcodes.LOOKUPSWITCH);
    }

    @Override
    public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
        CCTCollector.visitInstruction(Opcodes.MULTIANEWARRAY);
    }


    @Override
    public void visitEnd() {
        CCTCollector.visitMethodEnd();
    }
}
