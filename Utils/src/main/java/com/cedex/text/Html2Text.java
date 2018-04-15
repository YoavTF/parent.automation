package com.cedex.text;

import org.jsoup.Jsoup;

public class Html2Text {

	/**
	 * converts html style test to readable Plain text
	 * 
	 * @param html
	 * @return
	 */
	public static String toPlainText(String html) {
		return Jsoup.parse(html).text();
	}
}
