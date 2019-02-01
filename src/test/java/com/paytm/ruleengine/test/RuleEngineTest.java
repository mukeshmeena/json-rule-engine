package com.paytm.ruleengine.test;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.paytm.ruleengine.condition.Condition;
import com.paytm.ruleengine.condition.ConditionOperator;
import com.paytm.ruleengine.engine.RuleEngine;
import com.paytm.ruleengine.exceptions.InvalidRuleException;
import com.paytm.ruleengine.fact.DynamicFact;
import com.paytm.ruleengine.fact.Fact;
import com.paytm.ruleengine.fact.FactResolver;
import com.paytm.ruleengine.operator.Operator;
import com.paytm.ruleengine.operator.OperatorResolver;
import com.paytm.ruleengine.operator.basic.operators.EQOperator;
import com.paytm.ruleengine.operator.basic.operators.GTEOperator;
import com.paytm.ruleengine.rule.Rule;
import com.paytm.ruleengine.rule.RuleContext;
import com.paytm.ruleengine.rule.RuleResult;
import com.paytm.ruleengine.util.RuleLoader;

public class RuleEngineTest {

	@Test
	public void testLoadMarriage() throws InvalidRuleException {
		FactResolver resolver = new FactResolver();
		Fact fact = new DynamicFact<Integer>("age", (params, ctx) ->  {return 12;});
		Fact gender = new DynamicFact<String>("gender", (params, ctx) ->  {return "M";});

		resolver.registerFact(fact);
		resolver.registerFact(gender);

		Rule loadedRule = loadMarriageRule(resolver,new OperatorResolver());
		Rule createdRule = createMarriageRule();
		assertTrue("rule loaded and programatically crated don't match", loadedRule.equals(createdRule));
	}

	@Test
	public void testMarriageRule() throws InvalidRuleException {
		RuleEngine engine = new RuleEngine();
		Map<String, Object> inputParams = new HashMap<>();

		FactResolver resolver = new FactResolver();

		Rule rule = loadMarriageRule(resolver,new OperatorResolver());

		inputParams.put("age", 20);
		inputParams.put("gender", "M");
		RuleResult ruleResult = engine.run(rule, inputParams);
		assertTrue("result must be false", !ruleResult.isResult());

		inputParams.put("age", 23);
		ruleResult = engine.run(rule, inputParams);
		assertTrue("result must be true", ruleResult.isResult());
	}

	@Test
	public void testMarriageRuleWithDynamicFact() throws InvalidRuleException {
		RuleEngine engine = new RuleEngine();
		Map<String, Object> inputParams = new HashMap<>();
		FactResolver resolver = new FactResolver();

		Fact factUnderAge = new DynamicFact<Integer>("age", (params, ctx) ->  {return 20;});
		Fact gender = new DynamicFact<String>("gender", (params, ctx) ->  {return "M";});

		resolver.registerFact(factUnderAge);
		resolver.registerFact(gender);

		Rule rule = loadMarriageRule(resolver,new OperatorResolver());
		inputParams.put("gender", "M");
		RuleResult ruleResult = engine.run(rule, inputParams);
		assertTrue("result must be false", !ruleResult.isResult());

		Fact factOverAge = new DynamicFact<Integer>("age", (params, ctx) ->  {return 22;});

		resolver.registerFact(factOverAge);
		rule = loadMarriageRule(resolver,new OperatorResolver());
		ruleResult = engine.run(rule, inputParams);
		assertTrue("result must be true", ruleResult.isResult());
	}

	@Test
	public void testNewRule() throws InvalidRuleException {
		RuleEngine engine = new RuleEngine();
		FactResolver factResolver=new FactResolver();
		Fact merchantId=new Fact("merchant_ids") {
			@Override
			public Object get(Map params, RuleContext ctx) {
				return "[2]";
			}
		};

		Fact order=new Fact("order") {
			@Override
			public Object get(Map params, RuleContext ctx) {
				return null;
			}
		};

		Fact customer_id=new Fact("customer_id") {
			@Override
			public Object get(Map params, RuleContext ctx) {
				return "4394455";
			}
		};
		factResolver.registerFact(merchantId);
		factResolver.registerFact(order);
		factResolver.registerFact(customer_id);

		OperatorResolver operatorResolver=new OperatorResolver();

		Operator present=new Operator("present") {
			@Override
			public boolean compare(Object left, Object right) {
				return true;
			}
		};
		Operator<Object> hasCommon=new Operator<Object>("hasCommon") {
			@Override
			public boolean compare(Object left, Object right)  {
				ObjectMapper objectMapper=new ObjectMapper();
				try {

					JsonArray rightJsonArray=(JsonArray)right;
					JsonArray leftJsonArray=(JsonArray) (new JsonParser().parse((String)left));

					HashSet<Object> set=new HashSet();

					for(int i=0;i<rightJsonArray.size();i++){
						System.out.println(set.add(rightJsonArray.get(i))+" "+rightJsonArray.get(i));
					}
					for(int i=0;i<leftJsonArray.size();i++){
						if(set.contains(leftJsonArray.get(i))){
							return true;
						}
						System.out.println(set.add(leftJsonArray.get(i))+" "+leftJsonArray.get(i));
					}
				}
				catch (Exception e){
					System.out.println(e);
				}


				return false;
			}
		};

		operatorResolver.registerOperator(present);
		operatorResolver.registerOperator(hasCommon);
		RuleLoader loader = new RuleLoader(factResolver,operatorResolver);

		Rule rule=loader.loadByName("testrules/testrule");
		RuleResult ruleResult = engine.run(rule, null);
		assertTrue("result must be true", ruleResult.isResult());

	}
	private FactResolver createFactResolver() {
		FactResolver resolver = new FactResolver();
		Fact fact = new DynamicFact<Integer>("age", (params, ctx) ->  {return 12;});
		resolver.registerFact(fact);
		return resolver;
	}

	private Rule loadMarriageRule(FactResolver resolver,OperatorResolver operatorResolver) throws InvalidRuleException {
		RuleLoader loader = new RuleLoader(resolver,operatorResolver);
		return loader.loadByName("marriageRule");
	}

	private Rule createMarriageRule() {
		Condition maleAgeCondition = new Condition<>("age", new GTEOperator("GTE"), 21);
		Condition maleGenderCondition = new Condition<>("gender", new EQOperator("EQ"), "M");
		Condition maleMariageable = new Condition<>(maleAgeCondition, maleGenderCondition);

		Condition femaleAgeCondition = new Condition<>("age", new GTEOperator("GTE"), 18);
		Condition femaleGenderCondition = new Condition<>("gender", new EQOperator("EQ"), "F");
		Condition femaileMarriageable =  new Condition<>(femaleAgeCondition, femaleGenderCondition);

		Condition marriageableCondition = new Condition<>(ConditionOperator.OR, maleMariageable, femaileMarriageable);

		Rule rule = new Rule(marriageableCondition);
		rule.setName("marriageRule");

		rule.addSuccessHandler(ctx -> System.out.println("passed"));
		rule.addFailureHandler(ctx -> System.out.println("failed"));

		return rule;
	}

}
