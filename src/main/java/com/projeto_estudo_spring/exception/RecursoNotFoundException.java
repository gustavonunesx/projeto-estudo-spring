package com.projeto_estudo_spring.exception;

public class RecursoNotFoundException extends RuntimeException{
    
    public RecursoNotFoundException(String mensagem){
        super(mensagem);
    }
}
