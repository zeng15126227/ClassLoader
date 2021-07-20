package juc2;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.*;

public class UnsafeDemo4 {

    static final Unsafe UNSAFE;
    static final long A_OFFSET;
    static final long C_OFFSET;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            UNSAFE = (Unsafe) theUnsafe.get(null);
            Class<UnsafeDemo4> unsafeDemo3Class = UnsafeDemo4.class;
            A_OFFSET = UNSAFE.staticFieldOffset(unsafeDemo3Class.getDeclaredField("a"));
            C_OFFSET = UNSAFE.staticFieldOffset(unsafeDemo3Class.getDeclaredField("counter"));
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }


    static volatile int a = 0;
    static volatile int counter = 1;

    public static void lock() {
        for (; ; ) {
            if (UNSAFE.compareAndSwapInt(UnsafeDemo4.class, C_OFFSET, 1, 0)) {
                break;
            }
        }
        //Thread.yield();
    }

    public static void unLock() {
        counter = 1;
    }

    public static void inc() {
        lock();
        a++;
        unLock();
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(2, 5,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            executorService.execute(() -> inc());
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
        long endTime = System.currentTimeMillis();
        System.out.println(endTime-startTime);
        System.out.println(a);
    }
}
