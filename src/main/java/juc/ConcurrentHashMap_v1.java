package juc;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConcurrentHashMap_v1 {

    static ConcurrentHashMap<UUID, UUID> map = new ConcurrentHashMap<>();
    //static Map<UUID,UUID> map = Collections.synchronizedMap(new HashMap<>());
    //static Hashtable<UUID,UUID> map = new Hashtable<>();
    static final int count = 1000000;
    static final int threadCount = 100;
    static UUID[] keys = new UUID[count];
    static UUID[] values = new UUID[count];

    static {
        for (int i = 0; i < count; i++) {
            keys[i] = UUID.randomUUID();
            values[i] = UUID.randomUUID();
        }
    }

    static class MyThread extends Thread {
        int start;
        int gap = count / threadCount;

        public MyThread(int start){
            this.start = start;
        };

        @Override
        public void run() {
            for (int i=start;i<start+gap;i++){
                map.put(keys[i],values[i]);
            }
        }
    }


    public static void main(String[] args) {
         long startTime = System.currentTimeMillis();
         Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new MyThread(i*(count/threadCount));
        }
        for(Thread s:threads){
            s.start();
        }
        for (Thread s:threads){
            try {
                s.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime-startTime);
        System.out.println(map.size());


        startTime = System.currentTimeMillis();
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(()->{
                for (int j = 0; j < 1000000 ; j++) {
                    map.get(keys[10]);
                }
            });
        }
        for(Thread s:threads){
            s.start();
        }
        for (Thread s:threads){
            try {
                s.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        endTime = System.currentTimeMillis();
        System.out.println(endTime-startTime);


    }
}
