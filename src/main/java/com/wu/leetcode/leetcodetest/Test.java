package com.wu.leetcode.leetcodetest;

/**
 * @author wuxuyang
 * @date 2020/10/27 19:16
 * 练习类
 */
public class Test {
    public static ListNode add(ListNode l1,ListNode l2){
        ListNode iphone = new ListNode(0);
        ListNode huawei=iphone;

        int i=0;

        while (l1!=null||l2!=null){
            int x=l1==null? 0:l1.val;
            int y=l2==null? 0:l2.val;
            int sum=x+y+i;

            i=sum/10;
            sum=sum % 10;
            huawei.next=new ListNode(sum);

            huawei=huawei.next;
            if (l1!=null)
                l1=l1.next;
            if (l2!=null)
                l2=l2.next;
        }
        if (i==1){
            huawei.next=new ListNode(0);
        }
        return iphone.next;
    }
}
