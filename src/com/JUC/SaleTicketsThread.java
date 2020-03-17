package com.JUC;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 卖票demo
 * 三个售票员 卖出 3000张票
 * 
 * 在高内聚低耦合的前提下实现 
 * 线程 操作 资源类
 * @author nanshoudabaojian
 *
 */

class Ticket {
	private int num = 3000;
	
	Lock lock = new ReentrantLock();
	
	//卖票操作
	public void sale(){
		lock.lock();
		try {
			if(num > 0){
				System.out.println(Thread.currentThread().getName() + " sale ticket:" + num-- + "还剩下:" + num);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}
}

public class SaleTicketsThread{
	public static void main(String[] args) {
		
		Ticket t = new Ticket();
		new Thread(()->{
			for (int i = 0; i <= 3000; i++) {
				t.sale();
			}
		}, "A").start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i <= 3000; i++) {
					t.sale();
				}
			}

		}, "B").start();
		
		new Thread(()->{for (int i = 0; i <= 3000; i++) t.sale(); }, "C").start();
		
	}
}