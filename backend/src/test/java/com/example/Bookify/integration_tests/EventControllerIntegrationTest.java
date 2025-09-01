package com.example.Bookify.integration_tests;

import com.example.Bookify.dto.auth.AuthenticationRequest;
import com.example.Bookify.dto.auth.AuthenticationResponse;
import com.example.Bookify.dto.event.CategoryResponse;
import com.example.Bookify.dto.event.EventCreationRequest;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Testcontainers
class EventControllerIntegrationTest {



    @Autowired
    TestRestTemplate restTemplate;

    String authToken;


    @Container
    @ServiceConnection
    private static final MySQLContainer<?> mysql =
            new MySQLContainer<>("mysql:8.0")
                    .withDatabaseName("testcontainerdb")
                    .withUsername("testuser")
                    .withPassword("testpass");

    @Test
    @DisplayName("MySql Test Container Created and Running")
    @Order(1)
    void containerIsRunning() {
        assertTrue(mysql.isCreated(), "MySQL container should be created");
        assertTrue(mysql.isRunning(), "MySQL container should be running");
    }

    @Test
    @DisplayName("Test Category Creation")
    @Order(2)
    void testCategoryCreation_WhenValidDetails_ShouldReturnCreatedCategory(){
//arrange
        AuthenticationRequest authRequest=AuthenticationRequest.builder().email("mustafatarek112@gmail.com")
                .password("12345Mustafa@Tarek!").build();

        ResponseEntity<AuthenticationResponse> authResponse
                =restTemplate.postForEntity("/api/auth/login",authRequest,AuthenticationResponse.class);

        authToken=authResponse.getBody().accessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        headers.setBearerAuth(authToken);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

//act
        String categoryName = "Tech";
        ResponseEntity<CategoryResponse> response = restTemplate.exchange(
                "/api/event/category/{categoryName}",
                HttpMethod.POST,
                requestEntity,
                CategoryResponse.class,
                categoryName
        );

        //assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().id()>0,"id must has value");
        assertNotNull(response.getBody(),"Category must has name");
        assertEquals(categoryName, response.getBody().name(),"Category name must be same");



    }
}
