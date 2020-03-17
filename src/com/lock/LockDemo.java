package com.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 两个线程，一个线程打印1-52，另一个打印字母A-Z打印顺序为12A34B...5152Z,
 * 高内聚低耦合 线程 操作 资源类
 * 判断 通知 干活
 * @author nanshoudabaojian
 *
 */
public class LockDemo {
	public static void main(String[] args) {
		PrintToConsole p = new PrintToConsole();
		new Thread(() -> {
			p.printNum();
		} , "a").start();
		new Thread(() -> {
			p.printZimu();
		} , "b").start();
	}
}

class PrintToConsole{
	private Lock r1 = new ReentrantLock();
	private Condition c1 = r1.newCondition();
	private Condition c2 = r1.newCondition();
	private int flag = 0; //0打印数字 1打印字母
	public void printNum() {
		r1.lock();
		try {
			//判断
			for (int i = 1; i < 53; i++) {
				while(flag != 0){
					c1.await();
				}
				//干活
				System.out.print(i);
				if(i % 2 == 0){
					flag = 1;
					//通知
					c2.signal();
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			r1.unlock();
		}
	}
	public void printZimu() {
		r1.lock();
		try {
			for(int i=0; i<26; i++){
				while(flag != 1){
					c2.await();
				}
				System.out.print((char)('A' + i));
				flag = 0;
				c1.signal();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			r1.unlock();
		}
	}
}