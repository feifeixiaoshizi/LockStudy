package com.example.lib.thread;

import java.util.concurrent.Callable;

/**
 * Created by ThinkPad on 2018/12/18.
 */

public interface ExecuteService {

    Cancelable execute(Runnable runnable);

    <T> Cancelable execute(Callable<T> runnable, CallableListener<T> callBack);

    interface CallableListener<V> {
        void onFinish(V v);
    }
}
