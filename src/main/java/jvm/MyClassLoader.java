package jvm;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        System.out.println("in myClassLoader");
        //File file = new File("/Users/zxz/IdeaProjects/mashibing/target/classes", name.concat(".class"));
        File file = new File("/Users/zxz/IdeaProjects/test/target/classes/", name.concat(".class"));
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            int b = 0;
            while ((b = fis.read()) != -1) {
                bs.write(b);
            }
            byte[] bytes = bs.toByteArray();
            bs.close();
            fis.close();

            return defineClass(name, bytes, 0, bytes.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }

    public static void main(String[] args) {
        ClassLoader myClassLoader = new MyClassLoader();
        try {
            Class clazz = myClassLoader.loadClass("classLoaderDemo");

            Method method = clazz.getDeclaredMethod("m");
            Object object = clazz.newInstance();
            method.invoke(object);



            System.out.println(object.getClass().getClassLoader());
            System.out.println(myClassLoader.getClass().getClassLoader());
            System.out.println(myClassLoader.getParent());
            System.out.println(getSystemClassLoader());

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
