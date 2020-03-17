package com.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 找出同时满足
 * 偶数id 年龄>24 用户名转为大写 用户名字母倒序 只给一个用户名
 * @author nanshoudabaojian
 *
 */
public class StreamDemo {
	public static void main(String[] args) {
		User u1 = new User(11,"a",23);
		User u2 = new User(12,"b",24);
		User u3 = new User(13,"c",22);
		User u4 = new User(14,"d",28);
		User u5 = new User(16,"e",26);
		
		List<User> list = Arrays.asList(u1, u2, u3, u4, u5);
		
		list.stream()
		.filter(u -> {return u.getId() % 2 == 0;})
		.filter(u -> {return u.getAge() > 24;})
		.map(u ->{return u.getUserName().toUpperCase();})
//		.sorted((o1,o2) -> {return o2.compareTo(o1);}).limit(1).forEach((t) ->{System.out.println(t);});
		.sorted((o1,o2) -> {return o2.compareTo(o1);}).limit(1).forEach(System.out::println);
	}

	public static void myIdea(List<User> list) {
		List<String> results = new ArrayList<>();
		for(User u : list){
			if(u.getId()%2 == 0 && u.getAge() > 24){
				results.add(u.getUserName().toUpperCase());
			}
		}
		Collections.sort(results);
		System.out.println(results.get(results.size()-1));
	}
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class User{
	private Integer id;
	private String userName;
	private int age;
}
