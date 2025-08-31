package com.example.Bookify;
import org.junit.jupiter.api.BeforeAll;

import com.example.Bookify.dto.PageResponse;
import com.example.Bookify.dto.auth.AuthenticationRequest;
import com.example.Bookify.dto.auth.AuthenticationResponse;
import com.example.Bookify.dto.auth.RegisterRequest;
import com.example.Bookify.dto.user.UserDetailsResponse;
import com.example.Bookify.entity.user.User;
import com.example.Bookify.enums.UserRole;
import com.example.Bookify.repository.UserRepository;
import org.json.JSONException;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;

import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;


import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@SpringBootTest(webEnvironment = DEFINED_PORT, properties = "server.port=8081")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class UserControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    String authToken;
    String refreshToken;


    @Autowired
    private Environment environment;


    @Test
    @Order(1)
    void debugConfiguration() {
        System.out.println("=== CONFIGURATION DEBUG OUTPUT ===");

        // Check if test profile is active
        String[] activeProfiles = environment.getActiveProfiles();
        System.out.println("Active profiles: " + String.join(", ", activeProfiles));

        // Check specific properties from application-test.properties
        System.out.println("Datasource URL: " + environment.getProperty("spring.datasource.url"));
        System.out.println("JPA DDL Auto: " + environment.getProperty("spring.jpa.hibernate.ddl-auto"));
        System.out.println("Database Platform: " + environment.getProperty("spring.jpa.database-platform"));
        System.out.println("Server Port: " + environment.getProperty("server.port"));

        // Check if H2 is being used
        System.out.println("Datasource Driver: " + environment.getProperty("spring.datasource.driver-class-name"));

        // Check security exclusion
        System.out.println("Security Exclude: " + environment.getProperty("spring.autoconfigure.exclude"));


    }
    @Test
    @DisplayName("Create New User")
    @Order(2)
    void testUserCreation_WhenUserDetailsValid_ShouldCreateUserAndReturnUserDetails() {
        RegisterRequest userDetailsRequest = RegisterRequest.builder().name("mustafa")
                .email("mustafa12367@gmail.com")
                        .password("12345Mustafa@Chris")
                                .build();

        ResponseEntity<UserDetailsResponse> response =
                restTemplate.postForEntity("/api/auth/sign-up", userDetailsRequest, UserDetailsResponse.class);


        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }


    @Test
    @DisplayName("Getting User without token")
    @Order(3)
    void testGettingUsers_WhenNoAuthTokenProvided_ShouldReturnUnauthorized(){

        // Arrange
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity requestEntity = new HttpEntity(null, headers);

        // Act
        ResponseEntity<PageResponse<UserDetailsResponse>> response = restTemplate.exchange("/api/user",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        // Assert
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode(),
                "HTTP Status code 403 Forbidden should have been returned");
    }
    @Test
    @DisplayName("User log in")
    @Order(4)
    void testUserLogin_WhenValidCredentials_ShouldReturnAuthResponse() throws JSONException {
        //Arrange

        AuthenticationRequest authenticationRequest=AuthenticationRequest.builder()
                .email("mustafatarek112@gmail.com").password("12345Mustafa@Tarek!").build();

        //Act

        ResponseEntity<AuthenticationResponse> authenticationResponseResponseEntity
                =restTemplate.postForEntity("/api/auth/login",authenticationRequest,AuthenticationResponse.class);

        authToken=authenticationResponseResponseEntity.getBody().accessToken();
        refreshToken=authenticationResponseResponseEntity.getBody().refreshToken();
        //Assert

        Assertions.assertEquals(HttpStatus.OK,authenticationResponseResponseEntity.getStatusCode());

        Assertions.assertNotNull(authToken,"auth token mustn't be null");
        Assertions.assertNotNull(refreshToken,"refresh token mustn't be null");

    }


    @Test
    @DisplayName("Admin privileges only has access")
    @Order(5)
    void testPrivilegesWithGettingUser_WhenRoleHasNoAccess_ShouldReturnForbidden(){
        // Arrange
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        headers.setBearerAuth(authToken);

        HttpEntity requestEntity = new HttpEntity(null, headers);

        // Act
        ResponseEntity<PageResponse<UserDetailsResponse>> response = restTemplate.exchange("/api/user",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        // Assert
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode(),
                "HTTP Status code 403 Forbidden should have been returned");
    }
    @Test
    @DisplayName("Admin privileges only has access")
    @Order(6)
    void testPrivilegesWithGettingUser_WhenRoleHasAccess_ShouldReturnData(){
        // Arrange
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        headers.setBearerAuth(authToken);

        HttpEntity requestEntity = new HttpEntity(null, headers);

        // Act
        ResponseEntity<PageResponse<UserDetailsResponse>> response = restTemplate.exchange("/api/user",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        // Assert
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode(),
                "HTTP Status code  200 should have been returned");

        Assertions.assertNotNull(response.getBody(),"There must be at least one user");

    }


}

