package juc.interview2;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static juc.interview2.interview_v5.ready;

public class interview_v6 {

    static BlockingQueue<Integer> q1 = new ArrayBlockingQueue(1);
    static BlockingQueue<Integer> q2 = new ArrayBlockingQueue(1);
    static Thread t1,t2 = null;

    public static void main(String[] args) {

        int[] a = {1,2,3,4,5,6,7,8,9,10,
                11,12,13,14,15,16,17,18,19,20,
                21,22,23,24,25,26};
        char[] b = "ABCDEFGHIGKLMNOPQRSTUVWXYZ".toCharArray();
        try {
            q1.put(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t1 = new Thread(()->{
            for(int t:a){
                try {
                    q2.take();
                    System.out.println(t);
                    q1.put(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t1");

        t2 = new Thread(()->{
            for(char t:b){

                try {
                    q1.take();
                    System.out.println(t);
                    q2.put(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t2");

        t1.start();
        t2.start();

    }
}
