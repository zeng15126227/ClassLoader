package juc2;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class SyncVsCAS {

    public static final int THREAD_SIZE = 4;
    public static final long TIMES = (long)10000000;
    public static final Object lock = new Object();
    public static AtomicLong cnt1 = new AtomicLong(0);
    public static long cnt2 = 0L;

    public static void CountCAS() throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();
        CountDownLatch countDownLatch = new CountDownLatch(THREAD_SIZE);
        long start = System.currentTimeMillis();

        for (int i = 0; i < THREAD_SIZE; i ++) {
            executorService.submit(() -> {
                for (long j = 0; j < TIMES; j ++){
                    cnt1.getAndIncrement();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    public static void CountSyn() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        CountDownLatch countDownLatch = new CountDownLatch(THREAD_SIZE);
        long start = System.currentTimeMillis();

        for (int i = 0; i < THREAD_SIZE; i ++) {
            executorService.submit(() -> {
                for (long j = 0L; j < TIMES; j ++) {
                    synchronized (lock) {
                        cnt2++;
                    }
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    public static void main(String[] args) throws InterruptedException {
        CountCAS();
        System.out.println("============");
        CountSyn();
    }
}
