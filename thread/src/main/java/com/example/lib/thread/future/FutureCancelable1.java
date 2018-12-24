package com.example.lib.thread.future;

import com.example.lib.thread.Cancelable;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 采用继承的方式实现
 */

public class FutureCancelable1<T> extends FutureTask<T> implements Cancelable {
    public FutureCancelable1(Callable<T> callable) {
        super(callable);
    }

    public FutureCancelable1(Runnable runnable, T t) {
        super(runnable, t);
    }

    @Override
    public void cancel() {
      this.cancel(true);
    }

    @Override
    public boolean isCanceled() {
        return isCancelled();
    }

    @Override
    public void run() {
        super.run();
    }
}
