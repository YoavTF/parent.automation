package com.cedex.files;

import java.io.*;

/**
 * Files manipulations class<br>
 * 
 * Last changed: $LastChangedDate: 2012-09-08 11:57:51$
 * 
 * @author $Author: romang $
 * @version $Revision: 9766 $
 * 
 */
public class FilesManipulations {

	public static String readFileAsString(String filePath) throws IOException {

		StringBuffer fileData = new StringBuffer(1000);

		BufferedReader reader = new BufferedReader(

		new FileReader(filePath));

		char[] buf = new char[1024];

		int numRead = 0;

		while ((numRead = reader.read(buf)) != -1) {

			String readData = String.valueOf(buf, 0, numRead);

			fileData.append(readData);

			buf = new char[1024];

		}

		reader.close();

		return fileData.toString();

	}

	/**
	 * writes any sting to the file
	 * 
	 * @param inputFile
	 *            - the file itself
	 * @param inputString
	 *            - the string to write
	 * @param append
	 *            append string or overwrite <br>
	 *            if true will append new string into a file<br>
	 *            else will delete the file and add string to the new file
	 * @throws Exception
	 */
	public static void writeToFile(File inputFile, String inputString, boolean append) throws Exception {
		try {

			FileWriter fw = new FileWriter(inputFile, append);
			fw.write(inputString + "\n");
			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * writes any sting to the file
	 * 
	 * @param inputFile
	 *            - the name of the file
	 * @param inputString
	 *            - the string to write
	 * @param append
	 *            append string or overwrite <br>
	 *            if true will append new string into a file<br>
	 *            else will delete the file and add string to the new file
	 * @throws Exception
	 */
	public static void writeToFile(String inputFileName, String inputString, boolean append) throws Exception {
		try {

			FileWriter fw = new FileWriter(inputFileName, append);
			fw.write(inputString + "\n");
			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
