package com.wu.leetcode.leetcode.theYear2020.November;

import com.alibaba.fastjson.JSON;

/**
 * @author wuxuyang
 * @date 2020/12/7 16:41
 */
public class MatrixScore {
    /**
     * 有一个二维矩阵 A 其中每个元素的值为 0 或 1 。
     * 移动是指选择任一行或列，并转换该行或列中的每一个值：将所有 0 都更改为 1，将所有 1 都更改为 0。
     * 在做出任意次数的移动后，将该矩阵的每一行都按照二进制数来解释，矩阵的得分就是这些数字的总和。
     * 返回尽可能高的分数。
     *
     * 实例
     * 输入：[[0,0,1,1],[1,0,1,0],[1,1,0,0]]
     * 输出：39
     * 解释：
     * 转换为 [[1,1,1,1],[1,0,0,1],[1,1,1,1]]
     * 0b1111 + 0b1001 + 0b1111 = 15 + 9 + 15 = 39
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/score-after-flipping-matrix
     */
    public int matrixScore(int[][] a) {
        System.out.println(a);
       int s= 0b1111+0b1001;
       return s;
    }

    public static void main(String[] args) {
        int[][] a=new int[3][4];
        int s= 0b1111+0b1001+ 0b1111;
        int b=a[0][0];
        System.out.println(JSON.toJSON(b));
    }
}
