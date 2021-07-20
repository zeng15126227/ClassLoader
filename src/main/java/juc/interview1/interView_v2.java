package juc.interview1;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class interView_v2 {
    public static void main(String[] args) {
        MyContainer<String> container = new MyContainer<>();
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                for (int j = 0; j <5 ; j++) {
                    System.out.println(container.get());
                }
            },"consumer"+i).start();
        }
        for (int i = 0; i < 2; i++) {
            new Thread(()->{
                for (int j = 0; j <25 ; j++) {
                    container.put(Thread.currentThread().getName());
                }
            },"producer"+i).start();
        }


    }
}

class MyContainer<T> {
    private LinkedList<T> list = new LinkedList();
    private final int MAX = 10;
    private int count = 0;

    private Lock lock = new ReentrantLock();
    private Condition consumer = lock.newCondition();
    private Condition producer = lock.newCondition();

    public void put(T t) {
        lock.lock();
        try {
            while (list.size() == MAX) {
                producer.await();
            }
            list.add(t);
            ++count;
            consumer.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public T get() {
        T t = null;
        lock.lock();
        try {
            while (list.size() == 0) {
                consumer.await();
            }
            t = list.removeFirst();
            --count;
            producer.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return t;
    }
}
