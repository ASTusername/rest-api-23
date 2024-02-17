package tests;

import io.restassured.RestAssured;
import models.ResourceResponseModel;
import models.UserResponseModel;
import models.UsersListResponseModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.UserSpec.*;

public class UserGetTests {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    void successGetSingleUserTest() {

        UserResponseModel response = step("Запрос на получение пользователя", () ->
                given(requestSpec)
                        .when()
                        .get("/api/users/2")
                        .then()
                        .spec(userResponseSpec)
                        .extract().as(UserResponseModel.class));

        step("Проверка ответа", () -> {
            assertEquals(2, response.getData().getId());
            assertEquals("janet.weaver@reqres.in", response.getData().getEmail());
        });
    }

    @Test
    void userNotFoundTest() {

        UserResponseModel response = step("Запрос не существующего пользователя", () ->
                given(userWrongRequestSpec)
                        .when()
                        .get("/api/users/23")
                        .then()
                        .spec(response404Spec)
                        .extract().as(UserResponseModel.class));
    }

    @Test
    void successGetListUsersTest() {

        UsersListResponseModel response = step("Запрос на получение списка пользователей", () ->
                given(usersListRequestSpec)
                        .when()
                        .get("/api/users?page=2")
                        .then()
                        .spec(userResponseSpec)
                        .extract().as(UsersListResponseModel.class));

        step("Проверка ответа", () -> {
            assertEquals(2, response.getPage());
            assertEquals(6, response.getPerPage());
            assertEquals(12, response.getTotal());
            assertEquals(2, response.getTotalPages());
            assertEquals(6, response.getData().size());
            assertEquals(7, response.getData().get(0).getId());
            assertEquals("michael.lawson@reqres.in", response.getData().get(0).getEmail());
            assertEquals("Michael", response.getData().get(0).getFirstName());
            assertEquals("Lawson", response.getData().get(0).getLastName());
            assertEquals("https://reqres.in/img/faces/7-image.jpg", response.getData().get(0).getAvatar());
        });
    }


    @Test
    void successSingleResourceTest() {

        ResourceResponseModel response = step("Запрос на получение источника", () ->
                given(requestSpec)
                        .when()
                        .get("/api/unknown/2")
                        .then()
                        .spec(userResponseSpec)
                        .extract().as(ResourceResponseModel.class));

        step("Проверка ответа", () -> {
            assertEquals(2, response.getData().getId());
            assertEquals("fuchsia rose", response.getData().getName());
            assertEquals("https://reqres.in/#support-heading", response.getSupport().getUrl());
        });
    }

    @Test
    void singleResourceNotFoundTest() {

        ResourceResponseModel response = step("Запрос не существующего пользователя", () ->
                given(requestSpec)
                        .when()
                        .get("/api/unknown/23")
                        .then()
                        .spec(response404Spec)
                        .extract().as(ResourceResponseModel.class));
    }
}
