package com.paytm.ruleengine.operator.basic.operators;

import com.paytm.ruleengine.operator.Operator;

public class NEOperator extends Operator<Object> {

    public NEOperator(String name) {
        super(name);
    }

    @Override
    public boolean compare(Object left, Object right) {
        left = getDoubleValue(left);
        right = getDoubleValue(right);
        return !left.equals(right);
    }
}
