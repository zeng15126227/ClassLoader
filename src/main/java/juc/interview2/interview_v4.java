package juc.interview2;

public class interview_v4 {

    static Thread t1,t2 = null;

    public static void main(String[] args) {
        int[] a = {1,2,3,4,5,6,7,8,9,10,
                11,12,13,14,15,16,17,18,19,20,
                21,22,23,24,25,26};
        char[] b = "ABCDEFGHIGKLMNOPQRSTUVWXYZ".toCharArray();

        Object o = new Object();

        t1 = new Thread(()->{
            synchronized (o){
                for(int t:a){
                    System.out.println(t);
                    o.notify();
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        t2 = new Thread(()->{
            synchronized (o){
                for(char t:b){
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(t);
                    o.notify();
                }
            }
        });

        t2.start();
        t1.start();
    }
}
