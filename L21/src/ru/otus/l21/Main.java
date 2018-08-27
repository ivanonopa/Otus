package ru.otus.l21;

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
    public static void main(String... args) throws InterruptedException {

        calcSize(() -> new String());

        calcSize(() -> new ArrayList());
        System.out.println("ArrayList(10):");
        calcSize(() -> new ArrayList(10));
        System.out.println("ArrayList(20):");
        calcSize(() -> new ArrayList(20));
        System.out.println("ArrayList(30):");
        calcSize(() -> new ArrayList(30));
        System.out.println("ArrayList(50):");
        calcSize(() -> new ArrayList(100));
    }

    private static long calcSize(Supplier supplier) throws InterruptedException {
        System.out.println("pid: " + ManagementFactory.getRuntimeMXBean().getName());

        int size = 1_000_000;

        long mem = getMem();
        System.out.println("Mem: " + mem);

        Object[] array = new Object[size];

        long mem2 = getMem();
        System.out.println("Ref size: " + (mem2 - mem) / array.length);

        for (int i = 0; i < array.length; i++) {
            array[i] = supplier.get();
        }

        long mem3 = getMem();
        long objectSize = (mem3 - mem2) / array.length;
        System.out.println("Element size for " + supplier.get().getClass().getName() + " is " + objectSize);

        array = null;
        System.out.println("Array is ready for GC");

        return objectSize;
    }

    private static long getMem() throws InterruptedException {
        System.gc();
        Thread.sleep(10);
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
}