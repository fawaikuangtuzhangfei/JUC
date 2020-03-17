package com.cas;

import java.util.concurrent.atomic.AtomicReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 原子类操作 自定义类的原子性保证
 * @author nanshoudabaojian
 *
 */
public class AtomicReferenceDemo {

	public static void main(String[] args) {
		User zs = new User("zs", 11);
		User ls = new User("lis", 21);
		
		AtomicReference<User> userAtomicReference = new AtomicReference<>();
        userAtomicReference.set(zs);
        //如果是zs就替换成ls 
        System.out.println(userAtomicReference.compareAndSet(zs, ls)+"\t"+userAtomicReference.get().toString());
        System.out.println(userAtomicReference.compareAndSet(zs, ls)+"\t"+userAtomicReference.get().toString());
	}

}

@Getter
@Setter
@AllArgsConstructor
@ToString
class User{
   private String name;
   private int age;
}