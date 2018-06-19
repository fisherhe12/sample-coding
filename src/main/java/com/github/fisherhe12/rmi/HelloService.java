package com.github.fisherhe12.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * com.github.fisherhe12.rmi
 *
 * @author fisher
 */
public interface HelloService extends Remote {
    String sayHello(String name) throws RemoteException;
}
