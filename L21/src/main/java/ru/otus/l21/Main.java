package ru.otus.l21;

import org.openjdk.jol.info.ClassLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.function.Supplier;


/**
 * VM options -Xmx512m -Xms512m
 * -XX:+UseCompressedOops //on
 * -XX:-UseCompressedOops //off
 * <p>
 * Runtime runtime = Runtime.getRuntime();
 * long mem = runtime.totalMemory() - runtime.freeMemory();
 * <p>
 * System.gc()
 * <p>
 * jconsole, connect to pid
 */
@SuppressWarnings({"RedundantStringConstructorCall", "InfiniteLoopStatement"})
public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) throws InterruptedException {

        System.out.println("String: " + calcSize(() -> new String()));
        //System.out.println(ClassLayout.parseClass(String.class).toPrintable());
        System.out.println("Instrumentation: " + ObjectSizeFetcher.getObjectSize(new String()));

        System.out.println("Arraylist: " +calcSize(() -> new ArrayList()));
        System.out.println("Instrumentation: " + ObjectSizeFetcher.getObjectSize(new ArrayList()));

        System.out.println("ArrayList(10): " + calcSize(() -> new ArrayList(10)));
        System.out.println("Instrumentation: " + ObjectSizeFetcher.getObjectSize(new ArrayList(10)));
//        System.out.println("ArrayList(20): " + calcSize(() -> new ArrayList(20)));
//        System.out.println("ArrayList(30): " + calcSize(() -> new ArrayList(30)));
//        System.out.println("ArrayList(100): " + calcSize(() -> new ArrayList(100)));
    }

    private static long calcSize(Supplier supplier) throws InterruptedException {
        logger.info("pid: {}", ManagementFactory.getRuntimeMXBean().getName());

        int size = 1_000_000;

        long mem = getMem();
        logger.info("Mem: {}", mem);

        Object[] array = new Object[size];

        long mem2 = getMem();
        logger.info("Ref size: {}", (mem2 - mem) / array.length);

        for (int i = 0; i < array.length; i++) {
            array[i] = supplier.get();
        }

        long mem3 = getMem();
        long objectSize = (mem3 - mem2) / array.length;
        logger.info("Element size for {} is {}", supplier.get().getClass().getName(), objectSize);

        array = null;

        return objectSize;
    }

    private static long getMem() throws InterruptedException {
        System.gc();
        Thread.sleep(10);
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
}