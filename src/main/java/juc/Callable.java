package juc;

import java.util.concurrent.*;

public class Callable {
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        Future res = service.submit(()->{
            try {
                TimeUnit.MILLISECONDS.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "stop";
        });
        try {
            System.out.println(res.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
