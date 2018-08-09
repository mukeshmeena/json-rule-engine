package com.paytm.ruleengine.fact;

import java.util.Map;

import com.paytm.ruleengine.rule.RuleContext;

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
