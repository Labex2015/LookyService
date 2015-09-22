package br.feevale.labex.service;

import br.feevale.labex.controller.mod.RequestHelpGlobalMod;
import br.feevale.labex.model.RequestHelp;
import br.feevale.labex.model.RequestHelpGlobal;
import br.feevale.labex.model.User;

import java.util.List;

/**
 * Created by 0126128 on 08/06/2015.
 */
public interface RequestHelpService extends BaseService<RequestHelp>{
    RequestHelp saveRequestHelp(User user, User toRequest, String text, String params);

    List<RequestHelp> loadPendingInteractions(Long idUser);

    RequestHelpGlobal openGlobalRequestHelp(RequestHelpGlobal global);

    List<RequestHelpGlobalMod> listAllGlobalRequestHelp();
}
