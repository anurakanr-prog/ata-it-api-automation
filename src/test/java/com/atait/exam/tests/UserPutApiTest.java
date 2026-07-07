package com.atait.exam.tests;

import com.atait.exam.clients.UserClient;
import com.atait.exam.models.User;
import com.atait.exam.utils.TestDataUtil;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserPutApiTest {
    private final UserClient userClient = new UserClient();

    @Test
    @DisplayName("Verify Update user information")
    public void testUpdateUser(){
        //Gen Test data
        User newUserPayload = TestDataUtil.generateRandomUser();

        //Create new user data
        Response responseFromCreateApi = userClient.createUser(newUserPayload);
        responseFromCreateApi.prettyPrint();

        //Get user id
        int userId = responseFromCreateApi.jsonPath().getInt("id");

        //Get email
        String userEmail = responseFromCreateApi.jsonPath().getString("email");

        //New data for update user
        User updateNewUser = new User("NameUpdated", "update." + userEmail, "female", "inactive");

        //Update user via Api
        Response responseFromUpdateApi = userClient.updateUser(userId, updateNewUser);
        responseFromUpdateApi.prettyPrint();

        //Validate response
        assertEquals(200, responseFromUpdateApi.statusCode(), "Status code should be : 200");

        assertEquals(updateNewUser.getName(), responseFromUpdateApi.jsonPath().getString("name"), "New name should be : "+updateNewUser.getName());
        assertEquals(updateNewUser.getEmail(), responseFromUpdateApi.jsonPath().getString("email"), "New name should be : "+updateNewUser.getEmail());
        assertEquals(updateNewUser.getGender(), responseFromUpdateApi.jsonPath().getString("gender"), "New name should be : "+updateNewUser.getGender());
        assertEquals(updateNewUser.getStatus(), responseFromUpdateApi.jsonPath().getString("status"), "New name should be : "+updateNewUser.getStatus());

    }

    @Test
    @DisplayName("Update not existing user information")
    public void TestUpdateNotExistingUser(){
        //Gen Test data
        User newUserPayload = TestDataUtil.generateRandomUser();

        //Get user id
        int userId = 999999999;

        //Get email
        String userEmail = newUserPayload.getEmail();

        //New data for update user
        User updateNewUser = new User("NameUpdated", "update." + userEmail, "female", "inactive");

        //Update user via Api
        Response responseFromUpdateApi = userClient.updateUser(userId, updateNewUser);
        responseFromUpdateApi.prettyPrint();

        //Validate response
        assertEquals(404, responseFromUpdateApi.statusCode(), "Status code should be : 404");
        System.out.println("Cannot update not existing user with this code : " + responseFromUpdateApi.statusCode());
    }
}
