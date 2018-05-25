package com.paytm.ruleengine.util;

@FunctionalInterface
public interface RuleFailureHandler {
	
	public void onFailure(RuleContext ctx);

}
