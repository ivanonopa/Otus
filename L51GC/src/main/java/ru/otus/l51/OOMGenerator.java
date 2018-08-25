package ru.otus.l51;

import java.util.ArrayList;

public class OOMGenerator {

    private int size;

    public OOMGenerator(int size) {
        this.size = size;
    }

    void fill() throws InterruptedException {
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
