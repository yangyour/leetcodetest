package com.wu.leetcode.leetcode.November;

/**
 * @author wuxuyang
 * @date 2020/11/3 16:17
 */
public class Solution {
    /*
    * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
    * 输入: "abcabcbb"
    * 输出: 3
    * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
    */

    public int lengthOfLongestSubstring(String s) {
        char[] chars = s.toCharArray();
        int len=0;
        int les=0;
        int i=0;
        while (true){
            if (chars[i]!=chars[i+1]){
                i++;
                len=i+1;
            }else {
                les=i;
                i++;
            }
        }
        for (int i = 0; i < chars.length; i++) {

        }
    }
}
