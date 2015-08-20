package br.feevale.labex.controller.mod;

import br.feevale.labex.exceptions.InvalidFieldException;
import br.feevale.labex.model.RequestHelp;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by 0126128 on 03/06/2015.
 */
public class RequestHelpMod {


    private Long id;
    private UserMod requester;
    private UserMod helper;
    @NotNull
    @Size(min = 15, max = 255)
    protected String text;
    @NotNull
    @Size(min = 1, max = 255)//TODO: validar restrição, verificar como vai ser feito
    protected String params;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    protected Date date;
    private char status;
    private char type;

    public RequestHelpMod() {}

    public RequestHelpMod(RequestHelp help) {
        setId(help.getId());
        setRequester(new UserMod(help.getRequester()));
        setHelper(new UserMod(help.getHelper()));
        setText(help.getText());
        setParams(help.getParams());
        setDate(help.getDate());
        setStatus(help.getStatus());
        setType(help.getType());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserMod getRequester() {
        return requester;
    }

    public void setRequester(UserMod requester) {
        this.requester = requester;
    }

    public UserMod getHelper() {
        return helper;
    }

    public void setHelper(UserMod helper) {
        this.helper = helper;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public static void validateMe(RequestHelpMod requestHelpMod) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        final Set<ConstraintViolation<RequestHelpMod>> errors = validator.validate(requestHelpMod);

        final Iterator<ConstraintViolation<RequestHelpMod>> iteratorCategory = errors.iterator();
        if (iteratorCategory.hasNext()) {
            final ConstraintViolation<RequestHelpMod> violation = iteratorCategory.next();
            throw new InvalidFieldException(violation.getPropertyPath().toString(), violation.getMessage());
        }
    }
}
