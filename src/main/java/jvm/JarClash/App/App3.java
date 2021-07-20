package jvm.JarClash.App;

import com.cubd.classloader.c1;
import com.cubd.classloader.c2;
import jvm.JarClash.ClassLoader.MyClassLoader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class App3 {
    public static void main(String[] args) {
        ClassLoader loader1 = new MyClassLoader("/Users/zxz/IdeaProjects/mashibing/src/main/resources/","c1.jar");
        ClassLoader loader2 = new MyClassLoader("/Users/zxz/IdeaProjects/mashibing/src/main/resources/","c2.jar");

        try {
            Class clazz1 = loader1.loadClass("com.cubd.classloader.c1");
            Class clazz2 = loader2.loadClass("com.cubd.classloader.c2");

            Object a = clazz1.newInstance();
            Object b = clazz2.newInstance();

            Method m1 = clazz1.getDeclaredMethod("printHero");
            Method m2 = clazz2.getDeclaredMethod("printHero");

            m1.invoke(a);
            m2.invoke(b);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }
}
