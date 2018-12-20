package com.example.thinkpad.lockstudy.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 实现DelayAsyncExecuteService接口
 */
public class SingleThreadWorker extends AbstractExecuteService implements DelayAsyncExecuteService {
    private ScheduledExecutorService mExecutorService;
    private DelayExecuteService delayExecuteService;
    private AsyncExecueService asyncExecueService;

    public SingleThreadWorker() {
        this.mExecutorService = Executors.newScheduledThreadPool(1);
        delayExecuteService = Dispatcher.delayExecuteService(mExecutorService);
        asyncExecueService = Dispatcher.asyncExecueService(mExecutorService);
    }

    @Override
    public Cancelable execute(Runnable runnable, long delay, TimeUnit timeUnit) {
        return delayExecuteService.execute(runnable, delay, timeUnit);
    }

    @Override
    public Cancelable execute(Runnable runnable, long delay, long perodic, TimeUnit timeUnit) {
        return delayExecuteService.execute(runnable, delay, perodic, timeUnit);
    }

    @Override
    protected void run(Runnable runnable) {
         mExecutorService.submit(runnable);
    }


    @Override
    public <T> FutureCancelable<T> execute(Callable<T> callable, CallableListener<T> callBack) {
        return execute(callable, callBack, null);
    }

    @Override
    public <T> FutureCancelable<T> execute(Callable<T> callable, CallableListener<T> callBack,
                                           ExecuteService callableListenerRunIn) {
        return asyncExecueService.execute(callable, callBack, callableListenerRunIn);
    }
}
