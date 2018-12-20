package com.example.thinkpad.lockstudy.thread;

import java.util.concurrent.ScheduledExecutorService;

/**
 * 1.线程安全
 * 2.任务（任务类型 runnable  callable ）
 * 3.执行 取消 延时 重复执行
 * 4.同步 异步
 * 5.开始  关闭
 * 6.切换线程（封装一层来切换）
 * 7.线程阻塞（避免资源阻塞）
 * 8.单个线程执行
 */

public class Dispatcher {

    private static final DelayExecuteService delayWorker = new DelayWorker();

    public static DelayExecuteService delayExecuteService() {
        return delayWorker;
    }

    public static DelayExecuteService delayExecuteService(ScheduledExecutorService scheduledExecutorService) {
        return new DelayWorker(scheduledExecutorService);
    }

    public static AsyncExecueService asyncExecueService(ScheduledExecutorService scheduledExecutorService) {
        return new AsyncWorker(scheduledExecutorService);
    }

    private static final AndroidMainThreadWorker androidMainThreadWorker = new AndroidMainThreadWorker();

    public static DelayAsyncExecuteService androidMainThreadExecuteService() {
        return androidMainThreadWorker;
    }

    public static final SingleThreadWorker singleThreadWorker = new SingleThreadWorker();

    public static SingleThreadWorker singleThreadWorker() {
        return singleThreadWorker;
    }


}
