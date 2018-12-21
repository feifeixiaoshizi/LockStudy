package com.example.lib;

import com.example.lib.thread.Cancelable;
import com.example.lib.thread.Dispatcher;
import com.example.lib.thread.ExecuteService;
import com.example.lib.thread.FutureCancelable;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * test()和test1()效果是一样的，test是通过CAS实现的，test1是通过synchronized实现的。
 */
public class TestDispatcher {
    public static void main(String[] args) throws Exception {
        testRunnable();
        //testCallable();
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
            cancelable = Dispatcher.delayExecuteService().execute(callable);
        }
    }

    private static  int count  = 0;
    private static Cancelable cancelable;
    private static void testRunnable() {
     cancelable =  Dispatcher.delayExecuteService().execute(new Runnable() {
          @Override
          public void run() {
              if(count>10){
                  cancelable.cancel();
              }
             System.out.println("time:"+new Date(System.currentTimeMillis()).toString()+"count:"+count++);
          }
      },1,2, TimeUnit.SECONDS);
    }


}
