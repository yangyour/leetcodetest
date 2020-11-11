package com.wu.leetcode.leetcode.November;

import java.util.*;

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
                Iterator<Character> iterator = list.iterator();
                while (iterator.hasNext()){
                    if (!iterator.next().equals(chars[i1])){
                        iterator.remove();
                    }else {
                        iterator.remove();
                        break;
                    }
                }
                list.add(chars[i1]);
            }else {
                list.add(chars[i1]);
            }
            if (i1==chars.length-1){
                if (list.size()>les){
                    les=list.size();
                }
            }
        }
        return les;
    }


    public static int lengthOfLongestSubstringLeeCode(String s) {
        int n = s.length(), ans = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int end = 0, start = 0; end < n; end++) {
            char alpha = s.charAt(end);
            if (map.containsKey(alpha)) {
                start = Math.max(map.get(alpha), start);
            }
            ans = Math.max(ans, end - start + 1);
            map.put(s.charAt(end), end + 1);
        }
        return ans;
    }

    public static int test(String s){
//        bpfbhmipx
        int res=0,n=s.length();
        Map<Character,Integer> map=new HashMap<>();
        for (int end=0,start=0;end<n;end++){
            char c = s.charAt(end);
            if (map.containsKey(c)){
                start=Math.max(map.get(c),start);
            }
            res=Math.max(res,end-start+1);
            map.put(s.charAt(end),end+1);
        }
        return res;
    }
    public static void main(String[] args) {
        System.out.println(test("bpfbhmipx"));;
//        int max = Math.max(1, 2);
//        System.out.println(max);
    }
}
