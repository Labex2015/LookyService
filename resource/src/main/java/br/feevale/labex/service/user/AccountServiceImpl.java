package br.feevale.labex.service.user;

import br.feevale.labex.model.Account;
import br.feevale.labex.repository.AccountRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by 0126128 on 01/06/2015.
 */
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    @Inject
    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public Account save(Account entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Account findById(Long id) {
        return null;
    }

    @Override
    public List<Account> findAll() {
        return null;
    }

    @Override
    public List<Account> findByParam(Object... params) {
        return null;
    }
}
