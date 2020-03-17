package com.JUC;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 交替10轮 对初始值为0的数进行加减 最后还是0
 * 新版写法 ->利用Lock
 * 
 * 1 高内聚低耦合的情况下 线程 操作 资源类
 * 2 判断 干活 通知
 * 3 防止虚假唤醒->判断用while
 * 
 * @author nanshoudabaojian
 *
 */
public class ZeroThreadChange2 {
	public static void main(String[] args) {
		Zero2 z = new Zero2();
		
		new Thread(() -> {
			for(int i=0; i<100; i++){
				try {
					z.addNumber();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} , "A").start();
		new Thread(() -> {
			for(int i=0; i<100; i++){
				try {
					z.deNumber();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} , "B").start();
		new Thread(() -> {
			for(int i=0; i<100; i++){
				try {
					z.addNumber();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} , "C").start();
		new Thread(() -> {
			for(int i=0; i<100; i++){
				try {
					z.deNumber();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} , "D").start();
	}
}

class Zero2{
	private int number = 0;
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	
	public void addNumber() throws InterruptedException{
		lock.lock();
		try {
			//1 判断
			while(number != 0){
				condition.await();
			}
			//2.干活
			number++;
			System.out.println(Thread.currentThread().getName() + ":" + number);
			//3 通知
			condition.signalAll();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}
	
	public void deNumber() throws InterruptedException{
		lock.lock();
		try {
			//1 判断
			while(number == 0){
				condition.await();
			}
			//2.干活
			number--;
			System.out.println(Thread.currentThread().getName() + ":" + number);
			//3 通知
			condition.signalAll();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}
}
