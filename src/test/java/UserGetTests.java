import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.hasLength;
import static org.hamcrest.Matchers.is;

public class UserGetTests {

    @Test
    void successGetSingleUserTest() {

        given()
                .log().uri()

        .when()
            .get
                    ("https://reqres.in/api/users/2")

        .then()
            .log().status()
            .log().body()
            .statusCode(200)
            .body("data.id", is(2))
            .body("data.email", is("janet.weaver@reqres.in"));
    }

    @Test
    void userNotFoundTest() {

        given()
            .log().uri()
        .when()
            .get
                    ("https://reqres.in/api/users/23")

        .then()
            .log().status()
            .log().body()
            .statusCode(404);
    }
    @Test
    void successGetListUsersTest() {

        given()
            .log().uri()

        .when()
            .get("https://reqres.in/api/users?page=2")

        .then()
            .log().status()
            .log().body()
            .statusCode(200)
            .body("page", is(2))
            .body("per_page", is(6))
            .body("support.url", is("https://reqres.in/#support-heading"));
    }


    @Test
    void successSingleResourceTest() {

        given()
                .log().uri()

        .when()
                .get("https://reqres.in/api/unknown/2")

        .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", is(2))
                .body("support.url", is("https://reqres.in/#support-heading"));
    }

    @Test
    void singleResourceNotFoundTest() {

        given()
                .log().uri()

        .when()
                .get("https://reqres.in/api/unknown/23")

        .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }
}
