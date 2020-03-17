package com.produconsumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生产者 消费者模式
 * CAS\volatile\BlockingQueue\AtomicInteger\线程交互\原子引用
 * @author nanshoudabaojian
 *
 */
public class ProdConsumerBlockQueueDemo {

	public static void main(String[] args) throws Exception{
		ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(3);
		Cake cake = new Cake(queue);
		
		new Thread(() -> {
			try {
				cake.product();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} , "Product").start();
		
		new Thread(() -> {
			try {
				cake.resume();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} , "Consumer").start();
		
		
		TimeUnit.SECONDS.sleep(5);
		
		cake.stop();
		
	}
	
}

class Cake{
	
	//数量
	private AtomicInteger cakeNum = new AtomicInteger(1);
	//是否生产消费的标志
	private volatile boolean FLAG = true;
	//阻塞队列
	private BlockingQueue<String> queue = null;
	
	public Cake(BlockingQueue<String> queue){
		this.queue = queue;
	}
	
	//生产方法
	public void product() throws Exception{
		String data = null;
		boolean returnValue;
		while(FLAG){
			data = cakeNum.getAndIncrement() + "";
			returnValue = queue.offer(data, 2L, TimeUnit.SECONDS);
			if(returnValue){
				System.out.println(Thread.currentThread().getName() + "\t 插入队列数据" + data + "成功");
			}else{
				System.out.println(Thread.currentThread().getName() + "\t 插入队列数据" + data + "失败");
			}
			TimeUnit.SECONDS.sleep(1);
		}
		System.out.println(Thread.currentThread().getName() + "\t 停止 表示 flag" + FLAG);
	}
	
	//消费方法
	public void resume() throws Exception{
		String result = null;
		while(FLAG){
			result = queue.poll(2L, TimeUnit.SECONDS);
			//取出失败
			if(null == result || "".equalsIgnoreCase(result)){
				FLAG = false;
				System.out.println(Thread.currentThread().getName()+"\t"+"超过2m没有取到 消费退出");
                System.out.println();
                System.out.println();
                return;
			}
			System.out.println(Thread.currentThread().getName() + "消费队列" + result + "成功");
		}
	}
	
	//停止操作
	public void stop(){
		FLAG = false;
		System.out.println(Thread.currentThread().getName() + "\t 全停止");
	}
	
}
