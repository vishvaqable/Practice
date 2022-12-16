package api.tests;

import api.requests.common.ExcelOperation.ExcelReader;
import api.requests.common.TokenUtility.TokenUtility;
import io.unity.framework.init.base;
import io.unity.framework.readers.DataReader;
import io.unity.framework.readers.json_file_reader;
import io.unity.performaction.autoapi.RequestBuilder;
import io.unity.performaction.autoapi.ResponseValidator;
import io.unity.performaction.autoweb.testng_logs;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;





test
public class BlacklistDomainTest extends base {

    //---------------------------------- GET THE TEST DATA FOR VALID TOKEN--------------------------------------------//

    @DataProvider(name = "post_blacklist_domain_with_valid_token")
    public Object[][] data_provider() {
        DataReader reader = new DataReader();
        Object[][] data = null;
        data = reader.getExcelDataForDataProvider("blacklist_domain_test_case.xlsx", 0);
        return data;
    }

    @Test(dataProvider = "post_blacklist_domain_with_valid_token")
    public void post_blacklist_domain_with_valid_token(String Token_Type, String Content_Type, String Subscription_State, String Plan, String Role1, String SHaccount1, String SHaccount2, String Ownership, String Role2, String Status_Code, String Schema) throws ParseException {

        testng_logs logs = new testng_logs();
        RequestBuilder builder = new RequestBuilder();
        ExcelReader reader = new ExcelReader();

        Map params = new HashMap();
        params.put("Subscription_State", Subscription_State);
        params.put("Plan", Plan);
        params.put("Role", Role1);
        params.put("SHaccount1", SHaccount1);

        JSONObject pathParameter = new JSONObject();

        JSONObject queryParameters = new JSONObject();

        logs.test_step("----------------------------------------- GET THE TOKEN FROM ROLE1 --------------------------------------------");
        JSONObject header = new JSONObject();
        header.put("Authorization",reader.readFromToExcel(new File("src/test/java/data/Credential.xlsx"),"Sheet1",params,"Token"));
        header.put("content-type", Content_Type);

        JSONObject body = new JSONObject();
        body.put("domains","www.yahoomail.com");

        logs.test_step("------------------------------------------ BLACKLIST DOMAIN TEST--------------------------------------------");
        String update_file = builder.updateRequestObject("blacklist_domain/post_blacklist_domain/post_blacklist_domain", pathParameter, queryParameters, header, body);
        JSONObject response = builder.performRequest(update_file);

        ResponseValidator validator = new ResponseValidator(response);

        logs.test_step("------------------------------------------ VALIDATE STATUS CODE--------------------------------------------");
        validator.statusCodeShouldBe(201);

        logs.test_step("------------------------------------------ VALIDATE RESPONSE TIME --------------------------------------------");
        validator.validateResponseTime((Long)response.get("responseTime"));

        logs.test_step("------------------------------------------ VALIDATE SCHEMA --------------------------------------------");
        validator.validateSchema(Schema);

    }


    //---------------------------------- GET THE TEST DATA FOR INVALID TOKEN-------------------------------------------//

    @DataProvider(name = "post_blacklist_domain_with_invalid_token")
    public Object[][] data_provider2() {
        json_file_reader config = new json_file_reader();
        DataReader reader = new DataReader();
        Object[][] data = null;
        data = reader.getExcelDataForDataProvider("blacklist_domain_test_case.xlsx", 1);
        return data;
    }

    @Test(dataProvider = "post_blacklist_domain_with_invalid_token")
    public void post_blacklist_domain_with_invalid_token(String Token_Type, String Content_Type, String Subscription_State, String Plan, String Role1, String SHaccount1, String Ownership, String Status_Code, String Schema) {

        testng_logs logs = new testng_logs();
        RequestBuilder builder = new RequestBuilder();

        Map params = new HashMap();
        params.put("Subscription_State", Subscription_State);
        params.put("Plan", Plan);
        params.put("Role", Role1);
        params.put("SHaccount1", SHaccount1);

        JSONObject pathParameter = new JSONObject();

        JSONObject queryParameters = new JSONObject();

        JSONObject header = new JSONObject();
        header.put("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOjE4OTQsImlhdCI6MTY2MDc0MTk5NywiZXhwIjoxNjYzMzMzOTk3fQ.1uNPfk7_f45MCaGTIFJdtqYtCVrFhH4H1ZZCc7CSGUU" );
        header.put("content-type", Content_Type);

        JSONObject body = new JSONObject();
        body.put("domains","test");

        logs.test_step("------------------------------------------ BLACKLIST DOMAIN TEST--------------------------------------------");
        String update_file = builder.updateRequestObject("blacklist_domain/post_blacklist_domain/post_blacklist_domain", pathParameter, queryParameters, header, body);
        JSONObject response = builder.performRequest(update_file);

        ResponseValidator validator = new ResponseValidator(response);

        logs.test_step("------------------------------------------ VALIDATE STATUS CODE--------------------------------------------");
        validator.statusCodeShouldBe(400);

        logs.test_step("------------------------------------------ VALIDATE RESPONSE TIME --------------------------------------------");
        validator.validateResponseTime((Long)response.get("responseTime"));

        logs.test_step("------------------------------------------ VALIDATE SCHEMA --------------------------------------------");
        validator.validateSchema(Schema);

    }

    //---------------------------------- GET THE TEST DATA FOR BLANK TOKEN --------------------------------------------//

    @DataProvider(name = "post_blacklist_domain_with_blank_token")
    public Object[][] data_provider3() {
        json_file_reader config = new json_file_reader();
        DataReader reader = new DataReader();
        Object[][] data = null;
        data = reader.getExcelDataForDataProvider("blacklist_domain_test_case.xlsx", 2);
        return data;
    }

    @Test(dataProvider = "post_blacklist_domain_with_blank_token")
    public void post_blacklist_domain_with_blank_token(String Token_Type, String Content_Type, String Subscription_State, String Plan, String Role1, String SHaccount1, String Ownership, String Status_Code, String Schema) {

        testng_logs logs = new testng_logs();
        RequestBuilder builder = new RequestBuilder();

        Map params = new HashMap();
        params.put("Subscription_State", Subscription_State);
        params.put("Plan", Plan);
        params.put("Role", Role1);
        params.put("SHaccount1", SHaccount1);

        JSONObject pathParameter = new JSONObject();

        JSONObject queryParameters = new JSONObject();

        JSONObject header = new JSONObject();
        header.put("content-type", Content_Type);

        JSONObject body = new JSONObject();
        body.put("domains","test");

        logs.test_step("------------------------------------------ BLACKLIST DOMAIN TEST--------------------------------------------");
        String update_file= builder.updateRequestObject("blacklist_domain/post_blacklist_domain/post_blacklist_domain", pathParameter, queryParameters, header, body);
        JSONObject response = builder.performRequest(update_file);

        ResponseValidator validator = new ResponseValidator(response);

        logs.test_step("------------------------------------------ VALIDATE STATUS CODE--------------------------------------------");
        validator.statusCodeShouldBe(400);

        logs.test_step("------------------------------------------ VALIDATE RESPONSE TIME --------------------------------------------");
        validator.validateResponseTime((Long)response.get("responseTime"));

        logs.test_step("------------------------------------------ VALIDATE SCHEMA --------------------------------------------");
        validator.validateSchema(Schema);
    }

    //---------------------------------- GET THE TEST DATA FOR EXPIRED TOKEN --------------------------------------------//

    @DataProvider(name = "post_blacklist_domain_with_expired_token")
    public Object[][] data_provider4() {
        DataReader reader = new DataReader();
        Object[][] data = null;
        data = reader.getExcelDataForDataProvider("blacklist_domain_test_case.xlsx", 3);
        return data;
    }

    @Test(dataProvider = "post_blacklist_domain_with_expired_token")
    public void post_blacklist_domain_with_expired_token(String Token_Type,String Token, String Content_Type, String Subscription_State, String Plan, String Role1, String SHaccount1, String Ownership, String Status_Code, String Schema) {

        testng_logs logs = new testng_logs();
        RequestBuilder builder = new RequestBuilder();

        Map params = new HashMap();
        params.put("Subscription_State", Subscription_State);
        params.put("Plan", Plan);
        params.put("Role", Role1);
        params.put("SHaccount1", SHaccount1);

        JSONObject pathParameter = new JSONObject();

        JSONObject queryParameters = new JSONObject();

        JSONObject header = new JSONObject();
        header.put("Authorization",Token);
        header.put("Content_Type", Content_Type);

        JSONObject body = new JSONObject();
        body.put("domains","test");

        logs.test_step("------------------------------------------ BLACKLIST DOMAIN TEST--------------------------------------------");
        String update_file = builder.updateRequestObject("blacklist_domain/post_blacklist_domain/post_blacklist_domain", pathParameter, queryParameters, header, body);
        JSONObject response = builder.performRequest(update_file);

        ResponseValidator validator = new ResponseValidator(response);

        logs.test_step("------------------------------------------ VALIDATE STATUS CODE--------------------------------------------");
        validator.statusCodeShouldBe(400);

        logs.test_step("------------------------------------------ VALIDATE RESPONSE TIME --------------------------------------------");
        validator.validateResponseTime((Long)response.get("responseTime"));

        logs.test_step("------------------------------------------ VALIDATE SCHEMA --------------------------------------------");
        validator.validateSchema(Schema);
    }

    @Test
    public void post_blacklist_domain_with_different_content_type() {

        testng_logs logs = new testng_logs();
        RequestBuilder builder = new RequestBuilder();

        TokenUtility token_utils = new TokenUtility();
        JSONObject pathParameter = new JSONObject();
        JSONObject queryParameters = new JSONObject();

        JSONObject body = new JSONObject();
        body.put("domains","test");

        JSONObject header = new JSONObject();
        header.put("Authorization",token_utils.getTokenFromUser());
        header.put("content-type", "application/zip");

        logs.test_step("------------------------------------------ BLACKLIST DOMAIN TEST--------------------------------------------");
        String update_file = builder.updateRequestObject("blacklist_domain/post_blacklist_domain/post_blacklist_domain", pathParameter, queryParameters, header, body);
        JSONObject response = builder.performRequest(update_file);
        ResponseValidator validator = new ResponseValidator(response);

        logs.test_step("------------------------------------------ VALIDATE STATUS CODE--------------------------------------------");
        validator.statusCodeShouldBe(400);

        logs.test_step("------------------------------------------ VALIDATE RESPONSE TIME --------------------------------------------");
        validator.validateResponseTime((Long)response.get("responseTime"));

    }


    @Test
    public void post_blacklist_domain_with_different_method_name() {

        testng_logs logs = new testng_logs();
        RequestBuilder builder = new RequestBuilder();
        TokenUtility token_utils = new TokenUtility();

        JSONObject pathParameter = new JSONObject();
        JSONObject queryParameters = new JSONObject();

        JSONObject body = new JSONObject();
        body.put("domains","test");

        JSONObject header = new JSONObject();
        header.put("Authorization",token_utils.getTokenFromUser());
        header.put("content-type", "application/json");

        logs.test_step("------------------------------------------ BLACKLIST DOMAIN TEST--------------------------------------------");
        String update_file = builder.updateRequestObject("blacklist_domain/post_blacklist_domain/post_blacklist_domain_with_different_method", pathParameter, queryParameters, header, body);
        JSONObject response = builder.performRequest(update_file);
        ResponseValidator validator = new ResponseValidator(response);

        logs.test_step("------------------------------------------ VALIDATE STATUS CODE--------------------------------------------");
        validator.statusCodeShouldBe(404);

        logs.test_step("------------------------------------------ VALIDATE RESPONSE TIME --------------------------------------------");
        validator.validateResponseTime((Long)response.get("responseTime"));

    }

    @DataProvider(name = "post_blacklist_domain_with_invalid_title")
    public Object[][] data_provider5() {
        json_file_reader config = new json_file_reader();
        DataReader reader = new DataReader();
        Object[][] data = null;
        data = reader.getExcelDataForDataProvider("TITLE_FUNCTIONAL_TEST_CASE.xlsx", 0);
        return data;
    }

    @Test(dataProvider = "post_blacklist_domain_with_invalid_title")
    public void post_blacklist_domain_with_invalid_title(String Id,String Filter_Name, String Value, String status_code) {

        testng_logs logs = new testng_logs();
        RequestBuilder builder = new RequestBuilder();

        TokenUtility token_utils = new TokenUtility();
        JSONObject pathParameter = new JSONObject();
        JSONObject queryParameters = new JSONObject();

        JSONObject body = new JSONObject();
        body.put("domains",Value);

        JSONObject header = new JSONObject();
        header.put("Authorization",token_utils.getTokenFromUser());
        header.put("content-type", "application/json");

        logs.test_step("------------------------------------------ BLACKLIST DOMAIN TEST--------------------------------------------");
        String update_file = builder.updateRequestObject("blacklist_domain/post_blacklist_domain/post_blacklist_domain", pathParameter, queryParameters, header, body);
        JSONObject response = builder.performRequest(update_file);
        ResponseValidator validator = new ResponseValidator(response);

        logs.test_step("------------------------------------------ VALIDATE STATUS CODE--------------------------------------------");
        validator.statusCodeShouldBe(400);

        logs.test_step("------------------------------------------ VALIDATE RESPONSE TIME --------------------------------------------");
        validator.validateResponseTime((Long)response.get("responseTime"));

    }

}
