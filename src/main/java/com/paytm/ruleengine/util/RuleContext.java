package com.paytm.ruleengine.util;

import java.util.HashMap;
import java.util.Map;

public class RuleContext {
	private Map<String, Object> context = new HashMap<>();
	
	public void put(String key, Object value) {
		this.context.put(key, value);
	}
	
	public Object get(String key) {
		return this.context.get(key);
	}
}
