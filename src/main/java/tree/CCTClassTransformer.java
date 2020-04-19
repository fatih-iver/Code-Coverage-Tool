package tree;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.List;

public class CCTClassTransformer extends ClassTransformer {

    public static ClassTransformer DefaultTransformer = new CCTClassTransformer();

    public CCTClassTransformer() {
        super(null);
    }

    public CCTClassTransformer(ClassTransformer ct) {
        super(ct);
    }

    @Override
    public void transform(ClassNode cn) {

        List<MethodNode> methodNodeList = cn.methods;

        for(MethodNode methodNode: methodNodeList) {
            CCTMethodTransformer.DefaultTransformer.transform(methodNode);
        }

        super.transform(cn);
    }
}
