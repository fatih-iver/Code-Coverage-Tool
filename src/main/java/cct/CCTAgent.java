package cct;

import java.lang.instrument.Instrumentation;

public class CCTAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new CCTClassFileTransformer());
        Runtime.getRuntime().addShutdownHook(new Thread(CCTMapper::printResults));
    }
}
