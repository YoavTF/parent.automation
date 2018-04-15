package com.cedex.converters;

import java.text.DecimalFormat;

/**
 * float formatter <br>
 * 
 * Last changed: $LastChangedDate: 2012-09-08 11:57:51$
 * 
 * @author $Author: romang $
 * @version $Revision: 9092 $
 * 
 */
public class FloatFormatter {

	/**
	 * function converts received time in seconds to minutes
	 * 
	 * @param floatForFormat
	 *            number for formating
	 * @param - roundPlace num of digits after the dot
	 * 
	 */
	public static float format(float floatForFormat, int roundPlace) {

		String numFormat = "#.";
		for (int i = 0; i < roundPlace; i++) {
			numFormat = numFormat + "#";
		}

		DecimalFormat newFormat = new DecimalFormat(numFormat);

		float floatFormatted = Float.valueOf(newFormat.format(floatForFormat));

		return floatFormatted;

	}
}
