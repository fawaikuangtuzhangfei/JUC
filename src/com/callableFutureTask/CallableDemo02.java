package com.callableFutureTask;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 复习回顾1
 * @author nanshoudabaojian
 *
 */
public class CallableDemo02 {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		MyCall mycall = new MyCall();
		FutureTask<Integer> t = new FutureTask<Integer>(mycall);
		new Thread(t).start();
		System.out.println(t.get());
		FutureTask<Integer> t2 = new FutureTask<Integer>(() -> {return 123;});
		new Thread(t2).start();
		t2.get();
	}
}

class MyCall implements Callable<Integer>{

	@Override
	public Integer call() throws Exception {
		return 123;
	}
	
}
