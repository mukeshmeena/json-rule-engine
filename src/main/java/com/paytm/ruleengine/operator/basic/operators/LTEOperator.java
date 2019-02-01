package com.paytm.ruleengine.operator.basic.operators;

import com.paytm.ruleengine.operator.Operator;

public class LTEOperator extends Operator<Number> {

    public LTEOperator(String name) {
        super(name);
    }

    @Override
    public boolean compare(Number left, Number right) {
        return left.doubleValue() <= right.doubleValue();
    }
}
