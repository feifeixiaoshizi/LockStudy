package com.example.lib;


import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 */
public class TestScheduledThreadPoolExectutor {
    public static void main(String[] args) throws Exception {
        test2();
    }

   private static ScheduledFuture scheduledFuture;
    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void test2() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(100);
        scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if(atomicInteger.incrementAndGet()>3){
                    scheduledFuture.cancel(false);
                }
                try {
                    Thread.currentThread().sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("ThreadName" + Thread.currentThread().getName() + "--->"
                        + new Date(System.currentTimeMillis()).toString());
                /*
 目的是为了每隔1一秒执行一次，但是跟怒日志可以分析出是每隔2秒执行一次，因为执行一次任务需要消耗2秒。
  时间   0    1    2   3   4
 任务   r1   r2'   r2
 0时开始执行，本来应该在1时再次执行，但是在2时才能执行完毕，然后设置在1时开始执行，刺水时间已经到2了，就开始执行了。
ThreadNamepool-1-thread-17--->Tue Dec 18 14:58:12 CST 2018
ThreadNamepool-1-thread-9--->Tue Dec 18 14:58:14 CST 2018
ThreadNamepool-1-thread-18--->Tue Dec 18 14:58:16 CST 2018
ThreadNamepool-1-thread-5--->Tue Dec 18 14:58:18 CST 2018
ThreadNamepool-1-thread-19--->Tue Dec 18 14:58:20 CST 2018
ThreadNamepool-1-thread-10--->Tue Dec 18 14:58:22 CST 2018
*/
            }
        }, 1, 1, TimeUnit.SECONDS);
    }


}
