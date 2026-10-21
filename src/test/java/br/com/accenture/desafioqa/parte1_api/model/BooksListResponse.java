package br.com.accenture.desafioqa.parte1_api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Modelo de resposta para listagem de livros (GET /BookStore/v1/Books).
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BooksListResponse {

    private List<BookModel> books;

    public List<BookModel> getBooks() {
        return books;
    }

    public void setBooks(List<BookModel> books) {
        this.books = books;
    }
}

