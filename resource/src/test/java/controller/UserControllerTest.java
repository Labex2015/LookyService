package controller;

import br.feevale.labex.controller.UserController;
import br.feevale.labex.controller.mod.InteractionMod;
import br.feevale.labex.controller.mod.Profile;
import br.feevale.labex.controller.mod.UserMod;
import br.feevale.labex.controller.response.BaseCollectionResponse;
import br.feevale.labex.model.*;
import br.feevale.labex.service.InteractionService;
import br.feevale.labex.service.KnowledgeService;
import br.feevale.labex.service.user.UserService;
import mocks.EvaluationMocks;
import mocks.InteractionMocks;
import mocks.SearchMock;
import mocks.UserAndAccountMocks;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
/**
 * Created by 0126128 on 01/06/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    private static final Long USER_VALID = 1L;
    private static final Long USER_VALID_2 = 2L;
    private static final Long USER_INVALID = 7890000000000L;

    private static final Long INTERACTION_VALID = 3L;
    private static final Long INTERACTION_INVALID = 3151515151L;

    private User user;
    AccountType accountType;
    Account account;

    @Mock
    public UserService service;
    @Mock
    public InteractionService serviceInteractions;
    @Mock
    public KnowledgeService knowledgeService;

    public UserController controller;


    @org.junit.Before
    public void setUp(){
        controller = new UserController(service, serviceInteractions, knowledgeService);
        createAccount();
    }

    @Test
    public void findUserWithId(){
        UserMod userMod = new UserMod(getDefaultUser());
        ResponseEntity responseEntity = new ResponseEntity(userMod, HttpStatus.OK);
        given(service.getProfile(USER_VALID)).willReturn(userMod);

        assertThat(controller.loadProfile(USER_VALID)).isEqualTo(responseEntity);

    }

    @Test
    public void findUserWithInvalidId(){
        given(service.getProfile(USER_INVALID)).willReturn(null);
        assertThat(controller.loadProfile(USER_INVALID)).isEqualTo(new ResponseEntity(HttpStatus.NO_CONTENT));
    }

    @Test
    public void loadUserProfile(){
        given(service.getPublicProfile(USER_VALID)).willReturn(UserAndAccountMocks.returnUserProfile(USER_VALID));
        assertThat(controller.loadPublicProfile(USER_VALID).getBody()).isInstanceOf(Profile.class);
    }

    @Test
    public void loadUserProfileInvalid(){
        given(service.getPublicProfile(USER_VALID)).willReturn(UserAndAccountMocks.returnUserProfile(USER_VALID));
        assertThat(((ResponseEntity) controller.loadPublicProfile(USER_INVALID)).getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);

    }

    //@Test
    public void listAllInteractions(){
        given(serviceInteractions.findActiveInteractions(USER_VALID))
                .willReturn(InteractionMocks.getActiveInteractionList(USER_VALID, 32L, 25L, 1L, 8L, 175L, 1665L));

        List<Interaction> interactions = InteractionMocks.getActiveInteractionList(USER_VALID, 32L, 25L, 1L, 8L, 175L, 1665L);

        ResponseEntity responseEntity = new ResponseEntity(new BaseCollectionResponse(() -> {
            List<InteractionMod> helpMods = interactions.stream().map(InteractionMod::new)
                    .collect(Collectors.toCollection(ArrayList<InteractionMod>::new));
            return helpMods;
        }), HttpStatus.OK);
        assertThat(((ResponseEntity) controller.getActiveInteractions(USER_VALID)).getStatusCode())
                .isEqualTo(responseEntity.getStatusCode());
        assertThat(compareBaseCollections((BaseCollectionResponse) controller.getActiveInteractions(USER_VALID).getBody()
                ,(BaseCollectionResponse) responseEntity.getBody())).isEqualTo(true);
    }

    @Test
    public void listAllInteractionsEmpty(){
        given(serviceInteractions.findActiveInteractions(USER_VALID))
                .willReturn(new ArrayList<Interaction>());

        assertThat(controller.getActiveInteractions(USER_VALID)).isEqualTo(new ResponseEntity(HttpStatus.NO_CONTENT));
    }

    private Boolean compareBaseCollections(BaseCollectionResponse body, BaseCollectionResponse body1) {
        if(body.data.size() != body1.data.size())
            return false;

        return true;
    }

    @Test
    public void notifyNoContentForActiveInteractions(){
        given(serviceInteractions.findActiveInteractions(any())).willReturn(new ArrayList());

        assertThat(controller.getActiveInteractions(USER_VALID))
                .isEqualToComparingFieldByField(new ResponseEntity(HttpStatus.NO_CONTENT));
    }

    @Test
    public void notifyBadParameterForActiveInteractions(){
        assertThat(controller.getActiveInteractions(0L)).isEqualTo(new ResponseEntity("Parâmetro inválido-> 0", HttpStatus.UNPROCESSABLE_ENTITY));
    }

    @Test
    public void closeInteractionNotFound(){
        given(serviceInteractions.findById(any())).willReturn(null);

        assertThat(controller.closeInteraction(USER_VALID, USER_INVALID, null)).
                isEqualToComparingFieldByField(new ResponseEntity(
                        String.format("Nenhuma interação localizada para o ID %s", USER_INVALID), HttpStatus.NO_CONTENT));
    }

    @Test
    public void closeInteractionWithBadURLParameter(){
        assertThat(controller.closeInteraction(0L,0L, null)).
                isEqualToComparingFieldByField(new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY));
    }

    @Test
    public void closeInteractionWithoutBodyParameter(){
        given(serviceInteractions.findById(INTERACTION_VALID))
                .willReturn(InteractionMocks.getActiveInteraction(USER_VALID, USER_VALID_2));
        given(service.getUserHelperInteraction(INTERACTION_VALID))
                .willReturn(UserAndAccountMocks.returnUserComplete(USER_VALID_2));

        assertThat(controller.closeInteraction(USER_VALID, INTERACTION_VALID, null))
                .isEqualToComparingFieldByField(new ResponseEntity("Você deve enviar uma avaliação.",
                                                HttpStatus.UNPROCESSABLE_ENTITY));
    }

    @Test
    public void closeInteractionWithInvalidBodyParameter(){
        given(serviceInteractions.findById(INTERACTION_VALID))
                .willReturn(InteractionMocks.getActiveInteraction(USER_VALID, USER_VALID_2));
        given(service.getUserHelperInteraction(INTERACTION_VALID))
                .willReturn(UserAndAccountMocks.returnUserComplete(USER_VALID_2));

        assertThat(controller.closeInteraction(USER_VALID, INTERACTION_VALID, EvaluationMocks.getInvalidEvaluationMod())).
        isEqualToComparingFieldByField(new ResponseEntity(String.format("Parâmetro %s inválido, verificar requisitos.",
                "helpPoints"), HttpStatus.UNPROCESSABLE_ENTITY));
    }

    @Test
    public void closeInteractionWithBodyOK(){
        given(serviceInteractions.findById(any()))
                .willReturn(InteractionMocks.getActiveInteraction(USER_VALID, USER_VALID_2));
        given(service.getUserHelperInteraction(any()))
                .willReturn(UserAndAccountMocks.returnUserComplete(USER_VALID_2));
        given(service.getUserByInteraction(INTERACTION_VALID, USER_VALID))
                .willReturn(UserAndAccountMocks.returnUserComplete(USER_VALID_2));

        given(serviceInteractions.closeInteraction(any(), any()))
                .willReturn(true);
        assertThat(controller.closeInteraction(USER_VALID, INTERACTION_VALID, EvaluationMocks.getEvaluationMod())).
                isEqualToComparingFieldByField(new ResponseEntity(HttpStatus.OK));
    }

    @Test
    public void closeInteractionWithoutBodyOK(){
        given(serviceInteractions.findById(any()))
                .willReturn(InteractionMocks.getActiveInteraction(USER_VALID_2, USER_VALID));
        given(service.getUserHelperInteraction(any()))
                .willReturn(UserAndAccountMocks.returnUserComplete(USER_VALID));

        given(service.getUserByInteraction(INTERACTION_VALID, USER_VALID))
                .willReturn(UserAndAccountMocks.returnUserComplete(USER_VALID_2));

        given(serviceInteractions.closeInteraction(any(), any()))
                .willReturn(true);

        assertThat(controller.closeInteraction(USER_VALID, INTERACTION_VALID, null)).
                isEqualToComparingFieldByField(new ResponseEntity(HttpStatus.OK));
    }
/*
    @Test
    public void closeInteractionAndLoadUserToSendNotification(){
        User user = UserAndAccountMocks.returnUserComplete(USER_VALID);
        user.setId(2L);
        given(service.findById(2L)).willReturn(user);
        given(serviceInteractions.findById(INTERACTION_VALID))
                .willReturn(InteractionMocks.getActiveInteraction(2L, 1L));

        assertThat()
        controller.closeInteraction(USER_VALID, INTERACTION_VALID, null);
    }*/

   /* @Test
    public void loadPendingInteractions(){
        given(serviceInteractions.loadPendingInteractions(any()))
                .willReturn(InteractionMocks.getActiveInteractionList(1L, 2L,3L,4L));

        assertThat(((ResponseEntity)controller.getPendingInteractions(4L)).getStatusCode())
                .isEqualTo(HttpStatus.OK);

        assertThat(((ResponseEntity<List<InteractionMod>>)controller.getPendingInteractions(4L)).getBody().size())
                .isEqualTo(3);
    }

    @Test
    public void loadPendingInteractionsNoContent(){
        given(serviceInteractions.loadPendingInteractions(any()))
                .willReturn(null);

        assertThat(((ResponseEntity)controller.getPendingInteractions(1L)).getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void loadPendingInteractionsInvalidParameter(){
        assertThat(((ResponseEntity)controller.getPendingInteractions(0L)).getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }*/

    //@Test TODO: Implementar mediante questoes de seguranca
    public void loadPendingInteractionsInvalidUser(){

    }


    //@Test
    public void searchHelpInvalidParameters(){
        SearchItem invalid = SearchMock.getInvalidSearch();
        assertThat(((ResponseEntity) controller.searchHelp(USER_INVALID,invalid.param, invalid.subject,
                                                           invalid.position, invalid.max)).getStatusCode()
        ).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    public void searchHelpValidParameters(){
        SearchItem searchItem = SearchMock.getSearch(null);
        given(service.searchUsersToHelp(searchItem.param, searchItem.position, searchItem.max, null, USER_VALID))
                .willReturn(UserAndAccountMocks.returnUserModList());
        ResponseEntity responseEntity = (ResponseEntity) controller.searchHelp(USER_VALID, searchItem.param, searchItem.subject,
                                                                               searchItem.position, searchItem.max);
        assertThat(responseEntity.getStatusCode()
        ).isEqualTo(HttpStatus.OK);
        assertThat(((List)responseEntity.getBody()).size()
        ).isEqualTo(3);
    }

    private User getDefaultUser(){
        user = new User();
        user.setId(1L);
        user.setName("James Bond");
        user.setEmail("james_bond@mi6.com");
        user.setDegree(null);
        user.setSemester(7);
        user.setDeviceKey("213100awdwad2320120dwadwad44334_dawdaw323434__0dwad");
        user.setDescription("Vodka, poker e mulheres.");
        user.setLatitude(42.712202F);
        user.setLongitude(19.359772F);
        user.setUsername("bond007");

        service.saveAccountType(accountType);
        service.saveAccount(account);

        user.setAccount(account);
        return user;
    }

    private void createAccount(){
        accountType = new AccountType("GOOGLE");
        accountType.setId(1L);
        account = new Account("bond007@gmail.com", accountType);
        account.setId(1L);
    }

}
