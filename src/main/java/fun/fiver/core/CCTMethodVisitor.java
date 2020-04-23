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
        CCTCollector.addMethod(methodName);
    }

    @Override
    public void visitLabel(Label label) {
        CCTCollector.addLabel(methodName, label.toString());
    }

    @Override
    public void visitLineNumber(int line, Label start) {
        CCTCollector.matchLabelToLineNumber(methodName, start.toString(), line);
    }

    @Override
    public void visitEnd() {
        CCTCollector.finishCollectingFor(methodName);
    }
}
