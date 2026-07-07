package com.atait.exam.tests;

import com.atait.exam.clients.UserClient;
import com.atait.exam.config.Config;
import com.atait.exam.models.User;
import com.atait.exam.utils.TestDataUtil;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserPostApiTest {
    private final UserClient userClient = new UserClient();

    @Test
    @DisplayName("Verify create user : Success case")
    public void testCreateUser() {

        //Generate test data payload
        User newUserPayload = TestDataUtil.generateRandomUser();

        //Set Post Api
        Response responseFromPostApi = userClient.createUser(newUserPayload);
        responseFromPostApi.prettyPrint();

        //Validate response
        assertEquals(201, responseFromPostApi.statusCode(), "Expected status code 201 Created from POST /users");

        assertNotNull(responseFromPostApi.jsonPath().getString("id"), "Response should contain an ID");

        assertEquals(newUserPayload.getName(), responseFromPostApi.jsonPath().getString("name"));
        assertEquals(newUserPayload.getEmail(), responseFromPostApi.jsonPath().getString("email"));
        assertEquals(newUserPayload.getGender(), responseFromPostApi.jsonPath().getString("gender"));
        assertEquals(newUserPayload.getStatus(), responseFromPostApi.jsonPath().getString("status"));
    }

    @Test
    @DisplayName("Verify create duplicate user  : Negative case")
    public void testCreateDuplicateUser() {

        //Generate test data payload
        User newUserPayload = TestDataUtil.generateRandomUser();

        //Sent Post Api
        Response responseFromPostApi = userClient.createUser(newUserPayload);
        responseFromPostApi.prettyPrint();

        //Validate response
        assertEquals(201, responseFromPostApi.statusCode(), "Expected status code 201 Created from POST /users");

        assertNotNull(responseFromPostApi.jsonPath().getString("id"), "Response should contain an ID");

        assertEquals(newUserPayload.getName(), responseFromPostApi.jsonPath().getString("name"));
        assertEquals(newUserPayload.getEmail(), responseFromPostApi.jsonPath().getString("email"));
        assertEquals(newUserPayload.getGender(), responseFromPostApi.jsonPath().getString("gender"));
        assertEquals(newUserPayload.getStatus(), responseFromPostApi.jsonPath().getString("status"));

        //Sent Post Api (Create again)
        Response resentApi = userClient.createUser(newUserPayload);

        //Validate response
        assertEquals(422, resentApi.statusCode(), "Response should contain an ID");
        System.out.println("Cannot create duplicate user with this code : " + resentApi.statusCode());
    }

    @Test
    @DisplayName("Verify can not create user when unauthorize : Negative case")
    public void testCreateFailUnauthorize() {

        //Generate test data payload
        User newUser = TestDataUtil.generateRandomUser();
        String unauthorizeToken = Config.getUnauthorizeToken();

        //Sent Post Api
        Response responseFromPostApi = userClient.createUserWithCustomToken(newUser, unauthorizeToken);
        responseFromPostApi.prettyPrint();

        //Validate response
        assertEquals(401, responseFromPostApi.statusCode(), "Expected status code 401 Unauthorized for invalid token");
        System.out.println("Cannot create because unauthorize token : " + responseFromPostApi.statusCode());
    }

    @Test
    @DisplayName("Verify can not create user when not sent email in body : Negative case")
    public void testCreateFailInvalidBody() {

        //Generate test data payload
        User generatedUserData = TestDataUtil.generateRandomUser();
        User newUserPayload = new User(generatedUserData.getName(), null, generatedUserData.getGender(), generatedUserData.getStatus());

        //Set Post Api
        Response responseFromPostApi = userClient.createUser(newUserPayload);
        responseFromPostApi.prettyPrint();

        //Validate response
        assertEquals(422, responseFromPostApi.statusCode(), "Unprocessable Entity when email is missing");
        System.out.println("Cannot create because invalid body : " + responseFromPostApi.statusCode());
    }
}
