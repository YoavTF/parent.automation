package com.cedex.xml;

import com.cedex.files.FilesManipulations;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.*;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Xml Parser class<br>
 * 
 * Last changed: $LastChangedDate: 2012-09-08 11:57:51$
 * 
 * @author $Author: romang $
 * @version $Revision: 9702 $
 * 
 */
public class XmlParser {
	/**
	 * The parsed XML tree.
	 */
	protected Document _doc;

	/**
	 * received xml File as input and reads it to string
	 * 
	 * @param xmlFilePath
	 *            full path of xml file
	 * @param xmlFileName
	 *            - the xml file itself
	 * @throws IOException
	 */
	public XmlParser(String xmlFilePath, String xmlFileName) throws IOException {
		String xmlFileContent = FilesManipulations.readFileAsString(xmlFilePath + File.separator + xmlFileName);
		init(xmlFileContent);
	}

	public XmlParser(String xmlText) {
		init(xmlText);
		// try {
		// // Parse the XML document
		// DOMParser parser = new DOMParser();
		// parser.parse(new InputSource(new StringReader(xmlText)));
		// _doc = parser.getDocument();
		// } catch (Exception e) {
		// throw new IllegalArgumentException("Error loading XML: \n" +
		// e.getClass().getName() + ": " + e.getMessage());
		// }
	}

	private void init(String xmlText) {
		try {
			// Parse the XML document
			DOMParser parser = new DOMParser();
			parser.parse(new InputSource(new StringReader(xmlText)));
			_doc = parser.getDocument();
		} catch (Exception e) {
			throw new IllegalArgumentException("Error loading XML: \n" + e.getClass().getName() + ": " + e.getMessage());
		}
	}

	/**
	 * Function looking for node with specific attribute and specific value in
	 * XML String and returns looking for attribute's value
	 * 
	 * @param
	 *            xmlRecords - XML file as String,
	 * @param
	 *            expectedNode -looking for XML Node
	 * @param
	 *            expectedAttribute -looking for XML NODE with specific
	 *            Attribute
	 * @param
	 *            expectedAttributeValue - expectedAttribute attribute should be
	 *            equals to that value
	 * @param
	 *            lookingForAttrbute - value of this attribute will be returned
	 * @return String value of looking for attribute
	 */
	public String getNodeAttributeValueForXmlString(String xmlRecords, String expectedNode, String expectedAttribute,
			String expectedAttributeValue, String lookingForAttrbute) throws Exception {
		String attributeValue = "NOT_FOUND";
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xmlRecords));

			Document doc = db.parse(is);
			// NodeList nodes = doc.getElementsByTagName("CG");
			NodeList nodes = doc.getElementsByTagName(expectedNode);

			// iterate the employees
			for (int i = 0; i < nodes.getLength(); i++) {
				Element element = (Element) nodes.item(i);

				// NodeList name = element.getElementsByTagName(expectedNode);
				// Element line = (Element) name.item(0);
				if (element.getAttribute(expectedAttribute).equals(expectedAttributeValue)) {
					attributeValue = element.getAttribute(lookingForAttrbute);
					System.out.println("Element: '" + expectedNode + "' with attribute: " + expectedAttribute + "='"
							+ expectedAttributeValue + "' found attribute " + lookingForAttrbute + " ='"
							+ attributeValue + "'");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return attributeValue;
	}

	/**
	 * Function looking for node with specific attribute and specific value in
	 * XML String and returns looking for attribute's value
	 * 
	 * @param
	 *            xmlRecords - XML file as String,
	 * @param
	 *            expectedNode -looking for XML Node
	 * @return String value of looking for attribute
	 */
	public String getNodeValueForXmlString(String xmlRecords, String expectedNode) throws Exception {
		String elementValue = "NOT_FOUND";
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xmlRecords));

			Document doc = db.parse(is);
			NodeList nodes = doc.getElementsByTagName(expectedNode);

			// iterate the employees
			for (int i = 0; i < nodes.getLength(); i++) {
				Element element = (Element) nodes.item(i);
				// Node node = (Node) nodes.item(i);
				Node frsNode = element.getFirstChild();
				if (frsNode != null) {
					elementValue = frsNode.getNodeValue();
					System.out.println("Element: '" + expectedNode + " ='" + elementValue + "'");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return elementValue;
	}

	/**
	 * @param
	 *            xpath - path to specific node
	 * @param
	 *            mapKeyAppend - appends to map key
	 * @return an ordered Map of keys to values rooted at the specified path,
	 *         and defined by the specified relative key and value paths.
	 */
	public Map<String, Map<String, String>> getMap(String xpath, String mapKeyAppend) {
		Map<String, Map<String, String>> map = new LinkedHashMap<String, Map<String, String>>();
		map = getListAttributesPerNode(xpath, mapKeyAppend);

		return map;
	}

	/**
	 * @param
	 *            xpath - path to specific node
	 *
	 * @return an ordered Map of keys to values rooted at the specified path,
	 *         and defined by the specified relative key and value paths.
	 */
	public Map<String, Map> getXmlAsNode(String xpath) {
		Map<String, Map> map = new LinkedHashMap<String, Map>();
		NodeIterator nodeIterator = null;
		try {

			nodeIterator = XPathAPI.selectNodeIterator(_doc, xpath);
			// String foundString = "NOT_FOUND";

			for (Node node = nodeIterator.nextNode(); node != null; node = nodeIterator.nextNode()) {
				NamedNodeMap nodeElement = node.getAttributes();
				String mapKey = node.getNodeName();
				Map<String, String> subMap = new LinkedHashMap<String, String>();
				for (int j = 0; j < nodeElement.getLength(); j++) {
					String nodeValue = nodeElement.item(j).getNodeValue();
					String nodeName = nodeElement.item(j).getNodeName();
					// if (nodeName.equals(expectedAttributeName)) {
					// // Start collectiing values
					// mapKey = "CGID_" + nodeValue;
					//
					// }
					subMap.put(nodeName, nodeValue);

				}
				map.put(mapKey, subMap);

			}

		} catch (Exception e) {
			throw new IllegalArgumentException("Error retrieving from XPath: " + xpath + "\n" + e.getClass().getName()
					+ ": " + e.getMessage());
		}

		return map;
	}

	/**
	 * function builds a map of of specific node's attributes (Names->Valus) to
	 * recognize that Node supply expectedAttribute and expectedAttributeValue
	 * 
	 * @param xpath
	 *            - path of looking for node in the XML string (looks like:
	 *            /xmlroot )
	 * @param expectedAttributeName
	 *            and expectedAttributeValue - for recognition of specific node
	 *            if there are many of those for example get map of all
	 *            attributes fro cg that id=1 <ReportCg>
	 *            "<CG id="1" lag="60000000
	 *            "   name="CG-1" volumes="10" syncMode="
	 *            full" journal_size = "10000000" restore_jnlg = "08" > </CG>"
	 *            "<CG id="
	 *            3" lag="100000000"  name="CG-3" volumes="10" syncMode="
	 *            full" journal_size = "10000000" restore_jnlg = "08" > </CG>"
	 *            <ReportCg> xpath=/ReportCg/CG expectedAttributeName=id
	 *            expectedAttributeValue=1
	 * 
	 * @return a List of all values found at the specified path.
	 */
	public Map<String, Map<String, String>> getListAttributesPerNode(String xpath, String expectedAttributeName) {
		try {

			Map<String, Map<String, String>> map = new LinkedHashMap<String, Map<String, String>>();

			NodeIterator i = XPathAPI.selectNodeIterator(_doc, xpath);
			// String foundString = "NOT_FOUND";

			for (Node nd = i.nextNode(); nd != null; nd = i.nextNode()) {
				NamedNodeMap nodeElement = nd.getAttributes();
				String mapKey = "NOT_FOUND";
				Map<String, String> subMap = new LinkedHashMap<String, String>();
				for (int j = 0; j < nodeElement.getLength(); j++) {
					String nodeValue = nodeElement.item(j).getNodeValue();
					String nodeName = nodeElement.item(j).getNodeName();
					if (nodeName.equals(expectedAttributeName)) {
						// Start collectiing values
						mapKey = "CGID_" + nodeValue;

					}
					subMap.put(nodeName, nodeValue);

				}
				map.put(mapKey, subMap);

			}
			return map;
		} catch (Exception e) {
			throw new IllegalArgumentException("Error retrieving from XPath: " + xpath + "\n" + e.getClass().getName()
					+ ": " + e.getMessage());
		}
	}

	/**
	 * @return the value of the specified Node.
	 */
	protected String getValue(Node nd) throws Exception {
		if (nd == null)
			return null;

		// Element
		if (nd.getNodeType() == Node.ELEMENT_NODE) {
			String flattenedValue = "";
			nd.normalize();
			NodeIterator i = XPathAPI.selectNodeIterator(nd, "descendant::text()");
			for (Node t = i.nextNode(); t != null; t = i.nextNode()) {
				flattenedValue += t.getNodeValue();
			}
			return flattenedValue;
		}

		// Non-element
		else
			return nd.getNodeValue();
	}

}
