package com.example.lib.thread;

import java.util.concurrent.Callable;

@Deprecated
public class FutureCancelableCallable<T> extends FutureCancelable<T> implements Callable<T> {
    private Callable<T> callable;
    private AsyncExecueService.CallableListener<T> callback;

    public FutureCancelableCallable(Callable<T> callable, AsyncExecueService.CallableListener<T> callback) {
        this.callable = callable;
        this.callback = callback;
    }

    public static <T> FutureCancelableCallable<T> decorate(Callable<T> runnable,
                                                           AsyncExecueService.CallableListener<T> callBack) {
        return new FutureCancelableCallable(runnable, callBack);
    }


    @Override
    public void cancel() {
        super.cancel();
        release();
    }

    @Override
    public T call() {
        T result = null;
        try {
            result = callable.call();
            System.out.println("result:" + result);
            if (callback != null) {
                callback.onFinish(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            release();
        }
        return result;
    }

    private void release() {
        if (callback != null) {
            callback = null;
        }
    }
}
