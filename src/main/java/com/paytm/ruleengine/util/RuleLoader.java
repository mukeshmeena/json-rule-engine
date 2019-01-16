package com.paytm.ruleengine.util;

import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonReader;
import com.paytm.ruleengine.condition.Condition;
import com.paytm.ruleengine.condition.ConditionOperator;
import com.paytm.ruleengine.fact.Fact;
import com.paytm.ruleengine.fact.FactResolver;
import com.paytm.ruleengine.operator.FactOperator;
import com.paytm.ruleengine.rule.Rule;

public class RuleLoader {
  
  private FactResolver factResolver;
  
  public RuleLoader(FactResolver factResolver) {
    this.factResolver = factResolver;
  }
	
	public Rule loadByName(String ruleName) {
		Rule rule = null;
		String fileName = ruleName+".json";
		InputStream in = getFileFromClasspath(fileName);
		JsonReader reader = new JsonReader(new InputStreamReader(in));
		JsonObject json = new JsonParser().parse(reader).getAsJsonObject();
		rule = loadRule(json);
		return rule;
	}
	
	private InputStream getFileFromClasspath(String fileName) {
        return this.getClass().getResourceAsStream("/" + fileName);
    }
	
	private Rule loadRule(JsonObject json) {
		Rule rule = new Rule();
		JsonElement nameElement = json.get("name");
		if(nameElement != null) {
			rule.setName(nameElement.getAsString());
		}
		
		JsonObject conditionsJson = json.getAsJsonObject("conditions");
		Condition condition = new Condition();
		loadCondition(conditionsJson, condition);
		rule.setCondition(condition);
		
		//JsonObject successActionsJson = json.getAsJsonObject("successActions");
		//JsonObject failureActionsJson = json.getAsJsonObject("failureActions");
		
		return rule;
	}
	
	private void loadCondition(JsonObject json, Condition condition) {
		JsonArray conditionArrayJson = json.getAsJsonArray("any");
		if(conditionArrayJson != null) {
			condition.setExpression(ConditionOperator.OR);
			
			for(JsonElement el: conditionArrayJson) {
				JsonObject childConditionJson = el.getAsJsonObject();
				Condition childCondition = new Condition();
				loadCondition(childConditionJson, childCondition);
				condition.addCondition(childCondition);
			}
			
		} else {
			conditionArrayJson = json.getAsJsonArray("all");
			if(conditionArrayJson != null) {
				condition.setExpression(ConditionOperator.AND);
				
				for(JsonElement el: conditionArrayJson) {
					JsonObject childConditionJson = el.getAsJsonObject();
					Condition childCondition = new Condition();
					loadCondition(childConditionJson, childCondition);
					condition.addCondition(childCondition);
				}
			} else {
				condition.setFactName(json.get("fact").getAsString());
				condition.setOperator(FactOperator.valueOf(json.get("operator").getAsString()));
				
				Fact fact = this.factResolver.resolve(condition.getFactName());
				if(fact != null) {
				  condition.setFact(fact);
				}
				
				JsonElement valueJson = json.get("value");
				if(valueJson.isJsonPrimitive()) {
					JsonPrimitive primitiveJson = valueJson.getAsJsonPrimitive();
					if(primitiveJson.isNumber()) {
						condition.setConfigValue(primitiveJson.getAsNumber().intValue());
					} else {
						condition.setConfigValue(primitiveJson.getAsString());
					}
				}
			}
		}
	}
	
}
