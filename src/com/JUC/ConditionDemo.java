package com.JUC;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程之间的顺序调用 A->B->C->A->B->C...
 * A打印1-5 B打印1-10 C打印1-15
 * 交替10轮
 * 
 * 高内聚低耦合 线程 操作 资源类
 * 判断 操作 通知
 * 防止虚假唤醒
 * 
 * 一个Lock多个Condition
 * @author nanshoudabaojian
 *
 */
public class ConditionDemo {
	public static void main(String[] args) {
		PrintDemo pd = new PrintDemo();
		new Thread(() -> {
			for (int i = 0; i < 10; i++) {
				pd.print5();
			}
		} , "A").start();
		new Thread(() -> {
			for (int i = 0; i < 10; i++) {
				pd.print10();
			}
		} , "B").start();
		new Thread(() -> {
			for (int i = 0; i < 10; i++) {
				pd.print15();
			}
		} , "C").start();
	}
}

class PrintDemo{
	private int number = 1; //A 1 B 2 C 3
	private Lock lock = new ReentrantLock();
	private Condition c1 = lock.newCondition();
	private Condition c2 = lock.newCondition();
	private Condition c3 = lock.newCondition();
	
	public void print5(){
		lock.lock();
		try {
			//1.判断
			while(number != 1){
				c1.await();
			}
			//2.干活
			for (int i = 0; i < 5; i++) {
				System.out.println(Thread.currentThread().getName() + ": " + i);
			}
			//3.通知
			number = 2;
			c2.signal();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}
	public void print10(){
		lock.lock();
		try {
			//1.判断
			while(number != 2){
				c2.await();
			}
			//2.干活
			for (int i = 0; i < 10; i++) {
				System.out.println(Thread.currentThread().getName() + ": " + i);
			}
			//3.通知
			number = 3;
			c3.signal();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}
	public void print15(){
		lock.lock();
		try {
			//1.判断
			while(number != 3){
				c3.await();
			}
			//2.干活
			for (int i = 0; i < 15; i++) {
				System.out.println(Thread.currentThread().getName() + ": " + i);
			}
			//3.通知
			number = 1;
			c1.signal();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}
}
