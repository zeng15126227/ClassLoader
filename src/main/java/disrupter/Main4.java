package disrupter;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main4 {
    static int count = 0;
    public static void main(String[] args) throws IOException {
        int bufferSize = 1024;
        //创建disruptor
        Disruptor<WineEvent> wineEventDisruptor = new Disruptor<WineEvent>(WineEvent::new, bufferSize, DaemonThreadFactory.INSTANCE);
        //定义disruptor处理event的handler
        wineEventDisruptor.handleEventsWith((event, sequence, endOfBatch) -> {
            count++;
            System.out.println(Thread.currentThread().getName());
            System.out.println(sequence);
            System.out.println(endOfBatch);
            System.out.println((WineEvent) event);
        });
        //启动disruptor
        wineEventDisruptor.start();
        //获取环形缓冲区
        RingBuffer<WineEvent> ringBuffer = wineEventDisruptor.getRingBuffer();

        CyclicBarrier barrier = new CyclicBarrier(50);
        ExecutorService service = Executors.newCachedThreadPool();
        for(int i=0;i<50;i++){
            int finalI = i;
            service.submit(()->{
                System.out.println(String.format("第%s个线程准备就绪",String.valueOf(finalI +1)));
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                for(int j=0;j<100;j++){
                    ringBuffer.publishEvent((event,sequence,name,area)->{
                        System.out.println(Thread.currentThread().getName()+"生产");
                        event.setName(name);
                        event.setArea(area);
                    },"武陵春","中州");
                }
            });
        }
    }
}
