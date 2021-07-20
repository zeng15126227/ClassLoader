package juc2;

import java.util.concurrent.TimeUnit;

public class ThreadStop {
    static volatile boolean flag = true;
    static long res = 0;
    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{

            while(flag){
                res++;
            }

        }).start();

        TimeUnit.MILLISECONDS.sleep(1000);
        flag=false;
        System.out.println(res);
    }
}
