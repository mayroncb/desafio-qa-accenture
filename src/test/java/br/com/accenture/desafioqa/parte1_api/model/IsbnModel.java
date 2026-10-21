package br.com.accenture.desafioqa.parte1_api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Representa um item de ISBN na coleção de livros alugados.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IsbnModel {

    private String isbn;

    public IsbnModel() {
    }

    public IsbnModel(String isbn) {
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}

