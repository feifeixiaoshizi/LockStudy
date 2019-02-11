package com.example.lib.generic;

public class Generic <T>{
    private T instance;

    public T getInstance() {
        return instance;
    }

    public void setInstance(T instance) {
        this.instance = instance;
    }

}
