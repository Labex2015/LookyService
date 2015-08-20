package br.feevale.labex.controller;


import br.feevale.labex.controller.data.KnowledgesData;
import br.feevale.labex.controller.mod.EvaluationMod;
import br.feevale.labex.controller.mod.InteractionMod;
import br.feevale.labex.controller.mod.Profile;
import br.feevale.labex.controller.mod.UserMod;
import br.feevale.labex.controller.response.BaseCollectionResponse;
import br.feevale.labex.exceptions.InvalidFieldException;
import br.feevale.labex.gcm.vo.NotificationContent;
import br.feevale.labex.model.*;
import br.feevale.labex.service.InteractionService;
import br.feevale.labex.service.KnowledgeService;
import br.feevale.labex.service.user.UserService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController extends BaseController{

    private final UserService service;
    private final InteractionService interactionService;
    private final KnowledgeService knowledgeService;

    @Inject
    public UserController(UserService service, InteractionService serviceInteractions,
                          KnowledgeService knowledgeService) {
        this.service = service;
        this.interactionService = serviceInteractions;
        this.knowledgeService = knowledgeService;
    }

    @RequestMapping(value = "/user/{id}/profile/private", method = RequestMethod.GET)
    public HttpEntity loadProfile(@PathVariable Long id){
            if(id == 0)
                return new ResponseEntity(String.format("Parâmetro inválido. => %s", id),HttpStatus.UNPROCESSABLE_ENTITY);

            UserMod userMod = service.getProfile(id);
            if(userMod != null && userMod.getId() > 0)
                return new ResponseEntity(userMod, HttpStatus.OK);
            else
                return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/user/{id}/profile", method = RequestMethod.GET)
    public HttpEntity loadPublicProfile(@PathVariable Long id){
        if(id == 0)
            return new ResponseEntity(String.format("Parâmetro inválido. => %s", id),HttpStatus.UNPROCESSABLE_ENTITY);

        UserProfile profile = service.getPublicProfile(id);
        if(profile != null) {
            List<KnowledgesData> list = knowledgeService.findKnowledgesByUser(id);
            List<Evaluation> evaluations = interactionService.getEvaluationsForOne(id);
            Profile profileComplete = new Profile(profile, list,evaluations);

            return new ResponseEntity(profileComplete, HttpStatus.OK);
        }else
            return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/user/{idUser}/interactions", method = RequestMethod.GET)
    public HttpEntity getActiveInteractions(@PathVariable Long idUser) {
        if(idUser == 0)
            return new ResponseEntity(String.format("Parâmetro inválido-> %s", idUser),
                    HttpStatus.UNPROCESSABLE_ENTITY);
        List<Interaction> interactions = interactionService.findActiveInteractions(idUser);
        if(interactions.isEmpty())
            return new ResponseEntity(HttpStatus.NO_CONTENT);

        return new ResponseEntity(new BaseCollectionResponse(()->{
            List<Interaction> helpMods = interactions.stream().collect(Collectors.toList());
            return helpMods;
        }), HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{idUser}/interaction/{idInteraction}", method = RequestMethod.PUT)
    public HttpEntity closeInteraction(@PathVariable Long id, @PathVariable Long idInteraction,
                                       @RequestBody EvaluationMod evaluationMod) {
        if(id == 0 || idInteraction == 0)
            return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);

        Interaction interaction = interactionService.findById(idInteraction);
        if(interaction == null)
            return new ResponseEntity(String.format("Nenhuma interação localizada para o " +
                    "ID %s", idInteraction), HttpStatus.NO_CONTENT);

        User helper = service.getUserHelperInteraction(idInteraction);

        if(helper.getId() != id){
            if(evaluationMod == null)
                return new ResponseEntity("Você deve enviar uma avaliação.", HttpStatus.UNPROCESSABLE_ENTITY);
            try {
                EvaluationMod.validateEvaluation(evaluationMod);
            }catch (InvalidFieldException e){
                return new ResponseEntity(String.format("Parâmetro %s inválido, verificar requisitos.",
                        e.getField()), HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }

        User userToNotify = service.getUserByInteraction(idInteraction, id);
        sendNotification(userToNotify.getDeviceKey(),getNotification(userToNotify));


        return new ResponseEntity(interactionService.closeInteraction(interaction, evaluationMod) ? HttpStatus.OK :
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/user/{idUser}/interactions/pending", method = RequestMethod.GET)
    public HttpEntity getPendingInteractions(@PathVariable Long idUser) {
        if(idUser == 0)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        List<Interaction> interactions = interactionService.loadPendingInteractions(idUser);
        if(interactions == null || interactions.isEmpty())
            return new ResponseEntity(HttpStatus.NO_CONTENT);

        List<InteractionMod> mods = new ArrayList<InteractionMod>();
        for(Interaction i : interactions)
            mods.add(new InteractionMod(i));

        return new ResponseEntity<List<InteractionMod>>(mods, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/help/search", method = RequestMethod.GET)
    public HttpEntity searchHelp(@RequestBody SearchItem search) {

        if(search.param == null || search.param.isEmpty())
            return new ResponseEntity("Parâmetro de busca inválido.",
                    HttpStatus.UNPROCESSABLE_ENTITY);

        List<UserMod> resultList = service.searchUsersToHelp(search.param, search.position, search.max, search.subject);
        if(resultList.isEmpty())
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        return new ResponseEntity(resultList, HttpStatus.OK);
    }

    private NotificationContent getNotification(User user){
        NotificationContent content = new NotificationContent();
        content.setData(String.format("O usuário %s encerrou a interação entre vocês.", user.getUsername()));

        return content;
    }



}
