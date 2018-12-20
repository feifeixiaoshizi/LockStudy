package com.example.thinkpad.lockstudy.thread;

import android.os.Handler;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * 取消一个请求：
 * 1.通过handler把runnabe从消息队列中移除
 * 2.通过封装Runnable进一步控制Runnable中的run()方法。
 * 该类是通过handler来取消任务，不建议是用来额，建议使用FutureCancelableRunnable或者FutureCancelableRunnableFuture
 * @param <T>
 */
@Deprecated
public class AndroidHandlerCancelable<T> extends FutureCancelable<T> implements Runnable {
    private volatile boolean isCanceled;
    private Handler handler;
    private Runnable runnable;
    private Callable<T> callable;
    private AsyncExecueService.CallableListener<T> callBack;

    public AndroidHandlerCancelable(Handler handler, Runnable runnable) {
        this.handler = handler;
        this.runnable = runnable;
    }

    public AndroidHandlerCancelable(Handler handler, Callable<T> callable,
                                    AsyncExecueService.CallableListener<T> callBack) {
        this.handler = handler;
        this.callable = callable;
        this.callBack = callBack;
    }

    static AndroidHandlerCancelable decorate(Handler handler, Runnable runnable) {
        return new AndroidHandlerCancelable(handler, runnable);
    }

    static <T> AndroidHandlerCancelable decorate(Handler handler, Callable<T> callable, AsyncExecueService.CallableListener<T> callBack) {
        return new AndroidHandlerCancelable(handler, callable, callBack);
    }

    @Override
    public void cancel() {
        handler.removeCallbacks(this);
        isCanceled = true;
    }

    @Override
    public boolean isCanceled() {
        return isCanceled;
    }

    @Override
    public Future<T> getFuture() {
        throw new UnsupportedOperationException("android handler not support Future!");
    }

    @Override
    public void run() {
        try {
            if (runnable != null) {
                runnable.run();
            }
            if (callable != null) {
                T result = callable.call();
                if (callBack != null) {
                    callBack.onFinish(result);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
}
