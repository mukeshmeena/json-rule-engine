package com.paytm.ruleengine.operator.basic.operators;

import com.paytm.ruleengine.operator.Operator;

public class EQOperator extends Operator<Object> {

    public EQOperator(String name) {
        super(name);
    }

    @Override
    public boolean compare(Object left, Object right) {
        left = getDoubleValue(left);
        right = getDoubleValue(right);
        return left.equals(right);
    }
}
