package fun.fiver.core;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public class CCTClassVisitor extends ClassVisitor {

    public CCTClassVisitor(int api) {
        super(api);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        return new CCTMethodVisitor(api, name);
    }

}
