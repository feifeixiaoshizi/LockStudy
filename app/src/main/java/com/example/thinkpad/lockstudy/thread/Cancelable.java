package com.example.thinkpad.lockstudy.thread;

public interface Cancelable {

    void cancel();

    boolean isCanceled();
}
