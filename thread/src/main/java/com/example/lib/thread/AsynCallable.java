package com.example.lib.thread;

import java.util.concurrent.Callable;

/**
 * Created by ThinkPad on 2018/12/20.
 */

public class AsynCallable<T> implements Callable<T> {
    private AsyncExecueService.CallableListener<T> callableListener;
    private Callable<T> callable;

    public static <T> Callable<T> decorate(Callable<T> callable,
                                           AsyncExecueService.CallableListener<T> callableListener) {
        if (callable != null && callableListener != null) {
            return new AsynCallable<>(callable, callableListener);
        }
        return callable;

    }

    private AsynCallable(Callable<T> callable, AsyncExecueService.CallableListener<T> callableListener) {
        this.callableListener = callableListener;
        this.callable = callable;
    }

    @Override
    public T call() throws Exception {
        T result = callable.call();
        if (callableListener != null) {
            callableListener.onFinish(result);
        }
        return result;
    }


}
