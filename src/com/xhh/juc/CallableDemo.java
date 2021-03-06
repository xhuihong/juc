package com.xhh.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

class MyThread implements Runnable {
    @Override
    public void run() {

    }
}

class MyThread2 implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println("come in callable");
        return 1024;
    }
}

public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException{
        FutureTask<Integer> futureTask = new FutureTask(new MyThread2());
        new Thread(futureTask, "A").start();
        Integer number=futureTask.get();
        System.out.println(number);
    }
}
