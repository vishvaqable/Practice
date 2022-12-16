package api.tests;

import api.requests.common.ExcelOperation.ExcelReader;
import api.requests.common.TokenUtility.TokenUtility;
import io.unity.framework.init.base;
import io.unity.framework.readers.DataReader;
import io.unity.performaction.autoapi.RequestBuilder;
import io.unity.performaction.autoapi.ResponseValidator;
import io.unity.performaction.autoweb.testng_logs;
import org.json.simple.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthV2toV3MigrationTest extends base {


    //---------------------------------- GET THE TEST DATA FOR VALID TOKEN --------------------------------------------//
    @DataProvider(name = "auth_v2tov3_migration_with_valid_token_data_provider")
    public Object[][] data_provider() {
        DataReader reader = new DataReader();
        Object[][] data = null;
        data = reader.getExcelDataForDataProvider("Auth_V2toV3_Migration_Test_case.xlsx", 0);
        return data;
    }
    @Test(dataProvider = "auth_v2tov3_migration_with_valid_token_data_provider")
    public void auth_v2tov3_migration_with_valid_token(String Token_Type, String Content_Type, String Subscription_State, String Plan, String Role1, String SHaccount1, String SHaccount2, String Ownership, String Role2, String Status_Code, String Schema){
        testng_logs logs = new testng_logs();
        RequestBuilder builder = new RequestBuilder();
        ExcelReader reader = new ExcelReader();

        Map params = new HashMap();
        params.put("Subscription_State", Subscription_State);
        params.put("Plan", Plan);
        params.put("Role", Role1);
        params.put("SHaccount1", SHaccount1);
        JSONObject pathParameter = new JSONObject();

        JSONObject queryParameter = new JSONObject();

        logs.test_step("----------------------------------------- GET THE TOKEN FROM ROLE1 --------------------------------------------");
        JSONObject header = new JSONObject();
        header.put("Authorization",reader.readFromToExcel(new File("src/test/java/data/Credential.xlsx"),"Sheet1",params,"Token"));
        header.put("Content-Type", Content_Type);

        JSONObject body = new JSONObject();

        body.put("designation","Business Development Representative");
        body.put("phoneNumber","879-877-8787");
        body.put("firstName","Bob");
        body.put("lastName","Bob");
        body.put("teamSize","1-10");
        body.put("industry","SaaS");
        body.put("useCase","Lead Generation");
        body.put( "email", "testqable905+31@gmail.com");
        body.put("timeZone", "Asia/Kolkata");
        body.put("signupUrlParam", "string");
        body.put("rewardfulReferralId", "123");


        logs.test_step("------------------------------------------ AUTH V2 TO V3 MIGRATION --------------------------------------------");
        String update_file = builder.updateRequestObject("auth/auth_v2tov3_migration/auth_v2tov3_migration", pathParameter, queryParameter, header, body);
        JSONObject response = builder.performRequest(update_file);

        ResponseValidator validator = new ResponseValidator(response);

        logs.test_step("------------------------------------------ VALIDATE STATUS CODE--------------------------------------------");
        validator.statusCodeShouldBe(200);

        logs.test_step("------------------------------------------ VALIDATE RESPONSE TIME --------------------------------------------");
        validator.validateResponseTime((Long)response.get("responseTime"));

        logs.test_step("------------------------------------------ VALIDATE SCHEMA --------------------------------------------");
        validator.validateSchema(Schema);

    }

    //---------------------------------- GET THE TEST DATA FOR INVALID TOKEN-------------------------------------------//

    @DataProvider(name = "auth_v2tov3_migration_with_invalid_token_data_provider")
    public Object[][] data_provider2() {
        DataReader reader = new DataReader();
        Object[][] data = null;
        data = reader.getExcelDataForDataProvider("Auth_V2toV3_Migration_Test_case.xlsx", 2);
        return data;
    }

    @Test(dataProvider = "auth_v2tov3_migration_with_invalid_token_data_provider")
    public void auth_v2tov3_migration_with_invalid_token(String Token_Type, String Content_Type, String Subscription_State, String Plan, String Role1, String SHaccount1, String Ownership, String Status_Code, String Schema) {
        testng_logs logs = new testng_logs();
        RequestBuilder builder = new RequestBuilder();

        JSONObject pathParameter = new JSONObject();
        JSONObject queryParameters = new JSONObject();

        JSONObject header = new JSONObject();
        header.put("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOjE4OTQsImlhdCI6MTY2MDc0MTk5NywiZXhwIjoxNjYzMzMzOTk3fQ.1uNPfk7_f45MCaGTIFJdtqYtCVrFhH4H1ZZCc7CSGUU" );
        header.put("Content_Type", Content_Type);

        JSONObject body = new JSONObject();

        logs.test_step("------------------------------------------ AUTH V2 TO V3 MIGRATION --------------------------------------------");
        String update_file = builder.updateRequestObject("auth/auth_v2tov3_migration/auth_v2tov3_migration", pathParameter, queryParameters, header, body);
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

    @DataProvider(name = "auth_v2tov3_migration_with_blank_token_data_provider")
    public Object[][] data_provider3() {
        DataReader reader = new DataReader();
        Object[][] data = null;
        data = reader.getExcelDataForDataProvider("Auth_V2toV3_Migration_Test_case.xlsx", 3);
        return data;
    }

    @Test(dataProvider = "auth_v2tov3_migration_with_blank_token_data_provider")
    public void auth_v2tov3_migration_with_blank_token(String Token_Type, String Content_Type, String Subscription_State, String Plan, String Role1, String SHaccount1, String Ownership, String Status_Code, String Schema) {

        testng_logs logs = new testng_logs();
        RequestBuilder builder = new RequestBuilder();

        JSONObject pathParameter = new JSONObject();

        JSONObject queryParameters = new JSONObject();

        JSONObject header = new JSONObject();
        header.put("Content_Type", Content_Type);

        JSONObject body = new JSONObject();

        logs.test_step("------------------------------------------ AUTH V2 TO V3 MIGRATION --------------------------------------------");
        String update_file = builder.updateRequestObject("auth/auth_v2tov3_migration/auth_v2tov3_migration", pathParameter, queryParameters, header, body);
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

    @DataProvider(name = "auth_v2tov3_migration_with_expired_token_data_provider")
    public Object[][] data_provider4() {
        DataReader reader = new DataReader();
        Object[][] data = null;
        data = reader.getExcelDataForDataProvider("Auth_V2toV3_Migration_Test_case.xlsx", 4);
        return data;
    }

    @Test(dataProvider = "auth_v2tov3_migration_with_expired_token_data_provider")
    public void auth_v2tov3_migration_with_expired_token(String Token_Type,String Token, String Content_Type, String Subscription_State, String Plan, String Role1, String SHaccount1, String Ownership, String Status_Code, String Schema) {

        testng_logs logs = new testng_logs();
        RequestBuilder builder = new RequestBuilder();

        JSONObject pathParameter = new JSONObject();

        JSONObject queryParameters = new JSONObject();

        JSONObject header = new JSONObject();
        header.put("Authorization",Token);
        header.put("Content_Type", Content_Type);

        JSONObject body = new JSONObject();

        logs.test_step("------------------------------------------ AUTH V2 TO V3 MIGRATION --------------------------------------------");
        String update_file = builder.updateRequestObject("auth/auth_v2tov3_migration/auth_v2tov3_migration", pathParameter, queryParameters, header, body);
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
    public void auth_v2tov3_migration_with_different_content_type() {

        testng_logs logs = new testng_logs();
        RequestBuilder builder = new RequestBuilder();
        TokenUtility token_utils = new TokenUtility();
        JSONObject pathParameter = new JSONObject();
        JSONObject queryParameters = new JSONObject();

        JSONObject body = new JSONObject();

        JSONObject header = new JSONObject();
        header.put("Authorization",token_utils.getTokenFromUser());
        header.put("content-type", "application/zip");

        logs.test_step("------------------------------------------ AUTH V2 TO V3 MIGRATION --------------------------------------------");
        String update_file = builder.updateRequestObject("auth/auth_v2tov3_migration/auth_v2tov3_migration", pathParameter, queryParameters, header, body);
        JSONObject response = builder.performRequest(update_file);
        ResponseValidator validator = new ResponseValidator(response);

        logs.test_step("------------------------------------------ VALIDATE STATUS CODE--------------------------------------------");
        validator.statusCodeShouldBe(400);

        logs.test_step("------------------------------------------ VALIDATE RESPONSE TIME --------------------------------------------");
        validator.validateResponseTime((Long)response.get("responseTime"));

    }


    @Test
    public void auth_v2tov3_migration_with_different_method_name() {

        testng_logs logs = new testng_logs();
        RequestBuilder builder = new RequestBuilder();
        TokenUtility token_utils = new TokenUtility();
        JSONObject pathParameter = new JSONObject();
        JSONObject queryParameters = new JSONObject();

        JSONObject body = new JSONObject();

        JSONObject header = new JSONObject();
        header.put("Authorization",token_utils.getTokenFromUser());
        header.put("content-type", "application/json");

        logs.test_step("------------------------------------------ AUTH V2 TO V3 MIGRATION --------------------------------------------");
        String update_file = builder.updateRequestObject("auth/auth_v2tov3_migration/auth_v2tov3_migration_with_different_method", pathParameter, queryParameters, header, body);
        JSONObject response = builder.performRequest(update_file);
        ResponseValidator validator = new ResponseValidator(response);

        logs.test_step("------------------------------------------ VALIDATE STATUS CODE--------------------------------------------");
        validator.statusCodeShouldBe(400);

        logs.test_step("------------------------------------------ VALIDATE RESPONSE TIME --------------------------------------------");
        validator.validateResponseTime((Long)response.get("responseTime"));

    }


}
