package com.example.thinkpad.lockstudy.thread;

import java.util.concurrent.Callable;

/**
 * 继承自ExecuteService，在原来的基础上添加异步的功能
 */
public interface AsyncExecueService extends ExecuteService {
    <T> FutureCancelable<T> execute(Callable<T> callable, CallableListener<T> callBack);

    <T> FutureCancelable<T> execute(Callable<T> callable, CallableListener<T> callBack,
                                    ExecuteService callableListenerRunIn);

    interface CallableListener<V> {
        void onFinish(V v);
    }
}
