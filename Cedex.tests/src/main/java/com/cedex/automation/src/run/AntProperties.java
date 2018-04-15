package com.cedex.automation.src.run;

import jsystem.framework.RunProperties;
import jsystem.framework.common.CommonResources;
import jsystem.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * API to manipulate with ant.ptoperties (build in) file
 * 
 * Last changed: $LastChangedDate: 2012-09-08 11:57:51$ <br>
 * Author: $Author: romang $<br>
 * Svn Version: $Revision: 8917 $ <br>
 * 
 * @author $Author: romang $
 * @version $Revision: 8917 $
 * 
 */

public class AntProperties {

	private static Logger log = Logger.getLogger(RunProperties.class.getName());
	private static AntProperties ap = null;

	private AntProperties() {
		// singleton
	}

	public static AntProperties getInstance() {
		if (ap == null) {
			ap = new AntProperties();
		}
		return ap;
	}

	/**
	 * The properties file
	 */
	private File antPropertiesFile = new File(CommonResources.ANT_INTERNAL_PROPERTIES_FILE);

	/**
	 * Get run property. Can be set by different test. and valid for the all
	 * run.
	 * 
	 * @param key
	 *            the property name
	 * @return the property value
	 * @throws IOException
	 */
	public synchronized String getAntProperty(String key) throws IOException {
		Properties p = loadProperties();
		return p.getProperty(key);
	}

	/**
	 * Set run property. will be valid for the all run
	 * 
	 * @param key
	 *            the property name
	 * @param value
	 *            the property value
	 * @throws IOException
	 */
	public synchronized void setAntProperty(String key, String value) throws IOException {
		Properties p = loadProperties();
		p.setProperty(key, value);
		saveProperties(p);
	}

	/**
	 * Remove run property
	 * 
	 * @param key
	 *            the property name
	 * @throws IOException
	 */
	public synchronized void removeAntProperty(String key) throws IOException {
		Properties p = loadProperties();
		p.remove(key);
		saveProperties(p);
	}

	/**
	 * Get all the properties of the run
	 * 
	 * @return all the properties
	 * @throws IOException
	 */
	public Properties getAntProperties() throws IOException {
		return loadProperties();
	}

	/**
	 */
	private synchronized Properties loadProperties() throws IOException {
		Properties p = new Properties();
		if (antPropertiesFile.exists()) {
			p = FileUtils.loadPropertiesFromFile(antPropertiesFile.getAbsolutePath());
		}
		return p;
	}

	/**
	 */
	private synchronized void saveProperties(Properties props) throws IOException {
		FileUtils.savePropertiesToFile(props, antPropertiesFile.getAbsolutePath());
	}
}
