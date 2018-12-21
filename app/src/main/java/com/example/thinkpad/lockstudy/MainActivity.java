package com.example.thinkpad.lockstudy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.thinkpad.lockstudy.thread.AsyncExecueService;
import com.example.thinkpad.lockstudy.thread.Cancelable;
import com.example.thinkpad.lockstudy.thread.Dispatcher;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity  {
    public static final String TAG = "MainActivity";
    private TextView callable;
    private TextView repeat;
    public static int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callable = (TextView) findViewById(R.id.callable);
        callable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testCallable();
            }
        });
        repeat = (TextView) findViewById(R.id.repeat);
        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testPerodic();
            }
        });

    }

    private void testCallable() {
        Dispatcher.singleThreadWorker().execute(new Callable<String>() {
            @Override
            public String call() throws Exception {
                reportLog("callable:call");
                return "test";
            }
        }, new AsyncExecueService.CallableListener<String>() {
            @Override
            public void onFinish(String s) {
                reportLog("callable:finish" + s);
            }
        }, Dispatcher.androidMainThreadExecuteService());
    }
    public Cancelable cancelable;
    private void testPerodic(){
        cancelable  = Dispatcher.androidMainThreadExecuteService().execute(new Runnable() {
            @Override
            public void run() {
                reportLog("perodic:"+count++);
                if(count>10){
                    cancelable.cancel();
                }
            }
        },1,2, TimeUnit.SECONDS);
    }

    private void reportLog(String string) {
        Log.d(TAG, "ThreadName:" + Thread.currentThread().getName() + "-->" + string);
    }


}
