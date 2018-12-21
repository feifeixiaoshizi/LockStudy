package com.example.lib.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AsyncWorker extends AbstractExecuteService implements AsyncExecueService {

    private ScheduledExecutorService mExecutorService;

    public AsyncWorker() {
        mExecutorService = Executors.newScheduledThreadPool(10);
    }

    public AsyncWorker(ScheduledExecutorService mExecutorService) {
        this.mExecutorService = mExecutorService;
    }

    @Override
    public <T> FutureCancelable<T> execute(Callable<T> callable, CallableListener<T> callBack) {
        return execute(callable, callBack, null);
    }

    @Override
    public <T> FutureCancelable<T> execute(Callable<T> callable, CallableListener<T> callBack,
                                           ExecuteService callableListenerRunIn) {
        FutureCancelableRunnableFuture<T> futureCancelableRunnableFuture =
                convert(wapperCallable(callable, wapperCallableListener(callBack, callableListenerRunIn)));
        run(futureCancelableRunnableFuture);
        return futureCancelableRunnableFuture;
    }

    @Override
    protected void run(Runnable runnable) {
        mExecutorService.submit(runnable);
    }

    private <T> Callable<T> wapperCallable(Callable<T> callable, CallableListener<T> callableListener) {
        return AsynCallable.decorate(callable, callableListener);
    }

    private <T> CallableListener<T> wapperCallableListener(CallableListener<T> callableListener,
                                                           ExecuteService callableListenerRunIn) {
        return CallableListernerOnExecuteService.decorate(callableListener, callableListenerRunIn);
    }

}
