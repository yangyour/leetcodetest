package com.wu.leetcode.leetcode.thYear2021.January;

/**
 * @author wuxuyang
 * @date 2021/1/4 17:58
 */
public class CanPlaceFlowers {
    /**
     * 假设有一个很长的花坛，一部分地块种植了花，另一部分却没有。
     * 可是，花不能种植在相邻的地块上，它们会争夺水源，两者都会死去。
     * <p>
     * 给你一个整数数组  flowerbed 表示花坛，
     * 由若干 0 和 1 组成，其中 0 表示没种植花，1 表示种植了花。
     * 另有一个数 n ，能否在不打破种植规则的情况下种入 n 朵花？
     * 能则返回 true ，不能则返回 false。
     */
    public static boolean test(int[] flowerbed, int n) {
        int count = 0;
        if (flowerbed.length==1){
            if (flowerbed[0]==1){
                return true;
            }else if (n>0){
                return false;
            }
        }
        //[1,0,0,0,1,0,0]
        for (int i = 0; i < flowerbed.length; i++) {
            if (i == 0 && flowerbed[i] == 0 && flowerbed[i + 1] == 0) {
                count++;
                i++;
            } else if (i == flowerbed.length - 1 && flowerbed[i] == 0 && flowerbed[i - 1] == 0) {
                count++;
            } else if (i!=flowerbed.length - 1&&i!=0){
                if (flowerbed[i] == 0 && flowerbed[i + 1] == 0 && flowerbed[i - 1] == 0) {
                    count++;
                    i++;
                }
            }
        }
        if (count >= n) {
            return true;
        } else {
            return false;
        }
    }
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        int count = 0;
        int m = flowerbed.length;
        int prev = -1;
        for (int i = 0; i < m; i++) {
            if (flowerbed[i] == 1) {
                if (prev < 0) {
                    count += i / 2;
                } else {
                    count += (i - prev - 2) / 2;
                }
                prev = i;
            }
        }
        if (prev < 0) {
            count += (m + 1) / 2;
        } else {
            count += (m - prev - 1) / 2;
        }
        return count >= n;
    }


    public static void main(String[] args) {
        System.out.println(test(new int[]{1,0,0,0,1,0,0}, 1));
    }
}
