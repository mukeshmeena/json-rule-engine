package com.paytm.ruleengine.operator;

import java.util.Set;

public abstract class Operator <T> {
    private String name;

    public Operator(String name){
        this.name=name;
    }

    public abstract boolean compare(T left,T right);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    protected Number getNumber(Object obj) {
        Number number=null;
        if (obj instanceof Number) {
            number = (Number) obj;
        } else {
            // lets see if it's a DateTime instead
            //	            try {
            //	                DateTime dateTime = DateTime.parse(obj.toString());
            //	                number = dateTime.getMillis();
            //	            } catch (IllegalArgumentException ex) {
            //	                throw new InvalidParameterException(obj.toString() + " is not a number or date. Unable to compare", ex);
            //	            }
        }
        return number;
    }

    protected Object getDoubleValue(Object obj) {
        if (obj instanceof Number) {
            Number number = (Number) obj;
            obj = number.doubleValue();
        }
        return obj;
    }

    protected Set getSet(Object param) {
        Set set=null;
        if (param instanceof Set) {
            set = (Set) param;
        } else {
            // throw new InvalidParameterException("Parameter must be a Set");
        }
        return set;
    }
}
