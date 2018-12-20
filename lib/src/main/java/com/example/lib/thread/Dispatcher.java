package com.example.lib.thread;
/**
 * 1.线程安全
 * 2.任务
 * 3.执行 取消 延时 重复执行
 * 4.同步 异步
 * 5.开始  关闭
 * 6.切换线程
 * 7.线程阻塞
 * 8.单个线程执行
 */

public class Dispatcher {

    private static final DelayExecuteService delayWorker=new DelayWorker();
    public static DelayExecuteService delayWorker(){
        return delayWorker;
    }

}
