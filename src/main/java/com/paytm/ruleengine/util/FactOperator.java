package com.paytm.ruleengine.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public enum FactOperator implements Serializable {
	    GT {
	        @Override
	        public Boolean compare(Object left, Object right) {
	            Number leftNumber = getNumber(left);
	            Number rightNumber = getNumber(right);
	            return leftNumber.doubleValue() > rightNumber.doubleValue();
	        }
	    },
	    LT {
	        @Override
	        public Boolean compare(Object left, Object right) {
	            return GT.compare(right, left);
	        }
	    },
	    GTE {
	        @Override
	        public Boolean compare(Object left, Object right) {
	            Number leftNumber = getNumber(left);
	            Number rightNumber = getNumber(right);
	            return leftNumber.doubleValue() >= rightNumber.doubleValue();
	        }
	    },
	    LTE {
	        @Override
	        public Boolean compare(Object left, Object right) {
	            return GTE.compare(right, left);
	        }
	    },
	    EQ {
	        @Override
	        public Boolean compare(Object left, Object right) {
	            left = getDoubleValue(left);
	            right = getDoubleValue(right);
	            return left.equals(right);
	        }
	    },
	    NE {
	        @Override
	        public Boolean compare(Object left, Object right) {
	            return !EQ.compare(left, right);
	        }
	    },
	    IN {
	        @Override
	        @SuppressWarnings("unchecked")
	        public Boolean compare(Object left, Object right) {
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
	    },
	    NOT_IN {
	        @Override
	        public Boolean compare(Object left, Object right) {
	            return !IN.compare(left, right);
	        }
	    },
	    BETWEEN {
	        @Override
	        public Boolean compare(Object left, Object right) {
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

	            return between;
	        }
	    },
	    NOT_BETWEEN {
	        @Override
	        public Boolean compare(Object left, Object right) {
	            return !BETWEEN.compare(left, right);
	        }
	    };

	    public abstract Boolean compare(Object left, Object right);

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
