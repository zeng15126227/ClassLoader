package juc2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class TPETerminate {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(getIds());
    }

    public static List<Integer> getIds() throws InterruptedException {
        List<Integer> list = new ArrayList<>();
        ExecutorService executorService = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        });
        executorService.submit(()->{
            System.out.println("线程开始");
            try {
                TimeUnit.MILLISECONDS.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list.add(1);
            System.out.println("线程结束");
        });
        //executorService.shutdown();
        executorService.awaitTermination(1,TimeUnit.MINUTES);
        return list;
    }
}
