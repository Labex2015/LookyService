package mocks;

import br.feevale.labex.controller.mod.EvaluationMod;
import br.feevale.labex.model.Evaluation;

/**
 * Created by 0126128 on 16/06/2015.
 */
public class EvaluationMocks {

    public static EvaluationMod getEvaluationMod(){
        EvaluationMod mod = new EvaluationMod();
        mod.setAnswerPoints(2);
        mod.setHelpPoints(3);
        mod.setComment("Médio");

        return mod;
    }

    public static EvaluationMod getInvalidEvaluationMod(){
        EvaluationMod mod = new EvaluationMod();
        mod.setAnswerPoints(2);
        mod.setHelpPoints(6);
        mod.setComment("");

        return mod;
    }

    public static Evaluation loadEvaluationWithId(Long id, EvaluationMod mod){
        Evaluation evaluation = new Evaluation();
        evaluation.loadEvaluationToSave(mod);
        evaluation.setId(id);
        return evaluation;
    }
}
