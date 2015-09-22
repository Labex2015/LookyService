package service;

import br.feevale.labex.exceptions.IllegalRequestException;
import br.feevale.labex.model.RequestHelp;
import br.feevale.labex.repository.RequestHelpGlobalRepository;
import br.feevale.labex.repository.RequestHelpRepository;
import br.feevale.labex.service.RequestHelpService;
import br.feevale.labex.service.RequestHelpServiceImpl;
import mocks.RequestHelpMock;
import mocks.UserAndAccountMocks;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by 0126128 on 01/07/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class HelpServiceTest {

    @Mock
    private RequestHelpRepository requestHelpRepository;

    @Mock
    private RequestHelpGlobalRepository globalRepository;

    private RequestHelpService service;

    @Before
    public void setUp(){
        service = new RequestHelpServiceImpl(requestHelpRepository, globalRepository);
    }

    @Test
    public void assertRequestHelpCreation(){
        RequestHelp help = RequestHelpMock.getActiveRequestHelp(1L, 2L).get(0);

        when(service.save(any())).thenReturn(help);

        assertThat(service.saveRequestHelp(UserAndAccountMocks.returnUserComplete(1L),
                        UserAndAccountMocks.returnUserComplete(2L), "",""))
                .isNotNull();

        assertThat(service.saveRequestHelp(UserAndAccountMocks.returnUserComplete(1L),
                UserAndAccountMocks.returnUserComplete(2L), "","").getStatus())
                .isEqualTo(RequestHelp.WAITING);
    }

    @Test(expected = IllegalRequestException.class)
    public void assertRequestHelpCreationBlock(){
        when(requestHelpRepository.findByRequesterAndHelper(1L, 2L)).thenReturn(RequestHelpMock.getActiveRequestHelp(1L, 2L).get(0));
        service.saveRequestHelp(UserAndAccountMocks.returnUserComplete(1L),
                UserAndAccountMocks.returnUserComplete(2L), "","");
    }

}
