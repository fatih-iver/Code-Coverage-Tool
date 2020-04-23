package fun.fiver.cct;

import fun.fiver.core.CCTClassVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

import java.io.IOException;
import java.lang.instrument.Instrumentation;

public class CCTAgent {

    public static void premain(String agentArgs, Instrumentation inst) {

        String separator = ";";

        String[] agentArguments = agentArgs.split(separator);

        String fullyQualifiedClassName = agentArguments[0];

        String className = fullyQualifiedClassName.replaceAll("\\.", "/");

        CCTCollector.startCollectingFor(className);

        inst.addTransformer(new CCTClassFileTransformer(className));

        String sourceFilePath = agentArguments[1];

        Runnable shutdownRunnable = () -> CCTReporter.generateReport(fullyQualifiedClassName, sourceFilePath);

        Thread shutdownThread = new Thread(shutdownRunnable);

        Runtime.getRuntime().addShutdownHook(shutdownThread);


    }
}
