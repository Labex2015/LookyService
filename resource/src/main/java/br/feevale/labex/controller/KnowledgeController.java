package br.feevale.labex.controller;

import br.feevale.labex.controller.mod.KnowledgeMod;
import br.feevale.labex.service.KnowledgeService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * Created by grimmjowjack on 8/19/15.
 */
@RestController
public class KnowledgeController {

    private final KnowledgeService service;

    @Inject
    public KnowledgeController(KnowledgeService service) {
        this.service = service;
    }

    @RequestMapping(value = "/areas")
    public HttpEntity listAreas(){
        return new ResponseEntity(service.listAreas(), HttpStatus.OK);
    }

    @RequestMapping(value = "/knowledge", method = RequestMethod.POST)
    public ResponseEntity saveKnowledge(@RequestBody KnowledgeMod knowledgeMod) {

        if(!validateKnowledge(knowledgeMod)){
            String message = knowledgeMod.idUser == null || knowledgeMod.idUser == 0 ?
                                                    "Dados de usuário inválido -> 0/null"
                                                    : "Campo referênte a área está inválido: "+String.valueOf(knowledgeMod.area) +;
            return new ResponseEntity(message,HttpStatus.UNPROCESSABLE_ENTITY);
        }




        return null;
    }

    private boolean validateKnowledge(KnowledgeMod knowledgeMod) {
        return  ((knowledgeMod.area != null && knowledgeMod.area > 0)
               ||(knowledgeMod.name != null && knowledgeMod.name.length() > 0 ))
              && (knowledgeMod.idUser != null && knowledgeMod.idUser > 0);
    }
}
