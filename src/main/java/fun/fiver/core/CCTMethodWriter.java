package fun.fiver.core;

import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

public class CCTMethodWriter extends MethodVisitor {

    private final String methodName;
    private boolean isFirstLabelVisited;

    public CCTMethodWriter(String methodName, int api, MethodVisitor methodVisitor) {
        super(api, methodVisitor);
        this.methodName = methodName;
        this.isFirstLabelVisited = false;
    }

    @Override
    public void visitLabel(Label label) {
        super.visitLabel(label);

        if (!isFirstLabelVisited) {
            isFirstLabelVisited = true;
            mv.visitLdcInsn(methodName);
            mv.visitMethodInsn(INVOKESTATIC, "fun/fiver/cct/CCTCollector", "visitMethodStart", "(Ljava/lang/String;)V", false);
        }

        mv.visitLdcInsn(label.toString());
        mv.visitMethodInsn(INVOKESTATIC, "fun/fiver/cct/CCTCollector", "visitLabel", "(Ljava/lang/String;)V", false);

    }


    @Override
    public void visitLineNumber(int line, Label start) {
        super.visitLineNumber(line, start);

        mv.visitLdcInsn(methodName + ":" + line);
        mv.visitMethodInsn(INVOKESTATIC, "fun/fiver/cct/CCTMapper", "map", "(Ljava/lang/String;)V", false);

        mv.visitLdcInsn(String.valueOf(line));
        mv.visitMethodInsn(INVOKESTATIC, "fun/fiver/cct/CCTCollector", "visitLineNumber", "(Ljava/lang/String;)V", false);
    }

    @Override
    public void visitInsn(int opcode) {
        mv.visitLdcInsn(String.valueOf(opcode));
        mv.visitMethodInsn(INVOKESTATIC, "fun/fiver/cct/CCTCollector", "visitInstruction", "(Ljava/lang/String;)V", false);
        super.visitInsn(opcode);
    }


    @Override
    public void visitIntInsn(int opcode, int operand) {
        mv.visitLdcInsn(String.valueOf(opcode));
        mv.visitMethodInsn(INVOKESTATIC, "fun/fiver/cct/CCTCollector", "visitInstruction", "(Ljava/lang/String;)V", false);
        super.visitIntInsn(opcode, operand);
    }


    @Override
    public void visitVarInsn(int opcode, int var) {
        mv.visitLdcInsn(String.valueOf(opcode));
        mv.visitMethodInsn(INVOKESTATIC, "fun/fiver/cct/CCTCollector", "visitInstruction", "(Ljava/lang/String;)V", false);
        super.visitVarInsn(opcode, var);
    }


    @Override
    public void visitTypeInsn(int opcode, String type) {
        mv.visitLdcInsn(String.valueOf(opcode));
        mv.visitMethodInsn(INVOKESTATIC, "fun/fiver/cct/CCTCollector", "visitInstruction", "(Ljava/lang/String;)V", false);
        super.visitTypeInsn(opcode, type);
    }


    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
        mv.visitLdcInsn(String.valueOf(opcode));
        mv.visitMethodInsn(INVOKESTATIC, "fun/fiver/cct/CCTCollector", "visitInstruction", "(Ljava/lang/String;)V", false);
        super.visitFieldInsn(opcode, owner, name, descriptor);
    }


    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        mv.visitLdcInsn(String.valueOf(opcode));
        mv.visitMethodInsn(INVOKESTATIC, "fun/fiver/cct/CCTCollector", "visitInstruction", "(Ljava/lang/String;)V", false);
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
    }


    @Override
    public void visitJumpInsn(int opcode, Label label) {
        mv.visitLdcInsn(String.valueOf(opcode));
        mv.visitMethodInsn(INVOKESTATIC, "fun/fiver/cct/CCTCollector", "visitInstruction", "(Ljava/lang/String;)V", false);
        super.visitJumpInsn(opcode, label);
    }

    @Override
    public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
        mv.visitLdcInsn(String.valueOf(INVOKEDYNAMIC));
        mv.visitMethodInsn(INVOKESTATIC, "fun/fiver/cct/CCTCollector", "visitInstruction", "(Ljava/lang/String;)V", false);
        super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);
    }

    @Override
    public void visitLdcInsn(Object value) {
        mv.visitLdcInsn(String.valueOf(LDC));
        mv.visitMethodInsn(INVOKESTATIC, "fun/fiver/cct/CCTCollector", "visitInstruction", "(Ljava/lang/String;)V", false);
        super.visitLdcInsn(value);
    }

    @Override
    public void visitIincInsn(int var, int increment) {
        mv.visitLdcInsn(String.valueOf(IINC));
        mv.visitMethodInsn(INVOKESTATIC, "fun/fiver/cct/CCTCollector", "visitInstruction", "(Ljava/lang/String;)V", false);
        super.visitIincInsn(var, increment);
    }

    @Override
    public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
        mv.visitLdcInsn(String.valueOf(TABLESWITCH));
        mv.visitMethodInsn(INVOKESTATIC, "fun/fiver/cct/CCTCollector", "visitInstruction", "(Ljava/lang/String;)V", false);
        super.visitTableSwitchInsn(min, max, dflt, labels);
    }

    @Override
    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
        mv.visitLdcInsn(String.valueOf(LOOKUPSWITCH));
        mv.visitMethodInsn(INVOKESTATIC, "fun/fiver/cct/CCTCollector", "visitInstruction", "(Ljava/lang/String;)V", false);
        super.visitLookupSwitchInsn(dflt, keys, labels);
    }

    @Override
    public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
        mv.visitLdcInsn(String.valueOf(MULTIANEWARRAY));
        mv.visitMethodInsn(INVOKESTATIC, "fun/fiver/cct/CCTCollector", "visitInstruction", "(Ljava/lang/String;)V", false);
        super.visitMultiANewArrayInsn(descriptor, numDimensions);
    }


}
