package test.com.cedex;


import com.cedex.api.ResponseSender;
import com.cedex.api.crm.CRMApi;
import com.cedex.api.crm.transaction.Transaction;
import com.cedex.api.crm.userpage.User;
import com.cedex.api.crm.usersearch.Result;
import com.cedex.api.crm.usersearch.SearchApiSender.CRMSearchUserBy;
import com.cedex.api.crm.usersearch.SearchApiSender.CRMSortBy;
import com.cedex.api.crm.usersearch.SearchApiSender.CRMSortOrder;
import com.cedex.api.crm.usersearch.SearchApiSender.CRMUserStatus;
import junit.framework.SystemTestCase4;
import org.junit.Before;
import org.junit.Test;
//
//import static io.restassured.RestAssured.get;
//import static org.hamcrest.Matchers.equalTo;

public class ApiTests extends SystemTestCase4 {

    private CRMApi crmApi;


    @Before
    public void before() throws Exception {
        crmApi = (CRMApi) system.getSystemObject("crmApi");

    }

//    @Test
//    public void temp1() throws Exception {
//        get("http://almond:8081/static/#/users/3")
//                .then().body("result.id", equalTo(3));
//    }
    @Test
    public void temp() throws Exception {


        crmApi.getRequestSender().sendWithoutParameters("http://almond:8081/accounts/3");

        ResponseSender rs=crmApi.getRequestSender().sendWithParameters("","address","mxbHqTkZk3cmFJC9MGhPpazLYNdYuqP8JR","merchantId","1");
//        JsonObject receivedJson = rs != null ? rs.getResponseJsonObject() : null;
        String jsonAsStr = rs != null ? rs.getResponseJson() : null;
//        JsonObject obj=crmApi.getRequestSender().sendWithParameters("","address","mxbHqTkZk3cmFJC9MGhPpazLYNdYuqP8JR","merchantId","1");
//        JsonObject obj=crmApi.getRequestSender().sendWithParameters("","address","0x13e92413a899e3d173fd0e5915a167dde5fa0db3","merchantId","1");
//        report.report("obj="+obj.toString());
    }
    @Test
    public void searchTest() throws Exception {
        Result[] res = crmApi.getSearchApiSender().requestSearch(CRMSearchUserBy.ID, "1");
        Result[] res1 = crmApi.getSearchApiSender().requestSearch(CRMSearchUserBy.FIRST_NAME, "a");
        Result[] res2 = crmApi.getSearchApiSender().requestSearch(CRMSearchUserBy.LAST_NAME, "a");
        Result[] res3 = crmApi.getSearchApiSender().requestSearch(CRMSearchUserBy.EMAIL, "e");
        Result[] res4 = crmApi.getSearchApiSender().requestSearch(CRMSearchUserBy.STATUS, CRMUserStatus.CONFIRMED.getCRMUserStatus()+"");

        //        Result[] res4 = searchApiSender.requestSearch(SearchResultSender.CRMSearchUserBy.COUNTRY_ISO, "c");
        report.report("res count="+res.length);
        report.report("res1 count="+res1.length);
        report.report("res2 count="+res2.length);
        report.report("res3 count="+res3.length);
        report.report("res4 count="+res4.length);

    }
    @Test
    public void sortTest() throws Exception {
        Result[] res = crmApi.getSearchApiSender().requestSort(CRMSearchUserBy.EMAIL,"a" , CRMSortBy.ID , CRMSortOrder.ASC );

        Result[] res1 = crmApi.getSearchApiSender().requestSortNextPage(CRMSearchUserBy.EMAIL,"a" , CRMSortBy.ID , CRMSortOrder.ASC );
        Result[] res2 = crmApi.getSearchApiSender().requestSortNextPage(CRMSearchUserBy.EMAIL,"a" , CRMSortBy.ID , CRMSortOrder.ASC );

        Result[] res3 = crmApi.getSearchApiSender().requestSortPreviousPage(CRMSearchUserBy.EMAIL,"a" , CRMSortBy.ID , CRMSortOrder.ASC );
        report.report("res count="+res.length);
        report.report("res1 count="+res1.length);
        report.report("res2 count="+res2.length);
    }

    @Test
    public void getUser() throws Exception {
        User user=crmApi.getUserPageSender().requestUser(134);
        report.report("DONE");
    }

    @Test
    public void getTransactionsPerUser() throws Exception {
        Transaction[] transactions=crmApi.getTransactionPerUserPageSender().requestTransactionsPerUser(4);
        report.report("DONE");
    }
    @Test
    public void getTransactionsAllUsers() throws Exception {
        Transaction[] transactions=crmApi.getTransactionPerUserPageSender().requestTransactionsForAllUsers();
        report.report("DONE");
    }
    @Test
    public void getTransaction() throws Exception {
        Transaction transactions=crmApi.getTransactionPerUserPageSender().requestTransaction(947);
        report.report("DONE");
    }

    @Test
    public void changeStatus() throws Exception {
//        crmApi.setRootUrl("http://almond:8081/static/#");
        crmApi.getUserPageSender().updateUserStatus(3,CRMUserStatus.BLACK_LIST);
//        User user=crmApi.getUserPageSender().requestUser(3);
//        if (user!=null) {
//
//        }
    }

//    @Test
//    public void temp1() throws IOException {
//
//        URL obj = new URL("http://almond:8081/accounts/3");
//        HttpURLConnection myRequest = (HttpURLConnection) obj.openConnection();
//        myRequest.setDoOutput(true);
//        myRequest.setRequestMethod("GET");
//
//    }
//    @Test
//    public void sendGet() throws Exception {
////http://almond:8081/accounts/3
//        String url = "http://almond:8081/accounts/3";
//
//        URL obj = new URL(url);
//        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//        con.setDoOutput(true);
//        // optional default is GET
//        con.setRequestMethod("GET");
//
//        //add request header
//        con.setRequestProperty("User-Agent", USER_AGENT);
//
//        int responseCode = con.getResponseCode();
//        System.out.println("\nSending 'GET' request to URL : " + url);
//        System.out.println("Response Code : " + responseCode);
//
//        BufferedReader in = new BufferedReader(
//                new InputStreamReader(con.getInputStream()));
//        String inputLine;
//        StringBuffer response = new StringBuffer();
//
//        while ((inputLine = in.readLine()) != null) {
//            response.append(inputLine);
//        }
//        in.close();
//
//        //print result
//        System.out.println(response.toString());
//
//    }




}
