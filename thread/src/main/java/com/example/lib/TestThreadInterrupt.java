package com.example.lib;

/**
 * 线程中断：
 * 1.中断时协作机制，中断作用分为两个角度：阻塞和非阻塞，非阻塞不受影响，阻塞会被唤醒，进入异常流程。
 * 2.处理中断分为4种
 * a：直接声明异常
 * b：捕获异常然后再抛出,捕获了InterruptedException后线程的中断状态就变为了false。（****）
 * c：不能抛出时，调用interupt（），保留中断状态
 * d：不能直接抛出异常时，捕获异常，调用interrupt（）保留中断状态，并转换为运行时异常抛出。
 * 3.中断发生时机
 *  a：线程开启前，中断状态为false，后续的阻塞方法也不会抛出异常。
 *  b：阻塞方法调用前，执行到阻塞方法的时候，会抛出异常。
 *  c：阻塞方法调用后，正在阻塞的方法会抛出异常。
 */
public class TestThreadInterrupt {
    public static void main(String[] args) throws Exception {
        //测试捕获中断
       testInterrupCatch1();
    }

    public  static  Thread testInterrupCatch (){
        //根据日志可以发现调用interrupt（）后线程中断状态为true，
        // 但是捕获到中断异常后，中断状态为false。
        Thread interupThread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                    //isInterrupted2:false
                    System.out.println("isInterrupted2:"+Thread.currentThread().isInterrupted());
                }
            }
        };
        interupThread.start();
        interupThread.interrupt();
        //isInterrupted1:true
        System.out.println("isInterrupted1:"+interupThread.isInterrupted());
        return interupThread;
    }

    public  static  Thread testInterrupCatch1 (){
        Thread interupThread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);
                    Thread.currentThread().interrupt();


                } catch (InterruptedException e) {
                    System.out.println("isInterrupted2:"+Thread.currentThread().isInterrupted());
                }
            }
        };
        interupThread.interrupt();
        //isInterrupted1:false 在线程开启前调用interrupt是无效的。
        System.out.println("isInterrupted1:"+ interupThread.isInterrupted());
        interupThread.start();
        return interupThread;
    }

}
