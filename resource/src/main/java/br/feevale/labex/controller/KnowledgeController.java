package br.feevale.labex.controller;

import br.feevale.labex.controller.mod.KnowledgeMod;
import br.feevale.labex.controller.mod.ResourcesKnowledge;
import br.feevale.labex.controller.utils.APIDoc;
import br.feevale.labex.model.Area;
import br.feevale.labex.model.Knowledge;
import br.feevale.labex.model.Subject;
import br.feevale.labex.model.User;
import br.feevale.labex.service.KnowledgeService;
import br.feevale.labex.service.user.UserService;
import br.feevale.labex.utils.Utils;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiBodyObject;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.pojo.ApiVisibility;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by grimmjowjack on 8/19/15.
 */
@Api(description = APIDoc.DESC_KNOWLEDGE,
        name = "KnowledgeController",
        group = APIDoc.GROUP_KNOWLEDGE,
        visibility = ApiVisibility.PUBLIC)
@RestController
public class KnowledgeController {

    private final KnowledgeService service;
    private final UserService userService;

    @Inject
    public KnowledgeController(KnowledgeService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @ApiMethod(produces = APIDoc.PROD_CONSU_JSON,
               summary = APIDoc.SUM_LIST_AREAS)
    @RequestMapping(value = "/areas")
    public HttpEntity listAreas(){
        return new ResponseEntity(service.listAreas(), HttpStatus.OK);
    }

    /*
    * TODO: Refatorar esse código, está um lixo. Validar Subject enviado, senão vai dar exception.
     */
    @ApiMethod(produces = APIDoc.PROD_CONSU_JSON,
               consumes = APIDoc.PROD_CONSU_JSON,
               summary = APIDoc.SUM_SAVE_KNOWLEDGE)
    @ApiBodyObject(clazz = KnowledgeMod.class)
    @RequestMapping(value = "/knowledge", method = RequestMethod.POST)
    public ResponseEntity saveKnowledge(@RequestBody KnowledgeMod param) {

        ResponseEntity responseEntity = getErrorResponse(param);
        if(responseEntity != null)
            return responseEntity;

        User user = null;
        if (Utils.verifyParamEmptyOrNull(param.idUser))
            user = userService.findById(param.idUser);
        else if(user == null)
            return new ResponseEntity("Usuário inválido.", HttpStatus.UNPROCESSABLE_ENTITY);

        Area area = null;
        if(Utils.verifyParamEmptyOrNull(param.area)){
            area = service.findAreaById(param.area);
            if(area == null)
                return new ResponseEntity("Impossível localizar área com id: "+param.area,
                        HttpStatus.UNPROCESSABLE_ENTITY);
            else
                param.name = area.getName();
        }else{
            area = service.saveArea(new Area(param.name));
            param.area = area.getId();
        }

        Knowledge knowledge = new Knowledge(user, area);
        if(param.subject != null && param.subject > 0) {
            Subject subject = service.findSubjectById(param.subject);
            knowledge.setSubject(subject);
        }
        service.save(knowledge);

        return new ResponseEntity(param, knowledge != null ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ApiMethod(summary = APIDoc.SUM_DELETE_KNOWLEDGE,
               responsestatuscode = APIDoc.STATUS_DELETE_KNOWLEDGE)
    @RequestMapping(value = "/user/{idUser}/knowledge/{idArea}", method = RequestMethod.DELETE)
    public HttpEntity deleteKnowledge(@PathVariable("idUser") Long idUser, @PathVariable("idArea") Long idArea){
        if(!Utils.verifyParamEmptyOrNull(idUser) || !Utils.verifyParamEmptyOrNull(idArea))
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        if(service.delete(idArea, idUser))
            return new ResponseEntity(HttpStatus.OK);
        else
            return new ResponseEntity(HttpStatus.NOT_MODIFIED);
    }

    @ApiMethod(summary = APIDoc.SUM_LIST_SUBJECTS)
    @RequestMapping(value = "/subjects", produces = "application/json")
    public HttpEntity getSubjects(){
        return new ResponseEntity(service.listSubjects(), HttpStatus.OK);
    }


    @ApiMethod(summary = APIDoc.SUM_LIST_KNOWLEDGE,
            responsestatuscode = APIDoc.STATUS_LIST_KNOWLEDGE)
    @RequestMapping(value = "/user/{idUser}/knowledge", method = RequestMethod.GET)
    public HttpEntity getKnowledgesByUser(@PathVariable("idUser") Long idUser){
        List<KnowledgeMod> knowledges = service.listKnowledgesByUser(idUser);
        if(knowledges.isEmpty())
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity(knowledges, HttpStatus.OK);
    }


    @ApiMethod(summary = APIDoc.SUM_RESURCES_KNOWLEDGE,
            responsestatuscode = APIDoc.STATUS_LIST_KNOWLEDGE_RESOURCE)
    @RequestMapping(value = "/knowledge/resources", method = RequestMethod.GET)
    public HttpEntity getKnowledgesAndAreas(){
        ResourcesKnowledge resource = new ResourcesKnowledge();
        resource.areas = service.listAreas();
        resource.subjects = service.listSubjects();

        return new ResponseEntity(resource, HttpStatus.OK);
    }

    private ResponseEntity getErrorResponse(Object object) {

        if(object instanceof KnowledgeMod) {
            KnowledgeMod param = (KnowledgeMod)object;
            if (!Utils.verifyParamEmptyOrNull(param.area) && !Utils.verifyParamEmptyOrNull(param.name)) {
                return new ResponseEntity("Dados referênte a área estão inválidos: {" +
                        param.area + " : " + param.name + "}", HttpStatus.UNPROCESSABLE_ENTITY);
            }

        }
        return null;
    }
}
