package com.xhh.juc;

import java.util.concurrent.*;

/*
 *第4种获得/使用java多线程的方式，通过线程池
 * （其他三种是：继承Thread类；实现Runnable接口，但是Runnable没有返回值，不抛异常；
 * 实现Callable接口，有返回值，会跑出异常）
 * */
public class MyThreadPoolDemo {

    public static void main(String[] args) {
        ExecutorService threadPool = new ThreadPoolExecutor(
                2,
                5,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy());
            try{
                for (int i = 1; i <=10 ; i++) {
                    int tmp=i;
                    threadPool.execute(()->{
                        System.out.println(Thread.currentThread().getName()+"\t 办理业务"+tmp +"任务");
                    });
                }
            }catch(Exception e){
                e.printStackTrace();
            }finally {
                threadPool.shutdown();
            }


    }

    public static void threadPoolInit() {
        //ExecutorService threadPool = Executors.newFixedThreadPool(5);//一池五个处理线程
        //ExecutorService threadPool = Executors.newSingleThreadExecutor();//一池一线程
        ExecutorService threadPool = Executors.newCachedThreadPool();//一池N一线程
        //模拟10用户来办理业务，每个用户就是一个来自外部请求线程
        try{
            for (int i = 0; i <10 ; i++) {
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"\t 办理业务");
                });
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
