package com.cedex.fortests;


import com.cedex.jsystem.ReporterLight;
import jsystem.framework.sut.SutFactory;

/**
 * Tests utilities class<br>
 * 
 * Last changed: $LastChangedDate: 2012-09-08 11:57:51$
 * 
 * @author $Author: romang $
 * @version $Revision: 9719 $
 * 
 */
public abstract class SutUtils implements ReporterLight {

	/**
	 * Function replays current SUT name (without .xml )
	 * 
	 * 
	 * 
	 */
	public static String getCurrentSUTName() {
		String usingSUT = "NOT_FOUND";

		String sut = SutFactory.getInstance().getSutInstance().getSetupName();

		if (sut != null) {
			String[] usingSUTAsArr = sut.split(".xml");
			usingSUT = usingSUTAsArr[0];
			report.report("Using SUT file: " + usingSUT);

		}
		return usingSUT;
	}
}
