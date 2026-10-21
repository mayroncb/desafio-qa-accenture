package br.com.accenture.desafioqa.parte1_api.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Modelo de resposta do endpoint GET /Account/v1/User/{userID}.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDetailsResponse {

    @JsonProperty("userId")
    @JsonAlias({"userID", "userId"})
    private String userId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("books")
    private List<BookModel> books;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<BookModel> getBooks() {
        return books;
    }

    public void setBooks(List<BookModel> books) {
        this.books = books;
    }
}

