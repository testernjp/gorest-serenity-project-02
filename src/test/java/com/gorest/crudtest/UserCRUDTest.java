package com.gorest.crudtest;

import com.gorest.testbase.TestBase;
import com.gorest.userinfo.UserSteps;
import com.gorest.util.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class UserCRUDTest extends TestBase {


    static String name = TestUtils.getRandomValue() + "Newtester";
    static String email = TestUtils.getRandomValue() + "abc@gmail.com";
    static String gender = "male";
    static String status = "active";
    static int id;

    @Steps
    UserSteps steps;

    @Title("User created successfully")
    @Test
    public void test001() {

        ValidatableResponse response = steps.createUser(name, email, gender, status);
        response.statusCode(201);
    }

    @Title("Verify if the user was created successfully")
    @Test
    public void test002() {

        HashMap<String, Object> userMap = steps.getUserInfoById(name);
        Assert.assertThat(userMap, hasValue(name));
        id = (int) userMap.get("id");
    }


    @Title("Update the user information and verify the updated information")
    @Test
    public void test003() {
        ValidatableResponse response = steps.updateUser(id, name, email, gender, status);
        response.statusCode(200);
    }


    @Title("Verify if the user was deleted successfully")
    @Test
    public void test004() {
        steps.deleteUser(id).statusCode(204);
        steps.getUserById(id).statusCode(404);
    }
}