package com.paytm.ruleengine.engine;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.paytm.ruleengine.event.RuleFailureHandler;
import com.paytm.ruleengine.event.RuleSuccessHandler;
import com.paytm.ruleengine.rule.Rule;
import com.paytm.ruleengine.rule.RuleContext;
import com.paytm.ruleengine.rule.RuleResult;

public class RuleEngine {
	private Rule rule;
	private RuleContext ctx = new RuleContext();
	private List<RuleSuccessHandler> successHandlers = new LinkedList<>();
	private List<RuleFailureHandler> failureHandlers = new LinkedList<>();
	
	public RuleEngine() {
		
	}
	
	public RuleEngine(Rule rule) {
		this.rule = rule;
	}
	
	public void addSuccessHandler(RuleSuccessHandler handler) {
		this.successHandlers.add(handler);
	}
	
	public void addFailureHandler(RuleFailureHandler handler) {
		this.failureHandlers.add(handler);
	}
	
	public RuleResult run(Map<String, Object> params) {
		RuleResult ruleResult = new RuleResult();
		boolean isPass = rule.eval(params, ctx, ruleResult);
		if(isPass) {
			for(RuleSuccessHandler h: successHandlers) {
				h.onSuccess(ctx);
			}
		} else {
			for(RuleFailureHandler h: failureHandlers) {
				h.onFailure(ctx);
			}
		}
		return ruleResult;
	}

}
