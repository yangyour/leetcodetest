package com.wu.leetcode.leetcode.theYear2020.December;

import java.util.Arrays;

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
    public int test(int m, int n) {
        int count = m * n;
        //第一格可以往右走，没格都可以选择往下或者往右，所以没格都有2条路
        // 8 1111111
        //   1111111
        //   1111111
        //   1111111
        int start = 0;
        for (int j = 0; j < n; j++) {
            int zh = n - 1;
            int zo = m + n - 1;
        }
        return 0;
    }

    //正确答案
    public static int uniquePaths(int m, int n) {
        int[] cur = new int[n];
        Arrays.fill(cur, 1);
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                cur[j] += cur[j - 1];
            }
        }
        return cur[n - 1];
    }

    public static int test1(int m, int n) {
        int[] arr = new int[n];
        Arrays.fill(arr, 1);
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                arr[j] += arr[j - 1];
            }
        }
        return arr[n - 1];
    }
    public static int test2(int m,int n){
        int arr[] =new int[n];
        Arrays.fill(arr,1);
        for (int i=1;i<m;i++){
            for (int j=1;j<n;j++){
                arr[j]+=arr[j-1];
            }
        }
        return arr[n-1];
    }

    public static int test3(int m,int n){
        int[] arr=new int[n];
        return 1;
    }

    public static void main(String[] args) {
        System.out.println(test2(5, 10));
    }

}
