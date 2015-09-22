package br.feevale.labex.exceptions;

/**
 * Created by grimmjowjack on 9/9/15.
 */
public class InvalidUserNameException extends RuntimeException{

    public InvalidUserNameException(String s) {
        super(String.format("Nome de usuário %s está vinculado com outra conta",s));
    }
}
