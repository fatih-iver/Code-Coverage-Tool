package tree;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.tree.ClassNode;

public class CCTClassNode extends ClassNode {

    public CCTClassNode(ClassVisitor cv) {
        super();
        this.cv = cv;
    }

    public CCTClassNode(int api, ClassVisitor cv) {
        super(api);
        this.cv = cv;
    }

    @Override
    public void visitEnd() {
        //TODO
        cv.visitEnd();
    }
}
