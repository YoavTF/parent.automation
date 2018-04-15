package com.cedex.automation.src.validators;

import com.cedex.GlobalParameters;
import com.cedex.automation.src.Lab;
import com.cedex.automation.src.wrappers.BaseWrapper;
import com.cedex.automation.src.wrappers.RestApiWrapper;
import com.cedex.automation.src.wrappers.RestApiWrapper.JsonValueType;
import com.cedex.numbers.IntegerUtils;
import com.cedex.numbers.IntegerUtils.IntegerOperator;
import com.cedex.text.StringUtils;
import com.cedex.text.StringUtils.StringOperator;
import jsystem.framework.report.Reporter;

import java.io.IOException;

public class RestApiValidator extends BaseWrapper {
    RestApiWrapper restApiWrapper;

    public RestApiValidator(Lab lab) {
        super(lab);
        restApiWrapper=new RestApiWrapper(lab);
    }


    /**
     * @param jsonString
     * @param jsonXpath
     * @param jsonExpectedValue
     * @param jsonExpectedValueType
     * @param integerOperator
     * @return
     */
    public boolean validateJsonValue(String jsonString, String jsonXpath, String jsonExpectedValue, JsonValueType jsonExpectedValueType, IntegerOperator integerOperator) throws IOException {
        return validateJsonValue(jsonString, jsonXpath, jsonExpectedValue, jsonExpectedValueType, integerOperator.toString());
    }

    /**
     * @param jsonString
     * @param jsonXpath
     * @param jsonExpectedValue
     * @param jsonExpectedValueType
     * @param stringOperator
     * @return
     */
    public boolean validateJsonValue(String jsonString, String jsonXpath, String jsonExpectedValue, JsonValueType jsonExpectedValueType, StringOperator stringOperator) throws IOException {
        return validateJsonValue(jsonString, jsonXpath, jsonExpectedValue, jsonExpectedValueType, stringOperator.toString());
    }

    /**
     * @param jsonString
     * @param jsonXpath
     * @param jsonExpectedValue
     * @param jsonExpectedValueType
     * @param stringOperator
     * @return
     */
    private boolean validateJsonValue(String jsonString, String jsonXpath, String jsonExpectedValue, JsonValueType jsonExpectedValueType, String stringOperator) throws IOException {
        String foundValue = GlobalParameters.notFound;
        boolean isExpectedJsonValue = false;
        report.startLevel("Looking validating json value: " + jsonXpath + " " + stringOperator + " to expected value: " + jsonExpectedValue);
        try {
            foundValue = restApiWrapper.getJsonValue(jsonString, jsonXpath, jsonExpectedValueType);
            if (jsonExpectedValueType == JsonValueType.STRING) {
                isExpectedJsonValue = StringUtils.stringCompare(foundValue, jsonExpectedValue, StringOperator.getEnumFromString(stringOperator));
            } else {
                isExpectedJsonValue = IntegerUtils.integerCompare(Integer.parseInt(foundValue), Integer.parseInt(jsonExpectedValue), IntegerOperator.getEnumFromString(stringOperator));
            }
        } catch (Exception e) {
            report.report("json= "+jsonString);
            report.report("looking for pattern: "+jsonXpath);
            report.report("Got Exception during XPath Reading: " + e.getMessage(), Reporter.FAIL);

        } finally {
            report.report("Found value is: " + foundValue);
            report.report("Expected value is: " + jsonExpectedValue);
            report.report("Json location is: " + jsonXpath);
            report.report("String operation is: " + stringOperator);
            report.report("validation result is: " + isExpectedJsonValue);
            report.report("json is: " + jsonString);
            report.stopLevel();
        }
        return isExpectedJsonValue;
    }
}
