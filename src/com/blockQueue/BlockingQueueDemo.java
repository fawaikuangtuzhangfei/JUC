package com.blockQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * BlockingQueue 阻塞队列
 * 
 * 实现类有: 必须传递一个阻塞值
 * ArrayBlockingQueue->数组结构构成的有界的阻塞队列
 * LinkedBlockingQueue->链表结构构成的有界的阻塞队列(但是大小为Integer.MAX_VALUE)
 * 
 * 主要方法有:
 * 异常:add(e); remove(); element();   IllegalStateException
 * 特殊值:offer(e); poll(); peek();
 * 阻塞:put(e); take();
 * 超时:offer(e,time,unit); poll(time,unit);
 * @author nanshoudabaojian
 *
 */
public class BlockingQueueDemo {
	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<String> bq = new ArrayBlockingQueue<>(3);
		
		/*System.out.println(bq.add("a"));
		System.out.println(bq.add("b"));
		System.out.println(bq.add("c"));
//		System.out.println(bq.add("a"));//IllegalStateException
		System.out.println(bq.remove());
		System.out.println(bq.remove());
		System.out.println(bq.remove());
		System.out.println(bq.remove());*/
		
		/*System.out.println(bq.offer("a"));
		System.out.println(bq.offer("b"));
		System.out.println(bq.offer("ac"));
		System.out.println(bq.offer("a"));//false
		System.out.println(bq.poll());
		System.out.println(bq.poll());
		System.out.println(bq.poll());
		System.out.println(bq.poll());//null */	
		
		/*bq.put("a");
		bq.put("b");
		bq.put("c");
//		bq.put("d");//阻塞
		System.out.println(bq.take());
		System.out.println(bq.take());
		System.out.println(bq.take());
//		System.out.println(bq.take());//阻塞 */	
	
		//超时退出
		bq.offer("a", 3L, TimeUnit.SECONDS);
		bq.offer("b", 3L, TimeUnit.SECONDS);
		bq.offer("c", 3L, TimeUnit.SECONDS);
		bq.offer("d", 3L, TimeUnit.SECONDS);
		System.out.println(bq.poll(3L, TimeUnit.SECONDS));
		System.out.println(bq.poll(3L, TimeUnit.SECONDS));
		System.out.println(bq.poll(3L, TimeUnit.SECONDS));
		System.out.println(bq.poll(3L, TimeUnit.SECONDS));
	}
}
