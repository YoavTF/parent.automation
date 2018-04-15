/**
 * 
 */
package com.cedex;

import com.cedex.Linux.LinuxLocal;
import com.cedex.OsCheck.OSType;
import com.cedex.Windows.WindowsLocal;

/**
 * function of local windows/linux (jsystem or Eclipse running on) classes aggregated here (
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
public class OsLocal implements GlobalParameters, LocalMachineInterface {

	  OSType currOs = OsCheck.getOperatingSystemType();

	  /**
	   * get ip address for received host name
	   * 
	   * @return
	   * @throws Exception
	   */
	  @Override
	  public String getIpAddressFromHostName(String hostName) throws Exception {
			String ip = notFound;
			// OSType currOs = OsCheck.getOperatingSystemType();
			switch (currOs) {
				  case Windows: {
						ip = new WindowsLocal().getIpAddressFromHostName(hostName);
						break;
				  }
				  case Linux: {
						ip = new LinuxLocal().getIpAddressFromHostName(hostName);
						break;
				  }
				  default: {
						throw new Exception("unKnown option os: " + currOs);
				  }

			}

			return ip;
	  }

	  /**
	   * get machine hostname
	   * 
	   * @throws Exception
	   **/
	  @Override
	  public String getHostName() throws Exception {
			String hostName = notFound;
			switch (currOs) {
				  case Windows: {
						hostName = new WindowsLocal().getHostName();
						break;
				  }
				  case Linux: {
						hostName = new LinuxLocal().getHostName();
						break;
				  }
				  default: {
						throw new Exception("unKnown option os: " + currOs);
				  }

			}

			return hostName;
	  }

	  /**
	   * get automation machine local ip address
	   * 
	   * @return
	   * @throws Exception
	   */
	  @Override
	  public String getIpAddress() throws Exception {
			String ip = notFound;
			// OSType currOs = OsCheck.getOperatingSystemType();
			switch (currOs) {
				  case Windows: {
						ip = new WindowsLocal().getIpAddress();
						break;
				  }
				  case Linux: {
						ip = new LinuxLocal().getIpAddress();
						break;
				  }
				  default: {
						throw new Exception("unKnown option os: " + currOs);
				  }

			}

			return ip;
	  }
}
