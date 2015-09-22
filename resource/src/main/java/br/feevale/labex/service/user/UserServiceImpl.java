package br.feevale.labex.service.user;

import br.feevale.labex.controller.mod.LoginMod;
import br.feevale.labex.controller.mod.UserMod;
import br.feevale.labex.exceptions.InvalidUserNameException;
import br.feevale.labex.model.Account;
import br.feevale.labex.model.AccountType;
import br.feevale.labex.model.User;
import br.feevale.labex.model.UserProfile;
import br.feevale.labex.repository.AccountRepository;
import br.feevale.labex.repository.AccountTypeRepository;
import br.feevale.labex.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by 0126128 on 01/06/2015.
 */
@Service
public class UserServiceImpl implements UserService {


    private final Logger log = Logger.getLogger(getClass().getName());


    private final UserRepository repository;
    private final AccountRepository accountRepository;
    private final AccountTypeRepository accountTypeRepository;

    @Inject
    public UserServiceImpl(UserRepository repository, AccountRepository accountRepository,
                           AccountTypeRepository accountTypeRepository) {
        this.repository = repository;
        this.accountRepository = accountRepository;
        this.accountTypeRepository = accountTypeRepository;
    }

    @Override
    public UserMod getProfile(Long id) {
        User user = findById(id);
        if(user != null && user.getId() > 0)
            return new UserMod(user);
        else
            return null;
    }

    @Override//TODO: Alterar código lixo
    public UserProfile getPublicProfile(Long id) {
        List<Object[]> objects = repository.loadProfile(id);
        UserProfile userProfile = new UserProfile();
        if(objects != null && objects.size() > 0){
            userProfile.id = (BigInteger)objects.get(0)[0];
            userProfile.username = (String)objects.get(0)[1];
            userProfile.degree = (String)objects.get(0)[2];
            userProfile.latitude = (Float)objects.get(0)[3];
            userProfile.longitude = (Float)objects.get(0)[4];
            userProfile.picturePath = (String)objects.get(0)[5];
            userProfile.semester = objects.get(0)[6] == null ? 0 : (Integer)objects.get(0)[6];
            userProfile.requester =  ((BigInteger)objects.get(0)[7]).intValue();
            userProfile.helper = ((BigInteger)objects.get(0)[8]).intValue();
            userProfile.evaluations = ((BigInteger)objects.get(0)[9]).intValue();
            userProfile.answerPoints = ((BigDecimal)objects.get(0)[10]).intValue();
            userProfile.helpPoints = ((BigDecimal)objects.get(0)[11]).intValue();

            return userProfile;
        }

        return null;
    }

    @Transactional
    @Override
    public Account saveAccount(Account account) {
        return accountRepository.saveAndFlush(account);
    }

    @Transactional
    @Override
    public AccountType saveAccountType(AccountType accountType) {
        return accountTypeRepository.saveAndFlush(accountType);
    }

    @Override
    public User getUserHelperInteraction(Long idInteraction) {
        return null;
    }

    @Override
    public User getUserByInteraction(Long idInteraction, Long id) {
        return repository.findUserByInteraction(idInteraction, id);
    }

    @Override
    public List<UserMod> searchUsersToHelp(String param, int position, int max, Long idSubject, Long idUser) {

        position = position == 1 ? 0 : position;
        max = max > 10 || max == 0 ? 10 : max;

        List<User> users = null;
        if(idSubject != null && idSubject > 0)
            users = repository.searchUsersToHelp(param, idSubject, position, max, idUser);
        else
            users = repository.searchUsersToHelp(param, position, max, idUser);

        List<UserMod> mods = users.stream().map(UserMod::new).collect(Collectors.toList());
        return mods;
    }

    @Transactional
    @Override
    public User login(LoginMod loginMod, String type) {
        User user = repository.findUserByToken(loginMod.getAccountID());
        if(user != null)
            return user;
        if(!validateUserName(loginMod.getUsername()))
            throw new InvalidUserNameException(loginMod.getUsername());

        user = new User();
        user.setSemester(loginMod.getSemester());
        user.setName(loginMod.getName());

        if(loginMod.getEmail() != null) {
            user.setEmail(loginMod.getEmail());
            if(loginMod.getUsername() == null)
                user.setUsername(loginMod.getEmail().substring(0, loginMod.getEmail().indexOf("@")));
        }
        if(loginMod.getUsername() != null){

            String username = !loginMod.getUsername().contains("@") ?  loginMod.getUsername() :
                               loginMod.getUsername().substring(0, loginMod.getUsername().indexOf("@"));
            user.setUsername(username);

            if(loginMod.getEmail() == null)
                if(loginMod.getUsername().contains("@"))
                    user.setEmail(loginMod.getUsername());
                else
                    user.setEmail(user.getUsername().trim().replaceAll(" ", "").concat("@looky.com"));
        }
        user.setDeviceKey(loginMod.getDeviceKey());
        user.setPicturePath(loginMod.getPicturePath());

        AccountType accountType = accountTypeRepository.findByType(type);

        if(accountType == null)
            throw new IllegalArgumentException("Tipo de conta inválida.");

        Account account = new Account();
        account.setAccountType(accountType);
        account.setProfileStatus(true);
        if(loginMod.getToken().length() <= 400)
            account.setToken(loginMod.getToken());
        account.setAppStatus(true);
        account.setAccount(loginMod.getAccountID());
        account = accountRepository.saveAndFlush(account);

        user.setAccount(account);
        return save(user);
    }

    @Override
    public boolean validateUserName(String username) {
        return repository.findUserByUsername(username) != null ? false : true;
    }

    @Transactional
    @Override
    public User save(User entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public void delete(Long id) {
        User user = findById(id);
        repository.delete(user);
    }

    @Override
    public User findById(Long id) {
        return repository.findOne(id);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public List<User> findByParam(Object... params) {
        return null;//TODO: A fazer;
    }
}
