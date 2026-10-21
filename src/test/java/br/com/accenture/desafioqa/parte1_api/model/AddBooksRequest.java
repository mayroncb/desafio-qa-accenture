package br.com.accenture.desafioqa.parte1_api.model;

import java.util.List;

/**
 * Payload de requisição para alugar livros (POST /BookStore/v1/Books).
 */
public class AddBooksRequest {

    private String userId;
    private List<IsbnModel> collectionOfIsbns;

    public AddBooksRequest() {
    }

    public AddBooksRequest(String userId, List<IsbnModel> collectionOfIsbns) {
        this.userId = userId;
        this.collectionOfIsbns = collectionOfIsbns;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<IsbnModel> getCollectionOfIsbns() {
        return collectionOfIsbns;
    }

    public void setCollectionOfIsbns(List<IsbnModel> collectionOfIsbns) {
        this.collectionOfIsbns = collectionOfIsbns;
    }
}

