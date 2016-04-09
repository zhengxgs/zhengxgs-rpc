package com.zhengxgs.proxy;

import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by zhengxgs on 2016/4/6.
 */
public class ProxyHandler implements InvocationHandler {

	private Object proxied;

	public ProxyHandler(Object proxied) {
		this.proxied = proxied;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println(method.getDeclaringClass().getName());
		method.invoke(proxied, args);
		return "321";
	}

	public static void main(String args[]) {
		RealSubject real = new RealSubject();
		Subject proxySubject = (Subject) Proxy.newProxyInstance(Subject.class.getClassLoader(), new Class[] { Subject.class },
				new ProxyHandler(real));

		System.out.println(proxySubject.doSomething("111", 222, 333));
		//write proxySubject class binary data to file
		// createProxyClassFile();
	}

	public static void createProxyClassFile() {
		String name = "ProxySubject";
		byte[] data = ProxyGenerator.generateProxyClass(name, new Class[] { Subject.class });
		try {
			FileOutputStream out = new FileOutputStream(name + ".class");
			out.write(data);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}