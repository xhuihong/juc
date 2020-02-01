package com.xhh.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Data {
    private int number = 0;
    private Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public void inc() throws InterruptedException {
        lock.lock();
        try {
            while (number != 0) {
                condition.await();
            }
            number++;
            System.out.println(Thread.currentThread().getName() + "\t" + number);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void desc() throws InterruptedException {
        lock.lock();
        try {
            while (number == 0) {
                condition.await();
            }
            number--;
            System.out.println(Thread.currentThread().getName()+"\t"+number);
            condition.signalAll();
        }  finally {
            lock.unlock();
        }
    }

}

/**
 * 传统版的生产者消费者模型
 */
public class ProdCons_TradDemo {
    public static void main(String[] args) {
        Data data = new Data();
        new Thread(()->{
            for (int i = 0; i <5 ; i++) {
                try {
                    data.inc();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            },"A").start();
        new Thread(()->{
            for (int i = 0; i <5 ; i++) {
                try {
                    data.desc();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            },"B").start();
    }
}
