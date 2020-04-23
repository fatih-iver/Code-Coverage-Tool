package fun.fiver.cct;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class CCTClassFileTransformer implements ClassFileTransformer {

    private final String className;

    public CCTClassFileTransformer(String className) {
        this.className = className;
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (className.equals(this.className)) {
            try {
                return CCTTransformer.transformClass(this.className);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return classfileBuffer;
    }
}
