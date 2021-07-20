package jvm.JarClash.ClassLoader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class MyClassLoader extends ClassLoader {

    private HashMap<String, Class> classes = new HashMap();
    private String classpath;
    private String jarName;

    public MyClassLoader(String classpath, String jarName) {
        this.classpath = classpath;
        this.jarName = jarName;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        //不是目标class
        if (!name.startsWith("com.cubd.classloader")) {
            return super.loadClass(name);
        }
        //重复加载
        if (classes.containsKey(name)) {
            return classes.get(name);
        }
        //加载类
        byte[] classDate = new byte[0];
        try {
            classDate = getDate(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (classDate == null) {
            System.out.println("自定义加载类异常");
        } else {
            Class c = defineClass(name, classDate, 0, classDate.length, null);
            classes.put(name, c);
            return c;
        }
        return super.loadClass(name);
    }

    //返回类的字节码
    private byte[] getDate(String className) throws IOException {
        String tmp = className.replaceAll("\\.", "/");
        JarFile jar = new JarFile(classpath + "/" + jarName);
        JarEntry entry = jar.getJarEntry(tmp + ".class");
        InputStream is = jar.getInputStream(entry);
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        int nextValue = is.read();
        while (-1 != nextValue) {
            byteStream.write(nextValue);
            nextValue = is.read();
        }

        return byteStream.toByteArray();
    }
}
