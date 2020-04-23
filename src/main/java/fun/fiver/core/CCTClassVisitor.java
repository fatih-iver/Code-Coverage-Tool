package fun.fiver.core;

import fun.fiver.cct.CCTCollector;
import org.objectweb.asm.*;

import java.util.logging.Logger;

public class CCTClassVisitor extends ClassVisitor {


    public CCTClassVisitor(int api) {
        super(api);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        return new CCTMethodVisitor(api, name);
    }

    @Override
    public void visitEnd() {
        CCTCollector.finishCollecting();
    }
}
