package br.feevale.labex.controller;

import br.feevale.labex.controller.mod.GlobalHelpMod;
import br.feevale.labex.controller.mod.RequestHelpGlobalMod;
import br.feevale.labex.controller.mod.RequestHelpMod;
import br.feevale.labex.controller.mod.ResponseMod;
import br.feevale.labex.exceptions.IllegalRequestException;
import br.feevale.labex.exceptions.InvalidFieldException;
import br.feevale.labex.gcm.vo.HelpContent;
import br.feevale.labex.gcm.vo.NotificationContent;
import br.feevale.labex.model.Interaction;
import br.feevale.labex.model.RequestHelp;
import br.feevale.labex.model.RequestHelpGlobal;
import br.feevale.labex.model.User;
import br.feevale.labex.service.InteractionService;
import br.feevale.labex.service.RequestHelpService;
import br.feevale.labex.service.user.UserService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    @RequestMapping(value = "/user/{idUser}/respond", method = RequestMethod.PUT)
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


    @RequestMapping(value = "/user/{idUser}/interactions/pending", method = RequestMethod.GET)
    public HttpEntity getPendingInteractions(@PathVariable Long idUser) {
        if(idUser == 0)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        List<RequestHelp> interactions = service.loadPendingInteractions(idUser);
        if(interactions == null || interactions.isEmpty())
            return new ResponseEntity(HttpStatus.NO_CONTENT);

        return processRequestHelpReturn(interactions);
    }

    @RequestMapping(value = "/user/request_help/global", method = RequestMethod.POST)
    public HttpEntity openGlobalRequestHelp(@RequestBody GlobalHelpMod globalHelpMod){

        try{
            globalHelpMod.validateMe(globalHelpMod);
            User user = userService.findById(globalHelpMod.idUser);
            if(user == null)
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);

            RequestHelp requestHelp = null;

            if(globalHelpMod.idRequestHelp > 0) {
                requestHelp = service.findById(globalHelpMod.idRequestHelp);
                if(requestHelp == null || requestHelp.getStatus() == RequestHelp.CLOSED)
                    return new ResponseEntity("Pedido de ajuda inválido.",HttpStatus.UNPROCESSABLE_ENTITY);
            }else{
                if(globalHelpMod.params == null || globalHelpMod.params.length() == 0)
                    return new ResponseEntity("Você precisa definir um parâmetro de busca.",HttpStatus.UNPROCESSABLE_ENTITY);

                requestHelp = new RequestHelp();
                requestHelp.setParams(globalHelpMod.params);
                requestHelp.setRequester(user);
            }
            RequestHelpGlobal global = new RequestHelpGlobal();
            global.setDescription(globalHelpMod.description);
            global.setTitle(globalHelpMod.title);
            global.setRequestHelp(requestHelp);

            global = service.openGlobalRequestHelp(global);
            if(global != null && global.getId() > 0)
                return new ResponseEntity(HttpStatus.CREATED);

            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e){
            e.printStackTrace();
            if(e instanceof InvalidFieldException)
                return new ResponseEntity(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);

            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "/request_help/global", method = RequestMethod.GET)
    public HttpEntity getOpenGlobalRequest(){
        List<RequestHelpGlobalMod> models = service.listAllGlobalRequestHelp();
        if(models == null || models.isEmpty())
            return new ResponseEntity(HttpStatus.NO_CONTENT);

        return new ResponseEntity(models, HttpStatus.OK);
    }

    @RequestMapping(value = "/request_help/global", method = RequestMethod.DELETE)
    public HttpEntity removeGlobalRequest(@RequestHeader(value = "user") String user,
                                          @RequestHeader(value = "token") String token,
                                          @RequestHeader(value = "item") String item){
        try{
            HashMap<String, Object> params = validateFields(user, token, item);
            User userObject = userService.getUserByTokenAndID((Long) params.get("user"), token);
            if(userObject == null)
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            RequestHelp requestHelp = service.findById((Long) params.get("item"));
            if(requestHelp == null)
                return new ResponseEntity(HttpStatus.NO_CONTENT);

            if(service.closeRequestHelp(requestHelp))
                return new ResponseEntity(HttpStatus.ACCEPTED);
            else
                return new ResponseEntity(HttpStatus.NOT_MODIFIED);

        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    private HashMap<String, Object> validateFields(String user, String token, String item) {

        if(user == null || user.isEmpty())
            throw new InvalidParameterException("Header missing: user");
        if(token == null || token.isEmpty())
            throw new InvalidParameterException("Header missing: token");
        if(item == null || item.isEmpty())
            throw new InvalidParameterException("Header missing: user");

        HashMap<String, Object> params = new HashMap<>();
        try{
            params.put("user", Long.parseLong(user));
            params.put("item", Long.parseLong(item));
        }catch (Exception e){
            throw new InvalidParameterException("Problems to convert header's items");
        }
        return params;
    }


    private HttpEntity processRequestHelpReturn(List<RequestHelp> helpList) {
        List<RequestHelpMod> mods = new ArrayList<RequestHelpMod>();
        for(RequestHelp r : helpList)
            mods.add(new RequestHelpMod(r));

        return new ResponseEntity(mods, HttpStatus.OK);
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
