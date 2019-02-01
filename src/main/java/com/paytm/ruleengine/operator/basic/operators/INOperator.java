package com.paytm.ruleengine.operator.basic.operators;

import com.paytm.ruleengine.operator.Operator;

import java.util.Set;

public class INOperator extends Operator<Object> {

    public INOperator(String name) {
        super(name);
    }

    @Override
    public boolean compare(Object left, Object right) {
        Set set = getSet(right);
        Number leftNumber = getNumber(left);
        boolean valueMatched = false;
        for (Object setValueObject : set) {
            Number setValue = getNumber(setValueObject);
            if (setValue.doubleValue() == leftNumber.doubleValue()) {
                valueMatched = true;
                return valueMatched;
            }
        }
        return valueMatched;
    }

}
