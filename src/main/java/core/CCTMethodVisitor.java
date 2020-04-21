package core;

import cct.CCTMapper;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import static org.objectweb.asm.Opcodes.*;

public class CCTMethodVisitor extends MethodVisitor {

    private String methodName;

    public CCTMethodVisitor(String methodName, int api, MethodVisitor methodVisitor)
    {
        super(api, methodVisitor);
        this.methodName = methodName;
    }


    @Override
    public void visitLineNumber(int line, Label start) {
        mv.visitLdcInsn(methodName + ":" + line);
        mv.visitMethodInsn(INVOKESTATIC, "cct/CCTMapper", "map", "(Ljava/lang/String;)V", false);
        super.visitLineNumber(line, start);
    }

    @Override
    public void visitInsn(int opcode) {
        if ((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW) {
            mv.visitLdcInsn(methodName);
            mv.visitMethodInsn(INVOKESTATIC, "cct/CCTMapper", "print", "(Ljava/lang/String;)V", false);
        }
        super.visitInsn(opcode);
    }

}
