package jvm.JarClash.App;

import com.cubd.classloader.*;

public class App1 {
    public static void main(String[] args) {
        new c1().printHero();
        new c2().printHero();
    }
}
