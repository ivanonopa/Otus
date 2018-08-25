package ru.otus.l51;

import com.sun.management.GarbageCollectorMXBean;
import org.slf4j.Logger;

import java.util.*;

public class GCStatTimer extends TimerTask {
    static private int minute = 1;
    private List<GarbageCollectorMXBean> mxbeans;
    GCStat oldGenStat;
    GCStat newGenStat;
    Logger logger;
    Set<String> oldGenGCNames = new HashSet<>(Arrays.asList("G1 Old Generation", "ConcurrentMarkSweep", "PS MarkSweep"));

    public GCStatTimer(List<GarbageCollectorMXBean> mxbeans, GCStat oldGenStat, GCStat newGenStat, Logger logger) {
        this.mxbeans = mxbeans;
        this.oldGenStat = oldGenStat;
        this.newGenStat = newGenStat;
        this.logger = logger;
    }

    public void run() {
        for (com.sun.management.GarbageCollectorMXBean gc : mxbeans) {
            // Get the standard attribute "CollectionCount"
            long count = gc.getCollectionCount();
            long time = gc.getCollectionTime();

            if (oldGenGCNames.contains(gc.getName())) {
                logger.info("GC name={}, count={}, time={} ", oldGenStat.getName(), count - oldGenStat.getCount(), time - oldGenStat.getTime());
                oldGenStat.setCount(count);
                oldGenStat.setTime(time);
            } else {
                logger.info("GC name={}, count={}, time={} ", newGenStat.getName(), count - newGenStat.getCount(), time - newGenStat.getTime());
                newGenStat.setCount(count);
                newGenStat.setTime(time);
            }

        }
        minute++;
    }
}
