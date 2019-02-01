package com.paytm.ruleengine.operator;

import com.paytm.ruleengine.operator.basic.operators.*;

import java.util.HashMap;
import java.util.Map;

public class OperatorResolver {
    private Map<String,Operator> operatorMap=getDefaultOperatorsMap();

    public void registerOperator(Operator operator){
        operatorMap.put(operator.getName(),operator);
    }

    public Operator resolve(String operatorName){
        return operatorMap.get(operatorName);
    }

    private Map<String,Operator>  getDefaultOperatorsMap() {
        Map<String,Operator> defaultOperatorMap=new HashMap<>();
        LTOperator ltOperator=new LTOperator("LT");
        defaultOperatorMap.put(ltOperator.getName(),ltOperator);

        GTOperator gtOperator=new GTOperator("GT");
        defaultOperatorMap.put(gtOperator.getName(),gtOperator);

        LTEOperator lteOperator=new LTEOperator("LTE");
        defaultOperatorMap.put(lteOperator.getName(),lteOperator);

        GTEOperator gteOperator=new GTEOperator("GTE");
        defaultOperatorMap.put(gteOperator.getName(),gteOperator);

        EQOperator eqOperator=new EQOperator("EQ");
        defaultOperatorMap.put(eqOperator.getName(),eqOperator);

        BetweenOperator betweenOperator=new BetweenOperator("BETWEEN");
        defaultOperatorMap.put(betweenOperator.getName(),betweenOperator);

        NotBetweenOperator notBetweenOperator=new NotBetweenOperator("NOT_BETWEEN");
        defaultOperatorMap.put(notBetweenOperator.getName(),notBetweenOperator);

        INOperator inOperator=new INOperator("IN");
        defaultOperatorMap.put(inOperator.getName(),inOperator);

        NotINOperator notINOperator=new NotINOperator("NOT_IN");
        defaultOperatorMap.put(notINOperator.getName(),notINOperator);

        NEOperator neOperator=new NEOperator("NE");
        defaultOperatorMap.put(neOperator.getName(),neOperator);

        return defaultOperatorMap;
    }

}
