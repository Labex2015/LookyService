package br.feevale.labex.controller;

import br.feevale.labex.controller.mod.RequestHelpMod;
import br.feevale.labex.controller.mod.ResponseMod;
import br.feevale.labex.exceptions.IllegalRequestException;
import br.feevale.labex.exceptions.InvalidFieldException;
import br.feevale.labex.gcm.vo.HelpContent;
import br.feevale.labex.gcm.vo.NotificationContent;
import br.feevale.labex.model.Interaction;
import br.feevale.labex.model.RequestHelp;
import br.feevale.labex.model.User;
import br.feevale.labex.service.InteractionService;
import br.feevale.labex.service.RequestHelpService;
import br.feevale.labex.service.user.UserService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * Created by 0126128 on 02/07/2015.
 */
@RestController
public class InteractionController extends BaseController{


    private final RequestHelpService service;
    private final UserService userService;
    private final InteractionService interactionService;

    @Inject
    public InteractionController(RequestHelpService service, UserService userService, InteractionService interactionService) {
        this.service = service;
        this.userService = userService;
        this.interactionService = interactionService;
    }

    @RequestMapping(value = "/user/{idUser}/request/{idHelper}", method = RequestMethod.POST)
    public HttpEntity requestUserHelp(@PathVariable Long idUser, @PathVariable Long idHelper,
                                      @RequestBody RequestHelpMod requestHelpMod) {
        try{
            RequestHelpMod.validateMe(requestHelpMod);
            User user = userService.findById(idUser);
            User helper = userService.findById(idHelper);
            if(user == null || helper == null)
                return new ResponseEntity("Impossível localizar usuário para um dos ids informados.",
                        HttpStatus.PRECONDITION_FAILED);
            RequestHelp requestHelp = service.saveRequestHelp(user, helper, requestHelpMod.getText(), requestHelpMod.getParams());
            if(requestHelp == null)
                return new ResponseEntity("Problemas ao registrar pedido de ajuda.",HttpStatus.INTERNAL_SERVER_ERROR);
            sendNotification(helper.getDeviceKey(),getHelpContent(requestHelp));
            return new ResponseEntity(HttpStatus.OK);
        }catch(Exception e){
            if(e instanceof InvalidFieldException)
                return new ResponseEntity(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
            else if(e instanceof IllegalRequestException)
                return new ResponseEntity(e.getMessage(), HttpStatus.PRECONDITION_FAILED);

            return  new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/user/{idUser}/respond")
    public HttpEntity respondToRequest(@PathVariable Long idUser,@RequestBody ResponseMod responseMod) {
        try{
            responseMod.validateMe(responseMod);
            RequestHelp request = service.findById(responseMod.id);
            if(request == null || request.getHelper().getId() != idUser
                               || request.getStatus() != RequestHelp.WAITING)
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            User askedBy = request.getHelper();

            if(responseMod.accepted) {
                Interaction interaction = new Interaction(request);
                interactionService.save(interaction);
            }
            request.setStatus(responseMod.accepted ? RequestHelp.ACCEPTED : RequestHelp.REJECTED);

            if(!responseMod.accepted) request.setHelper(null);

            service.save(request);
            sendNotification(askedBy.getDeviceKey(), getNotificationContent(responseMod.text));

            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            if(e instanceof InvalidFieldException)
                return new ResponseEntity(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private HelpContent getHelpContent(RequestHelp requestHelp){
        HelpContent helpContent = new HelpContent();
        RequestHelpMod content = new RequestHelpMod(requestHelp);
        helpContent.setData(content);
        return helpContent;
    }

    private NotificationContent getNotificationContent(String message){
        NotificationContent content = new NotificationContent();
        content.setData(message);
        return content;
    }


}
