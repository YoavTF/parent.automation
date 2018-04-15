package com.cedex.jsystem;

import jsystem.framework.report.ListenerstManager;
import jsystem.framework.report.Reporter;

/**
 * Reporter Light interface <br>
 * 
 * Last changed: $LastChangedDate: 2012-09-08 11:57:51$
 * 
 * @author $Author: romang $
 * @version $Revision: 8801 $
 * 
 */
public interface ReporterLight {

	  /**
	   * a reference to the report mechanism. Used to add reports to the log
	   */

	  public static Reporter report = ListenerstManager.getInstance();

}
