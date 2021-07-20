package juc;

import java.util.concurrent.CountDownLatch;

public class CountDownLatch_v1 {

    public static void main(String[] args) {
        Thread[] threads = new Thread[100];
        CountDownLatch latch = new CountDownLatch(threads.length);
        for(int i=0;i<threads.length;i++){
            threads[i] = new Thread(()->{
                int res=0;
                for(int j=0;j<1000;j++){
                    res+=j;
                }
                latch.countDown();
            });
        }
        for(int i=0;i<threads.length;i++){
            threads[i].start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
