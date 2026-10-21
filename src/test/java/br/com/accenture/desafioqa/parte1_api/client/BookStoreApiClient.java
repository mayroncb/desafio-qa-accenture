package br.com.accenture.desafioqa.parte1_api.client;

import br.com.accenture.desafioqa.config.ConfigManager;
import br.com.accenture.desafioqa.parte1_api.model.AddBooksRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * Cliente HTTP responsável pelas interações com os endpoints BookStore (/BookStore/v1/*) da API DemoQA.
 */
public class BookStoreApiClient {

    private RequestSpecification baseRequest() {
        return RestAssured.given()
                .relaxedHTTPSValidation()
                .baseUri(ConfigManager.getApiBaseUrl())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }

    public Response listBooks(String endpoint) {
        return baseRequest()
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }

    public Response rentBooks(String endpoint, AddBooksRequest payload, String bearerToken) {
        return baseRequest()
                .header("Authorization", "Bearer " + bearerToken)
                .body(payload)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
    }
}
