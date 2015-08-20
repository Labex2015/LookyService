package service;

import br.feevale.labex.controller.mod.EvaluationMod;
import br.feevale.labex.model.Evaluation;
import br.feevale.labex.model.Interaction;
import br.feevale.labex.repository.EvaluationRepository;
import br.feevale.labex.repository.InteractionRepository;
import br.feevale.labex.service.InteractionService;
import br.feevale.labex.service.InteractionServiceImpl;
import mocks.EvaluationMocks;
import mocks.InteractionMocks;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by 0126128 on 03/06/2015.
 */

@RunWith(MockitoJUnitRunner.class)
public class InteractionServiceTest {

    private static final Long VALID_ID = 1L;
    private static final Long INVALID_ID = 988889898989898998L;
    private static final Long ID_ZERO = 0L;

    @Mock
    private InteractionRepository repository;

    @Mock
    private EvaluationRepository evaluationRepository;

    private InteractionService service;

    @Before
    public void setUp(){
        service = new InteractionServiceImpl(repository, evaluationRepository);
    }

    @Test
    public void findUserWithInteractions(){
        List list = InteractionMocks.getActiveInteractionList(VALID_ID, 21L, 23L, 234L, 533L, 5102L);
        when(repository.findByUser(VALID_ID)).thenReturn(list);
        assertThat(service.getUserInteractions(VALID_ID)).isEqualTo(list);
    }

    @Test
    public void findUserWithNoInteractions(){
        List list = InteractionMocks.getActiveInteractionList(VALID_ID, 21L, 23L, 234L, 533L, 5102L);
        when(repository.findByUser(VALID_ID)).thenReturn(list);
        assertThat(service.getUserInteractions(INVALID_ID)).isEmpty();
    }

    @Test
    public void closeInteractionWithErrorOnSaveInteraction(){
        when(evaluationRepository.saveAndFlush(EvaluationMocks.loadEvaluationWithId(0L, EvaluationMocks.getEvaluationMod())))
                .thenReturn(EvaluationMocks.loadEvaluationWithId(0L, EvaluationMocks.getEvaluationMod()));

        assertThat(service.closeInteraction(InteractionMocks.getActiveInteraction(1L, 3L), EvaluationMocks.getEvaluationMod()
                )).isEqualTo(false);
    }

    @Test
    public void closeInteractionWithErrorOnSaveEvaluation(){
        when(repository.saveAndFlush(any())).thenThrow(new RuntimeException());
        assertThat(service.closeInteraction(InteractionMocks.getActiveInteraction(1L, 3L), EvaluationMocks.getEvaluationMod()
        )).isEqualTo(false);
    }

    @Test
    public void closeInteractionWithEvaluation(){
        EvaluationMod mod = EvaluationMocks.getEvaluationMod();
        Evaluation evaluation = EvaluationMocks.loadEvaluationWithId(3L, mod);
        Interaction interaction = InteractionMocks.getActiveInteraction(1L, 2L);
        interaction.setId(7L);

        when(evaluationRepository.saveAndFlush(any())).thenReturn(evaluation);
        assertThat(service.closeInteraction(interaction, EvaluationMocks.getEvaluationMod())).isEqualTo(true);
    }

    @Test
    public void closeInteractionWithoutEvaluation(){
        assertThat(service.closeInteraction(InteractionMocks.getActiveInteraction(1L, 2L),null)).isEqualTo(true);
    }


}
