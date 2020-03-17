package com.JUC;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CycliBarrier ->用来控制多个线程互相等待，只有当多个线程都到达时才会继续
 * 
 * 内部也维护了一个计数器，每执行一次await()计数器-1，为0时继续
 * 与CountDownLatch的区别是:
 * 		CycliBarrier可以通过reset()方法来循环使用计数器
 * @author nanshoudabaojian
 *
 */
public class CyclicBarrierDemo {
	public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
		
		CyclicBarrier cyc = new CyclicBarrier(7, () -> {System.out.println(Thread.currentThread().getName() + " 召唤神龙");});
		
		for (int i = 0; i < 7; i++) {
			final int tempInt = i;
			new Thread(() -> {
				System.out.println(Thread.currentThread().getName() + " 获取到" + tempInt + "龙珠");
				try {
					cyc.await();
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} , String.valueOf(i)).start();
		}
		
	}

	public static void seven() {
		CyclicBarrier cyc = new CyclicBarrier(7, () -> {System.out.println(Thread.currentThread().getName() + " 召唤神龙");});
		
		for (int i = 0; i < 7; i++) {
			final int tempInt = i;
			new Thread(() -> {
				System.out.println(Thread.currentThread().getName() + " 获取到" + tempInt + "龙珠");
				try {
					cyc.await();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} , String.valueOf(i)).start();
		}
	}
}
