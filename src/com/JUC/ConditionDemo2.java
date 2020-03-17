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
public class ConditionDemo2 {
	public static void main(String[] args) {
		PrintDemo2 pd = new PrintDemo2();
		new Thread(() -> {
			for (int i = 0; i < 10; i++) {
				pd.print(0,1);
			}
		} , "A").start();
		new Thread(() -> {
			for (int i = 0; i < 10; i++) {
				pd.print(1,2);
			}
		} , "B").start();
		new Thread(() -> {
			for (int i = 0; i < 10; i++) {
				pd.print(2,0);
			}
		} , "C").start();
	}
}

class PrintDemo2{
	private int number = 0; //A 0 B 1 C 2
	private Lock lock = new ReentrantLock();
	private Condition c1 = lock.newCondition();
	private Condition c2 = lock.newCondition();
	private Condition c3 = lock.newCondition();
	private Condition[] cs = {c1, c2, c3};

	public void print(int x, int y){
		lock.lock();
		try {
			//1.判断
			while(number != x){
				//若此处为cs[number]就会发生死锁
				cs[x].await();
			}
			//2.干活
			for (int i = 0; i < (x+1)*5; i++) {
				System.out.println(Thread.currentThread().getName() + ": " + i);
			}
			//3.通知
			number = y;
			cs[y].signal();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}
	
}
