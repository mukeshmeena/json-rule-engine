package com.paytm.ruleengine.util;

import java.io.Serializable;
import java.util.Map;

public class Rule implements Serializable {
	private static final long serialVersionUID = -7428782115469535292L;
	
	private Condition condition;
	private int priority=1;
	
	public Rule() {
		
	}
	
	public Rule(Condition condition) {
		this.condition = condition;
	}

	public boolean eval(Map<String, Object> params, RuleContext ctx, RuleResult ruleResult) {
		boolean result = condition.eval(params, ctx, ruleResult);
		ruleResult.setResult(result);
		return result;
	}

	public Condition getCondition() {
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
}
