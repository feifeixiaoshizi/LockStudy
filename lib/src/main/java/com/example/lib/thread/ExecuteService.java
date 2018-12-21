package com.example.lib.thread;

import java.util.concurrent.Callable;

/**
 * 核心接口负责执行任务（Runnable Callable）
 */
public interface ExecuteService {

    Cancelable execute(Runnable runnable);

    <V> FutureCancelable<V> execute(Callable<V> callable);

}
