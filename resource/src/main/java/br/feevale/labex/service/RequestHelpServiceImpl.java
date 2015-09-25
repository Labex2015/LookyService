package br.feevale.labex.service;

import br.feevale.labex.controller.mod.RequestHelpGlobalMod;
import br.feevale.labex.controller.mod.UserMod;
import br.feevale.labex.exceptions.IllegalRequestException;
import br.feevale.labex.model.Interaction;
import br.feevale.labex.model.RequestHelp;
import br.feevale.labex.model.RequestHelpGlobal;
import br.feevale.labex.model.User;
import br.feevale.labex.repository.RequestHelpGlobalRepository;
import br.feevale.labex.repository.RequestHelpRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 0126128 on 01/07/2015.
 */
@Service
public class RequestHelpServiceImpl implements RequestHelpService {

    private final RequestHelpRepository repository;
    private final RequestHelpGlobalRepository globalRepository;

    @Inject
    public RequestHelpServiceImpl(RequestHelpRepository repository,
                                  RequestHelpGlobalRepository globalRepository) {
        this.repository = repository;
        this.globalRepository = globalRepository;
    }

    @Override
    public RequestHelp save(RequestHelp entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public RequestHelp findById(Long id) {
        return repository.findOne(id);
    }

    @Override
    public List<RequestHelp> findAll() {
        return null;
    }

    @Override
    public List<RequestHelp> findByParam(Object... params) {
        return null;
    }

    @Override
    public RequestHelp saveRequestHelp(User user, User toRequest, String text, String params) {

        RequestHelp requestHelp = repository.findByRequesterAndHelper(user.getId(), toRequest.getId());
        if(requestHelp != null)
            throw  new IllegalRequestException("Já existem um pedido ou uma interação para estes dois usuários");

        RequestHelp help = new RequestHelp(user, toRequest, text, params, new Date());
        return save(help);
    }

    @Override
    public List<RequestHelp> loadPendingInteractions(Long idUser) {
        return repository.findAllAsWaitingStatus(idUser);
    }

    @Transactional
    @Override
    public RequestHelpGlobal openGlobalRequestHelp(RequestHelpGlobal global) {

        RequestHelp requestHelp = global.getRequestHelp();
        if(requestHelp.getId() == null || requestHelp.getId() == 0) {
            requestHelp.setDate(new Date());
            requestHelp.setType(RequestHelp.GLOBAL);
            requestHelp.setStatus(RequestHelp.OPEN);
        }else{
            requestHelp.setHelper(null);
        }
        global.setRequestHelp(null);
        global = globalRepository.saveAndFlush(global);

        requestHelp.setRequestGlobal(global);
        requestHelp = repository.saveAndFlush(requestHelp);

        return requestHelp.getRequestGlobal();
    }

    @Transactional
    @Override
    public List<RequestHelpGlobalMod> listAllGlobalRequestHelp() {

        List<RequestHelp> globalList = repository.findAllByStatus(RequestHelp.OPEN);
        List<RequestHelpGlobalMod> models = new ArrayList<>();
        RequestHelpGlobalMod mod = null;

        for(RequestHelp r : globalList){
            if(r.getRequestGlobal() != null) {
                mod = new RequestHelpGlobalMod(r.getId(), r.getRequestGlobal().getTitle(),
                        r.getRequestGlobal().getDescription(), r.getParams());
                mod.user = new UserMod(r.getRequester());
                models.add(mod);
            }
        }

        return models;
    }

    @Override
    public Boolean closeRequestHelp(RequestHelp requestHelp) {
        try {
            requestHelp.setStatus(RequestHelp.CLOSED);
            requestHelp.setRequestGlobal(null);
            save(requestHelp);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
