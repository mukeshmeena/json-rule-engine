package com.paytm.ruleengine.util;

import java.util.Map;

public class StaticFact<V> extends Fact<V> {
	private V value;
	
	public StaticFact(String name, V value) {
		super(name);
		this.value = value;
	}

	@Override
	public V get(Map<String, Object> params, RuleContext ctx) {
		return value;
	}
}
