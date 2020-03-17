package com.jucapi;

import java.util.concurrent.CountDownLatch;

/**
 * 秦灭六国以及枚举类的使用
 * 
 * @author nanshoudabaojian
 *
 */
public class CountDownLatchDemoCountry {

	public static void main(String[] args) throws InterruptedException {
		
		CountDownLatch cdl = new CountDownLatch(6);
		
		for (int i = 1; i <= 6; i++) {
			new Thread(() -> {
				System.out.println(Thread.currentThread().getName() + "\t 被灭了");
				cdl.countDown();
			} , Country.forEach(i).getName()).start();
		}
		
		cdl.await();
		
		System.out.println(Thread.currentThread().getName() + "\t 秦统一六国");
	}
	
}
