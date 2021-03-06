package com.github.fisherhe12.jdk.reflect;


import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

/**
 * 动态代理模式演示
 * @author fisher
 */
class DynamicProxyTest {

	@Test
	void testDynamicProxy() {
		HelloImpl helloImpl = new HelloImpl();
		HelloinvocationHandler handler = new HelloinvocationHandler(helloImpl);
		//构造代码示例
		HelloInterface proxyInstance = (HelloInterface) Proxy
				.newProxyInstance(HelloImpl.class.getClassLoader(), HelloImpl.class.getInterfaces(), handler);
		//调用代理方法
		proxyInstance.sayHello();

	}
}
