package com.example.lib.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by ThinkPad on 2018/12/18.
 */

public interface DelayExecuteService extends ExecuteService {
    Cancelable execute(Runnable runnable, long delay, TimeUnit timeUnit);
    Cancelable execute(Runnable runnable, long delay, long perodic,TimeUnit timeUnit);
}
