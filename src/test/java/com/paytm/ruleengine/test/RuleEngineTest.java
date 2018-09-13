package com.paytm.ruleengine.test;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.paytm.ruleengine.condition.Condition;
import com.paytm.ruleengine.condition.ConditionOperator;
import com.paytm.ruleengine.engine.RuleEngine;
import com.paytm.ruleengine.operator.FactOperator;
import com.paytm.ruleengine.rule.Rule;
import com.paytm.ruleengine.rule.RuleResult;
import com.paytm.ruleengine.util.RuleLoader;

public class RuleEngineTest {
	
	@Test
	public void testLoadMarriage() {
		Rule loadedRule = loadMarriageRule();
		Rule createdRule = createMarriageRule();
		assertTrue("rule loaded and programatically crated don't match", loadedRule.equals(createdRule));
	}
	
	@Test
	public void testMarriageRule() {
		Rule rule = loadMarriageRule();
		
		RuleEngine engine = new RuleEngine();
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("age", 20);
		inputParams.put("gender", "M");
		//Fact fact = new DynamicFact<Integer>("age", (params, ctx) ->  {return 12;});
		RuleResult ruleResult = engine.run(rule, inputParams);
		assertTrue("result must be false", !ruleResult.isResult());

		inputParams.put("age", 23);
		ruleResult = engine.run(rule, inputParams);
		assertTrue("result must be true", ruleResult.isResult());
	}
	
	private Rule loadMarriageRule() {
		RuleLoader loader = new RuleLoader();
		return loader.loadByName("marriageRule");
	}
	
	private Rule createMarriageRule() {
		Condition maleAgeCondition = new Condition<>("age", FactOperator.GTE, 21);
		Condition maleGenderCondition = new Condition<>("gender", FactOperator.EQ, "M");
		Condition maleMariageable = new Condition<>(maleAgeCondition, maleGenderCondition);
		
		Condition femaleAgeCondition = new Condition<>("age", FactOperator.GTE, 18);
		Condition femaleGenderCondition = new Condition<>("gender", FactOperator.EQ, "F");
		Condition femaileMarriageable =  new Condition<>(femaleAgeCondition, femaleGenderCondition);
		
		Condition marriageableCondition = new Condition<>(ConditionOperator.OR, maleMariageable, femaileMarriageable);
		
		Rule rule = new Rule(marriageableCondition);
		rule.setName("marriageRule");
		
		rule.addSuccessHandler(ctx -> System.out.println("passed"));
		rule.addFailureHandler(ctx -> System.out.println("failed"));
		
		return rule;
	}

}
