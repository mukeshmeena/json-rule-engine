package com.paytm.ruleengine.util;

import java.util.LinkedList;
import java.util.List;

public class RuleResult {
	private boolean result;
	private List<ConditionResult> conditionResults = new LinkedList<>();
	
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public List<ConditionResult> getConditionResults() {
		return conditionResults;
	}
	public void addConditionResult(ConditionResult conditionResult) {
		this.conditionResults.add(conditionResult);
	}
	@Override
	public String toString() {
		return "RuleResult [result=" + result + ", conditionResults=" + conditionResults + "]";
	}
}
