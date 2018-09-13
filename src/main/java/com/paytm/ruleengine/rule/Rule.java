package com.paytm.ruleengine.rule;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.paytm.ruleengine.condition.Condition;
import com.paytm.ruleengine.event.RuleEventHandler;

public class Rule implements Serializable {
	private static final long serialVersionUID = -7428782115469535292L;
	
	private String name;
	private Condition condition;
	private int priority=1;
	private List<RuleEventHandler> successHandlers;
	private List<RuleEventHandler> failureHandlers;
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public List<RuleEventHandler> getSuccessHandlers() {
		return successHandlers;
	}
	
	public void addSuccessHandler(RuleEventHandler handler) {
		if(this.successHandlers == null) {
			this.successHandlers = new LinkedList<>();
		}
		this.successHandlers.add(handler);
	}

	public void setSuccessHandlers(List<RuleEventHandler> successHandlers) {
		this.successHandlers = successHandlers;
	}

	public List<RuleEventHandler> getFailureHandlers() {
		return failureHandlers;
	}
	
	public void addFailureHandler(RuleEventHandler handler) {
		if(this.failureHandlers == null) {
			this.failureHandlers = new LinkedList<>();
		}
		this.failureHandlers.add(handler);
	}

	public void setFailureHandlers(List<RuleEventHandler> failureHandlers) {
		this.failureHandlers = failureHandlers;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((condition == null) ? 0 : condition.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + priority;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rule other = (Rule) obj;
		if (condition == null) {
			if (other.condition != null)
				return false;
		} else if (!condition.equals(other.condition))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (priority != other.priority)
			return false;
		return true;
	}
	
}
