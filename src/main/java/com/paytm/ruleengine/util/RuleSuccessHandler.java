package com.paytm.ruleengine.util;

@FunctionalInterface
public interface RuleSuccessHandler {
	
	public void onSuccess(RuleContext ctx);

}
