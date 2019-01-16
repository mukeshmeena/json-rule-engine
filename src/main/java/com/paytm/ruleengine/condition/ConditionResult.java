package com.paytm.ruleengine.condition;

public class ConditionResult {
	private Condition condition;
	private boolean result;
	private Object factResult;
	
	public Condition getCondition() {
		return condition;
	}
	public void setCondition(Condition condition) {
		this.condition = condition;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public Object getFactResult() {
		return factResult;
	}
	public void setFactResult(Object factResult) {
		this.factResult = factResult;
	}
	@Override
	public String toString() {
		return "ConditionResult [condition=" + condition + ", result=" + result + ", factResult=" + factResult + "]";
	}
}
