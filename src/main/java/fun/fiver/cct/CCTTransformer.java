package fun.fiver.cct;

import fun.fiver.core.CCTClassVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.IOException;

public class CCTTransformer {

    public static byte[] transformClass(String className) throws IOException {

        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        CCTClassVisitor cctClassVisitor = new CCTClassVisitor(Opcodes.ASM8, classWriter);

        ClassReader classReader = new ClassReader(className);

        classReader.accept(cctClassVisitor, 0);

        return classWriter.toByteArray();

    }


}
