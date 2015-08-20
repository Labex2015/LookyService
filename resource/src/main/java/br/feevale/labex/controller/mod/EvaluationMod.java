package br.feevale.labex.controller.mod;

import br.feevale.labex.exceptions.InvalidFieldException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by 0126128 on 11/06/2015.
 */
public class EvaluationMod {

    private Long id;
    @Min(value = 0)
    @Max(value = 5)
    private Integer helpPoints;
    @Min(value = 0)
    @Max(value = 5)
    private Integer answerPoints;
    @NotNull
    @Size(max = 255)
    private String comment;
    private Date evaluated;
    private UserMod userEvaluator;

    public EvaluationMod() {}

    public EvaluationMod(Long id, Integer helpPoints, Integer answerPoints, String comment, Date evaluated,
                         UserMod userEvaluator) {
        this.id = id;
        this.helpPoints = helpPoints;
        this.answerPoints = answerPoints;
        this.comment = comment;
        this.evaluated = evaluated;
        this.userEvaluator = userEvaluator;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHelpPoints() {
        return helpPoints;
    }

    public void setHelpPoints(Integer helpPoints) {
        this.helpPoints = helpPoints;
    }

    public Integer getAnswerPoints() {
        return answerPoints;
    }

    public void setAnswerPoints(Integer answerPoints) {
        this.answerPoints = answerPoints;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getEvaluated() {
        return evaluated;
    }

    public void setEvaluated(Date evaluated) {
        this.evaluated = evaluated;
    }

    public UserMod getUserEvaluator() {
        return userEvaluator;
    }

    public void setUserEvaluator(UserMod userEvaluator) {
        this.userEvaluator = userEvaluator;
    }

    public static void validateEvaluation(EvaluationMod evaluationMod) {
       ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        final Set<ConstraintViolation<EvaluationMod>> errors = validator.validate(evaluationMod);

        final Iterator<ConstraintViolation<EvaluationMod>> iteratorCategory = errors.iterator();
        if (iteratorCategory.hasNext()) {
            final ConstraintViolation<EvaluationMod> violation = iteratorCategory.next();
            throw new InvalidFieldException(violation.getPropertyPath().toString(), violation.getMessage());
        }
    }


}
