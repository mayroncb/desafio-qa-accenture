package br.com.accenture.desafioqa.parte1_api.context;

import br.com.accenture.desafioqa.parte1_api.model.AccountCredentialsRequest;
import br.com.accenture.desafioqa.parte1_api.model.BookModel;
import br.com.accenture.desafioqa.parte1_api.model.CreateUserResponse;
import br.com.accenture.desafioqa.parte1_api.model.TokenResponse;
import br.com.accenture.desafioqa.parte1_api.model.UserDetailsResponse;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Contexto compartilhado entre os steps da Parte 1 (API) via injeção de dependência (PicoContainer),
 * permitindo que todos os 6 passos sejam executados de forma contínua e em uma única execução.
 */
public class ApiTestContext {

    private AccountCredentialsRequest credentials;
    private Response lastResponse;
    private CreateUserResponse createdUser;
    private TokenResponse tokenResponse;
    private List<BookModel> availableBooks = new ArrayList<>();
    private List<BookModel> selectedBooks = new ArrayList<>();
    private UserDetailsResponse userDetailsResponse;

    public AccountCredentialsRequest getCredentials() {
        return credentials;
    }

    public void setCredentials(AccountCredentialsRequest credentials) {
        this.credentials = credentials;
    }

    public Response getLastResponse() {
        return lastResponse;
    }

    public void setLastResponse(Response lastResponse) {
        this.lastResponse = lastResponse;
    }

    public CreateUserResponse getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(CreateUserResponse createdUser) {
        this.createdUser = createdUser;
    }

    public TokenResponse getTokenResponse() {
        return tokenResponse;
    }

    public void setTokenResponse(TokenResponse tokenResponse) {
        this.tokenResponse = tokenResponse;
    }

    public List<BookModel> getAvailableBooks() {
        return availableBooks;
    }

    public void setAvailableBooks(List<BookModel> availableBooks) {
        this.availableBooks = availableBooks;
    }

    public List<BookModel> getSelectedBooks() {
        return selectedBooks;
    }

    public void setSelectedBooks(List<BookModel> selectedBooks) {
        this.selectedBooks = selectedBooks;
    }

    public UserDetailsResponse getUserDetailsResponse() {
        return userDetailsResponse;
    }

    public void setUserDetailsResponse(UserDetailsResponse userDetailsResponse) {
        this.userDetailsResponse = userDetailsResponse;
    }
}

