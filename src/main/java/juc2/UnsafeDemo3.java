package juc2;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class UnsafeDemo3 {

    static final Unsafe UNSAFE;
    static final long A_OFFSET;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            UNSAFE = (Unsafe) theUnsafe.get(null);
            Class<UnsafeDemo3> unsafeDemo3Class = UnsafeDemo3.class;
            A_OFFSET = UNSAFE.staticFieldOffset(unsafeDemo3Class.getDeclaredField("a"));
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }


    static int a = 0;

    public static void inc() {
        for (; ; ) {
            int t = a;
            if (UNSAFE.compareAndSwapInt(UnsafeDemo3.class, A_OFFSET, t, ++t)) {
                break;
            }
            Thread.yield();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            executorService.execute(() -> inc());
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println(a);
    }
}
