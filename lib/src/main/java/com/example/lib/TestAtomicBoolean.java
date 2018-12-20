package com.example.lib;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * test()和test1()效果是一样的，test是通过CAS实现的，test1是通过synchronized实现的。
 */
public class TestAtomicBoolean {
    public static void main(String[] args) throws Exception {
        //测试捕获中断
        for (int i = 0; i < 1000; i++) {
            new Thread("" + i) {
                @Override
                public void run() {
                    test1();
                }
            }.start();
        }
    }

    private static AtomicBoolean atomicBoolean = new AtomicBoolean(false);
    public static void test() {
        //通过compareAndSet直接保证判断+修改为原子性操作
        if (atomicBoolean.compareAndSet(false, true)) {
            //保证只有一个线程可以执行到这里
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("enter" + Thread.currentThread().getName());

        }

    }

    private static volatile boolean state=false;
    public synchronized static void test1() {
        //判断+修改（不是原子性，必须加synchronized才能保证原子性）
        if (state == false) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            state = true;
            System.out.println("enter" + Thread.currentThread().getName());
        }
    }


}
