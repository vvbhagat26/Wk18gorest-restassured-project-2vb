package com.gorest.gorestinfo;

import com.gorest.constants.EndPoints;
import com.gorest.constants.Path;
import com.gorest.model.UserPojo;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;

import java.util.HashMap;

public class UserSteps {

    @Step("Get all user information")
    public ValidatableResponse getAllUsers(){
        return SerenityRest.given().log().all()
                .when()
                .get(Path.USERS)
                .then()
                .statusCode(200);
    }

    @Step("Create a new user with name : {0}, email : {1}, gender : {2}, status : {3}")
    public ValidatableResponse createAUser(String name, String email, String gender, String status){

        UserPojo userPojo=new UserPojo();
        userPojo.setName(name);
        userPojo.setEmail(email);
        userPojo.setGender(gender);
        userPojo.setStatus(status);

        return SerenityRest.given()
                .body(userPojo)
                .header("Content-Type","application/json")
                .header("Authorization","Bearer f5f18de054960f1caf494dcc62e0d13121ee13887f4166670deda22b5a7a1e41")
                .when()
                .post(Path.USERS)
                .then().log().all();
    }

    @Step("(Verifying user is created with userID : {0})")
    public HashMap<String,Object> getUserInfoByUserID(int userId){
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer f5f18de054960f1caf494dcc62e0d13121ee13887f4166670deda22b5a7a1e41")
                .pathParam("userID",userId)
                .when()
                .get(Path.USERS + EndPoints.GET_SINGLE_USER_BY_ID)
                .then().statusCode(200).extract().path("");
    }

    @Step("Updating user with userId : {0},name : {1}, email : {2}, gender {3}, status : {4}")
    public ValidatableResponse updateUser(int userId,String name, String email, String gender, String status){
        UserPojo userPojo=new UserPojo();
        userPojo.setName(name);
        userPojo.setEmail(email);
        userPojo.setGender(gender);
        userPojo.setStatus(status);

        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer f5f18de054960f1caf494dcc62e0d13121ee13887f4166670deda22b5a7a1e41")
                .pathParam("userID",userId)
                .body(userPojo)
                .when()
                .put(Path.USERS+EndPoints.UPDATE_USER_BY_ID)
                .then().log().all();
    }
    @Step("Deleting user information with userId : {0}")
    public ValidatableResponse deleteUser(int userId) {
        return SerenityRest.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer f5f18de054960f1caf494dcc62e0d13121ee13887f4166670deda22b5a7a1e41")
                .pathParam("userID", userId)
                .when()
                .delete(Path.USERS + EndPoints.DELETE_USER_BY_ID)
                .then().log().all();
    }

    @Step("Getting user information with userId : {0}")
    public ValidatableResponse getUserById(int userId) {
        return SerenityRest.given()
                .pathParam("userID",userId)
                .when()
                .get(Path.USERS + EndPoints.GET_SINGLE_USER_BY_ID)
                .then().log().all();
    }
}
