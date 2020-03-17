package com.forkJoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * 分支合并框架
 * 
 * 将一个复杂的任务不断分解 交给多个线程来进行
 * 
 * RecursiveTask 抽象类 继承后可在compute()里面递归调用fork()和join()
 * ForkJoinPool 分支合并池
 * ForkJoinTask 执行任务用到 相当于FutureTask
 * 
 * @author nanshoudabaojian
 *
 */
public class ForkJoinDemo {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		MyTask myTask = new MyTask(1, 100);
		//创建合并池
		ForkJoinPool pool = new ForkJoinPool();
		//用ForkJoinTask来执行合并池提交的任务 ->相当于FutureTask
		ForkJoinTask<Integer> t = pool.submit(myTask);
		//获取执行结果
		System.out.println(t.get());
		//关闭合并池
		pool.shutdown();
	}
}

/*
 * 将计算分解开来，不断的分为一半
 * 直到<10
 */
/**
 * @author nanshoudabaojian
 *
 */
class MyTask extends RecursiveTask<Integer>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final static int LJ_VALUE = 10;
	
	private int begin;
	private int end;
	private int result;
	
	public MyTask(int begin, int end) {
		super();
		this.begin = begin;
		this.end = end;
	}

	protected Integer compute() {
		if((end-begin) < LJ_VALUE){
			for(int i=begin; i<=end; i++){
				result = result + i;
			}
		}else{
			int middle = (end + begin) / 2;
			MyTask t1 = new MyTask(begin, middle);
			MyTask t2 = new MyTask(middle+1, end);
			t1.fork();
			t2.fork();
			result = t1.join() + t2.join();
		}
		return result;
	}
	
}
