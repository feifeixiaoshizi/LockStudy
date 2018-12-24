package com.example.lib;

import com.example.lib.thread.AsyncExecueService;
import com.example.lib.thread.Cancelable;
import com.example.lib.thread.Dispatcher;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * test()和test1()效果是一样的，test是通过CAS实现的，test1是通过synchronized实现的。
 */
public class TestDispatcher {
    public static void main(String[] args) throws Exception {
        //测试周期性任务
        //testPerodicRunnable();
        //testCallable();
        //testCallableCallback();
        testSingleThread();
    }

    private static  int count  = 0;
    private static Cancelable cancelable;
    private static void testCallable() {
        Callable<String> callable;
        for (int i = 0; i < 100; i++) {
            count++;
            callable = new Callable<String>() {
                @Override
                public String call() throws Exception {
                    printLog("call:"+count);
                    return "call:" + count;
                }
            };
            cancelable = Dispatcher.delayExecuteService().execute(callable);
        }
    }

    private static void testCallableCallback(){
        Dispatcher.asyncExecueService().execute(new Callable<String>() {
            @Override
            public String call() throws Exception {
                printLog("call:"+count);
                return "call";
            }
        }, new AsyncExecueService.CallableListener<String>() {
            @Override
            public void onFinish(String s) {
                  printLog("finish:"+s);
            }
        },Dispatcher.singleThreadWorker());
    }

    private static void testPerodicRunnable() {
     cancelable =  Dispatcher.delayExecuteService().execute(new Runnable() {
          @Override
          public void run() {
              if(count>10){
                  cancelable.cancel();
              }
              printLog("count:"+count++);
          }
      },1,2, TimeUnit.SECONDS);
    }


    private static void testSingleThread(){
        Runnable runnable =null;
        for(int i=0;i<100;i++){
            runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    printLog("single thread count:"+count++);
                }
            };
            cancelable =  Dispatcher.singleThreadWorker().execute(runnable,i,
                    TimeUnit.SECONDS);
            if(i>10){
                //调用取消任务
                cancelable.cancel();
            }
        }


    }

    /**
     * 打印日志
     * @param content
     */
    private static void printLog(String content){
        System.out.println("time:"+ new Date(System.currentTimeMillis())
                +"   ThreadName:"+Thread.currentThread().getName()
                +"   "+content);
    }

}
