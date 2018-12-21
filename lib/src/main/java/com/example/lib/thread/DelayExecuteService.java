package com.example.lib.thread;

import java.util.concurrent.TimeUnit;

/**
 * 继承ExecuteService，在原来的基础功能上，扩展出延时功能和重复执行功能
 */
public interface DelayExecuteService extends ExecuteService {
    Cancelable execute(Runnable runnable, long delay, TimeUnit timeUnit);
    Cancelable execute(Runnable runnable, long delay, long perodic, TimeUnit timeUnit);
}
