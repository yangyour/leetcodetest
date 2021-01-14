package com.wu.leetcode.leetcode.thYear2021.January;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuxuyang
 * @date 2021/1/14 14:44
 */
public class PrefixesDivBy5 {
    /**
     * 给定由若干 0 和 1 组成的数组 A。我们定义 N_i：从 A[0] 到 A[i] 
     * 的第 i 个子数组被解释为一个二进制数（从最高有效位到最低有效位）。
     *
     * 返回布尔值列表 answer，只有当 N_i 可以被 5 整除时，答案 answer[i] 为 true，否则为 false。
     *
     */
    public static List<Boolean> test1(int[] A) {
        List<Boolean> booleans=new ArrayList<>();
        String a="";
        for (int i = 0; i < A.length; i++) {
            a=a+A[i];
            if (Integer.valueOf(a)>2147483647){
                a=""+A[i];
            }
            int i1 = Integer.parseInt(a, 2);
            if (i1%5==0){
                booleans.add(true);
            }else {
                booleans.add(false);
            }
        }
        return booleans;
    }

    public List<Boolean> prefixesDivBy51(int[] A) {
        int start=0;
        List<Boolean> list=new ArrayList<>();
        for (int i = 0; i < A.length; i++) {
            start=(start*2+A[i])%5;
            if (start==0){
                list.add(true);
            }else{
                list.add(false);
            }
        }
        return list;
    }

    public List<Boolean> prefixesDivBy5(int[] A) {
        List<Boolean> list = new ArrayList<Boolean>();
        int prefix = 0;
        int length = A.length;
        for (int i = 0; i < length; i++) {
            prefix = ((prefix << 1) + A[i]) % 5;
            list.add(prefix == 0);
        }
        return list;
    }



    public static void main(String[] args) {
        System.out.println(Integer.parseInt("11101",2));
//        System.out.println(JSON.toJSON(test1(new int[]{1,1,0,1,1,1,1,1,1,1,1,0,1,1,1,0,0,1,0})));
    }
}
