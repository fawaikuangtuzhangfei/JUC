package com.volatileTest;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * number++问题
 *  由于其不是原子操作 包含三步操作 1.读取 2.加一 3.写回主内存 而volatile不能保证原子性
 * 解决方案:
 * 1/加sychronized锁
 * 2/利用原子类AtomicInteger
 * 
 * @author nanshoudabaojian
 *
 */
public class VolatileDemo {

	public static volatile AtomicInteger number = new AtomicInteger(0);
	
	public static  void plus(){
		number.getAndAdd(1);
	}
	
	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			new Thread(() -> {
				for(int j=0; j<1000; j++){
					plus();
				}
			} , String.valueOf(i)).start();
		}
		
		Thread.sleep(3000);
		System.out.println(number);
	}
	
}


/*Synchronized安全
public class VolatileDemo {

	public static int number = 0;
	
	public static synchronized void plus(){
		number++;
	}
	
	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			new Thread(() -> {
				for(int j=0; j<1000; j++){
					plus();
				}
			} , String.valueOf(i)).start();
		}
		
		Thread.sleep(3000);
		System.out.println(number);
	}
	
}*/


/*存在线程安全的:
public class VolatileDemo {

	public static volatile int number = 0;
	
	public static void plus(){
		number++;
	}
	
	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			new Thread(() -> {
				for(int j=0; j<1000; j++){
					plus();
				}
			} , String.valueOf(i)).start();
		}
		
		Thread.sleep(3000);
		System.out.println(number);
	}
	
}*/