package juc;

import java.util.concurrent.*;

public class FutureTaskDemo {
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        FutureTask res = new FutureTask(()->{
            TimeUnit.MILLISECONDS.sleep(5000);
            return "stop";
        });
        new Thread(res).start();
        try {
            System.out.println(res.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
