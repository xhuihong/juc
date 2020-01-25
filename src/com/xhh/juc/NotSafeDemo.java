package com.xhh.juc;


import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 1 故障现象
 * java.util.ConcurrentModificationException
 * <p>
 * 2 导致原因
 * 多线程并发争抢同一个资源类，且不加锁
 * 3 解决方法
 * 3.1 new Vector<>()
 * 3.2 Collections.synchronizedList(new ArrayList<>());
 * 3.3 new CopyOnWriteArrayList()
 * <p>
 * <p>
 * 4 优化建议(同样的错误不犯第2次)
 * 可以使用ouc中的包的类，而不是加锁
 *
 * 不止是list set() map(ConcurrentMap)都是如此
 */
public class NotSafeDemo {
    public static void main(String[] args) {
//        List<String> list = new Vector<>();
//        List<String> list = new Collections.synchronizedList(new ArrayList<>());
//        List<String> list = new CopyOnWriteArrayList<>();
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }

}
