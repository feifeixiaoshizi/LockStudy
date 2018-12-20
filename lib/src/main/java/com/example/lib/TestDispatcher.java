package com.example.lib;

import com.example.lib.thread.Cancelable;
import com.example.lib.thread.Dispatcher;
import com.example.lib.thread.ExecuteService;
import com.example.lib.thread.FutureCancelable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * test()和test1()效果是一样的，test是通过CAS实现的，test1是通过synchronized实现的。
 */
public class TestDispatcher {
    public static void main(String[] args) throws Exception {
        //testRunnable();
        testCallable();

    }

    public static int n = 0;

    private static void testCallable() {
        Callable<String> callable;
        Cancelable cancelable;
        for (int i = 0; i < 100; i++) {
            n = i;
            callable = new Callable<String>() {
                @Override
                public String call() throws Exception {
                    // System.out.println("ThreadName:" + Thread.currentThread().getName()+"call:"+n);
                    return "call:" + n;
                }
            };
            cancelable = Dispatcher.delayWorker().execute(callable, new ExecuteService.CallableListener<String>() {
                @Override
                public void onFinish(String s) {
                    //System.out.println("ThreadName:" + Thread.currentThread().getName()+"onfinish:"+n);
                }
            });


        }
    }

    private static void testRunnable() {
        Runnable runnable;
        Cancelable cancelable;
        for (int i = 0; i < 1000; i++) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    System.out.println("ThreadName:" + Thread.currentThread().getName());
                }
            };
            cancelable = Dispatcher.delayWorker().execute(runnable);
            cancelable.cancel();
        }
    }


}
