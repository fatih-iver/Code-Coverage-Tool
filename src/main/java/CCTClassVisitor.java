import org.objectweb.asm.ClassVisitor;

public class CCTClassVisitor extends ClassVisitor {

    public CCTClassVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);

    }

}
