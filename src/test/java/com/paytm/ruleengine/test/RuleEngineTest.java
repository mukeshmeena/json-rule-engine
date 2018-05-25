package com.paytm.ruleengine.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paytm.ruleengine.util.Condition;
import com.paytm.ruleengine.util.ConditionOperator;
import com.paytm.ruleengine.util.DynamicFact;
import com.paytm.ruleengine.util.Fact;
import com.paytm.ruleengine.util.FactOperator;
import com.paytm.ruleengine.util.Rule;
import com.paytm.ruleengine.util.RuleEngine;
import com.paytm.ruleengine.util.RuleResult;

public class RuleEngineTest {
	
	public static void main(String[] args) {
		
		Condition maleAgeCondition = new Condition<>("age", FactOperator.GTE, 21);
		Condition maleGenderCondition = new Condition<>("gender", FactOperator.EQ, "M");
		Condition maleMariageable = new Condition<>(maleAgeCondition, maleGenderCondition);
		
		Condition femaleAgeCondition = new Condition<>("age", FactOperator.GTE, 18);
		Condition femaleGenderCondition = new Condition<>("gender", FactOperator.EQ, "F");
		Condition femaileMarriageable =  new Condition<>(femaleAgeCondition, femaleGenderCondition);
		
		Condition marriageableCondition = new Condition<>(ConditionOperator.OR, maleMariageable, femaileMarriageable);
		
		Rule rule = new Rule(marriageableCondition);
		
		RuleEngine engine = new RuleEngine(rule);
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("age", 20);
		inputParams.put("gender", "M");
		
		Fact fact = new DynamicFact<Integer>("age", (params, ctx) ->  {return 12;});
		
		engine.addSuccessHandler(ctx -> System.out.println("passed"));
		engine.addFailureHandler(ctx -> System.out.println("failed"));
		
		RuleResult ruleResult = engine.run(inputParams);
		
		System.out.println(ruleResult);
		
		ObjectMapper mapper = new ObjectMapper();
		String ruleJson = null;
		try {
			ruleJson = mapper.writeValueAsString(rule);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(ruleJson);
		
		Rule newRule = null;
		try {
			newRule = mapper.readValue(ruleJson, Rule.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(newRule);
	}

}
