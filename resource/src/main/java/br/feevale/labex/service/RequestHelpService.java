package br.feevale.labex.service;

import br.feevale.labex.model.RequestHelp;
import br.feevale.labex.model.User;

/**
 * Created by 0126128 on 08/06/2015.
 */
public interface RequestHelpService extends BaseService<RequestHelp>{
    RequestHelp saveRequestHelp(User user, User toRequest, String text, String params);
}
