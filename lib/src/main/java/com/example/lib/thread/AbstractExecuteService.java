package com.example.lib.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public abstract class AbstractExecuteService implements ExecuteService {
    @Override
    public Cancelable execute(Runnable runnable) {
        FutureCancelableRunnable cancelableRunnable = FutureCancelableRunnable.decorate(runnable);
        Future scheduledFuture = run(runnable);
        cancelableRunnable.setFuture(scheduledFuture);
        return cancelableRunnable;
    }

    @Override
    public <T> Cancelable execute(Callable<T> callable, CallableListener<T> callBack) {
        FutureCancelableCallable<T> cancelable = FutureCancelableCallable.decorate(callable, callBack);
        Future<T> future = run(cancelable);
        cancelable.setFuture(future);
        return cancelable;
    }

    protected abstract Future run(Runnable runnable);

    protected abstract <T> Future<T> run(Callable<T> callable);
}
