package com.spinlock_Reenter_writeread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁demo
 * 也就是该线程遇到阻塞时不会停止 而是会一直循环请求 直至获取到
 * 通过cas完成自旋操作->a先进来 如果是null 就修改为thread，并持有5s
 * b进来之后发现不是null 一直while自旋请求 直至为null 进行修改
 * @author nanshoudabaojian
 *
 */
public class SpinLockDemo {

	AtomicReference<Thread> atomic = new AtomicReference<>();
	
	public void myLock(){
		System.out.println(Thread.currentThread().getName() + "\t come in");
		Thread thread = Thread.currentThread();
		// 如果为null 则进行更新
		while(!atomic.compareAndSet(null, thread)){
			
		}
	}
	
	public void myUnLock(){
		Thread thread = Thread.currentThread();
		atomic.compareAndSet(thread, null);
		System.out.println(Thread.currentThread().getName() + "\t invoke unlock");
	}
	
	public static void main(String[] args) {
		//多线程的模板
		//线程 操作 资源类
		//
		SpinLockDemo spin = new SpinLockDemo();
		
		new Thread(() -> {
			spin.myLock();
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (Exception e) {
				e.printStackTrace();
			}
			spin.myUnLock();
		} , "A").start();
		
		//休眠1s 让a先执行(防止指令重排 确保a先执行)
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		new Thread(() -> {
			spin.myLock();
			spin.myUnLock();
		} , "B").start();
	}
	
}
