package com.course.testng.multiThread;

import org.testng.annotations.Test;

public class MultiThreadOnAnotion {

    /**
     * invocationCount 使用10个线程执行
     * threadPoolSize 线程池 默认是1，设置为3 就会用三个线程执行
     * 多线程执行的时候要设置线程池的大小，默认的是1。
     * 使用多线程执行性能测试 主要是结果无法统计不方便。
     **/

    @Test(invocationCount = 10,threadPoolSize = 3)
    public void test1(){
        System.out.println(1);
        System.out.printf("Thread ID :%s %n",Thread.currentThread().getId());
    }
}
