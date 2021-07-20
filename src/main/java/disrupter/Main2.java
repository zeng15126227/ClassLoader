package disrupter;

import com.lmax.disruptor.EventTranslatorTwoArg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.Executors;

public class Main2 {
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

        EventTranslatorTwoArg<WineEvent,String,String> translator = new EventTranslatorTwoArg<WineEvent, String, String>() {
            @Override
            public void translateTo(WineEvent event, long sequence, String arg0, String arg1) {
                event.setName(arg0);
                event.setArea(arg1);
            }
        };

        ringBuffer.publishEvent(translator,"武陵春","中州");
    }
}
