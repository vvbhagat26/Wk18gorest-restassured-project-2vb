package com.gorest.crudtest;

import com.gorest.gorestinfo.UserSteps;
import com.gorest.testbase.TestBase;
import com.gorest.utils.TestUtils;
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
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserCRUDTest extends TestBase {

    //Body for create - post
    static String name = TestUtils.getRandomValue() + "Kia";
    static String email = TestUtils.getRandomValue() + "Kiashah@gmail.com";
    static String gender = "Female";
    static String status = "Active";
    static int userId;

    //Declare UserSteps obj.
    @Steps
    UserSteps userSteps;

    //1.Get all users (only basepath[resourses]-no endpoint)
    @Title("Get all Users Details")
    @Test
    public void test01_getAllUsers() {
        ValidatableResponse response = userSteps.getAllUsers().statusCode(200);
    }

    //2.Create-POST(only basepath[resourses]-no endpoint)
    @Title("create new user")
    @Test
    public void test02_createUser(){
        ValidatableResponse response = userSteps.createAUser(name,email,gender,status);  //This line will create a user with given body-params
        response.log().all().statusCode(201);  //This will check the status code and log it for the report
        userId=response.log().all().extract().path("id");  //This line will store the newly generated id in userId
        System.out.println(userId);  //This will print new id
    }

    //3.Read-GET(basepath[resourses]-with endpoint[newly generated id])
    @Title("Verify User Added successfully")
    @Test
    public void test03_VerifyUserId() {
        HashMap<String,Object> userMap =userSteps.getUserInfoByUserID(userId);
        Assert.assertThat(userMap,hasValue(name));
    }

    //4.Update-PUT OR PATCH(basepath[resourses]-with endpoint[newly generated id])
    @Title("Update the user information and verify the updated information")
    @Test
    public void test04_UpdateUser() {
        name = "Kiara" + TestUtils.getRandomValue();
        email = "Kiara123@gmail.com" + TestUtils.getRandomValue();
       userSteps.updateUser(userId,name,email,gender,status).statusCode(200);

       HashMap<String,Object> userMap=userSteps.getUserInfoByUserID(userId);
       Assert.assertThat(userMap,hasValue(userId));
    }

    //5.Delete - DELETE(basepath[resourses]-with endpoint[newly generated id])
    @Title("Delete the user and verify if the user is deleted")
    @Test
    public void test05_DeleteUserAndVerify() {
     userSteps.deleteUser(userId).statusCode(204);

     userSteps.getUserById(userId).statusCode(404);

    }


}

