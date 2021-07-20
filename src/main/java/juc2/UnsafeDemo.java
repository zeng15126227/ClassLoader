package juc2;

import java.util.concurrent.atomic.AtomicInteger;

public class UnsafeDemo {
    static AtomicInteger atomicInteger = new AtomicInteger(1);
    public static void main(String[] args) {
        int snap = atomicInteger.get();
        new Thread(()->{
           atomicInteger.incrementAndGet();
        }).start();
        System.out.println(snap);
        System.out.println(atomicInteger.get());
    }
}
