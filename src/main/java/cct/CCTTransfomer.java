package cct;

import core.CCTClassVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class CCTTransfomer {

    public static byte[] transformClass(String name) throws IOException {

        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        CCTClassVisitor cctClassVisitor = new CCTClassVisitor(Opcodes.ASM8, classWriter);

        ClassReader classReader = new ClassReader("Calculator");

        classReader.accept(cctClassVisitor, 0);

        return classWriter.toByteArray();

    }



}
