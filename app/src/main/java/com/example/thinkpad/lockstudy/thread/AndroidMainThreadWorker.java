package com.example.thinkpad.lockstudy.thread;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
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
        FutureCancelableRunnable cancelableRunnable = convert(runnable);
        Message message = Message.obtain(mainHandler, cancelableRunnable);
        message.obj = runnable;
        long delayTime = timeUnit.toMillis(delay);
        mainHandler.sendMessageDelayed(message, delayTime);
        return cancelableRunnable;
    }

    @Override
    public Cancelable execute(Runnable runnable, long delay, long perodic, TimeUnit timeUnit) {
        PeriodicRunnable<Void> periodicRunnable = new PeriodicRunnable<Void>(runnable, null, delay,
                perodic, timeUnit);
        executePeriodic(periodicRunnable);
        return periodicRunnable;
    }

    private <V> void executePeriodic(PeriodicRunnable<V> periodicRunnable) {
        Message message = Message.obtain(mainHandler, periodicRunnable);
        message.obj = periodicRunnable;
        long delayTime = periodicRunnable.timeUnit.toMillis(periodicRunnable.delay);
        mainHandler.sendMessageDelayed(message, delayTime);
    }

    /**
     * 实现了android的重复执行功能
     *
     * @param <V>
     */
    private class PeriodicRunnable<V> extends FutureTask<V> implements Cancelable {
        private long delay;
        private long perodic;
        private TimeUnit timeUnit;

        public PeriodicRunnable(@NonNull Callable<V> callable, long delay, long perodic, TimeUnit timeUnit) {
            super(callable);
            this.delay = delay;
            this.perodic = perodic;
            this.timeUnit = timeUnit;
        }

        public PeriodicRunnable(@NonNull Runnable runnable, V result, long delay, long perodic,
                                TimeUnit timeUnit) {
            super(runnable, result);
            this.delay = delay;
            this.perodic = perodic;
            this.timeUnit = timeUnit;
        }

        public PeriodicRunnable(@NonNull Callable<V> callable) {
            super(callable);
        }

        @Override
        public void run() {
            if (super.runAndReset()) {
                this.delay = perodic;
                executePeriodic(this);
            }
        }

        @Override
        public void cancel() {
            this.cancel(true);
        }

        @Override
        public boolean isCanceled() {
            return this.isCancelled();
        }
    }

    private <T> Callable<T> wapperCallable(Callable<T> callable, CallableListener<T> callableListener) {
        return AsynCallable.decorate(callable, callableListener);
    }

    private <T> CallableListener<T> wapperCallableListener(CallableListener<T> callableListener,
                                                           ExecuteService callableListenerRunIn) {
        return CallableListernerOnExecuteService.decorate(callableListener, callableListenerRunIn);
    }
}
