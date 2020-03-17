package com.JUC;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReadWriteLock
 * 读写锁
 * 写时加写锁，读时加读锁，读写分离
 * @author nanshoudabaojian
 *
 */
public class ReadWriteLockDemo {
	public static void main(String[] args) {
		Cache cache = new Cache();
		for (int i = 0; i < 5; i++) {
			final int tempInt = i;
			new Thread(() -> {
				cache.put(tempInt + "", tempInt);
			} , String.valueOf(i)).start();
		}
		
		for (int i = 0; i < 5; i++) {
			final int tempInt = i;
			new Thread(() -> {
				cache.get(tempInt+"");
			} , String.valueOf(i)).start();
		}
	}
}

/*
 * 模拟缓存
 */
class Cache{
	private Map<String, Object> map = new HashMap<String, Object>();
	private ReadWriteLock rwl = new ReentrantReadWriteLock();
	public void put(String key, Object value){
		rwl.writeLock().lock();
		try {
			System.out.println(Thread.currentThread().getName() + "--开始写入" + key);
			//线程休眠
			try { TimeUnit.SECONDS.sleep(1); } catch (Exception e) { e.printStackTrace(); }
			map.put(key, value);
			System.out.println(Thread.currentThread().getName() + "--成功写入" + key);
		} finally {
			rwl.writeLock().unlock();
		}
	}
	
	public void get(String key){
		rwl.readLock().lock();
		try {
			System.out.println(Thread.currentThread().getName() + "--开始读取");
			//线程休眠
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Object result = map.get(key);
			System.out.println(Thread.currentThread().getName() + "--成功读取" + result);
		} finally {
			rwl.readLock().unlock();
		}
		
	}
}




















