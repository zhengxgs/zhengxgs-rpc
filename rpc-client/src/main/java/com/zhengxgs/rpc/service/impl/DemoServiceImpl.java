package com.zhengxgs.rpc.service.impl;


import com.zhengxgs.rpc.service.IDemoService;

/**
 * Created by zhengxgs on 2016/4/8.
 */
public class DemoServiceImpl implements IDemoService {


    @Override
    public String sayHi() {
        System.out.println("hello world");
        return "hello world";
    }
}
