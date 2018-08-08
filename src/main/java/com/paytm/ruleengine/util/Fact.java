package com.paytm.ruleengine.util;

import java.util.Map;

public abstract class Fact<V> {
	private String name;

	public Fact(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public abstract V get(Map<String, Object> params, RuleContext ctx);
	
}
