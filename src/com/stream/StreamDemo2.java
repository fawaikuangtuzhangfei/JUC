package com.stream;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 流式计算
 * 
 * 函数式接口 java.util.function
 * 四大函数式接口(Java自带):
 * 消费型 Consumer<T>   void accept(T t);
 * 供给型 Supplier<T>  T get();
 * 函数型 Function<T, R>   R applt(T t);
 * 断言型 Predicate<T>	boolean test(T t);
 * @author nanshoudabaojian
 *
 */
public class StreamDemo2 {
	public static void main(String[] args) {
		//消费型
		/*Consumer<String> consumer = new Consumer<String>() {

			public void accept(String t) {
				
			}
			
		};*/
		Consumer<String> consumer = (t) -> {System.out.println("消费型" + t);};
		consumer.accept("123");
		
		//供给型
		/*Supplier supplier = new Supplier<String>() {

			public String get() {
				return null;
			}
		};*/
		Supplier<String> supplier = () -> {return "供给型"; };
		System.out.println(supplier.get());
		
		//函数式
		/*Function<String, Integer> function = new Function<String, Integer>() {

			public Integer apply(String t) {
				return null;
			}
		};*/
		Function<String, Integer> function = (s) ->{return 123;};
		System.out.println(function.apply("123"));
		
		//断言型
		/*Predicate<String> predicate = new Predicate<String>() {

			public boolean test(String t) {
				return false;
			}
		};*/
		Predicate<String> predicate = (s) ->{return s.isEmpty();};
		System.out.println(predicate.test("abc"));
		
	}
}
