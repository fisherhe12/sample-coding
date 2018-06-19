package com.github.fisherhe12.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * com.github.fisherhe12.rmi
 *
 * @author fisher
 */
public class HelloServiceImpl extends UnicastRemoteObject implements HelloService {

    public HelloServiceImpl() throws RemoteException {
    }

    @Override
    public String sayHello(String name) {
        return "hello world :" + name;
    }
}
