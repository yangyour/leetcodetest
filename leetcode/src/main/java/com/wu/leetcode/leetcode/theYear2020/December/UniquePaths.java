package com.wu.leetcode.leetcode.theYear2020.December;

/**
 * @author wuxuyang
 * @date 2020/12/9 10:46
 */
public class UniquePaths {
    /**
     * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。
     * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。
     * 问总共有多少条不同的路径？
     */
    public int uniquePaths(int m, int n) {
        int count=m*n;
        //第一格可以往右走，没格都可以选择往下或者往右，所以没格都有2条路
        // 8 1111111
        //   1111111
        //   1111111
        //   1111111
        int start=0;
        for (int i=0;i<m;i++){
            for (int j=0;j<n;j++){
                if (i==0){

                }
            }
        }
        return 0;
    }
}
