package com.example.Bookify.integration_tests;


import com.example.Bookify.dto.auth.AuthenticationRequest;
import com.example.Bookify.dto.auth.AuthenticationResponse;
import com.example.Bookify.dto.auth.RegisterRequest;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.junit.runner.Request;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.DEFAULT_URI;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Testcontainers
public class BookingControllerIntegrationTest {

    @LocalServerPort
    int port;

    String authToken;

    private static RequestSpecification requestSpec;
    @Container
    @ServiceConnection
    private static final MySQLContainer<?> mysql =
            new MySQLContainer<>("mysql:8.0")
                    .withDatabaseName("testcontainerdb")
                    .withUsername("testuser")
                    .withPassword("testpass");

    @BeforeAll
    void setup(){
        RestAssured.baseURI=DEFAULT_URI;
        RestAssured.port=port;
        RequestLoggingFilter requestLoggingFilter=new RequestLoggingFilter();
        ResponseLoggingFilter responseLoggingFilter=new ResponseLoggingFilter();
        RestAssured.filters(requestLoggingFilter,responseLoggingFilter);

       RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }

    @Test
    @DisplayName("MySql Test Container Created and Running")
    @Order(1)
    void containerIsRunning() {
        assertTrue(mysql.isCreated(), "MySQL container should be created");
        assertTrue(mysql.isRunning(), "MySQL container should be running");
    }

    @Test
    @DisplayName("Create New User")
    @Order(2)
    void testCreatingUser_WhenValidDetailsProvided_ReturnCreatedUser(){
        RegisterRequest userDetailsRequest = RegisterRequest.builder()
                .name("mustafa")
                .email("mustafa12367@gmail.com")
                .password("12345Mustafa@Chris")
                .build();
        given().
                body(userDetailsRequest).
        when().
                post("/api/auth/sign-up").
        then().
                statusCode(201).
                body("id",notNullValue());

    }


    @Test
    @DisplayName("Booking a non Existing Event")
    @Order(3)
    void testBooking_WhenEventNotExist_ThrowsNotFoundException(){
        //authenticate
        AuthenticationRequest authRequest=AuthenticationRequest.builder().email("mustafa12367@gmail.com")
                .password("12345Mustafa@Chris").build();

        AuthenticationResponse response=given().
               body(authRequest).
        when().
                post("/api/auth/login").
        then().extract().response().as(AuthenticationResponse.class);


        assertNotNull(response.accessToken());
        authToken=response.accessToken();

        // try booking
      Response response2=  given().
                pathParams("eventId",3).
                auth().oauth2(authToken).
        when().
                post("/api/booking/book-event/{eventId}").
        then().
                extract().response();

      assertEquals(HttpStatus.NOT_FOUND.value(),response2.getStatusCode());


    }


}

