package com.example.thinkpad.lockstudy.thread;

public class CallableListernerOnMainThread<T> implements AsyncExecueService.CallableListener<T>{
    private AsyncExecueService.CallableListener<T> callableListener;
    private AndroidMainThreadWorker androidMainThreadWorker ;

    public CallableListernerOnMainThread(AsyncExecueService.CallableListener<T> callableListener) {
        this.callableListener = callableListener;
        this.androidMainThreadWorker = new AndroidMainThreadWorker();
    }

    @Override
    public void onFinish(final T t) {
        androidMainThreadWorker.execute(new Runnable() {
            @Override
            public void run() {
                callableListener.onFinish(t);
            }
        });
    }
}
