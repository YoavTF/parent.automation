package com.cedex.numbers;

import com.cedex.jsystem.ReporterLight;

import java.util.HashMap;
import java.util.Map;

public class IntegerUtils implements ReporterLight {

    public static enum IntegerOperator {
        EQUALS("EQUALS"), NOT_EQUALS("NOT_EQUALS"), GREATER("GREATER"), GREATER_EQUALS("GREATER_EQUALS"), LESS("LESS"), LESS_EQUALS("LESS_EQUALS");

        private String integerOperator;

        private IntegerOperator(String integerOperator) {
            this.integerOperator = integerOperator;
        }

        private String getIntegerOperator() {
            return integerOperator;
        }

        private static final Map<String, IntegerOperator> lookup = new HashMap<String, IntegerOperator>();
        static {
            for (IntegerOperator currStatus : IntegerOperator.values())
                lookup.put(currStatus.getIntegerOperator(), currStatus);
        }

        public static IntegerOperator getEnumFromString(String dgStatus) {
            return lookup.get(dgStatus);
        }
    }

    public static boolean integerCompare(int int1, int int2, IntegerOperator integerOperator) {
        boolean isIntegerAreTrue = false;
        switch (integerOperator) {
            case NOT_EQUALS: {
                if (int1 != int2) {
                    isIntegerAreTrue = true;
                }
                break;
            }
            case GREATER: {
                if (int1 > int2) {
                    isIntegerAreTrue = true;
                }
                break;
            }
            case GREATER_EQUALS: {
                if (int1 >= int2) {
                    isIntegerAreTrue = true;
                }
                break;
            }
            case LESS: {
                if (int1 < int2) {
                    isIntegerAreTrue = true;
                }
                break;
            }
            case LESS_EQUALS: {
                if (int1 <= int2) {
                    isIntegerAreTrue = true;
                }
                break;
            }
            default: {
                if (int1 == int2) {
                    isIntegerAreTrue = true;
                }
            }
        }
        report.report(" Compare: "+int1+" and "+int2 +" should be: "+integerOperator +" result is: "+isIntegerAreTrue);
        return isIntegerAreTrue;
    }
}
