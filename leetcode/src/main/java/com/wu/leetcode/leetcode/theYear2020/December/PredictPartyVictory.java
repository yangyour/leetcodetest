package com.wu.leetcode.leetcode.theYear2020.December;

/**
 * @author wuxuyang
 * @date 2020/12/11 10:29
 */
public class PredictPartyVictory {
    /**
     *Dota2 的世界里有两个阵营：Radiant(天辉)和 Dire(夜魇)
     *
     * Dota2 参议院由来自两派的参议员组成。现在参议院希望对一个 Dota2 游戏里的改变作出决定。他们以一个基于轮为过程的投票进行。在每一轮中，每一位参议员都可以行使两项权利中的一项：
     *
     * 1.禁止一名参议员的权利：
     *
     * 参议员可以让另一位参议员在这一轮和随后的几轮中丧失所有的权利。
     *
     * 2.宣布胜利：
     *
     * 如果参议员发现有权利投票的参议员都是同一个阵营的，他可以宣布胜利并决定在游戏中的有关变化。
     *
     * 给定一个字符串代表每个参议员的阵营。字母 “R” 和 “D” 分别代表了 Radiant（天辉）和 Dire（夜魇）。然后，如果有 n 个参议员，给定字符串的大小将是 n。
     *
     * 以轮为基础的过程从给定顺序的第一个参议员开始到最后一个参议员结束。这一过程将持续到投票结束。所有失去权利的参议员将在过程中被跳过。
     *
     * 假设每一位参议员都足够聪明，会为自己的政党做出最好的策略，你需要预测哪一方最终会宣布胜利并在 Dota2 游戏中决定改变。输出应该是 Radiant 或 Dire。
     */
    public static String test1(String senate){
        char[] charArray = senate.toCharArray();
        if (charArray.length<1){
            return Character.toString(charArray[0]).equals("R")?"Radiant":"Dire";
        }
        Integer rLen=1;
        Integer dLen=1;
        for (int i = 0; i < charArray.length; i++) {

        }
        return rLen>dLen?"Radiant":"Dire";
    }

    public static void main(String[] args) {
        System.out.println(test1("RDDDDDDRRRRRRRRRRDRDR"));
    }


    public String predictPartyVictory(String senate) {
        int Rnumber = 0;//R阵营总人数
        int Dnumber = 0;//D阵营总人数
        int curBanR = 0;//当前被ban
        int curBanD = 0;//当前被ban
        int totalBanR = 0;//被ban总数
        int totalBanD = 0;//被ban总数
        char[] chars = senate.toCharArray();
        boolean flag = true;
        while(true){
            for(int i = 0;i < chars.length;i++){
                char cur = chars[i];
                if(cur == 'R'){
                    if(flag)
                        Rnumber++;
                    if(curBanR == 0){
                        curBanD++;
                        totalBanD++;
                        if(totalBanD == Dnumber  && !flag)return "Radiant";
                    }else{
                        curBanR--;
                        chars[i] = 'r';
                    }
                }else if(cur == 'D'){
                    if(flag)
                        Dnumber++;
                    if(curBanD == 0){
                        curBanR++;
                        totalBanR++;
                        if(totalBanR == Rnumber  && !flag)return "Dire";
                    }else{
                        curBanD--;
                        chars[i] = 'd';
                    }
                }
            }
            flag = false;
            if(totalBanD >= Dnumber)return "Radiant";
            if(totalBanR >= Rnumber)return "Dire";
        }
    }

//    public static String test1(String senate){
//        int RNumber=0;
//        int DNumber=0;
//        int curBanR=0;
//        int curBanD=0;
//        int totalBanNumR=0;
//        int totalBanNumD=0;
//        char[] charArray = senate.toCharArray();
//        boolean flag=true;
//        while (flag){
//            for (int i = 0; i < charArray.length; i++) {
//                if (charArray[i]=='D'){
//
//                }
//            }
//        }
//    }
}
