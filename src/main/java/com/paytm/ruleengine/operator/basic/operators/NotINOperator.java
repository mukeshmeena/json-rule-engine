package com.paytm.ruleengine.operator.basic.operators;

import com.paytm.ruleengine.operator.Operator;

import java.util.Set;

public class NotINOperator extends Operator<Object> {

    public NotINOperator(String name) {
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
        return !valueMatched;
    }

}
