package com.xhh.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Phone1 implements Runnable {
    public synchronized void sendSMS() {
        System.out.println(Thread.currentThread().getName() + "\t sendSMS");
        sendEmail();
    }

    public synchronized void sendEmail() {
        System.out.println(Thread.currentThread().getName() + "\t #### sendEmail");
    }

    Lock lock = new ReentrantLock();

    @Override
    public void run() {
        get();
    }

    public void get() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t get");
            set();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void set() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t set");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

/*
 * 可重入锁（也就是递归锁）：指的是同一个线程外层函数获得锁之后，内层递归函数仍然能获取该锁的代码，
 * 在同一线程在外层方法获取锁的时候，在进入内层方法会自动获取锁。也就是说，线程可以进入任何一个
 * 它已经拥有的锁所有同步着的代码块。
 * */
public class ReentrantLockDemo {
    public static void main(String[] args) {
        Phone1 phone1 = new Phone1();
        new Thread(() -> {
            phone1.sendSMS();
        }, "A").start();

        new Thread(() -> {
            phone1.sendSMS();
        }, "B").start();
        Thread thread = new Thread(phone1);
        Thread thread1 = new Thread(phone1);
        if (Thread.activeCount() > 2) {
            thread.start();
            thread1.start();
        }
    }
}
