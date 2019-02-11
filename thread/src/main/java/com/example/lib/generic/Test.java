package com.example.lib.generic;

/**
 * Created by ThinkPad on 2018/12/25.
 */

public class Test {
    public static void main(String[] args) throws Exception {
        //使用的时候传递类型参数String
        Generic<String> stringGeneric = new Generic<>();
        stringGeneric.setInstance("add");
        System.out.println(stringGeneric.getInstance());
    }
}
