package com.paytm.ruleengine.engine;

import java.util.List;
import java.util.Map;

import com.paytm.ruleengine.event.RuleEventHandler;
import com.paytm.ruleengine.rule.Rule;
import com.paytm.ruleengine.rule.RuleContext;
import com.paytm.ruleengine.rule.RuleResult;

public class RuleEngine {
	
	public RuleEngine() {
		
	}
	
	public RuleResult run(Rule rule, Map<String, Object> params) {
		RuleResult ruleResult = new RuleResult();
		RuleContext ctx = new RuleContext();
		boolean isPass = rule.eval(params, ctx, ruleResult);
		if(isPass) {
			List<RuleEventHandler> successHandlers = rule.getSuccessHandlers();
			if(successHandlers != null) {
				for(RuleEventHandler h: successHandlers) {
					h.handle(ctx);
				}
			}
		} else {
			List<RuleEventHandler> failureHandlers = rule.getFailureHandlers();
			if(failureHandlers != null) {
				for(RuleEventHandler h: failureHandlers) {
					h.handle(ctx);
				}
			}
		}
		return ruleResult;
	}

}
