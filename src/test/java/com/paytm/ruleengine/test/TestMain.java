package com.paytm.ruleengine.test;

import java.util.HashMap;
import java.util.Map;

import com.paytm.ruleengine.util.Param;

public class TestMain {

	public static void main(String[] args) {
		Integer integer = new Integer(5);
		
		Map<String, Param> map = new HashMap<>();
		map.put("user_id", new Param(integer));
	}
	
}
