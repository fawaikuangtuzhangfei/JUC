package com.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池->ThreadPoolExecutor
 * 
 * 顶级接口:Executor
 * 子接口:ExecutorService
 * 线程池工具类:Executors
 * 
 * 三种创建线程池的方式:
 * 1.创建固定大小的线程池 newFixedThreadPool(x); ->一个银行多个窗口
 * 2.创建只有一个线程的线程池 newSingleThreadExecutor(); 一个银行一个窗口
 * 3.创建可伸缩可复用的线程池 newCachedThreadPool();一个银行n个窗口
 * 
 * @author nanshoudabaojian
 *
 */
public class ThreadPoolDemo {
	public static void main(String[] args) {
		/*
		 * public ThreadPoolExecutor(
		 * 					  int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory,
                              RejectedExecutionHandler handler) 
		 */
		ExecutorService threadPool = new ThreadPoolExecutor(
				3,
				5, 
				1L, 
				TimeUnit.SECONDS, 
				new LinkedBlockingQueue<>(3),
				Executors.defaultThreadFactory(), 
				//AbortPolicy() 默认的拒绝策略 若队列+max已满 则抛出异常暂停服务
				//CallerRunsPolicy() 回退给上一个调用此线程的线程
				//DiscardPolicy() 处理不了直接扔掉
				//DiscardOldestPolicy() 扔掉等待队列中存活时间最长的
				new ThreadPoolExecutor.DiscardOldestPolicy());
		try {
			for (int i = 0; i < 11; i++) {
				threadPool.execute(() ->{
					System.out.println(Thread.currentThread().getName() + "办理业务!");
				});
			}
		} finally {
			threadPool.shutdown();
		}
	}

	public static void initThreadPool() {
		/*
		 * 以下代码模拟一个银行中有5个服务窗口
		 * 10名顾客执行自己的业务
		 */
//		ExecutorService threadPool = Executors.newFixedThreadPool(5);
//		ExecutorService threadPool = Executors.newSingleThreadExecutor();
		ExecutorService threadPool = Executors.newCachedThreadPool();
		try {
			for (int i = 0; i < 10; i++) {
				threadPool.execute(() ->{
					System.out.println(Thread.currentThread().getName() + "办理业务!");
				});
			}
		} finally {
			threadPool.shutdown();
		}
	}
}
