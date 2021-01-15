package com.wu.leetcode.leetcode.thYear2021.January.thread;

/**
 * @author wuxuyang
 * @date 2021/1/15 9:24
 */
public class Foo {
    public Foo() {
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });

    }

    public void first(Runnable printFirst) throws InterruptedException {

        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
    }

    public void second(Runnable printSecond) throws InterruptedException {

        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
    }

    public void third(Runnable printThird) throws InterruptedException {

        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
    }
}
