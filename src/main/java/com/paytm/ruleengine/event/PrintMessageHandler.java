package com.paytm.ruleengine.event;

import com.paytm.ruleengine.rule.RuleContext;

public class PrintMessageHandler implements RuleEventHandler {

	private String type;
	
	@Override
	public void handle(RuleContext ctx) {
		
		System.out.println("");
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
