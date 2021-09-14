package api;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class RestAssuredTest {

    public static String access_token;

    @BeforeAll
    public static void beforeAll() {
        access_token = given()
                .when()
                .params("corpid", "wwab5d790a424d431e")
                .params("corpsecret", "VTrfDyLQbm16_xoo_1B5-OnswSsxqb6Ey0SFDRK_Mno")
                .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                .then()
                .log()
                .all()
                .extract().body().path("access_token");
    }

    @Test
    void test() {
        System.out.println(access_token);

        given()
                .when()
                .contentType("application/json;charset=utf-8")
                .body("{\n" +
                        "   \"touser\" : \"@all\"," +
                        "   \"msgtype\" : \"text\"," +
                        "   \"agentid\" : 1000002," +
                        "   \"text\" : {\n" +
                        "       \"content\" : \"打开百度人网址\n https://baidu.com\"}," +
                        "}")
                .queryParam("access_token", access_token)
                .post("https://qyapi.weixin.qq.com/cgi-bin/message/send")
                .then()
                .log()
                .all()
                .body("errcode", equalTo(0))
                .body("errmsg", equalTo("ok. Warning: wrong json format. "));

        given()
                .when()
                .contentType("application/json;charset=utf-8")
                .body("{\n" +
                        "   \"agentid\": 1000002," +
                        "   \"report_location_flag\": 0," +
                        "   \"name\": \"财经助手\"," +
                        "   \"description\": \"内部财经服务平台\"," +
                        "}")
                .queryParam("access_token", access_token)
                .post("https://qyapi.weixin.qq.com/cgi-bin/agent/set")
                .then()
                .log()
                .all()
                .body("errcode", equalTo(0));

    }

}
