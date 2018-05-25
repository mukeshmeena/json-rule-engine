package com.paytm.ruleengine.util;

import java.util.Map;

public class DynamicFact<V> extends Fact<V> {
	private FactFunction<V> function;

	public DynamicFact(String name, FactFunction<V> function) {
		super(name);
		this.function = function;
	}

	@Override
	public V get(Map<String, Object> params, RuleContext ctx) {
		return function.get(params, ctx);
	}
	
}
