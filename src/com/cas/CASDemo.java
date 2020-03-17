package com.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS 比较并交换
 * @author nanshoudabaojian
 *
 */
public class CASDemo {

	public static void main(String[] args) {
		AtomicInteger atomicInteger = new AtomicInteger(5);
		System.out.println(atomicInteger.compareAndSet(5, 6) + "\t current" + atomicInteger.get());
		System.out.println(atomicInteger.compareAndSet(5, 10) + "\t current" + atomicInteger.get());
	}
}
