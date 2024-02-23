package tests;

import io.restassured.RestAssured;
import models.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.UserSpec.*;

import static org.assertj.core.api.Assertions.*;

public class UserGetTests {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test
    void successGetSingleUserTest() {

        UserResponseModel response = step("Запрос на получение пользователя", () ->
                given(requestSpec)
                        .when()
                        .get("/users/2")
                        .then()
                        .spec(userResponseSpec)
                        .extract().as(UserResponseModel.class));

        step("Проверка ответа", () -> {
            assertThat(response.getData().getId()).isEqualTo(2);
            assertThat(response.getData().getEmail()).isEqualTo("janet.weaver@reqres.in");
        });
    }

    @Test
    void userNotFoundTest() {

        step("Запрос не существующего пользователя", () ->
                given(userWrongRequestSpec)
                        .when()
                        .get("/users/23")
                        .then()
                        .spec(response404Spec));
    }

    @Test
    void successGetListUsersTest() {

        UsersListResponseModel response = step("Запрос на получение списка пользователей", () ->
                given(usersListRequestSpec)
                        .when()
                        .get("/users?page=2")
                        .then()
                        .spec(userResponseSpec)
                        .extract().as(UsersListResponseModel.class));

        step("Проверка ответа", () -> {
            assertThat(response.getPage()).isEqualTo(2);
            assertThat(response.getPerPage()).isEqualTo(6);
            assertThat(response.getTotal()).isEqualTo(12);
            assertThat(response.getTotalPages()).isEqualTo(2);
            assertThat(response.getData().size()).isEqualTo(6);
            assertThat(response.getData().get(0).getId()).isEqualTo(7);
            assertThat(response.getData().get(0).getEmail()).isEqualTo("michael.lawson@reqres.in");
            assertThat(response.getData().get(0).getFirstName()).isEqualTo("Michael");
            assertThat(response.getData().get(0).getLastName()).isEqualTo("Lawson");
            assertThat(response.getData().get(0).getAvatar()).isEqualTo("https://reqres.in/img/faces/7-image.jpg");
        });
    }


    @Test
    void successSingleResourceTest() {

        ResourceResponseModel response = step("Запрос на получение источника", () ->
                given(requestSpec)
                        .when()
                        .get("/unknown/2")
                        .then()
                        .spec(userResponseSpec)
                        .extract().as(ResourceResponseModel.class));

        step("Проверка ответа", () -> {
            assertThat(response.getData().getId()).isEqualTo(2);
            assertThat(response.getData().getName()).isEqualTo("fuchsia rose");
            assertThat(response.getSupport().getUrl()).isEqualTo("https://reqres.in/#support-heading");
        });
    }

    @Test
    void singleResourceNotFoundTest() {

        step("Запрос не существующего пользователя", () ->
                given(requestSpec)
                        .when()
                        .get("/unknown/23")
                        .then()
                        .spec(response404Spec));
    }

    @Test
    void successCreateUserTest() {
        UserCreateBodyModel userCreateBodyModel = new UserCreateBodyModel();
        userCreateBodyModel.setName("morpheus");
        userCreateBodyModel.setJob("leader");

        UserCreateResponseModel response = step("Запрос на создание пользователя", () ->
                given(requestPostSpec)
                        .when()
                        .body(userCreateBodyModel)
                        .post("/users")
                        .then()
                        .spec(userResponse201Spec)
                        .extract().as(UserCreateResponseModel.class));

        step("Проверка ответа", () -> {
            assertThat(response.getName()).isEqualTo("morpheus");
            assertThat(response.getJob()).isEqualTo("leader");});
    }

    @Test
    void successDeleteUserTest() {

        step("Запрос на создание пользователя", () ->
                given(requestSpec)
                        .when()
                        .delete("/users/2")
                        .then()
                        .spec(response204Spec)
                        .extract()
                        .response());
    }
}
