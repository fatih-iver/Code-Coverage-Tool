package core;

import org.objectweb.asm.MethodVisitor;

public class CCTMethodVisitor extends MethodVisitor {

    public CCTMethodVisitor(int api, MethodVisitor methodVisitor) {
        super(api, methodVisitor);
    }

}
