package com.paytm.ruleengine.fact;

import java.util.HashMap;
import java.util.Map;

public class FactResolver {
  
  private Map<String, Fact<?>> facts = new HashMap<>();
  
  public void registerFact(Fact<?> fact) {
    this.facts.put(fact.getName(), fact);
  }
  
  public Fact<?> resolve(String factName) {
    return this.facts.get(factName);
  }

}
