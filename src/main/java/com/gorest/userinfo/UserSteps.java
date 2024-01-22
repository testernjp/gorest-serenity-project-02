package com.gorest.userinfo;

import com.gorest.constant.EndPoints;
import com.gorest.model.UserPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;

import java.util.HashMap;

public class UserSteps {

    @Step
    public ValidatableResponse createUser(String name, String email,
                                          String gender, String status) {
        UserPojo userPojo = new UserPojo();
        userPojo.setName(name);
        userPojo.setEmail(email);
        userPojo.setGender(gender);
        userPojo.setStatus(status);

        return SerenityRest.given()
                .header("Authorization", "Bearer 03fce5af5bfff118402a50a1961ef191f6ab065e46dcdf6f5d96ea18c9496704")
                .contentType(ContentType.JSON)
                .when()
                .body(userPojo)
                .post(EndPoints.GET_ALL_USERS)
                .then().log().all();
    }

    @Step("Getting the user information with firstname :{0}")
    public HashMap<String, Object> getUserInfoById(String name) {
        String s1 = "findAll{it.name == '";
        String s2 = "'}.get(0)";

        return SerenityRest.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer 03fce5af5bfff118402a50a1961ef191f6ab065e46dcdf6f5d96ea18c9496704")
                .header("Connection", "keep-alive")
                .when()
                .get(EndPoints.GET_ALL_USERS)
                .then().statusCode(200)
                .extract()
                .path(s1 + name + s2);
    }

    @Step
    public ValidatableResponse updateUser(int userId, String name, String email,
                                          String gender, String status) {
        UserPojo userPojo = new UserPojo();
        userPojo.setName(name);
        userPojo.setEmail(email);
        userPojo.setGender(gender);
        userPojo.setStatus(status);

        return SerenityRest.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer 03fce5af5bfff118402a50a1961ef191f6ab065e46dcdf6f5d96ea18c9496704")
                .header("Connection", "keep-alive")
                .pathParam("userID", userId)
                .when()
                .body(userPojo)
                .put(EndPoints.UPDATE_USER_BY_ID)
                .then().statusCode(200);
    }

    @Step("Deleting User information with userId : {0}")
    public ValidatableResponse deleteUser(int userId) {
        return SerenityRest.given().log().all()
                .header("Authorization", "Bearer 03fce5af5bfff118402a50a1961ef191f6ab065e46dcdf6f5d96ea18c9496704")
                .header("Connection", "keep-alive")
                .contentType(ContentType.JSON)
                .pathParam("userID", userId)
                .when()
                .delete(EndPoints.DELETE_USER_BY_ID)
                .then()
                .statusCode(204);

    }

    @Step("Getting user information with userId : {0}")
    public ValidatableResponse getUserById(int userId) {

        return SerenityRest.given().log().all()
                .pathParam("userID", userId)
                .when()
                .get(EndPoints.GET_SINGLE_USER_BY_ID)
                .then()
                .statusCode(404);


    }
}
