package ru.otus.l51;

import com.sun.management.GarbageCollectorMXBean;

import java.lang.management.ManagementFactory;
import java.util.*;


public class GCTest {
    static private long youngGenCount = 0;
    static private long youngGenTime = 0;
    static private long oldGenCount = 0;
    static private long oldGenTime = 0;
    static private int minute = 1;


    public static void main(String[] args) throws InterruptedException {
        int size = 30 * 1000 * 1000;

        Set<String> youngGenGCNames = new HashSet<>();
        youngGenGCNames.add("G1 Young Generation");
        youngGenGCNames.add("ParNew");
        youngGenGCNames.add("PS Scavenge");

        Set<String> oldGenGCNames = new HashSet<>();
        oldGenGCNames.add("G1 Old Generation");
        oldGenGCNames.add("ConcurrentMarkSweep");
        oldGenGCNames.add("PS MarkSweep");


        List<GarbageCollectorMXBean> mxbeans = ManagementFactory.getPlatformMXBeans(com.sun.management.GarbageCollectorMXBean.class);

        Timer timer = new Timer();

        GCStat oldGenStat = new GCStat("Old generation GC");
        GCStat newGenStat = new GCStat("New generation GC");

        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println(minute + " minute: ");
                for (com.sun.management.GarbageCollectorMXBean gc : mxbeans) {
                    // Get the standard attribute "CollectionCount"
                    long count = gc.getCollectionCount();
                    long time = gc.getCollectionTime();

                    if (oldGenGCNames.contains(gc.getName())) {
                        oldGenStat.print(count, time);
                        oldGenStat.setCount(count);
                        oldGenStat.setTime(time);
                    } else {
                        newGenStat.print(count, time);
                        newGenStat.setCount(count);
                        newGenStat.setTime(time);
                    }

                }
                minute++;
            }
        }, 60000, 60000);


        ArrayList<Object> arrayList = new ArrayList<>();
        int shift = 0;
        for (int i = 0; i < size; i++) {
            arrayList.add(new Object());
            if (i % 4 == 0) {
                arrayList.remove(i - shift);
                shift++;
            }
            if (i > 0 && i % 100000 == 0) {
                arrayList.remove(0);
                shift++;
            }

            if (i % 100000 == 0) {
                //System.out.println(i);
                Thread.sleep(1000);
            }
        }

    }
}
