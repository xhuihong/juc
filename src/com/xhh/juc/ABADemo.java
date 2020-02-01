package com.xhh.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

public class ABADemo {
    static AtomicInteger atomicInteger = new AtomicInteger(100);
    static AtomicStampedReference<Integer> integerAtomicStampedReference = new AtomicStampedReference<>(100, 1);

    public static void main(String[] args) {
        new Thread(() -> {
            atomicInteger.compareAndSet(100, 101);
            atomicInteger.compareAndSet(101, 100);
        }, "A").start();
        new Thread(() -> {
            try { TimeUnit.SECONDS.sleep(2); } catch (Exception e) { e.printStackTrace(); }
            System.out.println(atomicInteger.compareAndSet(100, 2020) + "\t" + atomicInteger.get());
        }, "B").start();

        try { TimeUnit.SECONDS.sleep(2); } catch (Exception e) { e.printStackTrace(); }
        System.out.println("==================================");
        new Thread(() -> {
            int stamp = integerAtomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t第一次的版本号"+stamp);
            try { TimeUnit.SECONDS.sleep(1); } catch (Exception e) { e.printStackTrace(); }
            integerAtomicStampedReference.compareAndSet(100,101,integerAtomicStampedReference.getStamp(),integerAtomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"\t第二次的版本号"+integerAtomicStampedReference.getStamp());
            integerAtomicStampedReference.compareAndSet(101,100,integerAtomicStampedReference.getStamp(),integerAtomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"\t第三次的版本号"+integerAtomicStampedReference.getStamp());


        }, "C").start();
        new Thread(()->{
            int stamp = integerAtomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t第一次的版本号"+stamp);
            try { TimeUnit.SECONDS.sleep(3); } catch (Exception e) { e.printStackTrace(); }
            boolean result=integerAtomicStampedReference.compareAndSet(100,2019,stamp,stamp+1);
            System.out.println(Thread.currentThread().getName()+"\t修改成功否"+result+"\t 现在的版本号"+integerAtomicStampedReference.getStamp()+"\t现在的值是什么 "+integerAtomicStampedReference.getReference());
        },"D").start();
    }
}
