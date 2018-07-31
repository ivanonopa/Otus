package ru.otus.l41;

public class TestRunner {
    public static void main(String[] args) {
        TestFramework tf = new TestFramework();
        tf.run(AnnotationsTest.class);
    }
}
