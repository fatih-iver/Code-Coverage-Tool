package cct;

public class CCTClassLoader extends ClassLoader {

    public CCTClassLoader(ClassLoader parent) {
        super(parent);
    }

    public Class defineClass(String name, byte[] bytes) {
        return defineClass(name, bytes, 0, bytes.length);
    }

}
