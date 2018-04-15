package com.cedex.automation.tests.actions;

import com.cedex.api.ResponseSender;
import com.cedex.automation.src.validators.RestApiValidator;
import com.cedex.automation.src.wrappers.RestApiWrapper;
import com.cedex.automation.src.wrappers.RestApiWrapper.JsonValueType;
import com.cedex.automation.tests.base.AbstractBaseTest;
import com.cedex.text.StringUtils;
import com.cedex.text.StringUtils.StringOperator;
import jsystem.framework.TestProperties;
import jsystem.framework.scenario.Parameter;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static com.cedex.numbers.IntegerUtils.IntegerOperator;

public class APIValidations extends AbstractBaseTest {


    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------------- VARIABLES ------------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------
    private int expectedResponceCode = 200;
    private StringOperator stringOperator = StringOperator.EQUALS;
    private IntegerOperator integerOperator = IntegerOperator.EQUALS;
    private ResponseSender responseSender;
    private RestApiValidator restApiValidator;
    private RestApiWrapper restApiWrapper;
    private String jsonXpath;

    public int responseNumber = 1;
    private JsonValueType jsonExpectedValueType = JsonValueType.STRING;
    private String jsonExpectedValue;


    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------
    //--------------------------------------- ACTIONS ----------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------
    @Before
    public void before() throws Exception {
        restApiWrapper = new RestApiWrapper(lab);
        restApiValidator = new RestApiValidator(lab);

    }

    @Override
    public void handleUIEvent(HashMap<String, Parameter> map, String methodName) throws Exception {
        report.report("methodName=" + methodName);
        if (methodName.equalsIgnoreCase("isResponseJsonValueEquals")) {
            for (String currKey : map.keySet()) {
                Parameter currParameter = map.get(currKey);
                String parameterName = currParameter.getName();
                report.report("parameterName=" + parameterName);
                if (parameterName.equalsIgnoreCase("jsonExpectedValueType")) {
                    String currParameterValue = currParameter.getValue().toString();
                    report.report("currParameterValue=" + currParameterValue);
                    if (currParameterValue.equalsIgnoreCase("INTEGER")) {
                        map.get("IntegerOperator").setVisible(true);
                        map.get("StringOperator").setVisible(false);
                    } else {
                        map.get("IntegerOperator").setVisible(false);
                        map.get("StringOperator").setVisible(true);
                    }
                }
            }
        }

    }

    @Test
    @TestProperties(name = "BB: Validate Response Code ${stringOperator} to ${expectedResponceCode} ", paramsExclude = {"integerOperator", "browserIndex", "jsonExpectedValueType", "jsonExpectedValue", "jsonXpath"}, returnParam = {})
    public void isResponseCodeOk() throws Exception {
        responseSender = restApiWrapper.getResponseSenderByRequestIndex(responseNumber);
        int foundResponseCode = responseSender.getResponseCode();
        String expectedResponceCodeStr = super.loadStoredParameter(expectedResponceCode + "");
        boolean isResposeCode = StringUtils.stringCompare(foundResponseCode + "", expectedResponceCodeStr + "", stringOperator);
        addSubScenarioResult("Response code ", isResposeCode);
    }

    /**
     * Validating the json response sent by BB: sendRequest
     * <p>
     * <b>XPATH value explanation:</b>
     * 1. Overview
     * One of advantages of XML is the availability of processing – including XPath – which is defined as a W3C standard. For JSON, a similar tool called JSONPath has emerged.
     * <p>
     * This article will give an introduction to Jayway JsonPath, a Java implementation of the JSONPath specification. It describes setup, syntax, common APIs, and a demonstration of use cases.
     * <p>
     * <p>
     * 3. Syntax
     * The following JSON structure will be used in this section to demonstrate the syntax and APIs of JsonPath:
     * <p>
     * See <code>
     * {
     * "tool":
     * {
     * "jsonpath":
     * {
     * "creator":
     * {
     * "name": "Jayway Inc.",
     * "location":
     * [
     * "Malmo",
     * "San Francisco",
     * "Helsingborg"
     * ]
     * }
     * }
     * },
     * <p>
     * "book":
     * [
     * {
     * "title": "Beginning JSON",
     * "price": 49.99
     * },
     * <p>
     * {
     * "title": "JSON at Work",
     * "price": 29.99
     * }
     * ]
     * }
     * </code>
     * <p>
     * 3.1. Notation
     * JsonPath uses special notation to represent nodes and their connections to adjacent nodes in a JsonPath path. There are two styles of notation, namely dot and bracket.
     * <p>
     * Both of the following paths refer to the same node from the above JSON document, which is the third element within the location field of creator node, that is a child of jsonpath object belonging to tool under the root node.
     * <p>
     * With dot notation:
     * <p>
     * ?
     * $.tool.jsonpath.creator.location[2]
     * With bracket notation:
     * <p>
     * ?
     * $['tool']['jsonpath']['creator']['location'][2]
     * <p>
     * <p>
     * The dollar sign ($) represents root member object.
     * <p>
     * <b>for full documentation read here: </b>
     * https://github.com/json-path/JsonPath
     *
     * @throws Exception
     */
    @Test
    @TestProperties(name = "BB: Validate Response Json by Xpath: ${jsonXpath} expected to: ${jsonExpectedValue}", paramsExclude = {"browserIndex", "expectedResponceCode"}, returnParam = {})
    public void isResponseJsonValueEquals() throws Exception {
        responseSender = restApiWrapper.getResponseSenderByRequestIndex(responseNumber);
        String json = responseSender.getResponseJson();
        jsonExpectedValue = super.loadStoredParameter(jsonExpectedValue);
        boolean isExpectedJsonValue =false;
        if (jsonExpectedValueType==JsonValueType.STRING)
            isExpectedJsonValue = restApiValidator.validateJsonValue(json, jsonXpath, jsonExpectedValue, jsonExpectedValueType, stringOperator);
        else
            isExpectedJsonValue = restApiValidator.validateJsonValue(json, jsonXpath, jsonExpectedValue, jsonExpectedValueType, integerOperator);

        addSubScenarioResult("Response Json value ", isExpectedJsonValue);
    }

    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------- SETTERS / GETTERS  ---------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------


    public int getExpectedResponceCode() {
        return expectedResponceCode;
    }

    public void setExpectedResponceCode(int expectedResponceCode) {
        this.expectedResponceCode = expectedResponceCode;
    }

    public StringOperator getStringOperator() {
        return stringOperator;
    }

    public void setStringOperator(StringOperator stringOperator) {
        this.stringOperator = stringOperator;
    }

    public String getJsonXpath() {
        return jsonXpath;
    }

    public void setJsonXpath(String jsonXpath) {
        this.jsonXpath = jsonXpath;
    }

    /**
     * fetching value type interger or string (boolean its a string)
     *
     * @return
     */
    public JsonValueType getJsonExpectedValueType() {
        return jsonExpectedValueType;
    }

    public void setJsonExpectedValueType(JsonValueType jsonExpectedValueType) {
        this.jsonExpectedValueType = jsonExpectedValueType;
    }

    public String getJsonExpectedValue() {
        return jsonExpectedValue;
    }

    public void setJsonExpectedValue(String jsonExpectedValue) {
        this.jsonExpectedValue = jsonExpectedValue;
    }

    public int getResponseNumber() {
        return responseNumber;
    }

    /**
     * the index on sent responses (for first set 1,for second -2 ...)
     *
     * @param responseNumber
     */
    public void setResponseNumber(int responseNumber) {
        this.responseNumber = responseNumber;
    }

    public IntegerOperator getIntegerOperator() {
        return integerOperator;
    }

    public void setIntegerOperator(IntegerOperator integerOperator) {
        this.integerOperator = integerOperator;
    }
}
