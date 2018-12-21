package com.example.lib.thread;

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


    public static final SingleThreadWorker singleThreadWorker = new SingleThreadWorker();

    public static SingleThreadWorker singleThreadWorker() {
        return singleThreadWorker;
    }

  /*  public static  <V> Builder<V> newBuilder(){
        return new Builder();
    }
    public void test(){
        newBuilder().setRunnable(new Runnable() {
            @Override
            public void run() {

            }
        }).runUiThread().excute();
    }

    public static class Builder<V>{
        //runnable和callable互斥两者只能存在一个
        private Runnable runnable;
        private Callable<V> callable;
        private AsyncExecueService.CallableListener<V> callableListener;
        private int delay;
        private boolean callbackUIThread;
        private boolean runUIThread;
        private boolean runSingleThread;


        public Builder setRunnable(Runnable runnable) {
            this.runnable = runnable;
            this.callable = null;
            return this;
        }

        public Builder setDelay(int delay) {
            this.delay = delay;
            return this;
        }

        public Builder<V> setCallable(Callable<V> callable) {
            this.callable = callable;
            this.runnable=null;
            return this;
        }

        public Builder<V> setCallableListener(AsyncExecueService.CallableListener<V> callableListener) {
            this.callableListener = callableListener;
            return this;
        }
        public Builder runUiThread(){
            runUIThread = true;
            return this;
        }

        *//**
     *  线程切换
     * 延时
     * 任务
     * 同步 异步（listener ，）
     * 单个线程
     *
     * @return
     *//*
        public Cancelable excute(){
            if(runUIThread){
                return Dispatcher.androidMainThreadExecuteService().execute(runnable);
            }

            return null;
        }


    }*/


}
