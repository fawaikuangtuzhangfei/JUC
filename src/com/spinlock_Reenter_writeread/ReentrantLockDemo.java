package com.spinlock_Reenter_writeread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁 也叫递归锁
 * 外层的线程获得锁之后 内层的锁可以畅通无阻
 * synchronized和ReetrantLock就是可重入锁
 * 
 * 可重入锁最大的作用就是避免死锁
 * 
 * @author nanshoudabaojian
 *
 */
public class ReentrantLockDemo {

	public synchronized void sendEmail(){
		System.out.println(Thread.currentThread().getName() + "\t 发送邮件");
		sendMsg();
	}
	
	public synchronized void sendMsg(){
		System.out.println(Thread.currentThread().getName() + "\t 发送短信");
	}
	
	ReentrantLock lock = new ReentrantLock();
	
	public void set(){
		lock.lock();
		try {
			System.out.println(Thread.currentThread().getName() + "\t invoke set");
			get();
		} finally {
			lock.unlock();
		}
	}
	
	public void get(){
		lock.lock();
		try {
			System.out.println(Thread.currentThread().getName() + "\t invoke get");
		} finally {
			lock.unlock();
		}
		
	}
	
	public static void main(String[] args) {
		ReentrantLockDemo lock = new ReentrantLockDemo();
		
		new Thread(() -> {
			lock.sendEmail();
		} , "A").start();
		
		new Thread(() -> {
			lock.sendEmail();
		} , "B").start();
		
		
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("=================以上是sychronized==================");
		System.out.println();
		System.out.println("=================以下是ReentrantLock==================");
		
		new Thread(() -> {
			lock.set();
		} , "C").start();
		
		new Thread(() -> {
			lock.set();
		} , "D").start();
	}
	
}
