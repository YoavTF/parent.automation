package com.cedex;

import java.util.HashMap;
import java.util.Map;

/**
 * global parameters located here <br>
 * Last changed: $LastChangedDate: 2012-10-24 17:16:29 +0200 (Wed, 24 Oct 2012)
 * $
 *
 * @author $Author: romang $
 * @version $Revision: 12203 $
 */
public interface GlobalParameters {

    public final static String notFound = "NOT_FOUND";
    public final static String notDefined = "NOT_DEFINED";
    public final static String notSet = "NOT_SET";
    public final static String empty = "EMPTY";
    public final static String noTime = "noTime";

    public static enum TryCatchLocation {
        FROM_TRY, FROM_CATCH;
    }

    public static enum ToggleOnOff {
        ON, OFF;

        /**
         * convert from TOGGLE ON or OFF <br>
         * to int (TOGGLE OFF for 0<br>
         * TOGGLE ON for 1)
         *
         *
         * @return
         */
        public int enumToInt() {
            if (this == ToggleOnOff.ON) {
                return 1;
            } else {
                return 0;
            }
        }

        /**
         * convert from int status <br>
         * (0 or 1 possible only) to TOGGLE ON or OFF
         *
         * @param intToggle
         * @return
         */
        public static ToggleOnOff int2Enum(int intToggle) {
            if (intToggle == 0) {
                return ToggleOnOff.OFF;
            } else {
                return ToggleOnOff.ON;
            }
        }

        /**
         * convert from string status <br>
         * (only string on or off are available)
         *
         * @param strToggle values on or off (case not sensitive)
         * @return
         */
        public static ToggleOnOff str2Enum(String strToggle) {
            if (strToggle.equalsIgnoreCase(ToggleOnOff.OFF.toString())) {
                return ToggleOnOff.OFF;
            } else {
                return ToggleOnOff.ON;
            }
        }
    }

    public static enum ToggleYesNo {
        NO("no"), YES("yes");

        private final String toggleYesNo;

        private ToggleYesNo(String toggleYesNo) {
            this.toggleYesNo = toggleYesNo;
        }

        private String getToggleYesNo() {
            return toggleYesNo;
        }

        private static final Map<String, ToggleYesNo> lookup = new HashMap<String, ToggleYesNo>();

        static {
            for (ToggleYesNo currtoggleYesNo : ToggleYesNo.values())
                lookup.put(currtoggleYesNo.getToggleYesNo().toLowerCase(), currtoggleYesNo);
        }

        public static ToggleYesNo getEnumFromString(String toggleYesNo) {
            return lookup.get(toggleYesNo.toLowerCase());
        }

    }

    public static enum BooleanValueWithIgnore {
        TRUE(true), FALSE(false), IGNORE(false);

        boolean booleanValue;

        private BooleanValueWithIgnore(boolean booleanValue) {
            this.booleanValue = booleanValue;
        }

        public boolean getBooleanValue() {
            return booleanValue;
        }

        public static BooleanValueWithIgnore booleanToEnum(boolean theBooleanToConvert) {
            if (theBooleanToConvert) {
                return BooleanValueWithIgnore.TRUE;
            } else {
                return BooleanValueWithIgnore.FALSE;
            }
        }
    }

    public static enum TryValidationEnum {
        NOT_SET,FROM_TRY, FROM_CATCH;
    }
    public static enum BooleanValue {
        TRUE, FALSE
    }

    public static enum SizeUsage {
        USED, FREE, TOTAL;
    }
}
