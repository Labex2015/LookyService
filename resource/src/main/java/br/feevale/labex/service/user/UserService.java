package br.feevale.labex.service.user;

import br.feevale.labex.controller.mod.UserMod;
import br.feevale.labex.model.Account;
import br.feevale.labex.model.AccountType;
import br.feevale.labex.model.User;
import br.feevale.labex.model.UserProfile;
import br.feevale.labex.service.BaseService;

import java.util.List;

/**
 * Created by 0126128 on 01/06/2015.
 */
public interface UserService extends BaseService<User> {

    UserMod getProfile(Long id);
    UserProfile getPublicProfile(Long id);
    Account saveAccount(Account account);
    AccountType saveAccountType(AccountType accountType);

    User getUserHelperInteraction(Long idInteraction);

    User getUserByInteraction(Long idInteraction, Long id);

    List<UserMod> searchUsersToHelp(String param, int position, int max, Long idSubject);
}
