package com.die;

import java.util.concurrent.TimeUnit;

/**
 * 死锁
 * 
 * 原因:
 * 系统资源不足 进程运行推进顺序不合适 资源分配不当
 * 
 * 两个线程互相争夺彼此的锁 且都不放手
 * 当发生死锁时 可以通过jps -l 查出具体哪个线程出错
 * 然后根据上一步查到的线程号 通过jstack xxx 查出具体的原因
 * 
 * @author nanshoudabaojian
 *
 */
public class DieDemo {
	
	public static void main(String[] args) {
		String lockA = "lockA";
		String lockB = "lockB";
		
		
		new Thread(new Resource(lockA, lockB) {
			
		}, "AAA").start();
	
		new Thread(new Resource(lockB, lockA) {
			
		}, "BBB").start();

	}
	

}

class Resource implements Runnable{
	
	private String lockA;
	private String lockB;
	
	public Resource(String lockA, String lockB){
		this.lockA = lockA;
		this.lockB = lockB;
	}

	@Override
	public void run() {
		
		synchronized(lockA){
			System.out.println(Thread.currentThread().getName() + "\t 持有锁" + lockA + "\t 请求" + lockB);
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (Exception e) {
				e.printStackTrace();
			}
			synchronized(lockB){
				System.out.println(Thread.currentThread().getName() + "\t 持有锁" + lockB + "\t 请求" + lockA);
			}
		}
		
	}
	
	
}
