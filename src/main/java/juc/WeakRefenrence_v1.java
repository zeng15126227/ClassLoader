package juc;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.LinkedList;
import java.util.List;

public class WeakRefenrence_v1 {

    private static final List<Object> LIST = new LinkedList<>();
    private static final ReferenceQueue<M> QUEUE = new ReferenceQueue<>();

    public static void main(String[] args) {
        PhantomReference<M> phantomReference = new PhantomReference<>(new M(),QUEUE);

        new Thread(()->{
            while(true){
                LIST.add(new byte[1024*1024]);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                System.out.println(phantomReference.get());
            }
        }).start();

        new Thread(()->{
            while (true){
                Reference<? extends M> poll = QUEUE.poll();
                if(poll!=null){
                    System.out.println("虚引用被回收"+poll);
                }
            }
        }).start();
    }

    static class M{
        @Override
        protected void finalize() throws Throwable {
            System.out.println("in finalize ...");;
        }
    }
}
