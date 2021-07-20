package disrupter;

import com.lmax.disruptor.EventHandler;

public class WineEventHandler implements EventHandler {

    public static long count = 0;

    @Override
    public void onEvent(Object event, long sequence, boolean endOfBatch) throws Exception {
        count++;
        System.out.println(Thread.currentThread().getName());
        System.out.println(sequence);
        System.out.println(endOfBatch);
        System.out.println((WineEvent)event);
    }
}
