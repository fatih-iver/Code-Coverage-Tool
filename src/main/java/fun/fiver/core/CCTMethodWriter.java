package fun.fiver.core;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.INVOKESTATIC;

public class CCTMethodWriter extends MethodVisitor {

    private final String methodName;

    public CCTMethodWriter(String methodName, int api, MethodVisitor methodVisitor) {
        super(api, methodVisitor);
        this.methodName = methodName;
    }


    @Override
    public void visitLineNumber(int line, Label start) {
        mv.visitLdcInsn(methodName + ":" + line);
        mv.visitMethodInsn(INVOKESTATIC, "fun/fiver/cct/CCTMapper", "map", "(Ljava/lang/String;)V", false);
        super.visitLineNumber(line, start);
    }

    @Override
    public void visitInsn(int opcode) {
        /*
        if ((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW) {
            mv.visitLdcInsn(methodName);
            mv.visitMethodInsn(INVOKESTATIC, "cct/CCTMapper", "print", "(Ljava/lang/String;)V", false);
        }
         */
        super.visitInsn(opcode);
    }

}
