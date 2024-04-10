package com.spring.apirest.infra.error;

import org.springframework.http.HttpStatus;

import java.util.List;

//Classe responsavel pela padronização dos erros
public class RestErrorMessage {
    private HttpStatus status;
    private List<String> menssage;

    public RestErrorMessage(HttpStatus status, List<String> menssage) {
        this.status = status;
        this.menssage = menssage;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public List<String> getMenssage() {
        return menssage;
    }

    public void setMenssage(List<String> menssage) {
        this.menssage = menssage;
    }
}
