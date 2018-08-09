package com.paytm.ruleengine.fact;

import java.util.Map;

import com.paytm.ruleengine.rule.RuleContext;

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
