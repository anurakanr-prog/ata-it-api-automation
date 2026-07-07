package com.atait.exam.tests;

import com.atait.exam.clients.UserClient;
import com.atait.exam.models.User;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserGetApiTest {
    private final UserClient userClient = new UserClient();

    @Test
    @DisplayName("Verify Get all list : Success case")
    public void testGetAllUsers() {
        //Sent get API
        Response responseFromGetApi = userClient.getUsers();
        responseFromGetApi.prettyPrint();

        //Validate response
        assertEquals(200, responseFromGetApi.statusCode(), "Expected status code 200 for GET /users");

        List<Object> users = responseFromGetApi.jsonPath().getList("$");
        assertNotNull(users, "User list should not be null");
        assertFalse(users.isEmpty(), "User list should contain at least one user");

        assertNotNull(responseFromGetApi.jsonPath().getString("[0].id"), "First user should have an ID");
        assertNotNull(responseFromGetApi.jsonPath().getString("[0].name"), "First user should have a name");
        assertNotNull(responseFromGetApi.jsonPath().getString("[0].email"), "First user should have a email");
        assertNotNull(responseFromGetApi.jsonPath().getString("[0].gender"), "First user should have a gender");
        assertNotNull(responseFromGetApi.jsonPath().getString("[0].status"), "First user should have a status");
    }

    @Test
    @DisplayName("Verify Get User by ID : Success case")
    public void testGetUserByID() {

        //Set user id
        int userID = 8539737;

        //Sent Get by user id Api
        Response responseFromGetApi = userClient.getUser(userID);
        responseFromGetApi.prettyPrint();

        //Validate Response
        assertEquals(200, responseFromGetApi.statusCode(), "Expected status code 200 for GET /user/{id}");

        User returnUserDataFromResponse = responseFromGetApi.as(User.class);

        assertEquals("Ice Anurak Anuraksakunko", returnUserDataFromResponse.getName(), "Expected this user be : Ice Anurak Anuraksakunko");
        assertEquals("anurak.example@gmail.com", returnUserDataFromResponse.getEmail(), "Expected this user be : anurak.example@gmail.com");
        assertEquals("male", returnUserDataFromResponse.getGender(), "Expected this user be : male");
        assertEquals("active", returnUserDataFromResponse.getStatus(), "Expected this user be : active");
    }

    @Test
    @DisplayName("Verify Get User by ID but ID is not existing : Negative case")
    public void testGetUserByIDButNotExisting() {

        //Set user id
        int userID = 999999999;

        //Sent Get by user id Api
        Response responseFromGetApi = userClient.getUser(userID);
        responseFromGetApi.prettyPrint();

        //Validate Response
        assertEquals(404, responseFromGetApi.statusCode(), "Expected status code 200 for GET /user/{id}");
        System.out.println("Not found user with this code : " + responseFromGetApi.statusCode());

    }

}
