package com.cedex.feature.waiter;

public interface WaiterInterface {

	  /**
	   * wait for some state reached
	   * 
	   * @param maxTimeToWaitInMin
	   *              - max time to wait (in min)
	   * @throws Exception
	   */
	  public void waitFor(int maxTimeToWaitInMin) throws Exception;

	  /**
	   * wait for some state reached
	   * 
	   * @param maxTimeToWaitInMin
	   *              - max time to wait (in min)
	   * @param delayTimeInSec
	   *              delay timeout (in sec)
	   * @throws Exception
	   */
	  public void waitFor(int maxTimeToWaitInMin, int delayTimeInSec) throws Exception;

	  /**
	   * is condition of stop loop in func: {@link WaiterInterface#waitFor}
	   * reached
	   * 
	   * @return
	   * @throws Exception
	   */
	  public boolean isLoopStopCondition() throws Exception;

}
