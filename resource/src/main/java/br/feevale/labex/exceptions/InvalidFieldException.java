package br.feevale.labex.exceptions;

/**
 * Created by 0126128 on 16/06/2015.
 */
public class InvalidFieldException extends RuntimeException{

    private String field;

    public InvalidFieldException(String field, String message) {
        super(String.format("Atributo inválido: %s",message));
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
