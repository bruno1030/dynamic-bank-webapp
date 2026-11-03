package com.dynamicbank.webapp.client;

/**
 * Falha na chamada ao AccountService.
 * status = código HTTP devolvido pelo AccountService, ou null quando ele não pôde ser alcançado.
 */
public class AccountServiceException extends RuntimeException {

    private final Integer status;

    public AccountServiceException(String message, Integer status, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public boolean isUnavailable() {
        return status == null;
    }
}