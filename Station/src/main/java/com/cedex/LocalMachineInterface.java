/**
 * 
 */
package com.cedex;

/**
 * all common func for local/windows machine located here
 * 
 * Last changed: $LastChangedDate: 2012-09-08 11:57:51$ <br>
 * Author: $Author: romang $<br>
 * Svn Version: $Revision: 9784 $ <br>
 * 
 * @author $Author: romang $
 * @version $Revision: 9784 $
 * 
 */
/**
 * @author Romang
 * 
 */
public interface LocalMachineInterface {

	public static enum SortFileOrder {
		ASC, DESC;
	}

	public static enum SortFileBy {
		SIZE, DATE_CREATED, DATE_MODIFIED;
	}
	  /**
	   * get ip address for received host name
	   * 
	   * @return
	   * @throws Exception
	   */
	  public String getIpAddressFromHostName(String hostName) throws Exception;

	  /**
	   * get machine hostname
	   * 
	   * @throws Exception
	   **/
	  public String getHostName() throws Exception;

	  /**
	   * get automation machine local ip address
	   * 
	   * @return
	   * @throws Exception
	   */
	  public String getIpAddress() throws Exception;
}
