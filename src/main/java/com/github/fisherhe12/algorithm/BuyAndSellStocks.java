package com.github.fisherhe12.algorithm;

/**
 * 最佳时机买卖股票
 * <p>Input: [7,1,5,3,6,4] Output: 7 Explanation: Buy on day 2 (price = 1) and sell on day 3
 * (price = 5), profit = 5-1 = 4. Then buy on day 4 (price = 3) and sell on day 5 (price = 6),
 * profit = 6-3 = 3.
 *
 * @author Yu.He
 */
public class BuyAndSellStocks {

    /**
     * 贪心算法
     */
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int totalProfit = 0;
        for (int i = 0; i < prices.length - 1; i++) {
            int price = prices[i];
            int nextPrice = prices[i + 1];
            if (price < nextPrice) {
                totalProfit += nextPrice - price;
            }
        }
        return totalProfit;
    }

}
