package tqs.restassured;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TodosApiTest {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    @Test
    void testTodosEndpointIsAvailable() {
        given().
        when().
            get(BASE_URL + "/todos").
        then().
            statusCode(200);
    }

    @Test
    void testTodo4HasCorrectTitle() {
        given().
        when().
            get(BASE_URL + "/todos/4").
        then().
            statusCode(200).
            body("title", equalTo("et porro tempora"));
    }

    @Test
    void testListAllTodosContainsIds198And199() {
        given().
        when().
            get(BASE_URL + "/todos").
        then().
            statusCode(200).
            body("id", hasItems(198, 199));
    }

    @Test
    void testListingAllTodosRespondsInLessThan2Seconds() {
        long start = System.currentTimeMillis();

        given().
        when().
            get(BASE_URL + "/todos").
        then().
            statusCode(200);

        long duration = System.currentTimeMillis() - start;
        assertTrue(duration < 2000, "Resposta demorou mais de 2 segundos!");
    }
}
