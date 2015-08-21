package controller;

import br.feevale.labex.controller.KnowledgeController;
import br.feevale.labex.controller.mod.KnowledgeMod;
import br.feevale.labex.model.Area;
import br.feevale.labex.model.Knowledge;
import br.feevale.labex.model.KnowledgeId;
import br.feevale.labex.model.User;
import br.feevale.labex.service.KnowledgeService;
import br.feevale.labex.service.user.UserService;
import mocks.KnowledgesAndAreasMock;
import mocks.UserAndAccountMocks;
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

    private static Knowledge knowledge = null;

    @Mock
    public KnowledgeService service;
    @Mock
    public UserService userService;


    public KnowledgeController controller;


    @org.junit.Before
    public void setUp(){
        controller = new KnowledgeController(service, userService);
    }

    @Test
    public void saveKnowledgeWithAreaAlreadyExistent(){
        KnowledgeMod mod = KnowledgesAndAreasMock.getKnowledgeWithAreaId(USER_VALID,
                AREA_VALID_NAME, null);
        setKnowledge(AREA_VALID, USER_VALID);

        when(service.saveArea(any()))
                .thenReturn(KnowledgesAndAreasMock.getArea(AREA_VALID_NAME, AREA_VALID));
        when(userService.findById(mod.idUser)).thenReturn(UserAndAccountMocks
                .returnUserComplete(mod.idUser));
        when(service.save(any())).thenReturn(knowledge);

        ResponseEntity entity = controller.saveKnowledge(mod);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(((KnowledgeMod)entity.getBody()).area).isEqualTo(AREA_VALID);
    }

    @Test
    public void saveKnowledgeWithBadParameter(){
        KnowledgeMod knowledgeMod = KnowledgesAndAreasMock.getKnowledgeWithAreaId(USER_VALID, null, null);
        assertThat(controller.saveKnowledge(knowledgeMod).getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    public void saveKnowledgeWithBadUser(){
        KnowledgeMod knowledgeMod = KnowledgesAndAreasMock.getKnowledgeWithAreaId(USER_INVALID, AREA_VALID_NAME, null);
        assertThat(controller.saveKnowledge(knowledgeMod).getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }


    private static void setKnowledge(Long areaValid, Long userValid) {
        if(knowledge == null || knowledge.getId().getArea().getId() != areaValid){
            knowledge = new Knowledge();
            User user = new User();
            user.setId(userValid);
            Area area = new Area();
            area.setId(areaValid);
            knowledge.setId(new KnowledgeId(user, area));
        }
    }
}
