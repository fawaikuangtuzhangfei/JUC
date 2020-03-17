package com.JUC;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch 计数线程 ->用来控制一个或者多个线程等待多个线程
 * 
 * await()阻塞
 * countDown()计数器-1,当计数器为0时 所有await的线程被唤醒
 * @author nanshoudabaojian
 *
 */
public class CountDownLatchDemo {
	public static void main(String[] args) throws InterruptedException {
		/**
		 * 模拟6位同学离开之后，主线程才能关门
		 */
		CountDownLatch count = new CountDownLatch(6);
		
		for (int i = 0; i < 6; i++) {
			new Thread(() -> {
				System.out.println(Thread.currentThread().getName() + " 离开教室");
				count.countDown();
			} , String.valueOf(i)).start();
		}
		count.await();
		System.out.println(Thread.currentThread().getName() + " 关门");
	}

	public static void closeDoor() {
		for (int i = 0; i < 10; i++) {
			new Thread(() -> {
				System.out.println(Thread.currentThread().getName() + "同学,离开!");
			} , String.valueOf(i)).start();
		}
		
		System.out.println(Thread.currentThread().getName() +  " 关门!");
	}
	
	
}
