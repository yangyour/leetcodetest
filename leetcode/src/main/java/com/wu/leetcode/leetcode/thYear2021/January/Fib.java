package com.wu.leetcode.leetcode.thYear2021.January;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuxuyang
 * @date 2021/1/4 16:42
 */
public class Fib {
    /**
     * 斐波那契数，通常用 F(n) 表示，形成的序列称为 斐波那契数列 。
     * 该数列由 0 和 1 开始，后面的每一项数字都是前面两项数字的和。也就是：
     * F(0) = 0，F(1) = 1
     * F(n) = F(n - 1) + F(n - 2)，其中 n > 1
     * 给你 n ，请计算 F(n)
     */
    public static int test1(int n) {
        List<Integer> integers=new ArrayList<>();
        integers.add(0);
        for (int i=0;i<n;i++){
            if (i==0){
                integers.add(1);
            }else {
                integers.add(integers.get(i)+integers.get(i-1));
            }
        }
        return integers.get(integers.size()-1);
    }

    //抄的
    public int fib(int n){
        if (n == 2 || n == 1)
            return 1;
        int prev = 1, curr = 1;
        for (int i = 3; i <= n; i++) {
            int sum = prev + curr;
            prev = curr;
            curr = sum;
        }
        return curr;
    }

    public static void main(String[] args) {

    }
}
