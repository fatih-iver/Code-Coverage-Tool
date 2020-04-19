package tree;

import org.objectweb.asm.tree.MethodNode;

public class CCTMethodTransformer extends MethodTransformer{

    public static MethodTransformer DefaultTransformer = new CCTMethodTransformer();

    public CCTMethodTransformer() {
        super(null);
    }

    public CCTMethodTransformer(MethodTransformer mt) {
        super(mt);
    }

    @Override
    public void transform(MethodNode mn) {
        //TODO
        super.transform(mn);
    }
}
