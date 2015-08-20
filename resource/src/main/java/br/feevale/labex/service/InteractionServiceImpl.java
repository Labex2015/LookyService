package br.feevale.labex.service;

import br.feevale.labex.controller.mod.EvaluationMod;
import br.feevale.labex.model.Evaluation;
import br.feevale.labex.model.Interaction;
import br.feevale.labex.repository.EvaluationRepository;
import br.feevale.labex.repository.InteractionRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by 0126128 on 03/06/2015.
 */

@Service
public class InteractionServiceImpl implements InteractionService {


    private final InteractionRepository repository;
    private final EvaluationRepository evaluationRepository;

    @Inject
    public InteractionServiceImpl(InteractionRepository repository, EvaluationRepository evaluationRepository) {
        this.repository = repository;
        this.evaluationRepository = evaluationRepository;
    }

    @Override
    public List<Interaction> findActiveInteractions(Long userValid) {
        return repository.findByUser(userValid);
    }

    @Override
    public List<Interaction> getUserInteractions(Long validId) {
        return repository.findByUser(validId);
    }

    @Transactional
    @Override
    public Boolean closeInteraction(Interaction interaction, EvaluationMod evaluation) {

        try{
            if(evaluation != null) {
                Evaluation eval = new Evaluation();
                eval.loadEvaluationToSave(evaluation);
                eval.setInteraction(interaction);
                eval = evaluationRepository.saveAndFlush(eval);
                if (eval.getId() == 0)
                    return false;
            }
            interaction.setClosed(new Date());
            interaction.setOpen(false);
            repository.saveAndFlush(interaction);

            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Interaction> loadPendingInteractions(Long idUser) {
        return null;
    }

    @Override
    public List<Evaluation> getEvaluationsForOne(Long id) {
        return evaluationRepository.findEvaluationsToOne(id);
    }

    @Override
    public Interaction save(Interaction entity) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Interaction findById(Long id) {
        return null;
    }

    @Override
    public List<Interaction> findAll() {
        return null;
    }

    @Override
    public List<Interaction> findByParam(Object... params) {
        return null;
    }
}
