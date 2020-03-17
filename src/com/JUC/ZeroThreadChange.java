package com.JUC;

/**
 * 交替10轮 对初始值为0的数进行加减 最后还是0
 * 旧版写法->利用Synchronized方法
 * 
 * 1 高内聚低耦合的情况下 线程 操作 资源类
 * 2 判断 干活 通知
 * 3 防止虚假唤醒->判断用while
 * 
 * @author nanshoudabaojian
 *
 */
public class ZeroThreadChange {
	public static void main(String[] args) {
		Zero z = new Zero();
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

class Zero{
	private int number = 0;
	
	public synchronized void addNumber() throws InterruptedException{
		//1 判断
		while(number != 0){
			this.wait();
		}
		//2.干活
		number++;
		System.out.println(Thread.currentThread().getName() + ":" + number);
		//3 通知
		this.notifyAll();
	}
	
	public synchronized void deNumber() throws InterruptedException{
		//1 判断
		while(number == 0){
			this.wait();
		}
		//2.干活
		number--;
		System.out.println(Thread.currentThread().getName() + ":" + number);
		//3 通知
		this.notifyAll();
	}
}
