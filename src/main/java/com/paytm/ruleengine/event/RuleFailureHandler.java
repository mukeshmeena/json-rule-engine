package com.paytm.ruleengine.event;

import com.paytm.ruleengine.rule.RuleContext;

@FunctionalInterface
public interface RuleFailureHandler {
	
	public void onFailure(RuleContext ctx);

}
