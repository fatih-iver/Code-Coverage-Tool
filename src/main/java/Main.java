import core.CCTClassVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.util.TraceClassVisitor;
import tree.CCTClassTransformer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {

    public static void main(String[] args) throws IOException {

        /*

        System.out.println("Core API - Code Coverage Tool - Start");

        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        File userDirectory = new File(System.getProperty("user.dir"));

        File targetDirectory = new File(userDirectory, "target");

        if (!targetDirectory.exists())
            if (!targetDirectory.mkdir())
                System.out.println("target directory cannot be created");

        File generatedClassesDirectory = new File(targetDirectory, "generated-classes");

        if (!generatedClassesDirectory.exists())
            if (!generatedClassesDirectory.mkdir())
                System.out.println("generated-classes directory cannot be created");

        File calculatorClass = new File(generatedClassesDirectory, "Calculator");

        PrintWriter printWriter = new PrintWriter(new FileOutputStream(calculatorClass)));

        TraceClassVisitor traceClassVisitor = new TraceClassVisitor(classWriter, printWriter);

        CCTClassVisitor cctClassVisitor = new CCTClassVisitor(Opcodes.ASM8, traceClassVisitor);

        ClassReader classReader = new ClassReader("Calculator");

        classReader.accept(cctClassVisitor, 0);

        byte[] bytes = classWriter.toByteArray();

        System.out.println("Core API - Code Coverage Tool - End");

        */

        //-------------------------------------------------------------------------------------------

        System.out.println("Tree API - Code Coverage Tool - Start");

        ClassNode classNode = new ClassNode(Opcodes.ASM8);

        ClassReader classReader = new ClassReader("Calculator");

        classReader.accept(classNode, 0);

        CCTClassTransformer cctClassTransformer = new CCTClassTransformer();

        cctClassTransformer.transform(classNode);

        ClassWriter classWriter = new ClassWriter(0);

        classNode.accept(classWriter);

        byte[] byteArray = classWriter.toByteArray();

        System.out.println("Tree API - Code Coverage Tool - End");


    }

}
