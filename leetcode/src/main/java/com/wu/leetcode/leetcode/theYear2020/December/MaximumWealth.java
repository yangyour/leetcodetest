package com.wu.leetcode.leetcode.theYear2020.December;

import java.util.Arrays;

/**
 * @author wuxuyang
 * @date 2020/12/25 16:41
 */
public class MaximumWealth {
    /**
     * 给你一个 m x n 的整数网格 accounts ，其中 accounts[i][j] 是第 i​​​​​​​​​​​​ 位客户在第 j 家银行托管的资产数量。返回最富有客户所拥有的 资产总量 。
     * 客户的 资产总量 就是他们在各家银行托管的资产数量之和。最富有客户就是 资产总量 最大的客户。
     */
    public int test1(int[][] accounts) {
        int max=0;
        for (int[] account : accounts) {
            int s=0;
            for (int i : account) {
                s+=i;
            }
            if (max<s){
               max=s;
            }
        }
        return max;
    }

    public int maximumWealth(int[][] accounts) {
        return Arrays.stream(accounts).map(ints ->
                Arrays.stream(ints).sum()
        ).max(Integer::compareTo).get();
    }

}
