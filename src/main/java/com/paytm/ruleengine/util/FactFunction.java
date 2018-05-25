package com.paytm.ruleengine.util;

import java.util.Map;

@FunctionalInterface
public interface FactFunction<V> {
	V get(Map<String, Object> params, RuleContext ctx);
}
