package com.wu.leetcode.leetcodetest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wuxuyang
 * @date 2020/10/26 14:34
 */
public class October {

    /*2020/10/26
     **两数相加
     */

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode pre = new ListNode(0);
        ListNode cur = pre;
        int carry = 0;
        while (l1 != null || l2 != null) {
            int x = l1 == null ? 0 : l1.val;
            int y = l2 == null ? 0 : l2.val;
            int sum = x + y + carry;

            carry = sum / 10;
            sum = sum % 10;
            cur.next = new ListNode(sum);

            cur = cur.next;
            if (l1 != null)
                l1 = l1.next;
            if (l2 != null)
                l2 = l2.next;
        }
        if (carry == 1) {
            cur.next = new ListNode(carry);
        }
        return pre.next;
    }


    public static void main(String[] args) {
        ListNode listNode = new ListNode(2, new ListNode(4, new ListNode(3)));
        ListNode listNode1 = new ListNode(5, new ListNode(6, new ListNode(4)));
        ListNode listNode2 = addTwoNumbers(listNode, listNode1);
        System.out.println(listNode2.val);
    }
}
