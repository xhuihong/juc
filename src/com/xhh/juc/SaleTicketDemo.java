package com.xhh.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


class Ticket {//资源类 = 实例变量+实例方法
    //定义30张票
    private int number = 30;
    //上锁(可重复锁)
    Lock lock = new ReentrantLock();

    //卖票
    public void sale() {
        lock.lock();
        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + "\t卖出第几张票" + (number--) + "\t还剩下" + number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

/**
 * 题目：三个售票员         卖出          30张票
 * 笔记：如何编写企业级的多线程代码
 * 固定的编程套路+模板是什么？
 * <p>
 * 1 在高内聚低耦合的前提下，线程        操作      资源类
 * <p>
 * 1.1 一言不合，先创建一个资源类
 */
public class SaleTicketDemo {
    public static void main(String[] args) {//主线程，一切程序的人口
        Ticket ticket = new Ticket();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i=1;i<=40;i++){
//                    ticket.sale();
//                }
//            }
//        }, "A").start();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i=1;i<=40;i++){
//                    ticket.sale();
//                }
//            }
//        }, "B").start();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i=1;i<=40;i++){
//                    ticket.sale();
//                }
//            }
//        }, "C").start();
        new Thread(() ->{for (int i=1;i<=40;i++) ticket.sale();},"A").start();
        new Thread(() ->{for (int i=1;i<=40;i++) ticket.sale();},"B").start();
        new Thread(() ->{for (int i=1;i<=40;i++) ticket.sale();},"C").start();

    }

}
