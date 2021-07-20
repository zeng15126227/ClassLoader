package juc.interview2;

import java.util.concurrent.locks.LockSupport;

public class interview_v3 {

    static Thread t1,t2 = null;

    public static void main(String[] args) {
        int[] a = {1,2,3,4,5,6,7,8,9,10,
        11,12,13,14,15,16,17,18,19,20,
        21,22,23,24,25,26};
        char[] b = "ABCDEFGHIGKLMNOPQRSTUVWXYZ".toCharArray();


        t1 = new Thread(()->{
            for(int t:a){
                System.out.print(t);
                LockSupport.unpark(t2);
                LockSupport.park();
            }
        },"thread-1");

        t2 = new Thread(()->{
            for(char t:b){
                LockSupport.park();
                System.out.print(t);
                LockSupport.unpark(t1);

            }
        },"thread-2");


        t1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();

    }
}
