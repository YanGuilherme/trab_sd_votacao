package com.trabalhosd.votacao.exception;

public class EleicaoNotFoundException extends RuntimeException {
    public EleicaoNotFoundException() {
        super("Eleição não encontrada");
    }

    public EleicaoNotFoundException(String message) {
        super(message);
    }
}

