package com.cedex.automation.src.wrappers;

import com.cedex.GlobalParameters;
import com.cedex.StationUtils;
import com.cedex.api.RequestSender;
import com.cedex.api.RequestSender.RequestMethod;
import com.cedex.api.ResponseSender;
import com.cedex.automation.src.Lab;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.JsonPath;
import jsystem.framework.report.Reporter;

import java.io.*;
import java.util.HashMap;

public class RestApiWrapper extends BaseWrapper {


    public RestApiWrapper(Lab lab) {
        super(lab);
    }

    public static enum JsonValueType {
        STRING, INTEGER;
    }
    /**
     * Send a external request
     * @param url
     * @param requestMethod
     * @param parametersList
     * @param headersList
     * @return
     * @throws Exception
     */
    public ResponseSender sendRequest(String url,RequestMethod requestMethod,String[] parametersList,String[] headersList) throws Exception {
        return sendRequest(url,requestMethod ,parametersList ,headersList ,false ,"" ,"" );
    }

    /**
     * Send a external request
     * @param url
     * @param requestMethod
     * @param parametersList
     * @param headersList
     * @return
     * @throws Exception
     */
    public ResponseSender sendRequest(String url,RequestMethod requestMethod,String[] parametersList,String[] headersList,boolean useAuth,String userName,String userPassword) throws Exception {
        String requestBody="";
        switch (requestMethod) {
            case PUT:
            case POST: {
                requestBody = getParametersAsString(url, requestMethod, parametersList);
                break;
            }
            case GET: {
                url = getParametersAsString(url, requestMethod, parametersList);
                break;
            }
            default: {
                throw new Exception("Not Supported RequestMethod: " + requestMethod + " (only POST,GET,PUT are supported now) ");
            }

        }

        HashMap<String, String> headersmap = getHeadersMap(headersList);
        ResponseSender rs = null;
        lab.restApi.getRequestSender().setUseAuth(useAuth);
        if (useAuth) {
            lab.restApi.getRequestSender().setUserName(userName);
            lab.restApi.getRequestSender().setUserPassword(userPassword);
        }
        if (headersmap.size() > 0) {
            rs = lab.restApi.getRequestSender().sendWithoutParameters(url, requestMethod, requestBody, headersmap);

        } else {
            rs = lab.restApi.getRequestSender().sendWithoutParameters(url, requestMethod, requestBody);
        }

        return rs;
    }
    /**
     * get value from json
     *
     * @param jsonString            json as String
     * @param jsonXpath             - json xpath expression (@see http://goessner.net/articles/JsonPath/)
     * @param jsonExpectedValueType - received expected type INT or STRING
     * @return
     * @throws IOException
     */
    public String getJsonValue(String jsonString, String jsonXpath, JsonValueType jsonExpectedValueType) throws Exception {
        String foundValue = GlobalParameters.notFound;
        try {
            switch (jsonExpectedValueType) {

                case STRING: {
                    foundValue = JsonPath.read(jsonString, jsonXpath);
                    break;
                }
                default: {
                    int foundInteger = JsonPath.read(jsonString, jsonXpath);
                    foundValue = Integer.toString(foundInteger);
                }
            }
        } catch (Exception e) {
            report.report("Got Exception during XPath Reading: " + e.getMessage() + " " + e.getStackTrace(), Reporter.FAIL);
        }

        return foundValue;
    }

    /**
     * converting headers list received form user to map
     *
     * @param headersList
     * @return
     */
    public static HashMap<String, String> getHeadersMap(String[] headersList) {
        HashMap<String, String> headersmap = new HashMap<>();
        if (headersList != null && headersList.length > 0) {
            for (String currPair : headersList) {
                String[] currPairArr = currPair.split("=");
                String currParamName = currPairArr[0];
                String currValue = currPairArr[1];

                headersmap.put(currParamName, currValue);
            }
        }
        return headersmap;
    }

    /**
     * converting parameters list received from user to body (for POST or PUT) and to URL (for GET)
     *
     * @param url
     * @param requestMethod
     * @param receivedParametersList
     * @return
     * @throws Exception
     */
    public static String getParametersAsString(String url, RequestMethod requestMethod, String[] receivedParametersList) throws Exception {

        String parametesReadableString = null;
        switch (requestMethod) {
            case PUT:
            case POST: {
                JsonObject parameters = new JsonObject();
                if (receivedParametersList != null && receivedParametersList.length > 0) {
                    for (String currPair : receivedParametersList) {
                        String[] currPairArr = currPair.split("=");
                        String currParamName = currPairArr[0];
                        String currValue = currPairArr[1];
                        int currValueInt = -100;
                        try {
                            currValueInt = Integer.parseInt(currValue);
                            parameters.addProperty(currParamName, currValueInt);
                        } catch (Exception e) {
                            parameters.addProperty(currParamName, currValue);
                        }

                    }
                    parametesReadableString = parameters.toString();
                }
                break;

            }
            case GET: {
                if (receivedParametersList != null && receivedParametersList.length > 0) {
                    String[] getParemetersRaw = new String[receivedParametersList.length * 2];
                    int i = 0;
                    for (String currPair : receivedParametersList) {
                        String[] currPairArr = currPair.split("=");
                        String currParamName = currPairArr[0];
                        String currValue = currPairArr[1];
                        getParemetersRaw[i] = currParamName;
                        getParemetersRaw[i + 1] = currValue;
                        i += 2;
                    }
                    parametesReadableString = RequestSender.prepareFullUrl(url, "", getParemetersRaw);
                } else {
                    parametesReadableString = url;
                }
                break;
            }

            default: {
                throw new Exception("Not Supported RequestMethod: " + requestMethod + " (only POST,GET,PUT are supported now) ");
            }
        }


        return parametesReadableString;
    }

    /**
     * Store any object in the file
     * example: store RequestSender to file (serialize)
     *
     * @param objToSerialize
     * @param fileNameToPutIn - full path of the file (.ser)
     * @throws IOException
     */
    public static void serializeObject(Object objToSerialize, String fileNameToPutIn) throws IOException {
        // write object to file
//        FileOutputStream fos = new FileOutputStream("mybean.ser");
        File f = new File(fileNameToPutIn);
        if (f.isFile()) {
            f.delete();
        }
        FileOutputStream fos = new FileOutputStream(fileNameToPutIn);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(objToSerialize);
        oos.close();
    }

    /**
     * inject serialized object from file and returns its object
     *
     * @param fileNameFullPath
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object deserializeObject(String fileNameFullPath) throws IOException, ClassNotFoundException {
        // read object from file
        Object deserializedObject = null;
        File f = new File(fileNameFullPath);
        if (f.isFile()) {
            FileInputStream fis = new FileInputStream(fileNameFullPath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            deserializedObject = (Object) ois.readObject();
            ois.close();
        } else {
            report.report("ResponseSender file: " + fileNameFullPath + " not found , so nothing to deSerialize ...");
        }

        return deserializedObject;
    }

    /**
     * get full file name of serialize file per response
     *
     * @param responseCount
     * @return
     * @throws Exception
     */
    public String getSerializeableFileName(int responseCount) throws Exception {
        String rootFolder = System.getProperty("user.dir");

        String logFolder = StationUtils.appendFullChildName(rootFolder, "log");
        String logCurrentFolder = StationUtils.appendFullChildName(logFolder, "current");
        String fullFileName = StationUtils.appendFullChildName(logCurrentFolder, "responseSender" + responseCount + ".ser");

        return fullFileName;
    }

    public ResponseSender getResponseSenderByRequestIndex(int requestIndex) throws Exception {
        String fullSerializeFileName = this.getSerializeableFileName(requestIndex);
        return  (ResponseSender) RestApiWrapper.deserializeObject(fullSerializeFileName);
    }

}
