package com.wu.leetcode.leetcode.thYear2021.January;

import com.alibaba.fastjson.JSON;

/**
 * @author wuxuyang
 * @date 2021/1/4 17:24
 */
public class SwapNumbers {
    /**
     * 编写一个函数，不用临时变量，直接交换numbers = [a, b]中a与b的值。
     *
     * 示例：
     *
     * 输入: numbers = [1,2]
     * 输出: [2,1]
     *
     * [0,2147483647]
     * @param numbers
     * @return
     */
    public int[] test1(int[] numbers) {
        return new int[]{numbers[1], numbers[0]};
    }

    public static int[] swapNumbers(int[] numbers) {
        numbers[0]=numbers[0]+numbers[1];//3
        numbers[1]=numbers[0]-numbers[1];//1
        numbers[0]=numbers[0]-numbers[1];//
        return numbers;
    }

    public static void main(String[] args) {
        System.out.println(JSON.toJSON(swapNumbers(new int[]{3, 7777})));
    }
}
