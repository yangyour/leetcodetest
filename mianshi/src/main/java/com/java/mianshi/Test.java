package com.java.mianshi;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wuxuyang
 * @date 2021/1/13 14:35
 */
public class Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Thread daemonThread=new Thread(new Runnable() {
            @Override
            public void run() {
                //daemonThread run方法中是一个while死循环
                while (true){
                    try {
                        System.out.println("i am alive");
                        Thread.sleep(500);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }finally {
                        System.out.println("finally block");
                    }
                }
            }
        });
        daemonThread.setDaemon(true);
        daemonThread.start();
        try {
            Thread.sleep(800);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        Thread thread1=new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("impl Runnable thread");
            }
        });
        thread1.start();
        ExecutorService executorService= Executors.newSingleThreadExecutor();
        Future<String> submit=executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "three new callable thread";
            }
        });
        String returnSting=submit.get();
        System.out.println(returnSting);
    }

    private Lock lock=new ReentrantLock();//ReentrantLock是Lock的子类
    private void method(Thread thread){
        lock.lock();
        try {
            System.out.println("线程名:"+thread.getName()+"获得了锁");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("线程名："+thread.getName()+"释放了锁");
            lock.unlock();//释放锁对象
        }
    }
    public void tryLockMethod(Thread thread){
        //Lock.lock();//获取锁对象
        if(lock.tryLock()){
            try {
                System.out.println("线程名："+thread.getName()+"获得了锁");
                // Thread.sleep(2000);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                System.out.println("线程名："+thread.getName()+"释放了锁");
                lock.unlock();
            }
        }
    }
}

class MyCommon extends Thread{
    public void run(){
        for (int i = 0;i<5;i++){
            System.out.println("线程1第"+i+"次执行！");
            try{
                Thread.sleep(7);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
class MyDaemon implements Runnable{
    public void run(){
        for (long i=0;i<9999999L;i++){
            try{
                Thread.sleep(7);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}

