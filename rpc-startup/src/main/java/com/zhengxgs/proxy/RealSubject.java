package com.zhengxgs.proxy;

/**
 * Created by zhengxgs on 2016/4/6.
 */
public class RealSubject implements Subject {

	public String doSomething(String aaa, Integer bbb, int ccc) {
		System.out.println("call doSomething()");
        System.out.println("1111");
		return "123";

	}
}