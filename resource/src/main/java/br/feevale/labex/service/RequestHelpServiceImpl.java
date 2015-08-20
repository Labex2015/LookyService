package br.feevale.labex.service;

import br.feevale.labex.exceptions.IllegalRequestException;
import br.feevale.labex.model.RequestHelp;
import br.feevale.labex.model.User;
import br.feevale.labex.repository.RequestHelpRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by 0126128 on 01/07/2015.
 */
@Service
public class RequestHelpServiceImpl implements RequestHelpService {

    private final RequestHelpRepository repository;

    @Inject
    public RequestHelpServiceImpl(RequestHelpRepository requestHelpRepository) {
        this.repository = requestHelpRepository;
    }

    @Transactional
    @Override
    public RequestHelp save(RequestHelp entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public RequestHelp findById(Long id) {
        return null;
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
}
