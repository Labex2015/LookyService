package br.feevale.labex.controller.mod;

import br.feevale.labex.exceptions.InvalidFieldException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by 0126128 on 03/07/2015.
 */
public abstract class BaseMod<Entity>{

    public void validateMe(Entity mod) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        final Set<ConstraintViolation<Entity>> errors = validator.validate(mod);

        final Iterator<ConstraintViolation<Entity>> iteratorCategory = errors.iterator();
        if (iteratorCategory.hasNext()) {
            final ConstraintViolation<Entity> violation = iteratorCategory.next();
            throw new InvalidFieldException(violation.getPropertyPath().toString(), violation.getMessage());
        }
    }
}
