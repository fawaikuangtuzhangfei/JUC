package com.spinlock_Reenter_writeread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁(互斥锁)
 * 读时共享 写时独占
 * 读 读 可
 * 读 写 不可
 * 写 写 不可
 * 
 * 独占锁就是该锁只能被一个线程独占 ReentrantLock和Sychronized都是独占锁
 * 共享锁就是该锁可被多个线程所共有
 * 
 * JUC包下面的ReentrantReadWriteLock
 * 其中包含写锁和读锁
 * writeLock  readLock
 * 
 * @author nanshoudabaojian
 *
 */
public class ReadWriteReetrantDemo {
	
	
	private volatile Map<String, Object> cache = new HashMap<>();
	
	private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
	
	public void set(String key, Object value){
		rwLock.writeLock().lock();
		System.out.println(Thread.currentThread().getName() + "\t 正在写入" + key);
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		cache.put(key, value);
		System.out.println(Thread.currentThread().getName() + "\t 写入完成" + key);
		rwLock.writeLock().unlock();
	}
	
	public void get(String key){
		rwLock.readLock().lock();
		System.out.println(Thread.currentThread().getName() + "\t 正在读取");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Object result = cache.get(key);
		System.out.println(Thread.currentThread().getName() + "\t 读取完成" + result);
		rwLock.readLock().unlock();
	}

	public void clearAll(){
		cache.clear();
	}
	
	public static void main(String[] args) {
		
		ReadWriteReetrantDemo rwLock = new ReadWriteReetrantDemo();
		
		for (int i = 0; i < 5; i++) {
			final int tempInt = i;
			new Thread(() -> {
				rwLock.set(""+tempInt, tempInt);
			} , String.valueOf(i)).start();
		}
		
		for (int i = 0; i < 5; i++) {
			final int tempInt = i;
			new Thread(() -> {
				rwLock.get(""+tempInt);
			} , String.valueOf(i)).start();
		}
		
	}
	
}
