package test;

import java.util.concurrent.TimeUnit;

public class jstack1 {
    public static void main(String[] args) throws InterruptedException {

        Object o = new Object();

        new Thread(()->{
            synchronized (o){
                System.out.println("t1进入");
                try {
                    TimeUnit.MINUTES.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t1推出");
            }
        },"t1").start();

        TimeUnit.MILLISECONDS.sleep(1000);

        for (int i = 2; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (o){
                        try {
                            TimeUnit.MILLISECONDS.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName());
                    }
                }
            },"t"+i).start();
        }
        System.out.println("main结束");
    }
}
