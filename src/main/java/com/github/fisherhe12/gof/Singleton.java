package com.github.fisherhe12.gof;

/**
 * 枚举单例
 *
 * @author fisher
 */
public class Singleton {

}

enum SingletonEnum {

    INSTANCE;
    private Singleton instance;

    SingletonEnum() {
        instance = new Singleton();
    }

    public Singleton getInstance() {
        return instance;
    }
}



