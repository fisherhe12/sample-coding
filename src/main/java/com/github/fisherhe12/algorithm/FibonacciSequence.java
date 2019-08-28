package com.github.fisherhe12.algorithm;

/**
 * <p>斐波那契数列如下：
 * <p>1,1,2,3,5,8,13,21,34,...
 * <p>*那么，计算fib(5)时，需要计算1次fib(4),2次fib(3),3次fib(2)，调用了2次fib(1)*，即：
 * <p>fib(5) = fib(4) + fib(3)
 * <p>fib(4) = fib(3) + fib(2) ；fib(3) = fib(2) + fib(1)
 * <p>fib(3) = fib(2) + fib(1)
 * <p>这里面包含了许多重复计算，而实际上我们只需计算fib(4)、fib(3)、fib(2)和fib(1)各一次即可，
 * 后面的optimizeFibonacci函数进行了优化，使时间复杂度降到了O(n).
 *
 * @author fisher
 */
public class FibonacciSequence {


    /**
     * 简单粗暴的n-1+n-2的递归
     */
    private static int fibonacci(int n) {
        if (n == 1 || n == 2) {
            return 1;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    /**
     * 时间复杂度o(n)
     * <p>1,1,2,3,5,8,13,21,34,...
     *
     * 那么，我们可以这样看：fib(1,1,5) = fib(1,2,4) = fib(2,3,3) = 5
     *
     * 也就是说，以1,1开头的斐波那契数列的第五项正是以1,2开头的斐波那契数列的第四项， 而以1,2开头的斐波那契数列的第四项也正是以2,3开头的斐波那契数列的第三项，
     * 更直接地，我们就可以一步到位：fib(2,3,3) = 2 + 3 = 5,计算结束。
     *
     * 要明确前两项数列的值,以及相对于前两项后的目标项位置
     */
    private static int optimizeFibonacci(int first, int second, int n) {
        if (n > 0) {
            if (n == 1) {
                return first;
            } else if (n == 2) {
                return second;
            } else if (n == 3) {
                return first + second;
            }
            return optimizeFibonacci(second, first + second, n - 1);
        }
        return 0;
    }

    /**
     * 最简单直接的循环的方式
     */
    private static int fibonacciByLoop(int n) {
        if (n < 1) {
            return -1;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        int first = 1;
        int second = 1;
        int result = 0;
        for (int i = 3; i <= n; i++) {
            result = first + second;
            first = second;
            second = result;
        }
        return result;
    }


    /**
     * 非递归实现斐波那契数列(数组方式)
     */
    private static int fibonacciByArray(int n) {
        if (n < 1) {
            return -1;
        }
        int[] array = new int[n];

        array[0] = 1;
        array[1] = 1;

        for (int i = 2; i < n; i++) {
            array[i] = array[i - 1] + array[i - 2];
        }
        return array[n - 1];
    }

}
