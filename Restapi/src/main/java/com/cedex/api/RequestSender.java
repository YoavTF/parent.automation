package com.cedex.api;

import com.cedex.jsystem.ReporterLight;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jsystem.framework.report.Reporter;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestSender implements ReporterLight {

    private String rootUrl;
    private String userName;
    private String userPassword;
    private boolean useAuth = true;
    private String cookie = null;


    public static enum RequestMethod {
        DELETE, HEAD, GET, OPTIONS, POST, PUT, TRACE;
    }

//    //---------------------------------------------------------------
//    // Variables Declaration
//    //---------------------------------------------------------------
//    DataOutputStream printout;
//    BufferedReader input;
//    BufferedReader in;
//    BufferedWriter output;
//    FileOutputStream log;
//
//    ResourceBundle objResources;
//
//    HttpsURLConnection urlConn;
//    SSLContext sslContext;
//    TrustManagerFactory trustMgtFactory;
//    KeyStore keyStore;

    public RequestSender(String rootUrl, String userName, String userPassword, boolean useAuth) {
        this.rootUrl = rootUrl;
        this.userName = userName;
        this.userPassword = userPassword;
        this.useAuth = useAuth;
    }

    public RequestSender(String rootUrl, boolean useAuth) {
        this.rootUrl = rootUrl;
        this.useAuth = useAuth;
    }

//    /**
//     * The init() method will be called by JSystem after the instantiation of
//     * the system object. <br>
//     * This can be a good place to assert that all the members that we need were
//     * defined in the SUT file.
//     */
//    public void init() throws Exception {
//        super.init();
//        Assert.assertNotNull("Please define the SearchResultSender URL in the SUT file", rootUrl);
//
//    }

    /**
     * snding a rest api GET request
     *
     * @param relativeUrl - additional url from the base one:
     *                    example: base: http://192.168.200.108:8081
     *                    relativeUrl: /accounts so
     *                    full url: http://192.168.200.108:8081/accounts
     * @param reqParams   list of request pairs param name param1 , value1 , param2 , value2 etc ...
     * @return responce json file as JsonObject
     */
    public ResponseSender sendWithParameters(String relativeUrl, String... reqParams) throws Exception {
        String fullUrl = prepareFullUrl(rootUrl, relativeUrl, reqParams);
        return sendWithoutParameters(fullUrl);
    }


    private HttpURLConnection updateConnectionToBodyData(HttpURLConnection connection, String bodyData) throws IOException {
        System.out.println("Create Output Stream Object");
        DataOutputStream printout = new DataOutputStream(connection.getOutputStream());
        System.out.println("Write the Request into Output Stream");
        printout.writeBytes(bodyData);
        printout.flush();

        return connection;
    }

    private void login(String fullUrl) throws Exception {
        HttpURLConnection request;
//        URL url = new URL("http://192.168.200.108:8081/auth");
        URL url = new URL(rootUrl + "/auth");
//        String authString = this.userName + ":" + this.userPassword;
//        System.out.println("auth string: " + authString);
//        byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
//        String authStringEnc = new String(authEncBytes);
//        System.out.println("Base64 encoded auth string: " + authStringEnc);

        String content = "user=" + this.userName + "&password=" + this.userPassword;
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestProperty("content-type", "application/json");
        connection.setInstanceFollowRedirects(false);

        connection.setRequestMethod(RequestMethod.POST.toString());
//        connection.setRequestProperty("Content-Type", "text/plain");
//        connection.setRequestProperty("charset", "utf-8");


//        connection=this.updateConnectionToBodyData(connection, content);
        System.out.println("Create Output Stream Object");
        DataOutputStream printout = new DataOutputStream(connection.getOutputStream());
        System.out.println("Write the Request into Output Stream");
        printout.writeBytes(content);
        printout.flush();


        connection.connect();

        //---------------------------------------------------------------
        // Check the Cookies and read the Cookie from the Response
        //---------------------------------------------------------------

        // Grab Set-Cookie headers:
        Map<String, List<String>> headersmap = connection.getHeaderFields();
        List<String> cookies = connection.getHeaderFields().get("Set-Cookie");

        if (cookies!=null) {
            for (String currCookie : cookies) {
                if (currCookie.contains("auth")) {
                    System.out.println("Cookie is before index " + currCookie);
                    currCookie = currCookie.substring(0, currCookie.indexOf(";"));
                    this.cookie = this.cookie != null ? this.cookie + currCookie.trim() : currCookie.trim();
//                this.cookie = this.cookie.replace("auth=", "");
//                this.cookie = this.cookie + "; ";
                }
            }
        } else {
            throw new Exception("Cookies is null , please validate autentication ...");

        }
//        this.cookie = this.cookie.substring(0, this.cookie.length() - 2);
        try {
            InputStream is = (InputStream) connection.getContent();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            String line = null;

            StringBuilder responseData = new StringBuilder();
            while ((line = in.readLine()) != null) {
                responseData.append(line);
            }
            report.report("responce: " + responseData.toString());
        } catch (Exception e) {
            report.report(e.getLocalizedMessage());
        }
//        // Send them back in subsequent requests:
//        for (String cookie : cookies) {
//            connection.addRequestProperty("Cookie", cookie.split(";", 2)[0]);
//        }

        //only one cookie
//        System.out.println("Read Cookie");
//        String cook = connection.getHeaderField("Set-Cookie");
//        if (cook != null) {
//            System.out.println("Cookie is before index " + cook);
//            cook = cook.substring(0, cook.indexOf(";"));
//            this.cookie = cook.trim();
//            System.out.println("Cookie is " + this.cookie);
//        }


    }

    /**
     * snding a rest api GET request
     *
     * @param fullUrl - full url with parameters (already combinred into url) or without
     * @return responce json file as JsonObject
     */
    public ResponseSender sendWithoutParameters(String fullUrl) throws Exception {
        return sendWithoutParameters(fullUrl, RequestMethod.GET, null);
    }

    /**
     * snding a rest api GET request
     *
     * @param fullUrl - full url with parameters (already combinred into url) or without
     * @return responce json file as JsonObject
     */
    public ResponseSender sendWithoutParameters(String fullUrl, RequestMethod requestMethod, String requestBody) throws Exception {
        HashMap<String, String> headersmap = new HashMap<>();
        headersmap.put("Accept", "application/json");
        return sendWithoutParameters(fullUrl, requestMethod, requestBody, headersmap);
    }

    /**
     * snding a rest api GET request
     *
     * @param fullUrl - full url with parameters (already combinred into url) or without
     * @return responce json file as JsonObject
     */
    public ResponseSender sendWithoutParameters(String fullUrl, RequestMethod requestMethod, String requestBody, HashMap<String, String> headersmap) throws Exception {
        URL url = null;
        HttpURLConnection myRequest;
        JsonObject responseJsonObj = null;
        DataOutputStream printout = null;
        ResponseSender responseSender = null;

        try {
            if (useAuth)
                login(fullUrl);
//            url = new URL("http://192.168.200.108:8081/accounts?id=1&page=0");
            report.report("sending rest api wih method: " + requestMethod + " ...  \n  url: " + fullUrl);
            url = new URL(fullUrl);
            myRequest = (HttpURLConnection) url.openConnection();
            myRequest.setDoOutput(true);
            myRequest.setRequestMethod(requestMethod.toString());
            if (useAuth)
                myRequest.setRequestProperty("Cookie", this.cookie);

            //ADD HEADERS TO REQUEST
            for (String currHeader : headersmap.keySet()) {
                report.report("Adding Header: " + currHeader + " with value: " + headersmap.get(currHeader));
                myRequest.addRequestProperty(currHeader, headersmap.get(currHeader));
            }


            //ADD PARAMETERS TO THE BODY
            if (requestBody != null && !requestBody.isEmpty()) {
                report.report("Adding body parameter(s): " + requestBody);
//                myRequest = this.updateConnectionToBodyData(myRequest, requestBody);
                System.out.println("Create Output Stream Object");
                printout = new DataOutputStream(myRequest.getOutputStream());
                System.out.println("Write the Request into Output Stream");
                printout.writeBytes(requestBody);
                printout.flush();


            }

            myRequest.connect();
            int responseStatusCode = myRequest.getResponseCode();
            try {
                JsonParser jp = new JsonParser();

                InputStream is = null;
//                InputStream is = (InputStream) myRequest.getContent();
                if (responseStatusCode >= 200 && responseStatusCode < 400) {
                    // Create an InputStream in order to extract the response object
                    is = myRequest.getInputStream();
                } else {
                    is = myRequest.getErrorStream();
                }
                InputStreamReader inputStreamReader = new InputStreamReader(is);
                JsonElement element = jp.parse(inputStreamReader);
                responseJsonObj = element.getAsJsonObject();
            } catch (Exception e) {
                report.report("got exception: " + e.getMessage() + " " + e.getStackTrace(), Reporter.FAIL);

            }
            if (responseStatusCode != HttpURLConnection.HTTP_OK) {
                report.report("Connecting to: " + fullUrl + " failed got responce code: " + myRequest.getResponseCode(),Reporter.WARNING);
                report.report("Error response: "+responseJsonObj.toString(),Reporter.WARNING);
            } else {
                report.report("OK - Received Response code: " + myRequest.getResponseCode());
            }
            responseSender = new ResponseSender(responseJsonObj, myRequest.getResponseCode());
        } catch (IOException e) {
//            e.printStackTrace();
            report.report("got exception: " + e.getMessage() + " " + e.getStackTrace(), Reporter.FAIL);
        } finally {
            if (printout != null)
                printout.close();
        }

        return responseSender;
    }

    public static String prepareFullUrl(String rootUrl, String relativeUrl, String... reqParams) throws Exception {

        String fullUrl = !relativeUrl.equals("") ? rootUrl + relativeUrl : rootUrl;
        if (reqParams.length > 0) {
            fullUrl += "?";
            if (reqParams.length % 2 != 0)
                throw new Exception("Parameter: reqParams lenght in not EVEN , as it should be");
            for (int i = 0; i < reqParams.length; i = i + 2) {
                fullUrl = fullUrl + reqParams[i] + "=" + reqParams[i + 1] + "&";
            }
        }
        fullUrl = fullUrl.substring(0, fullUrl.length() - 1);
        return fullUrl;
    }

    //    private void loginNewSSL(String fullUrl) {
//
//
//        try {
//
//            //---------------------------------------------------------------
//            // Set the System Properties
//            //---------------------------------------------------------------
//
//            System.out.println("INITIALIZE NECESSARY PARAMETERS ...");
//
//            System.setProperty("javax.net.debug", "all");
//
//            System.setProperty("UseSunHttpHandler", "true");
//
//            ///If u r using PROXY REMOVE THE FOLLOWING Commands and Give ur Proxy IP instead of 0.0.0.0
//            //System.setProperty("http.proxySet", "true");
//            //System.setProperty("http.proxyHost", "0.0.0.0");
//            //System.setProperty("http.proxyPort", "80");
//            //System.setProperty("https.proxyHost", "0.0.0.0");
//            //System.setProperty("https.proxyPort", "80");
//
//            System.setProperty("java.protocol.handler.pkgs", "javax.net.ssl");
//
//            //---------------------------------------------------------------
//            // Use own key store
//            //---------------------------------------------------------------
//
//
//            //---------------------------------------------------------------
//            // Initialization
//            //---------------------------------------------------------------
//
//
//            System.out.println("LOADING CERTIFICATES ....");
//
//            sslContext = SSLContext.getInstance("SSLv3");
////            setTrustedCertificate("/home/madhu");
//            setTrustedCertificate("C:\\tmp\\");
//
//            sslContext.init(null, trustMgtFactory.getTrustManagers(), null);
//            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
//            HttpsURLConnection.setDefaultSSLSocketFactory(sslSocketFactory);
//
//
//            /* U can try if the above lines are not working (Command the above 6 lines of code)
//            String c_keystore = "/home/madhu.keystore";
//            String c_password = "madhu123";
//            System.setProperty("javax.net.ssl.keyStore",c_keystore);
//            System.setProperty("javax.net.ssl.keyStorePassword",c_password);
//            System.setProperty("javax.net.ssl.trustStore",c_keystore);
//            System.setProperty("javax.net.ssl.trustStorePassword",c_password);
//
//            ****/
//
//            //---------------------------------------------------------------
//            // First Request (Login)
//            //---------------------------------------------------------------
//
//            System.out.println("TRYING TO LOGIN");
//
////            String fileURL = "https://securesitename.com";
////            String content = "parameter1=username&parameter2=password";
//            String content = "user=" + this.userName + "&password=" + this.userPassword;
//
//            System.out.println("Login URL is " + fullUrl);
//            System.out.println("Login Parameter is " + content);
//
//            com.sun.net.ssl.internal.ssl.Provider.install();
//            java.security.Security.insertProviderAt(new com.sun.net.ssl.internal.ssl.Provider(), 1);
//
//            URL url = new URL(null, fullUrl, new com.sun.net.ssl.internal.www.protocol.https.Handler());
//
//            //---------------------------------------------------------------
//            // Open the connection
//            //---------------------------------------------------------------
//
//            System.out.println("Open the Connection");
//            urlConn = (HttpsURLConnection) url.openConnection();
//
//            //---------------------------------------------------------------
//            // Set the parameters
//            //---------------------------------------------------------------
//
//            System.out.println("Set the Connection Parameters");
//
//            urlConn.setDoInput(true);
//            urlConn.setDoOutput(true);
//            urlConn.setUseCaches(false);
//            urlConn.setRequestMethod("POST");
//            urlConn.setFollowRedirects(true);
//            //urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            //urlConn.setRequestProperty("Content-length", String.valueOf(content.length()));
//
//            //---------------------------------------------------------------
//            // Send a request using OutputStream
//            //---------------------------------------------------------------
//
//            System.out.println("Create Output Stream Object");
//
//            printout = new DataOutputStream(urlConn.getOutputStream());
//
//            System.out.println("Write the Request into Output Stream");
//            printout.writeBytes(content);
//            printout.flush();
//
//            //---------------------------------------------------------------
//            // Receive the response using InputStream
//            //---------------------------------------------------------------
//
//            System.out.println("Read the Response in Input Stream");
//            input = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
//
//            String str;
//            while ((str = input.readLine()) != null) {
//                System.out.println(str);
//            }
//
//            //---------------------------------------------------------------
//            // Check the Cookies and read the Cookie from the Response
//            //---------------------------------------------------------------
//
//            System.out.println("Read Cookie");
//            String cook = urlConn.getHeaderField("Set-Cookie");
//            if (cook != null) {
//                System.out.println("Cookie is before index " + cook);
//                cook = cook.substring(0, cook.indexOf(";"));
//                this.cookie = cook.trim();
//                System.out.println("Cookie is " + this.cookie);
//
//            }
//
//            System.out.println("LOGIN Process Successful");
//        } catch (Exception e) {
//            report.report("got exception: " + e.getLocalizedMessage() + " " + e.getStackTrace());
//        }
//    }

    //    private void setTrustedCertificate(String strCertPath) {
//
//        try {
//
//            trustMgtFactory = TrustManagerFactory.getInstance("SunX509");
//            CertificateFactory cert = CertificateFactory.getInstance("X.509");
//            String certs[] = {"urcertificatename.cer", "madhu.cer"};
//            FileInputStream lo_fileinputstream = null;
//            for (int i = 0; i < certs.length; i++) {
//                String strCert = strCertPath + certs[i];
//                lo_fileinputstream = new FileInputStream(strCert);
//                X509Certificate servercacert = (X509Certificate) cert.generateCertificate(lo_fileinputstream);
//
//                lo_fileinputstream.close();
//                String s1 = servercacert.getSerialNumber().toString();
//
//                if (keyStore == null) {
//                    keyStore = KeyStore.getInstance("JKS");
//                    keyStore.load(null, null);
//                }
//                keyStore.setCertificateEntry(s1, servercacert);
//
//            }
//            trustMgtFactory.init(keyStore);
//
//        } catch (Exception _ex) {
//            String strStatus = "ERROR - " + _ex.getMessage();
//            System.out.println(strStatus);
//            _ex.printStackTrace();
//        }
//
//    }//end of setTrustedCertificate()

    public String getRootUrl() {
        return rootUrl;
    }

    public void setRootUrl(String rootUrl) {
        this.rootUrl = rootUrl;
    }

    public boolean isUseAuth() {
        return useAuth;
    }

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
