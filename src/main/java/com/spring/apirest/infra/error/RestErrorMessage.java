package com.spring.apirest.infra.error;

import org.springframework.http.HttpStatus;

//Classe responsavel pela padronização dos erros
public class RestErrorMessage {
    private HttpStatus status;
    private String menssage;

    public RestErrorMessage(HttpStatus status, String menssage) {
        this.status = status;
        this.menssage = menssage;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMenssage() {
        return menssage;
    }

    public void setMenssage(String menssage) {
        this.menssage = menssage;
    }
}
