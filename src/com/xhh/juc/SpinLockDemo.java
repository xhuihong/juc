package com.xhh.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/*
 * 写一个自旋锁
 * 自旋锁的好处：循环比较获取直到成功为止，没有类似wait的阻塞。
 * */
public class SpinLockDemo {
    AtomicReference<Thread> threadAtomicReference = new AtomicReference<>();

    public void myLock() {
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+"\t come in");
        while (!threadAtomicReference.compareAndSet(null,thread)){
            System.out.println(Thread.currentThread().getName()+"\t wait");
        }
    }
    public void unLock(){
        Thread thread=Thread.currentThread();
        threadAtomicReference.compareAndSet(thread,null);
        System.out.println(Thread.currentThread().getName()+"\t invoked myUnlock");
    }

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo=new SpinLockDemo();
        new Thread(()->{
            spinLockDemo.myLock();
            try{ TimeUnit.SECONDS.sleep(5);}catch(InterruptedException e){ e.printStackTrace(); }
            spinLockDemo.unLock();
            },"A").start();
            try{ TimeUnit.SECONDS.sleep(1);}catch(InterruptedException e){ e.printStackTrace(); }
            new Thread(()->{
                spinLockDemo.myLock();
                try{ TimeUnit.SECONDS.sleep(1);}catch(InterruptedException e){ e.printStackTrace(); }
                spinLockDemo.unLock();
                }," B").start();
    }
}
