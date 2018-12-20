package com.example.lib.thread;


public class FutureCancelableRunnable extends FutureCancelable implements Runnable {
    private Runnable runnable;

    public FutureCancelableRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    static FutureCancelableRunnable decorate(Runnable runnable) {
        return new FutureCancelableRunnable(runnable);
    }

    @Override
    public void run() {
        try {
            runnable.run();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           // cancel();
        }
    }
}
