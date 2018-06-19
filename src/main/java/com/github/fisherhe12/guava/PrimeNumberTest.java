package com.github.fisherhe12.guava;

import com.google.common.primitives.Ints;
import org.junit.Test;

/**
 * 筛选1-100的素数
 * 2是最小的质数，也是唯一的偶数质数
 * <pre>
 *     2 3 5 7 11 13 17 19 23 29 31 37 41 43 47 53 59 61 67 71 73 79 83 89 97 101 103 107 109 113 127 131 137 139 149
 * </pre>
 *
 * @author fisher
 * @date 2018-04-26
 */
public class PrimeNumberTest {



    @Test
    public void testPrintAllPrimeNumbers() {
        int max = 100;
        boolean[] flag = new boolean[max];
        int[] primes = new int[max / 3 + 1];
        int i = 0;
        for (int j = 2; j < max; j++) {
            if (!flag[j]) {
                primes[i++] = j;
                for (int k = j; k < max; k += j) {
                    flag[k] = true;
                }
            }
        }

        System.out.println(Ints.asList(primes));
    }

    @Test
    public void testPrintAllPrimeNumbers2() {
        int max = 100;
        int[] flag = new int[max / 32 + 1];
        int[] primes = new int[max / 3 + 1];
        int i = 0;
        for (int j = 2; j < max; j++) {
            if (((flag[j / 32] >> (j % 32)) & 1) == 0) {
                primes[i++] = j;
                for (int k = j; k < max; k += j) {
                    flag[k / 32] |= (1 << k % 32);
                }
            }
        }

        System.out.println(Ints.asList(primes));
    }
}
