package ru.otus.l51;

import com.sun.management.GarbageCollectorMXBean;

import java.lang.management.ManagementFactory;
import java.util.*;

public class GCTest {
    public static void main(String[] args) throws InterruptedException {
        int size = 30 * 1000 * 1000;

        List<GarbageCollectorMXBean> mxbeans = ManagementFactory.getPlatformMXBeans(com.sun.management.GarbageCollectorMXBean.class);

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            public void run() {

                for (com.sun.management.GarbageCollectorMXBean gc : mxbeans) {
                    // Get the standard attribute "CollectionCount"
                    long count = gc.getCollectionCount();
                    long time = gc.getCollectionTime();
                    System.out.println(gc.getName() + " count=" + count + ", time=" + time);

                }
            }
        }, 10, 1000);


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
                System.out.println(i);
                Thread.sleep(2000);
            }
        }

    }
}
