package com.wu.leetcode.leetcode.theYear2020.December;

/**
 * @author wuxuyang
 * @date 2020/12/10 16:25
 */
public class LemonadeChange {
    /**
     * 在柠檬水摊上，每一杯柠檬水的售价为 5 美元。
     * 顾客排队购买你的产品，（按账单 bills 支付的顺序）一次购买一杯。
     * 每位顾客只买一杯柠檬水，然后向你付 5 美元、10 美元或 20 美元。你必须给每个顾客正确找零，也就是说净交易是每位顾客向你支付 5 美元。
     * 注意，一开始你手头没有任何零钱。
     * 如果你能给每位顾客正确找零，返回 true ，否则返回 false 。
     *
     * @param bills
     * @return boolean
     */
    public static boolean test(int[] bills) {
        Integer five = 0;
        Integer ten = 0;
        for (int i = 0; i < bills.length; i++) {
            if (bills[i] == 5) {
                five += 1;
            } else if (bills[i] == 10) {
                ten += 1;
                five -= 1;
            } else if (bills[i] == 20) {
                if (ten > 0) {
                    five -= 1;
                    ten -= 1;
                } else {
                    five -= 3;
                }
            }
            if (five < 0 || ten < 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean lemonadeChange(int[] bills) {
        if (bills.length == 0) return true;
        int rest5 = 0;
        int rest10 = 0;
        for (int bill : bills) {
            if (bill == 5) rest5++;
            else if (bill == 10) {
                if (rest5 <= 0) return false;
                else { // 用1张5元，换来1张10元
                    rest5--;
                    rest10++;
                }
            } else { // bill == 20 两种选择：用3张5元；用1张5元+1张10元
                if (rest10 >= 1 && rest5 >= 1) { // 用1张5元+1张10元
                    rest10--;
                    rest5--;
                } else if (rest5 >= 3) { // 用3张5元
                    rest5 -= 3;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[] a = {5, 5, 5, 5, 20, 20, 5, 5, 20, 5};
        System.out.println(test(a));
    }

}
