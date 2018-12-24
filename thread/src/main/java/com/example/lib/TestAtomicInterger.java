package com.example.lib;

import java.util.concurrent.atomic.AtomicInteger;

/**
 */
public class TestAtomicInterger {
    private static volatile  int n =0;
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10000; i++) {
            new Thread("" + i) {
                @Override
                public void run() {
                    test();
                }
            }.start();
        }

    }

    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void test() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //通过AtomicInteger的incrementAndGet保证共享变量的原子性执行。
        if (atomicInteger.incrementAndGet() < 3) {
            System.out.println("enter"+"-->" + Thread.currentThread().getName());
        }


    }

    private static volatile int state = 0;

    public static void test1() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /**
         * 根据日志可以看出++state是线程不安全的
         * enter0
         * enter7
         * enter5
         */
        if (++state < 3) {
            System.out.println("enter" + Thread.currentThread().getName());
        }
    }


}
