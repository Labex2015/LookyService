package controller;

import br.feevale.labex.controller.KnowledgeController;
import br.feevale.labex.controller.mod.KnowledgeMod;
import br.feevale.labex.model.*;
import br.feevale.labex.service.KnowledgeService;
import mocks.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by 0126128 on 01/06/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class KnowledgeControllerTest {

    private static final Long USER_VALID = 1L;
    private static final Long USER_VALID_2 = 2L;
    private static final Long USER_INVALID = 7890000000000L;

    private static final Long AREA_VALID = 1L;
    private static final Long AREA_VALID_2 = 2L;
    private static final Long AREA_INVALID = 7890000000000L;

    private static final String AREA_VALID_NAME = "Java";
    private static final String AREA_VALID_NAME_2 = "Cálculo II";

    private static final Long SUBJECT1 = 1L;

    @Mock
    public KnowledgeService service;

    public KnowledgeController controller;


    @org.junit.Before
    public void setUp(){
        controller = new KnowledgeController(service);
    }

    @Test
    public void saveKnowledgeWithAreaAlreadyExistent(){
        KnowledgeMod knowledgeMod = KnowledgesAndAreasMock.getKnowledgeWithAreaId(USER_VALID,
                AREA_VALID_NAME, null);
        when(service.saveArea(new Area(AREA_VALID_NAME)))
                .thenReturn(KnowledgesAndAreasMock.getArea(AREA_VALID_NAME, AREA_VALID));
        ResponseEntity entity = controller.saveKnowledge(knowledgeMod);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(((KnowledgeMod)entity.getBody()).area).isEqualTo(AREA_VALID);
    }

    @Test
    public void saveKnowledgeWithBadParameter(){
        KnowledgeMod knowledgeMod = KnowledgesAndAreasMock.getKnowledgeWithAreaId(USER_VALID, null, null);
        assertThat(((ResponseEntity)controller.saveKnowledge(knowledgeMod))).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    public void saveKnowledgeWithBadUser(){
        KnowledgeMod knowledgeMod = KnowledgesAndAreasMock.getKnowledgeWithAreaId(USER_INVALID, AREA_VALID_NAME, null);
        assertThat(((ResponseEntity)controller.saveKnowledge(knowledgeMod))).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
