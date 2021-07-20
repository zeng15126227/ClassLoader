package juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLock_v1 {

    Lock lock = new ReentrantLock();

    void m1() {
        lock.lock();
        try {

            for (int i = 0; i < 10; i++) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    void m2() {
        lock.lock();
        try {
            System.out.println("m2...");
        } finally {
            lock.unlock();
        }
    }


    public static void main(String[] args) {
        ReentrantLock_v1 r1 = new ReentrantLock_v1();
        new Thread(r1::m1).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(r1::m2).start();
    }
}




















