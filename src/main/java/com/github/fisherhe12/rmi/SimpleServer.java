package com.github.fisherhe12.rmi;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * com.github.fisherhe12.rmi
 *
 * @author fisher
 */
public class SimpleServer {

    public static void main(String[] args) {
        try {
            HelloService helloService = new HelloServiceImpl();

            LocateRegistry.createRegistry(8080);
            Naming.bind("rmi://localhost:8080/sayHello", helloService);
            System.out.println("simple server start success...");
        } catch (RemoteException | MalformedURLException | AlreadyBoundException e) {
            e.printStackTrace();
        }

    }

}

