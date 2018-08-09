package com.paytm.ruleengine.event;

import com.paytm.ruleengine.rule.RuleContext;

@FunctionalInterface
public interface RuleSuccessHandler {
	
	public void onSuccess(RuleContext ctx);

}
