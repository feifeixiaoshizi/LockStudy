package com.example.thinkpad.lockstudy.thread;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class AndroidMainThreadWorker extends AbstractExecuteService implements DelayAsyncExecuteService {
    private Handler mainHandler;

    public AndroidMainThreadWorker() {
        this.mainHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    protected void run(Runnable runnable) {
        Message message = Message.obtain(mainHandler, runnable);
        message.obj = runnable;
        mainHandler.sendMessage(message);
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
    public Cancelable execute(Runnable runnable, long delay, TimeUnit timeUnit) {
        FutureCancelableRunnable cancelableRunnable =  convert(runnable);
        Message message = Message.obtain(mainHandler, cancelableRunnable);
        message.obj = runnable;
        long delayTime = timeUnit.toMillis(delay);
        mainHandler.sendMessageDelayed(message, delayTime);
        return cancelableRunnable;
    }

    @Override
    public Cancelable execute(Runnable runnable, long delay, long perodic, TimeUnit timeUnit) {
        return null;
    }

    private <T> Callable<T> wapperCallable(Callable<T> callable, CallableListener<T> callableListener) {
        return AsynCallable.decorate(callable, callableListener);
    }

    private <T> CallableListener<T> wapperCallableListener(CallableListener<T> callableListener,
                                                           ExecuteService callableListenerRunIn) {
        return CallableListernerOnExecuteService.decorate(callableListener, callableListenerRunIn);
    }
}
