package com.cedex.automation.tests.actions;

import com.cedex.api.RequestSender.RequestMethod;
import com.cedex.api.ResponseSender;
import com.cedex.automation.src.wrappers.RestApiWrapper;
import com.cedex.automation.src.wrappers.RestApiWrapper.JsonValueType;
import com.cedex.automation.tests.base.AbstractBaseTest;
import jsystem.framework.RunProperties;
import jsystem.framework.TestProperties;
import jsystem.framework.scenario.Parameter;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class APIActions extends AbstractBaseTest {


    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------------- VARIABLES ------------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------
    private RequestMethod requestMethod = RequestMethod.GET;
    private String url;
    private String[] parametersList;
    private String[] headersList;

    private RestApiWrapper restApiWrapper;
    private ResponseSender responseSender;

    private String jsonXpath;
    private JsonValueType jsonExpectedValueType = JsonValueType.STRING;
    public static int sentRequestCounter = 0;
    public int responseNumber = 1;
    public boolean useAuth=false;
    public String userName="";
    public String userPassword="";


    public String fullSerializeFileName;

    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------
    //--------------------------------------- ACTIONS ----------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------
    @Before
    public void before() throws Exception {
        restApiWrapper = new RestApiWrapper(lab);
//        String workingDir = System.getProperty("user.dir");
//        fullSerializeFileName = StationUtils.appendFullChildName(workingDir, "responseSender.ser");

//        responseSender = (ResponseSender) RestApiWrapper.deserializeObject(fullSerializeFileName);
    }

    @Override
    public void handleUIEvent(HashMap<String, Parameter> map, String methodName) throws Exception {
        report.report("methodName=" + methodName);
        if (methodName.equalsIgnoreCase("sendRequest")) {
            for (String currKey : map.keySet()) {
                Parameter currParameter = map.get(currKey);
                String parameterName = currParameter.getName();
                report.report("parameterName=" + parameterName);
                if (parameterName.equalsIgnoreCase("useAuth")) {
                    String currParameterValue = currParameter.getValue().toString();
                    report.report("currParameterValue=" + currParameterValue);
                    if (currParameterValue.equalsIgnoreCase("true")) {
                        map.get("UserName").setVisible(true);
                        map.get("UserPassword").setVisible(true);
                    } else {
                        map.get("UserName").setVisible(false);
                        map.get("UserPassword").setVisible(false);
                    }
                }
            }
        }

    }
    /**
     * Sending a POST,PUT and GET requests
     * <b>Example:</b>
     * if i want to send POSt request :
     * url: http://<hostname>/api/v1/wallet/hosted-wallet
     * <p>
     * with Headers:
     * Cache Control	no-cache
     * Content-Type	application/json
     * <p>
     * and parameters:
     * <p>
     * User_id	number
     * Password	string
     * <p>
     * i will set  in:
     * headersList= Cache Control=no-cache;Content-Type	application/json
     * and in
     * parametersList= User_id=3;Password=123456
     *
     * @throws Exception
     */
    @Test
    @TestProperties(name = "BB: Send ${requestMethod} request", paramsExclude = {"browserIndex", "jsonXpath", "jsonExpectedValueType", "responseNumber"}, returnParam = {})
    public void sendRequest() throws Exception {
        sentRequestCounter++;
        responseSender=restApiWrapper.sendRequest(url, requestMethod,parametersList ,headersList,useAuth,userName,userPassword );
        fullSerializeFileName = restApiWrapper.getSerializeableFileName(sentRequestCounter);
        RestApiWrapper.serializeObject(responseSender, fullSerializeFileName);
    }

    /**
     * choose the number of  sent response (defined by parameter:   responseNumber)
     * <p>
     * the fetched value will  have name like: jsonValue+responseNumber (for example jsonValue1)
     * the get this value in you next Building block #jsonValue+responseNumber
     *
     * @throws Exception
     */
    @Test
    @TestProperties(name = "BB: get a Value from json number: ${responseNumber}", paramsExclude = {"userName","userPassword","useAuth","browserIndex",
            "parametersList","headersList","url","requestMethod"}, returnParam = {})
    public void getJsonValue() throws Exception {

        responseSender=restApiWrapper.getResponseSenderByRequestIndex(responseNumber);
        if (responseSender!=null) {
            String json = responseSender.getResponseJson();
            String foundJsonValue = restApiWrapper.getJsonValue(json, jsonXpath, jsonExpectedValueType);
            String parameterName = "jsonValue" + responseNumber;

            RunProperties.getInstance().setRunProperty(parameterName, foundJsonValue);
        } else {
            throw new Exception("got exception, may by you get wrong responseNumber: "+responseNumber);
        }
    }
    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------- SETTERS / GETTERS  ---------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------


    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    /**
     * Request method : only GET , POST and PUT supported
     *
     * @param requestMethod
     */
    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getUrl() {
        return url;
    }

    /**
     * request's url (should start with : http:// or https:// only)
     *
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    public String[] getParametersList() {
        return parametersList;
    }

    /**
     * list of parameters (for GET , POST and PUT requests) : param1=value1;param2=value2
     *
     * @param parametersList
     */
    public void setParametersList(String[] parametersList) {
        this.parametersList = parametersList;
    }

    public String[] getHeadersList() {
        return headersList;
    }

    /**
     * list of request headers looks like: header1=value1;header2=value2
     *
     * @param headersList
     */
    public void setHeadersList(String[] headersList) {
        this.headersList = headersList;
    }

    public String getJsonXpath() {
        return jsonXpath;
    }

    /**
     * the rule to fetch json value (by json xpath)
     *
     * @param jsonXpath
     */
    public void setJsonXpath(String jsonXpath) {
        this.jsonXpath = jsonXpath;
    }

    public JsonValueType getJsonExpectedValueType() {
        return jsonExpectedValueType;
    }

    /**
     * the type of json's injected value (String or Integer)
     *
     * @param jsonExpectedValueType
     */
    public void setJsonExpectedValueType(JsonValueType jsonExpectedValueType) {
        this.jsonExpectedValueType = jsonExpectedValueType;
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

    public boolean isUseAuth() {
        return useAuth;
    }

    /**
     * if login and password needed set true
     * @param useAuth
     */
    public void setUseAuth(boolean useAuth) {
        this.useAuth = useAuth;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
