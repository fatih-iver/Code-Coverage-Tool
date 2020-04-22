package cct;

import java.lang.instrument.Instrumentation;

public class CCTAgent {

    public static void premain(String agentArgs, Instrumentation inst)
    {
        System.out.println("premain-start");
        inst.addTransformer(new CCTClassFileTransformer());
        System.out.println("premain-end");
    }
}
