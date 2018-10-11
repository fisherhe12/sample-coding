package com.github.fisherhe12.jdk.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * com.github.fisherhe12.jdk.reflect
 *
 * @author fisher
 */
public class HelloinvocationHandler implements InvocationHandler {

	private Object target;

	public HelloinvocationHandler(Object target) {
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("Invoking sayHello ");
		return method.invoke(target, args);
	}
}
