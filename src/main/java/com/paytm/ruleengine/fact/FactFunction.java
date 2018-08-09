package com.paytm.ruleengine.fact;

import java.util.Map;

import com.paytm.ruleengine.rule.RuleContext;

@FunctionalInterface
public interface FactFunction<V> {
	V get(Map<String, Object> params, RuleContext ctx);
}
