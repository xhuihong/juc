package com.xhh.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 字面上就是可循环使用的屏障。当一组线程得到一个屏障（同步点）时被阻塞，
 * 直到最后一个线程到达屏障时，屏障才会打开，所有被屏障拦截的线程才会继续工作。进入屏障通过await方法。
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier =new CyclicBarrier(7,()->{
            System.out.println("召唤到七颗龙珠");
        });
        for (int i = 1; i <=7 ; i++) {
            final int tempInt=i;
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t 召唤到第几个龙珠"+tempInt);
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }
    }
}
