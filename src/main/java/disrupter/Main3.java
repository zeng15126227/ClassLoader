package disrupter;

import com.lmax.disruptor.EventTranslatorTwoArg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.io.IOException;
import java.util.concurrent.Executors;

public class Main3 {
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

        for(int i=0;i<5;i++){
            ringBuffer.publishEvent((event,sequence,name,area)->{
                event.setName(name);
                event.setArea(area);
            },"武陵春","中州");
        }
        System.in.read();
    }
}
