package com.trabalhosd.votacao.exception;

public class CandidatoNotFoundException extends RuntimeException {
    public CandidatoNotFoundException() {
        super("Candidato não encontrada");
    }

    public CandidatoNotFoundException(String message) {
        super(message);
    }
}