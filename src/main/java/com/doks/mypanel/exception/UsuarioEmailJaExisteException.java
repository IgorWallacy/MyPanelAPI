package com.doks.mypanel.exception;

public class UsuarioEmailJaExisteException extends  RuntimeException {

    public UsuarioEmailJaExisteException(String message) {
        super(message);
    }
}
