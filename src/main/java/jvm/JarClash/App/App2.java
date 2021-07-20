package jvm.JarClash.App;

import com.cubd.classloader.c1;
import com.cubd.classloader.c2;
import jvm.JarClash.ClassLoader.MyClassLoader;

public class App2 {
    public static void main(String[] args) {
        ClassLoader loader = new MyClassLoader("/Users/zxz/IdeaProjects/mashibing/src/main/resources/","c1.jar");
        Thread.currentThread().setContextClassLoader(loader);
        new c1().printHero();
        loader = new MyClassLoader("/Users/zxz/IdeaProjects/mashibing/src/main/resources/","c2.jar");
        Thread.currentThread().setContextClassLoader(loader);
        new c2().printHero();
    }
}
