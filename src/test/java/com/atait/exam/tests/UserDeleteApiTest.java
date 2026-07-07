package com.atait.exam.tests;

import com.atait.exam.clients.UserClient;
import com.atait.exam.models.User;
import com.atait.exam.utils.TestDataUtil;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDeleteApiTest {
    private final UserClient userClient = new UserClient();

    @Test
    @DisplayName("Verify delete user : Success case")
    public void testDeleteUser() {

        //Set up test data
        User newUserPayload = TestDataUtil.generateRandomUser();
        Response responseFromPostApi = userClient.createUser(newUserPayload);

        //Get User id
        int newUserId = responseFromPostApi.jsonPath().getInt("id");

        //Sent Delete API
        Response responseFromDeleteApi = userClient.deleteUser(newUserId);

        //Validate response
        assertEquals(204, responseFromDeleteApi.statusCode(), "Expected status 204 ");
        System.out.println("Delete Success with this code : " + responseFromDeleteApi.statusCode());

        //Validate this user should not found
        Response responseFromGetApi = userClient.getUser(newUserId);
        assertEquals(404, responseFromGetApi.statusCode(), "Expected status 404 since user was deleted");
        System.out.println("Response should show not found : " + responseFromGetApi.statusCode());
    }

    @Test
    @DisplayName("Verify delete not existing user : Negative case")
    public void testDeleteNotExistingUser() {

        //Set User id not existing
        int UserId = 999999999;

        //Sent Delete API
        Response deleteResponse = userClient.deleteUser(UserId);
        deleteResponse.prettyPrint();

        //Validate this user should not found
        Response responseFromGetApi = userClient.getUser(UserId);
        assertEquals(404, responseFromGetApi.statusCode(), "Expected status 404 since user was deleted");
        System.out.println("Response should show not found : " + responseFromGetApi.statusCode());
    }
}
