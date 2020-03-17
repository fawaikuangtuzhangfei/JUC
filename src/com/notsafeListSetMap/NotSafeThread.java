package com.notsafeListSetMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 验证list、set、map的线程不安全
 * 线程不安全会出现异常:
 * 	ConcurrentModifactionException
 * 
 * 解决方案:
 * 1.1利用Vector
 * 1.2利用Collections的工具类
 * 1.3利用写时复制eg new CopyOnWriteArrayList();
 * @author nanshoudabaojian
 *
 */
public class NotSafeThread {
	public static void main(String[] args) {
		unSafeList();
//		SafeList1();
//		SafeList2();
//		SafeList3();
		
//		unSafeSet();
//		SafeSet1();
//		SafeSet2();
		
//		unSafeMap();
//		SafeMap1();
//		SafeMap2();
		
	}
	
	public static void unSafeList(){
		List<String> list = new ArrayList<String>();
		
		for(int i=0; i<10; i++){
			new Thread(() -> {
				list.add(UUID.randomUUID().toString().substring(0, 8));
				System.out.println(list);
			} , String.valueOf(i)).start();
		}
	}
	public static void SafeList3(){
		/*
		 * 写时复制:
		 * 	往一个容器里面添加元素时，不在当前容器里添加，
		 * 而是copy出一份新的，在新的中修改，修改完之后，
		 * 讲旧的引用指向新的引用。
		 * 
		 * 缺点:内存占用扩大一倍、数据不一致
		 * 不适用于:内存敏感且要求实时性较高的
		 */
		List<String> list = new CopyOnWriteArrayList<>();
		
		for(int i=0; i<30; i++){
			new Thread(() -> {
				list.add(UUID.randomUUID().toString().substring(0, 8));
				System.out.println(list);
			} , String.valueOf(i)).start();
		}
	}
	public static void SafeList2(){
		/*
		 * 利用Collections工具类的synchronizedList
		 */
		List<String> list = Collections.synchronizedList(new ArrayList<String>());
		
		for(int i=0; i<30; i++){
			new Thread(() -> {
				list.add(UUID.randomUUID().toString().substring(0, 8));
				System.out.println(list);
			} , String.valueOf(i)).start();
		}
	}
	public static void SafeList1(){
		/*
		 * Vector 的每个方法都添加了Sychronized,每个时刻只能有一个线程进来
		 * 导致造成了单线程模式,但是效率下降很快
		 */
		Vector<String> list = new Vector<String>();
		
		for(int i=0; i<10; i++){
			new Thread(() -> {
				list.add(UUID.randomUUID().toString().substring(0, 8));
				System.out.println(list);
			} , String.valueOf(i)).start();
		}
	}
	
	public static void unSafeSet(){
		Set<String> set = new HashSet<String>();
		for(int i=0; i<30; i++){
			new Thread(() -> {
				set.add(UUID.randomUUID().toString().substring(0, 8));
				System.out.println(set);
			} , String.valueOf(i)).start();
		}
	}
	public static void SafeSet1(){
		Set<String> set = Collections.synchronizedSet(new HashSet<String>());
		for(int i=0; i<30; i++){
			new Thread(() -> {
				set.add(UUID.randomUUID().toString().substring(0, 8));
				System.out.println(set);
			} , String.valueOf(i)).start();
		}
	}
	public static void SafeSet2(){
		Set<String> set = new CopyOnWriteArraySet<String>();
		for(int i=0; i<30; i++){
			new Thread(() -> {
				set.add(UUID.randomUUID().toString().substring(0, 8));
				System.out.println(set);
			} , String.valueOf(i)).start();
		}
	}
	
	public static void unSafeMap(){
		Map<String, Integer> map = new HashMap<String, Integer>();
		for(int i=0; i<30; i++){
			new Thread(() -> {
				map.put(UUID.randomUUID().toString().substring(0, 8), 1);
				System.out.println(map);
			} , String.valueOf(i)).start();
		}
	}
	public static void SafeMap1(){
		Map<String, Integer> map = Collections.synchronizedMap(new HashMap<String, Integer>());
		for(int i=0; i<30; i++){
			new Thread(() -> {
				map.put(UUID.randomUUID().toString().substring(0, 8), 1);
				System.out.println(map);
			} , String.valueOf(i)).start();
		}
	}
	public static void SafeMap2(){
		Map<String, Integer> map = new ConcurrentHashMap<>();
		for(int i=0; i<30; i++){
			new Thread(() -> {
				map.put(UUID.randomUUID().toString().substring(0, 8), 1);
				System.out.println(map);
			} , String.valueOf(i)).start();
		}
	}
}
