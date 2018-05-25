package com.paytm.ruleengine.util;

public class Param<T> {

	private T value;
	
	public Param(T value) {
		this.value = value;
	}
	
	public T get() {
		return value;
	}
	
	public void set(T value) {
		this.value = value;
	}
	
}
