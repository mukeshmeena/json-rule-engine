package com.paytm.ruleengine.event;

import com.paytm.ruleengine.rule.RuleContext;

@FunctionalInterface
public interface RuleEventHandler {
	
	public void handle(RuleContext ctx);

}
