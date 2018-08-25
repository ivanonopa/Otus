package ru.otus.l51;

public class GCStat {
    private long count;
    private long time;
    private String name;

    public String getName() {
        return name;
    }

    public GCStat(String name) {
        this.name = name;
        count = 0;
        time = 0;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

//    public void print(long count, long time){
//        System.out.println(name + " count=" + (count - this.count) + ", time=" + (time - this.time));
//    }

    @Override
    public String toString() {
        return name + ": count=" + count +
                ", time=" + time ;
    }
}
