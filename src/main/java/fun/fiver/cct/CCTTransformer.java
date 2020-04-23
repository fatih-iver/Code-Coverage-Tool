package fun.fiver.cct;

import fun.fiver.core.CCTClassWriter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.IOException;

public class CCTTransformer {

    public static byte[] transformClass(String className) throws IOException {

        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        CCTClassWriter cctClassWriter = new CCTClassWriter(Opcodes.ASM8, classWriter);

        ClassReader classReader = new ClassReader(className);

        classReader.accept(cctClassWriter, 0);

        return classWriter.toByteArray();

    }


}
