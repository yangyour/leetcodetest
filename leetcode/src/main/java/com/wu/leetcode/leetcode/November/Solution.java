package com.wu.leetcode.leetcode.November;

import java.util.ArrayList;
import java.util.List;

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

    public static int lengthOfLongestSubstring(String s) {
        char[] chars = s.toCharArray();
        int les=0;
        //dvdf
        List<Character> list=new ArrayList<>();
        for (int i1 = 0; i1 < chars.length; i1++) {
            if (list.contains(chars[i1])){
                if (list.size()>les){
                    les=list.size();
                }
                int i = list.indexOf(chars[i1]);
                list=list.subList(i,list.size()-1);
                list.add(chars[i1]);
            }else {
                list.add(chars[i1]);
            }
            if (i1==chars.length-1){
                if (list.size()>les){
//                    System.out.println(list.get(0));
                    les=list.size();
                }
            }
            for (Character character : list) {
                System.out.println(character);
            }
        }
        return les;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("dvdf"));;
    }
}
