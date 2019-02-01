package com.paytm.ruleengine.operator.basic.operators;

import com.paytm.ruleengine.operator.Operator;

public class GTOperator extends Operator<Number> {

    public GTOperator(String name) {
        super(name);
    }

    @Override
    public boolean compare(Number left, Number right) {
        return left.doubleValue() > right.doubleValue();
    }
}
