package service;

import br.feevale.labex.model.Area;
import br.feevale.labex.repository.AreaRepository;
import br.feevale.labex.repository.KnowledgeRepository;
import br.feevale.labex.service.KnowledgeService;
import br.feevale.labex.service.KnowledgeServiceImpl;
import mocks.KnowledgesAndAreasMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by 0126128 on 02/06/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class KnowledgeServiceTest {

    private static final String PARAM = "Java";
    private static final Long VALID_ID = 1L;
    private static final Long ANOTHER_ID = 2L;

    @Mock
    private KnowledgeRepository repository;

    @Mock
    private AreaRepository areaRepository;

    private KnowledgeService service;

    @Before
    public void setUp(){
        service = new KnowledgeServiceImpl(repository, areaRepository, null);
    }

    @Test
    public void saveAreaAlreadyExistent(){
        when(areaRepository.findAreaByName(PARAM)).thenReturn(KnowledgesAndAreasMock.getArea(PARAM, VALID_ID));
        when(areaRepository.saveAndFlush(any())).thenReturn(KnowledgesAndAreasMock.getArea(PARAM, ANOTHER_ID));
        assertThat(service.saveArea(new Area(PARAM)).getId()).isEqualTo(VALID_ID);
    }

}
