/**
 * 
 */
package com.cedex.Windows;

import com.cedex.LocalMachineInterface;
import jsystem.framework.system.SystemObjectImpl;

import java.net.InetAddress;

/**
 * this class represents a InteliJ IDE machine that a jsystem running on and InteliJ IDE
 * (env1auto,env2auto ...) <br>
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
public class WindowsLocal extends SystemObjectImpl implements LocalMachineInterface {

	  /**
	   * get ip address from hostname on local windows machine
	   * 
	   * @param hostName
	   *              -
	   * @return ip address
	   * @throws Exception
	   */
	  @Override
	  public String getIpAddressFromHostName(String hostName) throws Exception {
			InetAddress address = InetAddress.getByName(hostName);
			String ip = address.getHostAddress();

			return ip;
	  }

	  /**
	   * get machine hostname
	   * 
	   * @throws Exception
	   **/
	  @Override
	  public String getHostName() throws Exception {
			return InetAddress.getLocalHost().getHostName();
	  }

	  /**
	   * get automation machine local ip address
	   * 
	   * @return
	   * @throws Exception
	   */
	  public String getIpAddress() throws Exception {
			String hostName = this.getHostName();
			return getIpAddressFromHostName(hostName);
	  }
}
