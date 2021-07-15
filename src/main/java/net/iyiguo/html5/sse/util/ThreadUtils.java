package net.iyiguo.html5.sse.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ThreadUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadUtils.class);

    private ThreadUtils() {
    }

    public static void sleep(long mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException ie) {
            LOGGER.warn("thread sleep exception. {}", ie);
        }
    }
}
