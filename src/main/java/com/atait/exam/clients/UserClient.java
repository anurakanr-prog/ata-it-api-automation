package com.atait.exam.clients;
import com.atait.exam.config.Config;
import com.atait.exam.models.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UserClient {
    private static final String USERS_ENDPOINT = "/users";

    // Method นี้เปรียบเสมือนการ Setup Context พื้นฐานก่อนยิง API (เหมือนการตั้งค่า request.newContext() ใน Playwright)
    // เราจะใส่ Base URL, Headers และ Token ไว้ที่นี่ที่เดียว
    private RequestSpecification getBaseSpec() {
        return RestAssured.given()
                .baseUri(Config.getBaseUrl())
                .header("Authorization", "Bearer " + Config.getApiToken())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }

    //For unauthorize token
    public Response createUserWithCustomToken(User userPayload, String customToken) {
        return RestAssured.given()
                .config(RestAssured.config().logConfig(io.restassured.config.LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                .baseUri(Config.getBaseUrl())
                .header("Authorization", "Bearer " + customToken) // ใช้ Token ปลอมที่เราส่งเข้ามา
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(userPayload)
                .when()
                .post(USERS_ENDPOINT);
    }

    // --- Action Methods ---

    public Response getUsers() {
        return getBaseSpec()
                .when()
                .get(USERS_ENDPOINT);
    }

    public Response getUser(int userId) {
        return getBaseSpec()
                .pathParam("id", userId)
                .when()
                .get(USERS_ENDPOINT + "/{id}");
    }

    // สังเกตว่าเราโยน POJO (User userPayload) เข้ามาเลย เดี๋ยว REST Assured กับ Jackson จะแปลงเป็น JSON ให้เอง
    public Response createUser(User userPayload) {
        return getBaseSpec()
                .body(userPayload)
                .when()
                .post(USERS_ENDPOINT);
    }

    public Response updateUser(int userId, User userPayload) {
        return getBaseSpec()
                .pathParam("id", userId)
                .body(userPayload)
                .when()
                .put(USERS_ENDPOINT + "/{id}");
    }

    public Response deleteUser(int userId) {
        return getBaseSpec()
                .pathParam("id", userId)
                .when()
                .delete(USERS_ENDPOINT + "/{id}");
    }
}
