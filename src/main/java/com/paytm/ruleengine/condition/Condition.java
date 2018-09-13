package com.paytm.ruleengine.condition;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.paytm.ruleengine.fact.Fact;
import com.paytm.ruleengine.operator.FactOperator;
import com.paytm.ruleengine.rule.RuleContext;
import com.paytm.ruleengine.rule.RuleResult;

public class Condition<L, R> implements Serializable {
	private static final long serialVersionUID = 58448454428844572L;

	private ConditionOperator expression = ConditionOperator.AND;
	
	private List<Condition> conditions;
	private String factName;
	//can't be serialized ... need to be fetched from all registered facts
	private transient Fact<L> fact;
	private FactOperator operator;
	private R configValue;
	
	public Condition() {
		
	}
	
	public Condition(Condition... conditions) {
		if(this.conditions == null) this.conditions = new LinkedList<>();
		Collections.addAll(this.conditions, conditions);
	}
	
	public Condition(String factName, FactOperator op, R configValue) {
		this.factName = factName;
		this.operator = op;
		this.configValue = configValue;
	}
	
	public Condition(ConditionOperator exp, Condition... conditions) {
		if(this.conditions == null) this.conditions = new LinkedList<>();
		Collections.addAll(this.conditions, conditions);
		this.expression = exp;
	}
	
	public Condition(ConditionOperator exp, List<Condition> conditions) {
		this.expression = exp;
		this.conditions = conditions;
	}
	
	public boolean eval(Map<String, Object> params, RuleContext ctx, RuleResult ruleResult) {
		ConditionResult conditionResult = new ConditionResult();
		conditionResult.setCondition(this);
		
		boolean result = false;
		if(conditions != null) {
			if(ConditionOperator.AND.equals(expression)) {
				result = true;
				for(Condition c: conditions) {
					if(!(result = result && c.eval(params, ctx, ruleResult))) {
						break;
					}
				}
			} else {
				result = false;
				for(Condition c: conditions) {
					if((result = result || c.eval(params, ctx, ruleResult))) {
						break;
					}
				}
			}
		} else {
			Object factValue = null;
			if(fact == null) 
				 factValue = params.get(factName);
			else 
				 factValue = fact.get(params, ctx);
			result = operator.compare(factValue, configValue);
			
			conditionResult.setFactResult(factValue);
		}
		
		conditionResult.setResult(result);
		ruleResult.addConditionResult(conditionResult);
		return result;
	}

	public void setFact(Fact<L> fact) {
		this.fact = fact;
	}

	public ConditionOperator getExpression() {
		return expression;
	}

	public void setExpression(ConditionOperator expression) {
		this.expression = expression;
	}

	public List<Condition> getConditions() {
		return conditions;
	}
	
	public void addCondition(Condition condition) {
		if(this.conditions == null) 
			this.conditions = new LinkedList<>();
		this.conditions.add(condition);
	}

	public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
	}

	public String getFactName() {
		return factName;
	}

	public void setFactName(String factName) {
		this.factName = factName;
	}

	public FactOperator getOperator() {
		return operator;
	}

	public void setOperator(FactOperator operator) {
		this.operator = operator;
	}

	public R getConfigValue() {
		return configValue;
	}

	public void setConfigValue(R configValue) {
		this.configValue = configValue;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((conditions == null) ? 0 : conditions.hashCode());
		result = prime * result + ((configValue == null) ? 0 : configValue.hashCode());
		result = prime * result + ((expression == null) ? 0 : expression.hashCode());
		result = prime * result + ((factName == null) ? 0 : factName.hashCode());
		result = prime * result + ((operator == null) ? 0 : operator.hashCode());
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
		Condition other = (Condition) obj;
		if ( (conditions == null && other.conditions != null) ||
				(conditions != null && other.conditions == null) ) {
			return false;
		} else if (conditions != null) {
			if(conditions.size() != other.conditions.size())
				return false;
			for(int i=0; i<conditions.size(); i++) {
				if(!conditions.get(i).equals(other.conditions.get(i))) {
					return false;
				}
			}
		}
		if (configValue == null) {
			if (other.configValue != null)
				return false;
		} else if (!configValue.equals(other.configValue))
			return false;
		if (expression != other.expression)
			return false;
		if (factName == null) {
			if (other.factName != null)
				return false;
		} else if (!factName.equals(other.factName))
			return false;
		if (operator != other.operator)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Condition [expression=" + expression + ", conditions=" + conditions + ", factName=" + factName
				+ ", operator=" + operator + ", configValue=" + configValue + "]";
	}

}
