package disrupter;

import com.lmax.disruptor.EventFactory;

public class WineEventFactory implements EventFactory<WineEvent> {
    @Override
    public WineEvent newInstance() {
        return new WineEvent();
    }
}
