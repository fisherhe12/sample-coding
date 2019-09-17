package com.github.fisherhe12.algorithm;

/**
 * 计算x的n次方
 *
 * @author Yu.He
 */
public class Pow {

    public double myPow(double x, int n) {
        if (n == 0) {
            return 1;
        }
        if (x == 0) {
            return 0;
        }

        if (n > 0) {
            double res = myPow(x, n / 2);
            return n % 2 == 0 ? res * res : x * res * res;
        } else {
            x = 1 / x;
            return n == Integer.MIN_VALUE ? x * myPow(x, -(++n)) : myPow(x, -n);
        }
    }
}
