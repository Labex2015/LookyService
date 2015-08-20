package br.feevale.labex.service;

import br.feevale.labex.controller.mod.EvaluationMod;
import br.feevale.labex.model.Evaluation;
import br.feevale.labex.model.Interaction;

import java.util.List;

/**
 * Created by 0126128 on 03/06/2015.
 */
public interface InteractionService extends BaseService<Interaction>{
    List<Interaction> findActiveInteractions(Long userValid);

    List<Interaction> getUserInteractions(Long validId);

    Boolean closeInteraction(Interaction interaction, EvaluationMod evaluation);

    List<Interaction> loadPendingInteractions(Long idUser);

    List<Evaluation> getEvaluationsForOne(Long id);
}
