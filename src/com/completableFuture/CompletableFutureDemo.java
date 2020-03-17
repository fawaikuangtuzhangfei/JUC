package com.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 异步回调 CompletetableFuture
 * 
 * runAsync 无返回值 Runnable
 * supplyAsyn 有返回值 Supplier
 * @author nanshoudabaojian
 *
 */
public class CompletableFutureDemo {
	public static void main(String[] args) throws Exception {
		CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {System.out.println(Thread.currentThread().getName() + "\t无返回值");}); 
		cf.get();
		
		CompletableFuture<Integer> cf2 = CompletableFuture.supplyAsync( () ->{
			System.out.println(Thread.currentThread().getName() + "\t异步处理");
			int a = 10/0;
			return 1024;});
		
		cf2.whenComplete((t,e)->{
			System.out.println(Thread.currentThread().getName() + "\t完成" + t);
			System.out.println(Thread.currentThread().getName() + "\t完成" + e);
		}).exceptionally(ex ->{
			System.out.println(ex.getMessage());;
			return 404;
		});
		System.out.println(cf2.get());
	}
}
