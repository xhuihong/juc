package com.xhh.juc;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/*
 * 1 故障现象
 *   java.util.ConcurrentModificationException
 *
 * 2 导致原因
 *   并发争抢修改导致，参考我们的花名册签名情况。
 *   一个人正在写入，另一个同学过来抢夺，导致数据不一致异常。并发修改异常。
 * 3 解决方案
 *   3.1 new Vector<>();
 *   3.2 Collections.synchronizedList(new ArrayList<>());
 *   3.3 new CopyOnWriteArrayList<>()
 *       写时复制
 *       CopyOnWrite容器即写时复制的容器。往一个容器添加元素的时候，不直接往当前容器Object[]添加，
 *       而是先将当前object[]进行Copy，复制出一个新的容器Object[] newElements，然后新的容器Object[] newElements
 *       里添加元素，添加完元素之后，再将原容器的引用指向新的容器setArray（newElements）;这样做的好处是可以对
 *       copyonwrite容器进行并发的读，而不需要加锁，因为当前容器不会添加任何元素。所以copyonwrite容器也是一种
 *       读写分离的思想，读和写不同的容器。
 *
 * 4 优化建议
 * */

public class ContainNotSafeDemo {
    public static void main(String[] args) {
        //Map<String,String> map = new HashMap<>();
        Map<String,String> map = new ConcurrentHashMap<>();
        for (int i = 0; i <30 ; i++) {
            new Thread(()->{
                map.put(Thread.currentThread().getName()+"\t",UUID.randomUUID().toString().substring(0,4));
                System.out.println(map);
                },String.valueOf(i)).start();

        }
    }

    public static void setNoSafe() {
        //Set<String> set =new HashSet<>();
        //Set<String> set =Collections.synchronizedSet(new HashSet<>());
        Set<String> set = new CopyOnWriteArraySet<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
    }

    public static void listNotSafe() {
        //List<String> list = new ArrayList<>();
        //List<String> list = new Vector<>();
        //List<String> list = Collections.synchronizedList(new ArrayList<>());
        List<String> list = new CopyOnWriteArrayList<>();

        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(Thread.currentThread().getName() + "\t" + list);
            }, String.valueOf(i)).start();
        }
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println(list);
    }
}
