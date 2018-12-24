package com.example.lib.thread;

public interface Cancelable {

    void cancel();

    boolean isCanceled();
}
