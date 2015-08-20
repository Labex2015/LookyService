package controller;

import br.feevale.labex.controller.InteractionController;
import br.feevale.labex.exceptions.IllegalRequestException;
import br.feevale.labex.service.InteractionService;
import br.feevale.labex.service.RequestHelpService;
import br.feevale.labex.service.user.UserService;
import mocks.RequestHelpMock;
import mocks.ResponseMocks;
import mocks.UserAndAccountMocks;
import org.junit.Before;
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
 * Created by 0126128 on 02/07/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class InteractionControllerTest {

    @Mock
    private RequestHelpService requestHelpService;

    @Mock
    private UserService userService;

    @Mock
    private InteractionService interactionService;

    private InteractionController controller;


    @Before
    public void setUp(){
        controller = new InteractionController(requestHelpService, userService, interactionService);
    }

    @Test
    public void requestUserHelpConflict(){
        when(requestHelpService.saveRequestHelp(any(),any(),any(),any()))
                .thenThrow(new IllegalRequestException("Já existem um pedido ou uma interação para estes dois usuários"));
        assertThat(((ResponseEntity) controller.requestUserHelp(1L, 2L, RequestHelpMock.getRequestHelpMod()))
                .getStatusCode()).isEqualTo(HttpStatus.PRECONDITION_FAILED);
    }

    @Test
    public void requestUserHelpInvalidUser(){
        when(userService.findById(1L)).thenReturn(UserAndAccountMocks.returnUserComplete(1L));
        when(userService.findById(2L)).thenReturn(null);

        assertThat(((ResponseEntity) controller.requestUserHelp(1L, 2L, RequestHelpMock.getRequestHelpMod()))
                .getStatusCode()).isEqualTo(HttpStatus.PRECONDITION_FAILED);
        assertThat(controller.requestUserHelp(1L, 2L, RequestHelpMock.getRequestHelpMod())
                .getBody().toString()).isEqualTo("Impossível localizar usuário para um dos ids informados.");
    }

    @Test
    public void requestUserHelpInvalidBodyParameter(){
        assertThat(((ResponseEntity) controller.requestUserHelp(1L, 2L, RequestHelpMock.getRequestHelpModInvalid()))
                .getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    public void respondToRequestYes(){

        when(userService.findById(1L)).thenReturn(UserAndAccountMocks.returnUserComplete(1L));
        when(userService.findById(2L)).thenReturn(UserAndAccountMocks.returnUserComplete(2L));

        when(requestHelpService.findById(1L)).thenReturn(RequestHelpMock.getRequestHelpActivePrivate(1L,2L));
        assertThat(((ResponseEntity)controller.respondToRequest(2L, ResponseMocks.getPositiveResponse(1L)))
                .getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void respondToRequestNo(){

        when(userService.findById(1L)).thenReturn(UserAndAccountMocks.returnUserComplete(1L));
        when(userService.findById(2L)).thenReturn(UserAndAccountMocks.returnUserComplete(2L));
        when(requestHelpService.findById(1L)).thenReturn(RequestHelpMock.getRequestHelpActivePrivate(1L,2L));
        assertThat(((ResponseEntity)controller.respondToRequest(2L, ResponseMocks.getNegativeResponse(1L)))
                .getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void respondToRequestInvalid(){
        assertThat(((ResponseEntity)controller.respondToRequest(1L, ResponseMocks.getInvalidResponse()))
                .getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    public void respondToRequestAlreadyClosed(){
        when(userService.findById(1L)).thenReturn(UserAndAccountMocks.returnUserComplete(1L));
        when(userService.findById(2L)).thenReturn(UserAndAccountMocks.returnUserComplete(2L));
        when(requestHelpService.findById(1L)).thenReturn(RequestHelpMock.getRequestHelpClosedPrivate(1L, 2L));
        assertThat(((ResponseEntity)controller.respondToRequest(2L, ResponseMocks.getNegativeResponse(1L)))
                .getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void respondToRequestNoContent(){
        when(userService.findById(1L)).thenReturn(UserAndAccountMocks.returnUserComplete(1L));
        when(requestHelpService.findById(1L)).thenReturn(null);
        assertThat(((ResponseEntity)controller.respondToRequest(2L, ResponseMocks.getNegativeResponse(1L)))
                .getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}
