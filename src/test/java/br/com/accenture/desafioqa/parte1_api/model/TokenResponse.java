package br.com.accenture.desafioqa.parte1_api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Modelo de resposta do endpoint POST /Account/v1/GenerateToken.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenResponse {

    private String token;
    private String expires;
    private String status;
    private String result;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

