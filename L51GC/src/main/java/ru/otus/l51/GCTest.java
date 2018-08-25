package ru.otus.l51;

import com.sun.management.GarbageCollectorMXBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.util.*;


public class GCTest {

    private static Logger logger = LoggerFactory.getLogger(GCTest.class);

    public static void main(String[] args) throws InterruptedException {
        int size = 30 * 1000 * 1000;

        List<GarbageCollectorMXBean> mxbeans = ManagementFactory.getPlatformMXBeans(com.sun.management.GarbageCollectorMXBean.class);

        GCStat oldGenStat = new GCStat("Old generation GC");
        GCStat newGenStat = new GCStat("New generation GC");

        Timer timer = new Timer();
        timer.schedule(new GCStatTimer(mxbeans,oldGenStat,newGenStat,logger), 0, 60000);

        new OOMGenerator(size).fill();
    }
}
