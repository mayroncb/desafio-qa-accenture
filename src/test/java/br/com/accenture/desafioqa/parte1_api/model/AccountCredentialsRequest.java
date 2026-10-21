package br.com.accenture.desafioqa.parte1_api.model;

/**
 * Payload de requisição para criação de usuário, geração de token e verificação de autorização.
 */
public class AccountCredentialsRequest {

    private String userName;
    private String password;

    public AccountCredentialsRequest() {
    }

    public AccountCredentialsRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

