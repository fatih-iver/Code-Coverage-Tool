package cct;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class CCTClassFileTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

        if(className.equals("Calculator")){
            try {
                return CCTTransfomer.transformClass(className);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return classfileBuffer;
    }
}
