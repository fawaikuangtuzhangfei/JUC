package com.jucapi;

public enum Country {
	
	ONE(1, "齐"),
    /**
     *
     */
    TWO(2, "楚"),
    /**
     *
     */
    THREE(3, "燕"),
    /**
     *
     */
    FOUR(4, "赵"),
    /**
     *
     */
    FIVE(5, "魏"),
    /**
     *
     */
    SIX(6, "韩");

	
	Country(Integer code, String name) {
		this.code = code;
		this.name = name;
	}
	private Integer code; 
	private String name;
	
	public Integer getCode() {
		return code;
	}
	public String getName() {
		return name;
	} 
	
	/**
	 * 提供一个遍历的方法
	 */
	public static Country forEach(int code){
		Country[] countrys = Country.values();
		for(Country c : countrys){
			if(code == c.getCode()){
				return c;
			}
		}
		return null;
	}
	
}
