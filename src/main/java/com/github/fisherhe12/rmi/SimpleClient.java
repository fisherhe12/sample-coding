package com.github.fisherhe12.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * com.github.fisherhe12.rmidfdf
 *
 * @author fisher
 */
public class SimpleClient {

    public static void main(String[] args) {
        try {
            HelloService helloService = (HelloService) Naming.lookup("rmi://localhost:8080/sayHello");
            System.out.println(helloService.sayHello("fisher"));
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }
    }
}
