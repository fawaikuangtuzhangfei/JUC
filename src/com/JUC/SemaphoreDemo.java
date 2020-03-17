package com.JUC;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Semaphore 信号量
 * 类似于抢车位、操作系统中的信号量，
 * 可以用来控制互斥资源的访问线程数量
 * 
 * acquire(获取)信号量-1
 * release(释放)信号量+1 唤醒等待的线程
 * @author nanshoudabaojian
 *
 */
public class SemaphoreDemo {
	public static void main(String[] args) {
		/**
		 * 以下代码模拟6辆车抢占3个车位
		 */
		Semaphore semaphore = new Semaphore(1);//此处换为1,等价于synchronized
		
		for (int i = 0; i < 6; i++) {
			new Thread(() -> {
				try {
					semaphore.acquire();
					System.out.println(Thread.currentThread().getName() + "\t抢占车位");
					//线程休眠
					try { TimeUnit.SECONDS.sleep(3); } catch (Exception e) { e.printStackTrace(); }
					System.out.println(Thread.currentThread().getName() + "\t释放了车位");
				} catch (Exception e1) {
					e1.printStackTrace();
				}finally{
					semaphore.release();
				}
			} , String.valueOf(i)).start();
		}
	}
}
