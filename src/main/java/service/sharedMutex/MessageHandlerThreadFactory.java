package service.sharedMutex;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class MessageHandlerThreadFactory implements ThreadFactory {
    public static final MessageHandlerThreadFactory FACTORY = new MessageHandlerThreadFactory();
    private final AtomicInteger threadCount = new AtomicInteger(0);

    private MessageHandlerThreadFactory() {
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, String.format("MessageHandlerThread-%d", threadCount.incrementAndGet()));
    }
}
