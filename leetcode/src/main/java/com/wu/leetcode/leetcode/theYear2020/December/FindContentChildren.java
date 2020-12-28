package com.wu.leetcode.leetcode.theYear2020.December;

import com.alibaba.fastjson.JSON;

import java.util.*;

/**
 * @author wuxuyang
 * @date 2020/12/26 15:27
 */
public class FindContentChildren {
    /**
     * 假设你是一位很棒的家长，想要给你的孩子们一些小饼干。但是，每个孩子最多只能给一块饼干。
     *
     * 对每个孩子 i，都有一个胃口值 g[i]，这是能让孩子们满足胃口的饼干的最小尺寸；并且每块饼干 j，
     * 都有一个尺寸 s[j] 。如果 s[j] >= g[i]，我们可以将这个饼干 j 分配给孩子 i ，
     * 这个孩子会得到满足。你的目标是尽可能满足越多数量的孩子，并输出这个最大数值。
     * @param g
     * @param s
     * @return
     */
    public static int test1(int[] g, int[] s) {
        int count=0;
        List<Integer> list=new ArrayList<Integer>();
        for (int i : s) {
            list.add(i);
        }
        list.sort(Comparator.comparing(integer -> integer));
        for (int i : g) {
            Iterator<Integer> iterator = list.iterator();
            while (iterator.hasNext()){
                Integer next = iterator.next();
                if (next>=i){
                    count++;
                    iterator.remove();
                    break;
                }
            }
        }
        return count;
    }

    //抄的正确答案
    public int test2(int [] g,int []s){
        Arrays.sort(g);
        Arrays.sort(s);
        int count=0;
        for (int j=0;count<g.length&&j<s.length;j++){
            if (g[count]>=s[j]){
                count++;
            }
        }
        return count;
    }

    //复制的正确答案
    public int findContentChildren(int[] g, int[] s) {
        //先对胃口值和饼干尺寸进行排序
        Arrays.sort(g);
        Arrays.sort(s);
        int count = 0;
        for (int j = 0; count < g.length && j < s.length; j++) {
            //如果当前饼干能满足当前孩子的胃口值，count就加1，否则就继续查找更大的饼干
            if (g[count] <= s[j])
                count++;
        }
        return count;
    }


    public static void main(String[] args) {
        test1(new int[]{1, 5, 1, 2, 4, 5, 6}, new int[]{1, 2,51,3,13212312,14,12,11});
    }
}
