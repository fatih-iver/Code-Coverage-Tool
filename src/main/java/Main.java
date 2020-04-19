import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        System.out.println("Code Coverage Tool - Start");

        ClassWriter classWriter = new ClassWriter(0);

        CCTClassVisitor cctClassVisitor = new CCTClassVisitor(Opcodes.ASM8, classWriter);

        ClassReader classReader = new ClassReader("Calculator");

        classReader.accept(cctClassVisitor, 0);

        byte[] byteArray = classWriter.toByteArray();

        System.out.println("Code Coverage Tool - End");


    }

}
