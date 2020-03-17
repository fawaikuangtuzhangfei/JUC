package com.Lamda;

public class LamdaDemo {
	public static void main(String[] args) {
		BigBall b = new BigBall(){
			@Override
			public void say() {
				System.out.println("说");
			}
			
		};
		b.say();
		BigBall.laugh();
		
		BigBall b2 = () -> {
			System.out.println("xixi");
		};
		b2.say();
	}
}

@FunctionalInterface
interface BigBall{
	public void say();
	default void run(){
		System.out.println("rush B");
	}
	public static void laugh(){
		System.out.println("大笑");
	}
	public static void laugh2(){
		System.out.println("大笑");
	}
	default void sing(){
		System.out.println("大家好!");
	}
	default void go(){
		System.out.println("886!");
	}
}
