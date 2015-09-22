package service;

import br.feevale.labex.repository.AccountRepository;
import br.feevale.labex.repository.AccountTypeRepository;
import br.feevale.labex.repository.UserRepository;
import br.feevale.labex.service.user.UserService;
import mocks.UserAndAccountMocks;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by 0126128 on 02/06/2015.
 */
//@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private static final Long VALID_ID = 1L;
    private static final Long INVALID_ID = 988889898989898998L;
    private static final Long ID_ZERO = 0L;

    //@Mock
    private UserRepository repository;


    //@Mock
    private AccountRepository accountRepository;

    //@Mock
    private AccountTypeRepository accountTypeRepository;

    //private UserService service;

    //@Before
    public void setUp(){
        //service = null;
    }

    //@Test
    public void findInvalidUserID(){
        when(repository.findOne(ID_ZERO)).thenReturn(null);
        //assertThat(service.getProfile(ID_ZERO)).isEqualTo(null);
    }

    //@Test
    public void searchUsersToHelpOk(){
        when(repository.searchUsersToHelp("", 0, 10, VALID_ID))
                .thenReturn(UserAndAccountMocks.returnUserList());

        //assertThat(service.searchUsersToHelp("", 1, 100, null).size())
          //      .isEqualTo(UserAndAccountMocks.returnUserList().size());
    }


}
