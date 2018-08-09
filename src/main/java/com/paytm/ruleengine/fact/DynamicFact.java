package com.paytm.ruleengine.fact;

import java.util.Map;

import com.paytm.ruleengine.rule.RuleContext;

public class DynamicFact<V> extends Fact<V> {
	private FactFunction<V> function;
	private boolean cacheable=false;

	public DynamicFact(String name, FactFunction<V> function) {
		super(name);
		this.function = function;
	}
	
	public DynamicFact(String name, boolean cacheable, FactFunction<V> function) {
		super(name);
		this.function = function;
		this.cacheable = cacheable;
	}

	@Override
	public V get(Map<String, Object> params, RuleContext ctx) {
		V value = null;
		if(this.cacheable) {
			Object obj = ctx.get(getName());
			if(obj != null) {
				value = (V)obj;
			} else {
				value = function.get(params, ctx);
				ctx.put(getName(), value);
			}
		} else {
			value = function.get(params, ctx);
			ctx.put(getName(), value);
		}
		return value;
	}
	
}
