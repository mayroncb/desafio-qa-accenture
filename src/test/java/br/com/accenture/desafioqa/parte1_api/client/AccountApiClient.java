package br.com.accenture.desafioqa.parte1_api.client;

import br.com.accenture.desafioqa.config.ConfigManager;
import br.com.accenture.desafioqa.parte1_api.model.AccountCredentialsRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * Cliente HTTP responsável pelas interações com os endpoints Account (/Account/v1/*) da API DemoQA.
 */
public class AccountApiClient {

    private RequestSpecification baseRequest() {
        return RestAssured.given()
                .relaxedHTTPSValidation()
                .baseUri(ConfigManager.getApiBaseUrl())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }

    public Response createUser(String endpoint, AccountCredentialsRequest credentials) {
        return baseRequest()
                .body(credentials)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
    }

    public Response generateToken(String endpoint, AccountCredentialsRequest credentials) {
        return baseRequest()
                .body(credentials)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
    }

    public Response isAuthorized(String endpoint, AccountCredentialsRequest credentials) {
        return baseRequest()
                .body(credentials)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
    }

    public Response getUserDetails(String endpointTemplate, String userId, String bearerToken) {
        String resolvedPath = endpointTemplate.replace("{userID}", userId);
        return baseRequest()
                .header("Authorization", "Bearer " + bearerToken)
                .when()
                .get(resolvedPath)
                .then()
                .extract()
                .response();
    }
}
