package com.callableFutureTask;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * CallableDemo
 * 1.与Thread的不同
 * 	实现的接口和方法不同
 * 	Thread无异常而Callable有异常
 * 	有无返回值
 * 
 * 2.FutureTask用来接收返回值，一个对应一个无论几个线程
 * @author nanshoudabaojian
 *
 */
public class CallableDemo {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
//		demo01();
		MyCallableDemo2 myCal2 = new MyCallableDemo2();
		FutureTask<Integer> f = new FutureTask<Integer>(myCal2);
		new Thread(f , "A").start();
		new Thread(f , "B").start();
		System.out.println(Thread.currentThread().getName() + " is come");
		System.out.println(f.get());
	}

	public static void demo01() throws InterruptedException, ExecutionException {
		MyCallableDemo myCal = new MyCallableDemo();
		FutureTask<Integer> f = new FutureTask<Integer>(myCal);
		new Thread(f , "name").start();
		System.out.println(f.get());
	}
}

class MyCallableDemo2 implements Callable<Integer>{

	@Override
	public Integer call() throws Exception {
		System.out.println("COME IN CALL*************");
		TimeUnit.SECONDS.sleep(3);
		return 1024;
	}
	
}

class MyCallableDemo implements Callable<Integer>{

	@Override
	public Integer call() throws Exception {
		System.out.println("COME IN CALL*************");
		return 1024;
	}
	
}
