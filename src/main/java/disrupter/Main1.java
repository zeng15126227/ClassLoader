package disrupter;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.Executors;

public class Main1 {
    public static void main(String[] args) {
        //创建eventFactory
        WineEventFactory wineEventFactory = new WineEventFactory();
        int bufferSize = 1024;
        //创建disruptor
        Disruptor<WineEvent> wineEventDisruptor = new Disruptor<WineEvent>(wineEventFactory, bufferSize, Executors.defaultThreadFactory());
        //定义disruptor处理event的handler
        wineEventDisruptor.handleEventsWith(new WineEventHandler());
        //启动disruptor
        wineEventDisruptor.start();
        //获取环形缓冲区
        RingBuffer<WineEvent> ringBuffer = wineEventDisruptor.getRingBuffer();
        //下一个插入sequence
        long sequence = ringBuffer.next();
        try{
            WineEvent wineEvent = ringBuffer.get(sequence);
            wineEvent.setName("武陵春");
            wineEvent.setArea("中州");
        }finally {
            ringBuffer.publish(sequence);
        }
    }
}
