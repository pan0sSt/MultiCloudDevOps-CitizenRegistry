package gr.ekpa.citizen.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.context.WebApplicationContext;

import gr.ekpa.citizen.domain.Citizen;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class CitizenServiceIntegrationTest {

	@LocalServerPort
	private int port;
	
	@Autowired
	private WebApplicationContext context;
	
    @BeforeAll
    public void setup() {
    	RestAssuredMockMvc.webAppContextSetup(context);
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    @Order(1)
    public void testCreateCitizen() {
        String citizenJson = "{ \"id\": \"A1234561\", \"firstName\": \"John\", \"lastName\": \"Doe\", \"gender\": \"Male\", \"birthDate\": \"12-11-2000\" }";

        given()
            .contentType(ContentType.JSON)
            .body(citizenJson)
        .when()
            .post("/api/citizens")
        .then()
            .statusCode(201)
            .body("firstName", equalTo("John"))
            .body("lastName", equalTo("Doe"));
    }
    
    @Test
    @Order(2)
    public void testCreateWrongCitizen() {
    	Citizen citizen = new Citizen();

        given()
            .contentType(ContentType.JSON)
            .body(citizen)
        .when()
            .post("/api/citizens")
        .then()
            .statusCode(400);
    }
    
    @Test
    @Order(3)
    public void testUpdateCitizen() {
        String updateJson = "{ \"taxId\": \"123456789\", \"address\": \"123 Main St\" }";

        given()
            .contentType(ContentType.JSON)
            .body(updateJson)
        .when()
            .put("/api/citizens/A1234561")
        .then()
            .statusCode(200)
            .body("taxId", equalTo("123456789"))
            .body("address", equalTo("123 Main St"));
    }
    
    @Test
    @Order(4)
    public void testUpdateWrongCitizen() {
        String updateJson = "{ \"taxId\": \"123456781\", \"address\": \"123 Main St\" }";

        given()
            .contentType(ContentType.JSON)
            .body(updateJson)
        .when()
            .put("/api/citizens/A123")
        .then()
            .statusCode(400);
    }
    
    @Test
    @Order(5)
    public void testUpdateInvalidTaxId() {
        String updateJson = "{ \"taxId\": \"12345\", \"address\": \"123 Main St\" }";

        given()
            .contentType(ContentType.JSON)
            .body(updateJson)
        .when()
            .put("/api/citizens/A12")
        .then()
            .statusCode(400);
    }
    
    @Test
    @Order(6)
    public void testGetCitizen() {
        given()
        .when()
            .get("/api/citizens/A1234561")
        .then()
            .statusCode(200)
            .body("id", equalTo("A1234561"));
    }
    
    @Test
    @Order(7)
    public void testGetWrongCitizen() {
        given()
        .when()
            .get("/api/citizens/A1232222")
        .then()
            .statusCode(404);
    }
    
    @Test
    @Order(8)
    public void testGetInvalidCitizen() {
        given()
        .when()
            .get("/api/citizens/A123")
        .then()
            .statusCode(400);
    }
    
    @Test
    @Order(9)
    public void testSearchCitizen() {
        given()
            .queryParam("firstName", "John")
        .when()
            .get("/api/citizens/")
        .then()
            .statusCode(200)
            .body("[0].firstName", equalTo("John"));
    }
    
    @Test
    @Order(10)
    public void testSearchInvalidCitizen() {
        given()
            .queryParam("birthDate", "12/12/1990")
        .when()
            .get("/api/citizens/")
        .then()
            .statusCode(400);
    }
    
    @Test
    @Order(11)
    public void testSearchEmptyCitizen() {
        given()
            .queryParam("firstName", "Ellie")
        .when()
            .get("/api/citizens/")
        .then()
            .statusCode(404);
    }
    
    @Test
    @Order(12)
    public void testDeleteCitizen() {
        given()
        .when()
            .delete("/api/citizens/A1234561")
        .then()
            .statusCode(200);
    }
    
    @Test
    @Order(13)
    public void testDeleteWrongCitizen() {
        given()
        .when()
            .delete("/api/citizens/A1234562")
        .then()
            .statusCode(404);
    }
    
    @Test
    @Order(14)
    public void testDeleteInvalidCitizen() {
        given()
        .when()
            .delete("/api/citizens/A123")
        .then()
            .statusCode(400);
    }
}