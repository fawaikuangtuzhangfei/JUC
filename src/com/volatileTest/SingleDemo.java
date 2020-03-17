package com.volatileTest;

/**
 * 双重检索的单例模式DCL
 * 多线程下的单例
 * 	由于对象的创建分为三步 1.创建内存空间 2.初始化对象 3.将对象指向内存空间
 * 并且这三步并没有数据依赖性，所以编译器会进行指令重排
 * 在多线程的情况下 a线程指向了1 3 此刻single != null 此刻b线程进来了此刻
 * 就造成了线程安全问题
 * 解决方案:
 * 	双重检索+volatile禁止指令重排
 * @author nanshoudabaojian
 *
 */
public class SingleDemo {
	private volatile SingleDemo single = null;
	
	private SingleDemo(){
		System.out.println("实例被创建了");
	}
	
	public SingleDemo getInstance(){
		if(single == null){
			synchronized(SingleDemo.class){
				if(single == null){
					single = new SingleDemo();
				}
			}
		}
		return single;
	}
}
