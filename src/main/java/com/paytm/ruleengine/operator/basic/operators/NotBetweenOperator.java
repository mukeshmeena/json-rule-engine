package com.paytm.ruleengine.operator.basic.operators;

import com.paytm.ruleengine.operator.Operator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NotBetweenOperator extends Operator<Object> {

    public NotBetweenOperator(String name) {
        super(name);
    }

    @Override
    public boolean compare(Object left, Object right) {
        Set set = getSet(right);

        if (set.size() != 2) {
            // throw new InvalidParameterException("Right parameter must be a set of 2");
        }

        List<Double> doubleList = new ArrayList<>();

        for (Object obj : set) {
            doubleList.add(getNumber(obj).doubleValue());
        }

        Double[] doubleArray = new Double[2];
        doubleArray = doubleList.toArray(doubleArray);

        Double leftDouble = getNumber(left).doubleValue();

        boolean between;

        if (doubleArray[0] < doubleArray[1]) {
            between = (leftDouble >= doubleArray[0] && leftDouble <= doubleArray[1]);
        } else {
            between = (leftDouble >= doubleArray[1] && leftDouble <= doubleArray[0]); // no code coverage
        }

        return !between;

    }

}
